/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Windows;

import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Gui.Windows.MagicPartCenter;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import utils.ReportInfo;

/**
 *
 * @author lenovo
 */
public class AlnMagicPartCenter extends MagicPartCenter {

    private JMenuItem copyid = new JMenuItem("Copy ID");
    private JMenuItem copydetail = new JMenuItem("Copy Detailed Information");
    private JCheckBoxMenuItem showPopTip = new JCheckBoxMenuItem("Show PopTip");
    private JCheckBoxMenuItem showReadBorder = new JCheckBoxMenuItem("Show Border");
    private JCheckBoxMenuItem showReadString = new JCheckBoxMenuItem("Show Read Base String");

    public void PopupMenu(MouseEvent e) {

        button3Menu = new JPopupMenu();
        button3Menu.add(copyid);
        button3Menu.add(copydetail);
        button3Menu.addSeparator();

        showPopTip.setSelected(Log.instance().alignPara.showPopTip);
        showReadBorder.setSelected(Log.instance().alignPara.showReadBorder);
        showReadString.setSelected(Log.instance().alignPara.showReadString);

        button3Menu.add(showPopTip);
        button3Menu.add(showReadBorder);
        button3Menu.add(showReadString);
        Button3Action b3a = new Button3Action();
        copyid.addActionListener(b3a);
        copydetail.addActionListener(b3a);
        copyid.setEnabled(ifFocusedNotNull());
        copydetail.setEnabled(ifFocusedNotNull());

        showPopTip.addActionListener(b3a);
        showReadBorder.addActionListener(b3a);
        showReadString.addActionListener(b3a);

        button3Menu.show(e.getComponent(), e.getX(), e.getY());

    }

    class Button3Action implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (parent.tracks == null) {
                // System.out.println(parent.track + "----------parent.track null-------------");
                return;
            }
            Object source = e.getSource();
            if (source.equals(copyid)) {
                if (focusedPiece == null) {
                    ReportInfo.reportInformation("No read is selected");
                } else {
                    copyReadID(focusedPiece);
                }
            } else if (source.equals(copydetail)) {
                if (focusedPiece == null) {
                    ReportInfo.reportInformation("No read is selected");
                } else {
                    copyReadDetailedInfo(focusedPiece);
                }
            } else if (source.equals(showPopTip)) {
                Log.instance().alignPara.showPopTip = showPopTip.isSelected();

            } else if (source.equals(showReadBorder)) {
                Log.instance().alignPara.showReadBorder = showReadBorder.isSelected();
                parent.magicFrame.resetAssembly();
            } else if (source.equals(showReadString)) {
                Log.instance().alignPara.showReadString = showReadString.isSelected();
                parent.magicFrame.resetAssembly();
            }

        }
    }
    public AlnMagicPartCenter(MagicPanel magicPanel) {
        super(magicPanel);
        parent = magicPanel;
    }
}
