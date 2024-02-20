/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.IO;




import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
import utils.ReportInfo;

/**
 *
 * @author QIJ
 */
public class WriteData {
   
  



    public static void saveObject(String log_file,Object ob,boolean append) {
       // //System.out.println("Saving to: " + log_file);
        try {
            FileOutputStream fo = new FileOutputStream(log_file,append);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            
            oo.writeObject(ob);

            oo.close();
            bo.close();
            fo.close();
        ////System.out.println(record);
        } catch (IOException e) {
            ReportInfo.reportError("Can not save the history record.", e);
            ////System.out.println("Can not save the history record.");
        }
    }

    public static void saveLine(String filename, String line, boolean append) {
        ////System.out.println("Saving to: " + filename);
        try {
            FileWriter fw = new FileWriter(filename,append);    //the 2nd para is for adding content to the file end.
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(line+"\n");

            bw.close();
            fw.close();
        ////System.out.println(record);
        } catch (IOException e) {
            //System.out.println("Can not save the line.");
        }
    }

    public static void saveLines(String filename, Vector<String> lines, boolean append) {
        ////System.out.println("Saving to: " + filename);
        try {
            FileWriter fw = new FileWriter(filename,append);    //the 2nd para is for adding content to the file end.
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i=0;i<lines.size();i++) bw.write(lines.elementAt(i)+"\n");

            bw.close();
            fw.close();
        ////System.out.println(record);
        } catch (IOException e) {
            //System.out.println("Can not save the line.");
        }
    }

   

    public static void mergeFiles(Vector<String> in_files, String out_file, String position_file) {
        ////System.out.println("Saving to: " + filename);
        try {
            FileWriter fw = new FileWriter(out_file);
            BufferedWriter bw = new BufferedWriter(fw);

            FileWriter fw1 =null;   //for saving positions
            BufferedWriter bw1 = null;
            if(position_file!=null) {
                fw1=new FileWriter(position_file);
                bw1=new BufferedWriter(fw1);
            }

            long current=0;
            for(int i=0;i<in_files.size();i++) {
                String name=in_files.elementAt(i);
                if(!new File(name).exists()) continue;

                String s="#\t"+new File(name).getName();
                bw.write(s+"\n");
                current+=s.length()+1;
                if(position_file!=null) bw1.write(new File(name).getName()+"\t"+current+"\n");
                
                FileReader fr=new FileReader(name);
                BufferedReader br=new BufferedReader(fr);

                String line="";
                while((line=br.readLine())!=null) {
                    bw.write(line+"\n");
                    current+=line.length()+1;
                }

                fr.close();
                br.close();
            }

            if(position_file!=null) {
                bw1.close();
                fw1.close();
            }
            bw.close();
            fw.close();
        ////System.out.println(record);
        } catch (IOException e) {
            //System.out.println("Can not save the line.");
        }
    }

    public static void save2ColumnTable(String out_file,Vector col1,Vector col2) {
        ////System.out.println("Saving to: " + filename);
        if(col1==null || col2==null || col1.size()!=col2.size()) return;
        try {
            FileWriter fw = new FileWriter(out_file);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i=0;i<col1.size();i++) {
                bw.write(col1.elementAt(i).toString()+"\t"+col2.elementAt(i).toString());
            }

            bw.close();
            fw.close();
        ////System.out.println(record);
        } catch (IOException e) {
            //System.out.println("Can not save the line.");
        }
    }
}
