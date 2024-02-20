/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;




class InfoPane extends JTextPane{

    private Color bgColor;
    private Font titleFont;
    private Font contentFont;
    private int x;
    private int y;
    private int w;
    private int h;
    private String title;
    private String content;
    private String lengthData;
    private Point mouse;
    private JPanel parent;

    InfoPane(JPanel jPanel) {
        Color c = (Color) UIManager.get("info");
        this.bgColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), 190);

        this.titleFont = new Font("SansSerif", 1, 11);
       
        this.contentFont = new Font("SansSerif", 0, 10);
       
       this.parent=jPanel;
        
    }

    void setMousePosition(Point mouse) {
        this.mouse = mouse;
    }

    private void calculatePosition() {
        this.x = (this.mouse.x + 15);
        this.y = (this.mouse.y + 20);

        if (this.x + this.w >= this.parent.getWidth()) {
            this.x = (this.parent.getWidth() - this.w - 1);
        }
        if (this.y + this.h >= this.parent.getHeight()) {
            this.y = (this.parent.getHeight()- this.h - 1);
        }
    }

    public void paint(Point mouse,String title, String content ) {
        this.title = title;
        this.content = content;
        setMousePosition(mouse);
        calculatePosition();

        Graphics2D g = (Graphics2D) parent.getGraphics();
        g.translate(this.x, this.y);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(this.bgColor);
        g.fillRoundRect(0, 0, this.w - 1, this.h - 1, 10, 10);
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, this.w - 1, this.h - 1, 10, 10);

        g.setFont(this.titleFont);
        g.drawString(this.title, 10, 15);
        g.setFont(this.contentFont);
        g.drawString(this.content, 10, 30);
//        g.drawString(this.lengthData, 10, 45);


        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.translate(-this.x, -this.y);
    }
}
