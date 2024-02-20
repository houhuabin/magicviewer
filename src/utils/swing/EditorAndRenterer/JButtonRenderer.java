package utils.swing.EditorAndRenterer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JButtonRenderer extends JButton implements TableCellRenderer 
{
	public JButtonRenderer()
	{
		////System.out.println("JButtonRenderer");
		//this.setText(a);
	}

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column) 
	{
		////System.out.println("getTableCellRendererComponent");
		if(value instanceof JButton) return (JButton)value;
		return this;
	}
}
