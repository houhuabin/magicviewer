/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Piece.GeneticUnit;

/**
 *
 * @author Huabin Hou
 */
public class IntronPiece extends GeneticPiece {
    //public int upExonNum=0;
   // public int downExonNum=0;
    public String exonNumber;
 public IntronPiece()
 {}
     public IntronPiece(CDSPiece upCds,CDSPiece downCds) {
        // this.upExonNum =upCds.exonNumber;
        // this.downExonNum = downCds.exonNumber;
         exonNumber ="'"+upCds.exonNumber+","+downCds.exonNumber+"'";
         this.start= upCds.end+1;
         this.end= downCds.start-1;
         this.chrom=downCds.chrom;
         this.strand=downCds.strand;
         feature="intron";
         //System.out.println(exonNumber+"=====exonNumber====");
     }

}
