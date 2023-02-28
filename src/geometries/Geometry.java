package geometries;

import primitives.Point;
import primitives.Vector;

/**Geometry class is used for various geometries in three-dimensional cartesian space
 * @author: Yossi Tyberg
 */ 
public interface Geometry {
    /**
     * Finds the normal vector from the geometry at a point
     * @param p Point to find the normal from
     */
    Vector getNormal(Point p);
}
