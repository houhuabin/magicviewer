/*
 * ReadsColorPanel.java
 *
 * Created on October 16, 2008, 11:06 AM
 */
package Magic.Options.Alignment;


import utils.swing.EditorAndRenterer.JPanelEditor;
import utils.swing.EditorAndRenterer.JPanelRenderer;
import Magic.WinMain.MagicFrame;
import Magic.Units.Color.BaseImage;
import Magic.Units.File.Parameter.Log;;
import Magic.Units.Color.ColorRep;
import java.awt.Color;

import java.lang.reflect.Field;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class BaseColorPanel extends javax.swing.JPanel {

    private MagicFrame trackFrame;
  //  private String[] base = {"A", "T", "C", "G", "N","HightLight","Border"};
     private String[] base = {"A", "T", "C", "G", "N","Border"};

    /** Creates new form ReadsColorPanel */
    public BaseColorPanel(MagicFrame parent) {
        trackFrame = parent;
        initComponents();
        if (trackFrame.dataRep != null) {
            initTable();
        }
    }

    public void initTable() {
        try {
            DefaultTableModel dm = (DefaultTableModel) reads_color_table.getModel();
            while (dm.getRowCount() > 0) {
                dm.removeRow(0);
            }
            final Class cls = Class.forName("Magic.Units.File.Parameter.AlignPara");
            for (int i = 0; i < base.length; i++) {
                Vector<Object> vector = new Vector<Object>();
                vector.add(base[i]);
            
                JPanel panel = new JPanel();
                Color color = null;
                if (base[i].equals("A") || base[i].equals("T") || base[i].equals("C") || base[i].equals("G") || base[i].equals("N")) {
                    Field upfield = cls.getField(base[i].toUpperCase() + "Color");
                    color = (Color) upfield.get(Log.instance().alignPara);
                   //  //System.out.println("------read color-----"+upfield.getName());
                } else  if (base[i].equals("HightLight")){
                   int max=Log.instance().alignPara.HightLight.length;
                    color = Log.instance().alignPara.HightLight[max-1];
                }else
                {
                   color = Log.instance().alignPara.Border;
                }


                panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                panel.setBackground(color);
                panel.addMouseListener(new java.awt.event.MouseAdapter() {

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        JPanel panel = (JPanel) evt.getSource();
                        Color color = JColorChooser.showDialog(panel, "show", panel.getBackground());
                        if (color != null) {
                            panel.setBackground(color);
                            String selectBase = "";
                            JTable table = (JTable) panel.getParent();

                            for (int i = 0; i < table.getRowCount(); i++) {
                                if (table.getValueAt(i, 1).equals(panel)) {
                                    selectBase = (String) table.getValueAt(i, 0);
                                    break;
                                }
                            }

                            if (selectBase.equals("A") || selectBase.equals("T") || selectBase.equals("C") || selectBase.equals("G") || selectBase.equals("N")) {
                                Field upfield = null;
                                try {
                                    upfield = cls.getField(selectBase.toUpperCase() + "Color");
                                    upfield.set(Log.instance().alignPara, color);
                                } catch (Exception ex) {
                                    ReportInfo.reportError("", ex);
                                }

                            } else if (selectBase.equals("HightLight")){
                                Log.instance().alignPara.HightLight = ColorRep.getDarkerArrayColor(color, Log.instance().alignPara.HightLight.length);

                            }else {
                                Log.instance().alignPara.Border = color;

                            }
                            BaseImage.reset();
                            trackFrame.alignAssemblyPanel.reflash();
            
                        }
                    }
                });
                vector.add(panel);
                dm.addRow(vector);
            }

            reads_color_table.getColumn(reads_color_table.getColumnName(1)).setCellRenderer(new JPanelRenderer());
            reads_color_table.getColumn(reads_color_table.getColumnName(1)).setCellEditor(new JPanelEditor(new JCheckBox()));
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public void update(MagicFrame parent) {
        trackFrame = parent;
        initTable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        reads_color_table = new javax.swing.JTable();

        setFont(new java.awt.Font("Times New Roman", 0, 12));

        jScrollPane4.setFont(new java.awt.Font("Times New Roman", 0, 12));

        reads_color_table.setFont(new java.awt.Font("Times New Roman", 0, 12));
        reads_color_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Base", "Color"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reads_color_table.setColumnSelectionAllowed(true);
        reads_color_table.setRowHeight(22);
        reads_color_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(reads_color_table);
        reads_color_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        reads_color_table.getColumnModel().getColumn(0).setResizable(false);
        reads_color_table.getColumnModel().getColumn(0).setPreferredWidth(10);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable reads_color_table;
    // End of variables declaration//GEN-END:variables
}
