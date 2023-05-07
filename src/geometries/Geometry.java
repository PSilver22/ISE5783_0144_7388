package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**Geometry class is used for various geometries in three-dimensional cartesian space
 * @author: Yossi Tyberg
 */ 
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Finds the normal vector from the geometry at a point
     * @param p Point to find the normal from
     */
    public abstract Vector getNormal(Point p);

    /**
     * Getter for the emission color of the geometry
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter for the emission color of the geometry
     * @param newEmission The new value
     * @return The newly updated geometry
     */
    public Geometry setEmission(Color newEmission) {
        emission = newEmission;

        return this;
    }
}
