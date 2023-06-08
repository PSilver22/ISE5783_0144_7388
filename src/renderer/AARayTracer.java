package renderer;

import distribution.PointDistribution;
import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public class AARayTracer implements RayTracer {
    private RayTracer rt;
    private Camera cam;
    private PointDistribution distribution;
    private Plane viewPlane;

    public AARayTracer(RayTracer rt, Camera cam, PointDistribution pd) {
        this.cam = cam;
        this.rt = rt;

        this.distribution = pd;
        this.distribution.setDirections(cam.getTo(), cam.getUp(), cam.getRight());

        Point vpPoint = cam.getLocation().add(cam.getTo().scale(cam.getVpDistance()));
        viewPlane = new Plane(vpPoint, cam.getTo());
    }

    @Override
    public Color traceRay(Ray ray) {
        Point pixelCenter = viewPlane.findIntersections(ray).get(0);

        distribution.setCenter(pixelCenter);

        List<Point> rayHeads = distribution.getDistribution();

        Color sum = rt.traceRay(ray);

        for (Point head : rayHeads) {
            sum = sum.add(rt.traceRay(new Ray(cam.getLocation(), head.subtract(cam.getLocation()))));
        }

        return sum.reduce(rayHeads.size() + 1);
    }
}
