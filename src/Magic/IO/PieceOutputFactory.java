/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.IO;

import Magic.Analysis.Base.TrackRelatedWalk.MergePiece;
import Magic.Analysis.Merage.MergePara;
import Magic.WinMain.MagicFrame;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.FileFormat;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.Piece;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.Units.Track.Track;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import utils.FileUtil;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class PieceOutputFactory {

    public TaskBase monitor;

    public void outputMerge(String output, PieceField[] formatDefine, ArrayList<MergePiece> merges, TaskBase monitor) {
        // StepProgress monitor = new StepProgress(parent,   "Output Results", KeyWords.START, false, 1,null);
         this.monitor=monitor;
        monitor.appendMessage("\n\nOutput Start...\n");
        if (merges == null) {
		monitor.cancelTask();
            return;
        }

        //monitor.reSet("Output Result" + StringRep.START, false, merges.size());

        try {
            if (MergePara.currentOutputPolicy == MergePara.OutputPolicy.Pooled) {
                pooledProcess(output, merges, monitor, formatDefine);
            } else {
                autoSeperateProcess(output, merges, monitor, formatDefine);
            }
        } catch (Exception ex) {
            ReportInfo.reportError(output, ex);
        }
        monitor.appendMessage("Output Result:" + StringRep.DONE);

    }

    public void pooledProcess(String outputFile, ArrayList<MergePiece> merges, TaskBase monitor, PieceField[] formatDefine) {
        try {

            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < merges.size(); i++) {
                if(!monitor.okToRun())
                {
                   return;
                }
                MergePiece mergePiece = merges.get(i);
                outPutMergePiece(mergePiece, formatDefine, bw);
                monitor.setValue(i);
            }

            bw.close();
            fw.close();
        } catch (Exception ex) {
            ReportInfo.reportError(outputFile, ex);

        }

    }

    public void autoSeperateProcess(String outputFile, ArrayList<MergePiece> merges, TaskBase monitor, PieceField[] formatDefine) {
        try {

            FileWriter[] fws = new FileWriter[MergePara.snpTypes.size()];
            BufferedWriter[] bws = new BufferedWriter[MergePara.snpTypes.size()];
            for (int i = 0; i < MergePara.snpTypes.size(); i++) {
                fws[i] = new FileWriter(FileUtil.getNoTypePart(outputFile) + "." + MergePara.snpTypes.get(i) + FileUtil.getTypePart(outputFile));
                bws[i] = new BufferedWriter(fws[i]);
            }
            for (int i = 0; i < merges.size(); i++) {
                 if(!monitor.okToRun())
                {
                   return;
                }
                MergePiece mergePiece = merges.get(i);
                // //System.out.println(((SNPPiece) (mergePiece.main.geneticPiece)).snpType+"----------snpType-------------");
                int index = MergePara.snpTypes.indexOf(((SNPPiece) (mergePiece.main.geneticPiece)).snpType);
                outPutMergePiece(mergePiece, formatDefine, bws[index]);
                monitor.setValue(i);
            }
            for (int i = 0; i < MergePara.snpTypes.size(); i++) {
                bws[i].close();
                fws[i].close();

            }
			monitor.cancelTask();

        } catch (Exception ex) {
            ReportInfo.reportError(outputFile, ex);

        }

    }

    public void outPutMergePiece(MergePiece mergePiece, PieceField[] foramtDefine, BufferedWriter bw) throws IOException {

        if (foramtDefine==FileFormat.GFFFormat) {
            String oneline = getMainPieceStr(mergePiece.main, foramtDefine);
         //  System.out.println(oneline);
            if (mergePiece.main.geneticPiece instanceof SNPPiece) {
               
                if (!MergePara.snpTypes.contains(((SNPPiece) mergePiece.main.geneticPiece).snpType)) {
                    //System.out.println(((SNPPiece) mergePiece.main.geneticPiece).snpType);
                    return;
                }
                oneline += "SNPType=" + ((SNPPiece) mergePiece.main.geneticPiece).snpType + "; ";
            }
            for (Piece correlatedPiece : mergePiece.correlated) {
                oneline += getCorrelatedPieceStr(correlatedPiece);
            }
            if (oneline.endsWith(" ")) {
                oneline = oneline.substring(0, oneline.length() - 1);
            }
            bw.write(oneline);
            bw.write("\n");
        }
    }

    public void outputFilterTrack(String outFile, Track track, TaskBase monitor) {
        try {
           // this.parent = parent;
            monitor.appendMessage("\n\nOutput Start...\n");
           // parent.monitor.reSet("Output Result" + StringRep.START, false, TrackRelatedWalk.getTrackSize(track));
            FileWriter fw = new FileWriter(outFile);
            BufferedWriter bw = new BufferedWriter(fw);
            HashMap<String, ArrayList<Piece>> contigItems = track.iteratorPieces.get();
            Iterator it = contigItems.entrySet().iterator();


            while (it.hasNext()) {
                 if(!monitor.okToRun())
                {
                   return;
                }
                Entry entry = (Entry) it.next();
                String id = (String) entry.getKey();
                monitor.appendMessage(id + "...");
                ArrayList<Piece> pieces = (ArrayList<Piece>) entry.getValue();
                FileFormat ff = new FileFormat();
                PieceField[]  formatDefine = ff.getFormatDefineString(track.trackSet.format);
                outPutContig(pieces, formatDefine, bw);
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public void outPutContig(ArrayList<Piece> pieces, PieceField[]  formatDefine, BufferedWriter bw) {
        // System.out.println(pieces.size() + "-----------------pieces  size-----------------");
        Iterator<Piece> iter = pieces.iterator();
        while (iter.hasNext()) {
            monitor.setValue(monitor.getValue() + 1);
            Piece piece = iter.next();
            outputPiece(piece, formatDefine, bw);
        }

    }

    public void outputPiece(Piece piece, PieceField[] formatDefine, BufferedWriter bw) {
        try {
            String oneline = getNoExtraPieceStr(piece, formatDefine);
            // System.out.println(oneline);
            bw.write(oneline);
            bw.write("\n");
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

    }

    public String getMainPieceStr(Piece piece, PieceField[] foramtDefine) throws IOException {
        String result = piece.toFormatString(foramtDefine,true);
        return result;
    }
    public String getNoExtraPieceStr(Piece piece, PieceField[] foramtDefine) throws IOException {
        String result = piece.toFormatString(foramtDefine,true);
        return result;
    }

    public String getCorrelatedPieceStr(Piece piece) throws IOException {
        return piece.toGroupString();

    }
    // }
}
