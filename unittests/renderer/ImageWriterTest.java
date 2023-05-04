package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        int nX = 800;
        int nY = 500;
        ImageWriter image = new ImageWriter("image", nX, nY);
        Color fillC = new Color(java.awt.Color.BLUE);
        Color lineC = new Color(java.awt.Color.GREEN);
        for (int i = 0; i < nX; i++)
        {
            for (int j = 0; j< nY; j++)
            {
                if ( i%50 == 0 || j%50 == 0)
                {
                    image.writePixel(i,j, lineC);
                }
                else
                {
                    image.writePixel(i,j, fillC);
                }
            }
        }
        image.writeToImage();
    }
}