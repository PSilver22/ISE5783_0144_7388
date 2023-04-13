package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        // ------ Boundary Value Analysis ------

        // TC01: Empty geometry
        Geometries tc01 = new Geometries();
        assertNull(tc01.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))),
                "TC01: findIntersections returns a value for an empty geometry.");

        Geometries g = new Geometries(
                new Triangle(new Point(1, 0, 1), new Point(1, 1, 1), new Point(0, 1, 1)),
                new Plane(new Point(0, 0, 1), new Vector(0, 1, 0)),
                new Sphere(new Point(1, 0, 1), 1)
        );

        // TC02: No intersections
        assertNull(g.findIntersections(new Ray(new Point(-1, 0, 1), new Vector(-1, 0, 0))),
                "TC02: findIntersections returns a value when there are no intersections");

        // TC03: One intersection
        assertEquals(1, g.findIntersections(new Ray(new Point(0, 3, 1), new Vector(1, -1, 0))).size(),
                "TC03: findIntersections doesn't return exactly 1 point when there is one intersection.");

        // TC04: All Intersections
        assertEquals(5, g.findIntersections(new Ray(new Point(0, 1.5, 1), new Vector(1, -1, 0))).size(),
                "TC04: findIntersections doesn't return exactly 5 intersections pointer where there are 5 intersections.");

        // ------ Equivalence Partition ------

        // TC05: Several intersections, but not all
        assertEquals(3, g.findIntersections(new Ray(new Point(0, .5, 1), new Vector(1, -1, 0))).size(),
                "TC05: findIntersections doesn't return exactly 3 intersection points where there are 3 intersections.");

    }
}