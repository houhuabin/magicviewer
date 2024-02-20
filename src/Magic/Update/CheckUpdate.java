/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Update;

import javax.swing.*;
import java.net.*;
import java.io.*;
import utils.swing.SwingUtil;

public class CheckUpdate {

    public static String localVersion = "";
    public static String netVersion = "";

    public CheckUpdate() {

        //启动更新线程
        new Check().start();
    }

    private class Check extends Thread {

        private boolean isUpdated = false;
        // String netVersion;
        String LocalVerFileName = "property" + System.getProperties().getProperty("file.separator") + "ver.txt";

        public void run() {
            //更新文件版本标识URL
            String versionUrl = "http://bioinformatics.zj.cn/magicviewer/AutoUpdate/ver";

            /**//*
            这里是通过HTTP访问一个页面,以取得网络上的版本号
            比如这里就是在这个页面直接打印出 6.19.1.1
            然后把这个版本号比对本地的版本号,如果版本号不同的话,就从网络上下载新的程序并覆盖现有程序

             */

            URL url = null;
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader netVer = null;

            //读取网络上的版本号
            try {
                url = new URL(versionUrl);
                is = url.openStream();
                isr = new InputStreamReader(is);

                netVer = new BufferedReader(isr);
                netVersion = netVer.readLine();
                localVersion = getNowVer();

                if (netVersion.equals(localVersion)) {
                    isUpdated = false;
                } else {
                    isUpdated = true;
                }

            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            } finally {
                //释放资源
                try {
                    netVer.close();
                    isr.close();
                    is.close();
                } catch (IOException ex1) {
                }
            }

            //如果版本不同,下载网络上的文件,更新本地文件
            if (isUpdated) {
                Object[] options = {"update now", "update late", "don't show this again"};

                int response = JOptionPane.showOptionDialog(null, " New version is now available  select update option", "Need Update", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if (response == 0) {
                    SwingUtil.openURL("http://bioinformatics.zj.cn/magicviewer/download.php");
                } else if (response == 1) {
                  //  JOptionPane.showMessageDialog(null, "您按下了存款按钮");
                } else if (response == 2) {
                   // JOptionPane.showMessageDialog(null, "您按下了取款按钮");
                }


            }

        }

        private String getNowVer() {
            //本地版本文件
            File verFile = new File(LocalVerFileName);

            FileReader is = null;
            BufferedReader br = null;

            //读取本地版本
            try {
                is = new FileReader(verFile);

                br = new BufferedReader(is);
                String ver = br.readLine();

                return ver;
            } catch (FileNotFoundException ex) {
//                msg.append("本地版本文件未找到\n");
            } catch (IOException ex) {
                // msg.append("本地版本文件读取错误\n");
            } finally {
                //释放资源
                try {
                    br.close();
                    is.close();
                } catch (IOException ex1) {
                }
            }
            return "";
        }
    }

    public static void main(String[] args) {
        CheckUpdate checkupdate = new CheckUpdate();

    }
}
