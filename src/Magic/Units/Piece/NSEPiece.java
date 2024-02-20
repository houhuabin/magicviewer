/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece;


import Magic.Units.Piece.GeneticUnit.BasePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import java.util.Vector;



/**
 *
 * @author Huabin Hou
 */
public class NSEPiece extends Piece{

   
   // public String feature;
    public  float score;
    public Vector<Piece> subPieceList= new Vector<Piece>();

   
    public NSEPiece() {
        geneticPiece = new GeneticPiece();
       // geneticPiece.start = geneticPiece.end = 0;
       // geneticPiece.strand = true;
       // geneticPiece.content = "";
    }

  
   

    public void mergeGene() {
    }

    
}
