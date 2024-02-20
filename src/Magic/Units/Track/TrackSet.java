/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Track;

import Magic.Units.File.Parameter.LogAbstract;
import java.awt.Color;
import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.util.ArrayList;

/**
 *
 * @author Huabin Hou
 */
public class TrackSet extends LogAbstract implements Serializable{
    public String name;
    public String filename;
    public String format;
    

    public int pieceHeight;
    public int pieceInterval;
    public int trackHeight;

    public int maxVertical;
    public Color COLOR;
    //public ArrayList<Field> selectField;
    //public  ArrayList<String> selectField;
}
