/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InputDataDialog.java
 *
 * Created on Feb 4, 2009, 10:43:59 PM
 */

package Magic.IO;

import utils.swing.EditorAndRenterer.JButtonEditor;
import utils.swing.EditorAndRenterer.JButtonRenderer;
import utils.swing.EditorAndRenterer.JComboBoxEditor;
import utils.swing.EditorAndRenterer.JComboBoxRenderer;
import Magic.Units.File.FileFormat;
import Magic.Units.Main.SeeEggMethods;
import Magic.Units.IO.ViewerLog;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.StringRep;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.swing.SwingUtil;

/**
 *
 * @author QIJ
 */
public class InputDataDialog extends javax.swing.JDialog implements ActionListener{

    /** A return status code - returned if Cancel button has been pressed */
    public final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    
	private JTable file_table;
    DefaultTableModel dm;
	private Vector<JButton> browse_button;
	private Vector<JButton> remove_button;

    private String reference_file;
    private Vector data;
    private Vector<String> headers;
	
	Vector<String> formats=StringRep.getFileFormats();

    public String CURRENT_PATH = System.getProperty("user.dir");

    

    /** Creates new form InputDataDialog */
    public InputDataDialog(java.awt.Frame parent, boolean modal,Vector d) {
        super(parent, modal);
        data=d;
        initComponents();
        initValues();
        table_pane.getViewport().setBackground(Color.WHITE);
        SwingUtil.setLocation(this);
    }

    public ViewerLog getViewerLog(){
        if(data==null || data.size()==0) return null;
        ViewerLog log=new ViewerLog(data.size());
        log.reference_file=reference_file;
//        log.names=getProgramName();
        
        return log;
    }

    public void initValues(){
        headers=new Vector<String>();
        headers.add("Name");
        headers.add("File");
        headers.add("Change");
        headers.add("Remove");
        headers.add("Format");

        dm=new DefaultTableModel();
		browse_button=new Vector<JButton>();
		remove_button=new Vector<JButton>();
		
		for(int i=0;i<data.size();i++)
		{
			Vector vector=(Vector)data.elementAt(i);
			String name=(String)vector.elementAt(0);
			String file=(String)vector.elementAt(1);

			modifyRow(i,name,file,new JButton("Browse"),new JButton("Remove"),new JComboBox(formats));
		}

		dm.setDataVector(data,headers);
		file_table=new JTable(dm);
        setRender();
        table_pane.setViewportView(file_table);
    }

    public void addRow(String name,String file,JButton b_button,JButton r_button,JComboBox box)
	{
		b_button.addActionListener(this);
		browse_button.add(b_button);
		r_button.addActionListener(this);
		remove_button.add(r_button);

		String format=FileFormat.checkFileFormatByContent(file);
        box.setSelectedItem(format);

		Vector vector=new Vector();
		vector.add(name);
		vector.add(file);
		vector.add(b_button);
		vector.add(r_button);
		vector.add(box);
		data.add(vector);
	}

	public void modifyRow(int index,String name,String file,JButton b_button,JButton r_button,JComboBox box)
	{
		b_button.addActionListener(this);
		browse_button.add(b_button);
		r_button.addActionListener(this);
		remove_button.add(r_button);

		String format=FileFormat.checkFileFormatByContent(file);
        box.setSelectedItem(format);

		Vector vector=new Vector();
		vector.add(name);
		vector.add(file);
		vector.add(b_button);
		vector.add(r_button);
		vector.add(box);
		data.setElementAt(vector,index);
	}

	public void removeRow(int index)
	{
		data.removeElementAt(index);
		browse_button.removeElementAt(index);
		remove_button.removeElementAt(index);
	}

    public JButton getNewButton(String text){
        JButton button=new JButton();
        button.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        button.setText(text);
        button.setMargin(new java.awt.Insets(2, 2, 2, 2));
        return button;
    }

	public void setRender()
	{
        file_table.setBackground(Color.WHITE);        
        file_table.setRowHeight(25);
        
		file_table.getColumn(headers.elementAt(2)).setCellRenderer(new JButtonRenderer());
		file_table.getColumn(headers.elementAt(2)).setCellEditor(new JButtonEditor(new JCheckBox(),new JButton()));
		file_table.getColumn(headers.elementAt(3)).setCellRenderer(new JButtonRenderer());
		file_table.getColumn(headers.elementAt(3)).setCellEditor(new JButtonEditor(new JCheckBox(),new JButton()));
		file_table.getColumn(headers.elementAt(4)).setCellRenderer(new JComboBoxRenderer());
		file_table.getColumn(headers.elementAt(4)).setCellEditor(new JComboBoxEditor(new JCheckBox(),new JComboBox()));

		file_table.getColumn(headers.elementAt(0)).setMaxWidth(60);
		file_table.getColumn(headers.elementAt(1)).setMaxWidth(350);
		file_table.getColumn(headers.elementAt(2)).setMaxWidth(55);
		file_table.getColumn(headers.elementAt(3)).setMaxWidth(55);
		file_table.getColumn(headers.elementAt(4)).setMaxWidth(70);
	}

	public boolean testCollection()
	{
		if(reference_file==null || reference_file.length()==0)
		{
			int answer=JOptionPane.showConfirmDialog(this,"No genome file is selected, no DNA info will be displayed."
													+"Do you want to upload it now?");
			switch(answer)
			{
				case JOptionPane.YES_OPTION:
				{
					JFileChooser filechooser=new JFileChooser();
					filechooser.setCurrentDirectory(new File(CURRENT_PATH));
					int open=filechooser.showOpenDialog(this);
					switch(open)
					{
						case JFileChooser.APPROVE_OPTION: break;
						case JFileChooser.CANCEL_OPTION: return false;
					}
					File file=filechooser.getSelectedFile();
					if(file==null) return false;
					CURRENT_PATH=file.getParent();
					reference_file=file.getAbsolutePath();
                    reference_field.setText(reference_file);
					break;
				}
				case JOptionPane.NO_OPTION: break;
				case JOptionPane.CANCEL_OPTION: return false;
				case JOptionPane.CLOSED_OPTION: return false;
			}
		}
		if(data.size()==0)
		{
			JOptionPane.showMessageDialog(this,"No alignment file is selected");
			return false;
		}
		else
		{
            Vector<Vector<String>> b=getProgramName();
			for(int i=0;i<b.size();i++)
			{
				if(b.elementAt(i).elementAt(0).length()==0)
				{
					JOptionPane.showMessageDialog(this,b.elementAt(i).elementAt(0)+": File name is empty.");
					return false;
				}
			}
		}
		
		return true;
	}

    public Vector<Vector<String>> getProgramName()
	{
		Vector<Vector<String>> names=new Vector<Vector<String>>();
		
		for(int i=0;i<data.size();i++)
		{
			Vector row=(Vector)data.elementAt(i);
            Vector<String> vec=new Vector<String>();
			vec.add((String)row.elementAt(0));
			vec.add((String)row.elementAt(1));
			JComboBox box=(JComboBox)row.elementAt(4);
			vec.add((String)box.getSelectedItem());
            names.add(vec);
		}
		return names;
	}

    public void actionPerformed(ActionEvent evt)
	{
		Object source=evt.getSource();
		if(evt.getActionCommand().equals("Browse"))
		{
			////System.out.println("PATH="+PATH);
			JFileChooser filechooser=new JFileChooser();
			filechooser.setCurrentDirectory(new File(CURRENT_PATH));
			int answer=filechooser.showOpenDialog(this);
			switch(answer)
			{
				case JFileChooser.APPROVE_OPTION: break;
				case JFileChooser.CANCEL_OPTION: return;
			}
			File file=filechooser.getSelectedFile();
			if(file==null) return;
			CURRENT_PATH=file.getParent();			

            for (int i = 0; i < browse_button.size(); i++) {
                if (source.equals(browse_button.elementAt(i))) {
                    modifyRow(i, (String) file_table.getValueAt(i, 0), file.getPath(), new JButton("Browse"), new JButton("Remove"), new JComboBox(formats));
                    file_table.repaint();
                }
            }
		}
		else if(evt.getActionCommand().equals("Remove"))
		{
			JButton button;
			//System.out.println(source);

			for(int i=0;i<dm.getRowCount();i++)
			{
				////System.out.println("kkk "+dm.getValueAt(i,3));
				button=(JButton)dm.getValueAt(i,3);
				////System.out.println(i+"+++"+button.getText()+"\n"+remove_button.elementAt(i));
				if(source.equals(button))
				{
					//System.out.println("index="+i);
					removeRow(i);
					break;
				}
			}
			
			dm.setDataVector(data,headers);
			setRender();
		}
	}

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;

        setVisible(false);
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        reference_field = new javax.swing.JTextField();
        refer_browse_button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        table_pane = new javax.swing.JScrollPane();
        add_button = new javax.swing.JButton();
        ok_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File Selection for Reference Genome (FASTA)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        reference_field.setEditable(false);
        reference_field.setFont(new java.awt.Font("Times New Roman", 0, 16));
        reference_field.setText("Select a sequence file from \"Browse\" button");

        refer_browse_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        refer_browse_button.setText("Browse");
        refer_browse_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refer_browse_buttonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .add(reference_field, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(refer_browse_button, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(refer_browse_button)
                    .add(reference_field, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File Selection for Alignment Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        table_pane.setBackground(new java.awt.Color(255, 255, 255));
        table_pane.setFont(new java.awt.Font("Times New Roman", 0, 12));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, table_pane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(table_pane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );

        add_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        add_button.setText("Add");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        ok_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        ok_button.setText("OK");
        ok_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_buttonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(236, 236, 236)
                        .add(ok_button))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(add_button)
                            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(add_button)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(ok_button))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refer_browse_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refer_browse_buttonActionPerformed
        // TODO add your handling code here:
        JFileChooser filechooser=new JFileChooser();
        //filechooser.setMultiSelectionEnabled(true);
        filechooser.setCurrentDirectory(new File(CURRENT_PATH));
        int answer=filechooser.showOpenDialog(this);
        switch(answer) {
            case JFileChooser.APPROVE_OPTION: break;
            case JFileChooser.CANCEL_OPTION: return;
        }
        File file=filechooser.getSelectedFile();
        if(file==null) return;
        String name = file.getAbsolutePath();
        try {
            name = file.getCanonicalPath();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        CURRENT_PATH=file.getParent();
        reference_field.setText(name);
        reference_file=name;
}//GEN-LAST:event_refer_browse_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        // TODO add your handling code here:
        JFileChooser filechooser=new JFileChooser();
        filechooser.setMultiSelectionEnabled(true);
        filechooser.setCurrentDirectory(new File(CURRENT_PATH));
        int answer=filechooser.showOpenDialog(this);
        switch(answer) {
            case JFileChooser.APPROVE_OPTION: break;
            case JFileChooser.CANCEL_OPTION: return;
        }
        File[] files=filechooser.getSelectedFiles();
        if(files==null || files.length==0) return;
        CURRENT_PATH=files[0].getParent();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            //System.out.println("Add: " + file.getName());
            String name = file.getAbsolutePath();
            try {
                name = file.getCanonicalPath();
            } catch (Exception e) {
                //System.out.println(e.getMessage());
            }
            
            addRow("No_" + (data.size() + 1), name, getNewButton("Browse"), getNewButton("Remove"), new JComboBox(formats));
        }
        dm.setDataVector(data, headers);
        setRender();

}//GEN-LAST:event_add_buttonActionPerformed

    private void ok_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_buttonActionPerformed
        // TODO add your handling code here:
        if(testCollection()) doClose(RET_OK);
}//GEN-LAST:event_ok_buttonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        doClose(RET_CANCEL);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton ok_button;
    private javax.swing.JButton refer_browse_button;
    private javax.swing.JTextField reference_field;
    private javax.swing.JScrollPane table_pane;
    // End of variables declaration//GEN-END:variables

}
