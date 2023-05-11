package primitives;

public class Material {
    public Double3 kD = Double3.ZERO,kS = Double3.ZERO;
    public int nShininess = 0;

    public void setkD(Double3 kD) {
        this.kD = kD;
    }

    public void setkD(double kD) {
        this.kD = new Double3(kD);
    }

    public void setkS(Double3 kS) {
        this.kS = kS;
    }

    public void setkS(double kS) {
        this.kS = new Double3(kS);
    }

    public void setNShininess(int shininess) {
        this.nShininess = shininess;
    }
}
