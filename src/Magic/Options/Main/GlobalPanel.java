/*
 * FontPanel.java
 *
 * Created on October 16, 2008, 9:04 AM
 */
package Magic.Options.Main;

import Magic.Options.Alignment.*;
import Magic.Options.Annotation.*;
import Magic.WinMain.MagicFrame;
import Magic.Units.Color.BaseImage;
import Magic.Units.File.Parameter.Log;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.broadinstitute.sting.utils.pileup.PileupElement;
import utils.FormatCheck;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class GlobalPanel extends javax.swing.JPanel {

    private MagicFrame trackFrame;

    /** Creates new form FontPanel */
    public GlobalPanel(MagicFrame parent) {
        trackFrame = parent;
        initComponents();
        if (trackFrame.dataRep != null) {
            initValues();
        }
    }

    public void initValues() {
//        alignmentWindowLen.setText(String.valueOf(Log.instance().global.alignmentWindowLen));
        if (Log.instance().global.Qualty_SCORE_ADD == 64) {
            jComboQuality.setSelectedItem("Illumina");
        } else {
            jComboQuality.setSelectedItem("Sanger");
        }
        if (Log.instance().alignPara.showPopTip) {
            poptip.setSelected(true);
        }

        SpinnerNumberModel adjustModel=new SpinnerNumberModel(Log.instance().global.adjustWindowSize,1,Integer.MAX_VALUE,Log.instance().global.adjustWindowSize);
        jSpinnerAdjust.setModel(adjustModel);

         SpinnerNumberModel viewModel=new SpinnerNumberModel(Log.instance().global.alignmentWindowLen,800,2400,100);
        jSpinnerView.setModel(viewModel);

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
        jComboQuality = new javax.swing.JComboBox();
        defaultPlatform_label1 = new javax.swing.JLabel();
        poptip = new javax.swing.JCheckBox();
        defaultPlatform_label2 = new javax.swing.JLabel();
        jSpinnerAdjust = new javax.swing.JSpinner();
        defaultPlatform_label3 = new javax.swing.JLabel();
        jSpinnerView = new javax.swing.JSpinner();

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        jComboQuality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Illumina", "Sanger" }));
        jComboQuality.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboQualityItemStateChanged(evt);
            }
        });

        defaultPlatform_label1.setText("Quality Type");

        poptip.setText("Show PopTip");
        poptip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poptipActionPerformed(evt);
            }
        });

        defaultPlatform_label2.setText("Adjust Window Size ");

        jSpinnerAdjust.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerAdjustStateChanged(evt);
            }
        });

        defaultPlatform_label3.setText("View Window Size ");

        jSpinnerView.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerViewStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(poptip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(defaultPlatform_label1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 51, Short.MAX_VALUE)
                        .add(jComboQuality, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(defaultPlatform_label2)
                            .add(defaultPlatform_label3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jSpinnerView, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .add(jSpinnerAdjust, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(defaultPlatform_label1)
                    .add(jComboQuality, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(defaultPlatform_label2)
                    .add(jSpinnerAdjust, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(defaultPlatform_label3)
                    .add(jSpinnerView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 4, Short.MAX_VALUE)
                .add(poptip)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboQualityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboQualityItemStateChanged

        if (jComboQuality.getSelectedItem().equals("Illumina")) {
            Log.instance().global.Qualty_SCORE_ADD = 64;
            PileupElement.QUALITY_SUB = 31;
        } else {
            Log.instance().global.Qualty_SCORE_ADD = 33;
            PileupElement.QUALITY_SUB = 0;
        }
        trackFrame.resetAssembly();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboQualityItemStateChanged

    private void poptipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poptipActionPerformed
        /*  if (poptip.isSelected() == Log.instance().alignPara.showPopTip) {
        } else {*/

        Log.instance().alignPara.showPopTip = poptip.isSelected();
        // }

        // TODO add your handling code here:
}//GEN-LAST:event_poptipActionPerformed

    private void jSpinnerAdjustStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerAdjustStateChanged
        Log.instance().global.adjustWindowSize=(Integer)jSpinnerAdjust.getValue();
        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinnerAdjustStateChanged

    private void jSpinnerViewStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerViewStateChanged
       Log.instance().global.alignmentWindowLen=(Integer)jSpinnerAdjust.getValue();

        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinnerViewStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel defaultPlatform_label1;
    private javax.swing.JLabel defaultPlatform_label2;
    private javax.swing.JLabel defaultPlatform_label3;
    private javax.swing.JComboBox jComboQuality;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSpinner jSpinnerAdjust;
    private javax.swing.JSpinner jSpinnerView;
    private javax.swing.JCheckBox poptip;
    // End of variables declaration//GEN-END:variables
}