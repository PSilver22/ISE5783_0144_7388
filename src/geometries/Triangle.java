package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Triangle class represents two-dimensional triangle in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Triangle extends Polygon {
    /**
     * Constructor for a triangle - which has 3 points
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle (Point p1, Point p2, Point p3){
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionList = plane.findIntersections(ray);
        if (intersectionList == null) return null;

        Point p = intersectionList.get(0);

        Vector normal = plane.getNormal();

        Point a = vertices.get(0);
        Point b = vertices.get(1);
        Point c = vertices.get(2);

        try {
            double areaABC = normal.dotProduct(new Vector(b.subtract(a)).crossProduct(new Vector(c.subtract(a))));
            double areaPBC = normal.dotProduct(new Vector(b.subtract(p)).crossProduct(new Vector(c.subtract(p))));
            double areaPCA = normal.dotProduct(new Vector(c.subtract(p)).crossProduct(new Vector(a.subtract(p))));

            double newX = areaPBC / areaABC;
            double newY = areaPCA / areaABC;
            double newZ = 1 - newX - newY;

            if (newX <= 0 || newX > 1) return null;
            if (newY <= 0 || newY > 1) return null;
            if (newZ <= 0 || newZ > 1) return null;

            return intersectionList;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    //getters,toString,getNormal should be inherited from polygon
}