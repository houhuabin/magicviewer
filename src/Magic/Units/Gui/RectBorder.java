/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui;

/**
 *
 * @author lenovo
 */
public class RectBorder {

 public   int x1, y1, x2, y2;

    public boolean contain(int x, int y) {
       // System.out.println(x1+"---"+x2+"---"+y1+"---"+y2+"      "+x+"=="+y);
        if (x1 <= x && x2 >= x) {
            if (y1 <= y && y2 >= y) {
                return true;
            }
        }
        return false;
    }
}
