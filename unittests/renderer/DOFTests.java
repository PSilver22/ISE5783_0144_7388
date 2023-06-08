package renderer;

import distribution.CircularGridDistribution;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.WHITE;

public class DOFTests {
    private Scene scene = null;
    private Camera camera = new Camera(new Point(0,500, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(300, 300).setVPDistance(1250).rotateUp(-12);
    private int numRays = 16;

    private void initScene() {
        if (scene != null) return;
        scene = new Scene("Test scene");

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                new Plane(new Point(0,-100,0), new Vector(0,1,0)).setEmission(new Color(CYAN).scale(.25))
                        .setMaterial(new Material().setKd(.8).setShininess(10).setKr(1)),
                new Sphere(new Point(0, 0,100), 25).setEmission(new Color(BLUE).scale(1))
                        .setMaterial(new Material().setKs(0.3).setKd(0.3).setShininess(30)),
                new Sphere(new Point(75, 0, 0), 15).setEmission(new Color(BLUE).scale(1))
                        .setMaterial(new Material().setKs(0.3).setKd(0.3).setShininess(30)),
                new Sphere(new Point(-75, 0, 0), 15).setEmission(new Color(BLUE).scale(1))
                        .setMaterial(new Material().setKs(0.3).setKd(0.3).setShininess(30)),
                new Triangle(new Point(-50, 50, 150), new Point(50, 50, 150), new Point(0, 150, 100))
                        .setMaterial(new Material().setKt(.75).setKd(.1).setKs(.3).setShininess(10))
                        .setEmission(new Color(GREEN).scale(.3)),
                new Sphere(new Point(0, 75, 10), 15)
                        .setMaterial(new Material().setKs(0.3).setKd(0.3).setShininess(30))
                        .setEmission(new Color(RED).scale(.5)),
                new Triangle(new Point(-200, 125, -200), new Point (-175, 100, -100), new Point(-225, 100, -100))
                        .setMaterial(new Material().setKd(.1).setKs(.3).setShininess(10))
                        .setEmission(new Color(RED).scale(.5)),
                new Triangle(new Point(200, 125, -200), new Point (225, 100, -100), new Point(175, 100, -100))
                        .setMaterial(new Material().setKd(.1).setKs(.3).setShininess(10))
                        .setEmission(new Color(RED).scale(.5)),
                new Triangle(new Point(-400,-100,-900), new Point(0,-100,-1000), new Point(-200,200,-1000))
                        .setMaterial(new Material().setKd(.1).setKs(.3).setShininess(10).setKr(1)),
                new Triangle(new Point(400,-100,-900), new Point(0,-100,-1000), new Point(200,200,-1000))
                        .setMaterial(new Material().setKd(.1).setKs(.3).setShininess(10).setKr(1)));

        scene.lights.add(new SpotLight(new Color(WHITE).reduce(5), new Point(175, 200, 0), new Vector(-100, -200, 0)));
        scene.lights.add(new SpotLight(new Color(WHITE).reduce(5), new Point(-175, 200, 0), new Vector(100, -200, 0)));
        scene.lights.add(new SpotLight(new Color(WHITE).reduce(3), new Point(0, 300, 300), new Vector(0, -3, -2)));
    }

    @Test
    public void largeFrontDOFTest() {
        initScene();

        ImageWriter imageWriter = new ImageWriter("largeFrontDOF", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new DOFRayTracer(
                        new RayTracerBasic(scene),
                        camera,
                        new CircularGridDistribution(10, numRays),
                        1200
                ))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void smallFrontDOFTest() {
        initScene();

        ImageWriter imageWriter = new ImageWriter("smallFrontDOF", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new DOFRayTracer(
                        new RayTracerBasic(scene),
                        camera,
                        new CircularGridDistribution(100, numRays),
                        1200
                ))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void smallBackDOFTest() {
        initScene();

        ImageWriter imageWriter = new ImageWriter("smallBackDOF", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new DOFRayTracer(
                        new RayTracerBasic(scene),
                        camera,
                        new CircularGridDistribution(100, numRays),
                        3250
                ))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void largeBackDOFTest() {
        initScene();

        ImageWriter imageWriter = new ImageWriter("largeBackDOF", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new DOFRayTracer(
                        new RayTracerBasic(scene),
                        camera,
                        new CircularGridDistribution(25, numRays),
                        3250
                ))
                .renderImage()
                .writeToImage();
    }
}
