/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File;

import Magic.Units.Gui.Task.TaskBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import net.sf.picard.reference.IndexedFastaSequenceFile;
import utils.FileUtil;
import utils.ReportInfo;
import utils.SamViewUtil;

/**
 *
 * @author lenovo
 */
public class FastaFile implements Serializable {

    public int currentContigLen = 0;
    public String currentContigName = "";
    public String refile;
    //  public  IndexedFastaSequenceFile fab;
    //private IndexedFastaSequenceFile fab;

    public FastaFile(String refile, String currentContigName) {

        this.refile = refile;
        //   System.out.println(refile+"--------refile------------------");
        this.currentContigName = currentContigName;

        try {
            //   generateDicAndIndex(refile, monitor);
            final File f = new File(this.refile);
            //   System.out.println(f.getAbsolutePath() + ".fai"+"-------------");
            IndexedFastaSequenceFile fab = new IndexedFastaSequenceFile(f);

            if (this.currentContigName == null) {
                this.currentContigName = fab.getSequenceDictionary().getSequences().get(0).getSequenceName();
                //  System.out.println(currentContigName + "+++++++++currentContigName++++++++++++");
            }
            if (this.currentContigName != null) {
                // System.out.println(currentContigName+"-----------currentContigName------------------");
                this.currentContigLen = getContigLen(currentContigName);
            }

        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public boolean ifHaveThisChrom(String chrom) {
        java.util.List<String> cotigNames = SamViewUtil.getReferenceNames(refile);
        if (cotigNames.contains(chrom)) {
            return true;
        }
        return false;
    }

    public static void generateDicAndIndex(String refile, TaskBase task) {
        // if(monitor==null)
        //    monitor= new StepProgress(MagicFrame.instance, "Loading Annotation","",  null);
        // monitor.next();
        if (!(new File(FileUtil.getNoTypePart(refile) + ".dict").exists())) {
            //  monitor.setVisible(true);
            //  monitor.reSet("Create Sequence Dictionary\n    Create sequence dictionary", true, 1);
            SamViewUtil.createSequenceDictionary(refile);
        }

        if (!(new File(refile + ".fai").exists())) {
            //  monitor.setVisible(true);
            //  monitor.reSet("Index reference\n    Index reference file", true, 1);
            SamViewUtil.indexRef(refile, task);
        }

    }

    public void changeContig(String contigName) {
        this.currentContigName = contigName;
        this.currentContigLen = getContigLen(currentContigName);
    }

    public int getContigLen(String contigName) {
        // System.out.println("contigName:++++++++  "+contigName);
        IndexedFastaSequenceFile fab = null;
        try {
            fab = new IndexedFastaSequenceFile(new File(this.refile));
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        if (contigName == null) {
            return 0;
        }

        // System.out.println(contigName+"-------contigName--------");
        return fab.getSequenceDictionary().getSequence(contigName).getSequenceLength();
    }

    public String getFastaSeq(int start, int end, String refName) {
        String seq = null;
        IndexedFastaSequenceFile fab = null;
        try {
            fab = new IndexedFastaSequenceFile(new File(this.refile));

            seq = new String(fab.getSubsequenceAt(refName, start, end).getBases()).toUpperCase();
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        if (seq == null || seq.equals("")) {
        }
        return seq;
    }
}
