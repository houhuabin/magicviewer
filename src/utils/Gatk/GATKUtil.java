/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.Gatk;


import utils.*;
import org.broadinstitute.sting.gatk.CommandLineGATK;

/**
 *
 * @author Huabin Hou
 */
public class GATKUtil {

    /*public static void genoTyper(GenotyperBean genotyperBean) {
    try {
    String indelArgument = "";
    String ignoSecondArgument = "";
    String regionArgumen = "";

    if (!genotyperBean.intervals.equals("All")) {
    regionArgumen = " -L " + genotyperBean.intervals;
    }


    String genoTyperCommand = regionArgumen + " -U -R " + genotyperBean.referenceFile + " -T UnifiedGenotyper -I " + genotyperBean.samFiles + " -varout " + genotyperBean.VARIANTS_FILE + " -lod " + genotyperBean.LOD_THRESHOLD + " -pl  " + genotyperBean.defaultPlatform + " " + indelArgument + " " + ignoSecondArgument;
    runCommand(genoTyperCommand);
    } catch (Exception ex) {
    ReportInfo.reportException("", ex);
    }
    }
     */
  
    

    public static void runCommand(String[] command) {
        try {

        //    SystemUtil.setClassPath("lib"+Log.instance().global.FILE_SEPARATOR+"GenomeAnalysisTK.jar");
           /* SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\StingUtils.jar");
             SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\VCFTool.jar");
              SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\Aligner.jar");
               SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\StingUtils.jar");
                SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\StingUtils.jar");
                 SystemUtil.setClassPath("E:\\project\\magicinsight\\lib\\StingUtils.jar");
                getClassPathsetClassPath("E:\\project\\magicinsight\\lib\\StingUtils.jar");*/
            
            SystemUtil.printArray(command);

            
            CommandLineGATK instance = new CommandLineGATK();

            CommandLineGATK.start(instance, command);
              //  SystemUtil.printCurrentTime("paint end");
               //  System.out.println("");

        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public static void main(String[] argv) {
        try {

//runCommand("-U  -I  chrXYZ.sort.bam    -R  chrXYZ.fasta  -T UnifiedGenotyper -bm  ONE_STATE  -vf  VCF  -deletions  1  -ps  1  -hets  1e-3  -pl  SOLEXA       -singleSample    -varout  E:\\project\\magicinsight\\test\\chrXYZ.sort.var.out  -lod  5  -coverage  10000  -gm  EM_POINT_ESTIMATE  ");
           // runCommand(" -U  -I  chrXYZ.sort.bam    -R  chrXYZ.fasta  -T UnifiedGenotyper -varout chrXYZ.geli.calls  -bm  ONE_STATE  -vf  VCF  -deletions  1  -ps  1  -hets  1e-3  -pl  SOLEXA   -lod  5  -coverage  10000  -gm  EM_POINT_ESTIMATE ");
        } catch (Exception e) {
        }
    }
}
