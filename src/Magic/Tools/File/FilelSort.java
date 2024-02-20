/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Tools.File;

import Magic.Algorithms.Extsort.ExternalSorter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileUtil;
import utils.ReportInfo;

/**
 *
 * @author Administrator
 */
public class FilelSort {

    public static void sortGFF(String gffFile) {
        try {
            ExternalSorter sorter = new ExternalSorter();
            String resultFile = FileUtil.insertSuffix(gffFile, ".sorted");
            int[] soap2SortIndex = {7, 8};
            String[] soap2SortType = {"String", "Number"};
            sorter.doFormatSort(gffFile, resultFile, soap2SortIndex, soap2SortType);
        } catch (IOException ex) {
            ReportInfo.reportError(gffFile, ex);
        }
    }

     public static void sortSOAP(String gffFile) {
        try {
            ExternalSorter sorter = new ExternalSorter();
            String resultFile = FileUtil.insertSuffix(gffFile, ".sorted");
            int[] soap2SortIndex = {7, 8};
            String[] soap2SortType = {"String", "Number"};
            sorter.doFormatSort(gffFile, resultFile, soap2SortIndex, soap2SortType);
        } catch (IOException ex) {
            ReportInfo.reportError(gffFile, ex);
        }
    }

      public static void sortSOAPByName(String gffFile) {
        try {
            ExternalSorter sorter = new ExternalSorter();
            String resultFile = FileUtil.insertSuffix(gffFile, ".sorted");
            int[] soap2SortIndex = {0};
            String[] soap2SortType = {"String"};
            sorter.doFormatSort(gffFile, resultFile, soap2SortIndex, soap2SortType);
        } catch (IOException ex) {
            ReportInfo.reportError(gffFile, ex);
        }
    }

    public static void main(String[] args) throws IOException {
      //  sortSOAPByName("E:\\hou\\methy\\total.soap");
        sortSOAP( "E:\\hou\\methy\\total.soap");

    }
}
