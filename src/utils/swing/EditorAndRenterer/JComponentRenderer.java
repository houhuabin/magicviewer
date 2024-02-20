package utils.swing.EditorAndRenterer;


import javax.swing.JTable;

import javax.swing.table.TableCellRenderer;

import java.awt.Component;



public class JComponentRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            return null;
        }
        return (Component) value;

    }
}
