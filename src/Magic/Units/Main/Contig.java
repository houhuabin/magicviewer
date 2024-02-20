/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Main;

import Magic.Units.Main.BriefM8Line;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author QIJ
 */
public class Contig {
    public String name="";
    public int index=-1;
    //public int length=0;
    public char[] sequence;
    public int simu_start=0;
    public int simu_end=0;
    public int next_contig=-1;
    public int next_dist=0;
    public boolean strand=true;
    public Vector<BriefM8Line> hits=null;
}
