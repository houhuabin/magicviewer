package utils.swing.EditorAndRenterer;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class JButtonEditor extends DefaultCellEditor implements ItemListener
{
	JButton button;

	public JButtonEditor(JCheckBox checkbox,JButton a) 
	{
		super(checkbox);
	}

	public Component getTableCellEditorComponent(JTable table,
		Object value, boolean isSelected, int row, int column) {
		
		////System.out.println(value);
		if(value instanceof JButton)
		{
			button=(JButton)value;
		}
		return button;
	}

	public Object getCellEditorValue() 
	{
		////System.out.println("getCellEditorValue");
		return button;
	}

	public void itemStateChanged(ItemEvent e) 
	{
		////System.out.println("itemStateChanged");
		super.fireEditingStopped();
	}
}