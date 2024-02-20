/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File.Parameter;

import Magic.Units.Color.ColorRep;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Huabin Hou
 */
public class AnnoPara implements Serializable {

    public int ZOOM = 0;
    public String VIEW_MODE = StringRep.NONOVERLAP;
    public String ARROW_MODE = StringRep.SHOW_ARROW;
    //public int READS_HIGHLIGHT_LEFT = 0;
   // public int READS_HIGHLIGHT_RIGHT = 0;
    public boolean showTag = true;
    public Color nullSnpColor = ColorRep.cadetblue;
    public Color readthroughSnpColor = ColorRep.red;
    public Color nonsenseSnpColor = ColorRep.red;
    public Color missenseSnpColor = ColorRep.red;
    public Color synonymousSnpColor = ColorRep.pink;
    
    public Color spliceSiteSnpColor = ColorRep.aquamarine;
    public Color intronSnpColor = ColorRep.warblue;
    public Color UTR5SnpColor = ColorRep.warblue;
    public Color UTR3SnpColor = ColorRep.warblue;
    public Color intergenicSnpColor = ColorRep.green;
}
