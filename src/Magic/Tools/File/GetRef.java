/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Tools.File;

import Magic.Units.File.FastaFile;
import utils.SequanceUtil;

/**
 *
 * @author Administrator
 */
public class GetRef {

    public static void main(String argvs[])
    {

    FastaFile ff= new FastaFile("E:\\project\\magicinsight\\medip\\human_hg18.fa","chrY");
    String seq=ff.getFastaSeq(30852320, 30852475, "chr4");
    System.out.println(SequanceUtil.reverseComplement(seq).replace("C", "T"));

    }

}
