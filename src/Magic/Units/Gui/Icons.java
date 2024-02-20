/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui;

import java.util.HashMap;
import javax.swing.ImageIcon;

public class Icons {

    private static String pth;
    private static String ext;
    private static HashMap<String, ImageIcon> hashtable = new HashMap();

    public static void initialize(String paramString1, String paramString2) {
        pth = paramString1;
        ext = paramString2;
    }

    public static ImageIcon getIcon(String paramString) {
        ImageIcon localImageIcon = (ImageIcon) hashtable.get(paramString);
        if (localImageIcon == null) {
            Icons localIcons = new Icons();
            Class localClass = localIcons.getClass();
            String str = paramString.toLowerCase() + ext;
            localImageIcon = new ImageIcon(localClass.getResource(pth + "/" + str));
            hashtable.put(paramString, localImageIcon);
        }
        return localImageIcon;
    }
}
