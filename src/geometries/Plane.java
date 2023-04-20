package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;


/** Plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 * @author Yossi Tyberg */
public class Plane implements Geometry {
    //plane is represented by a point and the orthogonal vector
    private final Point q0;
    private final Vector normal; //vec
    /**constructor based on 3 points
     * @ param p1,p2,p3 - 3 points on the plane
     * @throws IllegalArgumentException if any point is a duplicate of any other point or if
     * all three points are on the same line
     */
    public Plane(Point p1, Point p2, Point p3) {
        //throw exception if any points are identical b/c 3 different points are required to determine the plane
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
        {
            throw new IllegalArgumentException("points must be different to determine plane");
        }

        //throw exception if all points are on the same line (b/c a line isn't a plane)

        Point temp = p2.subtract(p1);
        Vector directionV = new Vector(temp.getX(), temp.getY(), temp.getZ()); //direction vector of the line between p1,p2
        //booleans determining whether there's a change in each coordinate on the line (if it's 0
        //there isn't)
        boolean dX = directionV.getX() != 0;
        boolean dY = directionV.getY() != 0;
        boolean dZ = directionV.getZ() != 0;
        //get values for symmetric equation of a line in 3d space
        double x  = dX ? (p3.getX()-p2.getX()) / directionV.getX() : p3.getX();
        double y  = dY ? (p3.getY()-p2.getY()) / directionV.getY() : p3.getY();
        double z  = dZ ? (p3.getZ()-p2.getZ()) / directionV.getZ() : p3.getZ();
        //if the coordinates that aren't 0 in the direction vector are equal, and those that are,
        //are equal to p2's coordinates, then all points are on the same line.
        if((x != y && dX && dY) || (x != z && dX && dZ) || (y != z && dY && dZ)) {}
        else if(!dX && x!= p2.getX() || !dY && y!= p2.getY() || !dZ && z!= p2.getZ()) {}
        else
        {
            throw new IllegalArgumentException("all points are on the same line");
        }
        //normal is the cross product of vectors on the plane, in this case p2-p1 and p3-p1
        Point why = p3.subtract(p1);
        Vector nec = new Vector(why.getX(), why.getY(), why.getZ());
        normal = (directionV.crossProduct(nec)).normalize();
        q0 = p1;
    }
    /**constructor based on a point and a vector
     * @ param p: point on the plane
     * @ param v: vector on the plane
     */
    public Plane (Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    public Point getP() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }


    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // if the base of the ray is on the reference point
        if (q0.equals(ray.getPoint())) return null;

        // Find the scale of the ray if it does intersect the plane
        double nQMinusP0 = normal.dotProduct(new Vector(q0.subtract(ray.getPoint())));
        double nv = normal.dotProduct(ray.getVector());

        // If the ray is parallel to the plane
        if (isZero(nv)) return null;

        double t = alignZero(nQMinusP0 / nv);

        // If the ray scales in the positive direction, return the found point
        if (t > 0) return List.of(ray.getPoint(t));

        // Otherwise, return null
        return null;
    }

    @Override
    public String toString() {
        return "q0: (" + q0.toString() + ", normal: " + normal.toString();
    }
}

