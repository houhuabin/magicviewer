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
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import utils.ReportInfo;
import utils.swing.EditorAndRenterer.JCheckBoxEditor;
import utils.swing.EditorAndRenterer.JComponentRenderer;

/**
 *
 * @author lenovo
 */
public class FormatTable extends javax.swing.JTable {

    private String[] title = {"name", "select", "example"};
    private PieceField[] formatElements;
    private boolean[] formatSelect;
    private String format;
    private int tableheight = 22;

    public FormatTable(Track track) {
        this.format = track.trackSet.format;
        FileFormat ff = new FileFormat();
        formatElements = ff.getFormatFieldString(format);
      // System.out.println(format+"++++++++++++++++++++++++++++++++++++++++++==");
        this.formatSelect = ff.getFormatSelectArray(format);


        setModel(new javax.swing.table.DefaultTableModel(
                new Object[0][0],
                title) {
            //  Class[] types = new Class[]{
            //   java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            //  };
            // public Class getColumnClass(int columnIndex) {
            //return types[columnIndex];
            //  }
        });

        setColumnSelectionAllowed(true);
        setRowHeight(tableheight);
        getTableHeader().setReorderingAllowed(true);

        getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        DefaultTableModel dm = (DefaultTableModel) getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }


        try {
               ArrayList fieldNameArray = new ArrayList();
            for (int i = 0; i < formatElements.length; i++) {
             //    System.out.println(formatElements[i].name()+"----------formatElements i-------------");
                 //field name 去冗余 比如vcf 格式里 dp 出现了两次
         

                Vector<Object> vector = new Vector<Object>();
                if (formatElements[i]== PieceField.group || formatElements[i]==PieceField.groupTwo) {
                    //  String group = (String) track.items[0].getFieldValueByName(formatElements[i]);
                    HashMap<String, String> hm = (HashMap<String, String>) track.currentPieces[0].getDetailFieldValue(formatElements[i].name());
                    Iterator iter = hm.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry group = (Map.Entry) iter.next();
                        if (!fieldNameArray.contains(group.getKey())) {
                           // System.out.println(group.getKey());
                            fieldNameArray.add(group.getKey());
                        } else {
                           //  System.out.println(group.getKey()+"=================no");
                            continue;
                        }
                        vector = new Vector<Object>();
                        vector.add(group.getKey());
                        if (this.formatSelect == null) {
                            vector.add(new JCheckBox("select", true));
                        } else {
                            vector.add(new JCheckBox("select", formatSelect[i]));
                        }
                        vector.add(group.getValue());


                        dm.addRow(vector);
                    }
                } else {
                    
                    if(formatElements[i].name().equals("ref"))
                    {
                     
                    }
                    vector.add(formatElements[i].name());
                    if (this.formatSelect == null) {
                        vector.add(new JCheckBox("select", true));
                    } else {
                        vector.add(new JCheckBox("select", formatSelect[i]));
                    }
                    vector.add(track.currentPieces[0].getDetailFieldValue(formatElements[i].name()));

                    dm.addRow(vector);

                }

            }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        getColumn(getColumnName(1)).setCellRenderer(new JComponentRenderer());
        getColumn(getColumnName(1)).setCellEditor(new JCheckBoxEditor(new JCheckBox("selected", true)));

    }
}
