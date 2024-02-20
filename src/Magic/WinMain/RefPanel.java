/*
 * AlignmentPanel.java
 *
 * Created on November 10, 2008, 10:38 PM
 */
package Magic.WinMain;

import Magic.Units.IO.SeqData;
import Magic.Units.Gui.Axi;
import Magic.Units.Color.ColorRep;
import Magic.Units.Color.SeqPaint;
import Magic.Units.Gui.Windows.MagicPanel;
import java.awt.image.BufferedImage;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.AlnMagicPanel;
import Magic.Units.Track.Track;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Vector;
import javax.swing.JFrame;
import utils.ReportInfo;
import utils.SystemUtil;

/**
 *
 * @author Huabin Hou
 */
public class RefPanel extends AlnMagicPanel {
    int AXI_HEIGHT = 10;
    int REF_HEIGHT = 10;
    public Axi axi = new Axi(Log.instance().alignPara.AXIS_FONT, getFontMetrics(Log.instance().alignPara.AXIS_FONT), ColorRep.darkgolden);
    private final byte[] lock = new byte[0]; // 特殊的instance变量

    /** Creates new form AlignmentPanel */
    public RefPanel(JFrame a) {
        magicFrame = (MagicFrame) a;
        FontMetrics fm = this.getFontMetrics(Log.instance().alignPara.DNA_FONT);
        Log.instance().alignPara.baseWidth = fm.charWidth('A');
        Log.instance().alignPara.baseHeight = fm.getAscent();
        initComponents();
        setWidthAndHeight();
        // //System.out.println("  height ----------" + this.getHeight());
    }

    public void setData() {

        seqData = MagicFrame.instance.seqData;
        if (seqData == null ||seqData.seqPanelDatas.size()==0) {
            ifPaint = false;
            return;
        }
        ifPaint = true;
        setWidthAndHeight();
        tracks = new Track[1];
        tracks[0] = seqData.seqPanelDatas.get(0).seqEntries;

    }

    public void setWidthAndHeight() {
        AXI_HEIGHT = getFontMetrics(Log.instance().alignPara.AXIS_FONT).getHeight() + 10;
        if (Log.instance().viewMode == Log.instance().global.MethyMode) {
            REF_HEIGHT = Log.instance().alignPara.baseHeight * 2 + 4;
        } else {
            REF_HEIGHT = Log.instance().alignPara.baseHeight;
        }
        setMaxHeight(AXI_HEIGHT + REF_HEIGHT);
        setMaxWidth(Log.instance().global.alignmentWindowLen * Log.instance().alignPara.baseWidth);
        this.setPreferredSize(new Dimension(getMaxWidth(), getMaxHeight()));
        // //System.out.println(MAX_HEIGHT+"---------MAX_HEIGHT-------------------");
    }

    @Override
    public void paintComponent(final Graphics g1) {
       //  SystemUtil.printCurrentTime("paint start");
        super.paintComponent(g1);
        paintImplement((Graphics2D) g1);
        // SystemUtil.printCurrentTime("paint end");
        //  System.out.println("");

    }

    @Override
    public void paintBuffer(Graphics2D g) {   
      
        g.setFont(Log.instance().alignPara.DNA_FONT);
        axi.drawRef(g, 0, getFontMetrics(Log.instance().alignPara.AXIS_FONT).getAscent() + 2, seqData.windowStart - 1, seqData.windowEnd - 1, Log.instance().alignPara.baseWidth, seqData.insertPoint);
        paintSequences(g);
        updateBuffer = false;
    }

    public void paintMethy(Graphics2D g) {
        //  //System.out.println("SELECTED_SNPS " + SELECTED_SNPS.size());
/*        int LogBean.getInstance().alignPara.HEIGHT = LogBean.getInstance().alignPara.HEIGHT[1] + seqData.tracks.trackSet.ROW_HEIGHT;
        int focus = -1;
        Mutation focus_snp = null;
        for (int i = 0; i < SELECTED_SNPS.size(); i++) {
        Mutation snp = SELECTED_SNPS.elementAt(i);
        //  //System.out.print(snp.refer_site + "  #####  ");
        int position = (snp.refer_site - seqData.windowEnd - 1);

        g.setColor(Color.RED);
        g.drawRect(position * LogBean.getInstance().alignPara.DNA_FONT_WIDTH, LogBean.getInstance().alignPara.HEIGHT[0], snp.refer_dna.length() * LogBean.getInstance().alignPara.DNA_FONT_WIDTH, LogBean.getInstance().alignPara.HEIGHT);

        focus = position;
        focus_snp = snp;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 20 / 100.0F);
        g.setComposite(composite);
        g.fillRect(position * LogBean.getInstance().alignPara.DNA_FONT_WIDTH, LogBean.getInstance().alignPara.HEIGHT[0], snp.refer_dna.length() * LogBean.getInstance().alignPara.DNA_FONT_WIDTH, LogBean.getInstance().alignPara.HEIGHT);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 100 / 100.0F);
        g.setComposite(composite);
        //}
        }*/
    }

    public void paintRef(Graphics2D g) {
        synchronized (lock) {


            if (seqData.reference == null) {
                return;
            }
            try {
                for (int i = 0; i < Log.instance().global.alignmentWindowLen; i++) {
                    int seqend = i + 1 + seqData.refSub;
                    if (seqData.reference == null) {
                        return;
                    }
                    if (seqend > seqData.reference.length()) {
                        return;
                    }

                    String c = seqData.reference.substring(i + seqData.refSub, seqend).toUpperCase();
                    SeqPaint.paintRef(c, "none", i * Log.instance().alignPara.baseWidth, AXI_HEIGHT, g);
                }
                g.setColor(Color.white);
            } catch (Exception ex) {
                ReportInfo.reportError("", ex);
            }
        }
    }

    public void paintSequences(Graphics2D g) {

        if (seqData == null || seqData.seqPanelDatas.size() == 0) {
            return;
        }
        paintRef(g);

    }

    public void showReadInfo(int x, int y) {
        magicFrame.poptip.setVisible(false);
    }

    /* public int getCurrentLine(int value) {
    //  //System.out.println(LogBean.getInstance().alignPara.HEIGHT[1]+"   LogBean.getInstance().alignPara.HEIGHT[1]----------------------  ");
    return (value - Log.instance().alignPara.HEIGHT[1]) / Log.instance().alignPara.DNA_FONT_HEIGHT;
    }
     */
    public int getCurrentIndex(int value) {
        return (value) / Log.instance().alignPara.baseWidth;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(246, 244, 236));
        setFont(new java.awt.Font("Times New Roman", 0, 12));
        setMaximumSize(new java.awt.Dimension(32767, 400));
        setPreferredSize(new java.awt.Dimension(400, 40));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
