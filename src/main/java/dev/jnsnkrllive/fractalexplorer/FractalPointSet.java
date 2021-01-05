package dev.jnsnkrllive.fractalexplorer;

import java.awt.Color;
import java.util.HashSet;

public class FractalPointSet {

    private HashSet<FractalPoint> pts = new HashSet<FractalPoint>();

    // define the frame dimensions for conversion methods
    private int dimHeight;                  // Height dimension
    private int dimWidth;                   // Width dimension

    // define the domain and range of the set
    private double domainMin;               // Domain Minimum
    private double domainMax;               // Domain Maximum
    private double domain;                  // Domain
    private double rangeMin;                // Range Minimum
    private double rangeMax;                // Range Maximum
    private double range;                   // Range

    // define the color scheme
    public static final Color BLACK         = new Color(  0,   0,   0);
    public static final Color CERULEAN      = new Color( 29, 172, 214);
    public static final Color INCHWORM      = new Color(178, 236,  93);
    public static final Color WHITE         = new Color(255, 255, 255);

    /**
     * TODO - Constructor
     * @param dMin      a double representing the minimum of the domain
     * @param dMax      a double representing the maximum of the domain
     * @param rMin      a double representing the minimum of the range
     * @param rMax      a double representing the maximum of the range
     * @param w         an integer representing the pixel width of the frame
     * @param h         an integer representing the pixel height of the frame
     */
    public FractalPointSet(double dMin, double dMax, double rMin, double rMax, int w, int h) {
        // TODO - Verify that dMax > dMin
        if (dMin > dMax) {
            System.out.println("Invalid Domain: Minimum > Maximum");
        }
        // TODO - Verify that rMax > rMin
        if (rMin > rMax) {
            System.out.println("Invalid Range: Minimum > Maximum");
        }
        // Set instance variables
        this.domainMin = dMin;
        this.domainMax = dMax;
        this.domain = dMax - dMin;
        this.rangeMin = rMin;
        this.rangeMax = rMax;
        this.range = rMax - rMin;
        this.dimWidth = w;
        this.dimHeight = h;
    }

    /**
     * TODO (Upper-Left is Origin of Frame)
     * @param x         an integer representing the x-axis pixel location
     * @return          a dobule representing the x-axis coordinate
     */
    private double xToA(int x) {
        return (double) domainMin + x * domain / dimWidth;
    }

    /**
     * TODO (Upper-Left is Origin of Frame)
     * @param y         an integer representing the y-axis pixel location
     * @return          a dobule representing the y-axis coordinate
     */
    private double yToB(int y) {
        return (double) rangeMin + y * range / dimHeight;
    }

    /**
     * TODO
     */
    private HashSet<FractalPoint> julian() {
        // TODO
        return pts;
    }

    /**
     * TODO
     */
    private HashSet<FractalPoint> mandelbrot() {
        int iterMax = 1000;
        int iterHi = iterMax / 1000 * 40;
        int iterLo = iterMax / 1000 * 4;
        int iterMin = 0;
        for (int x = 0; x < dimWidth; x++) {
            for (int y = 0; y < dimHeight; y++) {
                int iter = 0;
                double aO = xToA(x);
                double bO = yToB(y);
                double a = 0;
                double b = 0;
                while (a*a + b*b < 2*2 && iter < iterMax) {
                    double aTemp = a*a - b*b + aO;
                    b = 2*a*b + bO;
                    a = aTemp;
                    iter++;
                }
                Color c;
                int red, green, blue;
                if (iter >= iterMax) {
                    c = BLACK;
                } else if (iter >= iterHi) {
                    c = CERULEAN;
                } else if (iter >= iterLo) {
                    red = (CERULEAN.getRed() - INCHWORM.getRed()) / (iterHi - iterLo) * (iter - iterLo) + INCHWORM.getRed();
                    green = (CERULEAN.getGreen() - INCHWORM.getGreen()) / (iterHi - iterLo) * (iter - iterLo) + INCHWORM.getGreen();
                    blue = (CERULEAN.getBlue() - INCHWORM.getBlue()) / (iterHi - iterLo) * (iter - iterLo) + INCHWORM.getBlue();
                    c = new Color(red, green, blue);
                } else {
                    red = (INCHWORM.getRed() - WHITE.getRed()) / (iterLo - iterMin) * (iter - iterMin) + WHITE.getRed();
                    green = (INCHWORM.getGreen() - WHITE.getGreen()) / (iterLo - iterMin) * (iter - iterMin) + WHITE.getGreen();
                    blue = (INCHWORM.getBlue() - WHITE.getBlue()) / (iterLo - iterMin) * (iter - iterMin) + WHITE.getBlue();
                    c = new Color(red, green, blue);
                }
                FractalPoint fp = new FractalPoint(x, y, c);
                pts.add(fp);
            }
        }
        return pts;
    }

    /**
     * TODO
     * @param type      a string representing the type of fractal points
     */
    public HashSet<FractalPoint> getFractalPoints(String type) {
        switch(FractalType.valueOf(type.toUpperCase())) {
            case MANDELBROT:
                pts = mandelbrot();
                break;
            
            case JULIAN:
                break;
            
            default:
                break;
        }
        return pts;
    }

}
