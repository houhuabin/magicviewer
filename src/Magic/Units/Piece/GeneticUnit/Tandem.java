/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Piece.GeneticUnit;

/**
 *
 * @author QIJ
 */
public class Tandem {
    public int start=0;
	public int end=0;
	public boolean strand=true;
	public String content="";
	public int unit_size=0;
	public float copy_num=0;
	public String unit_seq="";

	public void print()
	{
		char ch='+';
		if(!strand) ch='-';
		//System.out.format("%d %d %c %s %d %f %s\n",start,end,ch,content,unit_size,copy_num,unit_seq);
	}
}
