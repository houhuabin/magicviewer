/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Administrator
 */
public class FieldUtil {

      public static Float getNullFloat(String value)
    {
       if(value==null)
       {
          return null;
       }
       return Float.valueOf(value);
    }
public static Boolean getStrandValue(String value) {
        if(value==null)
        {
           return null;
        }
        if (value.equals("+")) {
            return true;
        } else {
            return false;
        }
    }

      public static Integer getNullInteger(String value)
    {
       if(value==null)
       {
          return null;
       }
       return Integer.valueOf(value);
    }

}
