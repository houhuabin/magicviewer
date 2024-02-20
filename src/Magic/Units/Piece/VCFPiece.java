/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.Analysis.Table.Operator.RelationType;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import utils.FieldUtil;

/**
 *
 * @author Huabin Hou
 */
public class VCFPiece extends FilePiece {

    public String ref;
    public String score;
    public String filter;
    public HashMap<String, String> group;
    public String format;
    public HashMap<String, String> groupTwo;
    // public static PieceField[] VCFFormat = {PieceField.chrom, PieceField.start, PieceField.id, PieceField.ref, PieceField.sequence, PieceField.score, PieceField.filter, PieceField.group, PieceField.groupStart, PieceField.groupEnd};
    // public String groupStart;

    @Override
    public Piece readPiece(String[] lineArray, GeneticType geneticTpe) {


        GeneticPiece geneticPiece = GeneticPiece.createInstance(geneticTpe);
        // VCFPiece vCFPiece=new VCFPiece();

        geneticPiece.chrom = lineArray[0];
        geneticPiece.start = FieldUtil.getNullInteger(lineArray[1]);
        geneticPiece.end = geneticPiece.start;
        geneticPiece.id = lineArray[2];
        geneticPiece.sequence = lineArray[4];

        VCFPiece vcf = new VCFPiece();
        vcf.ref = lineArray[3];
        vcf.score = lineArray[5];
        vcf.filter = lineArray[6];

        Piece piece = new Piece(geneticPiece, vcf);
        vcf.group = piece.getGroup(lineArray[7]);
        vcf.groupTwo = getGroupTwo(lineArray[8], lineArray[9]);
        return piece;
    }

    public GeneticType getGeneticType(String[] lineArray) {
        return GeneticType.SNP;
    }

    /* public Float treatFloatFieldValue(String value) {

    if (isNullField(value)) {
    return null;
    } else if (value.equals("Infinity")) {
    return -1.f;
    }
    return Float.valueOf(value);
    }*/
    public static HashMap<String, String> getGroupTwo(String groupStart, String groupEnd) {
        if (Piece.isNullField(groupStart) || Piece.isNullField(groupEnd)) {
            return null;
        }
        String[] groupStartElements = groupStart.split(":");
        String[] groupEndElements = groupEnd.split(":");
        HashMap<String, String> hm = new HashMap();
        for (int i = 0; i < groupStartElements.length; i++) {
            hm.put(groupStartElements[i], groupEndElements[i]);
        }
        return hm;
    }
}
