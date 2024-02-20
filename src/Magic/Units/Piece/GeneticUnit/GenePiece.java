/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece.GeneticUnit;

import Magic.Analysis.Merage.MergeAnnoImpl;
import Magic.Analysis.Merage.MergePara;
import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.Piece;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Huabin Hou
 */
public class GenePiece extends GeneticPiece {

    public ArrayList<IntronPiece> intron = new ArrayList<IntronPiece>();
    public ArrayList<CDSPiece> CDSs = new ArrayList<CDSPiece>();
    public ArrayList<Utr3Piece> utr3= new ArrayList<Utr3Piece>();
    public ArrayList<Utr5Piece> utr5= new ArrayList<Utr5Piece>();
    public static int spliceSiteLen = 4;

    public GenePiece() {
    }

    @Override
    public void mergePrepare() {
          setGeneSeq();
    }
    

    

     //要求注释文件的顺序，既nsePiece.subPieceList中的数据是按顺序排好的
    public void setGeneSeq() {
        String geneSeq = "";
        for (int i = 0; i <CDSs.size(); i++) {
            CDSPiece currentCDS = CDSs.get(i);
             currentCDS.exonNumber = i + 1;
            geneSeq += MergePara.reference.getFastaSeq(currentCDS.start, currentCDS.end, currentCDS.chrom);
        }
      sequence = geneSeq;
    }

    public void addElement(GeneticType geneticType, GeneticPiece element) {
        if (geneticType == GeneticType.CDS) {
            CDSs.add((CDSPiece) element);
        }
        if (geneticType == GeneticType.Intron) {
            intron.add((IntronPiece) element);
        }
        if (geneticType == GeneticType.Utr3) {
            utr3 .add((Utr3Piece) element);
        }
        if (geneticType == GeneticType.Utr5) {
            utr5.add((Utr5Piece) element);
        }

    }

    @Override
    public SNPType getSNPType(Piece snp, Piece correlatedPiece) {
        SNPType currentSNTType = null;
        currentSNTType = getInExonProcess(snp);
         if(sequence==null)
         {
               return null;
         }
        if (currentSNTType == null) {

            if (isInUtr5(snp,correlatedPiece)) {
               
                currentSNTType = SNPType.UTR5;
            } else if (isInUtr3(snp,correlatedPiece)) {            
                currentSNTType = SNPType.UTR3;
            } else if (isInSpliceSiteProcess(snp, correlatedPiece)) {
            } else if (isInIntron(snp, correlatedPiece)) {
                currentSNTType = SNPType.intron;
            }
        }
        return currentSNTType;

    }

    public boolean isInSpliceSiteProcess(Piece snp, Piece correlatedPiece) {
        ArrayList<CDSPiece> cdsList = CDSs;
        boolean isIn = false;

        for (int i = 0; i < cdsList.size(); i++) {

            CDSPiece currentCDS = cdsList.get(i);
            //如果位于splice site
            if (isSpliceSite(i + 1, cdsList.size(), snp, currentCDS)) {

                correlatedPiece.geneticPiece = currentCDS;
                ((SNPPiece) snp.geneticPiece).snpType = MergePara.SNPType.spliceSite;

                isIn = true;
            }//如果位于cds 内
        }
        return isIn;
    }

    public boolean isSpliceSite(int cdsIndex, int cdsCount, Piece snp, CDSPiece cds) {
        if (cdsCount <= 1) {
            return false;
        }


        if (cdsIndex == 1) {
            if (Math.abs(cds.end - snp.geneticPiece.end) < spliceSiteLen) {
                return true;
            }
        } else if (cdsIndex == cdsCount) {
            if (Math.abs(snp.geneticPiece.start - cds.start) < spliceSiteLen) {
                return true;
            }
        } else {
            if (Math.abs(snp.geneticPiece.start - cds.start) < spliceSiteLen || Math.abs(cds.end - snp.geneticPiece.end) < spliceSiteLen) {
                return true;
            }
        }
        return false;
    }

    public boolean isInUtr3(Piece snp, Piece correlatedPiece) {
        // if(isInGeneticLocation(snp.geneticPiece, gene.utr3))
        // System.out.println(snp.geneticPiece.start+"--------"+gene.utr3.start+"-------"+gene.utr3.end);
         return isInElement( snp,  correlatedPiece, utr3);
    }

    public boolean isInUtr5(Piece snp, Piece correlatedPiece) {
         return isInElement( snp,  correlatedPiece, utr5);
    }

    public SNPType getInExonProcess(Piece snp) {
        ArrayList<CDSPiece> cdsList = CDSs;
        SNPType isIn = null;

        int sequenceStratPosion = 0;
        for (int i = 0; i < cdsList.size(); i++) {

            CDSPiece currentCDS = cdsList.get(i);
          
            //如果位于splice site
            //如果位于cds 内
            if (GeneticPiece.isInGeneticLocation(snp.geneticPiece, currentCDS)) {
                //correlatedPiece.geneticPiece = cds;

                if (snp.geneticPiece.sequence == null) {
                    // isIn =MergePara.SNPType.
                    continue;
                }

                //offset 为snp在gene sequence上的坐标
                int offset = sequenceStratPosion + snp.geneticPiece.start - currentCDS.start;
                String cdsCodon = getCodon(offset, snp);
                String snpCodon = currentCDS.getSnpCodon(offset, snp, cdsCodon);
                isIn = CDSPiece.getSNPTypeByCodon(cdsCodon, snpCodon);
                // //System.out.println(cdsCodon + "-----cdsCodon-----" + snpCodon + "----------" + snp.geneticPiece.start + "--------------" + gene.geneticPiece.strand);
            }
            sequenceStratPosion += currentCDS.end - currentCDS.start + 1;

        }
        return isIn;
    }

     public boolean isInElement(Piece snp, Piece correlatedPiece,ArrayList elements)
     {
         boolean isIn = false;
          if(elements==null)
        {
           return false;
        }
       for (int i = 0; i < elements.size(); i++) {
            GeneticPiece currentIntron = (GeneticPiece) elements.get(i);
            if (GeneticPiece.isInGeneticLocation(snp.geneticPiece, currentIntron)) {
                correlatedPiece.geneticPiece = currentIntron;
                return true;
            }
        }
        return isIn;
     }


    public boolean isInIntron(Piece snp, Piece correlatedPiece) {            
        return isInElement( snp,  correlatedPiece, intron);
    }

   

    public GenePiece(BasePiece cds) {
        this.chrom = cds.chrom;
        this.strand = cds.strand;
        this.start = cds.start;//for 原核生物 对真核生物也无所谓，反正后边会重新计算start end
        this.end = cds.end;//for 原核生物
        this.feature = "gene";
        this.id = cds.id;
    }
}
