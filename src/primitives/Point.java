package primitives;

/** Class representing a point in 3D space
 * @author Pinny Silver
 */
public class Point {
    /** x, y, z coordinate tuple */
    Double3 xyz;

    /** Constructor which initializes the point with a 3-tuple
     * @param xyz 3-tuple with x, y, z coordinates
     */
    public Point(Double3 xyz) { this.xyz = xyz; }

    /**
     * Constructor which initializes the point with 3 doubles for each coordinate
     * @param x x-axis offset
     * @param y y-axis offset
     * @param z z-axis offset
     */
    public Point(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Getter for x
     * @return value of x
     */
    public double getX() { return xyz.d1; }

    /**
     * Getter for y
     * @return value of y
     */
    public double getY() { return xyz.d2; }

    /**
     * Getter for z
     * @return value of z
     */
    public double getZ() { return xyz.d3; }

    /**
     * Creates a new Point moved in the direction and magnitude of the operand Vector
     * @param op The Vector operand
     * @return New Point which is the result from the addition
     */
    public Point add(Vector op) {
        return new Point(xyz.add(op.xyz));
    }

    /**
     * Creates a new Point where each coordinate is the subtraction of the respective coordinates of op from this
     * @param op Point to subtract from this
     * @return New Point which is the result from the subtraction
     */
    public Point subtract(Point op) { return new Point(xyz.subtract(op.xyz)); }

    /** Product two Points into a new Point where each couple of
     * numbers is multiplied
     * @param  rhs right handle side operand for product
     * @return     result of product */
    public Point product(Point rhs) {
        return new Point((this.xyz).product(rhs.xyz));
    }
    /**
     * Finds the distance squared between this and op
     * @param op Point to find the distance squared from
     * @return Distance between this and op, squared
     */
    public double distanceSquared(Point op) {
        // (x1 - x0)^2 + (y1 - y0)^2 + (z1 - z0)^2
        Double3 diff = op.xyz.subtract(xyz);
        Double3 prod = diff.product(diff);

        return prod.d1 + prod.d2 + prod.d3;
    }

    /**
     * Finds the distance between this and op
     * @param op Point to find the distance from
     * @return Distance between this and op
     */
    public double distance(Point op) {
        double dSq = distanceSquared(op);
        return Math.sqrt(dSq);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Point p) {
            return xyz.equals(p.xyz);
        }

        return false;
    }

    @Override
    public String toString() { return xyz.toString(); }
}