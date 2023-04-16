package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface which represents a 3D object that can intersect with a ray
 */
public interface Intersectable {
    /**
     * Find the intersections of the Intersectable 3D object and a ray
     * @param ray The ray to find intersections with
     * @return List of points which intersect with the ray
     */
    List<Point> findIntersections(Ray ray);
}
