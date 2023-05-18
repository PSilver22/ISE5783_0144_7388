package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A light directed at an area
 */
public class SpotLight extends PointLight {
    private Vector direction;
    private double scale = 1;

    /**
     * constructor for a new spot light of the passed color and intensity at passed position and direction
     * @param intensity light color
     * @param position light position
     * @param direction direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the width of the spotlight beam
     * @param expScale
     * @return The newly updated spotlight
     */
    public SpotLight setNarrowBeam(double expScale) {
        scale = expScale;

        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        //double angle = Math.acos(direction.dotProduct(getL(p)));
        //if (angle > radians) { return super.getIntensity(p).scale(0); }

        //double freq = Math.PI / (2 * radians);

        //return super.getIntensity(p).scale(Math.max(0, Math.cos(freq * angle)));
        return super.getIntensity().scale(Math.pow(Math.max(0, direction.dotProduct(getL(p))), scale));
    }
}
