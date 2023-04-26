package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class representing the camera used by the renderer
 */
public class Camera {
    // Location of the camera in 3D space
    private Point location;

    // Directions for which way the camera is facing
    private Vector to, up, right;

    // The shape and distance from the camera of the view plane
    private double vpWidth = 0, vpHeight = 0, vpDistance = 0;

    /**
     * Creates an instance of a camera
     * @param location Location of the camera in 3D space
     * @param to The direction the lens of the camera is pointing. Must be orthogonal to the up vector.
     * @param up The direction the top of the camera is pointing. Must be orthogonal to the to vector.
     */
    public Camera(Point location, Vector to, Vector up) {
        if (!isZero(to.dotProduct(up)))
            throw new IllegalArgumentException("ERROR: to vector must be orthogonal to up vector in Camera object initialization.");

        this.location = location;
        this.to = to.normalize();
        this.up = up.normalize();
        this.right = to.crossProduct(up).normalize();
    }

    /**
     * Sets the size of the view plane
     * @param width
     * @param height
     * @return The instance of the camera with the view plane shape modified.
     */
    public Camera setVPSize(double width, double height) {
        this.vpWidth = width;
        this.vpHeight = height;

        return this;
    }

    /**
     * Sets the distance of the view plane from the camera "lens"
     * @param distance distance from the camera lens
     * @return The instance of the camera with the view plane distance modified
     */
    public Camera setVPDistance(double distance) {
        this.vpDistance = distance;

        return this;
    }

    /**
     * Creates a ray going from the camera's location towards a specific pixel in the view plane.
     * @param nX Number of columns in the view plane
     * @param nY Number of rows in the view plane
     * @param j The column of the pixel to shoot the ray through
     * @param i The row of the pixel to shoot the ray through
     * @return A ray with its base at the location of the camera and it's direction pointing through the pixel at row i and column j
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //calculate center of view plane
        Point pCenter = location.add(to.scale(vpDistance));
        //calculate pixel height and width
        double Rx = vpWidth/nX;
        double Ry = vpHeight/nY;
        //calculate scalars for distance of pixel center from view plane's center
        double xJ = (j - (nX-1)/2) * Rx;
        double yI = (i - (nY-1)/2) * Ry;
        //calculate center of pixel
        Point pIJ = pCenter;
        if (!isZero(xJ))
        {
            pIJ = pIJ.add(right.scale(xJ));
        }
        if (!isZero(yI))
        {
            pIJ = pIJ.add(up.scale(yI));
        }

        return new Ray(location, (pIJ.subtract(location)).normalize());
    }
}
