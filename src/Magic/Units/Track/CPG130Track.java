/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Track;

import Magic.Units.Track.Track;
import java.awt.Color;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class CPG130Track extends Track{
    //CGI_prediction_ID	start	end	chromosome	prediction_method	length	cpgnum	gcnum	percpg	pergc	obsexp
    public String CPG130Format="id start end chrom";

    //#chrom	chromStart	chromEnd	name	length	cpgNum	gcNum	perCpg	perGc	obsExp
    public String ExtFormat="chrom start end id";
    

    public CPG130Track() {
         super();
         trackSet.COLOR = Color.ORANGE;
    }
	
 public  void getTrack(String infile,String contig) {
        try {
            Class cls = Class.forName("SeeEgg.Units.Piece");
            //this.items = getTrackItems(infile, ExtFormat, cls,contig).toArray();
        } catch (ClassNotFoundException ex) {
           ReportInfo.reportError("", ex);
        }
    }
}
