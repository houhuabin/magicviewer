/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.Units.Color.ColorRep;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import Magic.Units.Piece.ViewPiece.ViewPiece;
import java.awt.Color;
import utils.FieldUtil;

/**
 *
 * @author Huabin Hou
 */
public class BEDPiece extends FilePiece {

    /*public static PieceField[] BEDFormat = {PieceField.chrom, PieceField.start, PieceField.end, PieceField.id, PieceField.score,
    PieceField.strand, PieceField.thickStart, PieceField.thickEnd, PieceField.color, PieceField.blockCount, PieceField.blockSizes, PieceField.blockStarts};*/
    @Override
    public Piece readPiece(String[] lineArray, GeneticType geneticTpe) {

        GeneticPiece geneticPiece = GeneticPiece.createInstance(geneticTpe);
        Piece piece = new Piece(geneticPiece, new BEDPiece());
        ViewPiece viewPiece = piece.viewPiece;
        geneticPiece.chrom = lineArray[0];
        geneticPiece.start = FieldUtil.getNullInteger(lineArray[1]);
        geneticPiece.end = FieldUtil.getNullInteger(lineArray[2]);
        if (lineArray.length > 3) {
            geneticPiece.id = lineArray[3];
            if (!lineArray[4].equals("0")) {
                int index = (int) (9 - FieldUtil.getNullInteger(lineArray[4]) / 112);
                viewPiece.color = ColorRep.GrayColor[index];
            }
            geneticPiece.strand = FieldUtil.getStrandValue(lineArray[5]);


            Piece subPieceThick = new Piece();
            subPieceThick.geneticPiece.strand = geneticPiece.strand;
            subPieceThick.geneticPiece.start = FieldUtil.getNullInteger(lineArray[6]);
            subPieceThick.geneticPiece.end = FieldUtil.getNullInteger(lineArray[7]);
            //  subPieceList.add(subPieceThick);



            if (!lineArray[5].equals("0")) {
                String[] tempSt2 = lineArray[8].split(",");
                viewPiece.color = new Color(FieldUtil.getNullInteger(tempSt2[0]), FieldUtil.getNullInteger(tempSt2[1]), FieldUtil.getNullInteger(tempSt2[1]));
            }
            Piece[] subPieces = new Piece[FieldUtil.getNullInteger(lineArray[9])];
            String[] blockSizes = lineArray[10].split(",");
            String[] blockStarts = lineArray[11].split(",");
            for (int i = 0; i < subPieces.length; i++) {
                Piece subPiece = new Piece();
                subPiece.geneticPiece.strand = geneticPiece.strand;
                subPiece.geneticPiece.start = FieldUtil.getNullInteger(blockStarts[i]) + geneticPiece.start;
                subPiece.geneticPiece.end = subPiece.geneticPiece.start + FieldUtil.getNullInteger(blockSizes[i]) - 1;
                // subPieceList.add(subPiece);
            }
        }
        return piece;
    }
}
