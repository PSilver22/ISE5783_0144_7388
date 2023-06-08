package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The base class for a ray tracer
 */
public abstract class RayTracerBase implements RayTracer {
    protected Scene scene;

    /**
     * Set the scene for the ray tracer
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
}
