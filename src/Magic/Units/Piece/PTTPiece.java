/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.IO.MonitoredFileReadImplement;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import Magic.Units.Track.Track;
import java.util.HashMap;
import utils.FormatCheck;
import utils.FieldUtil;
import utils.ReportInfo;

/**
 *
 * @author lenovo
 */
public class PTTPiece extends FilePiece {

    public String length;
    public String gene;
    public String synonym;
    public String code;
    public String cog;
    // public static PieceField[] PTTFormat = {PieceField.location, PieceField.strand, PieceField.length, PieceField.id, PieceField.gene, PieceField.synonym, PieceField.code, PieceField.cog};
    //因为chrom是再开头定义的，不是在每行定义的。
    public static String chrom;

    @Override
    public void readTrackFile(MonitoredFileReadImplement br, Track track, HashMap result) {
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("Contig")) {
                    PTTPiece.chrom = line.split("\\s+")[1];
                }

                if (!(FormatCheck.isLocation(line.split("\\t+")[0]))) {
                    continue;
                }
                //   System.out.println(line);
                String[] lineArray = FilePiece.treatNullFieldValue(line);
                GeneticType geneticTpe = GeneticType.CDS;
                Piece piece = readPiece(lineArray, geneticTpe);
                piece.viewPiece.parent = track;
                Track.addPiece(result, piece.geneticPiece.chrom, piece);
            }
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    @Override
    public Piece readPiece(String[] lineArray, GeneticType geneticTpe) {


        GeneticPiece geneticPiece = GeneticPiece.createInstance(geneticTpe);
        geneticPiece.chrom = PTTPiece.chrom;

        String[] location = lineArray[0].split("\\.\\.");
        geneticPiece.start = FieldUtil.getNullInteger((location[0]));
        geneticPiece.end = FieldUtil.getNullInteger(location[1]);
        geneticPiece.strand = FieldUtil.getStrandValue(lineArray[1]);
          PTTPiece pTTPiece = new PTTPiece();
        pTTPiece.length = lineArray[2];
        geneticPiece.id = lineArray[3];
        if (lineArray.length == 8) {
            pTTPiece.gene = lineArray[4];
            pTTPiece.synonym = lineArray[5];
            pTTPiece.code = lineArray[6];
            pTTPiece.cog = lineArray[7];
        }

        Piece piece = new Piece(geneticPiece, pTTPiece);
        return piece;
    }
}
