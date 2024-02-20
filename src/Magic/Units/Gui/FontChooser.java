/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewOkCancelDialog.java
 *
 * Created on Feb 6, 2009, 11:02:14 AM
 */

package Magic.Units.Gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author QIJ
 */
public class FontChooser extends javax.swing.JDialog {
    String [] font_names=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String [] font_styles={"PLAIN", "ITALIC", "BOLD", "BOLD+ITALIC"};
    int[] font_sizes={8, 9, 10, 11, 12, 13 ,14, 15 ,16, 17 ,18, 19 ,20, 22, 24, 26, 28, 36, 48, 72};
    HashMap<String,Integer> string2style=new HashMap<String,Integer>();
    HashMap<Integer,String> style2string=new HashMap<Integer,String>();

    DefaultListModel font_list_model=new DefaultListModel();
    DefaultListModel style_list_model=new DefaultListModel();
    DefaultListModel size_list_model=new DefaultListModel();

    
    Font init_font=new Font("Helvetica", Font.BOLD, 12);
    Font current_font=new Font("Helvetica", Font.BOLD, 12);
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    /** Creates new form NewOkCancelDialog */
    public FontChooser(java.awt.Frame parent, boolean modal, String title, Font font) {
        super(parent, modal);
        initComponents();
        this.setTitle(title);
        init_font=font;

        string2style.put(font_styles[0],Font.PLAIN);
        string2style.put(font_styles[1],Font.ITALIC);
        string2style.put(font_styles[2],Font.BOLD);
        string2style.put(font_styles[3],Font.BOLD+Font.ITALIC);

        style2string.put(Font.PLAIN,font_styles[0]);
        style2string.put(Font.ITALIC,font_styles[1]);
        style2string.put(Font.BOLD,font_styles[2]);
        style2string.put(Font.BOLD+Font.ITALIC,font_styles[3]);

        initValues();
        this.setVisible(true);
    }

    public void initValues(){
        font_list_model=(DefaultListModel)font_list.getModel();
        for(int i=0;i<font_names.length;i++) font_list_model.addElement(font_names[i]);
        //font_list.setModel(font_list_model);

        style_list_model=(DefaultListModel)style_list.getModel();
        for(int i=0;i<font_styles.length;i++) style_list_model.addElement(font_styles[i]);        

        size_list_model=(DefaultListModel)size_list.getModel();
        for(int i=0;i<font_sizes.length;i++) size_list_model.addElement(font_sizes[i]);

        font_field.setText(init_font.getFamily());
        style_field.setText(style2string.get(init_font.getStyle()));
        size_field.setText(""+init_font.getSize());

        display_label.setText(init_font.getFamily());
        display_label.setFont(init_font);
    }
    
    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    public Font getFont(){
        return current_font;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        font_field = new javax.swing.JTextField();
        style_field = new javax.swing.JTextField();
        size_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        font_list = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        style_list = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        size_list = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        display_label = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setFont(new java.awt.Font("Times New Roman", 0, 12));
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Times New Roman", 0, 12));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jLabel1.setText("Font");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jLabel2.setText("Style");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jLabel3.setText("Size");

        font_field.setEditable(false);
        font_field.setFont(new java.awt.Font("Times New Roman", 0, 12));

        style_field.setEditable(false);
        style_field.setFont(new java.awt.Font("Times New Roman", 0, 12));

        size_field.setEditable(false);
        size_field.setFont(new java.awt.Font("Times New Roman", 0, 12));

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 12));

        font_list.setFont(new java.awt.Font("Times New Roman", 0, 12));
        font_list.setModel(font_list_model);
        font_list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                font_listValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(font_list);

        jScrollPane2.setFont(new java.awt.Font("Times New Roman", 0, 12));

        style_list.setFont(new java.awt.Font("Times New Roman", 0, 12));
        style_list.setModel(style_list_model);
        style_list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                style_listValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(style_list);

        jScrollPane3.setFont(new java.awt.Font("Times New Roman", 0, 12));

        size_list.setFont(new java.awt.Font("Times New Roman", 0, 12));
        size_list.setModel(size_list_model);
        size_list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                size_listValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(size_list);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));
        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 12));

        display_label.setBackground(new java.awt.Color(255, 255, 255));
        display_label.setFont(new java.awt.Font("Times New Roman", 0, 12));
        display_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        display_label.setOpaque(true);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(display_label, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 306, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(display_label, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(font_field, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .add(jScrollPane1, 0, 0, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(style_field, 0, 0, Short.MAX_VALUE)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(size_field, 0, 0, Short.MAX_VALUE)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(okButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(cancelButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabel1)
                            .add(jLabel2)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(font_field, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(style_field, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(size_field, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(layout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {font_field, size_field, style_field}, org.jdesktop.layout.GroupLayout.VERTICAL);

        layout.linkSize(new java.awt.Component[] {jScrollPane1, jScrollPane2, jScrollPane3}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void font_listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_font_listValueChanged
        // TODO add your handling code here:
        font_field.setText((String)font_list.getSelectedValue());
        current_font=new Font((String)font_list.getSelectedValue(),current_font.getStyle(),current_font.getSize());

        display_label.setText(current_font.getFamily());
        display_label.setFont(current_font);
    }//GEN-LAST:event_font_listValueChanged

    private void style_listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_style_listValueChanged
        // TODO add your handling code here:
        style_field.setText((String)style_list.getSelectedValue());
        current_font=new Font(current_font.getFamily(),string2style.get((String)style_list.getSelectedValue()),current_font.getSize());

        display_label.setText(current_font.getFamily());
        display_label.setFont(current_font);
    }//GEN-LAST:event_style_listValueChanged

    private void size_listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_size_listValueChanged
        // TODO add your handling code here:
        size_field.setText(""+(Integer)size_list.getSelectedValue());
        current_font=new Font(current_font.getFamily(),current_font.getStyle(),(Integer)size_list.getSelectedValue());

        display_label.setText(current_font.getFamily());
        display_label.setFont(current_font);
    }//GEN-LAST:event_size_listValueChanged

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel display_label;
    private javax.swing.JTextField font_field;
    private javax.swing.JList font_list;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField size_field;
    private javax.swing.JList size_list;
    private javax.swing.JTextField style_field;
    private javax.swing.JList style_list;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
