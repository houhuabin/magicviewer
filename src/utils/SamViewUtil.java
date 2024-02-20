/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Magic.Units.IO.SeqData;

import Magic.Units.Piece.Piece;
import Magic.Units.File.FileFormat;
import Magic.Algorithms.Vertical;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.StringRep;

import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Task.IndeterminateTask;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.Piece.GeneticUnit.ReadPiece;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.IO.SeqPanelData;
import net.sf.samtools.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import net.sf.picard.reference.IndexedFastaSequenceFile;
import net.sf.picard.sam.CreateSequenceDictionary;

/**
 *
 * @author Huabin Hou
 */
public class SamViewUtil {

    public static final char MAX_SOLEXA_SCORE = 140;

    // public static String sortCmd="samtools sort ";
    public static void createSequenceDictionary(String ref) {
        // System.out.println(ref+" getNoTypePar  "+FileUtil.getNoTypePart(ref+".dict"));
        String[] argv=new String[2];
       argv[0]="R="+ref;
       argv[1]="O="+FileUtil.getNoTypePart(ref) + ".dict";

        new CreateSequenceDictionary().instanceMain(argv);
     //   CreateSequenceDictionary csd = new CreateSequenceDictionary(ref, FileUtil.getNoTypePart(ref) + ".dict");
       // csd.doWork();
    }

    public static ArrayList getAlgnTaskNames(String alignmentFileFormat, String aligmentFile) {
        ArrayList<String> name = new ArrayList<String>();
        if (!alignmentFileFormat.equals(StringRep.SAM) && !alignmentFileFormat.equals(StringRep.BAM)) {
            name.add("Convert to SAM");
            name.add("Convert to BAM");
        }
        if (alignmentFileFormat.equals(StringRep.SAM)) {
            name.add("Convert to BAM");
        }
        if (!(new File(aligmentFile + ".bai").exists())) {
            name.add("Sort");
            name.add("Index");
        }

        return name;

    }

    //return the file name of sorted bam file , if not surported file type return null;
    public static int processAlignmentFile(JFrame parent, final String aligmentFile, final String refFile) {

        final String alignmentFileFormat = FileFormat.checkFileFormatByContent(aligmentFile);
        if (!FileFormat.checkSurportedAlignFile(alignmentFileFormat)) {
            return ForEverStatic.RET_ERR;
        }
        IndeterminateTask lt = new IndeterminateTask() {

            public void runTask(int paramInt) throws Exception {
                if (alignmentFileFormat.equals(StringRep.BAM)) {
                    final SAMFileReader reader = new SAMFileReader(new File(aligmentFile));
                    // System.out.println(reader.getFileHeader().getAttribute("SO")+"-----------------SO-------------------");
                    if (!(new File(aligmentFile + ".bai").exists())) {
                        //  System.out.println("==============bai==========");
                        String sortedBam = aligmentFile;
                        if (!SamViewUtil.checkSorted(aligmentFile)) {
                            SamViewUtil.sort(aligmentFile,this);
                            sortedBam = FileUtil.getNoTypePart(aligmentFile) + ".sorted.bam";
                        }
                         SamViewUtil.index(sortedBam,this);
                        rm0d(FileUtil.getNoTypePart(aligmentFile) + ".sorted.bam.bai");
    
                    }
    
                } else {
                    if (!alignmentFileFormat.equals(StringRep.SAM)) {
                          SamViewUtil.alignment2SAM(aligmentFile, alignmentFileFormat, this);
                    }

                  SamViewUtil.sam2Bam(aligmentFile, refFile, this);
                  SamViewUtil.sort(FileUtil.getNoTypePart(aligmentFile) + ".bam", this);

                      SamViewUtil.index(FileUtil.getNoTypePart(aligmentFile) + ".sorted.bam", this);
                    rm0d(FileUtil.getNoTypePart(aligmentFile) + ".sorted.bam.bai");
                }
            }

            public ArrayList<String> getNames() {
                return getAlgnTaskNames(alignmentFileFormat, aligmentFile);
            }
        };
        NewProgress monitor = new NewProgress("Process Alignment File", StringRep.START, lt);
        // monitor.startTask();
        if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
            //System.out.println("Error ");
            if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                ReportInfo.reportError(null, monitor.getException());
                return ForEverStatic.RET_ERR;
            }
            return ForEverStatic.RET_CANCEL;


        }
         return ForEverStatic.RET_OK;
    }

    public static void rm0d(String bamIndexFile) {
        //System.out.println(bamIndexFile + "---------bamIndexFile-------");
        Remove0D rm = new Remove0D(bamIndexFile);
        rm.remove();
    }

    public static boolean checkSorted(String bamFile) {

        final SAMFileReader reader = new SAMFileReader(new File(bamFile));
        // System.out.println(reader.getFileHeader().getAttribute("SO"));
        if (reader.getFileHeader().getSortOrder() == SAMFileHeader.SortOrder.unsorted) {
            //  System.out.println(reader.getFileHeader().getAttribute("SO"));
            return false;
        } else {
            // System.out.println(reader.getFileHeader().getAttribute("SO"));
            return true;
        }
    }

    public static void indexRef(String refFile, TaskBase monitor) {
        try {
            refFile = StringUtil.getKonggeMingling(refFile);
            //String sortCmd = "samtools faidx   " + refFile;
            // System.out.println(sortCmd + "===========================");
            //SystemUtil.exec (" external\\bin\\samtools faidx   E:\\out\\chrS.fa ",monitor,null,true);
            String[] cmd = new String[3];
            cmd[0] = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "samtools";
            cmd[1] = "faidx";
            cmd[2] = refFile;
            SystemUtil.exec(cmd, monitor, null, true);
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public static void sam2Bam(String samFile, String refFile, TaskBase monitor) {
        try {

            String refIndexFile = refFile + ".fai ";
            String bamFile = samFile.substring(0, samFile.length() - 4) + ".bam";

            refFile = StringUtil.getKonggeMingling(refFile);
            refIndexFile = StringUtil.getKonggeMingling(refIndexFile);
            bamFile = StringUtil.getKonggeMingling(bamFile);
            samFile = StringUtil.getKonggeMingling(samFile);

            //String sortCmd = "samtools view   -b -h -S -T " + refFile + " -t " + refIndexFile + " " + samFile + " -o " + bamFile;

            //convertFileType(samFile, monitor);
            String[] cmd = new String[10];
            cmd[0] = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "samtools";
            cmd[1] = "view";
            cmd[2] = "-b";
            cmd[3] = "-h";
            cmd[4] = "-S";
            cmd[5] = "-T";
            cmd[6] = refFile;
            cmd[7] = samFile;
            cmd[8] = "-o";
            cmd[9] = bamFile;
            SystemUtil.exec(cmd, monitor, null, true);
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public static void main(String argv[]) {
        String input = "E:\\project\\magicinsight\\test\\chrM\\chrM.sam";
        String ref = "E:\\project\\magicinsight\\test\\chrM\\chrM.fa";
        sam2Bam(input, ref, null);
    }

    public static String sort(String bamFile, TaskBase monitor) {

        String sortOutFile = FileUtil.getNoTypePart(bamFile) + ".sorted";
        try {
            bamFile = StringUtil.getKonggeMingling(bamFile);
            sortOutFile = StringUtil.getKonggeMingling(sortOutFile);
            //String sortCmd = "samtools sort  " + bamFile + "  " + sortOutFile;
            String[] cmd = new String[4];
            cmd[0] = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "samtools";
            cmd[1] = "sort";
            cmd[2] = bamFile;
            cmd[3] = sortOutFile;
            SystemUtil.exec(cmd, monitor, null, true);
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        /*MergeSamFiles msf = new MergeSamFiles();
        msf.INPUT.add(new File(bamFile));
        msf.OUTPUT = new File(sortOutFile);
        MergeSamFiles.TMP_DIR = new File(FileUtil.getFilePath(sortOutFile));
        msf.go();*/

        return sortOutFile;

    }

    //samtools dosenot set SO filed，so afert sort we need set SO field mannulely. @HD     VN:1.0  GO:none SO:coordinate
    public static void setSort(String bamFile, TaskBase monitor) {
        try {
            final SAMFileReader inputSam = new SAMFileReader(new File(bamFile));
            SAMFileHeader samfh = inputSam.getFileHeader();
            samfh.setSortOrder(SAMFileHeader.SortOrder.coordinate);
            final SAMFileWriter outputSam = new SAMFileWriterFactory().makeSAMOrBAMWriter(samfh,
                    true, new File(bamFile.substring(0, bamFile.length() - 4) + ".sort"));
            for (final SAMRecord samRecord : inputSam) {
                // Convert read name to upper case.
                samRecord.setReadName(samRecord.getReadName().toUpperCase());
                outputSam.addAlignment(samRecord);
            }

            outputSam.close();
            inputSam.close();
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public static void index(String bamFile, TaskBase monitor) {
        try {
            // System.out.println(bamFile + "=====bamFile========");
            bamFile = StringUtil.getKonggeMingling(bamFile);
            // String indexCmd = "samtools index  " + bamFile;
            String[] cmd = new String[3];
            cmd[0] = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "samtools";
            cmd[1] = "index";
            cmd[2] = bamFile;
            SystemUtil.exec(cmd, monitor, null, true);
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    public static void convertFileType(String inputSamOrBamFile, TaskBase monitor) {
        /*  int filesize = (int) FileUtil.getFileLen(inputSamOrBamFile);
        monitor.setMaximum(filesize);

        final SAMFileReader inputSam = new SAMFileReader(new File(inputSamOrBamFile));
        String outfile = null;
        if (inputSamOrBamFile.endsWith(".sam")) {
        outfile = FileUtil.getNoTypePart(inputSamOrBamFile) + ".bam";
        } else if (inputSamOrBamFile.endsWith(".bam")) {
        outfile = FileUtil.getNoTypePart(inputSamOrBamFile) + ".sam";
        } else {
        ReportInfo.reportError("Not SAM or BAM format!");
        return;
        }

        final SAMFileWriter outputSam = new SAMFileWriterFactory().makeSAMOrBAMWriter(inputSam.getFileHeader(),
        true, new File(outfile));

        int currentFileSize = 0;

        for (final SAMRecord samRecord : inputSam) {
        samRecord.setReadName(samRecord.getReadName().toUpperCase());
        outputSam.addAlignment(samRecord);
        if (monitor != null) {
        currentFileSize += samRecord.toLine().length();
        monitor.setProgress(currentFileSize);
        }
        }

        outputSam.close();
        inputSam.close();*/
    }

    public static void alignment2SAM(String file, String fileType, TaskBase monitor) {
        try {
            String[] convertCmd = new String[2];
            if (fileType.equals(StringRep.SOAP2)) {
                convertCmd[0] = ForEverStatic.PERL_PATH + ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "soap2sam.pl";
            } else if (fileType.equals(StringRep.MAQ)) {
                convertCmd[0] = ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "maq2sam-long";
            } else if (fileType.equals(StringRep.BOWTIE)) {
                convertCmd[0] = ForEverStatic.PERL_PATH + ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "bowtie2sam.pl";
            } else if (fileType.equals(StringRep.BLAST)) {
                convertCmd[0] = ForEverStatic.PERL_PATH + ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "blast2sam.pl";
            } else if (fileType.equals(StringRep.PSL)) {
                convertCmd[0] = ForEverStatic.PERL_PATH + ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "psl2sam.pl";
            } else if (fileType.equals(StringRep.ZOOM)) {
                convertCmd[0] = ForEverStatic.PERL_PATH + ForEverStatic.EXE_PATH + ForEverStatic.FILE_SEPARATOR + "zoom2sam.pl";
            }
            convertCmd[1] = file;
            // System.out.println(convertCmd+"    ---------------------==============");
            //System.out.println( System.getProperty("user.dir"));

            SystemUtil.exec(convertCmd, monitor, file + ".sam", false);
            //SystemUtil.exec("external\\bin\\perl\\bin\\perl --help");
            //  System.out.println(convertCmd+"   done ---------------------==============");
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

    }

    /* public static List<String> getReferenceNames(final String bamFile) {
    final SAMFileReader reader = new SAMFileReader(new File(bamFile));
    final List<String> result = new ArrayList<String>();
    final List<SAMSequenceRecord> seqRecords = reader.getFileHeader().getSequenceDictionary().getSequences();
    for (final SAMSequenceRecord seqRecord : seqRecords) {
    if (seqRecord.getSequenceName() != null) {
    result.add(seqRecord.getSequenceName());

    }
    }
    reader.close();
    return result;
    }*/
    public static List<String> getReferenceNames(final String refFile) {
        final List<String> result = new ArrayList<String>();
        try {
            IndexedFastaSequenceFile fb = new IndexedFastaSequenceFile(new File(refFile));
            for (int i = 0; i < fb.getSequenceDictionary().size(); i++) {
                String id = fb.getSequenceDictionary().getSequence(i).getSequenceName();
                result.add(id);
            }
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        return result;
    }

    public static int getReferenceLen(String bamFile, String sequenceName) {
        final SAMFileReader reader = new SAMFileReader(new File(bamFile));

        int referenceLen = 0;
        final List<SAMSequenceRecord> seqRecords = reader.getFileHeader().getSequenceDictionary().getSequences();
        for (final SAMSequenceRecord seqRecord : seqRecords) {
            if (seqRecord.getSequenceName().equals(sequenceName)) {
                //  result.add(seqRecord.getSequenceName());
                referenceLen = seqRecord.getSequenceLength();

            }
        }
        reader.close();
        return referenceLen;
    }

    public static String getReferenceSeq(String bamFile, String sequenceName) {
        final SAMFileReader reader = new SAMFileReader(new File(bamFile));

        String seq = null;
        final List<SAMSequenceRecord> seqRecords = reader.getFileHeader().getSequenceDictionary().getSequences();
        for (final SAMSequenceRecord seqRecord : seqRecords) {
            if (seqRecord.getSequenceName().equals(sequenceName)) {
                //  result.add(seqRecord.getSequenceName());
                //    seq= ;
            }
        }
        reader.close();
        return seq;
    }

    public static class Insert {

        public int posion;
        public int len;
    }

    public static class InsertComparator implements Comparator {

        public int compare(Object o1, Object o2) {
            Insert p1 = (Insert) o1;
            Insert p2 = (Insert) o2;
            if (p1.posion < p2.posion) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public static String insertRefSeq(ArrayList<Insert> insertPoint, String refsequence, int refSub) {

        StringBuffer str = new StringBuffer(refsequence);
        int insertNum = 0;


        for (final Insert element : insertPoint) {

            int point = element.posion + insertNum;


            for (int i = 0; i < element.len; i++) {
                str.insert(point + refSub, "*");
                insertNum++;

            }
        }
        return str.toString();

    }

    public static int consesusToOrigin(int conPosion, SeqData seqData) {
        /* int orign = conPosion;

        for (final Insert element : insertPoint) {
        //  if (element.posion <= start && element.posion > 0) {
        if (element.posion < orign) {
        System.out.println(orign+"---------"+element.posion);
        orign -= element.len;
        }
        }*/
        return conPosion - getStartAddByInsert(conPosion - seqData.windowStart, seqData.insertPoint);
        //  return orign;
    }

    public static int originToConsesus(int posion, SeqData seqData) {
        return posion + getStartAddByInsert(posion - seqData.windowStart, seqData.insertPoint);
    }

    public static int getStartAddByInsert(int orignStart, ArrayList<Insert> insertPoint) {
        int add = 0;
        if (insertPoint == null) {
            return 0;
        }
        for (final Insert element : insertPoint) {
            //  if (element.posion <= start && element.posion > 0) {
            if (element.posion < orignStart) {
                add += element.len;
            }
        }
        return add;
    }

    public static void setInsertSite(ArrayList<SAMRecord> iter1ArrayList, int point, Vertical verPositive, ArrayList<Insert> insertPoint, SeqData seqData) {

        for (SAMRecord sm : iter1ArrayList) {
            int currentCigarPosionStart = 0;
            int pieceStart = sm.getAlignmentStart() - point;
            for (final CigarElement element : sm.getCigar().getCigarElements()) {
                switch (element.getOperator()) {

                    case S:
                        if (currentCigarPosionStart == 0) {
                            pieceStart = pieceStart - element.getLength();
                        }
                    case M:
                        currentCigarPosionStart += element.getLength();
                        break;

                    case I:
                        boolean contain = false;
                        int currentPoint = currentCigarPosionStart + pieceStart;
                        for (final Insert insertElement : insertPoint) {
                            if (insertElement.posion == currentPoint) {
                                insertElement.len = Math.max(element.getLength(), insertElement.len);
                                contain = true;
                            }
                        }
                        if (contain == false && currentPoint >= 0) {
                            Insert insert = new Insert();
                            insert.posion = currentPoint;
                            insert.len = element.getLength();
                            // System.out.println(insert.posion+"=============insert=========");
                            insertPoint.add(insert);

                        }
                        if (currentPoint < 0) {
                            pieceStart = pieceStart - element.getLength();
                        }
                        //currentCigarPosionStart += element.getLength();
                        break;
                    case H:
                        break;
                    case P:
                    case D:
                    case N:
                        currentCigarPosionStart += element.getLength();
                        break;
                }
            }
        }
        Comparator comp = new InsertComparator();
        Collections.sort(insertPoint, comp);

    }

    public static void CigarToPiece(ArrayList<SAMRecord> iter1ArrayList, int point, Vertical verPositive, ArrayList<Insert> insertPoint, SeqData seqData, SeqPanelData seqPanelData) {
        ArrayList<Piece> data = new ArrayList<Piece>();
        int minStart = point;
        int maxEnd = point + Log.instance().global.alignmentWindowLen;
        //    AlignTrack en = new AlignTrack();
        for (SAMRecord sm : iter1ArrayList) {
            int poeceEnd = sm.getAlignmentStart() + sm.getCigar().getReferenceLength();

            minStart = minStart < sm.getAlignmentStart() ? minStart : sm.getAlignmentStart();
            //System.out.println(sm.getAlignmentStart() + "  ===piece.geneticPiece.start  " + sm.getCigar().getReferenceLength() + "   " + poeceEnd);
            maxEnd = maxEnd > poeceEnd ? maxEnd : poeceEnd;
            Piece piece = new Piece();
            ReadPiece geneticPiece = new ReadPiece();

            geneticPiece.id = sm.getReadName();
            geneticPiece.start = sm.getAlignmentStart();
            geneticPiece.end = sm.getAlignmentEnd();


            geneticPiece.windowStart = sm.getAlignmentStart() - point + 1;

            geneticPiece.windowStart += getStartAddByInsert(geneticPiece.windowStart, insertPoint);
            piece.geneticPiece = geneticPiece;



            piece.geneticPiece.strand = !sm.getReadNegativeStrandFlag();
            String resultSeq = "";
            String resultQuality = "";

            // 相对坐标,记录当前cigar在sm.getReadString()上的起始位置
            int currentCigarSeqStart = 0;
            int currentCigarPosionStart = 0;

            int insertNum = 0;
            int Ilen = 0;

            boolean lastIsInsert = true;


            for (final CigarElement element : sm.getCigar().getCigarElements()) {

                switch (element.getOperator()) {
                    case S:
                        if (currentCigarSeqStart == 0) {
                            geneticPiece.windowStart = geneticPiece.windowStart - element.getLength();

                        }
                        lastIsInsert = false;
                        break;
                    case M:
                        int currentCigarPointNoInsert = currentCigarPosionStart + sm.getAlignmentStart() - point - Ilen;
                        String seqInSigar = sm.getReadString().substring(currentCigarSeqStart, element.getLength() + currentCigarSeqStart);
                        String quaInSigar = sm.getBaseQualityString().substring(currentCigarSeqStart, element.getLength() + currentCigarSeqStart);


                        for (final Insert insertElement : insertPoint) {

                            //     System.out.println(insertElement.posion + "    " + currentCigarPointNoInsert + "  " + piece.geneticPiece.id + "   " + lastIsInsert);
                            if ((insertElement.posion > currentCigarPointNoInsert && insertElement.posion < currentCigarPointNoInsert + element.getLength()) || (!lastIsInsert && insertElement.posion == currentCigarPointNoInsert)) {

                                StringBuffer sbSInSigar = new StringBuffer(seqInSigar);
                                StringBuffer sbQInSigar = new StringBuffer(quaInSigar);
                                for (int i = 0; i < insertElement.len; i++) {

                                    sbSInSigar.insert(insertElement.posion - currentCigarPointNoInsert + insertNum, "*");
                                    sbQInSigar.insert(insertElement.posion - currentCigarPointNoInsert + insertNum, MAX_SOLEXA_SCORE);
                                    insertNum++;

                                }
                                seqInSigar = sbSInSigar.toString();
                                quaInSigar = sbQInSigar.toString();
                            }


                        }
                        resultSeq += seqInSigar;
                        resultQuality += quaInSigar;
                        currentCigarSeqStart += element.getLength();
                        currentCigarPosionStart += element.getLength();
                        lastIsInsert = false;
                        break;

                    case I:
                        int currentPoint = currentCigarSeqStart + geneticPiece.windowStart;

                        if (currentPoint <= 0) {
                            geneticPiece.windowStart = geneticPiece.windowStart - element.getLength();
                        }

                        resultSeq += sm.getReadString().substring(currentCigarSeqStart, element.getLength() + currentCigarSeqStart);
                        resultQuality += sm.getBaseQualityString().substring(currentCigarSeqStart, element.getLength() + currentCigarSeqStart);

                        currentCigarSeqStart += element.getLength();

                        currentCigarPosionStart += element.getLength();
                        Ilen = Ilen + element.getLength();
                        lastIsInsert = true;

                        break;

                    case P:
                        for (int i = 0; i < element.getLength(); i++) {
                            resultSeq += "*";
                            resultQuality = resultQuality + "*";
                            // resultQuality = resultQuality + (char) MAX_SOLEXA_SCORE;
                        }
                        currentCigarPosionStart += element.getLength();
                        lastIsInsert = false;
                        break;

                    case H:
                        lastIsInsert = false;
                        break;
                    case D:
                    case N:

                        seqInSigar = "";
                        quaInSigar = "";
                        for (int i = 0; i < element.getLength(); i++) {
                            seqInSigar += "-";
                            quaInSigar = quaInSigar + (char) MAX_SOLEXA_SCORE;
                        }
                        currentCigarPointNoInsert = currentCigarPosionStart + sm.getAlignmentStart() - point - Ilen;
                        for (final Insert insertElement : insertPoint) {
                            //     System.out.println(insertElement.posion + "    " + currentCigarPointNoInsert + "  " + piece.geneticPiece.id + "   " + lastIsInsert);
                            if ((insertElement.posion > currentCigarPointNoInsert && insertElement.posion < currentCigarPointNoInsert + element.getLength()) || (!lastIsInsert && insertElement.posion == currentCigarPointNoInsert)) {

                                StringBuffer sbSInSigar = new StringBuffer(seqInSigar);
                                StringBuffer sbQ = new StringBuffer(quaInSigar);
                                for (int i = 0; i < insertElement.len; i++) {
                                    sbSInSigar.insert(insertElement.posion - currentCigarPointNoInsert + insertNum, "*");
                                    sbQ.insert(insertElement.posion - currentCigarPointNoInsert + insertNum, MAX_SOLEXA_SCORE);
                                    insertNum++;

                                }
                                seqInSigar = sbSInSigar.toString();
                                quaInSigar = sbQ.toString();
                            }


                        }
                        resultSeq += seqInSigar;
                        resultQuality += quaInSigar;
                        currentCigarPosionStart += element.getLength();
                        lastIsInsert = false;
                        break;
                }
            }

            ((ReadPiece) (piece.geneticPiece)).sequence = resultSeq;
            ((ReadPiece) (piece.geneticPiece)).quality = resultQuality;
            if (piece.geneticPiece.strand == false) {
                //   ((ReadPiece) (piece.geneticPiece)).sequence  = utils.SequanceUtil.complement(((ReadPiece) (piece.geneticPiece)).sequence );
            }

            //如果read里有很多n cigar等于* start=end显示会变成一条竖线

            if (piece.geneticPiece.sequence.length() == 0) {
                continue;
            }
            ((ReadPiece) (piece.geneticPiece)).cigar = sm.getCigar().toString();
            ((ReadPiece) (piece.geneticPiece)).mappingQuality = sm.getMappingQuality();
            geneticPiece.windowEnd = piece.geneticPiece.sequence.length() + geneticPiece.windowStart;
            piece.viewPiece.vertical = verPositive.getVerticalPosition(geneticPiece.windowStart, geneticPiece.windowEnd);
            data.add(piece);
        }

        seqData.refSeqStart = minStart;
        seqData.refEnd = Math.min(maxEnd, Log.instance().reference.currentContigLen);

        seqData.refSub = point - minStart;
        seqPanelData.seqEntries.currentPieces = data.toArray(new Piece[0]);
    }
}
