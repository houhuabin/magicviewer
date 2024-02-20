/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;

import Magic.Analysis.Table.Operator;
import Magic.Analysis.Table.Operator.RelationType;
import Magic.IO.MonitoredFileReadImplement;
import Magic.Units.File.FileFormat;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.Main.OnelineFormatParse;
import Magic.Units.Piece.GeneticUnit.GeneticPiece.GeneticType;
import Magic.Units.Track.Track;
import Magic.WinMain.MagicFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class FilePiece implements Cloneable {

    public Object clone() {
        FilePiece o = null;
        try {
            o = (FilePiece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public void readTrackFile(MonitoredFileReadImplement br, Track track, HashMap result) {
        String line;
        try {
			readFileInit();
            while ((line = br.readLine()) != null && track.ok2Read) {
                if (line.startsWith("#")) {
                    continue;
                }
                //   System.out.println(line);
                
                String[] lineArray = FilePiece.treatNullFieldValue(line);
                readOneLine(lineArray, track, result);

            }
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public void readFileInit()
    {

    }

    public GeneticType getGeneticType(String[] lineArray) {
        return GeneticType.Common;
    }

    public void readOneLine(String[] lineArray, Track track, HashMap result) {
        GeneticType geneticTpe = getGeneticType(lineArray);
        Piece piece = readPiece(lineArray, geneticTpe);

        //比如对于基因类型的gff文件，每次读一行，当读入的文mRNA既基因时新建一个piece,否则当读入的为cds intron等时，只修改基因，不产生新的piece
        if (piece != null) {
            Track.addPiece(result, piece.geneticPiece.chrom, piece);
            piece.viewPiece.parent = track;
        }
        else
        {
         //  System.out.println(" null==================");
        }
        // return null;
    }

    public Piece readPiece(String[] lineArray, GeneticType geneticTpe) {
        return null;
    }

    /* public HashMap<String, ArrayList<Piece>> getTrackItems(String infile, String contig) {
    //  SystemUtil.printCurrentTime();
    String format = FileFormat.checkFileFormatByContent(infile);
    FileFormat ff = new FileFormat();
    FileFormat.PieceField[] formatDefine = ff.getFormatDefineString(format);
    HashMap result = new HashMap();

    try {
    MonitoredFileReadImplement br = new MonitoredFileReadImplement(infile, MagicFrame.instance.monitor);

    // OnelineFormatParse olfp = new OnelineFormatParse(formatDefine);
    //  HashMap onlineFormat = olfp.getOnlineFormat();

    int lineNum = 0;
    Class cls = Class.forName("Magic.Units.Piece." + format + "Piece");
    String line;
    while ((line = br.readLine()) != null) {
    //   System.out.println(line);
    Piece piece;
    ++lineNum;

    if (line.startsWith("#")) {
    continue;
    }
    if (formatDefine == FileFormat.PTTFormat) {
    if (line.startsWith("Contig")) {
    this.hmtemp.put("contig", line.split("\\s+")[1]);
    }

    if (!(FormatCheck.isLocation(line.split("\\t+")[0]))) {
    continue;
    }

    }

    if (formatDefine == FileFormat.BEDFormat) {
    piece = new NSEPiece();
    } else {
    piece = new Piece();
    }

    piece.viewPiece.parent = this;

    piece.filePiece = ((FilePiece) cls.newInstance());
    setValue(line, formatDefine, piece, onlineFormat, result);
    }

    br.close();
    } catch (Exception ex) {
    ReportInfo.reportError("", ex);
    }

    return result;
    }*/
    public static String[] treatNullFieldValue(String line) {
        String[] lineArray = line.split("\t");
        int len = lineArray.length;
        for (int i = 0; i < len; i++) {
            //System.out.println(lineArray[i]);
            if (lineArray[i].equals(".")) {
                // System.out.println("========================");
                lineArray[i] = null;
            }
        }
        return lineArray;
    }

    public static void main(String[] argv) {
        String hhb = "chrM	Ensembl	cds	3308	4264	.	+	.	ID=ENST00000361390; ";
        String[] tmp = treatNullFieldValue(hhb);
        for (String t : tmp) {
          //  System.out.println(t);

        }
    }

    public void setFieldOperator(Vector<Object> vector, String fieldName) {
        if (fieldName.equals(FileFormat.PieceField.DP.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.large);
            vector.add("5");
        } else if (fieldName.equals(FileFormat.PieceField.HRun.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.small);
            vector.add("10");
        } else if (fieldName.equals(FileFormat.PieceField.MQ.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.large);
            vector.add("20");
        } else if (fieldName.equals(FileFormat.PieceField.AB.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.small);
            vector.add("0.95");
        } else if (fieldName.equals(FileFormat.PieceField.BQ.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.large);
            vector.add("");
        } else if (fieldName.equals(FileFormat.PieceField.GQ.name())) {
            vector.add(Operator.LogicType.AND);
            vector.add(Operator.RelationType.large);
            vector.add("2");
        } else {
            vector.add(Operator.LogicType.none);
            vector.add(Operator.RelationType.none);
            vector.add("");
        }
    }
}
