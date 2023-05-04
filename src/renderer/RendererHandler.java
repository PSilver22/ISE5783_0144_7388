package renderer;

import elements.AmbientLight;
import geometries.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.Queue;

/**
 * XML file handler which will create ImageWriter and Scene objects from an XML file
 */
public class RendererHandler extends DefaultHandler {
    private ImageWriter imageWriter = null;
    private Scene scene = null;
    private String sceneName;

    // Buffer to collect Geometries in order of their hierarchy
    private Queue<Geometries> geometryBuffer;

    // Buffer to collect points used in a polygon
    private Queue<Point> pointBuffer;

    /**
     * Create the parser based on a partially built scene
     * @param sceneName Name of the scene to be created
     */
    public RendererHandler(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * Parses a string to a Color object
     * @param colorStr The string to parse
     * @return Color object from colorStr
     */
    private Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");

        return new Color(
                Double.parseDouble(rgb[0]),
                Double.parseDouble(rgb[1]),
                Double.parseDouble(rgb[2]));
    }

    /**
     * Parse a string into a Point object
     * @param ptStr The string to parse
     * @return Point object from ptStr
     */
    private Point parsePoint(String ptStr) {
        String[] coords = ptStr.split(" ");

        return new Point(
                Double.parseDouble(coords[0]),
                Double.parseDouble(coords[1]),
                Double.parseDouble(coords[2])
        );
    }

    /**
     * Adds a Geometry object to the top Geometries object in the buffer.
     * @param g Geometry object to add
     */
    private void addShape(Geometry g) {
        Geometries curr = geometryBuffer.peek();
        curr.add(g);
    }

    @Override
    public void startDocument() {
        pointBuffer = new LinkedList<>();
        geometryBuffer = new LinkedList<>();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        // Create objects based on the tag type
        switch (qName) {
            // Expected tag format: <image-writer name=... nx=... ny=... />
            case "image-writer":
                imageWriter = new ImageWriter(
                        attr.getValue("name"),
                        Integer.parseUnsignedInt(attr.getValue("nx")),
                        Integer.parseUnsignedInt(attr.getValue("ny")));
                break;

            // Expected tag format: <scene background-color="r g b" />
            case "scene":
                scene = new Scene(sceneName);
                scene.background = parseColor(attr.getValue("background-color"));
                break;

            // Expected tag format: <ambient-light color="r g b" />
            case "ambient-light":
                scene.ambientLight = new AmbientLight(parseColor(attr.getValue("color")), Double3.ONE);
                break;

            // Expected tag format: <geometries> [list of shape tags] </geometries>
            case "geometries":
                geometryBuffer.add(new Geometries());
                break;

            // Expected tag format: <triangle p0="x y z" p1="x y z" p2="x y z" />
            case "triangle":
                addShape(new Triangle(
                        parsePoint(attr.getValue("p0")),
                        parsePoint(attr.getValue("p1")),
                        parsePoint(attr.getValue("p2"))));
                break;

            // Expected tag format: <polygon> [list of point tags] </polygon>
            case "polygon":
                pointBuffer.clear();
                break;

            // Expected tag format: <sphere center="x y z" radius=... />
            case "sphere":
                addShape(new Sphere(
                        parsePoint(attr.getValue("center")),
                        Double.parseDouble(attr.getValue("radius"))
                ));
                break;

            // Expected tag format: <tube p0="x y z" dir="x y z" radius=... />
            case "tube":
                addShape(new Tube(
                        new Ray(
                                parsePoint(attr.getValue("p0")),
                                new Vector(parsePoint(attr.getValue("dir")))
                        ),
                        Double.parseDouble(attr.getValue("radius"))));
                break;

            // Expected tag format: <plane p0="x y z" p1="x y z" p2="x y z" />
            case "plane":
                addShape(new Plane(
                        parsePoint(attr.getValue("p0")),
                        parsePoint(attr.getValue("p1")),
                        parsePoint(attr.getValue("p2"))));
                break;

            // Expected tag format: <cylinder p0="x y z" dir="x y z" radius=... height=... />
            case "cylinder":
                addShape(new Cylinder(
                        new Ray(
                                parsePoint(attr.getValue("p0")),
                                new Vector(parsePoint(attr.getValue("dir")))
                        ),
                        Double.parseDouble(attr.getValue("radius")),
                        Double.parseDouble(attr.getValue("height"))));
                break;

            // Expected tag format: <point x=... y=... z=... />
            case "point":
                pointBuffer.add(new Point(
                        Double.parseDouble(attr.getValue("x")),
                        Double.parseDouble(attr.getValue("y")),
                        Double.parseDouble(attr.getValue("z"))
                ));
                break;

            default:
                throw new SAXException("Element " + qName + " is not a valid tag.");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "geometries":
                // If this is the highest level Geometries object, put it into the scene
                if (geometryBuffer.size() == 1) {
                    scene.geometries = geometryBuffer.poll();
                    break;
                }

                // If this Geometries object is contained within another, add it to that one
                Geometries currG = geometryBuffer.poll();
                geometryBuffer.peek().add(currG);
                break;

            case "polygon":
                // Add the collected points to the polygon
                Polygon p = new Polygon(pointBuffer.toArray(new Point[pointBuffer.size()]));
                pointBuffer.clear();

                addShape(p);
                break;

            // All of these tags get their information from the attribute list and so there is nothing to do on closing
            case "scene":
            case "image-writer":
            case "ambient-light":
            case "triangle":
            case "sphere":
            case "tube":
            case "plane":
            case "cylinder":
            case "point":
                break;

            default:
                throw new SAXException("Element " + qName + " is not a valid tag.");
        }
    }

    /**
     * Getter for the Scene created based on the XML file
     * @return The Scene created or null if no Scene is found in the file
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Getter for the ImageWriter created based on the XML file
     * @return The ImageWriter created or null if no ImageWriter is found in the file
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }
}