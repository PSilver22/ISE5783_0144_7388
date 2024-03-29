package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import primitives.Util;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDist) {
// Get the intersections of the plane and check if it falls in the triangle
        List<GeoPoint> intersectionList = plane.findGeoIntersections(ray, maxDist);
        if (intersectionList == null) return null;

        GeoPoint gp = intersectionList.get(0);
        gp.geometry = this;

        Point p = gp.point;
        Vector normal = plane.getNormal();

        // Get the points of the triangle vertices
        Point v1 = vertices.get(0);
        Point v2 = vertices.get(1);
        Point v3 = vertices.get(2);

        try {
            // Find the area of the whole triangle and the area of two of the sub-triangles which are made with p
            double areaTriangle = normal.dotProduct(new Vector(v2.subtract(v1)).crossProduct(new Vector(v3.subtract(v1))));
            double areaSub1 = normal.dotProduct(new Vector(v2.subtract(p)).crossProduct(new Vector(v3.subtract(p))));
            double areaSub2 = normal.dotProduct(new Vector(v3.subtract(p)).crossProduct(new Vector(v1.subtract(p))));
            double areaSub3 = normal.dotProduct(new Vector(v1.subtract(p)).crossProduct(new Vector(v2.subtract(p))));

            // The barycentric x, y, and z coordinates will be the ratio of sub-triangle area to the whole triangle area
            double baryX = Util.alignZero(areaSub1 / areaTriangle);
            double baryY = Util.alignZero(areaSub2 / areaTriangle);
            double baryZ = Util.alignZero(areaSub3 / areaTriangle);

            // If any of the coordinates are not within the triangle, return null
            if (baryX <= 0 || baryX > 1) return null;
            if (baryY <= 0 || baryY > 1) return null;
            if (baryZ <= 0 || baryZ > 1) return null;

            // The barycentric coordinates must add to 0
            if (!Util.isZero(1 - (baryX + baryY + baryZ))) return null;

            return intersectionList;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    //getters,toString,getNormal should be inherited from polygon
}