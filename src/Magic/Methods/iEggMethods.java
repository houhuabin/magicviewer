/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Methods;


import Magic.Units.Main.M8Line;
import Magic.IO.ReadData;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.TaskBase;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.StrandedFeature;
import org.biojava.bio.seq.io.SeqIOTools;
import org.biojava.bio.symbol.Location;

/**
 *
 * @author QIJ
 */
public class iEggMethods {

    public static int catFile(String input, String output,TaskBase monitor) {
        int num=0;
       
     
        try {            
            //System.out.println("From: " + input);
            monitor.appendMessage(" from: " + input);
            if (!new File(input).exists() || !new File(input).isFile()) {
                return num;
            }
            FileReader fr = new FileReader(input);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(fw);

            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0 && line.charAt(0) == '>') {
                    num++;
                }
                bw.write(line + "\n");
            }
            fr.close();
            br.close();

            bw.close();
            fw.close();
        } catch (IOException ex) {
            //System.out.println( ex.getMessage());
        }
        return num;
    }

    public static int catFiles(Vector<String> inputs, String output,TaskBase monitor) {
        int num=0;
        if (inputs == null || inputs.size() == 0) {
            return num;
        }
        
      //  monitor.reSet("Cat files", "Cat files", true,false,1);
        
        try {
            FileWriter fw = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < inputs.size(); i++) {
                String name=inputs.elementAt(i);
                //System.out.println("From: " + name);
                monitor.appendMessage(" from: "+name);
                if(!new File(name).exists() || !new File(name).isFile()) continue;
                FileReader fr = new FileReader(name);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                while ((line = br.readLine()) != null) {
                    if(line.length()>0 && line.charAt(0)=='>') num++;
                    bw.write(line + "\n");
                }
                fr.close();
                br.close();
            }

            bw.close();
            fw.close();
        } catch (IOException ex) {
            //System.out.println( ex.getMessage());
        }
        return num;
    }

    public static int catFiles(String[] inputs, String output,TaskBase monitor) {
        int num=0;
        if (inputs == null || inputs.length == 0) {
            return num;
        }
     
    
        try {
            FileWriter fw = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < inputs.length; i++) {
                String name=inputs[i];
                //System.out.println("From: " + name);
                monitor.appendMessage(" from: "+name);
                if(!new File(name).exists() || !new File(name).isFile()) continue;
                FileReader fr = new FileReader(name);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                while ((line = br.readLine()) != null) {
                    if(line.length()>0 && line.charAt(0)=='>') num++;
                    bw.write(line + "\n");
                }
                fr.close();
                br.close();
            }

            bw.close();
            fw.close();
        } catch (IOException ex) {
            //System.out.println( ex.getMessage());
        }
        return num;
    }

    public static void runExternal(String command) {
        ////System.out.println(command);
        try {
            final Process proc = Runtime.getRuntime().exec(command);
            
            new Thread(new Runnable() {

                public void run() {
                    try {
                        InputStream stderr = proc.getErrorStream();
                        InputStreamReader isr = new InputStreamReader(stderr);
                        BufferedReader br = new BufferedReader(isr);
                        while (br.readLine() != null) {
                        }
                        stderr.close();
                        isr.close();
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(iEggMethods.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();

            InputStream stdin = proc.getInputStream(); //*** Standard output of external command
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            while (br.readLine() != null) {
            }
            stdin.close();
            isr.close();
            br.close();

        } catch (IOException ex) {
            //System.out.println("Error");
            //System.out.println(ex.getMessage());
        }
        ////System.out.println("finished");
    }

    /*
    public static void runExternal1(Vector<String> command) {
        ////System.out.println(command);
        try {
            ProcessBuilder builder=new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process proc = builder.start();
            
            InputStream stdin = proc.getInputStream(); //*** Standard output of external command
            
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            //this "while" can not be annotated, because its function is waitting for end of enternal program
            while ((line = br.readLine()) != null) {
                ////System.out.println(line);
            }
            stdin.close();
            isr.close();
            br.close();


        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
        ////System.out.println("finished");
    }
    */

    public static void runExternalByCPP(String cpp_path,String command) {
        ////System.out.println(command);
        try {
            String cmds=cpp_path+" \""+command+"\"";
            final Process proc = Runtime.getRuntime().exec(cmds);

            InputStream stdin = proc.getInputStream(); //*** Standard output of external command
            //InputStream stderr=proc.getErrorStream();//*** Error output of external command
            //OutputStream os=proc.getOutputStream();  //*** Input of external command

            new Thread(new Runnable() {

                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                    try {
                        while (br.readLine() != null) {
                            ;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(iEggMethods.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();

            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            //this "while" can not be annotated, because its function is waitting for end of enternal program
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
            }
            stdin.close();
            isr.close();
            br.close();

        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        ////System.out.println("finished");
    }

    public static void quickSortInt(Vector<Integer> key, Vector value, int first, int last, boolean increase) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        int mid = key.elementAt((first + last) / 2);
        do {
            if (increase) {
                while (key.elementAt(low) < mid) {
                    low++;
                }
                while (key.elementAt(high) > mid) {
                    high--;
                }
            } else {
                while (key.elementAt(low) > mid) {
                    low++;
                }
                while (key.elementAt(high) < mid) {
                    high--;
                }
            }
            if (low <= high) {
                int temp_key = key.elementAt(low);
                Object temp_value = value.elementAt(low);

                key.setElementAt(key.elementAt(high), low);
                value.setElementAt(value.elementAt(high), low);

                key.setElementAt(temp_key, high);
                value.setElementAt(temp_value, high);

                low++;
                high--;
            }
        } while (low <= high);
        quickSortInt(key, value, first, high, increase);
        quickSortInt(key, value, low, last, increase);
    }

    public static void quickSortFloat(Vector<Float> key, Vector value, int first, int last, boolean increase) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        float mid = key.elementAt((first + last) / 2);
        do {
            if (increase) {
                while (key.elementAt(low) < mid) {
                    low++;
                }
                while (key.elementAt(high) > mid) {
                    high--;
                }
            } else {
                while (key.elementAt(low) > mid) {
                    low++;
                }
                while (key.elementAt(high) < mid) {
                    high--;
                }
            }
            if (low <= high) {
                float temp_key = key.elementAt(low);
                Object temp_value = value.elementAt(low);

                key.setElementAt(key.elementAt(high), low);
                value.setElementAt(value.elementAt(high), low);

                key.setElementAt(temp_key, high);
                value.setElementAt(temp_value, high);

                low++;
                high--;
            }
        } while (low <= high);
        quickSortFloat(key, value, first, high, increase);
        quickSortFloat(key, value, low, last, increase);
    }

    public static void quickSortDouble(Vector<Double> key, Vector value, int first, int last, boolean increase) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        double mid = key.elementAt((first + last) / 2);
        do {
            if (increase) {
                while (key.elementAt(low) < mid) {
                    low++;
                }
                while (key.elementAt(high) > mid) {
                    high--;
                }
            } else {
                while (key.elementAt(low) > mid) {
                    low++;
                }
                while (key.elementAt(high) < mid) {
                    high--;
                }
            }
            if (low <= high) {
                double temp_key = key.elementAt(low);
                Object temp_value = value.elementAt(low);

                key.setElementAt(key.elementAt(high), low);
                value.setElementAt(value.elementAt(high), low);

                key.setElementAt(temp_key, high);
                value.setElementAt(temp_value, high);

                low++;
                high--;
            }
        } while (low <= high);
        quickSortDouble(key, value, first, high, increase);
        quickSortDouble(key, value, low, last, increase);
    }

    public static char transfer(char ch) {
        char new_ch = ch;

        switch (ch) {
            case 'A':
                new_ch = 'T';
                break;
            case 'T':
                new_ch = 'A';
                break;
            case 'C':
                new_ch = 'G';
                break;
            case 'G':
                new_ch = 'C';
                break;
        }

        return new_ch;
    }

  

    
    public static String getSubString(Vector vector, int start, int end) {
        String s = "";
        for (int i = start; i < end; i++) {
            s += (Character) (vector.elementAt(i));
        }
        return s;
    }

    public static String getSubString(char[] seq, int start, int end) {
        String s = "";
        for (int i = start; i < end; i++) {
            s += seq[i];
        }
        return s;
    }

    public static int indexOf(Vector vector, String kstring, int index) {
        if (kstring.length() == 0) {
            return -1;
        }
        if (vector.size() - index < kstring.length()) {
            return -1;
        }
        int current;
        int length = kstring.length();
        String now = " ";
        for (int i = index; i < index + length - 1; i++) {
            now += (Character) (vector.elementAt(i));
        }
        for (int i = index + length - 1; i < vector.size(); i++) {
            now += (Character) (vector.elementAt(i));
            if (now.equals(kstring)) {
                return i - length + 1;
            }
            now = now.substring(1, now.length());
        }
        return -1;
    }

    public static int indexOf(char[] seq, String kstring, int index) {
        if (kstring.length() == 0) {
            return -1;
        }
        if (seq.length - index < kstring.length()) {
            return -1;
        }
        int current;
        int length = kstring.length();
        String now = " ";
        for (int i = index; i < index + length - 1; i++) {
            now += seq[i];
        }
        for (int i = index + length - 1; i < seq.length; i++) {
            now += seq[i];
            if (now.equals(kstring)) {
                return i - length + 1;
            }
            now = now.substring(1, now.length());
        }
        return -1;
    }

    public static int intersect(int s1,int e1,int s2,int e2) {
        int length = Math.min(e1, e2) - Math.max(s1, s2);
        return length;
    }

    public static int intersect(int s1,int e1, Point p2) {
        int length = Math.min(e1, p2.y) - Math.max(s1, p2.x);
        return length;
    }

    public static int intersect(Point p1, Point p2) {
        int length = Math.min(p1.y, p2.y) - Math.max(p1.x, p2.x);
        return length;
    }
    
    public static Point intersect1(Point p1, Point p2) {
        int length = intersect(p1,p2);
        if(length<0) return new Point(-1,-1);
        return new Point(Math.max(p1.x,p2.x),Math.min(p1.y, p2.y));
    }

    public static String checkSeqFormat(String filename) {
        String format=StringRep.NULL;
        
        try
        {
            FileReader fr=new FileReader(filename);
            BufferedReader br=new BufferedReader(fr);
            
            String line;
            if((line=br.readLine())!=null)
            {
                if(line.charAt(0)=='>') format=StringRep.FASTA;
                else if(line.charAt(0)=='@') format=StringRep.FASTQ;
            }
                        
            
            fr.close();
            br.close();
        }catch(IOException e){}
        
        return format;
    }

  public static float getFactorial(int n)
    {
        float num=1;
        for(int i=1;i<=n;i++) num*=i;
        return num;
    }

    public static float getPermutation(int n, int m)
    {
        return getFactorial(n)/getFactorial(n-m);
    }

    public static float getCombination(int n,int m)
    {
        return getFactorial(n)/getFactorial(m)/getFactorial(n-m);
    }

    public static int getGenomeCoverage(int len,String blast_file)
    {        
        boolean[] cover=new boolean[len];
        for(int i=0;i<cover.length;i++) cover[i]=false;
        try
        {
            String line="";
            StringTokenizer nizer;
            FileReader fr=new FileReader(blast_file);
            BufferedReader br=new BufferedReader(fr);
            while((line=br.readLine())!=null)
            {
                if (line.length() == 0 || line.charAt(0) == '#') {
                    continue;
                }
                nizer = new StringTokenizer(line, "\t ");
                if (nizer.countTokens() < 12) {
                    //System.out.println("Error 1: column number <12");
                    //System.out.println(line);
                    System.exit(0);
                }

                M8Line blast = new M8Line(line,true);
                for(int i=blast.s_start;i<=blast.s_end;i++) cover[i-1]=true;
            }
        }
        catch(IOException e){;}
        int num=0;
        for(int i=0;i<cover.length;i++) if(cover[i]) num++;
        return num;
    }

/*
    public static int getFastaNum(String fasta_file) {
        //System.out.println("Get reads number");
        int num = 0;
        try {
            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    num++;
                }
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        return num;
    }
*/
    public static void getFastaLength(String fasta_file,Vector<String> ids,HashMap<String,Integer> map)
    {        
        //System.out.println("Get reads length: "+fasta_file);
        try {
            if(!new File(fasta_file).exists()) return;
            
            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String token = "";
            String id = "";
            StringTokenizer nizer;

            int len=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (id.length() > 0) {
                        if(map!=null) map.put(id, len);
                        len=0;
                    }

                    nizer = new StringTokenizer(line.substring(1), " \t");
                    id = nizer.nextToken();
                    if (id.length() == 0) {
                        //System.out.println("wrong id format in fasta file: " + fasta_file);
                        System.exit(0);
                    }
                    if(ids!=null) ids.add(id);
                }
                else len+=line.length();
            }

            if (id.length() > 0) {
                if(map!=null) map.put(id, len);
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
      //  if(ids!=null) System.out.println(ids.size());
    }

    public static int getFileLineNum(String filename) {
        //System.out.println("Get line number");
        int num = 0;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                num++;
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        return num;
    }

    public static void saveSelectedFasta(String fasta_file,String out_file,HashSet<String> id_set) {
        //System.out.println("Save selected fasta: "+fasta_file);
        int num=0;
        try {
            if(!new File(fasta_file).exists()) return;

            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw=new FileWriter(out_file);
            BufferedWriter bw=new BufferedWriter(fw);

            String line = "";
            String token = "";
            String id = "";
            StringTokenizer nizer;
            
            boolean save=true;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    nizer = new StringTokenizer(line.substring(1), " \t");
                    id = nizer.nextToken();
                    if (id.length() == 0) {
                        //System.out.println("wrong id format in fasta file: " + fasta_file);
                        save=false;
                        continue;
                    }
                    if(id_set.contains(id))
                    {
                        save=true;
                        num++;
                    }
                    else save=false;
                }
                if(save) bw.write(line+"\n");
            }

            fr.close();
            br.close();

            bw.close();
            fw.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }

        //System.out.println(num+" reads were saved");
    }

    public static double[] getReadsLengthCollection(String fasta_file) {
        //System.out.println("Get reads length: "+fasta_file);
        Vector<Integer> vec=new Vector<Integer>();
        try {
            if(!new File(fasta_file).exists()) return null;

            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String id = "";

            int len=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (id.length() > 0) {
                        vec.add(len);
                        len=0;
                    }
                    id=line;
                }
                else len+=line.length();
            }

            if (id.length() > 0) {
                vec.add(len);
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        //System.out.println("size="+vec.size());

        if(vec.size()==0) return null;
        double[] data=new double[vec.size()];
        for(int i=0;i<vec.size();i++) {
            data[i]=vec.elementAt(i);
        }

		return data;
    }

    public static double[] getReadsGCCollection(String fasta_file) {
        //System.out.println("Get reads GC: "+fasta_file);
        Vector<Double> vec=new Vector<Double>();
        try {
            if(!new File(fasta_file).exists()) return null;

            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String id = "";

            int len=0;
            int gc_num=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (id.length() > 0) {
                        vec.add(1.0*gc_num/len);
                        len=0;
                        gc_num=0;
                    }
                    id=line;
                }
                else {
                    len+=line.length();
                    for(int i=0;i<line.length();i++) {
                        if(line.charAt(i)=='g' || line.charAt(i)=='G' || line.charAt(i)=='c' || line.charAt(i)=='C') gc_num++;
                    }
                }
            }

            if (id.length() > 0) {
                vec.add(1.0*gc_num/len);
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        //System.out.println("size="+vec.size());

        if(vec.size()==0) return null;
        double[] data=new double[vec.size()];
        for(int i=0;i<vec.size();i++) {
            data[i]=vec.elementAt(i);
        }

		return data;
    }

    public static Vector<Point> getReadsLengthDistribution(String fasta_file) {
        //System.out.println("Get reads length: "+fasta_file);
        HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
        try {
            if(!new File(fasta_file).exists()) return null;

            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            int len=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (len > 0) {
                        if(map.containsKey(len)) map.put(len, map.get(len)+1);
                        else map.put(len, 1);
                        len=0;
                    }
                }
                else len+=line.length();
            }

            if (len > 0) {
                if(map.containsKey(len)) map.put(len, map.get(len)+1);
                else map.put(len, 1);
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        //System.out.println(map.size());

        Vector<Integer> key=new Vector<Integer>();
        Vector<Point> vec=new Vector<Point>();
        Iterator<Integer> it=map.keySet().iterator();
        while(it.hasNext()) {
            int len=it.next();
            key.add(len);
            vec.add(new Point(len,map.get(len)));
        }
        quickSortInt(key, vec, 0, key.size()-1, true);
		return vec;
    }

    public static int[] getReadsGCDistribution(String fasta_file,double bin_size) {
        //System.out.println("Get reads length: "+fasta_file);

        int num=(int)(1/bin_size)+1;
        int[] hist=new int[num];
        for(int i=0;i<hist.length;i++) hist[i]=0;
        try {
            if(!new File(fasta_file).exists()) return null;

            FileReader fr = new FileReader(fasta_file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            int len=0;
            int gc_num=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                if (line.charAt(0) == '>') {
                    if (len > 0) {
                        hist[(int)(1.0*gc_num/len/bin_size)]++;
                        len=0;
                        gc_num=0;
                    }
                }
                else {
                    len+=line.length();
                    for(int i=0;i<line.length();i++) {
                        if(line.charAt(i)=='g' || line.charAt(i)=='G' || line.charAt(i)=='c' || line.charAt(i)=='C') gc_num++;
                    }
                }
            }

            if (len > 0) {
                hist[(int)(1.0*gc_num/len/bin_size)]++;
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }

		return hist;
    }

    public static String printArray(boolean[] array)
    {
        StringBuffer content=new StringBuffer();
        for(int i=0;i<array.length;i++) content.append(" "+array[i]);
        return content.toString();
    }

    public static String printArray(int[] array)
    {
        StringBuffer content=new StringBuffer();
        for(int i=0;i<array.length;i++) content.append(" "+array[i]);
        return content.toString();
    }

    public static String printArray(double[] array)
    {
        StringBuffer content=new StringBuffer();
        for(int i=0;i<array.length;i++) content.append(" "+array[i]);
        return content.toString();
    }

    public static String printVector(Vector<Object> vec)
    {
        StringBuffer content=new StringBuffer();
        for(int i=0;i<vec.size();i++) content.append(vec.elementAt(i).toString());
        return content.toString();
    }

    public static String toValidName(String name)
    {
        Pattern pattern = Pattern.compile("\\(|\\)|\\;|\\:|\\.|\\,");
        Matcher matcher = pattern.matcher(name);
        return matcher.replaceAll("_");
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[ ] list = dir.listFiles( );
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File file = list[i];
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDir(file);
                }
            }
        }
        dir.delete();
    }
    
    



    public static File searchFile(File dir,String name) {
        File target=null;
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return null;
        }
        ////System.out.println("searching "+dir.getPath());
        if(dir.getName().equals(name)) return dir;
        
        File[ ] list = dir.listFiles( );
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File file = list[i];
                if(file.getName().equals(name)) target=file;
                else if (file.isDirectory()) {
                    target=searchFile(file,name);
                }
                if(target!=null) return target;
            }
        }
        return target;
    }

    public static String check454OrSolexa(String filename) {
        String TYPE=StringRep.UNKNOWN;
        HashSet<Integer> length_set=new HashSet<Integer>();
        int try_num=100;
        int current=0;
        try {
            if(!new File(filename).exists()) return TYPE;

            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            int len=0;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }                
                if (line.charAt(0) == '>') {
                    if(current>=try_num) break;
                    if (len > 0) {
                        ////System.out.println(len);
                        length_set.add(len);
                        len=0;
                    }
                    current++;
                }
                else {
                    len+=line.trim().length();
                }                
            }

            if (len > 0) {
                ////System.out.println(len);
                length_set.add(len);
            }

            fr.close();
            br.close();
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        if(length_set.size()==0) TYPE=StringRep.UNKNOWN;
        else if(length_set.size()==1) TYPE=StringRep.SOLEXA;
        else TYPE=StringRep.R454;
        return TYPE;
    }

    public static void checkExecutableFiles(String EXE_PATH,Vector<String> missed,Vector<String> unrunable)
    {
        String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
        Vector<String> exe_files=new Vector<String>();
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"formatdb");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"blastall");

        exe_files.add(EXE_PATH+FILE_SEPARATOR+"muscle");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"mummer");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"nucmer");

        exe_files.add(EXE_PATH+FILE_SEPARATOR+"pga");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"phyml");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"primer3_core");

        exe_files.add(EXE_PATH+FILE_SEPARATOR+"run_cpp");
        exe_files.add(EXE_PATH+FILE_SEPARATOR+"trf");

        for(int i=0;i<exe_files.size();i++) {
            File file1=new File(exe_files.elementAt(i));
            File file2=new File(exe_files.elementAt(i)+".exe");
            if(!file1.exists() && !file2.exists()) {
                missed.add(exe_files.elementAt(i));
            }
        }

        for(int i=0;i<exe_files.size();i++) {
            //runExternal(exe_files.elementAt(i));
        }
    }
}
