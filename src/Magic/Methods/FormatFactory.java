/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Methods;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.Parameter.Parameters;
import Magic.Units.Gui.Task.FormatConvertTask;
import Magic.Units.Gui.Task.NewProgress;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import utils.ReportInfo;


/**
 *
 * @author QIJ
 */
public class FormatFactory {

    public static void fastq2Fasta(String fastq_file, String fasta_seq_file, String fasta_qual_file) {
        //System.out.println("FastQ -> FastA\n"+fastq_file+"\n"+fasta_seq_file+"\n"+fasta_qual_file);
        File file=new File(fastq_file);
        if(!file.exists() || !file.isFile()) return;       
                
        try {
            FileReader fr = new FileReader(fastq_file);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw1 = new FileWriter(fasta_seq_file);
            BufferedWriter bw1 = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter(fasta_qual_file);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            String line;
            String id;            

            //int current=0;
            while ((line = br.readLine()) != null) {
                if(line.length()==0) continue;
                if (line.charAt(0) != '@') {
                    //System.out.println("Format error. " + line);
                    return;
                }
                //current+=line.length()+1;

                id = line.substring(1);
                fw1.write(">" + id + "\n");
                fw2.write(">" + id + "\n");

                line = br.readLine();
                //current+=line.length()+1;
                fw1.write(line + "\n");

                line = br.readLine();
                //current+=line.length()+1;
                if (line.charAt(0) != '+') {
                    //System.out.println("Format error. " + line);
                    return;
                }

                line = br.readLine();
                //current+=line.length()+1;
                for (int i = 0; i < line.length(); i++) {
                    int qual = line.charAt(i) - 33;
                    
                    if (i != 0) fw2.write(" "+qual);
                    else fw2.write(""+qual);
                }
                fw2.write("\n");
            }

            fr.close();
            br.close();

            bw1.close();
            fw1.close();

            bw2.close();
            fw2.close();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }


    public static int gbk_embl2Others(String in_type,String in_file,String out_type,String out_file) {

        FormatConvertTask lt = new FormatConvertTask( in_type, in_file, out_type, out_file);
            NewProgress monitor = new NewProgress("Loading Annotation", StringRep.START, lt);
            // monitor.startTask();
            if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
                //System.out.println("Error ");
                if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                    ReportInfo.reportError(null, monitor.getException());
                    return ForEverStatic.RET_ERR;
                }
              //  tracks = null;
                return ForEverStatic.RET_CANCEL;
            }
            return ForEverStatic.RET_OK;
    }

    public static void phd2Fasta(String phd_file, String fasta_seq_file, String fasta_qual_file, boolean append) {
        //System.out.println("PHD -> FastA");
        try {
            FileReader fr = new FileReader(phd_file);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw1 = new FileWriter(fasta_seq_file, append);
            BufferedWriter bw1 = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter(fasta_qual_file, append);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            String line;
            String id="ID";
            String token;
            StringTokenizer nizer;

            if((line = br.readLine()) != null)
            {
                nizer=new StringTokenizer(line," \t");                
                token=nizer.nextToken();
                if(nizer.hasMoreTokens()) id=nizer.nextToken();
            }
            
            bw1.write(">"+id+"\n");
            bw2.write(">"+id+"\n");
            while ((line = br.readLine()) != null) {
                if(line.startsWith("BEGIN_DNA")) break;
            }
            
            int length=0;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("END_DNA")) break;
                nizer=new StringTokenizer(line," \t");
                if(nizer.countTokens()<3) continue;
                
                if(length!=0) bw2.write(" ");
                bw1.write(nizer.nextToken());
                bw2.write(nizer.nextToken());                
                length++;
                if(length%60==0)
                {
                    bw1.write("\n");
                    bw2.write("\n");
                }
            }
            
            if (length % 60 != 0) {
                bw1.write("\n");
                bw2.write("\n");
            }            

            fr.close();
            br.close();

            bw1.close();
            fw1.close();

            bw2.close();
            fw2.close();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }

    public static void batchPhd2Fasta(String phd_folder, String fasta_seq_file, String fasta_qual_file) {
        File current_dir = new File(phd_folder);
        if (!current_dir.exists()) {
            //System.out.println("Checking: " + phd_folder + " not exist.");
            return;
        }
        if (!current_dir.isDirectory()) {
            //System.out.println("Checking: " + phd_folder + " is not a folder.");
            return;
        }
        String[] list = current_dir.list();
        if (list == null || list.length == 0) {
            //System.out.println("Checking: " + phd_folder + " is empty.");
            return;
        }

        String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
        for (int i = 0; i < list.length; i++) {
            String name=current_dir+FILE_SEPARATOR+list[i];
            if(!checkPhdFormat(name)) continue;
            phd2Fasta(name, fasta_seq_file, fasta_qual_file, true);
        }
    }
    
    public static boolean checkPhdFormat(String filename)
    {
        boolean is_phd=false;
        try {
            File file=new File(filename);
            if(!file.exists() || !file.isFile()) return is_phd;
            
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer nizer;

            if ((line = br.readLine()) != null) {
                if(line.startsWith("BEGIN_SEQUENCE")) is_phd=true;
            }
            fr.close();
            br.close();

        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
        
        return is_phd;
    }

    public static void alignInfo2Fasta(String align_info_file, String fasta_seq_file, String fasta_qual_file) {
        //System.out.println("AlignInfo -> FastA");
        try {
            FileReader fr = new FileReader(align_info_file);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw1 = new FileWriter(fasta_seq_file);
            BufferedWriter bw1 = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter(fasta_qual_file);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            String line;
            String token;
            StringTokenizer nizer;

            if((line = br.readLine()) != null)
            {
                nizer=new StringTokenizer(line," \t");
                token=nizer.nextToken();
            }

            int current=1;
            bw1.write(">"+"CONTIG_"+current+"\n");
            current++;
            //bw2.write(">"+id+"\n");
            
            int length=0;
            char ch=' ';
            int gap=0;
            while ((line = br.readLine()) != null) {
                if(line.length()==0) continue;
                nizer=new StringTokenizer(line," \t");
                if(nizer.countTokens()<3) continue;

                token=nizer.nextToken();
                ch=nizer.nextToken().charAt(0);
                if(ch=='N') {
                    gap++;                    
                } else {
                    if(gap>=Parameters.MAX_GAP) {
                        if(length>0) {
                            bw1.write(">"+"CONTIG_"+current+"\n");
                            current++;                          
                        }                        
                        length = 0;                        
                    } else if(gap>0) {
                        for(int a=0;a<gap;a++) {
                            bw1.write(""+'N');
                            length++;
                            if (length % 60 == 0) {
                                bw1.write("\n");
                            //bw2.write("\n");
                            }
                        }
                    }
                    gap = 0;
                    bw1.write(""+ch);
                    //bw2.write(nizer.nextToken());
                    length++;
                    if (length % 60 == 0) {
                        bw1.write("\n");
                    //bw2.write("\n");
                    }
                }                
            }

            if (length % 60 != 0) {
                bw1.write("\n");
                //bw2.write("\n");
            }

            fr.close();
            br.close();

            bw1.close();
            fw1.close();

            bw2.close();
            fw2.close();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }


    public static void main(String[] argv)
    {
          //       fasta2Fastq("D:\\AI-F-107.clean.formated","D:\\AI-F-107.clean.formated.fq");

    }
}
