package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    public void testConstructor() {
        // TC01: Correct triangle
        try {
            new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct triangle");
        }

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(0, 0, 2), new Point(0, 0, 3)),
                "Constructed a triangle with vertex on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 1)),
                "Constructed a triangle with vertex on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 1)),
                "Constructed a triangle with vertex on a side");
    }

    @Test
    public void testGetNormal() {}

    @Test
    public void findIntersections() {
        Triangle t = new Triangle(new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 1));

        // ====== Equivalence Partitions ======

        // TC01: Opposite a corner
        Ray tc01 = new Ray(new Point(0, 1, 0), new Vector(2, -3, 1));
        assertNull(t.findIntersections(tc01),
                "Error TC01: findIntersections returns a point when the ray is outside the opposite corner of the triangle.");

        // TC02: Off the side
        Ray tc02 = new Ray(new Point(0, 1, 0), new Vector(0, -2, 1));
        assertNull(t.findIntersections(tc02),
                "Error TC02: findIntersections returns a point when the ray is outside the side of the triangle.");

        // TC03: Inside the triangle
        Ray tc03 = new Ray(new Point(0, 1, 0), new Vector(.5, -.25, 1));
        assertEquals(List.of(new Point(.5, .75, 1)),
                t.findIntersections(tc03),
                "Error TC03: findIntersections returns the wrong points when the ray is inside the triangle.");

        // ====== Boundary Value Analysis ======

        // TC04: On the corner of the triangle
        Ray tc04 = new Ray(new Point(0, 1, 0), new Vector(0, 0, 1));
        assertNull(t.findIntersections(tc04),
                "Error TC04: findIntersections returns points when the ray hits the corner of the triangle.");

        // TC05: On the side of the triangle
        Ray tc05 = new Ray(new Point(0, 1, 0), new Vector(.5, 0, 1));
        assertNull(t.findIntersections(tc05),
                "Error TC05: findIntersections returns points when the ray hits the side of the triangle.");

        // TC06: Along the extended line of the side of the triangle
        Ray tc06 = new Ray(new Point(0, 1, 0), new Vector(1, 1, 1));
        assertNull(t.findIntersections(tc06),
                "Error TC04: findIntersections returns points when the ray is on the extended line of the side of the triangle.");
    }
}