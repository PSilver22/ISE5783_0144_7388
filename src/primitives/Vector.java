package primitives;

/**
 * Class which represents a direction and magnitude in 3D space
 * @author Pinny Silver
 */
public class Vector extends Point {

    /**
     * Constructor for a Vector which points to the coordinate triple of xyz in 3D space
     * @param xyz Coordinate 3-tuple which represents the head of the vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("The zero vector is not allowed.");
    }

    /**
     * Constructor for a Vector with its head at the coordinates x, y, z
     * @param x Head x-axis coordinate
     * @param y Head y-axis coordinate
     * @param z Head z-axis coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Creates a new vector whose head coordinates are the sum of the head coordinates of this and op
     * @param op The Vector operand
     * @return The result of the operation
     */
    public Vector add(Vector op) {
        return new Vector(xyz.add(op.xyz));
    }

    /**
     * Creates a new vector whose head coordinates are the difference of the head coordinates of this and op
     * @param op The Vector operand
     * @return The result of the operation
     */
    public Vector subtract(Vector op) {
        if (equals(op)) { throw new IllegalArgumentException("Can't subtract a vector from itself."); }
        return new Vector(xyz.subtract(op.xyz));
    }

    /**
     * Multiplies the 3-tuple of the head of this by the scalar
     * @param scalar The value to scale the vector by
     * @return The result of the operation
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Calculates the dot product of this and op
     * @param op Vector operand
     * @return The result of the operation
     */
    public double dotProduct(Vector op) {
        Double3 prod = xyz.product(op.xyz);
        return prod.d1 + prod.d2 + prod.d3;
    }

    /**
     * Finds the vector which is perpendicular to this and op vectors
     * @param op Vector operand
     * @return The result of the operation
     */
    public Vector crossProduct(Vector op) {
        // (y1z2 - z1y2, z1x2 - x1z2, x1y2-y1x2)
        return new Vector(
                xyz.d2 * op.xyz.d3 - xyz.d3 * op.xyz.d2,
                xyz.d3 * op.xyz.d1 - xyz.d1 * op.xyz.d3,
                xyz.d1 * op.xyz.d2 - xyz.d2 * op.xyz.d1
        );
    }

    /**
     * Calculates the length of the Vector squared
     * @return The length of the vector squared
     */
    public double lengthSquared() { return dotProduct(this); }

    /**
     * Calculates the length of the Vector
     * @return The length of the vector
     */
    public double length() { return Math.sqrt(lengthSquared()); }

    /**
     * Calculates the vector with length 1 that points in the same direction of this vector
     * @return The normalized vector
     */
    public Vector normalize() { return scale(1 / length()); }
}
