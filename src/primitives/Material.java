package primitives;

public class Material {
    //attenuation coefficient of diffused and shiny light
    public Double3 kD = Double3.ZERO,kS = Double3.ZERO;
    //shininess of the material
    public int nShininess = 0;


    //Setter methods use builder pattern - return Material

    //Double3 fields have two setters - one using Double3 and using for double
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setShininess(int shininess) {
        this.nShininess = shininess;
        return this;
    }
}
