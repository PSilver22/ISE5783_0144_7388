package elements;

import primitives.Color;

/**
 * A light of specific color (and intensity)
 */
public abstract class Light {
    private Color intensity = null;

    /**
     * constructor for a new light of the passed color and intensity
     * @param intensity
     */
    protected Light (Color intensity)
    {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
