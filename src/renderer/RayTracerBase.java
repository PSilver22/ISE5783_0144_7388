package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The base class for a ray tracer
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Set the scene for the ray tracer
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Shoots a ray and calculates the color based on the scene.
     * @param ray
     * @return Color from information gathered by the ray
     */
    public abstract Color traceRay(Ray ray);
}
