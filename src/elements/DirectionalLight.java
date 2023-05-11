package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Light which is sitting at an "infinite distance" from the scene.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction = null;

    /**
     * constructor for a new light of the passed color and intensity
     *
     * @param intensity
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }
}
