/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Main;

import Magic.Units.Color.ColorRep;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author QIJ
 */
public class Tag extends Rectangle {

    public Rectangle[] squares;
    public String text;
    public Color foreground;
    public Color background;
    public Color square_color = new java.awt.Color(204, 204, 255);
    public Font font;
    public boolean visible = true;
    public boolean bound_visible = true;
    //public boolean selected=false;
    private final int NUM = 8;
    private final int LEN = 5;
    private int LEFT_MARGIN = 4;
    private int RIGHT_MARGIN = 4;
    private int TOP_MARGIN = 2;
    private int BOTTOM_MARGIN = 2;
    public void setText(String name) {
        text = name;
    }

    public Tag(String t, Point location, Font f, FontMetrics fm) {
        text = t;
        foreground = ColorRep.darkgolden;
        Color c = (Color) UIManager.get("info");
        background = new Color(c.getRed(), c.getGreen(), c.getBlue(), 190);
        background = ColorRep.carrot;
        font = new Font("Helvetica", Font.PLAIN, 16);
     //   System.out.println();
        setBounds(location.x, location.y, fm.stringWidth(text) + LEFT_MARGIN + RIGHT_MARGIN, fm.getHeight() + TOP_MARGIN + BOTTOM_MARGIN);
    }



  

    
}
