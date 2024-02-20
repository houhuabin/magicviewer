package utils.swing.EditorAndRenterer;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class JComboBoxEditor extends DefaultCellEditor implements ItemListener
{
	JComboBox box;

	public JComboBoxEditor(JCheckBox checkbox,JComboBox a) 
	{
		super(checkbox);
	}

	public Component getTableCellEditorComponent(JTable table,
		Object value, boolean isSelected, int row, int column) {
		
		////System.out.println(value);
		if(value instanceof JComboBox)
		{
			box=(JComboBox)value;
		}
		return box;
	}

	public Object getCellEditorValue() 
	{
		////System.out.println("getCellEditorValue");
		return box;
	}

	public void itemStateChanged(ItemEvent e) 
	{
		////System.out.println("itemStateChanged");
		super.fireEditingStopped();
	}
}