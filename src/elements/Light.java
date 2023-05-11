package elements;

import primitives.Color;

/**
 * A light of specific color (and intensity)
 */
public abstract class Light {
    private Color intensity;

    /**
     * Constructor for a new light of the passed color and intensity
     * @param intensity
     */
    protected Light (Color intensity)
    {
        this.intensity = intensity;
    }

    /**
     * Gets the Color the light imparts on the scene
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
