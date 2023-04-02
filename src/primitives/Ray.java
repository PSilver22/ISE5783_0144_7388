package primitives;

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
