package elements;

import primitives.Color;
import primitives.Double3;

/** Class containing the color (and intensity) of an ambient light
 */
public class AmbientLight {
    static final AmbientLight NONE = new AmbientLight(Color.BLACK, new Double3(0, 0, 0));

    private Color intensity;
    public AmbientLight() { intensity = Color.BLACK; }

    /**Constructor, determines color intensity based on input - the original color and the attenuation factor
     * @param color   original color
     * @param attenuation   attenuation factor
     */
    public AmbientLight(Color color, Double3 attenuation) {
        intensity = color.scale(attenuation);
    }

    public Color getIntensity() { return intensity; }
}
