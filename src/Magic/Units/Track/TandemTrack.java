/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Piece.Piece;
import Magic.Units.Piece.GeneticUnit.Tandem;
import Magic.Units.Track.Track;
import Magic.Units.File.Parameter.StringRep;
import Magic.IO.ReadData;
import java.awt.Color;
import java.util.Vector;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class TandemTrack extends Track {

    public TandemTrack() {
        super();
        trackSet.COLOR = Color.PINK;
    }
    public String ExtFormat = "chrom start end id";

    public void getTrack(String infile, String contig) {
        try {
            Vector<Object> data = new Vector<Object>();
            Vector<Tandem> tandems = ReadData.readTandem(infile);
            if (tandems == null) {
                return;
            }
            for (int i = 0; i < tandems.size(); i++) {
                Tandem tandem = tandems.elementAt(i);
                Piece piece = new Piece();
                piece.geneticPiece.start = tandem.start;
                piece.geneticPiece.end = tandem.end;
                piece.geneticPiece.strand = tandem.strand;
                piece.geneticPiece.id = StringRep.TANDEM + "|" + tandem.unit_size + "|" + tandem.copy_num;
//                piece.source = tandem;
                data.add(piece);
            }

            this.currentPieces = (Piece[]) data.toArray();
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }
}
