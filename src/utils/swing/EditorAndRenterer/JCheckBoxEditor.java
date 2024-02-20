/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.swing.EditorAndRenterer;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class JCheckBoxEditor extends DefaultCellEditor
        implements ItemListener {

    private JCheckBox button;

    public JCheckBoxEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null) {
            return null;
        }
        button = (JCheckBox) value;
        button.addItemListener(this);
        return (Component) value;
    }

    public Object getCellEditorValue() {
        if(button==null)
        {
           return null;
        }
        button.removeItemListener(this);
        return button;
    }

    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}


