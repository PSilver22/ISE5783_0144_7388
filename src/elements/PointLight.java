package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

    public PointLight setkC(double kC)
    {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL)
    {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ)
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
}
