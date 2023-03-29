package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Cylinder class represents three-dimensional cylinder in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Cylinder extends Tube {
    //height of the tube
    private final double height;
    /**
     * Constructor for a cylinder represented by a ray, its radius and its height
     * @param r,d,h the center Ray and the radius and height doubles
     */
    public Cylinder(Ray r, double d, double h) {
        super(r, d);
        height = h;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ", height:" + height;
    }
}
