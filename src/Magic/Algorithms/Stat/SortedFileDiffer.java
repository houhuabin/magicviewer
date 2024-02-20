/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Algorithms.Stat;

import Magic.Algorithms.Extsort.AlgConstant;
import Magic.Algorithms.Extsort.FileOneLineRecord;
import Magic.Algorithms.Extsort.Record;
import Magic.Algorithms.Extsort.RecordStore;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class SortedFileDiffer {

    int equalNum = 0;
    int file1SpecialNum = 0;
    int file2SpecialNum = 0;

    public void doDiffer(String file1, String file2, int sortIndex[], String[] sortType) throws Exception {


        AlgConstant.sortIndex = sortIndex;
        AlgConstant.sortType = sortType;
        RecordStore source1 = new FileOneLineRecord(file1);
        Record readR1 = source1.readNextRecord();

        RecordStore source2 = new FileOneLineRecord(file2);
        Record readR2 = source2.readNextRecord();


        while (!readR1.isNull() && !readR2.isNull()) {

            if (readR1.compareTo(readR2) == 0) {
                readR1 = source1.readNextRecord();
                readR2 = source2.readNextRecord();
                equalNum++;
            } else if (readR1.compareTo(readR2) > 0) {
                file2SpecialNum++;
                // readR1 = source1.readNextRecord();
                readR2 = source2.readNextRecord();
            } else if (readR1.compareTo(readR2) < 0) {
                readR1 = source1.readNextRecord();
                file1SpecialNum++;
                // readR2 = source2.readNextRecord();
            }
        }
        while (!readR1.isNull()) {
            readR1 = source1.readNextRecord();
            file1SpecialNum++;
        }
        while (!readR2.isNull()) {
            readR2 = source2.readNextRecord();
            file2SpecialNum++;
        }
    }

    public static void main(String argvs[]) throws Exception {
        //  String file1 = argvs[0];
        // String file2 = argvs[1];
        String file1 = "E:\\";
        String file2 = "E:\\";
        int[] soap2SortIndex = {0, 3};
        String[] soap2SortType = {"String", "Number"};
        SortedFileDiffer sfd = new SortedFileDiffer();
        sfd.doDiffer(file1, file2, soap2SortIndex, soap2SortType);


        System.out.println("equal num:" + sfd.equalNum);
        System.out.println("file1SpecialNum num:" + sfd.file1SpecialNum);
        System.out.println("file2SpecialNum num:" + sfd.file2SpecialNum);

    }
}
