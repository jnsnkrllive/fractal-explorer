/***************************************************************************
 *  @author        Karl Jansen
 *  @version       1.1, 03/20/2014
 *
 *  Compilation:   javac FractalPoint.java
 *  Execution:     TODO
 *  Dependencies:  TODO
 *
 *  Description:
 *  TODO
 *
 ***************************************************************************/

import java.awt.Color;

public class FractalPoint {

    private final int x;
    private final int y;
    private final Color color;
    
    public FractalPoint(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * The string representation of a point.
     * @return     a string with the coordinates of this point with the color
     */
     public String toString() {
        return "(" + this.x + ", " + this.y + "): " + this.color.toString();
    }

}