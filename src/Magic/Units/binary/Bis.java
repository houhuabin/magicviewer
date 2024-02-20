/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.binary;

import Magic.Units.Piece.Piece;
import Magic.Units.Track.Track;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Piece.GeneticUnit.ReadPiece;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ReportInfo;
import utils.BaseTypeConvers;

/**
 *
 * @author Huabin Hou
 */
public class Bis {

    //public BisBean[] fabHeadArray;
    public String bisfileName;
    public RandomAccessFile rf;
    public static long rfPoint;
    public static long fileLen;
     public static int bisBeanlen;

    public Bis(String bisfileName) {
        try {
            this.bisfileName = bisfileName;
            this.rf = new RandomAccessFile(bisfileName, "r");
            long fileLength = rf.length();
            this.fileLen = fileLength;

            int num = (int) (fileLength / bisBeanlen);

            // //System.out.println(num+"   ----------------------------num");
            // this.fabHeadArray = new BisBean[num];
            this.rfPoint = rf.getFilePointer();

            //  this.fastabBean.maskStart = this.fastabBean.seqStart + this.fastabBean.seqStart;
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public BisBean[] getNextBisArray(int point) {

        setPoint(point * bisBeanlen);
        //System.out.println(point + " point -----------------------------------  ");
        return getNextBisArray();

    }

    public static BisBean[] getBisArrayBySeqTrack(int length, Track en) {
      
        BisBean[] bisArray = new BisBean[length];
        for(int i=0; i<length;i++)
        {
        BisBean bisBean = new BisBean();
         bisArray[i]=bisBean;
        }

          if(en==null)
        {
          return bisArray;
        }

        for (int i = 0; i < en.currentPieces.length; i++) {

            for (int j = 0; j < ((Piece) en.currentPieces[i]).geneticPiece.sequence.length(); j++) {
                 
                char currentChar = ((Piece) en.currentPieces[i]).geneticPiece.sequence.charAt(j);
                int currentPoint = ((ReadPiece)( en.currentPieces[i].geneticPiece)).windowStart + j;
                if (currentPoint > 0 && currentPoint <= length ) {
                    if (currentChar == 'A') {
                        bisArray[currentPoint-1].ANum++;

                    } else if (currentChar == 'T') {
                        bisArray[currentPoint-1].TNum++;

                    } else if (currentChar == 'C') {
                        bisArray[currentPoint-1].CNum++;

                    } else if (currentChar == 'G') {
                        bisArray[currentPoint-1].GNum++;

                    }else  if (currentChar == 'N')
                    {
                       bisArray[currentPoint-1].NNum++;
                    }
                    bisArray[currentPoint-1].totalNum++;
                }
                

            }

        }
        return bisArray;

    }

    public BisBean[] getNextBisArray() {
        BisBean[] bisBeanArray = null;
        try {
            int bytenum = 0;
            if (fileLen - rfPoint >= Log.instance().global.alignmentWindowLen * bisBeanlen) {
                bytenum = Log.instance().global.alignmentWindowLen * bisBeanlen;

            } else {
                bytenum = (int) (fileLen - rfPoint);
            }

            //System.out.println(fileLen + " fileLen #################  " + rfPoint + "  bytenum " + bytenum);
            if (bytenum <= 0) {
                return null;
            }
            byte[] result = new byte[bytenum];
            //    //System.out.println(bytenum+" bytenum #################");
            rf.readFully(result);
            this.rfPoint = rf.getFilePointer();
            bisBeanArray = byteAToBisBeanA(result);


        } catch (IOException ex) {
            ReportInfo.reportError("", ex);
        }
        return bisBeanArray;

    }

    public BisBean[] byteAToBisBeanA(byte[] b) {
        int bisNum = b.length / bisBeanlen;

        BisBean[] bisBeanArray = new BisBean[bisNum];
        int j = 0;
        for (int i = 0; i < bisNum; i++) {
            BisBean bisBean = new BisBean();
            bisBean.totalNum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBean.ANum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBean.TNum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBean.CNum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBean.GNum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBean.NNum = BaseTypeConvers.byteToChar(b[j++], b[j++]);
            bisBeanArray[i] = bisBean;
        }
        return bisBeanArray;
    }

    /*   public BisBean[] getNextBisArray(int win) {
    BisBean[] bisBeanArray = null;
    try {
    int bytenum = 0;
    LogBean.getInstance().global.referenceWindow =131072-131072%win;//为了保障Global.referenceWindow 能整除win
    if (fileLen - rfPoint >= LogBean.getInstance().global.referenceWindow * bisBeanlen) {
    bytenum = LogBean.getInstance().global.referenceWindow * bisBeanlen;

    } else {
    bytenum = (int) (fileLen - rfPoint);
    }

    byte[] result = new byte[bytenum];

    rf.readFully(result);
    this.rfPoint = rf.getFilePointer();
    bisBeanArray = byteAToBisBeanA(result,win);


    } catch (IOException ex) {
    ReportError.reportException("", ex);
    }
    return bisBeanArray;

    }*/
    //注意 1<<shift 要能被referenceWindow整除，否则最后一个bisBean出错
    public BisBean[] byteAToBisBeanA(byte[] b, int win) {
        int tempInt = b.length / 12;
        int bisNum;
        if (tempInt % win == 0) {
            bisNum = b.length / win / bisBeanlen;
        } else {
            bisNum = (b.length / win / bisBeanlen) + 1;
        }

        BisBean[] bisBeanArray = new BisBean[bisNum];
        int j = 0;
        for (int i = 0; i < bisNum; i++) {
            BisBean bisBean = new BisBean();
////System.out.println( i+"     !!!!!!!");
            for (int k = 0; (k < win) && (j < b.length); k++) {

                bisBean.totalNum += BaseTypeConvers.byteToChar(b[j++], b[j++]);
                bisBean.ANum += BaseTypeConvers.byteToChar(b[j++], b[j++]);
                bisBean.TNum += BaseTypeConvers.byteToChar(b[j++], b[j++]);
                bisBean.CNum += BaseTypeConvers.byteToChar(b[j++], b[j++]);
                bisBean.GNum += BaseTypeConvers.byteToChar(b[j++], b[j++]);
                bisBean.NNum += BaseTypeConvers.byteToChar(b[j++], b[j++]);

            }
            bisBeanArray[i] = bisBean;
        }
        return bisBeanArray;
    }

    public void setPoint(long point) {
        try {
            rf.seek(point);
            this.rfPoint = point;
        } catch (IOException ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public void close() {
        try {
            this.rf.close();
        } catch (IOException ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        /*
        byte[] b = new byte[2];
        b[0] = 2;
        b[1] = 1;
        char hhb = TypeConvers.byteToChar(b);
        //System.out.println((int) hhb);*/

        //System.out.println(" -----");
        RandomAccessFile rf = new RandomAccessFile("E:\\project\\magicinsight\\chrysmall\\PROJECT\\chrY.bis", "r");
        //System.out.println(rf.length() / 12 + " -----");

        Bis bis = new Bis("4.bis");
        /*
        while (bis.rfPoint < bis.fileLen) {

        BisBean[] bisArray = bis.getNextBisArray(4);
        // //System.out.println(bisArray.length);
        for (int i = 0; i < bisArray.length; i++) {
        //System.out.print(i + " ");
        //System.out.print((int) bisArray[i].ANum + " ");

        //System.out.print((int) bisArray[i].TNum + " ");
        //System.out.print((int) bisArray[i].CNum + " ");
        //System.out.print((int) bisArray[i].GNum + " ");
        //System.out.print((int) bisArray[i].NNum + " ");
        //System.out.print((int) bisArray[i].totalNum + " ");
        // //System.out.println((int) bisArray[i].ANum + bisArray[i].TNum + bisArray[i].CNum + bisArray[i].GNum + bisArray[i].NNum);

        }
        }*/


    }
}
