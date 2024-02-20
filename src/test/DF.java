/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author Huabin Hou
 */
import   java.awt.Dimension;
import   java.awt.Graphics;
import   java.awt.Image;
import   java.awt.Rectangle;
import   java.awt.Toolkit;
import   java.awt.event.MouseAdapter;
import   java.awt.event.MouseEvent;
import   javax.swing.ImageIcon;
import   javax.swing.JButton;
import   javax.swing.JFileChooser;
import   javax.swing.JPanel;
import   javax.swing.JScrollPane;

public   class   DF   extends   JPanel   {
    private   static   final   long   serialVersionUID   =   1L;
    private   ImagePanel   c   =   null;
    private   JButton   jButton   =   null;

    public   DF()   {
        super();
        initialize();
    }

    private   void   initialize()   {
        this.setSize(300,   200);
        this.setLayout(null);
        this.add(getC(),   null);
        this.add(getJButton(),   null);
    }

    private   ImagePanel   getC()   {
        if   (c   ==   null)   {
            c   =   new   ImagePanel();
            c.setBounds(new   Rectangle(25,   49,   243,   139));
        }
        return   c;
    }

    private   void   opfile()   {
        JFileChooser   chooser   =   new   JFileChooser();
        if   (chooser.showSaveDialog(this)   ==   JFileChooser.APPROVE_OPTION)   {
            String   fname   =   chooser.getSelectedFile().getAbsolutePath();
            //System.out.println(fname);
            c.setImage(fname);
        }
    }

    private   JButton   getJButton()   {
        if   (jButton   ==   null)   {
            jButton   =   new   JButton();
            jButton.setBounds(new   Rectangle(36,   11,   100,   30));
            jButton.setText( "选择图片 ");
            jButton.addMouseListener(new   MouseAdapter()   {
                public   void   mouseClicked(MouseEvent   e)   {
                    opfile();
                }
            });
        }
        return   jButton;
    }

    class   ImagePanel   extends   JScrollPane   {
        private   static   final   long   serialVersionUID   =   1L;
        private   MyPanel   jPanel   =   null;
        Image   img   =   null;

        public   ImagePanel()   {
            super();
            this.setViewportView(getJPanel());
        }

        void   setImage(String   filename)   {
            img   =   Toolkit.getDefaultToolkit().createImage(filename);
            ImageIcon   icon   =   new   ImageIcon(img);
            jPanel.setPreferredSize(new   Dimension(icon.getIconWidth(),   icon.getIconHeight()));
            jPanel.updateUI();
            jPanel.repaint();
        }

        private   JPanel   getJPanel()   {
            if   (jPanel   ==   null)   {
                jPanel   =   new   MyPanel();
                jPanel.setLocation(0,   0);
                jPanel.setLayout(null);
            }
            return   jPanel;
        }

        class   MyPanel   extends   JPanel   {
            public   void   paint(Graphics   g)   {
                g.drawImage(img,   0,   0,   this);
            }
        }
    }
}
