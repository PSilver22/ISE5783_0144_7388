package scene;

import lighting.AmbientLight;
import lighting.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive data structure representing the full 3D area we are rendering into an image
 */
public class Scene {
    public String name;

    // Color of a ray if it doesn't intersect a Geometry
    public Color background = Color.BLACK;

    // A color applied to all shapes in the scene.
    // Represents the color of the environment of the scene without any lighting.
    public AmbientLight ambientLight = new AmbientLight();

    // The composite of all 3D shapes in the scene
    public Geometries geometries = new Geometries();

    //collection of all light sources in the scene
    public List<LightSource> lights = new LinkedList<LightSource>();

    /**
     * Creates the Scene with all fields set to their default constructors
     * @param name Name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for the AmbientLight of the Scene
     * @param ambLight
     * @return The newly modified Scene
     */
    public Scene setAmbientLight(AmbientLight ambLight)
    {
        ambientLight = ambLight;
        return this;
    }

    /**
     * Setter for the background of the Scene
     * @param color The new Color for the background
     * @return The newly modified Scene
     */
    public Scene setBackground(Color color)
    {
        background = color;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
