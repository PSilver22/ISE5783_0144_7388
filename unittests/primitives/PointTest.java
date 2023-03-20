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

        assertEquals(p1.add(v2), new Point(0, 0, 0));
    }

    @Test
    void subtract() {
    }

    @Test
    void distanceSquared() {
    }

    @Test
    void distance() {
    }
}