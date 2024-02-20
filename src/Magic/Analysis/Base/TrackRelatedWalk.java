/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Base;


import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Administrator
 */
public class TrackRelatedWalk {

    public Track snpTrack;
    public ArrayList<Track> cdssList;
   ArrayList<MergePiece> result = new  ArrayList<MergePiece>();
   public TaskBase task;


    public class MergePiece {
        public Piece main;
        public ArrayList<Piece> correlated = new ArrayList<Piece>();
    }
     public TrackRelatedWalk() {
       
    }
    
    public TrackRelatedWalk(Track snpTrack, ArrayList<Track> cdssList) {
        this.snpTrack=snpTrack;
        this.cdssList=cdssList;
    }

    public void go()
    {
    
    }
   public static int getTotalCdssListSize(ArrayList<Track> cdssList) {
        int i = 0;
        for (Track cds : cdssList) {
            i += getTrackSize(cds);
        }
        return i;
    }

    public static int getTrackSize(Track cds) {

        return cds.iteratorPieces.size();
    }

    public ArrayList<MergePiece> mergeTracks(Track snpTrack, ArrayList<Track> cdssList) {
        //MagicFrame.instance.monitor.reSet("Calculate" + StringRep.START, false, getTrackSize(snpTrack));
         for (Track track : cdssList) {
             mergeTrack(snpTrack, track);
           // result.addAll(pieceReslut);
        }
        //  ArrayList<MergePiece>
        return result;
    }

    public void mergeTrack(Track snpTrack, Track gene) {

        HashMap<String, ArrayList<Piece>> contigItems = snpTrack.iteratorPieces.contigPieces;
        Iterator trackIt = contigItems.entrySet().iterator();
        while (trackIt.hasNext()) {
            Entry entry = (Entry) trackIt.next();
            String chrom = (String) entry.getKey();
            ArrayList<Piece> snPieces = (ArrayList<Piece>) entry.getValue();
            ArrayList<Piece> genPieces = gene.iteratorPieces.contigPieces.get(chrom);
            mergeContigs(snPieces, genPieces);

             task.appendMessage("Calculate: " + chrom + " " + StringRep.DONE);
        }

        //  ArrayList<MergePiece> pieceReslut = mergeContig(snpTrack.iteratorPieces, cdssList);
        //   result.addAll(pieceReslut);
       // return result;
    }

    public void mergeContigs(ArrayList<Piece> snPieces, ArrayList<Piece> genPieces) {
        for (Piece snp : snPieces) {
            MergePiece mergePiece = new MergePiece();
            result.add(mergePiece);
            mergePiece.main = snp;
            processContig(snp, genPieces,mergePiece);
           task.setValue( task.getValue() + 1);
        }
    }
    
     public void processContig(Piece snp,ArrayList<Piece> genPieces, MergePiece mergePiece)
     {


     }
}
