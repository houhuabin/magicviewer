/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Gui.Windows;

import Magic.Units.Gui.Windows.MagicPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ConcurrentModificationException;

/**
 *
 * @author lenovo
 */
public class AnnoMagicPanel extends MagicPanel{
  public void paintImplement(Graphics2D g1) {
        if (ifPaint == false || tracks == null) {
            return;
        }
        if (updateBuffer) {
            try {

                validateBuffer();
                Graphics2D g = this.buffer.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
               // g.translate(-dx, -dy);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                paintBuffer(g);
                paintSelectArea(g);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        g1.drawImage(this.buffer, 0, 0, null);

        try {           
            for (PartComponent part : this.parts) {
                part.paint(g1);
            }
            g1.translate(this.dx, this.dy);
        } catch (ConcurrentModificationException e) {
            repaint();
        }

    }

    @Override
    public void paintBuffer(Graphics2D g) {
      //  throw new UnsupportedOperationException("Not supported yet.");
    }
    public void paintSelectArea(Graphics2D g) {
        if (SELECTED_SHAPE == null) {
            // //System.out.println("SELECTED_SHAPE null ==================");
            return;
        }
        g.setColor(Color.cyan);
        g.draw(SELECTED_SHAPE);
        int percentO;
        if (selectColum == true) {
            g.setColor(Color.red);
            percentO = 50;
        } else {
            g.setColor(Color.red);
            percentO = 50;
        }

        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percentO / 100.0F);
        g.setComposite(composite);
        g.fill(SELECTED_SHAPE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        //g.dispose();
    }

}
