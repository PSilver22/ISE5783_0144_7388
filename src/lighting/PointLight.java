package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Light sitting at a point that imparts light to the surrounding area
 */
public class PointLight extends Light implements LightSource {

    private Point position = null;
    private double kC =1 ,kL = 0,kQ = 0;

    /**
     * constructor for a new point light of the passed color and intensity at passed position
     * @param intensity light color
     * @param position position of the light
     */
    public PointLight (Color intensity, Point position)
    {
        super(intensity);
        this.position = position;
    }

    /**
     * The constant coefficient of the attenuation of the light
     * @param kC
     * @return The newly updated LightSource
     */
    public PointLight setKc(double kC)
    {
        this.kC = kC;
        return this;
    }

    /**
     * The linear coefficient of attenuation
     * @param kL
     * @return The newly updated LightSource
     */
    public PointLight setKl(double kL)
    {
        this.kL = kL;
        return this;
    }

    /**
     * The quadratic coefficient of attenuation
     * @param kQ
     * @return The newly updated LightSource
     */
    public PointLight setKq(double kQ)
    {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity().scale(1/(kC + kL * position.distance(p) + kQ* position.distanceSquared(p)));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return 0;
    }
}
