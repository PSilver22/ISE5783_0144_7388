package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Class which follows a Ray through a 3D scene and calculates the colors it picks up.
 */
public class RayTracerBasic extends RayTracerBase {
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Finds the color of a point in the scene
     * @param p
     * @return Color of the point in the scene
     */
    public Color calcColor(GeoPoint p) {
        return scene.ambientLight.getIntensity().add(p.geometry.getEmission());
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        if (intersections == null) return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections));
    }
}
