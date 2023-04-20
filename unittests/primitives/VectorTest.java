package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest extends PointTest {

    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);
        // ------ Boundary Value Analysis ------

        // TC01: Add vector to it's negative
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(v2), "ERROR: Vector + -itself does not throw an exception");

        // ------ Equivalence Partition ------

        // TC02: Add vectors
        assertEquals(new Vector(-1, -2, -3), v1.add(new Vector(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        //doesn't check for other exceptions - that ok? if not use doesntThrow

        // ------ Boundary Value Analysis ------

        // TC01: Subtract the vector from itself
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");

        // ------ Equivalence Partition ------

        // TC02: Subtract two vectors
        assertEquals(new Vector(3, 6, 9), v1.subtract(new Vector(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void scale() {
        // ------ Equivalence Partition ------

        // TC01: Scale by a number
        Vector v = new Vector(1, 1, 1);
        assertEquals(new Vector(3, 3, 3),
                v.scale(3),
                "Error TC01: Scale is returning the wrong value.");

        // ------ Boundary Value Analysis ------

        // TC02: Scale by 0
        assertThrows(IllegalArgumentException.class, () -> v.scale(0),
                "Error TC02: Scaling a vector by 0 doesn't throw an IllegalArgumentException.");
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ------ Boundary Value Analysis ------

        // TC01: Dot product of orthogonal vectors
        assertEquals(0,v1.dotProduct(v3), 0.00000001,
                "ERROR: dotProduct() for orthogonal vectors is not zero");

        // ------- Equivalence Partitions -------

        // TC02: Dot product of two vectors
        assertEquals(0,v1.dotProduct(v2) + 28, 0.00000001,
                "ERROR: dotProduct() wrong value");
    }

    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ------ Boundary Value Analysis ------

        // TC01: Cross product of parallel vectors
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");

        Vector vr = v1.crossProduct(v3);

        // ------ Equivalence Partition ------

        // TC02: Test the length of cross product
        assertEquals(0, vr.length() - v1.length() * v3.length(), 0.00000001,
                "ERROR: crossProduct() wrong result length");

        // TC03: Test cross product is orthogonal to both operands
        assertEquals(0, vr.dotProduct(v1),0.00000001,
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0, vr.dotProduct(v3),0.00000001,
                "ERROR: crossProduct() result is not orthogonal to its operands");

    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        // ------ Equivalence Partition ------

        // TC01: Perform lengthSquared on a vector
        assertEquals(0,v1.lengthSquared() - 14, 0.00000001,
                "ERROR: lengthSquared() wrong value");
    }

    @Test
    void length() {
        // ------ Equivalence Partition ------

        // TC01: Perform length on a vector
        assertEquals(0,new Vector(0, 3, 4).length() - 5, 0.00000001,
                "ERROR: length() wrong value");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ------ Equivalence Partition ------

        // TC01: Test that the length is 1
        assertEquals(0, u.length() -1, 0.00000001,
                "ERROR: the normalized vector is not a unit vector");

        // TC02: Check that the normalized vector is parallel to the original
        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC03: Check that the normalized vector is in the same direction
        assertTrue(v.dotProduct(u) >= 0,
                "ERROR: the normalized vector is opposite to the original one");

    }
}