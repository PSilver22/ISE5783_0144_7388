package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries = new Geometries();
    public Scene(String name) {
        this.name = name;
    }

    //setter methods.  Return a scene as per builder pattern
    public Scene setAmbientLight(AmbientLight ambLight)
    {
        ambientLight = ambLight;
        return this;
    }

    public Scene setBackground(Color color)
    {
        background = color;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
