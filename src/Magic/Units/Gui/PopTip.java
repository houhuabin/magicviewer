package Magic.Units.Gui;

import Magic.Units.File.Parameter.AlignPara;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.GlobalSetting;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.LogImplement;
import Magic.WinMain.MagicFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class PopTip extends JPopupMenu {

    private int w = 120;
    private int h = 60;
    private InfoBase ib;
    private Color backgroundColor = new Color(195, 212, 232);
    int fontWidth = 0;
    int fontHeight = 0;
    private Point popPoint;

    public PopTip() {
        setPreferredSize(new Dimension(this.w, this.h));
        FontMetrics fm = getFontMetrics(new Font("SansSerif", 0, 11));
        this.fontWidth = fm.charWidth('A');
        this.fontHeight = fm.getAscent();
    }

    public Color getInfoColor() {
        Color c = (Color) UIManager.get("info");
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 190);
    }

    private BufferedImage getSequenceImage(InfoBase ib) {
        if (ib.read == null) {
            return null;
        }

        BufferedImage image = new BufferedImage(ib.read.length() + 20, 20, 1);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(195, 212, 232));
        g.fillRect(0, 0, image.getWidth() + 1, image.getHeight());
        Polygon p = new Polygon();

        int xStart = 10;
        int yStart = 0;
        if (ib.strand) {
            p.addPoint(xStart + ib.read.length(), yStart);

            p.addPoint(xStart + ib.read.length() + 5, yStart + 5);
            p.addPoint(xStart + ib.read.length(), yStart + 10);
        } else {
            p.addPoint(xStart, yStart);
            p.addPoint(xStart - 5, yStart + 5);
            p.addPoint(xStart, yStart + 10);
        }

        for (int i = 0; i < ib.read.length(); ++i) {
            char b = ib.read.charAt(i);
            char c = ib.refSeq.charAt(i);
            if (b == c) {
                int qual = (ib.quality.charAt(i) - Log.instance().global.Qualty_SCORE_ADD) / 5 + 1;

                g.setColor(Magic.Units.Color.ColorRep.GrayColor[utils.ForMagic.getValidatedQualityIndex(qual)]);
            } else {
                g.setColor(Color.red);
            }
            int x = xStart + i;
            g.drawLine(x, yStart, x, yStart + 10);
        }
        g.setColor(Color.white);
        g.fill(p);
        g.setColor(Color.black);
        g.draw(p);
        g.drawRect(xStart, yStart, ib.read.length(), 10);
        return image;
    }

    public void popupInfo(InfoBase ib, int x, int y, JPanel parent) {
        //  parent.g
        // System.out.println(x+"   ============  "+y+"   ");
        // MagicFrame.instance.get
        if (!(Log.instance().alignPara.showPopTip)) {
            return;
        }
        this.ib = ib;
        if ((ib.title == null) || (ib.title.equals(""))) {
            return;
        }






        validateSize(ib);
        JScrollPane scroll_pane = getJScrollPane(ib);

        removeAll();
        add(scroll_pane);



        /* if (y > parent.getHeight() - getHeight()) {
        // System.out.println(y + "  " + parent.getHeight());
        y = parent.getHeight() - getHeight() - 5;
        }
        if (x > parent.getWidth() - getWidth()) {
        // System.out.println(y + "  " + parent.getHeight());
        x = parent.getWidth() - getWidth() - 5;
        }*/
        validatePosion(x, y, parent);
        show(parent, popPoint.x, popPoint.y);

        validate();
        setVisible(true);
    }

    public void validatePosion(int x, int y, JPanel parent) {

        Point screen = new Point(x + 2, y + 10);

        int popX = x + 2;
        int popY = y + 10;
        SwingUtilities.convertPointToScreen(screen, parent);


        if (screen.y + this.getHeight() > ForEverStatic.desktopBoundsHeight) {
            // System.out.print(popPoint.y+"  "+ForEverStatic.desktopBoundsHeight+" "+h);
            popY = y - this.getHeight() - 2;
            // System.out.println("  "+popPoint.y);
        }
        if (screen.x + this.getWidth() > ForEverStatic.desktopBoundsWidth) {
            popX = x - this.getWidth();
        }
        popPoint = new Point(popX, popY);
    }

    public void validateSize(InfoBase ib) {
        int maxWidth = 0;
        int maxHeight = 0;

        if (ib.content != null) {
            int height = this.fontHeight * ib.content.length + 50;
            maxHeight = Math.max(this.h, height);
        }

        if (ib.read != null) {
            int windth = ib.read.length() + 30;
            maxWidth = Math.max(this.w, windth);
            maxHeight += 30;
        }

        maxWidth = getMaxWidth();

        setSize(new Dimension(maxWidth, maxHeight));
        setPreferredSize(new Dimension(maxWidth, maxHeight));
        validate();
    }

    public int getMaxWidth() {
        int windth = 0;
        if (this.ib.title != null) {
            windth = this.ib.title.length();
        }
        for (String str : this.ib.content) {
            windth = Math.max(windth, str.length());
        }
        int max = windth * this.fontWidth + 20;
        return Math.max(this.w, max);
    }

    public JScrollPane getJScrollPane(InfoBase ib) {
        StyledDocument styledDoc = new DefaultStyledDocument();
        JTextPane textPane = new JTextPane(styledDoc);

        textPane.setBackground(this.backgroundColor);
        textPane.setEditable(false);
        textPane.setMargin(new Insets(5, 10, 0, 10));

        MyPanel mp = new MyPanel();
        mp.setBackground(this.backgroundColor);

        JPanel consensusPanel = new JPanel(new BorderLayout());
        consensusPanel.add(textPane, "North");
        if (ib.read != null) {
            consensusPanel.add(mp);
        }
        consensusPanel.setBackground(this.backgroundColor);

        JScrollPane scrollPane = new JScrollPane(consensusPanel);

        createStyle("StyleTitle", styledDoc, 11, 1, 0, 0, Color.BLACK, "SansSerif");
        createStyle("StyleContent", styledDoc, 10, 0, 0, 0, Color.BLACK, "SansSerif");

        insertDoc(styledDoc, ib.title + ForEverStatic.lineSeparator, "StyleTitle");

        if (ib.content != null) {
            for (int i = 0; i < ib.content.length; ++i) {
                if (ib.content[i] != null) {
                    insertDoc(styledDoc, ib.content[i] + ForEverStatic.lineSeparator, "StyleContent");
                }
            }
        }
        return scrollPane;
    }

    public void insertDoc(StyledDocument styledDoc, String content, String currentStyle) {
        try {
            styledDoc.insertString(styledDoc.getLength(), content, styledDoc.getStyle(currentStyle));
        } catch (BadLocationException e) {
            System.err.println("BadLocationException: " + e);
        }
    }

    public void createStyle(String style, StyledDocument doc, int size, int bold, int italic, int underline, Color color, String fontName) {
        Style sys = StyleContext.getDefaultStyleContext().getStyle("default");
        try {
            doc.removeStyle(style);
        } catch (Exception e) {
        }
        Style s = doc.addStyle(style, sys);
        StyleConstants.setFontSize(s, size);
        StyleConstants.setBold(s, bold == 1);
        StyleConstants.setItalic(s, italic == 1);
        StyleConstants.setUnderline(s, underline == 1);
        StyleConstants.setForeground(s, color);
        StyleConstants.setFontFamily(s, fontName);
        StyleConstants.setLineSpacing(s, 10.0F);
        StyleConstants.setLeftIndent(s, 10.0F);
    }

    class MyPanel extends JPanel {

        public void paintComponent(Graphics g) {
            BufferedImage bfi = PopTip.this.getSequenceImage(PopTip.this.ib);

            if (bfi != null) {
                setPreferredSize(new Dimension(bfi.getWidth(), bfi.getHeight()));
                g.drawImage(bfi, 0, 0, this);
            }
        }
    }
}
