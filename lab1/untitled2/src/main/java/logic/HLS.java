package logic;

public class HLS {

    private double h; /* The hue. 0 <= h < 360. */
    private double l; /* The lightness. 0 <= s <= 1.*/
    private double s; /* The saturation. 0 <= v <= 1. */

    public HLS(double h, double l, double s) {
        this.h = h;
        this.l = l;
        this.s = s;
    }

    public double getH() {
        return h;
    }

    public double getL() {
        return l;
    }

    public double getS() {
        return s;
    }

    public String toString() {
        return ConvertMethods.toString(this);
    }


}
