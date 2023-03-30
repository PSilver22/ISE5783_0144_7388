package geometries;


import primitives.Point;
/** Triangle class represents two-dimensional triangle in 3D Cartesian coordinate
 * system
 * @author: Yossi Tyberg
 */
public class Triangle extends Polygon {
    /**
     * Constructor for a triangle - which has 3 points
     * @param p1, p2, p3 the 3 points of the triangle
     */
    public Triangle (Point p1, Point p2, Point p3){
        super(p1,p2,p3);
    }

    //getters,toString,getNormal should be inherited from polygon
}
