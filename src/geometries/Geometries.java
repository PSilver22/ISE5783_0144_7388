package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    private List<Intersectable> shapes;

    /**
     * Constructor for empty Geometries
     */
    public Geometries() {
        shapes = new LinkedList<>();
    }

    /**
     * Constructor for Geometries which is made up of the passed Intersectables
     * @param geometries Intersectable objects which make up the Geometries
     */
    public Geometries(Intersectable... geometries) {
        this();

        // Use List.of to create a collection, then add the items
        shapes.addAll(List.of(geometries));
    }

    /**
     * Add intersectables to the Geometries object
     * @param geometries List of intersectables to add
     */
    public void add(Intersectable... geometries) {
        shapes.addAll(List.of(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDist) {
        List<GeoPoint> result = null;

        // Collect all the intersections of the shapes in the Geometries object
        for (Intersectable shape : shapes) {
            List<GeoPoint> shapeIntersections = shape.findGeoIntersectionsHelper(ray, maxDist);

            if (shapeIntersections == null) continue;

            if (result == null) result = new LinkedList<>();

            result.addAll(shapeIntersections);
        }

        return result;
    }
}
