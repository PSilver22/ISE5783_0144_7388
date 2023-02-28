package geometries;

import primitives.Point;
import primitives.Vector;
/** Plane class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 * @author Yossi Tyberg */
public class Plane implements Geometry {
    //plane is represented by a point and the orthogonal vector
    private final Point q0;
    private final Vector normal; //vec
    /**constructor based on 3 points
     * @ param p1,p2,p3 - 3 points on the plane
     * @throws IllegalArgumentException if any point is a duplicate of any other point
     */
    public Plane(Point p1, Point p2, Point p3) {
        //throw exception if any points are identical b/c 3 different points are required to determine the plane
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
        {
            throw new IllegalArgumentException("points must be different to determine plane");
        }
        normal = null;
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
        //checking if point is on plane requires the xyz values of the point and vector
        //i.e. needs a way to access double.product()
        return normal;
    }

    @Override
    public String toString() {
        return "q0: (" + q0.toString() + ", normal: " + normal.toString();
    }
}

