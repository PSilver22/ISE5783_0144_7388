package distribution;

import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Interface for creating a list of points which are generated systematically
 */
public abstract class PointDistribution {
    protected Point center;
    protected Vector to, up, right;

    /**
     * Setter for the center of the point distribution
     * @param newCenter
     */
    public void setCenter(Point newCenter) {
        this.center = newCenter;
    }

    /**
     * Setter for the direction vectors of the plane the distribution is on
     * @param to
     * @param up
     * @param right
     */
    public void setDirections(Vector to, Vector up, Vector right) {
        this.to = to.normalize();
        this.up = up.normalize();
        this.right = right.normalize();
    }

    /**
     * Get the list of points
     * @return List of points which are generated systematically
     */
    public abstract List<Point> getDistribution();
}
