/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Windows;

import Magic.Units.Gui.InfoBase;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Piece.Piece;
import Magic.WinMain.MagicFrame;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPopupMenu;


import utils.swing.SwingUtil;

public class MagicPartCenter extends MouseAdapter implements PartComponent {

    public MagicPanel parent;
    // JPopupMenu button3Menu = new JPopupMenu();
    public Piece focusedPiece;
    public JPopupMenu button3Menu = new JPopupMenu();
    //public static boolean ifPaintFocuse = true;

    public void PopupMenu(MouseEvent e) {
    }

    public boolean ifFocusedNotNull() {
        if (focusedPiece == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setPiece(Piece focusedPiece) {
        this.focusedPiece = focusedPiece;
    }

    public MagicPartCenter(MagicPanel magicPanel) {
        this.parent = magicPanel;
    }

    public void copyReadID(Piece focusedPiece) {
        // System.out.println(focusedPiece + "----copyReadID-----focusedPiece---------");
        SwingUtil.copyToClipboard(focusedPiece.toTitleString());
    }

    public void copyReadDetailedInfo(Piece focusedPiece) {
        String detailedInfo = focusedPiece.toPopTipString();

        SwingUtil.copyToClipboard(detailedInfo);
    }

    public void paint(Graphics2D g) {
        if (focusedPiece != null && focusedPiece.viewPiece != null) {
            g.setColor(Color.red);
            g.draw3DRect(focusedPiece.viewPiece.x1, focusedPiece.viewPiece.y1, focusedPiece.viewPiece.x2 - focusedPiece.viewPiece.x1, focusedPiece.viewPiece.y2 - focusedPiece.viewPiece.y1, true);
        }
        parent.paintSelectArea(g);
    }

    public void clearScreen() {
        focusedPiece = null;
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        //  System.out.println("--------mouseMoved----------------");
        if (parent.tracks == null) {
            //  System.out.println("---------------parent is null----------------------");
            return;
        }
        parent.requestFocus();
        int x = evt.getX();
        int y = evt.getY();
        focusedPiece = parent.getFocusedPiece(x, y);
        //System.out.println("focusedPiece===========================");
        setPiece(focusedPiece);

        showReadInfo(x, y, focusedPiece);
        parent.repaint();
    }

    public void showReadInfo(int x, int y, Piece piece) {
        if (piece == null || parent.tracks == null) {
            //   System.out.println("---------- piece == null--------------------");
            return;
        }
        InfoBase rbi = new InfoBase();
        rbi.title = piece.toTitleString();
        rbi.content = piece.toContentString().split("\n");
        //  System.out.println("---------rbi show--------------");
       MagicFrame.instance.poptip.popupInfo(rbi, x, y, parent);
    }

    @Override
    public void mouseExited(MouseEvent evt) {

        if (parent == null) {
            return;
        }
        parent.magicFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


        //   Log.instance().alignPara.showPopTip=false;
       MagicFrame.instance.poptip.setVisible(false);
        // parent.MagicFrame.instance().poptip.

        // System.out.println("  parent.MagicFrame.instance().poptip.setVisible(false);================================");
        if (!button3Menu.isShowing()) {
            focusedPiece = null;
            //  parent.MagicFrame.instance().poptip.setVisible(false);
        }

        parent.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // System.out.println("mouse in ---------------------");
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // TODO add your handling code here:

        if (parent.tracks == null || parent.tracks[0] == null) {
            return;
        }
        parent.requestFocus();
        int x = evt.getX();
        int y = evt.getY();


        if (evt.getButton() == MouseEvent.BUTTON3) {
            PopupMenu(evt);

            parent.setUpdateBuffer(true);
            parent.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent evt) {
        int count = evt.getWheelRotation();
      /*  System.out.println("mouse wheel moved");
        if (parent.getVertical() == null) {
            System.out.println("parent.getVertical()==null");
            return;
        }
        int value = parent.getVertical().getValue();
        value += count * parent.VERT_UNIT;
        if (value < 0) {
            value = 0;
        }
        if (value > parent.getVertical().getMaximum()) {
            value = parent.getVertical().getMaximum();
        }
        parent.getVertical().setValue(value);*/
    }

    public void mousePressed(MouseEvent evt) {

        if (parent.tracks == null || parent.tracks[0] == null) {
            return;
        }
        parent.requestFocus();
        int x = evt.getX();
        int y = evt.getY();
        parent.start.setLocation(x, y);
    }
}
