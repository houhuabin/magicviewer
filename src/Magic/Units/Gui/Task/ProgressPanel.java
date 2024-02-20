/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProgressPanel.java
 *
 * Created on 2009-6-26, 14:54:58
 */
package Magic.Units.Gui.Task;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Huabin Hou
 */
public class ProgressPanel extends javax.swing.JPanel {

    /** Creates new form ProgressPanel */
    public ProgressPanel() {
        initComponents();
    }
    public int setpNum = 0;
    private ImageIcon running = new javax.swing.ImageIcon(getClass().getResource("/icons/66CC33.gif"));
    private ImageIcon waitting = new javax.swing.ImageIcon(getClass().getResource("/icons/manual3.png"));
    private ImageIcon done = new javax.swing.ImageIcon(getClass().getResource("/icons/Smiley_Happy3.png"));
    private ImageIcon arrowIcon = new javax.swing.ImageIcon(getClass().getResource("/icons/b_right.png"));
    private ArrayList<String> setpNames;
    private Vector<javax.swing.JButton> buttons = new Vector<javax.swing.JButton>();
    private Vector<javax.swing.JLabel> buttonAnnos = new Vector<javax.swing.JLabel>();
    private Vector<javax.swing.JLabel> arrows = new Vector<javax.swing.JLabel>();
    private javax.swing.GroupLayout layout;
    private int currentStep = 1;

    // private javax.swing.JLabel arrow = new javax.swing.JLabel();
    public ProgressPanel(ArrayList<String> setpNames) {
        initComponents();
        this.setpNum = setpNames.size();
        this.setpNames = setpNames;
        init();

    }

    private void init() {
        layout = new javax.swing.GroupLayout(this.containerPanel);
        this.containerPanel.setLayout(layout);

        this.containerPanel.setSize(100,50);

        for (int i = 0; i < setpNames.size(); i++) {
            String stepName = setpNames.get(i);
            javax.swing.JButton button = getButton(stepName);
            buttons.add(button);

            javax.swing.JLabel buttonAnno = getLabelByName(stepName);
            buttonAnnos.add(buttonAnno);

            if (i == setpNames.size() - 1) {
                javax.swing.JLabel arrow = new javax.swing.JLabel();
                arrows.add(arrow);
            } else {
                javax.swing.JLabel arrow = getLabelByIcon(arrowIcon);
                arrows.add(arrow);
            }
        }
        sethorizontalParallelGroup();
        setverticalGroupParallelGroup();
    }

    public void next() {
       // //System.out.println(currentStep +"-----currentStep------setpNum---------"+ setpNum);
        if (currentStep > setpNum) {
            return;
        }
        for (int i = 0; i < setpNum; i++) {
            if (i < currentStep - 1) {
              //  //System.out.println("---------done-------------------");
                buttons.elementAt(i).setIcon(done);
            } else if (i == currentStep - 1) {
                buttons.elementAt(i).setIcon(running);
            }
        }
        currentStep++;
    }

    private void sethorizontalParallelGroup() {
        SequentialGroup horizontalSequentialGroup = layout.createSequentialGroup();


        for (int i = 0; i < buttons.size(); i++) {

            javax.swing.JButton button = buttons.elementAt(i);
            javax.swing.JLabel buttonAnno = buttonAnnos.elementAt(i);
            javax.swing.JLabel arrow = arrows.elementAt(i);


            horizontalSequentialGroup.addGap(20, 20, 20).addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(20, 20, 20).addComponent(arrow)).
                    addComponent(buttonAnno));

        }

        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(horizontalSequentialGroup));

    }

    private void setverticalGroupParallelGroup() {
        SequentialGroup verticalSequentialGroup = layout.createSequentialGroup();
        verticalSequentialGroup.addContainerGap();
        ParallelGroup pg1 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        for (int i = 0; i < buttons.size(); i++) {
            javax.swing.JButton button = buttons.elementAt(i);
            javax.swing.JLabel arrow = arrows.elementAt(i);
            pg1.addComponent(button).addComponent(arrow);
        }
        verticalSequentialGroup.addGroup(pg1);


        verticalSequentialGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);

        ParallelGroup pg2 = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        for (javax.swing.JLabel buttonAnno : buttonAnnos) {
            pg2.addComponent(buttonAnno);
        }
        verticalSequentialGroup.addGroup(pg2);

        verticalSequentialGroup.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(verticalSequentialGroup));

    }

    public javax.swing.JLabel getLabelByName(String name) {
        javax.swing.JLabel arrow = new javax.swing.JLabel();
        arrow.setText(getHtmlString(name));
        return arrow;

    }

    public javax.swing.JLabel getLabelByIcon(javax.swing.ImageIcon ii) {
        javax.swing.JLabel arrow = new javax.swing.JLabel();
        arrow.setIcon(ii); // NOI18N
        return arrow;

    }

    public String getHtmlString(String name) {
        return "<html><center>" + name + "</center></html>";
    }

    public javax.swing.JButton getButton(String name) {
        javax.swing.JButton jb = new javax.swing.JButton();
        jb.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jb.setIcon(waitting); // NOI18N
        jb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jb.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jb.setMaximumSize(new java.awt.Dimension(41, 41));
        jb.setMinimumSize(new java.awt.Dimension(41, 41));
        jb.setPreferredSize(new java.awt.Dimension(41, 41));
        return jb;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout containerPanelLayout = new javax.swing.GroupLayout(containerPanel);
        containerPanel.setLayout(containerPanelLayout);
        containerPanelLayout.setHorizontalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );
        containerPanelLayout.setVerticalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        add(containerPanel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    // End of variables declaration//GEN-END:variables
}
