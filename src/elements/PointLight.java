package elements;

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
     * The constructor for PointLight
     * @param color Color of the light
     * @param position The position of the light in the scene
     */
    public PointLight (Color color, Point position)
    {
        super(color);
        this.position = position;
    }

    /**
     * The constant coefficient of the attenuation of the light
     * @param kC
     * @return The newly updated LightSource
     */
    public PointLight setkC(double kC)
    {
        this.kC = kC;
        return this;
    }

    /**
     * The linear coefficient of attenuation
     * @param kL
     * @return The newly updated LightSource
     */
    public PointLight setkL(double kL)
    {
        this.kL = kL;
        return this;
    }

    /**
     * The quadratic coefficient of attenuation
     * @param kQ
     * @return The newly updated LightSource
     */
    public PointLight setkQ(double kQ)
    {
        this.kQ = kQ;
        return this;
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
