/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui;

import Magic.Units.Color.ColorRep;
import com.jgoodies.looks.plastic.PlasticUtils;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.UIManager;

/**
 *
 * @author lenovo
 */
public class Slider {

    public BufferedImage highImage;
    public BufferedImage darkImage;
    private Color rec = Color.lightGray;
    private Color inner = Color.green;
    //private Color thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight").darker();
    private Color border = Color.red;
    // public Color border = UIManager.getColor("ScrollBar.darkShadow").darker();
    public int totalWidth = 30;
    public int innerWidth = 30;
    public int height = 12;
    public boolean isMouseIn = false;
    public int posionX = 0;//在annopanel 中的偏离左端的长度在滚动条滚动的时候该值不变
    public int innerPosionX = 0;//在annopanel 中的偏离左端的长度在滚动条滚动的时候该值不变
    public RectBorder rectBorder = new RectBorder();

    public Slider(int totalWidth, int innerWidth, int posion, int innerPosionX) {
        this.totalWidth = Math.max(6, totalWidth);
        this.innerWidth = Math.max(2, innerWidth);
        this.posionX =  Math.max(0, posion);
        this.innerPosionX = Math.max(1, innerPosionX);

        getLight();
        getDark();

    }

    private void getLight() {

        this.highImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = highImage.createGraphics();
        Rectangle r = new Rectangle(0, 0, totalWidth, height);
        paintRect(g, r);
        drawBorder(g, 0, 0, totalWidth, height, 3);
    }

    private void paintRect(Graphics2D g, Rectangle thumbBounds) {
  
        g.setColor(rec);
        g.fillRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);

        g.setColor(inner);
      //  System.out.println(innerPosionX+"-------------------------innerPosionX---------------------");
        g.fillRect(innerPosionX, 0, innerWidth, thumbBounds.height - 2);

        g.setColor(border);
        g.drawRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);

    }

    private void getDark() {
        this.darkImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = darkImage.createGraphics();
        Rectangle r = new Rectangle(0, 0, totalWidth, height);
        paintRect(g, r);
        drawBorder(g, 0, 0, totalWidth, height, 3);
    }

    public void drawBorder(Graphics2D g, int x, int y, int width, int height, int len) {
        /* Color temp;
        if (isMouseIn) {
        temp = border;
        } else {
        temp = border.darker();
        }
        g.setColor(temp);
        Rectangle r = new Rectangle(0, 0, width, height);
        PlasticUtils.addLight3DEffekt(g, r, false);*/
    }

    public boolean contains(int x, int y) {
        return rectBorder.contain(x, y);
    }

    public BufferedImage getImage() {
        if (isMouseIn) {
            return highImage;
        } else {

            return darkImage;
        }

    }
}
