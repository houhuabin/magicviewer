/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Main;

/**
 *
 * @author QIJ
 */
public class FastaRead {

    public String id="";
    public char[] sequence=null;
    public int[] qual=null;
    
    public float identity=0;
    public int muta_num=0;

    public void print() {
        //System.out.println(id + " " + identity + " " + muta_num);
    }
}
