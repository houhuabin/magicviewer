/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Task;

import Magic.Units.Gui.Task.SimpleTask;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class GenotypingTask extends SimpleTask {

    private ArrayList<String> bamFiles;
    private String refFile;

    //  private int oldTrackNum;
    public GenotypingTask(String refFile,ArrayList<String> bamFiles) {
        this.bamFiles = bamFiles;
       /// this.taskNames = tasknames;
        this.refFile=refFile;
        this.taskNames=new ArrayList<String>();
        taskNames.add("Count Covariates");
        taskNames.add("Quality Recalibration");
        taskNames.add("Local Realignment");
        taskNames.add("Indel  Genotyper");
        taskNames.add("Indel Filter");
        taskNames.add("SNP Genotyper");
        taskNames.add("SNP Filter");
    }

    public void runTask(int paramInt) throws Exception {
        if(!isOK)
        {  
           return;
        }

        switch (paramInt) {
            case 0:
                countCovariates();
                break;
            case 1:
                recalibration();
                break;
            case 2:
                realigning();
                break;
            case 3:
                indelGenotyper();
                break;            
            case 4:
                snpGenotyper();
                break;
            case 5:
                snpFilter();
                break;

        }

    }

    public void countCovariates() {
    }

    public void recalibration() {
    }

    public void realigning() {
    }

    public void indelGenotyper() {
        filterIndel();
    }

    public void filterIndel() {
    }

    public void snpGenotyper() {
    }

    public void snpFilter() {
    }

    public int getTaskCount() {
        return bamFiles.size();
    }

    public void cancelTask() {
        this.isOK = false;      
    }
}
