package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        Ray testRay = new Ray(
                new Point(0, 0, 0),
                new Vector(1, 0, 0)
        );

        // ------ Equivalence Partition ------

        // TC01: Min point is in the middle of the list
        assertEquals(new Point(1, 1, 0),
                testRay.findClosestPoint(List.of(
                    new Point(1000, 0, 0),
                    new Point(1, 1, 0),
                    new Point(0, 0, 1000)
        )), "Error TC01: findClosestPoint returns the wrong value.");

        // ------ Boundary Value Analysis ------

        // TC02: Empty list
        assertNull(testRay.findClosestPoint(List.of()), "ERROR TC02: findClosestPoint returns non-null when the points list is empty.");

        // TC03: Min point is in the front of the list
        assertEquals(new Point(1, 1, 0),
                testRay.findClosestPoint(List.of(
                        new Point(1, 1, 0),
                        new Point(1000, 0, 0),
                        new Point(0, 0, 1000)
                )), "Error TC03: findClosestPoint returns the wrong value when the min point is in the front of the list.");

        // TC04: Min point is in the back of the list
        assertEquals(new Point(1, 1, 0),
                testRay.findClosestPoint(List.of(
                        new Point(1000, 0, 0),
                        new Point(0, 0, 1000),
                        new Point(1, 1, 0)
                )), "Error TC03: findClosestPoint returns the wrong value when the min point is in the front of the list.");
    }
}