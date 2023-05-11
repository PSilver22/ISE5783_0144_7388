package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for light sources in a scene
 */
public interface LightSource {

    /**
     * Get the Color the light imparts on the given point
     * @param p
     * @return Color of the light at the point p
     */
    Color getIntensity(Point p);

    /**
     * Get the direction from the vector to the point.
     * @param p
     * @return The light's direction vector to the point p
     */
    Vector getL(Point p);
}
