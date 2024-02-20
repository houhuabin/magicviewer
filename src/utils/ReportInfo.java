package utils;

import Magic.Units.Gui.InfoDialog;
import Magic.WinMain.MagicFrame;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * <p>Title:���������Ϣ </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Gu Yuanyan
 * @version 1.0
 */
public class ReportInfo {

    //public static JFrame see;
    private InfoDialog hd;

    public ReportInfo() {
    }

    public enum InfoType {

        error, warning, information, question
    };

    /**
     * Ӧ�ô���Ӱ��ĳ�û�ĳ�β������������
     * @param str
     */
    static public void reportError(String str, Exception e) {
       e.printStackTrace();
       // InfoDialog ifdg =  InfoDialog.instance(see, InfoType.error);
      //  ifdg.setMessage(e.getMessage());
    }

    static public void report(String str) {
        //System.out.println("\nxxxxxxxxxxxxxxxxxxxxxx----programe error---xxxxxxxxxxxxxxxx");
        //System.out.println(" " + str);
        //System.out.println("\nnxxxxxxxxxxxxxxxxxxxxxxnxxxxxxxxxxxxxxxxxxxxxxnxxxxxxxxxxxxxxx\n");
    }

    static public void reportValidate(String str) {
        JOptionPane.showMessageDialog(MagicFrame.instance, str, "Warnning", JOptionPane.ERROR_MESSAGE);
    }

    static public void reportInformation(String str) {
        //System.out.println(str);
        InfoDialog ifdg =  InfoDialog.instance(MagicFrame.instance, InfoType.information);
       ifdg.appendMessage(str);
    }

    public static void main(String[] argv) {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            ReportInfo.reportError("���?��0��.", e);
        }
    }
}
