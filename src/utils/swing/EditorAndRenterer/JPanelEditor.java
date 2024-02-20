package utils.swing.EditorAndRenterer;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

public class JPanelEditor extends DefaultCellEditor
{
	JPanel panel;

	public JPanelEditor(JCheckBox checkbox) 
	{
		super(checkbox);
	}

	public Component getTableCellEditorComponent(JTable table,
		Object value, boolean isSelected, int row, int column) {
		
		////System.out.println(value);
		if(value instanceof JPanel)
		{
			panel=(JPanel)value;
		}
		return panel;
	}

	public Object getCellEditorValue() 
	{
		////System.out.println("getCellEditorValue");
		return panel;
	}
}