package distribution;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CircularGridDistribution extends PointDistribution {
    private double radius;
    private int resolution;

    public CircularGridDistribution(double radius, int numPoints) {
        this.radius = radius;
        this.resolution = (int)Math.ceil(Math.sqrt(numPoints));
    }

    @Override
    public List<Point> getDistribution() {
        List<Point> grid = new LinkedList<>();

        double gap = radius / resolution;

        Vector normalUp = up.normalize();
        Vector normalRight = right.normalize();

        Point topLeft = center.add(normalUp.scale(radius / 2)).subtract(normalRight.scale(radius / 2));

        for (int row = 0; row < resolution; ++row) {
            for (int col = 0; col < resolution; ++col) {
                Point currP;

                try {
                    currP = topLeft
                            .add(normalRight.scale(gap * (col + .5)))
                            .add(normalUp.scale(-gap * (row + .5)));
                } catch (IllegalArgumentException e) {
                    currP = Point.ZERO;
                }

                if (currP.distanceSquared(center) < radius * radius) {
                    grid.add(currP);
                }
            }
        }

        return grid;
    }
}
