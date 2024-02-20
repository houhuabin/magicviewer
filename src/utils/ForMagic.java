/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.swing.SwingUtil;
import Magic.IO.DataRep;
import Magic.Units.Gui.Task.LoadTrackTask;
import Magic.Units.File.FileFormat;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Track.Track;

import Magic.WinMain.MagicFrame;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author lenovo
 */
public class ForMagic {

    public static String getFastaFile(JFrame parent) {
        String file = SwingUtil.getOpenFileByChooser(null, new String[]{"fasta|fa|faa|fna"}, parent);
        return file;
    }

    public static String getReferenceFile(JFrame parent) {
        String file = SwingUtil.getOpenFileByChooser(null, new String[]{"fasta|fa|faa|fna|gbk"}, parent);
        return file;
    }

   // public static String getAlignmentFile(JFrame parent) {
       // return SwingUtil.getOpenFileByChooser(null, new String[]{"bam|sam"}, parent);
  //  }
    public static String[] getAlignmentFiles(JFrame parent) {
        return SwingUtil.getOpenFilesByChooser(null, new String[]{"bam|sam"}, parent);
    }

    public static int getValidatedQualityIndex(int index) {
        if (index < 0) {
            return 0;
        }
        if (index >= Log.instance().alignPara.qualityGradientSize) {
            return Log.instance().alignPara.qualityGradientSize - 1;
        }
        return index;
    }

    public static void setVer1Value(int value) {
        MagicFrame.instance.horiz1.setValueIsAdjusting(true);
        MagicFrame.instance.horiz1.setValue(value);
        // MagicFrame.instance.horiz1.setValueIsAdjusting(false);
    }

    public static Track getTracksBy(String annoFile) {
        boolean existInDtrunck = false;
        Track result = null;
        for (Track track : MagicFrame.instance.dataRep.tracks) {
            if (annoFile.equals(track.trackSet.filename)) {
                existInDtrunck = true;
                //  System.out.println("============existInDtrunck============");
                return track;
            }
        }
        if (!existInDtrunck) {
            Track[] track = new Track[1];
           ArrayList<String> names = new ArrayList<String>();
            names.add(annoFile);
            LoadTrackTask lt = new LoadTrackTask(names, track, null);
            NewProgress monitor = new NewProgress("Loading Annotation", StringRep.START, lt);
            monitor.startTask();
            if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
                if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                    ReportInfo.reportError(null, monitor.getException());
                }
                return null;
            }
        }
        return result;
    }

    public static Track getTrack(String filename, TaskBase task) {

        String format = FileFormat.checkFileFormatByContent(filename);
        String formatMean = FileFormat.checkFileMeansByFormat(format, filename);

        Track track = DataRep.getNewTrackByType(format, formatMean);

        track.trackSet.filename = filename;
        track.trackSet.format = format;
        track.trackSet.name = formatMean;
        track.getTrack(filename, MagicFrame.instance.currentLog.referContigName, task);
        return track;
    }

    public static boolean containField(PieceField[] formatFieldNames, String name) {
        for (PieceField p : formatFieldNames) {
            if (p.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ifChromInProject(String chrom) {
        return Log.instance().reference.ifHaveThisChrom(chrom);
    }
}
