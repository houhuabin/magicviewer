/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.IO;

import Magic.Units.Track.Track;
import java.util.ArrayList;
import utils.SamViewUtil.Insert;

/**
 *
 * @author Huabin Hou
 */
public class SeqData {
    public String reference = null;
    public ArrayList<SeqPanelData> seqPanelDatas= new ArrayList<SeqPanelData>();
    //public String extendReference = null;
    public String methyRefSeq = null;
    public int windowStart = 0;
    public int windowEnd = 0;

    public int refSub = 0;
    public int refSeqStart = 0;//reference 的起始为其覆盖的read的最小起始
    public int refEnd = 0;
    public ArrayList<Insert> insertPoint;
}
