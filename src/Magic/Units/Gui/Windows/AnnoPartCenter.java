/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Windows;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Gui.Windows.MagicPartCenter;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.WinMain.MagicFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
public class AnnoPartCenter extends MagicPartCenter {

    public AnnoPartCenter(MagicPanel magicPanel) {
        super(magicPanel);
        parent = magicPanel;
    }
    //public Piece selectedPiece;
    private JMenuItem copyid = new JMenuItem("Copy ID");
    private JMenuItem copydetail = new JMenuItem("Copy Detailed Information");
    private JCheckBoxMenuItem showPopTip = new JCheckBoxMenuItem("Show PopTip");
    //public int selectX;
    //private JCheckBoxMenuItem showReadBorder = new JCheckBoxMenuItem("Show Border");

    public void PopupMenu(MouseEvent e) {

        button3Menu = new JPopupMenu();
        button3Menu.add(copyid);
        button3Menu.add(copydetail);
        button3Menu.addSeparator();

        showPopTip.setSelected(Log.instance().alignPara.showPopTip);
        // showReadBorder.setSelected(Log.instance().alignPara.showReadBorder);

        button3Menu.add(showPopTip);
        //  button3Menu.add(showReadBorder);
        Button3Action b3a = new Button3Action();
        copyid.addActionListener(b3a);
        copydetail.addActionListener(b3a);
        copyid.setEnabled(ifFocusedNotNull());
        copydetail.setEnabled(ifFocusedNotNull());

        showPopTip.addActionListener(b3a);
        //  showReadBorder.addActionListener(b3a);

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

            }/* else if (source.equals(showReadBorder)) {
            Log.instance().alignPara.showReadBorder = showReadBorder.isSelected();
            parent.magicFrame.resetAssembly();
            }*/
        }
    }

    public void paint(Graphics2D g) {
        if (focusedPiece != null && focusedPiece.viewPiece != null) {
            g.setColor(Color.red);
            g.draw3DRect(focusedPiece.viewPiece.x1, focusedPiece.viewPiece.y1, focusedPiece.viewPiece.x2 - focusedPiece.viewPiece.x1, focusedPiece.viewPiece.y2 - focusedPiece.viewPiece.y1, true);
        }

        g.setColor(Color.black);
        int xsite = (MagicFrame.instance.annoAssemblyPanel.selectX - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 15 / 100.0F);
        g.setComposite(composite);
        g.drawLine(xsite, 0, xsite, ForEverStatic.screenHeight);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (!parent.ifPaint || parent.tracks == null) {
            return;
        }
        int realX = evt.getX() + parent.dx;

        int coordinate = realX << Log.instance().annoPara.ZOOM;
        if (evt.getButton() == MouseEvent.BUTTON1) {
            // if (evt.getClickCount()==2 && evt.getButton() == MouseEvent.BUTTON1) {

            if (focusedPiece != null) {
                if (focusedPiece.geneticPiece instanceof SNPPiece) {
                    coordinate = focusedPiece.geneticPiece.start;
                }
                //this.selectedPiece=focusedPiece;
            }
            MagicFrame.instance.annoAssemblyPanel.selectX = coordinate;
            parent.magicFrame.gotoSiteAndValidate(coordinate, true);
        } else if (evt.getButton() == MouseEvent.BUTTON3) {
            PopupMenu(evt);

            parent.setUpdateBuffer(true);
            parent.repaint();
        }
    }
}
