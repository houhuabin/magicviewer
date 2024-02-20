/*
 * AlignmentPanel.java
 *
 * Created on November 10, 2008, 10:38 PM
 */
package Magic.WinMain;

import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import Magic.Units.Piece.GeneticUnit.ReadPiece;
import utils.swing.SwingUtil;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.AlnMagicPanel;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import utils.SamViewUtil;

/**
 *
 * @author Huabin Hou
 */
public class AlignmentPanel extends AlnMagicPanel {

    int FONT_SHIFT = 2;
    public int correntXValue = 0;
    public int centerColumn = 0;

    /** Creates new form AlignmentPanel */
    public AlignmentPanel(JFrame a) {
        magicFrame = (MagicFrame) a;


        FontMetrics fm = this.getFontMetrics(Log.instance().alignPara.DNA_FONT);
        Log.instance().alignPara.baseWidth = fm.charWidth('A');
        Log.instance().alignPara.baseHeight = fm.getAscent();

        initComponents();
        partCenter = new AlignmentPartCenter(this);
        parts.add(partCenter);
       // poptip.setNeedTransilate(true);
      //  parts.add(poptip);
        this.addMouseListener(partCenter);
        this.addMouseMotionListener(partCenter);

    }

    public void setScrollBar() {
        SwingUtil.setMaigicPanelScrollImpl(this, vert);
        vert.setUnitIncrement(Log.instance().alignPara.baseHeight);
        // parent.horiz2.setValue(centerColumn*Log.instance().alignPara.DNA_FONT_WIDTH-dx);
    }

    public void setData() {

        seqData = MagicFrame.instance.seqData;
        if (seqData == null) {
            ifPaint = false;
            return;
        }
        tracks = new Track[1];
        tracks[0] = seqData.seqPanelDatas.get(seqPanelIndex).seqEntries;
        ifPaint = true;
        setWidthAndHeight();

     //   System.out.println(SamViewUtil.originToConsesus(MagicFrame.instance.annoAssemblyPanel.selectX,seqData) - seqData.windowStart+"  "+MagicFrame.instance.annoAssemblyPanel.selectX);
        setSelectColumShape(SamViewUtil.originToConsesus(MagicFrame.instance.annoAssemblyPanel.selectX ,seqData)- seqData.windowStart);
    }

    public void setWidthAndHeight() {
        if (tracks == null || tracks[0] == null) {
            return;
        }
        tracks[0].trackSet.trackHeight = (tracks[0].trackSet.maxVertical + 1) * Log.instance().alignPara.baseHeight;

        setMaxHeight(tracks[0].trackSet.trackHeight);
        setMaxWidth(Log.instance().global.alignmentWindowLen * Log.instance().alignPara.baseWidth);
        //   System.out.println(Log.instance().referenceWindow * Log.instance().alignPara.baseWidth+"------------max width-----------------");
        centerColumn = this.getCurrentIndex((dx + this.getWidth()) / 2);
        this.setPreferredSize(new Dimension(getMaxWidth(), getMaxHeight()));
        //    System.out.println(getMaxWidth()+"    ");
        setScrollBar();
    }

    public Object getPieceShape(Track track, Piece piece) {
        int width = (int) ((((ReadPiece) piece.geneticPiece).windowEnd - ((ReadPiece) piece.geneticPiece).windowStart + 1));
        if (width < 1) {
            width = 1;
        }
        int x_site = (int) (((ReadPiece) piece.geneticPiece).windowStart);
        int y_site = 0;


        y_site += track.middleY + (track.trackSet.pieceHeight + track.trackSet.pieceInterval) * piece.viewPiece.vertical + track.trackSet.pieceInterval + 1;

        if (track.trackSet.pieceHeight <= 1) {
            return y_site;
        }
        return new Rectangle(x_site, y_site, width, track.trackSet.pieceHeight);
    }

    @Override
    public void paintComponent(final Graphics g1) {
        //SystemUtil.printCurrentTime("paint start");
        super.paintComponent(g1);

        paintImplement((Graphics2D) g1);
        //SystemUtil.printCurrentTime("paint end");
        // System.out.println("");
    }

    @Override
    public void paintBuffer(Graphics2D g) {

        g.setFont(Log.instance().alignPara.DNA_FONT);
        paintSequences(g);
        updateBuffer = false;
        // g.translate(this.dx, this.dy);
    }

    public void paintSequences(Graphics2D g) {

        if (seqData == null || seqData.seqPanelDatas.size() == 0) {
            return;
        }
        tracks[0].paint(g);
    }

    @Override
    public Piece getFocusedPiece(int x, int y) {
        if (ifPaint == false || x < 0 || seqData == null || seqData.seqPanelDatas.size() == 0 || seqData.seqPanelDatas.get(seqPanelIndex).seqEntries.currentPieces == null) {
            return null;
        }

        int vertical = (y + dy) / Log.instance().alignPara.baseHeight;
        int posion = ((x + dx) / Log.instance().alignPara.baseWidth) + 1;

        for (Piece piece : seqData.seqPanelDatas.get(seqPanelIndex).seqEntries.currentPieces) {

            if (piece.viewPiece.vertical == vertical && ((ReadPiece) piece.geneticPiece).windowStart < posion + 1 && ((ReadPiece) piece.geneticPiece).windowEnd > posion) {
                //  System.out.println(piece + "-------------------" + piece.geneticPiece.id);
                return piece;
            }
        }
        return null;
    }

    public int getCurrentLine(int value) {
        //  //System.out.println(LogBean.getInstance().alignPara.HEIGHT[1]+"   LogBean.getInstance().alignPara.HEIGHT[1]----------------------  ");
        return (value) / Log.instance().alignPara.baseHeight;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vert = new javax.swing.JScrollBar();

        setBackground(new java.awt.Color(246, 244, 236));
        setFont(new java.awt.Font("Times New Roman", 0, 12));
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });

        vert.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                vertAdjustmentValueChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(383, Short.MAX_VALUE)
                .add(vert, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, vert, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    @Override
    public JScrollBar getVertical() {
        return vert;
    }

private void vertAdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_vertAdjustmentValueChanged
    translate(dx, vert.getValue());
}//GEN-LAST:event_vertAdjustmentValueChanged

private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
  int count = evt.getWheelRotation();
      //  System.out.println("mouse wheel moved");
        if (getVertical() == null) {
          //  System.out.println("parent.getVertical()==null");
            return;
        }
        int value = getVertical().getValue();
        value += count * VERT_UNIT;
        if (value < 0) {
            value = 0;
        }
        if (value > getVertical().getMaximum()) {
            value = getVertical().getMaximum();
        }
        getVertical().setValue(value);

    // TODO add your handling code here:
}//GEN-LAST:event_formMouseWheelMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollBar vert;
    // End of variables declaration//GEN-END:variables
}
