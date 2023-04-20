package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder c = new Cylinder(new Ray(new Point(0, 0 ,0), new Vector(1, 0, 0)), 2, 10);

        // ====== Equivalence Partition ======

        // TC01: Point on round surface
        Point p = new Point(5, 0, 2);
        assertEquals(new Vector(0, 0, 1),
                c.getNormal(p),
                "The normal on the round surface of the cylinder is wrong.");

        // TC02: Point on the bottom base surface
        p = new Point(0, 0, 1);
        assertEquals(new Vector(-1, 0, 0),
                c.getNormal(p),
                "The normal on the bottom base of the cylinder is wrong.");

        // TC03: Point on the top base surface
        p = new Point(10, 0, 1);
        assertEquals(new Vector(1, 0, 0),
                c.getNormal(p),
                "The normal on the bottom base of the cylinder is wrong.");

        // ====== Boundary Value Analysis ======

        // TC06: Point on the edge of the top base
        p = new Point(0, 0, 0);
        assertEquals(new Vector(-1, 0, 0),
                c.getNormal(p),
                "The normal on the bottom base of the cylinder is wrong.");

        // TC07 Point on the edge of the top base
        p = new Point(10, 0, 0);
        assertEquals(new Vector(1, 0, 0),
                c.getNormal(p),
                "The normal on the bottom base of the cylinder is wrong.");
    }
}