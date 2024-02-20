package utils;

import java.util.regex.*;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <p>Title: ���ַ�ĸ��ִ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class StringUtil {

    public StringUtil() {
    }


  

    public static String getKonggeMingling(String filename)
    {
                 // return "\""+filename+"\"";
                  return filename;
    }
    public static String conv_number(float number) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(number);
        return str;
    }
    public static final String[] STOP_WORDS = {"font", "red", "vif", "color", "colors", "span", "class", "id", "exdterm", "goterm", "nohh", "div", "href", "Javascript", "showTerm", "void", "nlp", "addforbiomedthod"};

    public static String byteToHex(byte buf) {
        int n = buf >= 0 ? buf : 256 + buf;
        String str = Integer.toHexString(n);
        return str.toUpperCase();
    }

    public static String IntToHex(int num) {
        String sResult = "";
        while (num > 0) {
            int m = num % 16;
            if (m < 10) {
                sResult = (char) ('0' + m) + sResult;
            } else {
                sResult = (char) ('A' + m - 10) + sResult;
            }
            num = num / 16;
        }
        return sResult;
    }

    public static String formatNum(int str) throws Exception {
        NumberFormat format = NumberFormat.getIntegerInstance();
        //设置数字的位数 由实际情况的最大数字决定
        format.setMinimumIntegerDigits(10);
        //是否按每三位隔开,如:1234567 将被格式化为 1,234,567。在这里选择 否
        format.setGroupingUsed(false);

        return format.format(str);
    }

    public static void rmStopWords(String str) throws Exception {
        for (int i = 0; i < STOP_WORDS.length; i++) {
            str.replaceAll(STOP_WORDS[i], "");
        }

    }

    /**ͨ��������ʽ������
     * splitByReg ��
     * @param str ��Ҫ������ַ�
     * @param regExp  ƥ���������ʽ
     * @return ������ַ�����
     */
    /*
    public static boolean valFasta(String filename) throws Exception
    {
    String str= FileUtil.readFile(filename);
    String titleFor ="^>\\w+$";   // 选择验证用的正则表达式
    String seqFor ="^[atcg]+$";   // 选择验证用的正则表达式
    String[]  arr  = str.split("\n");

    //for(i=0;i<arr.length;i++)

    for(int i=0;i<arr.length;i++)
    {
    String[] id=arr[i].split("[\\s\\|]");
    Pattern pattern = Pattern.compile(titleFor,Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(id[0]);

    if(matcher.matches() == false)
    {


    Pattern patternSeq = Pattern.compile(seqFor,Pattern.CASE_INSENSITIVE);
    Matcher matcherSeq = patternSeq.matcher(arr[i].replaceAll("\\s", ""));

    if(matcherSeq.matches() == false)
    {
    // //System.out.println(arr[i]);
    return false;
    }

    }
    }



    return true;
    }*/
    public static boolean valFasta(String filename) throws Exception {
        String titleFor = "^>\\w+$";   // 选择验证用的正则表达式
        String line = "";
        BufferedReader breader = new BufferedReader(new FileReader(filename));//header
        while ((line = breader.readLine()) != null) {
            String[] id = line.split("[\\s\\|]");

            Pattern pattern = Pattern.compile(titleFor, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(id[0]);
            if (matcher.matches() == false) {
                return false;

            } else {
                return true;
            }
        }
        return true;

    }

    public static boolean valFastaStr(String str) throws Exception {

        String titleFor = "^>\\w+$";   // 选择验证用的正则表达式
        String seqFor = "^[atcg]+$";   // 选择验证用的正则表达式
        String[] arr = str.split("\n");

        //for(i=0;i<arr.length;i++)

        for (int i = 0; i < arr.length; i++) {
            String[] id = arr[i].split("[\\s\\|]");
            Pattern pattern = Pattern.compile(titleFor, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(id[0]);

            if (matcher.matches() == false) {


                Pattern patternSeq = Pattern.compile(seqFor, Pattern.CASE_INSENSITIVE);
                Matcher matcherSeq = patternSeq.matcher(arr[i].replaceAll("\\s", ""));

                if (matcherSeq.matches() == false) {
                    // //System.out.println(arr[i]);
                    return false;
                }

            }
        }



        return true;
    }

    public static String geByReg(String regEx, String str) {

        // regEx=".+\\\\(.+)$";

        // str="c:\\dir1\\dir2\\name.txt";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        while (m.find()) {

            //System.out.println(m.group());

        }
        return m.group();

    }

    public static String AddTag(String snippet, String term) {



        snippet = snippet.replaceAll("MathType@.*?\\s", "");
        String[] termA = term.split("=1=1");



        if (termA != null) {
            /*
            if(!termA[0].equals(""))
            {
            String[] gotermA=termA[0].split("=2=2");
            if(gotermA != null)
            {
            for(int i=0;i<gotermA.length;i++)
            {
            String[] go= gotermA[i].split("===");
            String regTerm="(\\s|,|/|\\.|;|^|-|\\[|\\{|\\()("+go[0]+")(.*?[\\s-<])";
            Pattern pattern = Pattern.compile(regTerm, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(snippet);
            snippet =matcher.replaceAll("$1<div class=\"nohh\" id=\"u"+go[1]+"\" ><a href=\"Javascript:void(0);\" class=\"goterm\" onClick=\"Javascript:showTerm(this);\" num =\""+go[1]+"\">$2</a></div>&nbsp;$3" );
            }

            }

            }



            if(!termA[1].equals(""))
            {
            String[] exdtermA=termA[1].split("=2=2");
            if(exdtermA != null)
            {
            for(int i=0;i<exdtermA.length;i++)
            {

            String[] exd= exdtermA[i].split("===");
            if(!exd[0].equals("Color"))
            {
            String regTerm="(\\s|,|/|\\.|;|^|-|\\[|\\{|\\()("+exd[0]+")(.*?[\\s-<])";
            Pattern pattern = Pattern.compile(regTerm, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(snippet);
            snippet =matcher.replaceAll("$1<div class=\"nohh\" id=\"u"+exd[1]+"\" ><a href=\"Javascript:void(0);\" class=\"exdterm\" onClick=\"Javascript:showTerm(this);\" num =\""+exd[1]+"\">$2</a></div>&nbsp;$3" );
            }
            }

            }
            }
             */

            if (!termA[2].equals("")) {
                String[] protnameA = termA[2].split("===");
                if (protnameA != null) {
                    for (int i = 0; i < protnameA.length; i++) {
                        if (!protnameA[i].equals("th")) {
                            //String[] protname= protnameA[i].split("===");
                            String regTerm = "(\\s|,|/|\\.|;|^|-|\\[|\\{|\\()(" + protnameA[i].replaceAll("[()]", "") + ")(.*?[\\s-<])";
                            Pattern pattern = Pattern.compile(regTerm);
                            Matcher matcher = pattern.matcher(snippet);
                            snippet = matcher.replaceAll("$1<div class =\"nlp\" title=\"Gene Name\" >$2</div>&nbsp;$3");

                        }
                    }

                }
            }

            if (!termA[3].equals("")) {
                String[] speciesA = termA[3].split("===");
                if (speciesA != null) {
                    for (int i = 0; i < speciesA.length; i++) {
                        //String[] protname= protnameA[i].split("===");
                        String regTerm = "(\\s|,|/|\\.|;|^|-|\\[|\\{|\\()(" + speciesA[i].replaceAll("[()]", "") + ")(.*?[\\s-<])";
                        Pattern pattern = Pattern.compile(regTerm);
                        Matcher matcher = pattern.matcher(snippet);
                        snippet = matcher.replaceAll("$1<div class =\"nlp\" title=\"Species Name\" >$2</div>&nbsp;$3");
                    }

                }
            }
            termA[4] = termA[4].trim();
            if (!termA[4].equals("")) {
                String[] tissueA = termA[4].split("===");
                if (tissueA != null) {
                    for (int i = 0; i < tissueA.length; i++) {
                        //String[] protname= protnameA[i].split("===");
                        String regTerm = "(\\s|,|/|\\.|;|^|-|\\[|\\{|\\()(" + tissueA[i].replaceAll("[()]", "") + ")(.*?[\\s-<])";
                        Pattern pattern = Pattern.compile(regTerm);
                        Matcher matcher = pattern.matcher(snippet);
                        snippet = matcher.replaceAll("$1<div class =\"nlp\" title=\"Tissue Name\" >$2</div>&nbsp;$3");
                    }

                }
            }





        }




        return snippet;
    }

    public static String[][] splitByReg(String str, String regExp) {
        Pattern sp = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = sp.matcher(str);
        Vector<Vector<String>> colInoput = new Vector<Vector<String>>();
        while (matcher.find()) {
            Vector<String> v = new Vector<String>();
            for (int i = 1; i <= matcher.groupCount(); i++) {

                v.add(matcher.group(i));
            }
            colInoput.add(v);
        }
        String[][] resultList = null;
        if (colInoput.size() > 0) {
            resultList = new String[colInoput.size()][colInoput.get(0).size()];
        }
        for (int i = 0; i < colInoput.size(); i++) {

            String[] kk = new String[colInoput.get(i).size()];
            colInoput.get(i).copyInto(kk);
            resultList[i] = kk;
        }
        return resultList;
    }

    /**
     * ��������ַ���ȫת��ΪСд
     * @param inStr
     * @return
     */
    public static String toSmall(String inStr) {
        String outStr = inStr.toLowerCase();
        return outStr;
    }

    /**
     * javaʵ�ֲ���ִ�Сд�滻
     * @param source
     * @param oldstring
     * @param newstring
     * @return
     */
    public static String IgnoreCaseReplace(String source, String oldstring,
            String newstring) {
        Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        String ret = m.replaceAll(newstring);
        return ret;
    }

    /**
     * ��request���ݹ�4�Ĳ������ת�룬�Է�ֹ��������ĳ���
     * @param inStr
     * @return
     */
    public static String fromRequest(String inStr) {
        if (inStr == null) {
            //ReportInfo.debug("���棺request�к���nullֵ��");
        }
        String outStr = trimNull(inStr);
        try {
            outStr = new String(outStr.getBytes("ISO8859_1"), "GBK");
        } catch (Exception e) {
            outStr = "error!!!--StringUtil.fromRequest";
            // ReportError.reportException("����ת��GB2K->ISO8859_1 ���",e);
        }
        return outStr;
    }

    /**
     * �ַ������ݿ�ǰ��׼������
     * @param inStr �����ַ�
     * @return
     */
    public static String toDb(String inStr) {
        String outStr = inStr;
        String reStr = "";
        if (inStr == null) {
            outStr = "";
        } else {
            outStr = outStr.trim();
            outStr = outStr.replaceAll("'", "''"); //������滻��}�����

        }
        try {
            reStr = new String(outStr.getBytes("gb2312"), "latin1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reStr;
    }

    public static String generateInsertSql(String tableName, String[] columnName, String[] columnValue) {
        String sqlStr = "insert into " + tableName + "(";
        String sqlStrFoot = "";

        for (int i = 0; i < columnName.length - 1; i++) {
            sqlStr = sqlStr + toDb(columnName[i]) + ",";
            sqlStrFoot = sqlStrFoot + "'" + toDb(columnValue[i]) + "'" + ",";
        }
        return sqlStr + toDb(columnName[columnName.length - 1]) + ") values (" + sqlStrFoot + "'" + toDb(columnValue[columnValue.length - 1]) + "'" + ")";

    }

    public static String generateUpdateSql(String tableName, String[] columnName, String[] columnValue, String whereSql) {
        String sqlStr = "update " + tableName + "  set ";
        String sqlStrFoot = "";

        for (int i = 0; i < columnName.length - 1; i++) {
            sqlStr = sqlStr + toDb(columnName[i]) + "=" + "'" + toDb(columnValue[i]) + "'" + ",";
        }
        return sqlStr + toDb(columnName[columnName.length - 1]) + "=" + "'" + toDb(columnValue[columnValue.length - 1]) + "'" + " " + whereSql;

    }

    /**
     * ��null�滻Ϊ""�����ַ����ҵĿհ�ȥ��
     * @param inStr �����ַ�
     * @return
     */
    public static String trimNull(String inStr) {
        String outStr = inStr;
        if (outStr == null) {
            outStr = "";
        } else {
            outStr = outStr.trim();
        }
        return outStr;
    }

    /**
     * ���쳣��ջת��Ϊ�ַ�
     * @param e �쳣
     * @return
     */
    public static String getStackTraceString(Exception e) {
        String result = e.toString();
        StackTraceElement[] ttt = e.getStackTrace();
        for (int i = 0; i < ttt.length; i++) {
            result = result + "\r\n" + ttt[i].toString();
        }
        return result;
    }

    /**
     * �������ֵΪnull��""���յ��ַ����򷵻ء�&nbsp;��
     * @param inStr ����ֵ
     * @return
     */
    public static String revertNull(String inStr) {
        inStr = trimNull(inStr);
        if (inStr == null || inStr.equals("")) {
            return "&nbsp;";
        } else {
            return inStr;
        }
    }

    //页面input显示引号不正确
    public static String parseQuto(String sStr) {

        sStr = sStr.replaceAll("\"", "&#34;"); //输入框中显示双引号问题
        sStr = sStr.replaceAll("\'", "&#39;"); //输入框中显示单引号问题
        return sStr;
    }

    /**
     * �����ָ�ʽת��Ϊhtml���硰���з�תΪ��<br>���ȡ���������ݿ��жs���ݺ���ʾ����ҳ��ʱ��
     * @param inStr
     * @return
     */
    public static String toHtml(String inStr) {
        String result = inStr;
        if (result == null || result.equals("")) {
            result = "";
        } else {
            result = result.replaceAll("\'", "��");
            result = result.replaceAll("<", "&lt;");
            result = result.replaceAll(">", "&gt;");
            result = result.replaceAll(String.valueOf((char) 34), "&quot;");
            result = result.replaceAll("&", "&amp;");
            result = result.replaceAll(String.valueOf((char) 13), "<p>");
            result = result.replaceAll(String.valueOf((char) 9),
                    "&nbsp;&nbsp;&nbsp;&nbsp;");
            result = result.replaceAll(",", "��");
            result = result.replaceAll(String.valueOf((char) 32),
                    "&nbsp;&nbsp;");
        }
        return result;
    }

    public static String getPath() {
        return System.getProperty("user.dir");
    }

    public static void main(String[] argv) {

        String tmp = "FFSS'fdfd'fds'fdf";
        //System.out.println(toSmall(tmp));
        //System.out.println(toDb(tmp));

        tmp = null;
        //System.out.println(trimNull(tmp));

        tmp = "  dd  ";
        //System.out.println(trimNull(tmp));

        tmp = " �� �� ";
        //System.out.println(toHtml(tmp));


    }
}
