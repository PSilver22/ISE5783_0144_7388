package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest extends PointTest {



    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);
        //doesn't check for other exceptions - that ok?
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(v2), "ERROR: Vector + -itself does not throw an exception");

        assertEquals(new Vector(-1, -2, -3), v1.add(new Vector(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        //doesn't check for other exceptions - that ok? if not use doesntThrow
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");

        assertEquals(new Vector(3, 6, 9), v1.subtract(new Vector(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void scale() {
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertEquals(0,v1.dotProduct(v3), 0.00000001,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(0,v1.dotProduct(v2) + 28, 0.00000001,
                "ERROR: dotProduct() wrong value");
    }

    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");

        Vector vr = v1.crossProduct(v3);

        assertEquals(0, vr.length() - v1.length() * v3.length(), 0.00000001,
                "ERROR: crossProduct() wrong result length");
        //is there a way to do this in one operation?
        assertEquals(0, vr.dotProduct(v1),0.00000001,
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0, vr.dotProduct(v3),0.00000001,
                "ERROR: crossProduct() result is not orthogonal to its operands");

    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(0,v1.lengthSquared() - 14, 0.00000001,
                "ERROR: lengthSquared() wrong value");
    }

    @Test
    void length() {
        assertEquals(0,new Vector(0, 3, 4).length() - 5, 0.00000001,
                "ERROR: length() wrong value");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        assertEquals(0, u.length() -1, 0.00000001,
                "ERROR: the normalized vector is not a unit vector");

        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        assertTrue(v.dotProduct(u) >= 0, "ERROR: the normalized vector is opposite to the original one");

    }
}