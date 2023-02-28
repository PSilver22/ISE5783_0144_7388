package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Tube class represents three-dimensional tube in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Tube implements Geometry{
    //the central ray and the radius of the tube
    protected final Ray ray;
    protected final double radius;
    /**
     * Constructor for a tube represented by a ray and a radius
     * @param r,d the center Ray and the radius double
     */
    public Tube(Ray r, double d){
        ray = r;
        radius = d;
    }

    public Ray getRay() {
        return ray;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "ray:" + ray + ", radius:" + radius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
