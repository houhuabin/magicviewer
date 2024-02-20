/*
 * FontPanel.java
 *
 * Created on October 16, 2008, 9:04 AM
 */

package Magic.Options.Annotation;
import Magic.WinMain.MagicFrame;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.Parameter.AnnoPara;
import Magic.Units.File.Parameter.Log;
import java.awt.Dimension;
/**
 *
 * @author Huabin Hou
 */
public class ViewModePanel extends javax.swing.JPanel {

    private MagicFrame trackFrame;
    /** Creates new form FontPanel */
    public ViewModePanel(MagicFrame parent) {
        trackFrame=parent;
        initComponents();
        if(trackFrame.dataRep!=null) initValues();
    }

    public void initValues()
    {
        if(Log.instance().annoPara.VIEW_MODE.equals(StringRep.NONOVERLAP)) non_overlap_row_mode_button.setSelected(true);
        else if(Log.instance().annoPara.VIEW_MODE.equals(StringRep.SINGLE_LINE)) single_line_row_mode_button.setSelected(true);
        
        if(Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) color_by_strain_button.setSelected(true);
        else if(Log.instance().annoPara.ARROW_MODE.equals(StringRep.HIDE_ARROW)) color_by_row_button.setSelected(true);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        non_overlap_row_mode_button = new javax.swing.JRadioButton();
        single_line_row_mode_button = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        color_by_strain_button = new javax.swing.JRadioButton();
        color_by_row_button = new javax.swing.JRadioButton();

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "View Mode", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        buttonGroup1.add(non_overlap_row_mode_button);
        non_overlap_row_mode_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        non_overlap_row_mode_button.setText("Nooverlap");
        non_overlap_row_mode_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                non_overlap_row_mode_buttonActionPerformed(evt);
            }
        });

        buttonGroup1.add(single_line_row_mode_button);
        single_line_row_mode_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        single_line_row_mode_button.setText("Single Line");
        single_line_row_mode_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                single_line_row_mode_buttonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(non_overlap_row_mode_button)
                    .add(single_line_row_mode_button))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(non_overlap_row_mode_button)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(single_line_row_mode_button))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Arrow Mode", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        buttonGroup2.add(color_by_strain_button);
        color_by_strain_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        color_by_strain_button.setText("Show Gene Arrow");
        color_by_strain_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_by_strain_buttonActionPerformed(evt);
            }
        });

        buttonGroup2.add(color_by_row_button);
        color_by_row_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        color_by_row_button.setText("Hide Gene Arrow");
        color_by_row_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_by_row_buttonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(color_by_strain_button)
            .add(color_by_row_button)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(color_by_strain_button)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(color_by_row_button))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel6, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void non_overlap_row_mode_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_non_overlap_row_mode_buttonActionPerformed
// TODO add your handling code here:
    trackFrame.receiveViewMode(StringRep.NONOVERLAP);
}//GEN-LAST:event_non_overlap_row_mode_buttonActionPerformed

private void single_line_row_mode_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_single_line_row_mode_buttonActionPerformed
// TODO add your handling code here:
    trackFrame.receiveViewMode(StringRep.SINGLE_LINE);
}//GEN-LAST:event_single_line_row_mode_buttonActionPerformed

private void color_by_strain_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_by_strain_buttonActionPerformed
// TODO add your handling code here:
    trackFrame.receiveArrowMode(StringRep.SHOW_ARROW);
}//GEN-LAST:event_color_by_strain_buttonActionPerformed

private void color_by_row_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_by_row_buttonActionPerformed
// TODO add your handling code here:
    trackFrame.receiveArrowMode(StringRep.HIDE_ARROW);
}//GEN-LAST:event_color_by_row_buttonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton color_by_row_button;
    private javax.swing.JRadioButton color_by_strain_button;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton non_overlap_row_mode_button;
    private javax.swing.JRadioButton single_line_row_mode_button;
    // End of variables declaration//GEN-END:variables

}
