/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Main;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 *
 * @author QIJ
 */
public class M8Line {

    public String query_id;
    public String subject_id;
    public float identity;
    public int align_length;
    public int mismatches;
    public int gaps;
    public int q_start;
    public int q_end;
    public int s_start;
    public int s_end;
    public float e_value;
    public float bit_score;
    
    public boolean q_strand;
    public boolean s_strand;

    public M8Line() {
        query_id = "";
        subject_id = "";
        identity = 0;
        align_length = 0;
        mismatches = 0;
        gaps = 0;
        q_start = 0;
        q_end = 0;
        s_start = 0;
        s_end = 0;
        e_value = 0;
        bit_score = 0;
        q_strand=true;
        s_strand=true;
    }
    
    public M8Line(String line,boolean common_blast)
    {
        if(common_blast) setLine1(line);
        else setLine2(line);
    }

    public void setLine1(String line) {
        StringTokenizer nizer= new StringTokenizer(line, "\t ");
        if (nizer.countTokens() < 12) {
            //System.out.println("Error 1: column number <12");
            //System.out.println(line);
            return;
        }
        this.query_id = nizer.nextToken();
        this.subject_id = nizer.nextToken();

        

        this.identity = Float.valueOf(nizer.nextToken()).floatValue()/100;
        this.align_length = Integer.valueOf(nizer.nextToken()).intValue();
        this.mismatches = Integer.valueOf(nizer.nextToken()).intValue();
        this.gaps = Integer.valueOf(nizer.nextToken()).intValue();

        this.q_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.q_end = Integer.valueOf(nizer.nextToken()).intValue();
        this.q_strand=true;
        if (this.q_start > this.q_end) {
            int temp = this.q_start;
            this.q_start = this.q_end;
            this.q_end = temp;
            this.q_strand=false;
        }

        this.s_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.s_end = Integer.valueOf(nizer.nextToken()).intValue();
        this.s_strand=true;
        if (this.s_start > this.s_end) {
            int temp = this.s_start;
            this.s_start = this.s_end;
            this.s_end = temp;
            this.s_strand=false;
        }

        this.e_value = Float.valueOf(nizer.nextToken()).floatValue();
        this.bit_score = Float.valueOf(nizer.nextToken()).floatValue();
    }

    public void setLine2(String line) {
        StringTokenizer nizer= new StringTokenizer(line, "\t ");
        if (nizer.countTokens() < 12) {
            //System.out.println("Error 1: column number <12");
            //System.out.println(line);
            return;
        }
        this.subject_id = nizer.nextToken();
        this.query_id = nizer.nextToken();



        this.identity = Float.valueOf(nizer.nextToken()).floatValue()/100;
        this.align_length = Integer.valueOf(nizer.nextToken()).intValue();
        this.mismatches = Integer.valueOf(nizer.nextToken()).intValue();
        this.gaps = Integer.valueOf(nizer.nextToken()).intValue();

        this.s_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.s_end = Integer.valueOf(nizer.nextToken()).intValue();
        this.s_strand=true;
        if (this.s_start > this.s_end) {
            int temp = this.s_start;
            this.s_start = this.s_end;
            this.s_end = temp;
            this.s_strand=false;
        }

        this.q_start = Integer.valueOf(nizer.nextToken()).intValue();
        this.q_end = Integer.valueOf(nizer.nextToken()).intValue();
        this.q_strand=true;
        if (this.q_start > this.q_end) {
            int temp = this.q_start;
            this.q_start = this.q_end;
            this.q_end = temp;
            this.q_strand=false;
        }

        if(!q_strand) {
            q_strand=!q_strand;
            s_strand=!s_strand;
        }

        this.e_value = Float.valueOf(nizer.nextToken()).floatValue();
        this.bit_score = Float.valueOf(nizer.nextToken()).floatValue();
    }

    public String toString()
    {
        String s= query_id+"\t"+subject_id+"\t"+new DecimalFormat("0.00").format(identity*100)+"\t"+
                align_length+"\t"+mismatches+"\t"+gaps+"\t";
        if(q_strand) s+=q_start+"\t"+q_end+"\t";
        else s+=q_end+"\t"+q_start+"\t";
        if(s_strand) s+=s_start+"\t"+s_end+"\t";
        else s+=s_end+"\t"+s_start+"\t";
        s+=e_value+"\t"+new DecimalFormat("0.0").format(bit_score);

        return s;
    }
}
