/***************************************************************************
 *  @author        Karl Jansen
 *  @version       1.1, 03/20/2014
 *
 *  Compilation:   javac FractalExplorer.java
 *  Execution:     TODO
 *  Dependencies:  FractalPoint.java, FractalPointSet.java
 *
 *  Description:
 *  TODO
 *
 ***************************************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FractalExplorer extends JPanel implements MouseListener{

    // the current factal points
    private static HashSet<FractalPoint> pts;
    private static FractalPointSet pointSet;

    // define the frame dimensions
    private static final int height         = 640;
    private static final int width          = 640;

    // define the frame's domain and range
    private static double domainMin         = -2.25;
    private static double domainMax         = +0.75;
    private static double domain;
    private static double rangeMin          = -1.50;
    private static double rangeMax          = +1.50;
    private static double range;

    // define the zoom factor
    private static double zoomFactor        =  0.4;

    // define the canvas
    private static BufferedImage bi;
    private static Graphics g;
    private static Graphics2D big2d;
    private static JFrame frame = new JFrame("Fractal Explorer");;

    /**
     * TODO - Constructor
     */
    public FractalExplorer() {
        bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        big2d = bi.createGraphics();
        domain = domainMax - domainMin;
        range = rangeMax - rangeMin;
    }

    /**
     * TODO
     */
    private JFrame getFrame() {
        return this.frame;
    }

    /**
     * TODO (Upper-Left is Origin of Frame)
     * @param x         an integer representing the x-axis pixel location
     * @return          a dobule representing the x-axis coordinate
     */
    private double xToA(int x) {
        return (double) domainMin + x * (domainMax - domainMin) / width;
    }

    /**
     * TODO (Upper-Left is Origin of Frame)
     * @param y         an integer representing the y-axis pixel location
     * @return          a dobule representing the y-axis coordinate
     */
    private double yToB(int y) {
        return (double) rangeMin + y * (rangeMax - rangeMin) / height;
    }

    private void zoom(double a, double b, double z) {
        domainMin = a - z * (domainMax - domainMin) / 2;
        domainMax = a + z * (domainMax - domainMin) / 2;
        rangeMin = b - z * (rangeMax - rangeMin) / 2;
        rangeMax = b + z * (rangeMax - rangeMin) / 2;
    }

    private void zoomIn(double a, double b) {
        zoom(a, b, zoomFactor);
    }

    private void zoomOut(double a, double b) {
        zoom(a, b, 1/zoomFactor);
    }

    /**
     * TODO
     */
    public void setFractalPoints(HashSet<FractalPoint> pts) {
        this.pts = pts;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseButton = e.getButton();
        double a = xToA(e.getX());
        double b = yToB(e.getY());
        if (e.getButton() == 1) {  // "Left" button click >> Zoom In
            zoomIn(a, b);
        }
        if (e.getButton() == 3) {  // "Right" button click >> Zoom Out
            zoomOut(a, b);
        }
        pointSet = new FractalPointSet(domainMin, domainMax, rangeMin, rangeMax, width, height);
        setFractalPoints(pointSet.getFractalPoints("MANDELBROT"));
        paintComponent(big2d);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * TODO
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (FractalPoint p : pts) {
            g2d.setColor(p.getColor());
            g2d.drawLine(p.getX(), p.getY(), p.getX(), p.getY());
        }
    }

    /**
     * TODO
     */
    public void paintComponent(Graphics2D g2d) {
        g2d.drawImage(bi, 0, 0, null); 
        repaint(); 
    }

    /**
     * TODO
     */
    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer();
        //Create and set up the window
        JFrame frame = explorer.getFrame();
        frame.getContentPane().setPreferredSize(new Dimension(explorer.width, explorer.height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        // Add component to the window
        pointSet = new FractalPointSet(explorer.domainMin, explorer.domainMax, explorer.rangeMin, explorer.rangeMax, explorer.width, explorer.height);
        explorer.setFractalPoints(pointSet.getFractalPoints("MANDELBROT"));
        explorer.addMouseListener(explorer);
        frame.add(explorer);
        // Display the window
        frame.setVisible(true);
    }

}