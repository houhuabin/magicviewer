package utils.swing.EditorAndRenterer;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;

public class JSpinnerEditor extends DefaultCellEditor implements ItemListener
{
	JSpinner spinner;

	public JSpinnerEditor(JCheckBox checkbox,JSpinner a) 
	{
		super(checkbox);
	}

	public Component getTableCellEditorComponent(JTable table,
		Object value, boolean isSelected, int row, int column) {
		
		////System.out.println(value);
		if(value instanceof JSpinner)
		{
			spinner=(JSpinner)value;
		}
		return spinner;
	}

	public Object getCellEditorValue() 
	{
		////System.out.println("getCellEditorValue");
		return spinner;
	}

	public void itemStateChanged(ItemEvent e) 
	{
		////System.out.println("itemStateChanged");
		super.fireEditingStopped();
	}
}