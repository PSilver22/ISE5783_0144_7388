package primitives;

/**
 * Class for a 3x3 matrix
 */
public class Matrix33 {
    double[][] mat = new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}};

    /**
     * Initializes to identity matrix
     */
    public Matrix33() {}

    /**
     * Initializes matrix to:
     * a b c
     * d e f
     * g h i
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @param g
     * @param h
     * @param i
     */
    public Matrix33(double a, double b, double c,
                    double d, double e, double f,
                    double g, double h, double i) {
        mat[0][0] = a;
        mat[0][1] = b;
        mat[0][2] = c;
        mat[1][0] = d;
        mat[1][1] = e;
        mat[1][2] = f;
        mat[2][0] = g;
        mat[2][1] = h;
        mat[2][2] = i;
    }

    /**
     * Gets the 2x2 minor of the matrix for row, col
     * @param row The minor row
     * @param col The minor column
     * @return An array representing a 2x2 matrix
     */
    private double[] minor(int row, int col) {
        double[] result = new double[]{0, 0, 0, 0};
        int resultIndex = 0;

        for (int i = 0; i < 3; ++i) {
            if (i == row) continue;

            for (int j = 0; j < 3; ++j) {
                if (j == col) continue;
                result[resultIndex++] = mat[i][j];
            }
        }

        return result;
    }

    /**
     * Calculates the determinant of an array representing a 2x2 matrix
     * @param mat22
     * @return The result
     */
    private double det22(double[] mat22) {
        return mat22[0] * mat22[3] - mat22[1] * mat22[2];
    }

    /**
     * Calculates the transpose of the matrix
     * @return The transpose of the matrix
     */
    public Matrix33 transpose() {
        Matrix33 result = new Matrix33();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                result.mat[i][j] = mat[j][i];
            }
        }

        return result;
    }

    /**
     * Calculates the cofactor matrix
     * @return The cofactor matrix
     */
    public Matrix33 cofactor() {
        Matrix33 result = new Matrix33();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                double scale = ((i + j) % 2 == 0) ? 1 : -1;
                result.mat[i][j] = scale * det22(minor(i, j));
            }
        }

        return result;
    }

    /**
     * Calculates the adjugate matrix
     * @return The adjugate matrix
     */
    public Matrix33 adjugate() {
        return cofactor().transpose();
    }

    /**
     * Calculates the determinant of the matrix
     * @return The result
     */
    public double det() {
        double result = 0;
        for (int i = 0; i < 3; ++i) {
            double scale = (i % 2 == 0) ? mat[i][0] : -mat[i][0];
            result += scale * det22(minor(i, 0));
        }

        return result;
    }

    /**
     * Scales each number of the matrix
     * @param scalar
     * @return Matrix containing the result
     */
    public Matrix33 scale(double scalar) {
        Matrix33 result = new Matrix33();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                result.mat[i][j] = mat[i][j] * scalar;
            }
        }

        return result;
    }

    /**
     * Calculates the inverse matrix
     * @return The inverse matrix
     */
    public Matrix33 inverse() {
        return adjugate().scale(1 / det());
    }

    /**
     * Multiplies a vector by the matrix
     * @param v
     * @return The result
     */
    public Vector multiply(Vector v) {
        return new Vector(
                new Vector(mat[0][0], mat[0][1], mat[0][2]).dotProduct(v),
                new Vector(mat[1][0], mat[1][1], mat[1][2]).dotProduct(v),
                new Vector(mat[2][0], mat[2][1], mat[2][2]).dotProduct(v));
    }

    /**
     * Performs matrix multiplication
     * @param m
     * @return The result
     */
    public Matrix33 multiply(Matrix33 m) {
        Matrix33 result = new Matrix33();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Vector row = new Vector(mat[i][0], mat[i][1], mat[i][2]);
                Vector column = new Vector(m.mat[0][j], m.mat[1][j], m.mat[2][j]);

                result.mat[i][j] = row.dotProduct(column);
            }
        }

        return result;
    }
}