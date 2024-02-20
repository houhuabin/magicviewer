/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tools.java
 *
 * Created on Apr 2, 2009, 1:38:17 PM
 */
package See.Tools;

import See.Tools.SimulationPanel;
import See.Tools.FilePanel;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import See.constant.Global;
import See.Tools.MutationSimulation.MutaMethyJPanel;
import See.Tools.ReadSimulation.ReadSimuUniqLenPanel;
import See.Tools.ReadSimulation.ReadSimuUserPanel;
import See.Tools.ReadSimulation.ReadStatPanel;
import See.Tools.File.ReadSelectPanel;
import See.Tools.File.ReadLongPanel;
import See.Tools.File.FastaPartitionPanel;
import See.Tools.MutationSimulation.MutaUserPanel;
import See.Tools.MutationSimulation.MutaOneParaPanel;

import See.Tools.MutationSimulation.MutaTwoParasPanel;
import See.Tools.ReadSimulation.ReadStatPanel;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author QIJ
 */
public class ToolsDialog extends javax.swing.JDialog {

    SimulationPanel simu_panel;
    FilePanel others_panel;
    ReadSimuUniqLenPanel reads_simu_uniform_panel;
    ReadSimuUserPanel reads_simu_user_panel;
    ReadStatPanel read_stat_panel;
    MutaOneParaPanel muta_one_para_panel;
    MutaTwoParasPanel muta_two_paras_panel;
    MutaUserPanel muta_user_panel;
    FastaPartitionPanel fasta_part_panel;
    ReadLongPanel reads_long_panel;
    ReadSelectPanel reads_select_panel;
    MutaMethyJPanel muta_methy_easy_panel;
    MutaMethyJPanel muta_methy_panel;
    public final String READS_SIMU_UNIQUE_LEN = "READS_SIMU_UNIQUE_LEN";
    public final String READS_SIMU_USER = "READS_SIMU_USER";
    public final String MUTA_ONE_PARA = "MUTA_ONE_PARA";
    public final String MUTA_TWP_PARAS = "MUTA_TWP_PARAS";
    public final String MUTA_USER = "MUTA_USER";
    public final String READS_STAT = "READS_STAT";
    public final String FASTA_PART = "FASTA_PART";
    public final String READS_LONG = "READS_LONG";
    public final String READS_SELECT = "READS_SELECT";
     public final String METHY_EASY = "METHY_EASY";
     public final String METHY = "METHY";
    public String CURRENT_PATH = System.getProperty("user.dir");
    public String EXE_PATH = "";
    public String DOC_PATH = "";
    public String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
    public int panelNum;
    public JFrame parent;

    /** Creates new form Tools */
    public ToolsDialog(javax.swing.JFrame parent, boolean modal,int panelNum) {
         super(parent, modal);
        //jobstart=parent;
        this.panelNum = panelNum;
        this.parent=parent;
        EXE_PATH = "";
        DOC_PATH = "";

        initComponents();
        initValues();

        this.setLocation( Global.screenLen / 2 - this.getPreferredSize().width / 2,
                 Global.screenHeight / 2 - this.getPreferredSize().height / 2);
    }

    public void initValues() {
        if (panelNum == 0) {
            simu_panel = new SimulationPanel(this);
            updatePanel(simu_panel);
        } else {
            others_panel = new FilePanel(this);
            updatePanel(others_panel);
        }
        //this.pack();
    }

    public void receive(String value) {
        System.out.println("receive: " + value);
        if (value.equals(READS_SIMU_UNIQUE_LEN)) {
            if (reads_simu_uniform_panel == null) {
                reads_simu_uniform_panel = new ReadSimuUniqLenPanel(this);
            }
            updatePanel(reads_simu_uniform_panel);
        } else if (value.equals(READS_SIMU_USER)) {
            if (reads_simu_user_panel == null) {
                reads_simu_user_panel = new ReadSimuUserPanel(this);
            }
            updatePanel(reads_simu_user_panel);
        } else if (value.equals(READS_STAT)) {
            if (read_stat_panel == null) {
                read_stat_panel = new ReadStatPanel(this);
            }
            updatePanel(read_stat_panel);
        } else if (value.equals(MUTA_ONE_PARA)) {
            if (muta_one_para_panel == null) {
                muta_one_para_panel = new MutaOneParaPanel(this);
            }
            updatePanel(muta_one_para_panel);
        } else if (value.equals(MUTA_TWP_PARAS)) {
            if (muta_two_paras_panel == null) {
                muta_two_paras_panel = new MutaTwoParasPanel(this);
            }
            updatePanel(muta_two_paras_panel);
        } else if (value.equals(MUTA_USER)) {
            if (muta_user_panel == null) {
                muta_user_panel = new MutaUserPanel(this);
            }
            updatePanel(muta_user_panel);
        } else if (value.equals(FASTA_PART)) {
            if (fasta_part_panel == null) {
                fasta_part_panel = new FastaPartitionPanel(this);
            }
            updatePanel(fasta_part_panel);
        } else if (value.equals(READS_LONG)) {
            if (reads_long_panel == null) {
                reads_long_panel = new ReadLongPanel(this);
            }
            updatePanel(reads_long_panel);
        } else if (value.equals(READS_SELECT)) {
            if (reads_select_panel == null) {
                reads_select_panel = new ReadSelectPanel(this);
            }
            updatePanel(reads_select_panel);
        }
        else if (value.equals(METHY)) {
            if (muta_methy_panel == null) {
                muta_methy_panel = new MutaMethyJPanel(this);
            }
            updatePanel(muta_methy_panel);
        }else if (value.equals(METHY_EASY)) {
            if (muta_methy_easy_panel == null) {
                muta_methy_easy_panel = new MutaMethyJPanel(this);
            }
            updatePanel(muta_methy_easy_panel);
        }

    }

    public void updatePanel(JPanel panel) {
        container_panel.removeAll();

        org.jdesktop.layout.GroupLayout container_panelLayout = new org.jdesktop.layout.GroupLayout(container_panel);
        container_panel.setLayout(container_panelLayout);
        container_panelLayout.setHorizontalGroup(
                container_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        container_panelLayout.setVerticalGroup(
                container_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        this.pack();
    }

    public static void main(String args[]) {
        try {
            PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
            PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
            PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
            PlasticLookAndFeel.setTabStyle("Metal");



            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        } catch (Exception e) {
        }



      //  ToolsFrame tools_frame = new ToolsFrame(ToolsFrame(this,true,0);0);
       // tools_frame.setVisible(true);


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        container_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Tools");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 1, 12));
        jButton1.setText("Show All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        container_panel.setFont(new java.awt.Font("Times New Roman", 0, 12));

        org.jdesktop.layout.GroupLayout container_panelLayout = new org.jdesktop.layout.GroupLayout(container_panel);
        container_panel.setLayout(container_panelLayout);
        container_panelLayout.setHorizontalGroup(
            container_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 448, Short.MAX_VALUE)
        );
        container_panelLayout.setVerticalGroup(
            container_panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 284, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton1)
                .addContainerGap(357, Short.MAX_VALUE))
            .add(container_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(container_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       if (panelNum == 0) {
            simu_panel = new SimulationPanel(this);
            updatePanel(simu_panel);
        } else {
            others_panel = new FilePanel(this);
            updatePanel(others_panel);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:        

        this.dispose();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel container_panel;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
