package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * The base class for a ray tracer
 */
public abstract class RayTracerBase {
    protected Scene scene;
    protected Vector vTo;

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

    /**
     * Tells the raytracer what direction the camera is facing
     * @param vTo
     * @return The newly updated RayTracerBase
     */
    public RayTracerBase setCameraDirection(Vector vTo) {
        this.vTo = vTo;
        return this;
    }

}
