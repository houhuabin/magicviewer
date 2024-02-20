/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.FTP;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.util.Date;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/**
 *
 * @author Administrator
 */
public class DuanDian {

    public static void main(String[] argv) throws Exception {
        for(int i=0;i<200;i++){
        FTPClient client = new FTPClient();
        client.connect("59.79.168.90", 21);
        client.login("houhuabin", "56403133");
        client.setSoTimeout(5000);//设置连接超时时间为5000毫秒
        // 在尝试连接以后，你应该检查返回码验证是否连接成功！
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            System.err.println("FTP 服务器拒绝连接.");
            System.exit(1);
        }
       // System.out.println("连接正在建立中......");
        if (client.isConnected()) {
           // System.out.println("连接已建立！");
            client.setControlEncoding("gb2312");// 设置编码方式，默认编码方式为：ISO8859-1
            client.changeWorkingDirectory("archives");// 改变当前ftp的工作目录
            // client.changeToParentDirectory();//回到上级目录
            FTPFile[] fs = client.listFiles(); // 得到当前工作目录下的所有文件

           // for (FTPFile ff : fs) {
               // ff.getSize();
              
               // if (ff.getName().equals("test.txt")) {
                    File file = new File("E:" + File.separator
                            + "test.txt");
                    RandomAccessFile rfile = new RandomAccessFile(file, "rw");// 随机访问文件类
                    long offset = rfile.length();
                client.setRestartOffset(1);
                   rfile.seek(0);
                   client.changeWorkingDirectory("/home/houhuabin/magicinsight/chrM/");
                    InputStream input = client.retrieveFileStream("test.txt");
                    byte[] data = new byte[1024];
                //    System.out.println(new Date() + " 数据写入中...");
                    while (input.read(data) != -1) {
                        rfile.write(data);
                    }
                //    System.out.println(new Date() + " 所有数据已接受完成！");
                    input.close();
                    rfile.close();
               // }
          //  }
        }
        client.logout();
        client.disconnect();

    }}
}
