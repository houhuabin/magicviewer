/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.File.Parameter;

import java.io.Serializable;

/**
 *
 * @author QIJ
 */
public class Parameters  implements Serializable{
    //for blast
    public static  int BATCH_READS_NUM=10000;
     public static  int R454_MIN_DIFF=10;
    //for filter
   
   /* public  boolean USE_SOLEXA_MIN_BP=true;
    public  boolean USE_SOLEXA_MIN_IDENTITY=true;
    public  int SOLEXA_MIN_BP=32;
    public  double SOLEXA_MIN_IDENTITY=0.94;
    public  int SOLEXA_MIN_DIFF=5;*/

    public static  boolean HAVE_454=false;
    public static  boolean HAVE_SOLEXA=false;
    //for clustalw
 
    public static  final int READ_END_LEN=10;    //all reads will be add 10 G on both start and end
    public static  int FNA_FILE_SIZE=10000000;   //large fasta files will be divided into parts with size=10M

    public static  int WIN_FLANK=20;

    public  boolean PRINT=false;
  

    //for snp prediction
    public static  final double MIN_POLYMORPHISM=0.05;
    public static  final int MIN_SUPPORT=2;
    public static  final double CP_NUM_DIFINE=0.75;	//minimum copy number, for identifying mulitple indels in tandems (making groups), equals to support_len/unit_size, etc. could be 0.5 copy or 2 copy
    public static  final int NQS_WIN=3;
    public static  final int NQS_QUALITY=16;
    public static  final int WIN_TRIM=10; //trim borders of multi-align windows
    public static  final int READ_TRIM=3; //trim read
    public static  final int DEFAULT_SCORE=20;

    public static final int TYPE_NUM = 4;  //for snp evaluation

    public static  final int MAX_MUTA=3; //if substitution number predicted in a 2*MAX_MUTA window exceed MAX_MUTA, clustalw will be check if should be redo by clustalw
    public static  final int GAP_NUM1=3;    //for alignment window, if gap number on reference is smaller than this, it will be kept
    public static  final int GAP_NUM2=10;   //for alignment window, if gap number on reference is smaller than this, it will be kept only no good neighbors found
 

    public static  final int MAX_GAP=10;

 
    //for phyml
    public  final int NAME_LEN=20;
}
