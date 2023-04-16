package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Sphere class represents three-dimensional sphere in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Sphere implements Geometry{
    //center and radius of the sphere
    private final Point center;
    private final double radius;

    /**
     * Constructor for a sphere represented by a point and a radius
     * @param p,d the center Point and the radius double
     */
    public Sphere(Point p, double d) {
        center = p;
        radius = d;
    }

    public double getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point p) {
        return (new Vector(p.subtract(center))).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) { return null; }

    @Override
    public String toString() {
        return "center:" + center + ", radius=" + radius;
    }
}
