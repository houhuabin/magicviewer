/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Piece.GeneticUnit;

/**
 *
 * @author Huabin Hou
 */
public class ReadPiece extends GeneticPiece{
   //public String sequence;
   public String quality;
   public String cigar;
   public int mappingQuality;
   public Integer windowStart = null;
   public Integer  windowEnd = null;
   public ReadPiece()
   {
   }

}
