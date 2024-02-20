/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.IO;

import Magic.Units.Piece.GeneticUnit.Tandem;

import Magic.Units.File.Parameter.Log;

import Magic.Units.Main.FastaRead;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Main.DoublePoint;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.io.InvalidClassException;
import utils.ReportInfo;
import utils.StringUtil;

/**
 *
 * @author QIJ
 */
public class ReadData {

    /*    public static ClustalWMatrix readClustalW(String filename, String refer_name, int trim_len) {
    ////System.out.println("Reading clustalw results: " + refer_name+"\t"+filename);
    String line;
    String token;
    StringTokenizer nizer;

    Vector<String> ids = new Vector<String>();
    Vector<StringBuffer> seqs = new Vector<StringBuffer>();

    Vector<String> line_ids = new Vector<String>();
    Vector<String> line_seqs = new Vector<String>();

    try {
    File file = new File(filename);
    if (!file.exists()) {
    //System.out.println("File not found: " + filename);
    return null;
    }
    FileReader fr = new FileReader(filename);
    BufferedReader br = new BufferedReader(fr);

    if ((line = br.readLine()) != null) {
    if (!line.startsWith("CLUSTAL W") && !line.startsWith("MUSCLE")) {
    //System.out.println("Format error? " + filename);
    return null;
    }
    }

    while ((line = br.readLine()) != null) {
    if (line.length() == 0) {
    continue;	//there is one space line and one empty line between two parts
    }

    if (line.startsWith("  ")) //end of each block
    {
    if (ids.size() == 0) {
    for (int i = 0; i < line_ids.size(); i++) {
    ids.add(line_ids.elementAt(i));
    seqs.add(new StringBuffer());
    }
    }
    for (int i = 0; i < line_ids.size(); i++) {
    seqs.elementAt(i).append(line_seqs.elementAt(i));
    }

    line_ids = new Vector<String>();
    line_seqs = new Vector<String>();
    } else {
    nizer = new StringTokenizer(line, " ");
    line_ids.add(nizer.nextToken());
    line_seqs.add(nizer.nextToken());
    }
    }

    fr.close();
    br.close();
    } catch (IOException e) {
    //System.out.println("Exception in readClustalWResults()");
    //System.out.println("file format error: " + filename);
    }

    if (trim_len > 0) {
    for (int i = 0; i < seqs.size(); i++) {
    StringBuffer buff = seqs.elementAt(i);
    int ori_len = buff.length();
    int removed = 0;
    for (int a = 0; a < buff.length(); a++) {
    if (removed == trim_len) {
    break;
    }
    if (buff.charAt(a) == '-') {
    continue;
    }
    buff.deleteCharAt(a);
    a--;
    removed++;
    }

    removed = 0;
    for (int a = buff.length() - 1; a >= 0; a--) {
    if (removed == trim_len) {
    break;
    }
    if (buff.charAt(a) == '-') {
    continue;
    }
    buff.deleteCharAt(a);
    removed++;
    }
    if (buff.length() != ori_len - 2 * trim_len) {
    //System.out.println("ClustalW trim error");
    return null;
    }
    }
    }


    ClustalWMatrix matrix = new ClustalWMatrix();
    String s = filename;
    File f = new File(filename);
    s = f.getName();

    
    nizer = new StringTokenizer(s, ".");
    if (nizer.countTokens() < 2) {
    //System.out.println("Error[7]: Wrong format of filename: " + filename);
    return matrix;
    }

    StringTokenizer nizer1 = new StringTokenizer(nizer.nextToken(), "_");
    token = nizer1.nextToken();
    ////System.out.println("token1="+token);
    matrix.coords.refer_start = Integer.valueOf(token).intValue();
    token = nizer1.nextToken();
    ////System.out.println("token2="+token);
    matrix.coords.refer_end = Integer.valueOf(token).intValue();
    token = nizer1.nextToken();
    if (token.equals("T")) {
    matrix.coords.refer_strand = true;
    } else if (token.equals("F")) {
    matrix.coords.refer_strand = false;
    } else {
    //System.out.println("Error[7]: Wrong format of filename: " + filename);
    return matrix;
    }
    matrix.ids = new String[ids.size()];
    matrix.seqs = new char[ids.size()][];
    for (int i = 0; i < ids.size(); i++) {
    matrix.ids[i] = ids.elementAt(i);
    matrix.seqs[i] = seqs.elementAt(i).toString().toCharArray();
    }

    for (int i = 0; i < matrix.ids.length; i++) {
    if (matrix.ids[i].equals(refer_name)) {
    matrix.refer_index = i;
    }
    ////System.out.println("["+matrix.ids[i]+"]\t["+refer_name+"]");
    }
    if (matrix.refer_index < 0) {
    //System.out.println("index=" + matrix.refer_index + " " + refer_name + " " + filename);
    } else {
    int gap = 0;
    for (int a = 0; a < matrix.seqs[matrix.refer_index].length; a++) {
    if (matrix.seqs[matrix.refer_index][a] == '-') {
    gap++;
    }
    }
    matrix.gap_num = gap;
    }
    setValidAndAbsolute(matrix);
    return matrix;
    }

    public static ClustalWMatrix readClustalW(Vector<String> vec, Point range, String refer_name, int trim_len) {
    //System.out.println("Reading clustalw results: " + refer_name + "\t");
    if (vec == null || vec.size() < 1) {
    return null;
    }
    String line;
    String token;
    StringTokenizer nizer;

    Vector<String> ids = new Vector<String>();
    Vector<StringBuffer> seqs = new Vector<StringBuffer>();

    // Vector<String> line_ids = new Vector<String>();
    // Vector<String> line_seqs = new Vector<String>();

    // line=vec.firstElement();
    

    for (int i = 0; i < vec.size(); i++) {

    nizer = new StringTokenizer(vec.elementAt(i), " ");
    ids.add(nizer.nextToken());
    seqs.add(new StringBuffer(nizer.nextToken()));

    }

    if (trim_len > 0) {
    for (int i = 0; i < seqs.size(); i++) {
    StringBuffer buff = seqs.elementAt(i);
    int ori_len = buff.length();
    int removed = 0;
    for (int a = 0; a < buff.length(); a++) {
    if (removed == trim_len) {
    break;
    }
    if (buff.charAt(a) == '-') {
    continue;
    }
    buff.deleteCharAt(a);
    a--;
    removed++;
    }

    removed = 0;
    for (int a = buff.length() - 1; a >= 0; a--) {
    if (removed == trim_len) {
    break;
    }
    if (buff.charAt(a) == '-') {
    continue;
    }
    buff.deleteCharAt(a);
    removed++;
    }
    if (buff.length() != ori_len - 2 * trim_len) {
    //System.out.println("ClustalW trim error");
    return null;
    }
    }
    }


    ClustalWMatrix matrix = new ClustalWMatrix();
    matrix.coords.refer_start = range.x;
    matrix.coords.refer_end = range.y;
    matrix.coords.refer_strand = true;

    matrix.ids = new String[ids.size()];
    matrix.seqs = new char[ids.size()][];
    for (int i = 0; i < ids.size(); i++) {
    matrix.ids[i] = ids.elementAt(i);
    matrix.seqs[i] = seqs.elementAt(i).toString().toCharArray();
    }

    for (int i = 0; i < matrix.ids.length; i++) {
    // //System.out.println("ids"+" i:  "+matrix.ids[i]);
    if (matrix.ids[i].equals(refer_name)) {
    matrix.refer_index = i;
    }
    ////System.out.println("["+matrix.ids[i]+"]\t["+refer_name+"]");
    }
    if (matrix.refer_index < 0) {
    //System.out.println("index=" + matrix.refer_index + " " + refer_name);
    } else {
    int gap = 0;
    for (int a = 0; a < matrix.seqs[matrix.refer_index].length; a++) {
    if (matrix.seqs[matrix.refer_index][a] == '-') {
    gap++;
    }
    }
    matrix.gap_num = gap;
    }
    setValidAndAbsolute(matrix);
    return matrix;
    }*/
    public static Object openObject(String file) throws Exception {
        // GZIPInputStream gzfin = null;
        Object rb = null;

        FileInputStream fin = new FileInputStream(file);
        BufferedInputStream finb = new BufferedInputStream(fin);
        ObjectInputStream in = new ObjectInputStream(finb);
        rb = in.readObject();
        in.close();
        finb.close();
        fin.close();
        return rb;

    }

    /*    public static void setValidAndAbsolute(ClustalWMatrix matrix) {
    //find valid range for each read
    matrix.valid = new Point[matrix.ids.length];
    for (int i = 0; i < matrix.seqs.length; i++) {
    char[] line = matrix.seqs[i];
    int start = 0;
    int end = line.length - 1;

    if (i == matrix.refer_index) {
    for (int j = 0; j < line.length; j++) //'N' is real, we did not add 'N' in refer and query
    {
    if (line[j] == '-') {
    start++;
    } else {
    break;
    }
    }
    for (int j = line.length - 1; j >= 0; j--) {
    if (line[j] == '-') {
    end--;
    } else {
    break;
    }
    }
    } else {
    for (int j = 0; j < line.length; j++) {
    if (line[j] == '-' || line[j] == 'N') {
    start++;
    } else {
    break;
    }
    }
    for (int j = line.length - 1; j >= 0; j--) {
    if (line[j] == '-' || line[j] == 'N') {
    end--;
    } else {
    break;
    }
    }
    }
    matrix.valid[i] = new Point(start, end);
    ////System.out.println(i+":"+matrix.valid[i]);
    ////System.out.println(new String(matrix.seqs[i]));
    }

    if (matrix.refer_index < 0) {
    return;
    }
    //assign coordinates to absolute
    int[] absolute = new int[matrix.seqs[matrix.refer_index].length];
    for (int i = 0; i < absolute.length; i++) {
    absolute[i] = 0;
    }
    char[] refer_line = matrix.seqs[matrix.refer_index];

    //for refer
    int site1 = matrix.coords.refer_start;
    int site2 = matrix.coords.refer_end;

    for (int i = matrix.valid[matrix.refer_index].x; i <= matrix.valid[matrix.refer_index].y; i++) {
    if (refer_line[i] == '-') {
    absolute[i] = site1 - 1;
    } else {
    absolute[i] = site1;
    site1++;
    }
    }
    if (absolute[matrix.valid[matrix.refer_index].y] != site2) {
    //System.out.println("Error[8] refer wrong: " + absolute[matrix.valid[matrix.refer_index].y] + " " + site2);        //for query
    System.exit(0);
    }

    matrix.absolute = absolute;
    }
     */
    public static char[] readGenomeSequence(String filename) {
        //System.out.println("Reading Genome: " + filename);
        File infile;

        char[] genome = null;
        int number = 0;
        //char ch;
        String string;
        try {
            infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[5] file not found: " + filename);
            }
            FileReader fr = new FileReader(infile);
            BufferedReader br = new BufferedReader(fr);

            string = br.readLine();
            if (string.charAt(0) != '>') {
                //System.out.println(filename + " format error!");
                return genome;
            }
            //genome=new Vector();
            Vector seq_vector = new Vector();
            String regEx = " |\n|\t|\r";
            Pattern pattern = Pattern.compile(regEx);
            while ((string = br.readLine()) != null) {
                ////System.out.println(string);
                Matcher matcher = pattern.matcher(string);
                String s_temp = matcher.replaceAll("");
                s_temp = s_temp.toUpperCase();

                //for(int i=0;i<s_temp.length();i++) genome.add(new Character(s_temp.charAt(i)));
                seq_vector.add(s_temp);
                number += s_temp.length();
            }
            fr.close();
            br.close();

            if (number <= 0) {
                return genome;
            }
            genome = new char[number];
            int current = 0;
            for (int i = 0; i < seq_vector.size(); i++) {
                String s = (String) seq_vector.elementAt(i);
                for (int j = 0; j < s.length(); j++) {
                    genome[current] = s.charAt(j);
                    current++;
                }
            }
        } catch (IOException e) {
            //System.out.println("Exception in readGenomeSequence()");
            //System.out.println("file format error: " + filename);
        }

        //System.out.println("  " + genome.length + " bps loaded");
        return genome;
    }

    public static Vector<FastaRead> readFasta(String fasta_name) {
       // System.out.println("Reading Fasta: " + fasta_name);
        Vector<FastaRead> reads = new Vector<FastaRead>();
        String line;
        StringBuffer seq = new StringBuffer();
        try {
            //read fasta
            File infile = new File(fasta_name);
            if (!infile.exists()) {
              //  System.out.println("Error[11] file not found: " + fasta_name);
            }
            FileReader fr = new FileReader(fasta_name);
            BufferedReader br = new BufferedReader(fr);
            //Vector genome=new Vector();

            FastaRead read = new FastaRead();
            String token = null;
            StringTokenizer nizer;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (seq.length() > 0) {
                        read.sequence = seq.toString().toCharArray();
                    }
                    seq = new StringBuffer();
                    read = new FastaRead();
                    nizer = new StringTokenizer(line, " ");
                    token = nizer.nextToken();
                    read.id = token.substring(1, token.length());

                    reads.add(read);
                    continue;
                }
                //System.out.println(line );
                ////System.out.println(string);
                String regEx = " |\n|\t";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(line);
                String s_temp = matcher.replaceAll("");
                s_temp = s_temp.toUpperCase();

                seq.append(s_temp);
            }
            if (seq.length() > 0) {
                read.sequence = seq.toString().toCharArray();
            }

            fr.close();
            br.close();
        } catch (IOException e) {
            //System.out.println("Exception in readFasta()");
        }

        return reads;
    }

    public static HashMap<String, FastaRead> readFasta(String fasta_name, String quality_name) {
        ////System.out.println("Reading Fasta: " + fasta_name);
        HashMap<String, FastaRead> id2fasta = new HashMap<String, FastaRead>();
        String line;
        StringBuffer seq = new StringBuffer();
        try {
            //read fasta
            File infile = new File(fasta_name);
            if (!infile.exists()) {
                //System.out.println("Error[10] file not found: " + fasta_name);
            }
            FileReader fr = new FileReader(fasta_name);
            BufferedReader br = new BufferedReader(fr);
            //Vector genome=new Vector();

            FastaRead read = new FastaRead();
            String token = null;
            StringTokenizer nizer;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (seq.length() > 0) {
                        read.sequence = seq.toString().toCharArray();
                    }
                    seq = new StringBuffer();
                    read = new FastaRead();
                    nizer = new StringTokenizer(line, " ");
                    token = nizer.nextToken();
                    read.id = token.substring(1, token.length());
                    id2fasta.put(read.id, read);
                    continue;
                }

                ////System.out.println(string);
                String regEx = " |\n|\t";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(line);
                String s_temp = matcher.replaceAll("");
                s_temp = s_temp.toUpperCase();

                seq.append(s_temp);
            }
            if (seq.length() > 0) {
                read.sequence = seq.toString().toCharArray();
            }

            fr.close();
            br.close();
            ////System.out.println("  " + id2fasta.size() + " fasta reads loaded");

            //read quality
            if (quality_name == null) {
                return id2fasta;
            } else {
                infile = new File(quality_name);
                if (!infile.exists()) {
                    return id2fasta;
                }
            }

            fr = new FileReader(quality_name);
            br = new BufferedReader(fr);
            Vector<Integer> qual = new Vector<Integer>();
            String id = "";
            read = null;

            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (read != null) {
                        read.qual = new int[qual.size()];
                        for (int i = 0; i < qual.size(); i++) {
                            read.qual[i] = qual.elementAt(i);
                        }
                    }
                    qual = new Vector<Integer>();
                    nizer = new StringTokenizer(line, " ");
                    token = nizer.nextToken();
                    id = token.substring(1, token.length());
                    read = id2fasta.get(id);

                    if (read == null) {
                        //System.out.println("quality id " + id + " not found in fasta");
                    }
                    continue;
                }

                nizer = new StringTokenizer(line, " ");
                while (nizer.hasMoreTokens()) {
                    qual.add(Integer.valueOf(nizer.nextToken()));
                }
            }
            if (read != null) {
                read.qual = new int[qual.size()];
                for (int i = 0; i < qual.size(); i++) {
                    read.qual[i] = qual.elementAt(i);
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readFasta()");
        }

        return id2fasta;
    }

    public static HashMap<String, int[]> readQuality(String qual_name) {
        ////System.out.println("Reading Quality: " + qual_name);
        HashMap<String, int[]> id2qual = new HashMap<String, int[]>();
        if (qual_name == null) {
            return id2qual;
        }

        String line;
        StringBuffer seq = new StringBuffer();
        try {
            //read fasta            
            File infile = new File(qual_name);
            if (!infile.exists()) {
                //System.out.println("Error[10] file not found: " + qual_name);
                return id2qual;
            }
            FileReader fr = new FileReader(qual_name);
            BufferedReader br = new BufferedReader(fr);
            //Vector genome=new Vector();

            String token = "";
            StringTokenizer nizer;
            Vector<Integer> qual = new Vector<Integer>();
            String id = "";

            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (qual.size() > 0) {
                        int[] array = new int[qual.size()];
                        for (int i = 0; i < qual.size(); i++) {
                            array[i] = qual.elementAt(i);
                        }
                        id2qual.put(id, array);
                    }
                    qual = new Vector<Integer>();

                    nizer = new StringTokenizer(line, " ");
                    token = nizer.nextToken();
                    id = token.substring(1, token.length());
                    continue;
                }

                nizer = new StringTokenizer(line, " ");
                while (nizer.hasMoreTokens()) {
                    qual.add(Integer.valueOf(nizer.nextToken()));
                }
            }
            if (qual.size() > 0) {
                int[] array = new int[qual.size()];
                for (int i = 0; i < qual.size(); i++) {
                    array[i] = qual.elementAt(i);
                }
                id2qual.put(id, array);
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readFasta()");
        }

        return id2qual;
    }

    public static Vector<Tandem> readTandem(String filename) {
        Vector<Tandem> tandems = new Vector<Tandem>();
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[18] file not found: " + filename);
                return tandems;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;
            String token = "";
            StringTokenizer nizer;

            if (((line = br.readLine()) != null)) {
                if (line.startsWith("Tandem Repeats Finder")) {
                    while (((line = br.readLine()) != null)) {
                        if (line.startsWith("Parameters:")) {
                            break;
                        }
                    }
                } else {
                    fr.close();
                    br.close();
                    fr = new FileReader(filename);
                    br = new BufferedReader(fr);
                }
            }

            while (((line = br.readLine()) != null)) {
                if (line.length() == 0) {
                    continue;
                }
                nizer = new StringTokenizer(line, " \t|");
                if (nizer.countTokens() < 15) {
                    continue;
                }
                Tandem tandem = new Tandem();
                tandem.start = Integer.valueOf(nizer.nextToken()).intValue();
                tandem.end = Integer.valueOf(nizer.nextToken()).intValue();

                tandem.unit_size = Integer.valueOf(nizer.nextToken()).intValue();
                tandem.copy_num = Float.valueOf(nizer.nextToken()).floatValue();
                while (nizer.hasMoreTokens()) {
                    token = nizer.nextToken();
                }
                tandem.unit_seq = token;

                tandems.add(tandem);
            }

            fr.close();
            br.close();
        } catch (Exception e) {
            //System.out.println("Exception in readTandem()");
        }

        ////System.out.println(filename+":");
        ////System.out.println("  "+tandems.size()+" tandem repeats loaded");
        //for(int i=0;i<tandems.size();i++) tandems.elementAt(i).print();
        return tandems;
    }


    public static int getLineNum(String filename) {
        int num = 0;
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[13] file not found: " + filename);
                return -1;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
                if (line.length() == 0) {
                    continue;
                }
                num++;
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readFasta()");
        }
        return num;
    }

    public static StringBuffer readLinesInBuffer(String filename) {
        StringBuffer buffer = new StringBuffer();
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[14] file not found: " + filename);
                return buffer;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
                if (line.length() == 0) {
                    continue;
                }
                buffer.append(line);
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readLines()");
        }
        return buffer;
    }

    public static Vector<String> readLinesInVector(String filename) {
        Vector<String> vec = new Vector<String>();
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[15] file not found: " + filename);
                return vec;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
                if (line.length() == 0) {
                    continue;
                }
                vec.add(line);
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readLines()");
        }
        return vec;
    }

    public static String readFirstLine(String filename) {
        String line = null;
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[16] file not found: " + filename);
                return line;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            line = br.readLine();

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readLines()");
        }
        return line;
    }

    public static Vector<String> readMergedFile(String filename, long position) {
        Vector<String> vec = new Vector<String>();
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[17] file not found: " + filename);
                return vec;
            }
            RandomAccessFile rf = new RandomAccessFile(filename, "r");
            rf.seek(position);
            String line;
            while ((line = rf.readLine()) != null) {
                if (line.startsWith("#\t")) {
                    break;
                }
                vec.add(line);
            }

        } catch (IOException e) {
            //System.out.println("Exception in readLines()");
        }
        return vec;
    }

    public static Vector<DoublePoint> readDistribution(String filename) {
        Vector<DoublePoint> vec = new Vector<DoublePoint>();
        try {
            File infile = new File(filename);
            if (!infile.exists()) {
                //System.out.println("Error[17] file not found: " + filename);
                return vec;
            }

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;
            StringTokenizer nizer;
            double x;
            double y;

            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
                if (line.length() == 0) {
                    continue;
                }
                nizer = new StringTokenizer(line, " \t");
                if (nizer.countTokens() < 2) {
                    //System.out.println("Error[8] wrong format: " + nizer.countTokens());
                    //System.out.println(line);
                    continue;
                }

                x = Double.valueOf(nizer.nextToken());
                y = Double.valueOf(nizer.nextToken());
                DoublePoint p = new DoublePoint(x, y);
                vec.add(p);
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println("Exception in readLines()");
        }
        return vec;
    }
}
