package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction = null;

    /**
     * constructor for a new spot light of the passed color and intensity at passed position and direction
     * @param intensity light color
     * @param position light position
     * @param direction direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction =direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0,direction.dotProduct(getL(p))));
    }
}
