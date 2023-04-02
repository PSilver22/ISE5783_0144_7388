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

        assertEquals(p1.add(v2),
                new Point(0, 0, 0),
                "Point + Point does not work correctly");

        assertEquals(p1.add(new Vector(-2, -4, -6)),
                new Point(-1, -2, -3),
                "Point + -Point does not work correctly");
    }

    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-2, -4, -6);

        assertEquals(p1.subtract(p2), new Point(3, 6, 9), "Point - Point does not work correctly");

        assertEquals(new Point(2, 3, 4).subtract(p1),
                new Point(1, 1, 1),
                "Point - Point does not work correctly");
    }
}