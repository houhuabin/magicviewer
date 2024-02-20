package utils;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import java.io.*;
import java.net.*;
import java.util.*;
//import javax.swing.filechooser.*;
//import org.jr.swing.filter.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * �����з�װһЩ���õ��ļ�������
 * ���з������Ǿ�̬����������Ҫ��ɴ����ʵ��
 * Ϊ������ɴ����ʵ���췽��������Ϊprivate���͵ġ�
 * @since  0.1
 */
public class FileUtil {

    /**
     * ˽�й��췽������ֹ���ʵ����Ϊ�����಻��Ҫʵ��
     */
    private FileUtil() {
    }

    /**
     * �ж�ָ�����ļ��Ƿ���ڡ�
     * @param fileName Ҫ�жϵ��ļ����ļ���
     * @return ����ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * ����ָ����Ŀ¼��
     * ���ָ����Ŀ¼�ĸ�Ŀ¼�������򴴽���Ŀ¼����������Ҫ�ĸ�Ŀ¼��
     * <b>ע�⣺���ܻ��ڷ���false��ʱ�򴴽����ָ�Ŀ¼��</b>
     * @param file Ҫ������Ŀ¼
     * @return ��ȫ�����ɹ�ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * ����ָ����Ŀ¼��
     * ���ָ����Ŀ¼�ĸ�Ŀ¼�������򴴽���Ŀ¼����������Ҫ�ĸ�Ŀ¼��
     * <b>ע�⣺���ܻ��ڷ���false��ʱ�򴴽����ָ�Ŀ¼��</b>
     * @param fileName Ҫ������Ŀ¼��Ŀ¼��
     * @return ��ȫ�����ɹ�ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean makeDirectory(String fileName) {
        File file = new File(fileName);
        return makeDirectory(file);
    }

    /**
     * ���ָ��Ŀ¼�е��ļ���
     * ���������������ɾ�����е��ļ�������ֻҪ��һ���ļ�û�б�ɾ��᷵��false��
     * �����������������ɾ�����ɾ����Ŀ¼�������ݡ�
     * @param directory Ҫ��յ�Ŀ¼
     * @return Ŀ¼�µ������ļ������ɹ�ɾ��ʱ����true�����򷵻�false.
     * @since  0.1
     */
    public static boolean emptyDirectory(File directory) {
        boolean result = false;
        File[] tracks = directory.listFiles();
        for (int i = 0; i < tracks.length; i++) {
            if (!tracks[i].delete()) {
                result = false;
            }
        }
        return true;
    }

    /**
     * ���ָ��Ŀ¼�е��ļ���
     * ���������������ɾ�����е��ļ�������ֻҪ��һ���ļ�û�б�ɾ��᷵��false��
     * �����������������ɾ�����ɾ����Ŀ¼�������ݡ�
     * @param directoryName Ҫ��յ�Ŀ¼��Ŀ¼��
     * @return Ŀ¼�µ������ļ������ɹ�ɾ��ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean emptyDirectory(String directoryName) {
        File dir = new File(directoryName);
        return emptyDirectory(dir);
    }

    /**
     * ɾ��ָ��Ŀ¼�����е��������ݡ�
     * @param dirName Ҫɾ���Ŀ¼��Ŀ¼��
     * @return ɾ��ɹ�ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean deleteDirectory(String dirName) {
        return deleteDirectory(new File(dirName));
    }

    /**
     * ɾ��ָ��Ŀ¼�����е��������ݡ�
     * @param dir Ҫɾ���Ŀ¼
     * @return ɾ��ɹ�ʱ����true�����򷵻�false��
     * @since  0.1
     */
    public static boolean deleteDirectory(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir +
                    " is not a directory. ");
        }

        File[] tracks = dir.listFiles();
        int sz = tracks.length;

        for (int i = 0; i < sz; i++) {
            if (tracks[i].isDirectory()) {
                if (!deleteDirectory(tracks[i])) {
                    return false;
                }
            } else {
                if (!tracks[i].delete()) {
                    return false;
                }
            }
        }

        if (!dir.delete()) {
            return false;
        }
        return true;
    }

    public static String getFileName(String absoluteName) {
        File file = new File(absoluteName);
        return file.getName();
    }

    public static String insertSuffix(String absoluteName, String suffix) {
        String noTypePart = getNoTypePart(absoluteName);
        if (noTypePart.equals(absoluteName)) {
            return noTypePart + suffix;
        } else {
            return noTypePart + suffix + "." + getTypePart(absoluteName);
        }

    }

    public static String getParentPath(String filePath) {
        File file = new File(filePath);
        return file.getParent();
    }

    public static String getFilePath(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }

    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    public static String getNoTypePart(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    public static String getFileType(File file) {
        return getTypePart(file.getName());
    }

    /**
     * �õ��ļ������ֲ��֡�
     * ʵ���Ͼ���·���е����һ��·���ָ����Ĳ��֡�
     * @param fileName �ļ���
     * @return �ļ����е����ֲ���
     * @since  0.5
     */
    public static String getNamePart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                } else {
                    return fileName.substring(0, point);
                }
            } else {
                return fileName.substring(secondPoint + 1, point);
            }
        } else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * �õ��ļ����еĸ�·�����֡�
     * ������·���ָ�����Ч��
     * ������ʱ����""��
     * ����ļ�������·���ָ����β���򲻿��Ǹ÷ָ�������"/path/"����""��
     * @param fileName �ļ���
     * @return ��·���������ڻ����Ѿ��Ǹ�Ŀ¼ʱ����""
     * @since  0.5
     */
    public static String getPathPart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return "";
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                return "";
            } else {
                return fileName.substring(0, secondPoint);
            }
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * �õ�·���ָ������ļ�·���������ֵ�λ�á�
     * ����DOS����UNIX���ķָ�����ԡ�
     * @param fileName �ļ�·��
     * @return ·���ָ�����·���������ֵ�λ�ã�û�г���ʱ����-1��
     * @since  0.5
     */
    public static int getPathLsatIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }

    /**
     * �õ�·���ָ������ļ�·����ָ��λ��ǰ�����ֵ�λ�á�
     * ����DOS����UNIX���ķָ�����ԡ�
     * @param fileName �ļ�·��
     * @param fromIndex ��ʼ���ҵ�λ��
     * @return ·���ָ�����·����ָ��λ��ǰ�����ֵ�λ�ã�û�г���ʱ����-1��
     * @since  0.5
     */
    public static int getPathLsatIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /*
    /**
     * �õ����·����
     * �ļ�����Ŀ¼����ӽڵ�ʱ�����ļ���
     * @param pathName Ŀ¼��
     * @param fileName �ļ���
     * @return �õ��ļ��������Ŀ¼������·����Ŀ¼�²����ڸ��ļ�ʱ�����ļ���
     * @since  0.5
     *//*
    public static String getSubpath(String pathName, String fileName) {
    int index = fileName.indexOf(pathName);
    if (index != -1) {
    return fileName.substring(index + pathName.length() + 1);
    } else {
    return fileName;
    }
    }*/

    /*
    static public void writeToFile(String str, String filename) {


    try {
    FileWriter fw = new FileWriter(filename, false);
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(str);

    bw.close();
    fw.close();
    }//try
    catch (Exception e) {
    ReportInfo.reportException("writeToFile", e);
    }//catch
    }//appendToFile*/
    static public void appendToFile(String str, String filename) {

        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(str);

            bw.close();
            fw.close();
        }//try
        catch (Exception e) {
            ReportInfo.reportError("writeToFile", e);
        }//catch
    }//appendToFile

    /* public void prependToFile(String str, String filename) {
    String newStr;//final   string   to   be   written
    try {
    newStr = str + readFile(filename);
    writeFile(newStr, filename);
    }//try
    catch (Exception e) {
    //System.out.println(e.getMessage());
    }//catch
    }//prependToFile*/
    public String readFile(String filename) throws Exception {
        StringBuffer buf;//the   intermediary,   mutable   buffer
        BufferedReader breader;//reader   for   the   template   files
        try {
            breader = new BufferedReader(new FileReader(filename));//header
            buf = new StringBuffer();
            while (breader.ready()) {
                buf.append((char) breader.read());

            }
            breader.close();
        }//try   
        catch (Exception e) {
            throw e;
        }//catch   
        return buf.toString();
    }//readFile

    public List readFileToList(String filename) throws Exception {
        BufferedReader breader;//reader   for   the   template   files
        List list;//target   vector
        String line;//line   from   file
        list = new ArrayList();
        try {
            breader = new BufferedReader(new FileReader(filename));//header
            while ((line = breader.readLine()) != null) {
                list.add(line);

            }
            breader.close();
        }//try   
        catch (Exception e) {
            throw e;
        }//catch   
        return list;
    }//readFileToVector

    /* public static void writeFile(String str, String filename) {
    BufferedWriter bwriter;//writer   to   the   file
    String fullfilepath;//path   for   the   output   file
    try {
    bwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
    bwriter.write(str);
    bwriter.flush();
    bwriter.close();
    }//try
    catch (Exception e) {
    e.printStackTrace();
    }//catch
    }//writeFile*/
    public void copyFile(String sourcename, String targetname) throws Exception {
        BufferedReader breader;//reader   from   source
        BufferedWriter bwriter;//writer   to   target
        try {
            breader = new BufferedReader(new FileReader(sourcename));
            bwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetname)));
            while (breader.ready()) {
                bwriter.write(breader.read());

            }
            breader.close();
            bwriter.close();
        }//try   
        catch (Exception e) {
            throw e;
        }//catch   
    }//copyFile

    public static void clearFile(String fileName) {

        try {
            OutputStream a = new FileOutputStream(fileName, false);
            a.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //更改文件名
    public static void changeName(String oldFileString, String newFileString) {
        File newFile = new File(newFileString),
                oldFile = new File(oldFileString);
        oldFile.renameTo(newFile);
    }

    //用一个新的文件替换原有文件
    public static void replaceFile(String oldFileString, String newFileString) {
        deleteFile(oldFileString);
        changeName(newFileString, oldFileString);

    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public Properties loadProperties(String propsFile) throws Exception {
        FileInputStream instream;//the   input   stream
        Properties props;//props   object
        instream = null;
        props = new Properties();
        try {
            instream = new FileInputStream(propsFile);
            props.load(instream);
        }//try   
        finally {
            try {
                instream.close();
            }//try
            catch (Exception e1) {
            }
        }//finally   
        return props;
    }//loadProperties

    public static long getFileLen(String filename) {
        long fileSize = 0;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            fileSize = fis.available();
            fis.close();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return fileSize;
    }


    public static String getRelativePath(String file,String logfile)
    {
        String path = new File(logfile).getParent();
        String resultPath=file.replace(path, "");
        return resultPath;
    }


     private String getFilename(String log_path, String ori_name) {
   String name = ori_name;
    if (name.indexOf(ForEverStatic.FILE_SEPARATOR) < 0) name = log_path +ForEverStatic.FILE_SEPARATOR + name;
     return name;
  }

    public static void main(String[] argvs)
    {
       /*String logfile = "E:\\temp\\PROJECT.log";
       String file ="E:\\temp\\display\\genome.bed";*/
//       System.out.println(getRelativePath(file,logfile));

    }
}
