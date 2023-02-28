package geometries;

import primitives.Ray;

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
    public String toString() {
        return super.toString() + ", height:" + height;
    }

    //getNormal should be same as tube, i.e. inherited
}
