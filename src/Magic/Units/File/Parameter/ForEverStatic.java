/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File.Parameter;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

/**
 *
 * @author lenovo
 */
public class ForEverStatic {

    public static String lineSeparator = System.getProperty("line.separator");
    public static String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
    public static String EXE_PATH = "external" + FILE_SEPARATOR + "bin";
    public static String PERL_PATH = EXE_PATH + FILE_SEPARATOR + "perl" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "perl ";
    public static int screenLen = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int desktopBoundsWidth;
    public static int desktopBoundsHeight;
    public int CPU_NUM = Runtime.getRuntime().availableProcessors();
    public File TMP_DIR = new File(System.getProperty("java.io.tmpdir"), System.getProperty("user.name"));
    public static final int RET_OK = 1;
    public static final int RET_ERR = 2;
    public static final int RUNNING = 0;
    public static final int RET_CANCEL = -1;
    public static int invalidValue = 0xffffffff;
    public static String CURRENT_PATH = System.getProperty("user.dir");

    public static void setDefault(Insets screenInsets) {
        //去掉任务栏的高度
        Rectangle desktopBounds = new Rectangle(
                screenInsets.left, screenInsets.top,
                screenLen - screenInsets.left - screenInsets.right,
                screenHeight - screenInsets.top - screenInsets.bottom);

        ForEverStatic.desktopBoundsHeight = desktopBounds.height;
        ForEverStatic.desktopBoundsWidth = desktopBounds.width;
        ForEverStatic.screenHeight = screenHeight;
        ForEverStatic.screenLen = screenLen;

    }
}
