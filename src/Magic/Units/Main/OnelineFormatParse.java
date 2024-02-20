/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Main;

import Magic.Units.File.FileFormat.PieceField;
import java.util.HashMap;

/**
 *
 * @author Huabin Hou
 */
public class OnelineFormatParse {

    public String formatStr;

    public String getFormatStr() {
        return formatStr;
    }

    public HashMap getOnlineFormat() {
        return onlineFormat;
    }
    public HashMap onlineFormat = new HashMap();

    public OnelineFormatParse(PieceField[] fieldArray) {
     
        for (int i = 0; i < fieldArray.length; i++) {
           
                onlineFormat.put(i, fieldArray[i]);
            
        }

    }
}
