package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;

class SphereTest {

    @Test
    void getNormal() {
        // ====== Equivalence Partition ======

        // TC01: Point on the sphere
        Sphere s = new Sphere(new Point(0, 0, 0), 1);
        Point p = new Point(1, 0, 0);

        assertEquals(new Vector(1, 0, 0), s.getNormal(p), "Sphere's normal is the wrong value.");
    }
}