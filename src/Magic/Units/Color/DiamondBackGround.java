/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Color;

import java.awt.Transparency;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DiamondBackGround extends ImageUnit {

    public Color light;
    public Color dark;
    public Color mean;
    public int alpha;

    public enum GradientModel {

        cross, row, column, none;
    }
    public GradientModel currentgradientModel;
    //  public int textX = 0;
    //  public int textY = 0;
    //public Font textFont;
    //  public Color textColor;

    public DiamondBackGround(GradientModel model, Color c, int width, int height, int alpha) {
        super(c, width, height);
        this.width = width;
        this.height = height;
        this.alpha = alpha;
        this.currentgradientModel = model;
        this.light = c.brighter();
        this.dark = c.darker();
    }

    public void setDarkerAndLight(Color lighter, Color darker) {
        this.light = lighter;
        this.dark = darker;
    }

       public void setColor(Color color) {
       this.color =color;
    }

    public void paint() {
        //    //System.out.println(width+"--------height------"+height);
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        //  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //   g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        //  g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);


        if (currentgradientModel == GradientModel.cross) {
            g.setPaint(new GradientPaint(0.0F, 0.0F, light, width, height, dark));
        } else if (currentgradientModel == GradientModel.row) {
            g.setPaint(new GradientPaint(0.0F, 0.0F, light, 0, height, dark));
        } else if (currentgradientModel == GradientModel.column) {
            g.setPaint(new GradientPaint(0.0F, 0.0F, light, width, 0, dark));
        } else if (currentgradientModel == GradientModel.none) {
            // //System.out.println(color);
            g.setColor(color);
        }

        Rectangle2D.Float r = new Rectangle2D.Float(0.0F, 0.0F, width, height);
        g.fill(r);
        if (alpha > 0) {
            g.setPaint(new Color(20, 20, 20, alpha));
            g.fillRect(0, 0, width, height);
        }

        /*  if (text != null) {
        g.setFont(textFont.deriveFont(0, height - 3));
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        g.setColor(textColor);
        textX = (int) (width / 2.0F - (bounds.getWidth() / 2.0D));
        textY = height - fm.getMaxDescent();
        //   g.drawString(text, textX, textY);
        }*/

        g.dispose();
    }

    public BufferedImage getImage() {
        paint();
        return this.image;
    }
}
