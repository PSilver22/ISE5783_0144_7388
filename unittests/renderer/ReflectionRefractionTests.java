package renderer;

import static java.awt.Color.*;

import distribution.CircularGridDistribution;
import distribution.GridDistribution;
import distribution.PointDistribution;
import geometries.Plane;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   private void initScene() {
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
   public void baseNewEffects() {
      Camera camera = new Camera(new Point(0,500, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(300, 300).setVPDistance(1250).rotateUp(-12);

      initScene();

      ImageWriter imageWriter = new ImageWriter("baseNewEffects", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }

   @Test
   public void levelNewEffects() {
      Camera camera = new Camera(new Point(0,500, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(300, 300).setVPDistance(1250);

      initScene();

      camera.moveUp(-500);

      ImageWriter imageWriter = new ImageWriter("levelNewEffects", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }

   @Test
   public void backwardNewEffect() {
      Camera camera = new Camera(new Point(0,500, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(300, 300).setVPDistance(1250);

      initScene();

      camera.moveUp(-100);
      camera.moveForward(6000);
      camera.rotateRight(-180);
      camera.rotateUp(-5);

      ImageWriter imageWriter = new ImageWriter("backwardsNewEffects", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }
}
