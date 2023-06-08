package primitives;

/**
 * Factory for creating transformation matrices
 */
public class TransformationFactory {
    /**
     * Creates a change of basis matrix from the standard basis to the given
     * @param b1
     * @param b2
     * @param b3
     * @return The change of basis matrix
     */
    public static Matrix33 stdToBasis(Vector b1, Vector b2, Vector b3) {
        return new Matrix33(
                b1.getX(), b2.getX(), b3.getX(),
                b1.getY(), b2.getY(), b3.getY(),
                b1.getZ(), b2.getZ(), b3.getZ()
        );
    }

    /**
     * Creates a change of basis matrix from the given basis to the standard
     * @param b1
     * @param b2
     * @param b3
     * @return The change of basis matrix
     */
    public static Matrix33 basisToStd(Vector b1, Vector b2, Vector b3) {
        return stdToBasis(b1, b2, b3).inverse();
    }

    /**
     * Creates a rotation matrix around the x axis
     * @param radians The amount to rotate around the x axis
     * @return The rotation matrix
     */
    public static Matrix33 xRotation(double radians) {
        return new Matrix33(
                1, 0, 0,
                0, Math.cos(radians), -Math.sin(radians),
                0, Math.sin(radians), Math.cos(radians)
        );
    }

    /**
     * Creates a rotation matrix around the y axis
     * @param radians The amount to rotate around the y axis
     * @return The rotation matrix
     */
    public static Matrix33 yRotation(double radians) {
        return new Matrix33(
                Math.cos(radians), 0, -Math.sin(radians),
                0, 1, 0,
                Math.sin(radians), 0, Math.cos(radians)
        );
    }

    /**
     * Creates a rotation matrix around the z axis
     * @param radians The amount to rotate around the z axis
     * @return The rotation matrix
     */
    public static Matrix33 zRotation(double radians) {
        return new Matrix33(
                Math.cos(radians), -Math.sin(radians), 0,
                Math.sin(radians), Math.cos(radians), 0,
                0, 0, 1
        );
    }
}
