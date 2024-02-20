/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Test extends JFrame {

    private JButton readButton = new JButton("read file");
    private BufferedInputStream in;
    private ProgressMonitor pm;
    private String fileName = "D:\\Program Files\\CuteFtp Pro8.rar";

    public Test() {
        final Container contentPane = getContentPane();

        contentPane.setLayout(new FlowLayout());
        contentPane.add(readButton);

        readButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    in = new BufferedInputStream(
                            new FileInputStream(fileName));

                    pm = new ProgressMonitor(contentPane,
                            "Reading File:",
                            fileName,
                            0, in.available());
                } catch (FileNotFoundException fnfx) {
                    fnfx.printStackTrace();
                } catch (IOException iox) {
                    iox.printStackTrace();
                }

                ReadThread t = new ReadThread();
                t.start();
            }
        });
    }

    class ReadThread extends Thread {

        int i, cnt = 0;
        String s;

        public void run() {
            try {
                readButton.setEnabled(false);

                while (!pm.isCanceled() && (i = in.read()) != -1) {
                    try {
                        Thread.currentThread().sleep(25);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.print((char) i);

                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            pm.setProgress(++cnt);
                        }
                    });
                }
                if (pm.isCanceled()) {
                    JOptionPane.showMessageDialog(
                            Test.this,
                            "Operation Canceled!",
                            "Cancellation",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
            readButton.setEnabled(true);
        }
    }

    public static void main(String args[]) {
        GJApp.launch(new Test(),
                "Using Progress Monitors", 300, 300, 450, 300);
    }
}

class GJApp extends WindowAdapter {

    static private JPanel statusArea = new JPanel();
    static private JLabel status = new JLabel(" ");
    static private ResourceBundle resources;

    public static void launch(final JFrame f, String title,
            final int x, final int y,
            final int w, int h) {
        launch(f, title, x, y, w, h, null);
    }

    public static void launch(final JFrame f, String title,
            final int x, final int y,
            final int w, int h,
            String propertiesFilename) {
        f.setTitle(title);
        f.setBounds(x, y, w, h);
        f.setVisible(true);

        statusArea.setBorder(BorderFactory.createEtchedBorder());
        statusArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusArea.add(status);
        status.setHorizontalAlignment(JLabel.LEFT);

        f.setDefaultCloseOperation(
                WindowConstants.DISPOSE_ON_CLOSE);

        if (propertiesFilename != null) {
            resources = ResourceBundle.getBundle(
                    propertiesFilename, Locale.getDefault());
        }

        f.addWindowListener(new WindowAdapter() {

            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    static public JPanel getStatusArea() {
        return statusArea;
    }

    static public void showStatus(String s) {
        status.setText(s);
    }

    static Object getResource(String key) {
        if (resources != null) {
            return resources.getString(key);
        }
        return null;
    }
}

