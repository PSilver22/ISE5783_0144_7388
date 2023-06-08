package renderer;

import primitives.Color;
import primitives.Ray;

public interface RayTracer {
    /**
     * Gets a color from the information given by the ray
     * @param ray
     * @return Color from information gathered by the ray
     */
    Color traceRay(Ray ray);
}
