package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> shapes;

    public Geometries() {
        shapes = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries) {
        this();

        // Use List.of to create a collection, then add the items
        shapes.addAll(List.of(geometries));
    }

    public void add(Intersectable... geometries) {
        shapes.addAll(List.of(geometries));
    }

    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;

        for (Intersectable shape : shapes) {
            List<Point> shapeIntersections = shape.findIntersections(ray);

            if (shapeIntersections == null) continue;

            if (result == null) result = new LinkedList<>();

            result.addAll(shapeIntersections);
        }

        return result;
    }
}
