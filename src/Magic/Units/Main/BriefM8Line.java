/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Main;
import java.util.StringTokenizer;

/**
 *
 * @author QIJ
 */
public class BriefM8Line {

    public String query_id;

    public int q_start;
    public int q_end;
    public int s_start;
    public int s_end;
    
    public boolean q_strand;
    public boolean s_strand;

    public BriefM8Line() {
        query_id = "";
        q_start = 0;
        q_end = 0;
        s_start = 0;
        s_end = 0;
        q_strand=true;
        s_strand=true;
    }
    
    public BriefM8Line(String line)
    {
        StringTokenizer nizer= new StringTokenizer(line, "\t ");
        String token="";
        if (nizer.countTokens() < 12) {
            //System.out.println("Error 1: column number <12");
            //System.out.println(line);
            return;
        }
        this.query_id = nizer.nextToken();
        token = nizer.nextToken();  //for subject_id;

        token =nizer.nextToken();  //for identity
        token =nizer.nextToken();  //for align_len
        token =nizer.nextToken();    //for mismatch
        token =nizer.nextToken();  //for gaps

        this.q_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.q_end = Integer.valueOf(nizer.nextToken()).intValue();
        ////System.out.println("q: "+this.q_start+" "+this.q_end);
        this.q_strand=true;
        
        this.s_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.s_end = Integer.valueOf(nizer.nextToken()).intValue();
        ////System.out.println("s: "+this.s_start+" "+this.s_end);
        this.s_strand=true;
        if (this.s_start > this.s_end) {
            int temp = this.s_start;
            this.s_start = this.s_end;
            this.s_end = temp;
            this.s_strand=false;
        }

        token =nizer.nextToken();   //for e-value
        token =nizer.nextToken(); //for bit-score
    }

    public String toString()
    {
        return this.query_id+": "+" | "+this.query_id+": "+this.s_start+","+this.s_end+","+this.s_strand;
    }
}
