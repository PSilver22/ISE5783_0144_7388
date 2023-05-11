package primitives;

/**
 * PDS which dictates how light reflects off a geometry
 */
public class Material {
    public Double3 kD = Double3.ZERO,kS = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Setter for the diffusion attenuation factor
     * @param kD
     * @return
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for the diffusion attenuation factor
     * @param kD
     * @return
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for the specular attenuation factor
     * @param kS
     * @return
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for the specular attenuation factor
     * @param kS
     * @return
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for how reflective the material is
     * @param shininess
     * @return
     */
    public Material setShininess(int shininess) {
        this.nShininess = shininess;
        return this;
    }
}
