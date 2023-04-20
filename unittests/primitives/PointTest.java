package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *       Vector v1 = new Vector(1, 2, 3);
 *       Vector v2 = new Vector(-2, -4, -6);
 *       Vector v3 = new Vector(0, 3, -2);
 */

class PointTest {
    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}
     */
    @Test
    void add() {
        Point p1 = new Point(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ------ Boundary Value Analysis ------

        // TC01: Move the point to the origin
        assertEquals(p1.add(v2),
                new Point(0, 0, 0),
                "Point + -Point does not work correctly");

        // ------ Equivalence Partition ------

        // TC02: Adding two points
        assertEquals(p1.add(new Vector(-2, -4, -6)),
                new Point(-1, -2, -3),
                "Point + Point does not work correctly");
    }

    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-2, -4, -6);

        // ------ Equivalence Partition ------

        // TC01: Subtract two points
        assertEquals(p1.subtract(p2),
                new Point(3, 6, 9),
                "Point - Point does not work correctly");

        // TC02: Subtract two more points
        assertEquals(new Point(2, 3, 4).subtract(p1),
                new Point(1, 1, 1),
                "Point - Point does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}
     */
    @Test
    void distanceSquared()
    {
        // ============ Equivalence Partitions Test ==============
        //test if distanceSquared method returns correct result
        assertEquals(0,new Point(0, 0, 0).distanceSquared(new Point(1,1,1)) - 3, 0.00000001,
                "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}
     */
    @Test
    void distance()
    {
        // ============ Equivalence Partitions Test ==============
        //test if distance method returns correct result
        assertEquals(0,new Point(0, 0, 0).distance(new Point(1,1,1)) - Math.sqrt(3), 0.00000001,
                "ERROR: length() wrong value");
    }
}

