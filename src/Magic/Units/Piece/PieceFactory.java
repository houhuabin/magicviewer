/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;

/**
 *
 * @author lenovo
 */
public class PieceFactory {

    public enum KnownAnnoFormat {

        BED, GFF, PTT,VCF,UNKNOWN,UNEXIST
    }

       /*public static Piece createInstance(KnownAnnoFormat format, String line, GeneticType geneticTpe) {
        /*String[] fieldValues = line.split("\t");
        if (fieldValues[2].toLowerCase().equals("cds") && format == KnownAnnoFormat.GFF) {
            geneticTpe = GeneticType.CDS;
        }*/
    /* String[] lineArray = FilePiece.treatNullFieldValue(line);
        if (format == KnownAnnoFormat.BED) {
            return  BEDPiece.readBEDPiece(lineArray, geneticTpe);
        } else if (format == KnownAnnoFormat.GFF) {
            return  GFFPiece.readGFFPiece(lineArray, geneticTpe);
        } else if (format == KnownAnnoFormat.PTT) {
            return  PTTPiece.readPTTPiece(lineArray, geneticTpe);
        } else if (format == KnownAnnoFormat.VCF) {
            return  VCFPiece.readVCFPiece(lineArray, geneticTpe);
        }
        return null;
    }*/
}
