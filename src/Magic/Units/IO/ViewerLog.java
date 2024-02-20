/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.IO;

import Magic.IO.DataRep;
import Magic.Units.File.FileFormat;
import Magic.Units.Track.Track;
import Magic.Units.Track.TrackSet;
import Magic.Units.File.Parameter.LogAbstract;
import Magic.Units.File.Parameter.LogImplement;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import net.sf.picard.reference.IndexedFastaSequenceFile;
import utils.ReportInfo;

/**
 *
 * @author QIJ
 */
public class ViewerLog extends LogAbstract implements Serializable {

    public String log_file;
    public String referContigName = "CONTIG";
    public String reference_file;
    public String bisPath;
    //public int program_number;
    public Vector<TrackSet> trackSetVector = new Vector<TrackSet>();
    public int CURRENT_POSITION = 0;
    public int ZOOM = 0;
    public Vector SELECTED;
    public boolean ok = true;

    public ViewerLog(int num) {
        //if(num<=0) return;
        // program_number = num;
        // setSize(program_number);
    }

    public static void setViewerLog(ArrayList<String> annoFile, LogImplement log) {
        try {
//            StepProgress monitor = new StepProgress("Index reference file", "", null);
          //  FastaFile.generateDicAndIndex(log.reference.refile, monitor);
            // FastaFile ff = new FastaFile(log.reference.refile, null, monitor);
		//	monitor.doClose(ForEverStatic.RET_OK);
            IndexedFastaSequenceFile fb = new IndexedFastaSequenceFile(new File(log.reference.refile));
            log.viewerLogVector = new Vector<ViewerLog>();
            for (int i = 0; i < fb.getSequenceDictionary().size(); i++) {
                String id = fb.getSequenceDictionary().getSequence(i).getSequenceName();
                ViewerLog viewerLog = new ViewerLog(annoFile.size());

                viewerLog.log_file = log.projectProperty.log_file;
                viewerLog.referContigName = id;
                viewerLog.reference_file = log.reference.refile;
                //viewerLog.bisPath = log.projectProperty.PROJECT_FOLDER + Log.instance().global.FILE_SEPARATOR + id + ".bis";
                addViewer(annoFile, viewerLog);

                log.viewerLogVector.add(viewerLog);
            }
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public static void addViewerLog(ArrayList<String> annoFile, LogImplement log) {

        for (ViewerLog viewerLog : log.viewerLogVector) {
            addViewer(annoFile, viewerLog);
        }
    }

    public static void addViewer(ArrayList<String> annoFile, ViewerLog viewerLog) {
        int i = 0;
        for (String file : annoFile) {
            String format = FileFormat.checkFileFormatByContent(file);
            String formatMean = FileFormat.checkFileMeansByFormat(format, file);

            Track entrie = DataRep.getNewTrackByType(format, formatMean);
            TrackSet trackSet = entrie.trackSet;
            trackSet.filename = annoFile.get(i++);
            trackSet.format = format;
            trackSet.name = formatMean;
            viewerLog.trackSetVector.add(trackSet);
        }
    }

    public static void trackToViewer(Track entrie, ViewerLog viewerLog, int i) {

        viewerLog.trackSetVector.add(entrie.trackSet);
    }

    public void setSize(int newNum) {
        //   program_number = newNum;
    }

    public String toString() {
        String content = "refer=" + reference_file + "\n";
        content += "num=" + "\n";
        for (int i = 0; i < trackSetVector.size(); i++) {
            content += i + ": " + trackSetVector.elementAt(i) + "\n";
        }
        return content;
    }
}
