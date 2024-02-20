/*
 * ExpandingPanel.java
 *
 * Created on October 15, 2008, 3:53 PM
 */
package utils.swing.ExpandPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Huabin Hou
 */
public class SettingPanel extends javax.swing.JPanel {

    Vector<JPanel> panels;
    Vector<ActionPanel> aps;
    Vector<Boolean> expand;
    JDialog parent;

    /** Creates new form ExpandingPanel */
    public SettingPanel(Vector<String> names, Vector<JPanel> ps, Vector<Boolean> expa, JDialog jf) {
        panels = ps;
        expand = expa;
        parent = jf;
        if (names.size() != ps.size()) {
            return;
        }
        aps = new Vector<ActionPanel>();
        for (int i = 0; i < names.size(); i++) {
            ActionPanel p = new ActionPanel(names.elementAt(i));

            //如果name为空则用不隐藏
          //  if (!names.elementAt(i).equals("")) {
                p.addMouseListener(new java.awt.event.MouseAdapter() {

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        formMousePressed(evt);
                    }
                });
                aps.add(p);
          //  }

        }
        initAllPanels();
    }

    public void setExpand(int index, boolean exp) {
        if (aps == null) {
            return;
        }
        if (index < 0 || index > aps.size() - 1) {
            return;
        }
        ActionPanel ap = aps.elementAt(index);
        ap.setExpand(exp);
        ap.repaint();
        togglePanelVisibility(ap);
        parent.pack();
    }

    private void formMousePressed(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        ActionPanel ap = (ActionPanel) evt.getSource();
        if (ap.isExpanded()) {
            ap.setExpand(false);
        } else {
            ap.setExpand(true);
        }
        ap.repaint();
        togglePanelVisibility(ap);
        parent.pack();
    }

    // Variables declaration - do not modify
    // End of variables declaration
    private void togglePanelVisibility(ActionPanel ap) {
        int index = getPanelIndex(ap);
        ////System.out.println(index);
        if (aps.elementAt(index).isExpanded()) {
            panels.elementAt(index).setVisible(true);
        } else {
            panels.elementAt(index).setVisible(false);
        }

        ap.getParent().validate();
    }

    private int getPanelIndex(ActionPanel ap) {
        for (int i = 0; i < aps.size(); i++) {
            if (ap == aps.elementAt(i)) {
                return i;
            }
        }
        return -1;
    }

    private void initAllPanels() {
        this.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(1, 3, 0, 3);
        gbc.weightx = 1.0;
        gbc.fill = gbc.HORIZONTAL;
        gbc.gridwidth = gbc.REMAINDER;
        for (int i = 0; i < aps.size(); i++) {
            this.add(aps.elementAt(i), gbc);
            this.add(panels.elementAt(i), gbc);

            if (expand.elementAt(i).booleanValue()) {
                aps.elementAt(i).setExpand(true);
                panels.elementAt(i).setVisible(true);
            } else {
                aps.elementAt(i).setExpand(false);
                panels.elementAt(i).setVisible(false);
            }
            ////System.out.println(i);
        }
        //JLabel padding = new JLabel();
        //gbc.weighty = 1.0;
        //this.add(padding, gbc);
    }
}
