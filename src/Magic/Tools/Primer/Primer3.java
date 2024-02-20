/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.Primer;


import Magic.Units.File.FastaFile;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.TextFile;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import utils.FileUtil;
import utils.ReportInfo;

/**
 *
 * @author lenovo
 */
public class Primer3 {

    public Track mainAnnoTrack;
    public String outputPath;
    public String outputFile;
    //  public String variantFile;
    public FastaFile reference;
    public Hashtable primerParas = new Hashtable();
    public int targetLen = 200;
    private String inputFileString;
    private TextFile intputFile;

    public void readGo(TaskBase moniter) {
        try {

            new File(outputPath).mkdirs();
            this.inputFileString = outputPath + ForEverStatic.FILE_SEPARATOR + "primer3_input";
            this.outputFile = outputPath + ForEverStatic.FILE_SEPARATOR + "primer3_output";


            FileUtil.clearFile(inputFileString);
            intputFile = new TextFile(this.inputFileString);

            getInput(moniter);
            runCmd(inputFileString);
            intputFile.close();

        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public void getInput(TaskBase moniter) {
        String[] productRange = ((String) primerParas.get("PRIMER_PRODUCT_SIZE_RANGE")).split("-");
        int productLen = Integer.valueOf(productRange[1]) + 100;
        // int targetLen = 200;

        moniter.setMax(mainAnnoTrack.currentPieces.length);
        for (int i = 0; i < mainAnnoTrack.currentPieces.length; i++) {
            if(!moniter.okToRun())
            {
                return;
            }
            moniter.setValue(i);
            Piece piece = mainAnnoTrack.currentPieces[i];

            int outsideLen = Math.max(productLen, targetLen);
            int insideLen = Math.min(productLen, targetLen);

            int variantLen = piece.geneticPiece.end - piece.geneticPiece.start;
            int shiftLenBase = (outsideLen - variantLen) / 2;//100
            int realLeftShift = Math.min(shiftLenBase, piece.geneticPiece.start);//250

            int realTargetLeft = Math.min((outsideLen - insideLen) / 2, piece.geneticPiece.start);//100

            //如果end+shiftLenPrime 大于contig长度 则取contig len 为target末尾
            int realRight = Math.min(reference.getContigLen(piece.geneticPiece.chrom), piece.geneticPiece.end + shiftLenBase);

            String id;

            if (piece.geneticPiece.id != null && !piece.geneticPiece.id.equals("")) {
                id = piece.geneticPiece.id;
            } else {
                id = i + "";
            }
            primerParas.put("PRIMER_SEQUENCE_ID", id);
            String sequence = reference.getFastaSeq(piece.geneticPiece.start - realLeftShift, realRight, piece.geneticPiece.chrom).replace("\n", "");
            primerParas.put("SEQUENCE", sequence);
            primerParas.put("TARGET", realTargetLeft + "," + Math.max(targetLen, variantLen));
            wirteinputFileString(inputFileString);
        }

    }

    public void wirteinputFileString(String inputFileString) {

        for (Iterator it = primerParas.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            String value = (String) primerParas.get(key);
            if (!value.equals("")) {
                intputFile.write(key + "=" + value + "\n");
            }
        }
        intputFile.write("=\n");
    }

    public void runCmd(String inputFileString) {
        try {
            String cmd = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "primer3_core < " + inputFileString + " >" + outputFile;
            String[] cmda = new String[]{"cmd.exe ", "/c", cmd};
            Process proc = Runtime.getRuntime().exec(cmda);
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }
}
