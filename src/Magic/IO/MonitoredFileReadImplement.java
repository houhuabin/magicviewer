/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.IO;


import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.Gui.Task.TaskBase;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import utils.FileUtil;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class MonitoredFileReadImplement {

    public BufferedReader bufferedReader = null;
    public TaskBase monitor;
    public int currentFileSize = 0;
    public Reader fr;
    private final byte[] lock = new byte[0];
    private boolean destoryed=false;

    public MonitoredFileReadImplement(String filename, TaskBase monitor) throws IOException {

        this.monitor = monitor;
        File file = new File(filename);

        long fileSize = FileUtil.getFileLen(filename);

        monitor.setMax((int) fileSize);
       

        this.fr = new FileReader(file);
        this.bufferedReader = new BufferedReader(fr);
//        monitor.destory = this;
    }

    public String readLine() throws IOException {
        synchronized (lock) {
            if (destoryed) {
                return null;
                // monitor.setVisible(false);
            }
            String line = bufferedReader.readLine();
            if (line == null) {
                close();
                return null;
            }
            currentFileSize += line.length() + ForEverStatic.lineSeparator.length();
           // if (monitor != null) {
           // System.out.println("currentFileSize:"+currentFileSize);
                monitor.setValue(currentFileSize);

            //}
            return line;
        }
    }

    public void close() throws IOException {
		//monitor.doClose(ForEverStatic.RET_OK);
        bufferedReader.close();
        fr.close();
    }

    public void destroy() {
        try {
            synchronized (lock) {
                destoryed=true;
                close();
            }
        } catch (IOException ex) {
            ReportInfo.reportError(null, ex);
        }
    }
}
