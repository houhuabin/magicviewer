package utils.swing.EditorAndRenterer;

import java.awt.Component;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JSpinnerRenderer extends JSpinner implements TableCellRenderer 
{
	public JSpinnerRenderer()
	{
		////System.out.println("JButtonRenderer");
		//this.setText(a);
	}

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column) 
	{
		////System.out.println("getTableCellRendererComponent");
		if(value instanceof JSpinner) return (JSpinner)value;
		return this;
	}
}
