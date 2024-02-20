/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.File;

import Magic.Units.Piece.GeneticUnit.CDSPiece;
import Magic.Units.Piece.GeneticUnit.GenePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import utils.FileUtil;


/**
 *
 * @author Administrator
 */
public class UCSCGeneTide {

    public static String currentGeneName = "";
    public static Piece currentGenePiece = null;
    public static Piece firstPiece = null;
    public static Piece lastPiece = null;
 /*
    public static void tide(String file) {
        try {


          Track track = MagicUtil.getTrack(file);
            Iterator it = track.iteratorPieces.iterator();



            while (it.hasNext()) {
                Piece piece = (Piece) it.next();
                if (currentGeneName.equals(piece.getDetailFieldValue("transcript_id"))) {
                    if (piece.geneticPiece instanceof CDSPiece) {
                        ((GenePiece) currentGenePiece.geneticPiece).CDSs.add((CDSPiece) piece.geneticPiece);
                    }
                } else {
                    ouputGene(currentGenePiece);
                    currentGeneName = (String) piece.getDetailFieldValue("transcript_id");
                    firstPiece = piece;
                    currentGenePiece = new Piece(GeneticUnitPiece.createInstance(GeneticType.Gene), piece.filePiece);
                }
                lastPiece = piece;
            }
            ouputGene(currentGenePiece);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   public static void ouputGene(Piece piece) {
        ouputPiece(piece);

        ouputPiece(new Piece(((GenePiece) piece.geneticPiece).utr3, piece.filePiece));
        for (CDSPiece cds : ((GenePiece) currentGenePiece.geneticPiece).CDSs) {
            ouputPiece(new Piece(cds, piece.filePiece));
        }
        ouputPiece(new Piece(((GenePiece) piece.geneticPiece).utr5, piece.filePiece));
    }

    public static void ouputPiece(Piece piece) {
    }*/
}
