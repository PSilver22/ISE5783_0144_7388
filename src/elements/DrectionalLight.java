package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DrectionalLight extends Light implements LightSource {

    private Vector direction = null;

    /**
     * constructor for a new light of the passed color and intensity
     *
     * @param intensity
     */
    protected DrectionalLight(Color intensity, Vector direction) {
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
