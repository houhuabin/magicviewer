/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Color;

/**
 *
 * @author lenovo
 */

import java.awt.Transparency;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
public class StringImage extends ImageUnit {

   
    public String text;    
    public int textX = 0;
    public int textY = 0;
    public Font textFont;
    public Color textColor;

    public StringImage(String text, Font textfont, Color textColor,int width, int height) {
        super(textColor, width, height);
    
        this.text = text;
        this.textFont = textfont;
        this.textColor = textColor;
        this.width = width;
        this.height = height;
           paint();
    }

    public void paint() {
        //    //System.out.println(width+"--------height------"+height);
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

        g.dispose();

        g = image.createGraphics();

        if (text != null) {
            g.setFont(textFont.deriveFont(0, height - 3));
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D bounds = fm.getStringBounds(text, g);
            g.setColor(textColor);
            textX = (int) (width / 2.0F - (bounds.getWidth() / 2.0D));
            textY = height - fm.getMaxDescent();
            g.drawString(text, textX, textY);
        }

        g.dispose();
    }

}
