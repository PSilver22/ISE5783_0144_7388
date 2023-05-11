package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A light directed at an area
 */
public class SpotLight extends PointLight {
    private Vector direction = null;

    /**
     * Constructor for SpotLight
     * @param color Color of the light
     * @param position The light's position in the scene
     * @param direction The direction the light is pointing
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction;
    }
}
