/*
 * ReadsColorPanel.java
 *
 * Created on October 16, 2008, 11:06 AM
 */
package Magic.Analysis.Merage;

import utils.swing.ExpandPanel.ExpandPanelAbstract;
import Magic.Analysis.Filter.FieldProperty;
import Magic.Analysis.Filter.Filter;
import Magic.Analysis.Table.FormatTable;
import Magic.Analysis.Table.Operator.LogicType;
import Magic.Analysis.Table.Operator.RelationType;
import Magic.Analysis.Table.OperatorFormatTable;
import Magic.Units.File.FileFormat.PieceField;
import Magic.WinMain.MagicFrame;
import Magic.Units.Track.Track;

import java.awt.BorderLayout;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.JCheckBox;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.ReportInfo;
import utils.swing.ExpandPanel.SubmitPanel;

/**
 *
 * @author Huabin Hou
 */
public class MergeFormatPanel extends JPanel implements ExpandPanelAbstract {

    private MagicFrame trackFrame;
    private String format;
    private javax.swing.JPanel jPanel1;
    public Track track;
    private JTable table;
    private int height = 200;
    private int width = 500;
    private boolean ifMain = false;

    /** Creates new form ReadsColorPanel */
    public MergeFormatPanel(MagicFrame parent, Track track, boolean ifMain) {

        initComponents();
        format = track.trackSet.format;
        this.trackFrame = parent;
        this.ifMain = ifMain;
        this.track = track;

        initPanel();

        if (trackFrame.dataRep != null) {
            initTable();
        }

    }

    public void initPanel() {
        jPanel1 = new javax.swing.JPanel();

        setFont(new java.awt.Font("Times New Roman", 0, 12));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Setting"));
        jPanel1.setLayout(new java.awt.BorderLayout());
         setLayout();

    }

    public void setLayout()
    {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, width, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(layout.createSequentialGroup().add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, height, Short.MAX_VALUE).addContainerGap()));

    }

    public void initTable() {


        javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
        if (ifMain) {
            table = new OperatorFormatTable(track);
        } else {
            table = new FormatTable(track);
        }

        // setFont(new java.awt.Font("Times New Roman", 0, 12));
        jScrollPane4.setFont(new java.awt.Font("Times New Roman", 0, 12));


        jScrollPane4.setViewportView(table);
        jPanel1.add(jScrollPane4);

        this.setSize(this.getWidth(), table.getHeight() + 10);

    }

    public void setMaxHeight()
    {  
        //table.getRowCount 不包括title?

        height=table.getRowHeight()*(table.getRowCount()+2) + 10;
        setHeight(height);

    }
    public void setHeight(int height) {
        this.height = height;
        setLayout();
    }

    public void update(MagicFrame parent) {
        trackFrame = parent;
        initTable();
    }

    public void setFieldsProperty() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                JCheckBox cellValue = (JCheckBox) tableModel.getValueAt(i, 1);
                String fieldName = (String) tableModel.getValueAt(i, 0);

                FieldProperty fieldProperty = track.pieceFieldProperty.get(fieldName);
                if (fieldProperty == null) {
                    fieldProperty = new FieldProperty();
                } else {
                    if (fieldProperty.filter != null) {
                        //continue;
                    }
                }
                if (cellValue.isSelected()) {
                    fieldProperty.select = true;
                }
                if (ifMain) {
                    fieldProperty.filter = new Filter();
                    fieldProperty.filter.logic = (LogicType) tableModel.getValueAt(i, 2);
                    fieldProperty.filter.relation = (RelationType) tableModel.getValueAt(i, 3);
                    fieldProperty.filter.filterValue = (String) tableModel.getValueAt(i, 4);
                    if (fieldProperty.filter.relation == RelationType.none || fieldProperty.filter.logic == LogicType.none) {
                        fieldProperty.filter = null;
                    } else {
                      //  System.out.println(fieldProperty.filter.relation + "----filterValue-----" + fieldProperty.filter.filterValue + "-----" + fieldName);
                    }
                }
                //track.pieceFieldProperty.put(fieldName, fieldProperty);
                track.pieceFieldProperty.put(fieldName, fieldProperty);
            }


        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFont(new java.awt.Font("Times New Roman", 0, 12));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 416, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 234, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public boolean setting() {
        setFieldsProperty();
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
