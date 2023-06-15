package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.stream.IntStream;

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
    private double vpWidth = 0;
    private double vpHeight = 0;
    private double vpDistance = 0;

    private ImageWriter imageWriter;
    private RayTracer rayTracer;

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

    public Point getLocation() {
        return location;
    }

    public Vector getTo() {
        return to;
    }

    public Vector getUp() {
        return up;
    }

    public Vector getRight() {
        return right;
    }

    public double getVpWidth() {
        return vpWidth;
    }

    public double getVpHeight() {
        return vpHeight;
    }

    public double getVpDistance() {
        return vpDistance;
    }

    protected String getMissingResource() {
        if (location == null) return "location";
        if (to == null) return "to";
        if (up == null) return "up";
        if (right == null) return "right";
        if (imageWriter == null) return "imageWriter";
        if (rayTracer == null) return "rayTracer";

        return null;
    }

    /**
     * Sets the size of the view plane
     * @param width
     * @param height
     * @return The instance of the camera with the view plane shape modified.
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException("view plane size must be positive");
        }
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
        if (distance <= 0)
        {
            throw new IllegalArgumentException("distance to view plane must be positive");
        }
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
        double xJ = (j - (nX-1)/2d) * Rx;
        double yI = -(i - (nY-1)/2d) * Ry;
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

    /**
     * Sets the imageWriter and returns the camera instance for builder design pattern.
     * @param iw
     * @return The instance of the (now modified) camera.
     */
    public Camera setImageWriter(ImageWriter iw) {
        this.imageWriter = iw;

        return this;
    }

    /**
     * Sets the rayTracer and returns the camera instance for builder design pattern.
     * @param rayTracer
     * @return The instance of the (now modified) camera.
     */
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;

        return this;
    }

    private void castRay(int row, int col) {
        Ray currRay = constructRay(imageWriter.getNx(), imageWriter.getNy(), col, row);
        imageWriter.writePixel(col, row, rayTracer.traceRay(currRay));
    }

    /**
     * Finds and sets the color of each pixel in the viewplane.
     */
    public ImageWriter renderImage() {
        if (getMissingResource() != null) {
            throw new MissingResourceException("The camera is object is not fully built.", "Camera", getMissingResource());
        }

        int threadsCount = 12;

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        Pixel.initialize(nY, nX, 1);
        while (threadsCount -- > 0) {
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                    castRay(pixel.row, pixel.col);
                }
            }).start();
        }
        Pixel.waitToFinish();

//        for (int i = 0; i < imageWriter.getNy(); ++i) {
//            for (int j = 0; j < imageWriter.getNx(); ++j) {
//                castRay(i, j);
//                Pixel.pixelDone();
//                Pixel.printPixel();
//            }
//        }

        return imageWriter;
    }

    /**
     * Prints a grid over the image
     * @param interval Squares of the grid will have width and height equal to interval
     * @param color color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) throw new MissingResourceException("The Camera object is not fully built.", "Camera", "imageWriter");

        // Loop through the image pixels and place a grid pixel every interval number of pixels
        for (int row = 0; row < imageWriter.getNy(); ++row) {
            for (int col = 0; col < imageWriter.getNx(); ++col) {
                if (col % interval == 0 || row % interval == 0) {
                    imageWriter.writePixel(col, row, color);
                }
            }
        }
    }

    /**
     * Rotates the camera clockwise relative to the to vector
     * @param degrees Amount to rotate in degrees
     * @return The newly updated camera
     */
    public Camera rotateTo(double degrees) {
        double rads = degrees * (Math.PI / 180);

        Matrix33 camRotation = TransformationFactory.stdToBasis(to, up, right)
                .multiply(TransformationFactory.xRotation(rads))
                .multiply(TransformationFactory.basisToStd(to, up, right));

        up = camRotation.multiply(up);
        right = camRotation.multiply(right);

        return this;
    }

    /**
     * Rotates the camera clockwise relative to the up vector
     * @param degrees Amount to rotate in degrees
     * @return The newly updated camera
     */
    public Camera rotateUp(double degrees) {
        double rads = degrees * (Math.PI / 180);

        Matrix33 camRotation = TransformationFactory.stdToBasis(to, up, right)
                .multiply(TransformationFactory.zRotation(rads))
                .multiply(TransformationFactory.basisToStd(to, up, right));

        to = camRotation.multiply(to);
        up = camRotation.multiply(up);

        return this;
    }

    /**
     * Rotates the camera clockwise relative to the right vector
     * @param degrees Amount to rotate in degrees
     * @return The newly updated camera
     */
    public Camera rotateRight(double degrees) {
        double rads = degrees * (Math.PI / 180);

        Matrix33 camRotation = TransformationFactory.stdToBasis(to, up, right)
                .multiply(TransformationFactory.yRotation(rads))
                .multiply(TransformationFactory.basisToStd(to, up, right));

        to = camRotation.multiply(to);
        right = camRotation.multiply(right);

        return this;
    }

    /**
     * Moves the camera
     * @param amount The distance for the camera to move
     * @param dir A unit vector with the direction the camera should move
     * @return The newly updated camera
     */
    private Camera move(double amount, Vector dir) {
        location = location.add(dir.scale(amount));

        return this;
    }

    /**
     * Move the camera in the to direction
     * @param amount Amount to move the camera
     * @return The newly updated camera
     */
    public Camera moveForward(double amount) {
        return move(amount, to);
    }

    /**
     * Move the camera in the right direction
     * @param amount Amount to move the camera
     * @return The newly updated camera
     */
    public Camera moveRight(double amount) {
        return move(amount, right);
    }

    /**
     * Move the camera in the up direction
     * @param amount Amount to move the camera
     * @return The newly updated camera
     */
    public Camera moveUp(double amount) {
        return move(amount, up);
    }

    /**
     * Writes the current pixels to an image file.
     */
    public void writeToImage() {
        if (imageWriter == null) throw new MissingResourceException("The Camera object is not fully built.", "Camera", "imageWriter");

        imageWriter.writeToImage();
    }
}
