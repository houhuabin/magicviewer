/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 *
 * @author lenovo
 */
public class Remove0D {

    public String file;

    public Remove0D(String file) {

        this.file = file;
    }

    public void remove() {
        try {
            FileInputStream in = new FileInputStream(file);
            BufferedInputStream bi = new BufferedInputStream(in);
            /*  FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);*/

            FileOutputStream outtream = new FileOutputStream(file + "_temp");


            byte[] changeLine = new byte[1];
            changeLine[0] = 0x0A;

            byte lastRead = 0;


            byte[] inbyte = new byte[1];

            int i = 0;
            while (bi.read(inbyte) > 0) {


                if (inbyte[0] == 0x0A && lastRead == 0x0D) {
                } else {
                    if (i != 0) {
                        outtream.write(lastRead);
                    }
                }

                lastRead = inbyte[0];

                i++;

            }
            outtream.write(lastRead);
            in.close();
            bi.close();
            outtream.close();
         


        } catch (Exception e) {
            ReportInfo.reportError("File not found", e);
        }

        FileUtil.replaceFile(file, file + "_temp");

    }

    public static void main(String argvs[]) {
        Remove0D rd = new Remove0D("E:\\chrXYZ.sort.bam.bai");
        rd.remove();

    }
}

