package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestDraged extends JFrame {

    public Button button;
    Panel panel;
    static Point origin = new Point();
    int x = 0;
    int y = 0;

    public TestDraged() {
        super("TestMouseDragged");
        this.setSize(600, 400);
        button = new Button("Test MouseDragged");

        panel = new Panel();
        panel.add(button);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", panel);

        button.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });


        button.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                Point p = button.getLocation();
                button.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                repaint();
            }
        });
    }

    public static void main(String args[]) {
        TestDraged app = new TestDraged();
        app.setVisible(true);
        app.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
