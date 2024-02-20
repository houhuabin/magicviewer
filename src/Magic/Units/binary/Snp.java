/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.binary;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class Snp {

    public int[] point;
    public byte[] seq;
    public int num;
    public String fabfileName;
    public Vector<SnpHeadBean> fabHeadVector;//8 byte
    public RandomAccessFile rf;

    public Snp(String fbafile) {
        try {
            this.fabfileName = fbafile;
            this.rf = new RandomAccessFile(fbafile, "r");
            this.fabHeadVector = new Vector<SnpHeadBean>();
            SnpHeadBean fabHead = new SnpHeadBean();
            while (readSnpHead(rf, fabHead)) {
                this.fabHeadVector.add(fabHead);
                if (fabHead.nexSnpBlockStart >= rf.length()) {
                    break;
                }
                rf.seek(fabHead.nexSnpBlockStart);
                //System.out.println(fabHead.nexSnpBlockStart);
                fabHead = new SnpHeadBean();
            }

        //  this.fastabBean.maskStart = this.fastabBean.seqStart + this.fastabBean.seqStart;
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public boolean readSnpHead(RandomAccessFile rf, SnpHeadBean fabHead) {
        try {

            fabHead.nexSnpBlockStart = rf.readLong();
            fabHead.seqNameLen = rf.readInt();
            byte[] seqName = new byte[fabHead.seqNameLen];
            rf.read(seqName);
            fabHead.seqName = new String(seqName);
           
            fabHead.oriSeqLen = rf.readInt();
            fabHead.snpNum = rf.readInt();
            
            fabHead.snpStart = rf.readLong();

        } catch (IOException ex) {
            ReportInfo.reportError("", ex);

        }
        return true;

    }

    public SnpBean getSnp(String seqName)
    {
        SnpBean sb=null;
        try {
           // int seqLen = 0;
           
            for (int i = 0; i < this.fabHeadVector.size(); i++) {
                if (seqName.equals(fabHeadVector.elementAt(i).seqName)) {
                     ////System.out.println(this.fabHeadVector.size()+"  "+fabHeadVector.elementAt(i).snpNum);
                     sb=new SnpBean();
                     int[] point =new int[fabHeadVector.elementAt(i).snpNum];
                    byte[] seq =new byte[fabHeadVector.elementAt(i).snpNum];
                    rf.seek(fabHeadVector.elementAt(i).snpStart);
                     ////System.out.println(fabHeadVector.elementAt(i).snpStart+"==");
                   // //System.out.println(fabHeadVector.elementAt(i).snpStart);
                    for(int j=0;j<fabHeadVector.elementAt(i).snpNum;j++)
                    {
                       
                         point[j]=rf.readInt();
                        
                        seq[j]=rf.readByte();
                        ////System.out.println(seq[j]+"  "+point[j]);

                    }
                    sb.point=point;
                    sb.seq=seq;
                    break;
                   

                   // sequence = byteSeqToStr(sequenceByte, fabHeadVector.elementAt(i).oriSeqLen, Table.binaryByeTable);
                    // //System.out.println(sequence);
                    //result = sequence;
                }
            }
        } catch (IOException ex) {
            ReportInfo.reportError("", ex);
        }
        return sb;
    }
}
