package renderer;

import static java.awt.Color.*;

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

   @Test
   public void newEffects() {
      Camera camera = new Camera(new Point(0,0, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(300, 300).setVPDistance(2000);



      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      //camera.rotateRight(1);
      scene.geometries.add(
              new Plane(new Point(0,0,320), new Vector(.1,0,1)).setEmission(new Color(BLACK))
                      .setMaterial(new Material().setKd(.1).setKr(1)),
              /*new Plane(new Point(0,0,-100), new Vector(0,-3,1))
                      .setMaterial(new Material().setKt(1).setKr(1)),*/
              new Plane(new Point(0,-70,600), new Vector(0,1,0)).setEmission(new Color(BLUE).scale(.25))
                      .setMaterial(new Material().setKd(.75)),
              //geometries floating on the right - sphere flanked by triangles with slightly different
              //transparencies intercepting red and yellow spotlights respectively
              new Sphere(new Point(60, 50,730), 25).setEmission(new Color(BLUE).scale(0.3))
                      .setMaterial(new Material().setKs(0.3).setKd(0.3).setShininess(30)),
              new Triangle(new Point(25,70,730), new Point(20, 20,690), new Point(30,20,770))
                      .setMaterial(new Material().setKt(.9).setKd(.1).setKs(.3).setShininess(10)),
              new Triangle(new Point(95,70,730), new Point(100, 20,690), new Point(90,20,770))
                      .setMaterial(new Material().setKt(.7).setKd(.1).setKs(.3).setShininess(10)),
              //geometries by the matroshka spheres
              //outer sphere
              new Sphere(new Point(-90, 0, 1000), 15).setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(30).setKt(0.5)),
              //middle
              new Sphere(new Point(-90, 0, 1000), 10).setEmission(new Color(YELLOW).scale(.6))
                      .setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(30).setKt(0.4)),
              //inner
              new Sphere(new Point(-90, 0, 1000), 5).setEmission(new Color(BLACK).scale(.6))
                      .setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(30)),
              //base triangle
              new Triangle(new Point(-90,-15,960), new Point(-140,-35,1050), new Point(-50,-35,1050))
                      .setMaterial(new Material().setKs(0.5).setKd(0.5))
                      .setEmission(new Color(WHITE).scale(.4)),
              //background (for transparent shadows)
              new Triangle(new Point(-500,-10,970), new Point(-70,500,970), new Point(-70,-100,970))
                      .setMaterial(new Material().setKs(0.5).setKd(0.5).setKr(.4))
                      .setEmission(new Color(BLUE).scale(.3)),
              //back for left background triangle
              new Triangle(new Point(-500,-10,969), new Point(-70,500,969), new Point(-70,-100,969))
                      .setMaterial(new Material())

              );




      scene.lights.add(new DirectionalLight(new Color(10,10,10), new Vector(1,-10,-3)));
      //spotlights for floating geometries
      scene.lights.add(new SpotLight(new Color(YELLOW).scale(2), new Point(440, 50, 690), new Vector(-1, 0, .1)) //
              .setKl(4E-5).setKq(2E-7));
      scene.lights.add(new SpotLight(new Color(RED).scale(3), new Point(-440, 50, 770), new Vector(1, 0, .1)) //
              .setKl(4E-5).setKq(2E-7));
      //spotlight for matroshka sphere
      scene.lights.add(new SpotLight(new Color(400, 400, 400).scale(.6), new Point(-90, -20, 1020), new Vector(0, 1, -1)) //
              .setKl(4E-5).setKq(2E-7));

      /*

      scene.geometries.add(
              new Plane(new Point(0,-70,0), new Vector(0,1,0)).setEmission(new Color(BLUE).scale(.25))
                      .setMaterial((new Material().setKd(.75))),
              new Plane(new Point(0,0,-120), new Vector(0,0.2,1))
                      .setMaterial(new Material().setShininess(40).setKs(1).setKr(0.8))
                      .setEmission(new Color(BLUE).scale(0.1)),
              new Sphere(new Point(0, 50,130), 25).setEmission(new Color(PINK).scale(0.3))
                      .setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(30)),
              new Sphere(new Point(-120, 0, 0), 15).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(30).setKt(0.6))
              /*new Sphere(new Point(60, 50, 200), 30d).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
              new Triangle(new Point(-15,185,310), new Point(15,185,290), new Point(0,210,300)).setEmission(new Color(WHITE))
                      .setMaterial(new Material().setKs(0.8).setKr(1).setKt(0.1)));

      scene.lights.add(new PointLight(new Color(200, 350, 405), new Point(210, 250, 0)));
      scene.lights.add(new PointLight(new Color(200, 350, 405), new Point(-120, 0, 0)));
             /* .setKl(4E-5).setKq(2E-7));
      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
              .setKl(4E-5).setKq(2E-7));*/



      ImageWriter imageWriter = new ImageWriter("newEffects", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }
}
