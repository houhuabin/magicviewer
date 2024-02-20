/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece.GeneticUnit;

import Magic.Analysis.Merage.MergeAnnoImpl;
import Magic.Analysis.Merage.MergePara;
import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.Color.ColorRep;
import Magic.Units.Piece.GELIPiece;
import Magic.Units.Piece.Piece;
import java.awt.Color;
import utils.SequanceUtil;

/**
 *
 * @author Huabin Hou
 */
public class CDSPiece extends GeneticPiece {
  public static Color color=ColorRep.cds;
    public int exonNumber;

    public CDSPiece() {
        feature = "cds";
    }

    @Override
    public void mergePrepare() {
        setCDSSeq();
    }

    //要求注释文件的顺序，既nsePiece.subPieceList中的数据是按顺序排好的
    public void setCDSSeq() {
        sequence = MergePara.reference.getFastaSeq(start, end, chrom);
    }

    public CDSPiece(BasePiece geneticPiece) {
        feature = "cds";
        this.chrom = geneticPiece.chrom;
        this.strand = geneticPiece.strand;
        this.start = geneticPiece.start;
        this.end = geneticPiece.end;
        this.id = geneticPiece.id;
        //  this.exonNumber
    }

    @Override
    public SNPType getSNPType(Piece snp, Piece correlatedPiece) {

        if (snp.geneticPiece.sequence == null) {
            //System.out.println("===========snp.geneticPiece.sequence == null=============");
            return null;
        }
        if (sequence == null) {
            // System.out.println("===========sequence== null=============");
            // System.out.println();
            return null;
        }
        //offset 为snp在gene sequence上的坐标
        int offset = snp.geneticPiece.start - start;
        String cdsCodon = getCodon(offset, snp);
        String snpCodon = getSnpCodon(offset, snp, cdsCodon);
        SNPType st = getSNPTypeByCodon(cdsCodon, snpCodon);
       // System.out.println(snp.geneticPiece.start+"   "+st);
        return st;
    }

    public String getSnpCodon(int offset, Piece snp, String geneCodon) {
        int remainder = offset % 3;

        int realRemainder;
        if (strand) {
            realRemainder = remainder;
        } else {
            realRemainder = 2 - remainder;
        }

        String result = "";
        for (int i = 0; i < geneCodon.length(); i++) {

            if (realRemainder == i) {
                result += getSnpGenotype(snp);
            } else {
                result += geneCodon.charAt(i);
            }
        }
        return result;
    }

    public String getSnpGenotype(Piece snp) {
        String result = "";
        if (snp instanceof GELIPiece) {
            //  //System.out.println(((GELIPiece) snp).bestGenotype + "------GELIPiece----bestGenotype--------");
            result = ((GELIPiece) snp).bestGenotype.replace(((GELIPiece) snp).referenceBase, "");
        } else {
            result += snp.geneticPiece.sequence.charAt(0);
        }
        if (!strand) {
            result = SequanceUtil.reverseComplement(result);
        }
        return result;

    }

    public static SNPType getSNPTypeByCodon(String cdsCodon, String snpCodon) {
        SNPType isIn = null;
        String cdsProtean = MergeAnnoImpl.translateCodon(cdsCodon);
        String snpProtean = MergeAnnoImpl.translateCodon(snpCodon);
        if (cdsProtean.equals("*") && !snpProtean.equals("*")) {
            isIn = MergePara.SNPType.readthrough;
        } else if (snpProtean.equals("*") && !cdsProtean.equals("*")) {
            isIn = MergePara.SNPType.nonsense;
        } else if (snpProtean.equals(cdsProtean)) {
            isIn = MergePara.SNPType.synonymous;
        } else {
            isIn = MergePara.SNPType.missense;
        }
        return isIn;
    }
}
