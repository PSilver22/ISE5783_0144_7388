package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

class TubeTest {

    @Test
    void getNormal() {
        Tube t = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 2);

        // ====== Equivalence Partition ======

        // TC01: Point on the tube
        Point p = new Point(7, 2, 0);

        assertEquals(new Point(0, 1, 0),
                t.getNormal(p),
                "Tube's normal is the wrong value.");

        // ====== Boundary Value Analysis ======

        // TC02: p - base is perpendicular to the ray
        p = new Point(0, 0, 2);

        assertEquals(new Point(0, 0, 1),
                t.getNormal(p),
                "Tube's normal is the wrong value when p - p0 is perpendicular to the ray.");
    }
}