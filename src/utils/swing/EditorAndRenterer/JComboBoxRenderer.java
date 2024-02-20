package utils.swing.EditorAndRenterer;

import Magic.Analysis.Table.Operator;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JComboBoxRenderer extends JButton implements TableCellRenderer 
{
	public JComboBoxRenderer()
	{
		////System.out.println("JButtonRenderer");
		//this.setText(a);
	}

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column) 
	{
		
          return  new JComboBox(new Object[]{value});
	}
}
