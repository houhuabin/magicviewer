/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class ColectionUtil {
   public static ArrayList<String> getString(Object[] snp )
   {
       ArrayList<String> result =new ArrayList<String>();
       for(Object obj:snp)
       {
           result.add(obj.toString());
       }

       return result;

   }
   

}
