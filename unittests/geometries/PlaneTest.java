package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void Plane() {
        //test that trying to construct a plane from duplicate points throws an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(4,2,7),new Point(4,2,7),new Point(8,9,3)),
                "Error, constructs a plane with duplicate points");
        //test that trying to construct a plane from points on the same line throws an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1,1,1),new Point(2,2,2),new Point(3,3,3)),
                "Error, constructs a plane from points on the same line");
    }

    @Test
    void getNormal() {
        Vector testN = new Plane(new Point(1, 5, 7), new Point(6, 4, 8), new Point(9, 4, 1)).getNormal();
        assertEquals(new Vector(new Double3(-5, 43, 43)), testN,
                "Error: 3 points constructor does not calculate normal correctly");
    }
}