/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Huabin Hou
 */

/*
Dict.整数 = /^\d+$/ ;
Dict.中文字符 = /[\u4E00-\u9FA5]/g ;
Dict.email = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;　　//email地址
Dict.非负整数 = /^\d+$/;　　//非负整数（正整数 + 0）
Dict.正整数 = /^[0-9]*[1-9][0-9]*$/;　　//正整数
Dict.负整数 = /^-[0-9]*[1-9][0-9]*$/;　　//负整数
Dict.非负浮点数 = /^\d+(\.\d+)?$/;　　//非负浮点数（正浮点数 + 0）
Dict.正浮点数 = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;　　//正浮点数
Dict.非正浮点数 = /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/;　　//非正浮点数（负浮点数 + 0）
Dict.负浮点数 = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/;　　//负浮点数
Dict.浮点数 = /^(-?\d+)(\.\d+)?$/;　　//浮点数
Dict.英文 = /^[A-Za-z]+$/;　　//由26个英文字母组成的字符串
Dict.大写英文 = /^[A-Z]+$/;　　//由26个英文字母的大写组成的字符串
Dict.小写英文 = /^[a-z]+$/;　　//由26个英文字母的小写组成的字符串
Dict.数字和英文 = /^[A-Za-z0-9]+$/;　　//由数字和26个英文字母组成的字符串
Dict.数字和英文和下划线 = /^\w+$/;　　//由数字、26个英文字母或者下划线组成的字符串
Dict.日期=/^(\d{4})\-(\d{2})\-(\d{2}) $/ ; //yyyy-mm-dd的日期
Dict.时间=/^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/ ; //yyyy-mm-dd hh:mm:ss 的时间
/^((((1|\d)\d{2})-(0?|1)-(0?|\d|3))|(((1|\d)\d{2})-(0?|1)-(0?|\d|30))|(((1|\d)\d{2})-0?2-(0?|1\d|2))|(((1|\d)(0||)|((16||)00))-0?2-29-)) (([0-1][0-9])|(2[0-4])):([0-6][0-9])$/

 */
public class FormatCheck {

    public static boolean isRegion(String str) {
        if (str == null) {
            return true;
        }
        str.trim();
        if (str.equals("")) {
            return true;
        }
        String[] small = str.split(":");

        if (small.length != 2) {
            //System.out.println("small.length ");
            return false;
        } else {
            if(!ForMagic.ifChromInProject(small[0]))
            {
               // System.out.println(small[0]);
                return false;
            }
            String[] nums = small[1].split("-");
            if (nums.length != 2) {
                //System.out.println("small.length ");
                return false;
            }
            if ((!isPositiveInteger(nums[0])) || (!isPositiveInteger(nums[1]))) {
                //System.out.println("isPositiveInteger ");
                return false;
            }
        }
        return true;

    }

    public static boolean isFileName(String filename) {
        // Pattern p = Pattern.compile("([0-9A-Za-z/+_=\\-,.\\/:])+");//没考虑中文路径
        // Matcher m = p.matcher(filename);
        // return m.matches();
        return true;
    }

    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        boolean result = false;
        Pattern pattern = Pattern.compile("(-?\\d+)(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            pattern = Pattern.compile("(-?\\d+)e(-?\\d+)");
            isNum = pattern.matcher(str);
            if (isNum.matches()) {
                return true;
            }

        }
        return result;


    }

    public static boolean isPositiveInteger(String str) {
        if (str == null || str.equals("")) {
          //  System.out.println("null ");
            return false;
        }
        //System.out.println(str);
        Pattern pattern = Pattern.compile("[0-9]*[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            //
            //System.out.println("not match");
            return false;
        }
        return true;

    }

    public static boolean isStrand(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("-") || str.equals("+")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLocation(String str) {

        if (str == null) {
            return false;
        }
        // System.out.println(str);
        String[] location = str.split("\\.\\.");
        {

            if (location.length != 2) {
                // System.out.println(location.length);
                return false;
            }
            if (!isNumber(location[0]) || !isNumber(location[1])) {
                //System.out.println("number");
                return false;
            }
        }
        return true;

    }

    public static boolean isProtean(String str) {
        if (str == null) {
            return false;
        }
        //not implemented
        return true;
    }

    public static boolean isNotNull(Object obj) {
        if (obj == null) {
            //System.out.println("-----------check null----------");
            return false;
        } else if (obj instanceof String) {
            if (((String) obj).trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSequence(String str) {
        if (str == null) {
            return false;
        }
        //MKYRWSDBHV
        Pattern pattern = Pattern.compile("[ATCGNatcgn]*");
        Matcher isSequance = pattern.matcher(str);
        if (!isSequance.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isRGB(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("0")) {
            return true;
        }
        String[] temp = str.split(",");
        if (temp.length != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!isNumber(temp[i])) {
                return false;
            } else if (Integer.parseInt(temp[i]) < 0 || Integer.parseInt(temp[i]) > 255) {
                return false;
            }
        }
        return true;

    }

    public static boolean isBlock(String str) {
        if (str == null) {
            return false;
        }
        String[] temp = str.split(",");

        for (int i = 0; i < temp.length; i++) {
            if (!isNumber(temp[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static String rangeSeparator = ",";

    public static String getCompareType(String value, String filterValue) {
        if (isNumber(value)) {
            if (isNumber(filterValue)) {
                return "Number";
            } else {
                String[] temp = filterValue.split(rangeSeparator);
                if (temp.length == 2) {
                    if (isNumber(temp[0]) && isNumber(temp[1])) {
                        return "Number";
                    }
                }
            }
        }
        return "String";

    }

    public static void main(String args[]) {
        String a = " ";
        String b = "aaaa";
        //System.out.println(isNotNull(a));
        //System.out.println(isSequence(b));

    }
}
