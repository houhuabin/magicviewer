/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Main;

/**
 *
 * @author qij
 */
public class DoublePoint {
    public double x=0;
    public double y=0;

    public DoublePoint() {
        x=0;
        y=0;
    }

    public DoublePoint(double x1,double y1) {
        x=x1;
        y=y1;
    }

    public DoublePoint(DoublePoint p) {
        x=p.x;
        y=p.y;
    }

    public String toString() {
        return x+","+y;
    }
}
