/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Algorithms;

import Magic.Units.File.Parameter.Log;
import java.util.Vector;

/**
 *
 * @author Huabin Hou
 */
public class Vertical {

    private int verticalSize = 0;
    private Vector<Integer> vector = new Vector<Integer>();
    private boolean ifInCluster = true;
    private int end_site = 0;
    private int vertical = 0;
    private int maxVertical = 0;

    public int getVerticalPosition(int start, int end) {
        ////System.out.println(first+"~"+last);
        ifInCluster = true;
        for (int j = 0; j < verticalSize; j++) {

            end_site = ((Integer) vector.elementAt(j)).intValue();
            // //System.out.println(j+": "+end_site+"   "+start);
            if (start > end_site + Log.instance().global.readsInterver) {

                vertical = j;

                vector.setElementAt(end, j);
                ifInCluster = false;
                break;
            }
        }

        if (ifInCluster == true) {
            verticalSize++;
            vertical = vector.size();
            vector.add(end);
        }

        maxVertical = Math.max(maxVertical, vertical);

    //   //System.out.println(maxVertical+" ======maxVertical====="+vertical);
        return vertical;


    }

    public int getMaxVertical() {
        return maxVertical;
    }
}
