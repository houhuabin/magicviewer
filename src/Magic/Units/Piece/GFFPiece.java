/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.Units.Piece.GeneticUnit.GenePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import java.util.HashMap;
import utils.FieldUtil;

/**
 *
 * @author Huabin Hou
 */
public class GFFPiece extends FilePiece {

    public String source;
    //
    public Float score;
    public HashMap<String, String> group;
    public static GenePiece currentGene;
    //  public static PieceField[] GFFFormat = {
    // PieceField.chrom, PieceField.source, PieceField.feature, PieceField.start, PieceField.end, PieceField.score, PieceField.strand, PieceField.frame, PieceField.group};

    @Override
    public void readFileInit() {
        GFFPiece.currentGene = null;
    }

    @Override
    public GeneticType getGeneticType(String[] lineArray) {
        if (lineArray[4] == null || lineArray[4].equals(lineArray[3])) {
            //  System.out.println("snp============================");
            return GeneticType.SNP;
        }
        String feature = lineArray[2];
        if (feature != null) {
            if (feature.toUpperCase().equals("CDS")) {
                return GeneticType.CDS;
            }
            if (feature.toUpperCase().equals("3-UTR")) {
                return GeneticType.Utr3;
            }
            if (feature.toUpperCase().equals("5-UTR")) {
                return GeneticType.Utr5;
            }
            if (feature.toUpperCase().equals("INTRON")) {
                return GeneticType.Intron;
            }
            if (feature.toUpperCase().equals("MRNA")) {
                return GeneticType.Gene;
            }
        }
        return GeneticType.Common;
    }
    //  public String[] geneFeature = {"CDS", "3-UTR", "INTRON", "5-UTR"};

    @Override
    public Piece readPiece(String[] lineArray, GeneticType geneticTpe) {


        GeneticPiece geneticPiece = GeneticPiece.createInstance(geneticTpe);
        // GFFPiece gFFPiece=new GFFPiece();
        geneticPiece.chrom = lineArray[0];

        geneticPiece.feature = lineArray[2];
        //System.out.println(lineArray[3]);
        geneticPiece.start = FieldUtil.getNullInteger(lineArray[3]);
        geneticPiece.end = FieldUtil.getNullInteger(lineArray[4]);

        // System.out.println(lineArray[5]+"  ====  "+lineArray[6]);
        geneticPiece.strand = FieldUtil.getStrandValue(lineArray[6]);
        geneticPiece.frame = FieldUtil.getNullInteger(lineArray[7]);
       
        Piece piece = null;


        if (geneticTpe != GeneticType.Common && currentGene != null && geneticTpe != GeneticType.Gene) {
            // System.out.println(" null----------------");
            currentGene.addElement(geneticTpe, geneticPiece);
        } else {
            GFFPiece gff = new GFFPiece();
            gff.source = lineArray[1];
            gff.score = FieldUtil.getNullFloat(lineArray[5]);

            piece = new Piece(geneticPiece, gff);
            if (lineArray.length > 8) {
                gff.group = piece.getGroup(lineArray[8]);
            }

            if (geneticTpe == GeneticType.Gene) {
                GFFPiece.currentGene = (GenePiece) geneticPiece;
                //  System.out.println(" ----------------    "+currentGene);
            }
        }
        return piece;
    }
}
