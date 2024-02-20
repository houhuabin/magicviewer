package Magic.IO;

import Magic.Units.Gui.Task.LoadTrackTask;
import Magic.Units.IO.SeqData;
import Magic.Units.IO.ViewerLog;
import Magic.Units.Piece.GeneticUnit.Tandem;
import Magic.Units.File.FileFormat;
import Magic.Units.Main.BriefM8Line;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import java.awt.Color;

import java.io.*;
import java.util.*;


import Magic.WinMain.MagicFrame;
import Magic.Units.Track.AlignTrack;
import Magic.Units.File.Parameter.StringRep;

import Magic.Units.binary.Bis;
import Magic.Units.binary.BisBean;
import Magic.Units.Track.CPG130Track;
import Magic.Units.Track.PileupTrack;
import Magic.Units.Piece.Cpg130Piece;
import Magic.Units.Track.GeneTrack;
import Magic.Units.Track.MethySiteTrack;
import Magic.Units.Track.SNPTrack;
import Magic.Units.Track.TandemTrack;
import Magic.Algorithms.Vertical;


import Magic.Units.Track.TrackSet;
import Magic.Units.Piece.NSEPiece;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.FastaFile;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.LogImplement;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.IO.SeqPanelData;
import Magic.Units.Piece.GeneticUnit.PileupPiece;
import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMRecord;
import utils.ForMagic;
import utils.ReportInfo;
import utils.SamViewUtil;
import utils.SamViewUtil.Insert;

public class DataRep {

    public int trackNum = 0;
    public Track[] tracks = null;
    // private MagicFrame trackFrame;
    private ViewerLog annoViewLog;

    public DataRep() {
    }

    public DataRep(ViewerLog l) {
        annoViewLog = l;
        trackNum = annoViewLog.trackSetVector.size();
        tracks = new Track[trackNum];
        for (int i = 0; i < trackNum; i++) {

            String format = FileFormat.checkFileFormatByContent(annoViewLog.trackSetVector.elementAt(i).filename);
            // //System.out.println(log.trackSetVector.elementAt(i).filename + " ===========================  " + log.trackSetVector.elementAt(i).filename);
            String formatMean = FileFormat.checkFileMeansByFormat(format, annoViewLog.trackSetVector.elementAt(i).filename);
            //   System.out.println(format+"======================"+formatMean);
            tracks[i] = getNewTrackByType(format, formatMean);
            tracks[i].trackSet = annoViewLog.trackSetVector.elementAt(i);
        }
        //trackFrame = c;
    }

    public void setTrackByCurrentContig() {
        if (tracks == null) {
            //System.out.println("tracks null");
            return;
        }

        for (int i = 0; i < trackNum; i++) {

            ArrayList<Piece> al = tracks[i].iteratorPieces.getCurrentContigPieces(MagicFrame.instance.currentLog.referContigName);

            if (al == null) {
                //System.out.println("-----------al null------------");
                return;
            }
            //System.out.println("-----------al size------------"+al.size());
            tracks[i].currentPieces = al.toArray(new Piece[0]);

        }
        Log.instance().reference = new FastaFile(annoViewLog.reference_file, MagicFrame.instance.currentLog.referContigName);

    }

    //need refine by MagicUtil getTrack
    public void addTrackData(final ArrayList<String> filenames) {
        Track[] new_tracks = new Track[trackNum + filenames.size()];
        for (int i = 0; i < trackNum; i++) {
            new_tracks[i] = tracks[i];
        }
        LoadTrackTask lt = new LoadTrackTask(filenames, new_tracks, null);
        NewProgress monitor = new NewProgress("Loading Annotation", StringRep.START, lt);
        monitor.startTask();
        if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
            if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                ReportInfo.reportError(null, monitor.getException());
            }
            return;
        }
        tracks = new_tracks;
    }

    public void dataTrunkToLog() {
        // //System.out.println(trackFrame.logBean.viewerLogVector.size() + " ------viewerLogVector.size-------- ");
        for (ViewerLog viewerLog : Log.instance().viewerLogVector) {

            viewerLog.trackSetVector = new Vector<TrackSet>();
            for (int i = 0; i < trackNum; i++) {

                viewerLog.trackSetVector.add(tracks[i].trackSet);
            }
        }

    }

    public static Track getNewTrackByType(String format, String formatMean) {
        Track tracks = null;
        //  //System.out.println(format+"----------------formatMean----------------------"+formatMean);
        if (formatMean.equals(StringRep.GENE)) {
            tracks = new GeneTrack();
        } else if (formatMean.equals(StringRep.SNPS)) {
            // //System.out.println("SNPS track"+"=============================================");
            tracks = new SNPTrack();
        } else if (format.equals(StringRep.TANDEM)) {
            tracks = new TandemTrack();
        } else if (format.equals(StringRep.CPG130)) {
            tracks = new CPG130Track();
        } else if (format.equals(StringRep.MP)) {
            tracks = new MethySiteTrack();
        } else {
            tracks = new Track();
        }
        tracks.trackSet.format = format;
        tracks.trackSet.name = formatMean;
        return tracks;
    }

    public int getTracks() {
        try {


            tracks = new Track[trackNum];
            LoadTrackTask lt = new LoadTrackTask(getTrackFileByTraceSet(annoViewLog.trackSetVector), tracks, getTrackNameTraceSet(annoViewLog.trackSetVector));
            NewProgress monitor = new NewProgress("Loading Annotation", StringRep.START, lt);
            // monitor.startTask();
            if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
                //System.out.println("Error ");
                if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                    ReportInfo.reportError(null, monitor.getException());
                    return ForEverStatic.RET_ERR;
                }
                tracks = null;
                return ForEverStatic.RET_CANCEL;
            }
            // System.out.println("trackNum:" + trackNum);
            for (int i = 0; i < trackNum; i++) {

                tracks[i].trackSet = annoViewLog.trackSetVector.elementAt(i);
                //System.out.println(tracks[i].trackSet.filename);
            }

            Log.instance().reference = new FastaFile(annoViewLog.reference_file, MagicFrame.instance.currentLog.referContigName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ForEverStatic.RET_OK;
    }

    public static ArrayList<String> getTrackFileByTraceSet(Vector<TrackSet> trackSetVector) {
        ArrayList<String> result = new ArrayList<String>();
        for (TrackSet set : trackSetVector) {
            result.add(set.filename);
        }
        return result;
    }

    public static ArrayList<String> getTrackNameTraceSet(Vector<TrackSet> trackSetVector) {
        ArrayList<String> result = new ArrayList<String>();
        for (TrackSet set : trackSetVector) {
            result.add(set.name);
        }
        return result;
    }

    public ArrayList<String> getLoadingStepNames() {
        ArrayList<String> stepNames = new ArrayList<String>();
        for (int i = 0; i < trackNum; i++) {
            stepNames.add(tracks[i].trackSet.name);
        }
        return stepNames;
    }

    public static void findClusterByTrack(Track track) {
        track.trackSet.maxVertical = findCluster((Piece[]) track.currentPieces);
    }

    public static int findCluster(Piece[] items) {

        //Piece[] items = (Piece[]) track.items;
        Vector<Integer> vector = new Vector<Integer>();

        for (int i = 0; i < items.length; i++) {
            vector.add(i);

        }

        int[] x = new int[vector.size()];
        int[] start = new int[vector.size()];

        for (int i = 0; i < vector.size(); i++) {
            x[i] = vector.elementAt(i);
            start[i] = items[x[i]].geneticPiece.start;
            ////System.out.println(x[i]+" "+y[i]+" "+start[i]);
        }
        quickSort2(start, x, 0, vector.size() - 1);
        //int current=1;
        Vertical verticalZ = new Vertical();
        Vertical verticalF = new Vertical();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                // System.out.println("items[i]==null");
            } else if (items[i].geneticPiece.start == null) {
                // System.out.println("geneticPiece[i]==null");
            }

            if (items[i].geneticPiece.strand != null && items[i].geneticPiece.strand == true) {
                items[i].viewPiece.vertical = verticalZ.getVerticalPosition(items[i].geneticPiece.start, items[i].geneticPiece.end);
            } else {
                items[i].viewPiece.vertical = verticalF.getVerticalPosition(items[i].geneticPiece.start, items[i].geneticPiece.end);
            }
        }
        return Math.max(verticalZ.getMaxVertical(), verticalF.getMaxVertical());

    }

    public static void setVertical2(Piece[] items, int[] x, int first, int last) {

        ////System.out.println(first+"~"+last);
        if (first >= last) {
            return;
        }
        boolean join = true;
        Vector<Integer> vector = new Vector<Integer>();
        int end_site = 0;

        for (int i = first; i <= last; i++) {

            if (i == first) {
                vector.add(items[x[i]].geneticPiece.end);
                continue;
            }

            join = true;
            for (int j = 0; j < vector.size(); j++) {
                end_site = ((Integer) vector.elementAt(j)).intValue();
                if (items[x[i]].geneticPiece.start > end_site + 1) {
                    items[x[i]].viewPiece.vertical = j;
                    vector.setElementAt(items[x[i]].geneticPiece.end, j);
                    join = false;
                    break;
                }
            }

            if (join == true) {
                items[x[i]].viewPiece.vertical = vector.size();
                vector.add(items[x[i]].geneticPiece.end);
            }
            ////System.out.println(i+"\t"+read[x[i]].vertical_site[y[i]]+"\t"+read[x[i]].start[y[i]]+".."+read[x[i]].end[y[i]]);
        }
    }

    public void quickSort1(int[] start, int[] x, int[] y, int first, int last) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        int mid = start[(first + last) / 2];
        do {
            while (start[low] < mid) {
                low++;
            }
            while (start[high] > mid) {
                high--;
            }
            if (low <= high) {
                int temp_start = start[low];
                int temp_x = x[low];
                int temp_y = y[low];

                start[low] = start[high];
                x[low] = x[high];
                y[low] = y[high];

                start[high] = temp_start;
                x[high] = temp_x;
                y[high] = temp_y;

                low++;
                high--;
            }
        } while (low <= high);
        quickSort1(start, x, y, first, high);
        quickSort1(start, x, y, low, last);
    }

    public static void quickSort2(int[] start, int[] x, int first, int last) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        int mid = start[(first + last) / 2];
        do {
            while (start[low] < mid) {
                low++;
            }
            while (start[high] > mid) {
                high--;
            }
            if (low <= high) {
                int temp_start = start[low];
                int temp_x = x[low];

                start[low] = start[high];
                x[low] = x[high];

                start[high] = temp_start;
                x[high] = temp_x;

                low++;
                high--;
            }
        } while (low <= high);
        quickSort2(start, x, first, high);
        quickSort2(start, x, low, last);
    }

    public void removeData(int index) {
        if (index < 0 || index > trackNum - 1) {
            return;
        }
        Track[] new_tracks = new Track[trackNum - 1];

        int current = 0;
        for (int i = 0; i < trackNum; i++) {
            if (i == index) {
                continue;
            }
            new_tracks[current] = tracks[i];
            current++;
        }
        tracks = new_tracks;
        trackNum--;
    }

    public void exchangeData(int index1, int index2) {
        if (index1 < 0 || index1 > trackNum - 1) {
            return;
        }
        if (index2 < 0 || index2 > trackNum - 1) {
            return;
        }
        if (index1 == index2) {
            return;
        }
        Track temp = tracks[index1];
        tracks[index1] = tracks[index2];
        tracks[index2] = temp;
    }

    public void replaceData(int index, String filename) {

        Track[] track = new Track[1];
        ArrayList<String> names = new ArrayList<String>();
        names.add(filename);
        LoadTrackTask lt = new LoadTrackTask(names, track, null);
        NewProgress monitor = new NewProgress("Loading Annotation", StringRep.START, lt);
        monitor.startTask();
        if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
            if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                ReportInfo.reportError(null, monitor.getException());
            }
            return;
        }
        tracks[index] = track[0];
    }

    public int[] getSmallBig(int point, int max, int window) {
        int[] result = new int[2];
        int small, big;
        if (max - point < window) {
            small = max - window;
            big = max;
        } else {

            small = point;
            big = point + window;
        }
        result[0] = small;
        result[1] = big;
        return result;

    }

    public void getSeqTrackAndRef(int point, LogImplement log, SeqData seqData) {
        //System.out.println("Log.instance().reference:" + Log.instance().reference);
        if (point > Log.instance().reference.currentContigLen) {
            return;
        }
        getReadsByBAM(point, log, seqData);
    }

    public void getReadsByBAM(int point, LogImplement log, SeqData seqData) {


        for (String readFile : log.reads.getReads()) {

            AlignTrack en = new AlignTrack();
            SeqPanelData seqPanelData = new SeqPanelData();
            seqData.seqPanelDatas.add(seqPanelData);
            seqPanelData.seqEntries = en;

            int[] smallBig = getSmallBig(point, Log.instance().reference.currentContigLen, Log.instance().global.alignmentWindowLen);
            // seqData.windowStart = smallBig[0];
            seqData.windowStart = point;
            seqData.windowEnd = seqData.windowStart + Log.instance().global.alignmentWindowLen;

            //System.out.println(" start "+seqData.windowStart+" --xxxxxxx---windowEnd---xxxxxxx-- "+seqData.windowEnd+"---------------"+Log.instance().reference.currentContigLen);
            int maxVerStrand = 0;


            //  String readFile = log.projectProperty.readFile;
            ArrayList<Insert> insertPoint = new ArrayList<Insert>();

            try {

                final SAMFileReader inputSam = new SAMFileReader(new File(readFile));
                inputSam.setValidationStringency(SAMFileReader.ValidationStringency.SILENT);
                Iterator<SAMRecord> iter1 = inputSam.query(MagicFrame.instance.currentLog.referContigName, smallBig[0], smallBig[1], false);
                ArrayList<SAMRecord> iterArrayList = new ArrayList<SAMRecord>();
                while (iter1.hasNext()) {
                    iterArrayList.add((SAMRecord) iter1.next());
                }
                iter1 = null;

                Vertical verPositive = new Vertical();
                SamViewUtil.setInsertSite(iterArrayList, point, verPositive, insertPoint, seqData);
                SamViewUtil.CigarToPiece(iterArrayList, point, verPositive, insertPoint, seqData, seqPanelData);

                inputSam.close();


                en.trackSet.maxVertical = verPositive.getMaxVertical();

                en.middleY = (maxVerStrand + 2) * (en.trackSet.pieceHeight + en.trackSet.pieceInterval);

                en.trackSet.filename = readFile;
                seqData.insertPoint = insertPoint;


                String reference = Log.instance().reference.getFastaSeq(seqData.refSeqStart, seqData.refEnd, MagicFrame.instance.currentLog.referContigName);
                //  SystemUtil.printCurrentTime("ref start");
                seqData.reference = SamViewUtil.insertRefSeq(insertPoint, reference, seqData.refSub);
                //  SystemUtil.printCurrentTime("ref end");
                //    System.out.println("");

                en.seqData = seqData;
                en.setTrackViewPropoery();

            } catch (Exception ex) {
                ReportInfo.reportError("getSeqData", ex);
            }

        }
    }

    public void getColumnTrack(int length, ArrayList<SeqPanelData> seqPanelDatas) {
        for (SeqPanelData seqPanelData : seqPanelDatas) {
            Track seqen = seqPanelData.seqEntries;
            PileupTrack en = new PileupTrack();
            seqPanelData.pileupEntries = en;
            Vector<Piece> data = new Vector<Piece>();
            try {

                NSEPiece piece;
                BisBean[] bisArray = Bis.getBisArrayBySeqTrack(length, seqen);

                int temp = 0;
                int temp2 = 0;
                int maxCover = 0;
                int totalCover = 0;
                if (bisArray != null) {


                    for (int i = 0; i < bisArray.length; i++) {

                        maxCover = maxCover > bisArray[i].totalNum ? maxCover : bisArray[i].totalNum;
                        totalCover += bisArray[i].totalNum;
                        // int interval = 0;
                        temp = 0;
                        temp2 = 0;
                        NSEPiece nsepiece = new NSEPiece();
                        nsepiece.geneticPiece = new PileupPiece();
                        nsepiece.geneticPiece.id = String.valueOf(i + 1);
                        nsepiece.geneticPiece.start = i;
                        nsepiece.geneticPiece.end = i;
                        nsepiece.viewPiece.y1 = 0;
                        nsepiece.viewPiece.y2 = (int) bisArray[i].totalNum;

                        if (bisArray[i].ANum > 0) {
                            piece = new NSEPiece();
                            piece.geneticPiece = null;
                            piece.viewPiece.y1 = 0;//起始位置
                            temp = (int) (bisArray[i].ANum);
                            piece.viewPiece.y2 = temp;//height
                            piece.viewPiece.content = "A";
                            nsepiece.viewPiece.content += "A: " + String.valueOf((int) bisArray[i].ANum) + "  ";
                            nsepiece.subPieceList.add(piece);
                        }

                        if (bisArray[i].TNum > 0) {

                            piece = new NSEPiece();
                            piece.geneticPiece = new PileupPiece();
                            piece.geneticPiece.id = "T";

                            piece.geneticPiece.start = i;
                            piece.viewPiece.y1 = temp;
                            piece.geneticPiece.end = piece.geneticPiece.start;
                            temp2 = (int) (bisArray[i].TNum);
                            piece.viewPiece.y2 = (int) temp2;
                            piece.viewPiece.content = "T";
                            temp = temp + temp2;


                            nsepiece.viewPiece.content += "T: " + String.valueOf((int) bisArray[i].TNum) + "  ";
                            nsepiece.subPieceList.add(piece);

                        }

                        if (bisArray[i].CNum > 0) {

                            piece = new NSEPiece();
                            piece.geneticPiece = null;
                            piece.viewPiece.y1 = temp;

                            temp2 = (int) (bisArray[i].CNum);
                            piece.viewPiece.y2 = temp2;
                            piece.viewPiece.content = "C";
                            temp = temp + temp2;
                            nsepiece.viewPiece.content += "C: " + String.valueOf((int) bisArray[i].CNum) + "  ";
                            nsepiece.subPieceList.add(piece);
                        }

                        if (bisArray[i].GNum > 0) {

                            piece = new NSEPiece();
                            piece.geneticPiece = null;
                            piece.viewPiece.y1 = temp;
                            temp2 = (int) (bisArray[i].GNum);
                            piece.viewPiece.y2 = temp2;
                            piece.viewPiece.content = "G";
                            temp = temp + temp2;
                            nsepiece.viewPiece.content += "G: " + String.valueOf((int) bisArray[i].GNum) + "  ";
                            nsepiece.subPieceList.add(piece);
                        }

                        if (bisArray[i].NNum > 0) {

                            piece = new NSEPiece();
                            piece.geneticPiece = null;
                            piece.viewPiece.y1 = temp;
                            piece.viewPiece.y2 = (int) (bisArray[i].NNum);
                            piece.viewPiece.content = "N";
                            nsepiece.viewPiece.content += "N: " + String.valueOf((int) bisArray[i].NNum) + "  ";
                            nsepiece.subPieceList.add(piece);
                        }

                        data.add(nsepiece);

                    }
                }
                //  //System.out.println("Done");

                en.currentPieces = data.toArray(new Piece[0]);
                en.maxCover = maxCover;
                en.averageCover = totalCover / bisArray.length;
                if (en.maxCover > Log.instance().global.columnDisplayHeight) {
                    en.heightAdjust = (float) Log.instance().global.columnDisplayHeight / en.maxCover;
                }

                en.trackSet.trackHeight = Log.instance().global.columnDisplayHeight;
                en.middleY = (en.trackSet.trackHeight + 2) * (en.trackSet.pieceHeight + en.trackSet.pieceInterval);


                en.setTrackViewPropoeryY();
                en.setTrackViewPropoery();

            } catch (Exception e) {
                ReportInfo.reportError("Exception in read3ColumnContent()", e);
            }
            // return en;
        }
    }
}
