package primitives;

import java.util.List;

/**
 * Class representing a ray in 3D space, with a starting point and direction of the ray
 * @author Pinny Silver
 */
public class Ray {
    /** Base of the ray */
    final Point p0;

    /** Unit direction vector of the ray */
    final Vector dir;

    /**
     * Constructor for Ray with base at the given base point and direction set to the unit vector of dir
     * @param base Point from which ray begins
     * @param dir Direction the ray is pointing
     */
    public Ray(Point base, Vector dir) {
        this.p0 = base;
        this.dir = dir.normalize();
    }

    /**
     * Getter for the base point
     * @return
     */
    public Point getPoint() {
        return p0;
    }

    /**
     * Getter for the direction vector
     * @return
     */
    public Vector getVector() {
        return dir;
    }


    /**
     * calculate a point on a ray given a scalar by which to multiply direction vector
     * @param t scalar for vector multiplication
     * @return The head of t * Ray.
     */
    public Point getPoint(double t)
    {
        return p0.add(dir.scale(t));
    }

    /**
     * Given a list of points, calculate the closest point to the head of the Ray.
     * @throws NullPointerException If list of points is null
     * @param points List of points to search through
     * @return Point closest to head of the ray or null if the passed list is empty
     */
    public Point findClosestPoint (List<Point> points)
    {
        if (points == null) throw new NullPointerException("The points list passed is null.");

        Point head = p0.add(dir);

        // Search for the point with the shortest distance
        Point closest = null;
        double closestDistance = -1;
        for (Point p : points) {
            double distance = head.distanceSquared(p);

            if (closest == null || closestDistance > distance) {
                closest = p;
                closestDistance = distance;
            }
        }
        return closest;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o instanceof Ray r) {
            return p0.equals(r.p0) && dir.equals(r.dir);
        }

        return false;
    }

    @Override
    public String toString() {
        return p0.toString() + " -> " + p0.add(dir).toString();
    }
}
