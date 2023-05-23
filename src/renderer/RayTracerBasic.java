package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Class which follows a Ray through a 3D scene and calculates the colors it picks up.
 */
public class RayTracerBasic extends RayTracerBase {
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
    private Color calcSpecularLight(LightSource l, GeoPoint p) {
        if (vTo == null) throw new MissingResourceException("ERROR: Ray tracer doesn't know the direction of the camera in the scene.", "RayTracer", "vTo");
        Material mat = p.geometry.getMaterial();

        Vector n = p.getNormal();
        Vector lVector = l.getL(p.point);

        // The direction the shine is directly reflecting off of
        Vector r = lVector.subtract(n.scale(lVector.dotProduct(n)).scale(2));

        Double3 d = mat.kS.scale(Math.pow(Math.max(0, vTo.scale(-1).dotProduct(r)), mat.nShininess));

        return new Color(d);
    }

    /**
     * Helper function which calculates the color contribution of all lights on the point p
     * @param p
     * @return
     */
    private Color calcAllLightColor(GeoPoint p) {
        Color sum = p.geometry.getEmission();
        for (LightSource l : scene.lights) {

            Vector L = l.getL(p.point);
            Vector n = p.getNormal();
            double nl = n.dotProduct(L);
            // THIS SHOULD BE THE OPPOSITE
            if (!Util.checkSign(l.getL(p.point).dotProduct(p.getNormal()), vTo.dotProduct(p.getNormal()))) continue;
            if (unshaded(L,n,p,l,nl))
            {
                sum = sum.add(calcDiffusedLight(l, p).add(calcSpecularLight(l, p)).scale(l.getIntensity(p.point)));
            }
        }

        return sum;
    }

    /**
     * Finds the color of a point in the scene
     * @param p
     * @return Color of the point in the scene
     */
    public Color calcColor(GeoPoint p) {
        //return scene.ambientLight.getIntensity().add(p.geometry.getEmission());

        return scene.ambientLight.getIntensity()
                .add(p.geometry.getEmission())
                .add(calcAllLightColor(p));
    }
    //amount to move the ray’s head when creating shadow rays
    private static final double DELTA = 0.1;

    /**
     * check whether point is in shadow
     * @param l vector from light to geometry
     * @param n normal vector of geometry
     * @param gp point on geometry
     * @return
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource light, double nl)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA); // the normal scaled by DELTA towards the light
        Point point = gp.point.add(deltaVector); //point arrived at by moving along deltavector from intersection
        Ray lightRay = new Ray(point, lightDirection); //a ray from point in direction of the light
        //if there are no interections between point and the light then the point is unshadowed
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        return intersections == null;
    }
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        if (intersections == null) return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections));
    }
}
