package renderer;

import org.xml.sax.SAXException;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Builds a Scene and ImageWriter from a given XML file.
 */
public class XMLRendererBuilder {
    private String filename;
    private Scene scene;
    private RendererHandler handler;

    /**
     * Constructor which initializes the scene and creates the XML handler
     * @param sceneName
     * @param xmlFilename
     */
    public XMLRendererBuilder(String sceneName, String xmlFilename) {
        filename = xmlFilename;
        handler = new RendererHandler(sceneName);
    }

    /**
     * Creates the scene and/or image writer from the XML file
     */
    public void buildRenderer()  {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filename, handler);
        } catch (IOException e) {
            System.out.println("ERROR: File doesn't exist.");
        } catch (SAXException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the scene retrieved from the XML file through the buildRenderer() method
     * @return The scene retrieved from the XML file or null if either no scene is in the file or buildRenderer() is never called.
     */
    public Scene getScene() {
        return handler.getScene();
    }

    /**
     * Gets the ImageWriter retrieved from the XML file through the buildRenderer() method
     * @return The ImageWriter retrieved from the XML file or null if either no ImageWriter is in the file or buildRenderer() is never called.
     */
    public ImageWriter getImageWriter() {
        return handler.getImageWriter();
    }
}
