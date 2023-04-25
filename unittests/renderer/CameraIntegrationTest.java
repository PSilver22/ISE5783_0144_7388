package renderer;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CameraIntegrationTest {
    //set camera location view plane size and distance
    Camera cam = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).
            setVPSize(1,1).setVPDistance(1);
    //pixels per row/column
    int nX = 3;
    int nY = 3;

    //helper function to construct rays through center of each pixel
    private List<Ray> constructAllRays(Camera cam, int nX, int nY) {
        List<Ray> rays = new LinkedList<>();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                rays.add(cam.constructRay(nX, nY, i, j));
            }
        }
        return rays;
    }
    //helper function to find sum of intersections between rays through all pixels and a single shape
    private int sumPixelIntersections(List<Ray> rays, Geometry shape)
    {
        int sum = 0;
        for (Ray ray : rays) {
            try {
                sum += shape.findIntersections(ray).size();
            }
            catch (NullPointerException e)
            {
            }
        }
        return sum;
    }
    @Test
    public void sphereIntegrationTest() {
        Sphere sphere = new Sphere(new Point(1000,0,-3),1);
        assertEquals(0, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), "p");



        }


    }

