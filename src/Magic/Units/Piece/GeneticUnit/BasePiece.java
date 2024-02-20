/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Piece.GeneticUnit;

/**
 *
 * @author Huabin Hou
 */
public class BasePiece  implements Cloneable {
    public String chrom = null;
    public String id = null;
    public Integer start = null;
    public Integer end = null;
    public Boolean strand = null;
    public String sequence=null;
    
    @Override
     public Object clone() {
        BasePiece o = null;
        try {
            o = (BasePiece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    

}
