/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Table;

import Magic.Units.File.FileFormat;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.Track.Track;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import utils.ReportInfo;
import utils.swing.EditorAndRenterer.JCheckBoxEditor;
import utils.swing.EditorAndRenterer.JComboBoxRenderer;
import utils.swing.EditorAndRenterer.JComponentRenderer;

public class OperatorFormatTable extends javax.swing.JTable {

    private String[] title = {"name", "select", "relation", "operator", "value", "example"};
    private PieceField[] formatElements;
    private boolean[] formatSelect;
    private String format;
    private int tableheight = 22;

    public OperatorFormatTable(Track track) {
        this.format = track.trackSet.format;
        FileFormat ff = new FileFormat();
         formatElements = ff.getFormatFieldString(format);
       
        this.formatSelect = ff.getFormatSelectArray(format);


        setModel(new javax.swing.table.DefaultTableModel(
                new Object[0][0],
                title) {
        });

        setColumnSelectionAllowed(true);
        setRowHeight(tableheight);
        getTableHeader().setReorderingAllowed(true);
        getColumn(getColumnName(2)).setWidth(10);
        getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        DefaultTableModel dm = (DefaultTableModel) getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }


        try {
            //field name 去冗余 比如vcf 格式里 dp 出现了两次
            ArrayList fieldNameArray = new ArrayList();
            for (int i = 0; i < formatElements.length; i++) {
                Vector<Object> vector = new Vector<Object>();
                if (formatElements[i]==PieceField.group || formatElements[i]==PieceField.groupTwo) {
                    //  String group = (String) track.items[0].getFieldValueByName(formatElements[i]);

                    PieceField fieldName;
                    if (formatElements[i]==PieceField.group) {
                        fieldName = PieceField.group;
                    } else {
                        fieldName = PieceField.groupTwo;
                    }

                    HashMap<String, String> hm = (HashMap<String, String>) track.currentPieces[0].getDetailFieldValue(fieldName.name());
                    Iterator iter = hm.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry group = (Map.Entry) iter.next();
                        if (!fieldNameArray.contains(group.getKey())) {
                            fieldNameArray.add(group.getKey());
                        } else {
                            continue;
                        }
                        vector = new Vector<Object>();
                        vector.add(group.getKey());
                        
                        vector.add(new JCheckBox("select", formatSelect[i]));
                        track.currentPieces[i].filePiece.setFieldOperator(vector, (String) group.getKey());
                        vector.add(group.getValue());
                        dm.addRow(vector);
                    }
                } else {
                    if (!fieldNameArray.contains(formatElements[i])) {
                        fieldNameArray.add(formatElements[i]);
                    } else {
                        continue;
                    }
                    vector.add(formatElements[i].name());

                    vector.add(new JCheckBox("select", formatSelect[i]));
                    track.currentPieces[i].filePiece.setFieldOperator(vector, formatElements[i].name());
                    vector.add(track.currentPieces[0].getDetailFieldValue(formatElements[i].name()));

                    dm.addRow(vector);

                }

            }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        getColumn(getColumnName(1)).setCellEditor(new JCheckBoxEditor(new JCheckBox("selected", true)));
        getColumn(getColumnName(1)).setCellRenderer(new JComponentRenderer());


        getColumn(getColumnName(2)).setCellEditor(getLogicEditor());
        getColumn(getColumnName(2)).setCellRenderer(new JComboBoxRenderer());

        getColumn(getColumnName(3)).setCellEditor(getRelationEditor());
        getColumn(getColumnName(3)).setCellRenderer(new JComboBoxRenderer());
        //  getColumn(getColumnName(2)).setCellRenderer(new JCheckBoxRenderer());

    }

    public TableCellEditor getLogicEditor() {
        JComboBox operatorJComboBox = new JComboBox();

        for (Operator.LogicType operator : Operator.LogicType.values()) {
            operatorJComboBox.addItem(operator);
        }
        TableCellEditor operatorEditor = new DefaultCellEditor(operatorJComboBox);
        return operatorEditor;

    }

    public TableCellEditor getRelationEditor() {
        JComboBox operatorJComboBox = new JComboBox();

        for (Operator.RelationType operator : Operator.RelationType.values()) {
            operatorJComboBox.addItem(operator);
        }
        TableCellEditor operatorEditor = new DefaultCellEditor(operatorJComboBox);
        return operatorEditor;

    }
}
