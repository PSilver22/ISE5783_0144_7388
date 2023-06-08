package distribution;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a list of points which are organized in a grid
 */
public class GridDistribution extends PointDistribution {
    // Number of points in the x and y axis
    private int sideResolution;
    private double sideSize;

    /**
     * Constructor which will not wait to create the distribution
     * @param sideSize The size of the side of the square the grid is generated within
     * @param numPoints The number of points to generate within the square
     */
    public GridDistribution(double sideSize, int numPoints) {

        this.sideResolution = (int)Math.ceil(Math.sqrt(numPoints));
        this.sideSize = sideSize;
    }

    @Override
    public List<Point> getDistribution() {
        List<Point> grid = new ArrayList<>(sideResolution * sideResolution);

        double gap = sideSize / sideResolution;

        Point topLeft = center.add(up.scale(sideSize / 2)).subtract(right.scale(sideSize / 2));

        for (int row = 0; row < sideResolution; ++row) {
            for (int col = 0; col < sideResolution; ++col) {
                try {
                    grid.add(topLeft
                            .add(right.scale(gap * (col + .5)))
                            .add(up.scale(-gap * (row + .5))));
                } catch (IllegalArgumentException e) {
                    grid.add(Point.ZERO);
                }
            }
        }

        return grid;
    }
}
