/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Test extends JApplet {

    private JScrollBar vsb = new JScrollBar(JScrollBar.VERTICAL);
    private SSPanel panel = new SSPanel();

    public Test() {
        Container contentPane = getContentPane();

        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(vsb, BorderLayout.EAST);

        vsb.addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(
                    AdjustmentEvent e) {
                JScrollBar sb = (JScrollBar) e.getSource();
                showScrollBarValues();
                panel.setTopIndexByPixelValue(e.getValue());
                panel.repaint();
            }
        });
    }

    public void paint(Graphics g) {
        Dimension pref = panel.getPreferredSize();
        Dimension size = panel.getSize();
        int extent = size.height, max = pref.height;
        int value = Math.max(0,
                Math.min(vsb.getValue(), max - extent));

        vsb.setVisible(extent < max);
        vsb.setUnitIncrement(panel.getUnitHeight());
        vsb.setBlockIncrement(extent - vsb.getUnitIncrement());
        vsb.setValues(value, extent, 0, max);

        showScrollBarValues();
        super.paint(g);
    }

    private void showScrollBarValues() {
        showStatus("min: " + vsb.getMinimum() +
                ", max: " + vsb.getMaximum() +
                ", visible amount: " +
                vsb.getVisibleAmount() +
                ", value: " + vsb.getValue());
    }
}

class SSPanel extends JPanel {

    private int topIndex = 0;
    private int fh;
    private String[] data = {
        "Brown, Ted: 000-00-0001", "Brown, Ted: 000-00-0002",
        "Brown, Ted: 000-00-0003", "Brown, Ted: 000-00-0004",
        "Brown, Ted: 000-00-0005", "Brown, Ted: 000-00-0006",
        "Brown, Ted: 000-00-0007", "Brown, Ted: 000-00-0008",
        "Brown, Ted: 000-00-0009", "Brown, Ted: 000-00-00010",
        "Brown, Ted: 000-00-00011", "Brown, Ted: 000-00-00012",
        "Brown, Ted: 000-00-00013", "Brown, Ted: 000-00-00014",
        "Brown, Ted: 000-00-00015", "Brown, Ted: 000-00-00016",
        "Brown, Ted: 000-00-00017", "Brown, Ted: 000-00-00018",
        "Brown, Ted: 000-00-00019", "Brown, Ted: 000-00-00020",
        "Brown, Ted: 000-00-00021", "Brown, Ted: 000-00-00022",
        "Brown, Ted: 000-00-00023", "Brown, Ted: 000-00-00024",
        "Brown, Ted: 000-00-00025", "Brown, Ted: 000-00-00026",
        "Brown, Ted: 000-00-00027", "Brown, Ted: 000-00-00028",
        "Brown, Ted: 000-00-00029", "Brown, Ted: 000-00-00030",};

    public void paintComponent(Graphics g) {
        Color color = g.getColor();
        super.paintComponent(g);
        g.setColor(color);

        Dimension size = getSize();
        Insets insets = getInsets();
        int y = insets.top;

        for (int i = topIndex; i < data.length; ++i, y += fh) {
            g.drawString(data[i], 0, y);

            if (y + fh > size.height - insets.bottom) {
                break;
            }
        }
    }

    public void setTopIndexByPixelValue(int pixelValue) {
        topIndex = pixelValue / fh;
    }

    public int getUnitHeight() {
        return fh;
    }

    public Dimension getPreferredSize() {
        Dimension dim = new Dimension();
        Graphics g = getGraphics();

        try {
            FontMetrics fm = g.getFontMetrics();
            fh = fm.getHeight();

            dim.width = fm.stringWidth(data[data.length - 1]);
            dim.height = fm.getHeight() * (data.length + 1);
        } finally {
            g.dispose();
        }
        return dim;
    }
}

