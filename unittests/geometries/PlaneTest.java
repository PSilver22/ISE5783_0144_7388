package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void testConstructor() {
        //correct plane
        try {
            new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        //test that trying to construct a plane from duplicate points throws an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(4,2,7),new Point(4,2,7),new Point(8,9,3)),
                "Error, constructs a plane with duplicate points");
        //test that trying to construct a plane from points all on the same line throws an exception
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
        assertTrue(expected == testN || expected.scale(-1) == testN,
                "Error: 3 points constructor does not calculate normal correctly");
    }
}