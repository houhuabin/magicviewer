/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.File;

import Magic.IO.ReadData;
import Magic.Units.Main.FastaRead;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;


/**
 *
 * @author lenovo
 */
public class FastaPack {

    public static int oneLineNum = 50;

    public FastaPack(String file) throws Exception {
        Vector<FastaRead> fastaV = ReadData.readFasta(file);
        OutputFasta(fastaV, file);
    }

    public static void OutputFasta(Vector<FastaRead> fastaV, String outputFile) throws Exception {

        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        for (FastaRead fr : fastaV) {
            OutputFastaRead(fr, bw);
        }
        bw.close();
        fw.close();
    }

    public static void OutputFastaRead(FastaRead fr, BufferedWriter bw) throws Exception {
        bw.write(">"+fr.id);

        bw.write("\n");
        int lineNum = fr.sequence.length / FastaPack.oneLineNum;
        int i = 0;

        for (i = 0; i < lineNum; i++) {
            //  System.out.println();
            bw.write(fr.sequence, i * FastaPack.oneLineNum, FastaPack.oneLineNum);
            bw.write("\n");
        }
        bw.write(fr.sequence, i * FastaPack.oneLineNum, fr.sequence.length % FastaPack.oneLineNum);
          bw.write("\n");

    }

    public static void main(String[] argvs) {
        try {

            new FastaPack("E:\\lvlu\\Hs_RNA.fa");
           // new FastaPack("E:\\pKF3-94.fas");
           // new FastaPack("E:\\pKF3-140.fas");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
