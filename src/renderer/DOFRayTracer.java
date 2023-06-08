package renderer;

import distribution.PointDistribution;
import geometries.Plane;
import primitives.*;

import java.util.List;

/**
 * Decorator for a RayTracer which adds the depth of field effect
 */
public class DOFRayTracer implements RayTracer {
    private RayTracer rt;
    private Camera cam;
    private PointDistribution distribution;
    private Plane viewPlane, focalPlane;

    /**
     * Creates a ray tracer with the depth of field effect
     * @param rt Ray tracer to apply the effect to
     * @param cam The main camera
     * @param distribution The distribution used
     * @param focalDistance The distance from the viewplane to the focal point of the camera
     */
    public DOFRayTracer(RayTracer rt, Camera cam, PointDistribution distribution, double focalDistance) {
        this.cam = cam;

        this.rt = rt;

        this.distribution = distribution;
        this.distribution.setDirections(cam.getTo(), cam.getUp(), cam.getRight());

        Point vpPoint = cam.getLocation().add(cam.getTo().scale(cam.getVpDistance()));
        viewPlane = new Plane(vpPoint, cam.getTo());

        // The plane where the focal points lay
        focalPlane = new Plane(vpPoint.add(cam.getTo().scale(focalDistance)), cam.getTo());
    }

    @Override
    public Color traceRay(Ray ray) {
        Point pixelIntersect = viewPlane.findIntersections(ray).get(0);
        Point focalPlaneIntersect = focalPlane.findIntersections(ray).get(0);

        distribution.setCenter(pixelIntersect);

        List<Point> rayBases = distribution.getDistribution();

        Color sum = rt.traceRay(ray);

        for (Point base : rayBases) {
            sum = sum.add(rt.traceRay(new Ray(base, focalPlaneIntersect.subtract(base))));
        }

        return sum.reduce(rayBases.size() + 1);
    }
}
