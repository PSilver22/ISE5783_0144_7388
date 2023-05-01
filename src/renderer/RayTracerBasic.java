package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Class which renders an image using ray tracing.
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
    public Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);

        if (intersections == null) return scene.background;
        return calcColor(ray.findClosestPoint(intersections));
    }
}
