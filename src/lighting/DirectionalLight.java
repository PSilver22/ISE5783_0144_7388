package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Light which is sitting at an "infinite distance" from the scene.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction = null;

    /**
     * constructor for a new directional light of the passed color and intensity
     * @param intensity intensity of color
     * @param direction direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}