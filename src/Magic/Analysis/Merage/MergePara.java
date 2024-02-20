/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Merage;

import Magic.Units.File.FastaFile;
import Magic.Units.Track.Track;
import java.util.HashMap;
import java.util.Vector;
import org.biojava.bio.symbol.TranslationTable;

/**
 *
 * @author Huabin Hou
 */
public class MergePara {

    public enum CorrelatePolicy {

        Overlap, Contain;
    }

    public enum OutputPolicy {

        Pooled, AutoSeparate;
    }

    public enum MutimatchPolicy {

        Random, All,Priority;
    }

    public enum SNPType {
          readthrough,nonsense, missense, synonymous,spliceSite, UTR3, UTR5,intron,intergenic;
    }

    /*public enum SpeicesType {
        prokaryotic, eukaryotic;
    }
    */
    public static CorrelatePolicy currentCorrelatePolicy = CorrelatePolicy.Contain;
    public static OutputPolicy currentOutputPolicy = OutputPolicy.AutoSeparate;
    public static MutimatchPolicy currentMutimatchPolicy = MutimatchPolicy.Random;
    //public static SpeicesType speicesType = SpeicesType.eukaryotic;
    public static Vector<SNPType> snpTypes = new Vector<SNPType>();
    //public static String clusterString;
    public static String translationTable = "EUPLOTID_NUCLEAR";
    //public static HashMap<Track,Boolean[]> fieldSelect = new HashMap<Track,Boolean[]>();
    public static String outputFile;
    public static FastaFile reference;
}
