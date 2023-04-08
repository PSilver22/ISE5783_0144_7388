package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testConstructor() {
        // TC01: Legal Plane
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)),
                "Error: Correct Plane throws an exception.");

        // TC02: Plane from duplicate points
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(4,2,7),new Point(4,2,7),new Point(8,9,3)),
                "Error, constructs a plane with duplicate points");

        // TC03: Plane from points on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1,1,1),new Point(2,2,2),new Point(3,3,3)),
                "Error, constructs a plane from points on the same line");
    }

    //test that a plane constructed from 3 points returns the correct normal
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Vector testN = new Plane(new Point(1, 5, 7), new Point(6, 4, 8), new Point(9, 4, 1)).getNormal();
        Vector expected = new Vector(new Double3(7, 38, 3)).normalize(); //normalized cross product of the above

        assertTrue(expected.equals(testN) || (expected.scale(-1)).equals(testN),
                "Error: 3 points constructor does not calculate normal correctly");
    }

    @Test
    void findIntersections() {
        Plane testPlane = new Plane(new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 1));

        // ====== Equivalence Partitions Tests ======

        // TC01: Non-orthogonal, non-parallel Ray intersects the plane
        Ray tc01 = new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1));
        assertEquals(List.of(new Point(0, 0, 1)),
                testPlane.findIntersections(tc01),
                "Error TC01: findIntersections doesn't find correct points of intersection.");

        // TC02: Non-orthogonal, non-parallel Ray doesn't intersect the plane
        Ray tc02 = new Ray(new Point(1, 0, 0), new Vector(1, 0, -1));
        assertNull(testPlane.findIntersections(tc02),
                "Error TC02: findIntersections doesn't return null when there isn't an intersection.");

        // ====== Boundary Value Analysis Tests ======

        // TC03: Ray is parallel and not based in the plane
        Ray tc03 = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertNull(testPlane.findIntersections(tc03),
                "Error TC03: findIntersections doesn't return null when the ray is parallel to and not based on the plane.");

        // TC04: Ray is parallel and in the plane
        Ray tc04 = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        assertNull(testPlane.findIntersections(tc04),
                "Error TC04: findIntersections doesn't return null when the ray is parallel to and contained in the plane.");

        // TC05: Ray is orthogonal and intersecting the plane
        Ray tc05 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
        assertEquals(List.of(new Point(1, 0, 1)), testPlane.findIntersections(tc05),
                "Error TC05: findIntersections doesn't return the correct value for an orthogonal intersecting ray.");

        // TC06: Ray is orthogonal and on the plane
        Ray tc06 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        assertNull(testPlane.findIntersections(tc06),
                "Error TC06: findIntersections doesn't return null for an orthogonal ray based on the plane.");

        // TC07: Ray is orthogonal and pointing away from the plane
        Ray tc07 = new Ray(new Point(0, 0, 2), new Vector(0, 0, 1));
        assertNull(testPlane.findIntersections(tc07),
                "Error TC07: findIntersections doesn't return null for an orthogonal ray which doesn't intersect the plane.");

        // TC08: Ray isn't orthogonal nor parallel to the plane and based on the plane
        Ray tc08 = new Ray(new Point(0, 0, 1), new Vector(1, 0, 1));
        assertNull(testPlane.findIntersections(tc08),
                "Error TC08: findIntersections doesn't return null for a ray based on the plane.");

        // TC09: Ray isn't orthogonal, isn't parallel, and has a base equal to the reference point of the plane
        Ray tc09 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 1));
        assertNull(testPlane.findIntersections(tc09),
                "Error TC09: findIntersections doesn't return null for a non-orthogonal, non-parallel, ray with base equal to the plane reference point.");
    }
}