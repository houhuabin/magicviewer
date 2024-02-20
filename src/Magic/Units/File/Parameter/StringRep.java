/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.File.Parameter;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author QIJ
 */
public class StringRep {
    public static final String PROJECT_NAME="MagicViewer";
    public static final String PROJECT="PROJECT";
    public static final String SEQS = "SEQS";
    
    public static final String MUMMER = "MUMMER";
    public static final String CONTIGS = "CONTIGS";
    public static final String CLUSTALW = "CLUSTALW";
    public static final String MUSCLE = "MUSCLE";
    public static final String SNPS="SNPS";
   // public static final String GENES="GENES";
    public static final String READS="READS";
    public static final String GENOME="GENOME";
    public static final String CON="CON_";
    public static final String ALIGN="ALIGN";
    public static final String R454="R454";
    public static final String SOLEXA="SOLEXA";
    
    public static final String OLIGO="OLIGO";
    public static final String NAME="NAME";
    public static final String ANNO="ANNO";
     public static final String GENE="GENE";
    
    public static final String FASTA="FASTA";
    public static final String FASTQ="FASTQ";
    public static final String NOT_AVAILABLE="NOT_AVAILABLE";
    public static final String AutoCluster="AutoCluster";
    public static final String UserDefineCluster="UserDefineCluster";
    public static final String NoCluster="NoCluster";
    public static final String NotCDS="NotCDS";
   
    public static final String NULL="NULL";

     public static final String BED="BED";
    public static final String GFF="GFF";
    public static final String PTT="PTT";

    public static final String GBK="GBK";
    public static final String EMBL="EMBL";
    public static final String CDS="CDS";
    public static final String PROTEIN="PROTEIN";
    public static final String TRAN="TRNA";
    public static final String RRAN="RRNA";

     public static final String BSMAP="BSMAP";
    // public static final String CGI130="CGI130";

    public static final char SUBSTITUTION='S';
    public static final char INDEL='I';
  //  public static final char TANDEM='T';
    public static final char HOMO='O';
    public static final char HETERO='E';
//    public static final String HOMO="HOMOZY";
//    public static final String HETERO="HETERO";
//    public static final String INDEL="INDEL";
//    public static final String MULTI_INDEL="MULTI_INDEL";
    public static final String TANDEM="TANDEM";
    public static final String INCLUDING_HETERO="INCLUDING_HETERO";

    //for file type
    public static final String JAA="JAA";
    public static final String CLUSTER="CLUSTER";
    public static final String BLASTN="BLASTN";
    public static final String BLASTNM8="BLASTNM8";
	public static final String READINDEX="READINDEX";
	public static final String BIS="BIS";
        public static final String UCSC_GENE="UCSC_GENE";
    public static final String CPG130="CPG130";
    public static final String MP="MP";
    public static final String CGI="CGI";
    public static final String CONTIGINFO="CONTIGINFO";
    public static final String THREECOLUMN="3COLUMN";
    public static final String FOURCOLUMN="4COLUMN";

    public static final String GELI="GELI";
      public static final String VCF="VCF";
  
    public static final String UNKNOWN="UNKNOWN";
    public static final String UNEXIST="UNEXIST";



      public static final String MAQ="MAQ";
      public static final String SOAP2="SOAP2";
      public static final String BOWTIE="BOWTIE";
      public static final String SAM="SAM";
      public static final String BAM="BAM";
      public static final String BLAST = "BLAST";
     public static final String PSL="PSL";//BLAT
     public static final String ZOOM="ZOOM";

    //for track type
    public static final String PIECE="PIECE";

    //for log
    public static final String TYPE="TYPE";
    public static final String PROGRESS="PROGRESS";
    public static final String START="...";
    public static final String DONE="DONE";
    public static final String FORMATDB="FORMATDB";
    public static final String FILTER="FILTER";
    public static final String DISTRIBUTE="DISTRIBUTE";
    public static final String TANDEM_FIND="TANDEM_FIND";
    public static final String SNP_DETECTION="SNP_DETECTION";
    public static final String END="END";
    public static final String VIEWER_LOG="VIEWER_LOG";
    public static final String INPUT_PARTITION_NUM="INPUT_PARTITION_NUM";
    public static final String WIN_LEN="WIN_LEN";
    public static final String WIN_STEP="WIN_STEP";
    public static final String PGA="PGA";
    public static final String MGCOMPARISON="MGCOMPARISON";
    public static final String PHYML="PHYML";
    public static final String R="R";
    public static final String C="C";
    public static final String READS_FILE="READS_FILE";
    public static final String QUALITIES_FILE="QUALITIES_FILE";
    public static final String REFERENCE_FILE="REFERENCE_FILE";
    public static final String GENE_FILE="GENE_FILE";

    //for viewmode
    public static final String NONOVERLAP="NONOVERLAP";
    public static final String SINGLE_LINE="SINGLE_LINE";
    public static final String TRIM="TRIM";


    public static final String SHOW_ARROW="SHOW_ARROW";
    public static final String HIDE_ARROW="HIDE_ARROW";


    



    public static Vector<String> getFileFormats(){
        Vector<String> vec=new Vector<String>();
        vec.add(StringRep.JAA);
        vec.add(StringRep.SNPS);
        vec.add(StringRep.BLASTN);
        vec.add(StringRep.BLASTNM8);
        vec.add(StringRep.CLUSTER);
        vec.add(StringRep.THREECOLUMN);
        vec.add(StringRep.FOURCOLUMN);
        vec.add(StringRep.CONTIGINFO);
        vec.add(StringRep.TANDEM);
        vec.add(StringRep.MAQ);
        vec.add(StringRep.GFF);
        vec.add(StringRep.PTT);
        vec.add(StringRep.UNKNOWN);
        vec.add(StringRep.UNEXIST);

        return vec;
    }

    public static HashMap<Character,Color> getDNA2Color() {
        HashMap<Character,Color> dna2color=new HashMap<Character,Color>();
        dna2color.put('A',Color.RED);
        dna2color.put('T',Color.BLUE);
        dna2color.put('C',Color.GREEN);
        dna2color.put('G',Color.YELLOW);

        return dna2color;
    }
}
