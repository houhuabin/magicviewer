/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SelectMainAnnotation.java
 *
 * Created on Nov 15, 2009, 6:46:44 PM
 */
package Magic.Analysis.Filter;

import Magic.Units.Gui.Task.LoadTrackTask;
import Magic.WinMain.MagicFrame;
import Magic.Units.File.FileFormat;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.Track.Track;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import utils.FileUtil;
import utils.ForMagic;

import utils.ReportInfo;
import utils.swing.SwingUtil;

/**
 *
 * @author Huabin Hou
 */
public class FilterAnnotationMain extends javax.swing.JDialog {

    public DefaultListModel model1 = new DefaultListModel();
    MagicFrame parent;

    /** Creates new form SelectMainAnnotation */
    public FilterAnnotationMain(javax.swing.JFrame parent, Track mainAnnoTrack) {
        super(parent, false);
        initComponents();
        this.parent = (MagicFrame) parent;

        Main_annotaion.setText(mainAnnoTrack.trackSet.filename);
        Output.setText(FileUtil.insertSuffix(mainAnnoTrack.trackSet.filename, ".clean"));



        SwingUtil.setLocation(this);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OKButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        Main_annotaion = new javax.swing.JTextField();
        main_add_button = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        Output = new javax.swing.JTextField();
        output_add_button1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Anntation File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Times New Roman", 0, 12));

        Main_annotaion.setFont(new java.awt.Font("Times New Roman", 0, 12));
        Main_annotaion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Main_annotaionActionPerformed(evt);
            }
        });

        main_add_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        main_add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        main_add_button.setMargin(new java.awt.Insets(2, 2, 2, 2));
        main_add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                main_add_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Main_annotaion, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(main_add_button)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(main_add_button)
                    .addComponent(Main_annotaion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Output", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
        jPanel7.setFont(new java.awt.Font("Times New Roman", 0, 12));

        Output.setFont(new java.awt.Font("Times New Roman", 0, 12));
        Output.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutputActionPerformed(evt);
            }
        });

        output_add_button1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        output_add_button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        output_add_button1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        output_add_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                output_add_button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Output, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(output_add_button1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(output_add_button1)
                    .addComponent(Output, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(OKButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(OKButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Main_annotaionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Main_annotaionActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_Main_annotaionActionPerformed

    private void main_add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_main_add_buttonActionPerformed
        // TODO add your handling code here:


        String name = SwingUtil.getOpenFileByChooser(null, null, parent);
        if (name == null) {
            return;
        }

        String fileType = FileFormat.checkFileFormatByContent(name);

        if (FileFormat.isSurportedSNP(fileType)) {
            Main_annotaion.setText(name);
        } else {
            int answerConfirmation = JOptionPane.showConfirmDialog(parent, "Not surported format!", "ERROR", JOptionPane.DEFAULT_OPTION);
            switch (answerConfirmation) {
                case 0:
                case 1:
                    return;
            }

        }


    }//GEN-LAST:event_main_add_buttonActionPerformed
    public ArrayList<String> getVectorFiles() {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < model1.getSize(); i++) {
            names.add(model1.get(i).toString());
        }
        return names;
    }

    public void setVectorFiles(ArrayList<String> name) {
        for (int i = 0; i < name.size(); i++) {
            model1.addElement(name.get(i));
        }
    }
    private void output_add_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_output_add_button1ActionPerformed
        String name = SwingUtil.getOpenFileByChooser("Select output file", null, parent);
        if (name == null) {
            return;
        } else {
            Output.setText(name);
        }

        // TODO add your handling code here:
}//GEN-LAST:event_output_add_button1ActionPerformed

    private void OutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutputActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_OutputActionPerformed

    public boolean check() {

        return SwingUtil.validateJTextNull(this);

    }

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed

        if (!check()) {
            return;
        }

        Track track = ForMagic.getTracksBy(Main_annotaion.getText());
        if (track != null) {
            new FilterAnnotationDialog(parent, track, Output.getText());
            doClose(ForEverStatic.RET_OK);
        } else {
            doClose(ForEverStatic.RET_CANCEL);
        }

    }//GEN-LAST:event_OKButtonActionPerformed
    private void doClose(int retStatus) {
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Main_annotaion;
    private javax.swing.JButton OKButton;
    private javax.swing.JTextField Output;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton main_add_button;
    private javax.swing.JButton output_add_button1;
    // End of variables declaration//GEN-END:variables
}
