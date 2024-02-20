/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.Gui.Task.TaskBase;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Huabin Hou
 */
public class SystemUtil {

    public static Properties systemProperties = System.getProperties();
//    public static UserProperties userProperties = new UserProperties();

    public static void printCurrentTime(String str) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");//时:分:秒:毫秒
        System.out.println(str + ":   " + sdf.format(d));
    }

    public static void exec(String[] cmd, TaskBase monitor, String outputFile, boolean wait) throws Exception {

        try {
            //System.out.println(cmd + "        --------------------");
            //  String[] cmda = new String[]{"cmd.exe ", "/c", cmd};
            //  final  Process proc = Runtime.getRuntime().exec(cmda);
            /*String[] cmdArray = cmd.split("\\s+");
            for (int i = 0; i < cmdArray.length; i++) {
            cmdArray[i] = cmdArray[i].replace("\"", "");
            }*/

            /* for (int i = 0; i < cmd.length; i++) {
            System.out.print(cmd[i]);
            System.out.print(" ");
            }
            System.out.println();*/
            final Process proc = Runtime.getRuntime().exec(cmd);
            if (wait) {
                proc.waitFor();
            }
            new Thread(new Runnable() {

                public void run() {
                    try {
                        InputStream stderr = proc.getErrorStream();
                        InputStreamReader isr = new InputStreamReader(stderr);
                        BufferedReader br = new BufferedReader(isr);
                        while (br.readLine() != null) {
                        }
                        stderr.close();
                        isr.close();
                        br.close();
                    } catch (IOException ex) {
                        ReportInfo.reportError("", ex);
                    }
                }
            }).start();

            if (outputFile == null || outputFile.equals("")) {
                return;
            }

            InputStream stdin = proc.getInputStream(); //*** Standard output of external command
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);

            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);


            String line = "";
            int currentFileSize = 0;
            while ((line = br.readLine()) != null) {

                if (monitor != null) {
                    currentFileSize += line.length() + ForEverStatic.lineSeparator.length();
                    monitor.setValue(currentFileSize);
                    if (!monitor.okToRun()) {
                        proc.destroy();
                    }
                }
                bw.write(line + "\n");
            }
            stdin.close();
            isr.close();
            br.close();

            bw.close();
            fw.close();

        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }

    }

    public enum SystemType {

        windows, linux, mac
    };

    public static SystemType getSystemType() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("windows") > -1) {
            return SystemType.windows;
        } else if (OS.indexOf("linux") > -1 || OS.indexOf("aix") > -1 || OS.indexOf("unix") > -1) {
            return SystemType.linux;
        } else {
            return SystemType.mac;
        }
    }

    public static boolean isMac() {
        if (getSystemType() == SystemType.mac) {
            return true;
        }
        return false;
    }

       public static boolean isWindows() {

        if (getSystemType() == SystemType.windows) {
            return true;
        }
        return false;
    }

    public static void printArray(String[] array) {
        for (String element : array) {
            System.out.print(element + "  ");
        }
        System.out.println();
    }

 

    public static Map getEnv() {
        Map map = new HashMap();
        String OS = System.getProperty("os.name").toLowerCase();

        Process p = null;
        try {
            if (OS.indexOf("windows") > -1) {
                p = Runtime.getRuntime().exec("cmd /c set");
            } else if (OS.indexOf("linux") > -1 || OS.indexOf("aix") > -1 || OS.indexOf("unix") > -1) {
                p = Runtime.getRuntime().exec("/bin/sh -c set"); // Unix系列
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    String[] str = line.split("=");
                    map.put(str[0], str[1]);
                }

            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return map;
    }

    public static void addLibClassPath() {
        File[] tracks = new File("lib").listFiles();
        for (int i = 0; i < tracks.length; i++) {
            //   System.out.println(tracks[i].getAbsolutePath());
            SystemUtil.setClassPath(tracks[i].getAbsolutePath());
        }
    }

    public static void setClassPath(String newClassPath) {

        systemProperties.setProperty("java.class.path", getClassPath() + ';' + newClassPath);

        //System.out.println( System.getProperty("user.dir")+"++++++++++++user.dir+++++++++++");
        // System.out.println(SystemUtil.getClassPath() + "------ gatk cmd---------");
    }

    public static boolean checkUpdate() {
        //System.out.println(userProperties.getProperty("checkUpdate"));
        //  if(userProperties.getProperty("checkUpdate").equals("true"))
        {
            // return true;
        }
        return false;
    }

    public static String getClassPath() {

        return systemProperties.getProperty("java.class.path", null);
    }

    public static void main(String[] args) throws Exception {

        /* String command1 = "  external\\bin\\perl\\bin\\perl external\\bin\\soap2sam.pl E:\\out\\chrS.soap   ";//convert
        String command2 = " external\\bin\\samtools faidx   E:\\out\\chrS.fa ";
        String command3 = " external\\bin\\samtools view   -b -h -S -T E:\\out\\chrS.fa -t E:\\out\\chrS.fa.fai E:\\out\\chrS.soap.sam -o E:\\out\\chrS.soap.bam ";
        String command4 = " external\\bin\\samtools sort  E:\\out\\chrS.soap.bam  E:\\out\\chrS.soap.sorted   ";//sort
        String command5 = " external\\bin\\samtools index  E:\\out\\chrS.soap.sorted.bam";*/
        //String command = "external\\bin\\perl\\bin\\perl --help";

        /* exec(command1, null, "E:\\out\\chrS.soap.sam", false);
        exec(command2, null, null, true);
        exec(command3, null, null, true);
        exec(command4, null, null, true);
        exec(command5, null, null, true);*/

        /* String outFile = "E:\\out\\chrS.soap";
        String outFileFormat = KeyWords.SOAP2;
        String refFile = "E:\\out\\chrS.fa";
        SamViewUtil.alignment2SAM(outFile, outFileFormat, null);
        SamViewUtil.indexRef(refFile, null);
        SamViewUtil.sam2Bam(outFile + ".sam", refFile, null);
        SamViewUtil.sort(outFile + ".bam", null);
        SamViewUtil.index(outFile + ".sorted.bam", null);*/
        Properties p = System.getProperties();
        p.list(System.out);



    }
}
