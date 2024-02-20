/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Piece.FilePiece;
import com.google.common.collect.Lists;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMRecord;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.RNATools;
import org.biojava.bio.symbol.SymbolList;
import org.biojava.bio.symbol.SymbolListViews;
import org.biojava.bio.symbol.TranslationTable;
import org.apache.commons.jexl.*;
import org.reflections.util.Utils;
import utils.ColectionUtil;
import utils.FileUtil;
import utils.SystemUtil;

class HelloWorld {

    public static int a = 8;
    public static int b = 9;
    public static int c = a + b;

    public enum Test {

        houhuabin, zhoulingling
    };

    public static final void main(String args[]) throws Exception {
        HelloWorld hhb= new HelloWorld();
       hhb.readUrlSam();
    }

    public void readUrlSam() throws MalformedURLException
    {
     try {
        final SAMFileReader inputSam = new SAMFileReader(
                new URL("http://bioinformatics.zj.cn/magicviewer/download/test_data/chrM/chrM.sorted.bam"),new File("E:\\project\\magicinsight\\chrM\\chrM.sorted.bam.bai"),false);
            Iterator<SAMRecord> iter1 = inputSam.query("chrM", 1, 1000, false);
            ArrayList<SAMRecord> iterArrayList = new ArrayList<SAMRecord>();
            while (iter1.hasNext()) {
                System.out.println(((SAMRecord) iter1.next()).getAlignmentStart());
                 System.out.println(((SAMRecord) iter1.next()).getReadString());
            }
        } catch (ArithmeticException e) {
            //  e.printStackTrace();
        }
    }

    public static void cal() throws ArithmeticException {
        int a = 1;
        int b = 0;

        for (int i = 1; i < 100; i++) {

            System.out.println(a / b);
        }




    }

    public static boolean isSynonymous(String codon1, String codon2) {



        TranslationTable eup = RNATools.getGeneticCode(TranslationTable.EUPL_NUC);
        try {

            SymbolList seq1 = SymbolListViews.windowedSymbolList(DNATools.toRNA(DNATools.createDNA(codon1)), 3);
            SymbolList seq2 = SymbolListViews.windowedSymbolList(DNATools.toRNA(DNATools.createDNA(codon2)), 3);

            SymbolList aa1 = SymbolListViews.translate(seq1, eup);
            SymbolList aa2 = SymbolListViews.translate(seq2, eup);
            //System.out.println(aa1.seqString());
            if (aa1.seqString().equals(aa2.seqString())) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
