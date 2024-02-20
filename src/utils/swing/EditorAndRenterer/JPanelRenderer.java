package utils.swing.EditorAndRenterer;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JPanelRenderer extends JPanel implements TableCellRenderer 
{
	public JPanelRenderer()
	{
		////System.out.println("JButtonRenderer");
		//this.setText(a);
	}

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column) 
	{
		////System.out.println("getTableCellRendererComponent");
		if(value instanceof JPanel) return (JPanel)value;
		return this;
	}
}
