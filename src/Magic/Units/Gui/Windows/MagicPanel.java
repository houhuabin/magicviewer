/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MagicPanel.java
 *
 * Created on Dec 9, 2009, 7:47:04 PM
 */
package Magic.Units.Gui.Windows;

import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import Magic.WinMain.MagicFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.JScrollBar;

/**
 *
 * @author lenovo
 */
public abstract class MagicPanel extends javax.swing.JPanel {

    public int dx = 0;
    public int dy = 0;
    protected int maxWidth = 0;
    public Shape SELECTED_SHAPE;
    public boolean selectColum = true;
    protected int maxHeight = 0;
    public BufferedImage buffer;
    protected boolean updateBuffer = true;
    protected ArrayList<PartComponent> parts = new ArrayList();
    public boolean ifPaint;
    public Track[] tracks = null;
    public MagicPartCenter partCenter;
    public MagicFrame magicFrame;
    public Point start = new Point(-1, -1);   //for mouse selected start
    public Point end = new Point(-1, -1);     //for mouse selected end
    public Point START = new Point(-1, -1);   //for reordered selected start
    public Point END = new Point(-1, -1);     //for reordered selected end
    public int VERT_UNIT = 10;
//    public PopTip poptip=new PopTip();
  public JScrollBar getVertical() {
        return null;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setUpdateBuffer(boolean updateBuffer) {
        this.updateBuffer = updateBuffer;
    }

    /** Creates new form MagicPanel */
    public MagicPanel() {
        initComponents();
        //  partCenter = new MagicPartCenter(this);
        // parts.add(partCenter);
        // this.addMouseListener(partCenter);
        // this.addMouseMotionListener(partCenter);

    }

    public void translate(int x, int y) {
        dx = x;
        dy = y;
        this.updateBuffer = true;
        repaint();
    }

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
                g.translate(-dx, -dy);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                paintBuffer(g);
                paintSelectArea(g);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        g1.drawImage(this.buffer, 0, 0, null);

        try {
            g1.translate(-this.dx, -this.dy);
            for (PartComponent part : this.parts) {
                if(part!=null)
                part.paint(g1);
            }
            g1.translate(this.dx, this.dy);
        } catch (ConcurrentModificationException e) {
            repaint();
        }

    }

    protected void validateBuffer() {
        int w = this.getWidth();
        int h = this.getHeight();
        if ((this.buffer == null) || (this.buffer.getWidth() != w) || (this.buffer.getHeight() != h)) {
            this.buffer = ((BufferedImage) createImage(w, h));
        }

    }

    public abstract void paintBuffer(Graphics2D g);

    public void clear() {
        SELECTED_SHAPE = null;
    }

    public void paintSelectArea(Graphics2D g) {
        if (SELECTED_SHAPE == null) {
            // //System.out.println("SELECTED_SHAPE null ==================");
            return;
        }
        g.setColor(Color.BLACK);
       // g.draw(SELECTED_SHAPE);
        int percentO;
        if (selectColum == true) {
            g.setColor(Color.red);
            percentO = 15;
        } else {
            g.setColor(Color.red);
            percentO = 15;
        }

        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percentO / 100.0F);
        g.setComposite(composite);
        g.fill(SELECTED_SHAPE);
      //  if (SystemUtil.isWindows()) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
      //  }
        //g.dispose();
    }

    public void reOrderStartEndPoint() {
        //set up row
        if (start.x <= end.x) {
            START.x = start.x;
            END.x = end.x;
        } else {
            START.x = end.x;
            END.x = start.x;
        }
        //set up column
        if (start.y <= end.y) {
            START.y = start.y;
            END.y = end.y;
        } else {
            START.y = end.y;
            END.y = start.y;
        }
    }

    public Piece getFocusedPiece(int x, int y) {
        if (ifPaint == false || x < 0 || tracks == null) {
            return null;
        }

        for (Track track : tracks) {
            if (track.currentPieces == null) {
                continue;
            }
            for (Piece piece : track.currentPieces) {
                if (piece.contains(x, y)) {
                    //   System.out.println("--------contains-----------------");
                    return piece;
                }
                //  System.out.println(piece.viewPiece.x1+"------------------------"+piece.viewPiece.x2);
            }
        }
        return null;
    }

    public void clearScreen() {
        for (PartComponent partComponent : parts) {
            partComponent.clearScreen();
        }

        final Graphics2D g = ((BufferedImage) createImage(WIDTH, HEIGHT)).createGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.dispose();
        //  System.out.println("-----------clearScreen------------");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
