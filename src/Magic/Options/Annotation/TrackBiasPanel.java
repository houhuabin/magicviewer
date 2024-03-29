/*
 * ReadsBiasPanel.java
 *
 * Created on October 16, 2008, 11:06 AM
 */
package Magic.Options.Annotation;

import utils.swing.EditorAndRenterer.JSpinnerEditor;
import utils.swing.EditorAndRenterer.JSpinnerRenderer;
import Magic.WinMain.MagicFrame;
import java.util.Vector;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Huabin Hou
 */
public class TrackBiasPanel extends javax.swing.JPanel {

    private MagicFrame trackFrame;

    /** Creates new form ReadsBiasPanel */
    public TrackBiasPanel(MagicFrame parent) {
        trackFrame = parent;
        initComponents();
        if(trackFrame.dataRep!=null) initTable();
    }

    public void initTable() {

        DefaultTableModel dm = (DefaultTableModel) reads_bias_table.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }
                
        for (int i = 0; i < trackFrame.dataRep.trackNum; i++) {
            Vector<Object> vector = new Vector<Object>();
            String s1 = "" + (i + 1);
            String s2 = trackFrame.dataRep.tracks[i].trackSet.name;
            vector.add(s1);
            vector.add(s2);

            SpinnerModel model = new SpinnerNumberModel();
            JSpinner spinner = new JSpinner(model);
            spinner.setValue(trackFrame.dataRep.tracks[i].trackSet.pieceInterval);
            model.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    SpinnerModel model=(SpinnerModel)(evt.getSource());
                    int index=-1;
                    for(int i=0;i<reads_bias_table.getRowCount();i++)
                    {
                        if(model.equals(((JSpinner)reads_bias_table.getValueAt(i,2)).getModel()))
                        {
                            index=i;
                            break;
                        }
                    }
                    //System.out.println(index+" "+model.getValue());
                    if(index>=0) trackFrame.receiveReadsInterval(index,(Integer)model.getValue());
                }
            });
            vector.add(spinner);
            dm.addRow(vector);
        }
        
        reads_bias_table.getColumn(reads_bias_table.getColumnName(2)).setCellRenderer(new JSpinnerRenderer());
        reads_bias_table.getColumn(reads_bias_table.getColumnName(2)).setCellEditor(new JSpinnerEditor(new JCheckBox(), new JSpinner()));

    }

    public void update(MagicFrame parent)
    {
        trackFrame=parent;
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

        jScrollPane5 = new javax.swing.JScrollPane();
        reads_bias_table = new javax.swing.JTable();

        setFont(new java.awt.Font("Times New Roman", 0, 12));
        setPreferredSize(new java.awt.Dimension(271, 110));

        jScrollPane5.setFont(new java.awt.Font("Times New Roman", 0, 12));

        reads_bias_table.setFont(new java.awt.Font("Times New Roman", 0, 12));
        reads_bias_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Row", "Name", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reads_bias_table.setRowHeight(22);
        jScrollPane5.setViewportView(reads_bias_table);
        reads_bias_table.getColumnModel().getColumn(0).setResizable(false);
        reads_bias_table.getColumnModel().getColumn(0).setPreferredWidth(20);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable reads_bias_table;
    // End of variables declaration//GEN-END:variables
}
