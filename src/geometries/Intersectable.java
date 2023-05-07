package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface which represents a 3D object that can intersect with a ray
 */
public abstract class Intersectable {
    /**
     * Class which represents an intersection point on a geometry.
     * Contains a point of intersection and the geometry the point is found on.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor for a point lying on a geometry
         * @param geometry The Geometry point is found on
         * @param point A point on the edge of geometry
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof GeoPoint gp) {
                return geometry == gp.geometry && point == gp.point;
            }

            return false;
        }

        @Override
        public String toString() {
            return "Geometry: " + geometry.toString() + "\nPoint: " + point.toString();
        }
    }

    /**
     * Find the intersections of the Intersectable 3D object and a ray
     * @param ray The ray to find intersections with
     * @return List of points which intersect with the ray
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null:
                geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * Finds all the intersections of ray with the Intersectable object.
     * This method is a hook into the findGeoIntersections according to the NVI design pattern.
     * @param ray
     * @return List of the intersection GeoPoints
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Finds all the intersections of ray with the Intersectable object
     * @param ray
     * @return List of the intersection GeoPoints
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }
}
