package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Class which follows a Ray through a 3D scene and calculates the colors it picks up.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Helper function which calculates the diffused light's contribution to the color
     * @param l The light source we are calculating relative to
     * @param p The point p
     * @return The diffused light contribution to the point
     */
    private Color calcDiffusedLight(LightSource l, GeoPoint p) {
        Material mat = p.geometry.getMaterial();

        return new Color(mat.kD.scale(Util.abs(l.getL(p.point).dotProduct(p.getNormal()))));
    }

    /**
     * Helper function which calculates the color of the reflection of the light source in the material at a point
     * @param l
     * @param p
     * @return
     */
    private Color calcSpecularLight(LightSource l, GeoPoint p, Ray cameraRay) {
        Vector v = cameraRay.getVector();
        Material mat = p.geometry.getMaterial();

        Vector n = p.getNormal();
        Vector lVector = l.getL(p.point);

        // The direction the shine is directly reflecting off of
        Vector r = lVector.subtract(n.scale(lVector.dotProduct(n)).scale(2));

        Double3 d = mat.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), mat.nShininess));

        return new Color(d);
    }

    /**
     * Helper function which calculates the color contribution of the lights and intersection geometry at a point
     * @param p The point of intersection of cameraRay
     * @param cameraRay The ray from the camera
     * @return
     */
    private Color calcLocalEffects(GeoPoint p, Ray cameraRay, Double3 kx) {
        Color sum = p.geometry.getEmission();
        for (LightSource l : scene.lights) {

            Vector L = l.getL(p.point);
            Vector n = p.getNormal();

            double nl = Util.alignZero(n.dotProduct(L));

            if (!Util.checkSign(l.getL(p.point).dotProduct(p.getNormal()), cameraRay.getVector().dotProduct(p.getNormal()))) continue;
            Double3 ktr = transparency(L,n,p,l,nl);
            ktr = ktr.product(kx);
            if (!ktr.lowerThan(MIN_CALC_COLOR_K))
            {
                sum = sum.add(calcDiffusedLight(l, p).add(calcSpecularLight(l, p, cameraRay))).scale((l.getIntensity(p.point).scale(ktr)));
            }
        }

        return sum;
    }
    /**
     * Create a reflection ray
     * @param gp The camera ray intersection point
     * @param v The camera ray
     * @return Reflection ray
     */
    private Ray constructReflectionRay(GeoPoint gp, Ray v) {
        Vector vDir = v.getVector();
        double nv = Util.alignZero(gp.getNormal().dotProduct(vDir));

        Vector normal = (nv > 0) ? gp.getNormal().scale(-1) : gp.getNormal();

        Vector reflectionDir = vDir.subtract(normal.scale(2 * normal.dotProduct(vDir)));

        return new Ray(gp.point, reflectionDir, normal);
    }

    /**
     * Create a refraction ray
     * @param gp Camera ray intersection point
     * @param v Camera ray
     * @return Refraction ray
     */
    private Ray constructRefractionRay(GeoPoint gp, Ray v) {
        double nv = Util.alignZero(gp.getNormal().dotProduct(v.getVector()));

        Vector normal = (nv > 0) ? gp.getNormal() : gp.getNormal().scale(-1);

        return new Ray(gp.point, v.getVector(), normal);
    }

    /**
     * Finds the intersection point along the ray which is closest to the base
     * @param r
     * @return
     */
    private GeoPoint findClosestIntersection(Ray r) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(r);
        if (intersections == null) return null;

        return r.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the effect on the color at a point from the other geometries in the scene
     * @param gp Intersection point of camera ray
     * @param ray Camera ray
     * @param level Depth of recursive call
     * @param kx Accumulative coefficient of reflection / refraction
     * @return The effect of the geometries at this point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 kx) {
        Color color = Color.BLACK;
        Material mat = gp.geometry.getMaterial();

        // Calculate the reflection at the point
        Double3 kkr = kx.product(mat.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectionRay = constructReflectionRay(gp, ray);

            color = color.add(
                calcColor(
                    findClosestIntersection(reflectionRay),
                    reflectionRay,
                    level - 1,
                    kkr
                ).scale(mat.kR));
        }

        // Calculate the transparency at the point
        Double3 kkt = kx.product(mat.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractionRay = constructRefractionRay(gp, ray);
            color = color.add(calcColor(
                    findClosestIntersection(refractionRay),
                    refractionRay,
                    level - 1,
                    kkt
            ).scale(mat.kT));
        }

        return color;
    }

    /**
     * Calculates the color from a ray at a point
     * @param gp Point of intersection from the camera ray
     * @param ray The camera ray
     * @return The color the ray picked up
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        if (gp == null) return scene.background;
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color from a ray at a point
     * @param gp Point of intersection from the camera ray
     * @param ray The camera ray
     * @param level The depth of the recursion
     * @param kx The accumulated attenuation of reflection / refraction
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 kx) {
        if (gp == null) return scene.background;

        Color color = calcLocalEffects(gp, ray, kx);

        return (1 == level) ? color : color.add(calcGlobalEffects(gp, ray, level, kx));
    }

    /**
     * Check whether a point is shaded from a light source
     * @param l vector from light to geometry
     * @param n normal vector of geometry
     * @param gp point on geometry
     * @param light Light source to check the shadow from
     * @param nl Dot product of n and l
     * @return
     */
    private Double3 transparency(Vector l, Vector n, GeoPoint gp, LightSource light, double nl)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector normal = n.scale(nl < 0 ? 1 : -1); // the normal scaled by DELTA towards the light

        Ray lightRay = new Ray(gp.point, lightDirection, normal); //a ray from point in direction of the light

        //if there are no intersections between point and the light then the point is unshadowed
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint intersectPoint : intersections)
        {
            ktr = ktr.product(intersectPoint.geometry.getMaterial().kT);
        }
        return ktr;
    }
    @Override
    public Color traceRay(Ray ray) {
        return calcColor(findClosestIntersection(ray), ray);
    }
}
