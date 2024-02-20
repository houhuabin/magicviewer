/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author Administrator
 */
public class DifferStat {


    

    public static void main(String argvs[]) throws Exception {
        String file1 = argvs[0];
        String file2 = argvs[1];

        FileReader fr = new FileReader(file1);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            lineNum++;
            String[] lineArray = line.split("\t");


        }
        br.close();
        fr.close();

    }
}
