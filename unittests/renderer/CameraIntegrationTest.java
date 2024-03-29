package renderer;

import geometries.*;
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
            setVPSize(3,3).setVPDistance(1);
    //pixels per row/column
    int nX = 3;
    int nY = 3;

    String e = "incorrect number of intersections";

    //helper function to construct rays through center of each pixel
    private List<Ray> constructAllRays(Camera cam, int nX, int nY) {
        List<Ray> rays = new LinkedList<>();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                rays.add(cam.constructRay(nX, nY, j, i));
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
        //T1, sphere radius 1 directly ahead at a near distance (2 intersections)
        Sphere sphere = new Sphere(new Point(0,0,-3),1);
        assertEquals(2, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), e);

        //T2 sphere radius 2.5 directly ahead tangent to vp center (18 intersections)
        sphere = new Sphere(new Point(0,0,-2.5),2.5);
        assertEquals(18, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), e);

        //T3, sphere extends behind vp, intersects only middle pixel rays (10 intersections)
        sphere = new Sphere(new Point(0,0,-2),2);
        assertEquals(10, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), e);

        //T4, camera is inside sphere (9 intersections)
        sphere = new Sphere(new Point(0,0,0),4);
        assertEquals(9, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), e);

        //T5, camera is ahead of sphere (0 intersections)
        sphere = new Sphere(new Point(0,0,1),0.25);
        assertEquals(0, sumPixelIntersections(constructAllRays(cam, nX, nY), sphere), e);
        }
    @Test
    public void planeIntegrationTest() {
        //T1: plane orthogonal to camera (9 intersections)
        Plane plane = new Plane(new Point(0,0,-3), new Point(1,0,-3), new Point(0,1,-3));
        assertEquals(9, sumPixelIntersections(constructAllRays(cam, nX, nY), plane), e);

        //T2: steep diagonal plane (9 intersections)
        plane = new Plane(new Point(1,-1,-1), new Point(-1,-1,-1), new Point(0,1,0));
        assertEquals(9, sumPixelIntersections(constructAllRays(cam, nX, nY), plane), e);

        //T3: shallow diagonal plane (6 intersections)
        plane = new Plane(new Point(1,0,-1), new Point(-1,0,-1), new Point(0,1,0));
        assertEquals(6, sumPixelIntersections(constructAllRays(cam, nX, nY), plane), e);
    }

    @Test
    public void triangleIntegrationTest() {
        //T1: small center triangle (1 intersections)
        Triangle triangle = new Triangle(new Point(0.5,-0.5,-1), new Point(-0.5,-0.5,-1), new Point(0, 0.5, -1));
        assertEquals(1, sumPixelIntersections(constructAllRays(cam, nX, nY), triangle), e);

        //T2: narrow upper center triangle (2 intersections)
        triangle = new Triangle(new Point(0.5,-0.5,-1), new Point(-0.5,-0.5,-1), new Point(0, 20, -1));
        assertEquals(2, sumPixelIntersections(constructAllRays(cam, nX, nY), triangle), e);

    }
    }

