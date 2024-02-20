/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece.GeneticUnit;

import Magic.Analysis.Merage.MergeAnnoImpl;
import Magic.Units.Color.ColorRep;
import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author Huabin Hou
 */
public class Utr5Piece extends GeneticPiece {
  public static Color color=ColorRep.utr;
    public Utr5Piece()
    {
    }
    public Utr5Piece(Vector<CDSPiece> cds) {
        if (cds.elementAt(0).strand) {
            this.end = cds.elementAt(0).start-1;
            this.start = this.end - MergeAnnoImpl.utrLen;

        } else {
            this.start = cds.lastElement().end+1;
            this.end = this.start + MergeAnnoImpl.utrLen;
        }
        this.chrom=cds.elementAt(0).chrom;
        this.strand=cds.elementAt(0).strand;
        feature = "utr5";
    }
}
