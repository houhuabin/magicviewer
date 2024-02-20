/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece.GeneticUnit;

import Magic.Analysis.Merage.MergePara;
import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.Piece.Piece;
import utils.SequanceUtil;

/**
 *
 * @author Huabin Hou
 */
public class GeneticPiece extends BasePiece {

    public String feature;
    public Integer frame = 0;

    public static GeneticPiece createInstance(GeneticType geneticType) {
        if (geneticType == GeneticType.SNP) {
            return new SNPPiece();
        } else if (geneticType == GeneticType.CDS) {
            return new CDSPiece();
        } else if (geneticType == GeneticType.Gene) {
            return new GenePiece();
        } else if (geneticType == GeneticType.Intron) {
            return new IntronPiece();
        } else if (geneticType == GeneticType.Pileup) {
            return new PileupPiece();
        } else if (geneticType == GeneticType.Read) {
            return new ReadPiece();
        } else if (geneticType == GeneticType.Utr3) {
            return new Utr3Piece();
        } else if (geneticType == GeneticType.Utr5) {
            return new Utr5Piece();
        }

        return new GeneticPiece();
    }

    public SNPType getSNPType(Piece snp, Piece correlatedPiece) {
        return null;
    }

    

  public void mergePrepare()
  {

  }

    public String getCodon(int offset, Piece snp) {

        try {
            int remainder = offset % 3;
            //  System.out.println(offset + "--------" + remainder + "---" + gene.geneticPiece.start + "--");
            //  System.out.println(gene.geneticPiece.sequence.length() + "=======" + gene.geneticPiece.start + "==" + gene.geneticPiece.end);
            String condon = sequence.substring(offset - remainder, offset - remainder + 3);
            if (strand) {
                return condon;
            } else {
                return SequanceUtil.reverseComplement(condon);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  //System.out.println(gene.id + "  ========================"+offset+"-----------------"+gene.content.le);
        }
        return null;
    }

    public enum GeneticType {

        SNP, CDS, Gene, Intron, Pileup, Read, Utr3, Utr5, Common
    };


    public static boolean contain(int snpStart, int snpEnd, int cdsStart, int cdsEnd) {
        if (snpStart >= cdsStart && snpEnd <= cdsEnd) {
            return true;
        }
        return false;
    }

    public static boolean overlap(int snpStart, int snpEnd, int cdsStart, int cdsEnd) {
        if (snpStart < cdsStart && snpEnd > cdsStart) {
            return true;
        } else if (snpStart >= cdsStart && snpStart <= cdsEnd) {
            return true;
        }
        return false;
    }

 public static boolean isInGeneticLocation(BasePiece snp, BasePiece gene) {


        if (gene == null) {
            return false;
        }
        if (snp.end == null) {
            snp.end = snp.start;
        }
        return isInLocation(snp.start, snp.end, gene.start, gene.end,MergePara.currentCorrelatePolicy);
    }

    public static boolean isInLocation(int snpStart, int snpEnd, int cdsStart, int cdsEnd,MergePara.CorrelatePolicy currentCorrelatePolicy) {
        if (currentCorrelatePolicy == MergePara.CorrelatePolicy.Overlap) {
            return overlap(snpStart, snpEnd, cdsStart, cdsEnd);
        } else {
            return contain(snpStart, snpEnd, cdsStart, cdsEnd);

        }
    }
    public  static boolean isInPieceLocation(Piece snp, Piece gene) {
        return isInGeneticLocation(snp.geneticPiece, gene.geneticPiece);
    }
}
