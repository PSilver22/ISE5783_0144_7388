package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.stream.Stream;

import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;

/** Sphere class represents three-dimensional sphere in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Sphere extends Geometry {
    //center and radius of the sphere
    private final Point center;
    private final double radius;

    /**
     * Constructor for a sphere represented by a point and a radius
     * @param center,radius the center Point and the radius double
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDist) {

        //distance from base of ray to the point on the ray closest the sphere's center
        double tm;
        //distance from center of sphere to the point on the ray closest the sphere's center
        double d;
        //calculate tm and d
        //if ray base and sphere center are the same the vector connecting them is the 0 vector
        //(which would trigger exception) and tm = d = 0
        try {
            Vector u = new Vector(center.subtract(ray.getPoint()));
            tm = ray.getVector().dotProduct(u);
            d = Math.sqrt(u.lengthSquared()-(tm*tm));
        }
        catch (IllegalArgumentException e) {
            tm = 0;
            d = 0;
        }

        //if d>=radius the ray's closest point to the center (the sphere) is outside/tangent to the sphere(no intersect)
        if (d>=radius)
        {
            return null;
        }
        //distance along the ray between the outer edge of the sphere and point closest the sphere's center
        double th = Math.sqrt((radius*radius) - (d*d));
        //scalars for direction vector of ray
        double t1 = alignZero(tm+th);
        double t2 = alignZero(tm-th);
        //return a list of the intersection points in the direction of the ray - who are scaled positively
        //and are within the max distance
        if(t1 > 0 && alignZero(t1-maxDist)<=0 && t2 > 0 && alignZero(t2-maxDist)<=0)
        {return Stream.of(ray.getPoint(t1), ray.getPoint(t2)).map(p -> new GeoPoint(this, p)).toList();}
        else if (t1 >0 && alignZero(t1-maxDist)<=0)
        {return List.of(new GeoPoint(this, ray.getPoint(t1)));}
        else if (t2 > 0 && alignZero(t2-maxDist)<=0)
        {return List.of(new GeoPoint(this, ray.getPoint(t2)));}
        else
        {return null;}
    }

    @Override
    public String toString() {
        return "center: " + center + ", radius = " + radius;
    }
}
