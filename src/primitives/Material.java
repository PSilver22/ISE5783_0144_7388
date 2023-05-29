package primitives;

/**
 * PDS which dictates how light reflects off a geometry
 */
public class Material {
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    public Double3 kT = Double3.ZERO, kR = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Setter for the reflection factor
     * @param kR
     * @return The newly updated Material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter for the reflection factor
     * @param kR
     * @return The newly updated Material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter for the transparency factor
     * @param kT
     * @return The newly updated Material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for the transparency factor
     * @param kT
     * @return The newly updated Material
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for the diffusion attenuation factor
     * @param kD
     * @return The newly updated Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for the diffusion attenuation factor
     * @param kD
     * @return The newly updated Material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for the specular attenuation factor
     * @param kS
     * @return The newly updated Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for the specular attenuation factor
     * @param kS
     * @return The newly updated Material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for how reflective the material is
     * @param shininess
     * @return The newly updated Material
     */
    public Material setShininess(int shininess) {
        this.nShininess = shininess;
        return this;
    }
}
