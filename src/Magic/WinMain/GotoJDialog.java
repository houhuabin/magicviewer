/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GotoJDialog.java
 *
 * Created on 2009-9-17, 8:52:28
 */
package Magic.WinMain;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Huabin Hou
 */
public class GotoJDialog extends javax.swing.JDialog {

    private String selectContig;
    private int posion;

    /** Creates new form GotoJDialog */
    public GotoJDialog(java.awt.Frame parent, boolean modal, List<String> refContigNames, String selectContig) {
        super(parent, modal);


        initComponents();

        for (String cotigName : refContigNames) {
            jComboBoxRef.addItem(cotigName);
            if (cotigName.equals(selectContig)) {
                jComboBoxRef.setSelectedItem(cotigName);
            }
        }

        jTextCoor.requestFocus();

        jTextCoor.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doOK();
                }else if (e.getKeyCode() == KeyEvent.VK_CANCEL) {
                    doCancel();
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextCoor = new javax.swing.JTextField();
        jComboBoxRef = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jTextCoor.setText("0");
        jTextCoor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCoorActionPerformed(evt);
            }
        });

        jComboBoxRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRefActionPerformed(evt);
            }
        });

        jLabel1.setText("reference contig");

        jLabel2.setText("coordinate");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxRef, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextCoor, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextCoor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRefActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxRefActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        doOK();


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    private void doOK() {
        selectContig = (String) jComboBoxRef.getSelectedItem();
        if (jTextCoor.getText().equals("")) {
            returnStatus = ForEverStatic.RET_CANCEL;
        } else {
            posion = Integer.valueOf(jTextCoor.getText().trim()).intValue();
            returnStatus = ForEverStatic.RET_OK;
        }

        setVisible(false);
        dispose();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        returnStatus = ForEverStatic.RET_CANCEL;
        setVisible(false);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed
    private void doCancel() {
        returnStatus = ForEverStatic.RET_CANCEL;
        setVisible(false);
        dispose();
    }
    private void jTextCoorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCoorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCoorActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
        GotoJDialog dialog = new GotoJDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e) {
        System.exit(0);
        }
        });
        dialog.setVisible(true);
        }
        });*/
    }

    public int getPosion() {
        return posion;
    }

    public String getSelectContig() {
        return selectContig;
    }

    public int getReturnStatus() {
        return returnStatus;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBoxRef;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextCoor;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = ForEverStatic.RET_CANCEL;
}
