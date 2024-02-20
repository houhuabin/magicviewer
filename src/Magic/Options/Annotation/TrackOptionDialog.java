/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TrackOptionDialog.java
 *
 * Created on Feb 25, 2009, 8:07:15 PM
 */

package Magic.Options.Annotation;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.WinMain.MagicFrame;
import Magic.Units.File.Parameter.Log;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import utils.swing.SwingUtil;

/**
 *
 * @author QIJ
 */
public class TrackOptionDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    private MagicFrame trackFrame;
    private DefaultListModel list_model = new DefaultListModel();
    private DefaultListSelectionModel selection_model;

    /** Creates new form TrackOptionDialog */
    public TrackOptionDialog(MagicFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        trackFrame = parent;
        initValue();
        SwingUtil.setLocation(this);
    }

    public void initValue() {
        for (int i = 0; i < trackFrame.dataRep.trackNum; i++) {
            list_model.addElement(trackFrame.dataRep.tracks[i].trackSet.name);
        }
        selection_model = new DefaultListSelectionModel();
        selection_model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        track_list.setSelectionModel(selection_model);
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
     
        jScrollPane1 = new javax.swing.JScrollPane();
        track_list = new javax.swing.JList();
        add_button = new javax.swing.JButton();
        remove_button = new javax.swing.JButton();
        up_button = new javax.swing.JButton();
        down_button = new javax.swing.JButton();
        reload_button = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

      

        track_list.setModel(list_model);
        track_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                track_listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(track_list);

        add_button.setFont(new java.awt.Font("Monospaced", 0, 12));
        add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        add_button.setMargin(new java.awt.Insets(4, 4, 4, 4));
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        remove_button.setFont(new java.awt.Font("Monospaced", 0, 12));
        remove_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minus.png"))); // NOI18N
        remove_button.setMargin(new java.awt.Insets(4, 4, 4, 4));
        remove_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_buttonActionPerformed(evt);
            }
        });

        up_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        up_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/up.png"))); // NOI18N
        up_button.setMargin(new java.awt.Insets(4, 4, 4, 4));
        up_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                up_buttonActionPerformed(evt);
            }
        });

        down_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        down_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/down.png"))); // NOI18N
        down_button.setMargin(new java.awt.Insets(4, 4, 4, 4));
        down_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                down_buttonActionPerformed(evt);
            }
        });

        reload_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        reload_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        reload_button.setMargin(new java.awt.Insets(4, 4, 4, 4));
        reload_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reload_buttonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.CENTER, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(add_button, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(remove_button, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, up_button, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(down_button, 0, 0, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(reload_button, 0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {add_button, down_button,  reload_button, remove_button, up_button}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 281, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(add_button)
                    .add(remove_button)
                    .add(up_button)
                    .add(down_button)
                    .add(reload_button))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        layout.linkSize(new java.awt.Component[] {add_button, down_button, reload_button, remove_button, up_button}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        // TODO add your handling code here:
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File(ForEverStatic.CURRENT_PATH));
        int answer = filechooser.showOpenDialog(this);
        switch (answer) {
            case JFileChooser.APPROVE_OPTION:
                break;
            case JFileChooser.CANCEL_OPTION:
                return;
        }
        File file = filechooser.getSelectedFile();
        if (file == null) {
            return;
        }
        ForEverStatic.CURRENT_PATH = file.getPath();
        //System.out.println("Add: " + file.getPath());
        ArrayList<String>  str= new ArrayList<String>();
        str.add(file.getPath());
        trackFrame.dataRep.addTrackData(str);
        list_model.addElement(trackFrame.dataRep.tracks[trackFrame.dataRep.trackNum - 1].trackSet.name);

        trackFrame.receiveEntriesChanges();
        //System.out.println("Done");
}//GEN-LAST:event_add_buttonActionPerformed

    private void remove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_buttonActionPerformed
        // TODO add your handling code here:
        int index = track_list.getSelectedIndex();
        if (index < 0) {
            return;
        }
        //System.out.println("Remove: " + (index + 1));
        trackFrame.dataRep.removeData(index);
        list_model.removeElementAt(index);


       // trackFrame.annoAssemblyPanel.reflash();

        trackFrame.receiveEntriesChanges();
        //System.out.println("Done");
}//GEN-LAST:event_remove_buttonActionPerformed

    private void up_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_up_buttonActionPerformed
        // TODO add your handling code here:
        int index = track_list.getSelectedIndex();
        if (index < 0 || index == 0) {
            return;
        }
        //System.out.println("Move Up: " + (index + 1));
        trackFrame.dataRep.exchangeData(index, index - 1);
        Object temp = list_model.getElementAt(index);
        list_model.setElementAt(list_model.getElementAt(index - 1), index);
        list_model.setElementAt(temp, index - 1);
        track_list.setSelectedIndex(index - 1);


        trackFrame.annoAssemblyPanel.reflash();

        trackFrame.receiveEntriesChanges();
        //System.out.println("Done");
}//GEN-LAST:event_up_buttonActionPerformed

    private void down_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_down_buttonActionPerformed
        // TODO add your handling code here:
        int index = track_list.getSelectedIndex();
        if (index < 0 || index >= list_model.getSize() - 1) {
            return;
        }
        //System.out.println("Move Down: " + (index + 1));
        trackFrame.dataRep.exchangeData(index, index + 1);
        Object temp = list_model.getElementAt(index);
        list_model.setElementAt(list_model.getElementAt(index + 1), index);
        list_model.setElementAt(temp, index + 1);
        track_list.setSelectedIndex(index + 1);

//        trackFrame.linear_panel.blocks_in_window.clear();
      
        trackFrame.annoAssemblyPanel.reflash();

        trackFrame.receiveEntriesChanges();
        //System.out.println("Done");
}//GEN-LAST:event_down_buttonActionPerformed

    private void reload_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reload_buttonActionPerformed
        // TODO add your handling code here:
        int index = track_list.getSelectedIndex();
        if (index < 0 || index>=trackFrame.dataRep.trackNum) {
            return;
        }
        JFileChooser filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File(ForEverStatic.CURRENT_PATH));
        int answer = filechooser.showOpenDialog(this);
        switch (answer) {
            case JFileChooser.APPROVE_OPTION:
                break;
            case JFileChooser.CANCEL_OPTION:
                return;
        }
        File file = filechooser.getSelectedFile();
        if (file == null) {
            return;
        }

        trackFrame.dataRep.replaceData(index, file.getPath());
        trackFrame.annoAssemblyPanel.annotationPanel.highlight_index=index;
        trackFrame.annoAssemblyPanel.reflash();
}//GEN-LAST:event_reload_buttonActionPerformed

    private void track_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_track_listMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = track_list.getSelectedIndex();
            if (index < 0) {
                return;
            }
            //System.out.println("Set Name: " + (index + 1));
            String s = (String) JOptionPane.showInputDialog(null, "Set Track Name As:", trackFrame.dataRep.tracks[index].trackSet.name);
            if (s == null) {
                return;
            }
            trackFrame.dataRep.tracks[index].trackSet.name = s;
            trackFrame.annoAssemblyPanel.reflash();
            list_model.setElementAt(s, index);
        }
    }//GEN-LAST:event_track_listMouseClicked

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton down_button;
    private javax.swing.JList track_list;
    private javax.swing.JScrollPane jScrollPane1;
  
    private javax.swing.JButton reload_button;
    private javax.swing.JButton remove_button;
    private javax.swing.JButton up_button;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}