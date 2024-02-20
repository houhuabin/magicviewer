/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.FTP;

/**
 *
 * @author Administrator
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.samtools.util.HttpUtils;
import net.sf.samtools.util.SeekableStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author houhuabin
 */
public class SeekableFTPStream extends SeekableStream {

    private long position = 0;
    private long contentLength = -1;
  //  private final FTPClient client;
 
    private final String file;

    private final String userName;
    private final String password;
    private final String ip;
    private final int port;

    public SeekableFTPStream(final String userName, final String password, final String ip, final int port, final String file) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.file = file;
        // Try to get the file length
        //client.setRestartOffset(0);
        contentLength = getSize(file);


    }

    public long length() {
        return contentLength;
    }

    public  FTPClient getFTPClient() {
        FTPClient client = null;
        try {
            client = new FTPClient();
            client.connect(ip, port);
            client.login(userName, password);
            client.setSoTimeout(5000);//设置连接超时时间为5000毫秒
            // 在尝试连接以后，你应该检查返回码验证是否连接成功！
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                System.err.println("FTP sever refused connect.");
                System.exit(1);
            }
            System.out.println("connection......");
        } catch (Exception ignored) {
            System.err.println("Unable to connected to the sever ");
        }
        return client;
    }

    public long getSize(String file) {
        try {
            FTPClient client=getFTPClient();
            String fileName = file.substring(file.lastIndexOf("/") + 1);
            String path = file.substring(0, file.lastIndexOf("/") + 1);
            client.changeWorkingDirectory(path);// 改变当前ftp的工作目录
            // client.changeToParentDirectory();//回到上级目录
            FTPFile[] fs = client.listFiles(); // 得到当前工作目录下的所有文件

            for (FTPFile ff : fs) {
                ff.getSize();
                if (ff.getName().equals(fileName)) {
                    return ff.getSize();

                }
            }
        } catch (Exception ignored) {
            System.out.println("WARNING: Invalid content length (" + contentLength + "  for: " + file);
            return -1;
        }
        return -1;
    }

    public boolean eof() throws IOException {
        return position >= contentLength;
    }

    public void seek(final long position) {
        this.position = position;
    }

    public int read(byte[] buffer, int offset, int len) throws IOException {

        if (offset < 0 || len < 0 || (offset + len) > buffer.length) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return 0;
        }


      FTPClient client=getFTPClient();
        InputStream     input = null;
        int n = 0;
        try {

            // long endRange = position + len - 1;
            // IF we know the total content length, limit the end range to that.
            if (contentLength > 0) {
                len = (int) Math.min(len, contentLength - offset + 1);
            }

            client.setRestartOffset(offset);

             input = client.retrieveFileStream(file);

            while (n < len) {
                int count = input.read(buffer, offset + n, len - n);
                if (count < 0) {
                    if (n == 0) {
                        return -1;
                    } else {
                        break;
                    }
                }
                n += count;
            }

            position += n;

            return n;

        } catch (IOException e) {
            // THis is a bit of a hack, but its not clear how else to handle this.  If a byte range is specified
            // that goes past the end of the file the response code will be 416.  The MAC os translates this to
            // an IOException with the 416 code in the message.  Windows translates the error to an EOFException.
            //
            //  The BAM file iterator  uses the return value to detect end of file (specifically looks for n == 0).
            if (e.getMessage().contains("416") || (e instanceof EOFException)) {
                if (n < 0) {
                    return -1;
                } else {
                    position += n;
                    // As we are at EOF, the contentLength and position are by definition =
                    contentLength = position;
                    return n;
                }
            } else {
                throw e;
            }

        } finally {
            if (input != null) {
                input.close();
            }
            if (client != null) {
                client.logout();
                client.disconnect();
            }
        }
    }

    public void close() throws IOException {
        // Nothing to do
    }

    public int read() throws IOException {
        throw new UnsupportedOperationException("read() not support for SeekableHTTPStreams");
    }

  
    public byte[] readBytes(final long position, final int nBytes) throws IOException {
        this.position = position;
        final byte[] buffer = new byte[nBytes];
        final int bytesRead = read(buffer, 0, nBytes);
        if (bytesRead != nBytes) {
            throw new EOFException("Trying to read " + nBytes + " from " + file + " at position " + position +
            ", but only read " + bytesRead + " bytes.");
        }
        return buffer;
    }

    @Override
    public String getSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
