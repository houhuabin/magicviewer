/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AssemblyPanel2.java
 *
 * Created on Dec 9, 2009, 2:25:52 PM
 */
package Magic.WinMain;

import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.MagicPanel;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author lenovo
 */
public class AlignAssemblyPanel extends MagicPanel {

    public PileupPanel pipleup_panel;
    public AlignmentPanel align_panel;
    public RefPanel ref_panel;
    public ConsolePane consolePane;

    /** Creates new form AssemblyPanel2 */
    public AlignAssemblyPanel(MagicFrame magicFrame) {
        this.magicFrame = magicFrame;
        initComponents();


        this.pipleup_panel = new PileupPanel(magicFrame);
        this.align_panel = new AlignmentPanel(magicFrame);
        this.ref_panel = new RefPanel(magicFrame);

        setAssembly();
        setBarsAmount();

    }

    public void setAssembly() {
        JPanel assembly = new JPanel(new BorderLayout());
        assembly.add(this.pipleup_panel, BorderLayout.NORTH);
        assembly.add(this.ref_panel, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(assembly, BorderLayout.NORTH);
        this.add(this.align_panel, BorderLayout.CENTER);


    }

    public boolean isNull() {
        if (pipleup_panel == null || align_panel == null || ref_panel == null) {
            return true;
        }
        return false;
    }

    public void setSelectShapeByOrign(int orignPosion) {
        align_panel.setShapeByOrign(orignPosion);
    }

    public void reflash() {
        //setWidthAndHeight();
        align_panel.setData();
        pipleup_panel.setData();
        ref_panel.setData();

        pipleup_panel.setUpdateBuffer(true);
        ref_panel.setUpdateBuffer(true);
        align_panel.setUpdateBuffer(true);
        // setWidthAndHeight();
        this.validate();
        privateRepaint();
    }
     public void clearReflash() {
        //setWidthAndHeight();
        align_panel.clear();
       reflash();

    }

    public void setBarsAmount() {
        // SwingUtil.setMaigicPanelScrollImpl(align_panel, horiz1);
        // horiz1.setMaximum(this.viewport.getViewSize().width);
//        ver1.setBlockIncrement(Math.max(panelScale, panelMax));
        // ver1.setVisibleAmount(panelScale);
        //  horiz1.setUnitIncrement(10);
    }

    public void translateX(final int x) {
        dx = x;

        ref_panel.translate(x, ref_panel.dy);
        align_panel.translate(x, align_panel.dy);
        pipleup_panel.translate(x, pipleup_panel.dy);
    }

    public void privateRepaint() {
        ref_panel.repaint();
        align_panel.repaint();
        pipleup_panel.repaint();

    }

    @Override
    public void clearScreen() {
        this.pipleup_panel.seqData = null;
        this.align_panel.seqData = null;
        this.ref_panel.seqData = null;

        this.pipleup_panel.ifPaint = false;
        this.align_panel.ifPaint = false;
        this.ref_panel.ifPaint = false;

        this.pipleup_panel.clearScreen();
        this.align_panel.clearScreen();
        this.ref_panel.clearScreen();

        this.pipleup_panel.repaint();
        this.align_panel.repaint();
        this.ref_panel.repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

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

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited

        magicFrame.poptip.setVisible(false);
        if (align_panel != null) {
            align_panel.partCenter.focusedPiece = null;
        }
        //  System.out.println("---------------assembly mouse exited--------------------");
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseExited

    @Override
    public void paintImplement(Graphics2D g2) {
        pipleup_panel.paintImplement(g2);
        g2.translate(0, pipleup_panel.getHeight());
        ref_panel.paintImplement(g2);
        g2.translate(0, ref_panel.getHeight());
        align_panel.paintImplement(g2);
    }

    public void init() {
    }

    @Override
    public void paintBuffer(Graphics2D g) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }
    //private JScrollPane jScrollPane1 = new JScrollPane();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
