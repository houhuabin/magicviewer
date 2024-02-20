/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public class TwoFile {

    public String file1;
    public String file2;
    public int[] selected1;
    public int[] selected2;
    String[] field1;
    String[] field2;
    public String seperator = "\t";

    public TwoFile(String file1, String file2, int[] selected1, int[] selected2, String[] field1, String[] field2) {

        this.file1 = file1;
        this.file2 = file2;
        this.selected1 = selected1;
        this.selected2 = selected2;
        this.field1 = field1;
        this.field2 = field2;
    }

    public static void simlationEval(String file1, String file2) {
        int[] selected1 = {0, 1};
        int[] selected2 = {0, 1};
        String[] field1 = {"chrom", "site"};
        String[] field2 = {"chrom", "site"};
        TwoFile tf = new TwoFile(file1, file2, selected1, selected2, field1, field2);
        ArrayList<HashMap> al = tf.getEqual();
        System.out.println("total equal: " + al.size());
        System.out.println();
    }

    public ArrayList<HashMap> getEqual() {
        ArrayList<HashMap> h1s = read(file1, selected1, field1);
        ArrayList<HashMap> h2s = read(file2, selected1, field2);
        System.out.println("file1 size:" + h1s.size());
        System.out.println("file2 size:" + h2s.size());


        ArrayList<HashMap> result = new ArrayList<HashMap>();
        for (HashMap h1 : h1s) {
            if (containHashMap(h2s, h1)) {
                result.add(h1);
            }
        }
        return result;
    }

    public boolean containHashMap(ArrayList<HashMap> h2s, HashMap h1) {
        for (HashMap h2 : h2s) {
            if (equal(h2, h1)) {
                return true;
            }
        }
        return false;
    }

    public boolean equal(HashMap h1, HashMap h2) {
        Iterator it = h1.entrySet().iterator();
        boolean result = true;
        while (it.hasNext()) {
            Map.Entry en = (Map.Entry) it.next();
            result = result && containKeyValue(h2, en.getKey(), en.getValue());
        }
        return result;
    }

    public boolean containKeyValue(HashMap h2, Object key, Object value) {
        //  System.out.println(key+"  "+ value +"  "+h2.get(key));

        if (h2.get(key).equals(value)) {
            return true;
        }
        return false;
    }

    public ArrayList<HashMap> read(String file, int[] selected1, String[] field) {
        ArrayList<HashMap> lineArray = null;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader breader = new BufferedReader(fr);
            String line = null;
            lineArray = new ArrayList();
            while ((line = breader.readLine()) != null) {
                if (!line.startsWith("#") && !line.equals("")) {
                    HashMap hm = new HashMap();
                    String[] values = line.split(seperator);
                    for (int i : selected1) {
                        if (i < values.length) {
                            hm.put(field[i], values[i]);
                        }
                    }
                    lineArray.add(hm);
                }
            }
            fr.close();
            breader.close();

        } catch (Exception e) {
            ReportInfo.reportError("File not found", e);
        }
        return lineArray;

    }

    public static void main(String argvs[]) {
        simlationEval("E:\\read\\hhb.sorted.vcf", "E:\\read\\mutation.txt");

    }
}
