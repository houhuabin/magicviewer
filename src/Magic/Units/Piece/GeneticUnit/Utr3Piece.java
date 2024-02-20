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
public class Utr3Piece extends GeneticPiece {
    public static Color color=ColorRep.utr;
public Utr3Piece()
{}

    public Utr3Piece(Vector<CDSPiece> cds) {
        if (cds.elementAt(0).strand) {
            this.start = cds.lastElement().end+1;
            this.end = this.start + MergeAnnoImpl.utrLen;
        } else {
            this.end = cds.elementAt(0).start-1;
            this.start  = this.end - MergeAnnoImpl.utrLen;
        }
        this.chrom = cds.elementAt(0).chrom;
        this.strand = cds.elementAt(0).strand;
        feature = "utr3";
    }
}
