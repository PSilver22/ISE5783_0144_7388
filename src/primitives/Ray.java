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

    public Point getPoint() {
        return p0;
    }

    public Vector getVector() {
        return dir;
    }


    /**
     * calculate a point on a ray given a scalar by which to multiply direction vector
     * @param t scalar for vector multiplication
     */
    public Point getPoint(double t)
    {
        return p0.add(dir.scale(t));
    }

    /**
     * given a list of points, calculate the closest point to the base of the ray
     * throws exception if list is null or empty
     * @param points List of points to find the closest of
     */
    public Point findClosestPoint (List<Point> points)
    {
        if(points == null)
        {
            throw new IllegalArgumentException("points list is null");
        }
        if (points.size() == 0)
        {
            throw new IllegalArgumentException("points list is empty");
        }
        Point closest = points.get(0);
        for (int i = 1; i < points.size(); i++)
        {
            if(points.get(i).distanceSquared(p0) < closest.distanceSquared(p0))
            {
                closest = points.get(i);
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

    /**
     * Finds the point in the list of points which is closest to the head of the ray.
     * @param points List of points to check
     * @return The point from points which is closest to the head of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        Point head = p0.add(dir);

        double minDistance = -1;
        Point minPoint = null;
        for (Point point : points) {
            double distance = head.distance(point);

            if (minDistance > distance || minPoint == null) {
                minDistance = distance;
                minPoint = point;
            }
        }

        return minPoint;
    }

    @Override
    public String toString() {
        return p0.toString() + " -> " + p0.add(dir).toString();
    }
}
