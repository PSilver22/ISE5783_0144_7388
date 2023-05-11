package elements;

import primitives.Color;
import primitives.Double3;

/** Class containing the color (and intensity) of an ambient light, inherits from abstract Light
 */
public class AmbientLight extends Light{
    static final AmbientLight NONE = new AmbientLight(Color.BLACK, new Double3(0, 0, 0));

    public AmbientLight() { super(Color.BLACK); }

    /**Constructor, determines color intensity based on input - the original color and the attenuation factor
     * @param color   original color
     * @param attenuation   attenuation factor
     */
    public AmbientLight(Color color, Double3 attenuation) {
        super(color.scale(attenuation));
    }

}
