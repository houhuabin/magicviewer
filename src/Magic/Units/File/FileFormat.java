/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File;

import java.io.File;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;


import Magic.Units.File.Parameter.StringRep;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

;

import net.sf.samtools.util.BlockCompressedInputStream;

import utils.FormatCheck;

import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class FileFormat {

    //Format的定义，只适合oneline format,为文件的每列（\t），除了读取的原因外，还有输出的原因。与Check的没列相对应
    public static String[] needConvertFormat = {StringRep.MAQ, StringRep.SAM, StringRep.SOAP2, StringRep.BOWTIE};
    public static String[] surportedAlignFormat = {StringRep.MAQ, StringRep.SAM, StringRep.SOAP2, StringRep.BOWTIE, StringRep.BLAST, StringRep.PSL, StringRep.ZOOM, StringRep.BAM};
    public static PieceField[] BEDFormat = {PieceField.chrom, PieceField.start, PieceField.end, PieceField.id, PieceField.score,
        PieceField.strand, PieceField.thickStart, PieceField.thickEnd, PieceField.color, PieceField.blockCount, PieceField.blockSizes, PieceField.blockStarts};
    public static boolean[] BEDSelect = {false, false, false, true, false, true, true, true, false, true, true, true};
    public static String BEDCheck = "String Number Number String Number Strand Number Number RGB Number Block Block";
    public static PieceField[] GFFFormat = {PieceField.chrom, PieceField.source, PieceField.feature, PieceField.start, PieceField.end, PieceField.score, PieceField.strand, PieceField.frame, PieceField.group};
    public static boolean[] GFFSelect = {false, false, false, false, false, false, true, true, true};
    public static String GFFCheck = "String String String Number Number Number Strand Number String";
    public static String SOAP2Check = "String Sequence String Number [ab] Number Strand String Number Number";
    public static String SAMCheck = "String Number String Number Number String String Number Number Sequence String";
    public static String MAQCheck = "String String Number Strand Number Number Number Number Number Number Number Number Number Number Sequence String";
    public static String BOWTIECheck = "String String Number Strand Number Number Number Number Number Number Number Number Number Number Sequence String";
    public static String GELIFormat = "chrom start referenceBase numberOfThisReads maxMappingQuality content quality";
    public static String GELICheck = "String String Sequence Number Number Sequence Number Number Number Number Number Number Number Number Number Number Number Number";
    public static boolean[] VCFSelect = {true, true, false, true, true, true, false, true, true, true};
    public static PieceField[] VCFFormat = {PieceField.chrom, PieceField.start, PieceField.id, PieceField.ref, PieceField.sequence, PieceField.score, PieceField.filter, PieceField.group, PieceField.groupTwo};
    public static String VCFCheck = "String Number String Sequence Sequence Number String String String String";
    public static boolean[] PTTSelect = {true, true, false, true, false, false, false, true};
    public static PieceField[] PTTFormat = {PieceField.location, PieceField.strand, PieceField.length, PieceField.id, PieceField.gene, PieceField.synonym, PieceField.code, PieceField.cog};
    public static String PTTCheck = "Location Strand Number String String String Protean String";

    public enum PieceField {

        chrom, feature, start, end, strand, frame, exonNumber, snpType, id, sequence,
        score, color, source, ref, filter, DP, HRun, MQ, AB, BQ, GQ, synonym, code, cog, length, gene,
        thickStart, thickEnd, blockCount, blockSizes, blockStarts, group, groupTwo, location,
    }

    // public static String PTTFormat = "location strand length id sequence score filter group groupStart groupEnd";
    // public static String BOWITECheck = "String Number Number String Number Strand Number Number RGB Number Block Block";
    // public static String SAMCheck = "String Number Number String Number Strand Number Number RGB Number Block Block";

    /*public static String checkFileMeansByFileName(String file) {
    String format = FileFormat.checkFileFormatByContent(file);
    return checkFileMeansByFormat(format);

    }*/
    public PieceField[] getFormatDefineString(String format) {
        PieceField[] result = null;
        try {
            Class cls = this.getClass();
            Field fomatDefineField = cls.getField(format + "Format");
            result = (PieceField[]) fomatDefineField.get(this);
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        return result;
    }

    public PieceField[] getFormatFieldString(String format) {
        if (format.equals(StringRep.VCF)) {
            PieceField[] re = {PieceField.chrom, PieceField.start, PieceField.id, PieceField.ref, PieceField.sequence, PieceField.score, PieceField.filter, PieceField.group, PieceField.groupTwo};
            return re;
        }
        return getFormatDefineString(format);
    }

    public boolean[] getFormatSelectArray(String format) {
        boolean[] result = null;
        try {
            Class cls = this.getClass();
            Field fomatDefineField = cls.getField(format + "Select");
            result = (boolean[]) fomatDefineField.get(this);
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
        return result;
    }

    public static boolean checkGFFSNP(String filename) {
        boolean isSNP = false;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String[] lines = getNoAnnotationLine(br).split("\\t");

            if (lines[3].equals(lines[4]) || lines[4].equals("")) {
                isSNP = true;

            }
        } catch (Exception e) {
            ReportInfo.reportError(filename, e);
        }
        return isSNP;
    }

    public static String checkFileMeansByFormat(String format, String filename) {
        String fileMeans = StringRep.UNKNOWN;

        ////System.out.println(format+"      =================format");
        if (format.equals(StringRep.CPG130)) {
            fileMeans = StringRep.CGI;
        } else if (format.equals(StringRep.FOURCOLUMN) || format.equals(StringRep.BED) || format.equals(StringRep.PTT)) {
            fileMeans = StringRep.GENE;
        } else if (format.equals(StringRep.GFF)) {
            if (checkGFFSNP(filename)) {
                fileMeans = StringRep.SNPS;
            } else {
                fileMeans = StringRep.GENE;
            }

        } else if (format.equals(StringRep.GELI) || format.equals(StringRep.VCF)) {
            fileMeans = StringRep.SNPS;
        } else if (format.equals(StringRep.TANDEM)) {
            fileMeans = StringRep.TANDEM;
        } else if (format.equals(StringRep.MP)) {
            fileMeans = StringRep.MP;
        } else {
            fileMeans = format;
        }
        return fileMeans;

    }

    public static Vector<String> getSurportedAnnoFormats() {
        Vector<String> vec = new Vector<String>();
        //  vec.add(KeyWords.SNPS);
        //  vec.add(KeyWords.TANDEM);


        //   vec.add(KeyWords.THREECOLUMN);
        //    vec.add(KeyWords.FOURCOLUMN);
        vec.add(StringRep.GFF);
        vec.add(StringRep.PTT);
        vec.add(StringRep.BED);
        //  vec.add(KeyWords.GELI);
        vec.add(StringRep.VCF);
        return vec;
    }

    public static boolean checkAnnotationFormat(String format) {
        boolean isAnno = false;
        // String format = checkFileFormatByContent(filename);
        //  String formatMean = checkFileMeansByFormat(filename);
        Vector<String> surportedAnnoFormats = getSurportedAnnoFormats();
        for (String formatSurported : surportedAnnoFormats) {
            if (formatSurported.equals(format)) {
                isAnno = true;
            }
        }
        return isAnno;

    }

    public static boolean check(String str, String format) {

        if (format.equals("Number")) {
            return FormatCheck.isNumber(str);
        } else if (format.equals("RGB")) {
            return FormatCheck.isRGB(str);
        } else if (format.equals("Strand")) {
            return FormatCheck.isStrand(str);
        } else if (format.equals("Location")) {
            return FormatCheck.isLocation(str);
        } else if (format.equals("Protean")) {
            return FormatCheck.isProtean(str);
        } else if (format.equals("Block")) {
            return FormatCheck.isBlock(str);
        } else if (format.equals("Sequence")) {
            return FormatCheck.isSequence(str);
        } else if (format.equals("String")) {
            return true;
        } else {
            return str.matches(format);
        }


    }

    public static boolean check(String[] line, String[] check, int length) {
        boolean is = true;
        for (int i = 0; i < length; i++) {
            // System.out.println(line[i]+" line[i]  "+check[i]);
            if (line[i].equals(".")) {
                return true;
            }

            if (!check(line[i], check[i])) {

                return false;
            }
        }
        return is;
    }

    public static boolean checkBEDByContent(String line) {
        // //System.out.println();
        String[] lines = line.split("\\t");
        if (lines.length != 12) {
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, BEDCheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkGELIByContent(String line) {
        // //System.out.println();
        String[] lines = line.split("\\s+");
        ////System.out.println(lines.length);
        if (lines.length != 18) {
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, GELICheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkVCFByContent(String line) {
        // System.out.println("checkVCFByContent");
        String[] lines = line.split("\\s+");

        if (lines.length < 10) {
            // System.out.println(lines.length+"");
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, VCFCheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkGFFByContent(String line) {
        String[] lines = line.split("\\t");
        if (lines.length != 9) {
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, GFFCheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkPTTByContent(String line) {
        String[] lines = line.split("\\t");
        if (lines.length != 8) {
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, PTTCheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkSOAP2ByContent(String line) {
        // //System.out.println();
        String[] lines = line.split("\\t");
        if (lines.length < 10) {
            return false;
        }
        return check(line.split("\\t"), SOAP2Check.split("\\s+"), 10);
    }

    public static boolean checkMAQByContent(String line) {
        // //System.out.println();
        String[] lines = line.split("\\t");
        if (lines.length != 16) {
            return false;
        }
        String[] lineArray = line.split("\\t");
        return check(lineArray, MAQCheck.split("\\s+"), lineArray.length);
    }

    public static boolean checkSAMByContent(String line) {
        // //System.out.println();
        String[] lines = line.split("\\t");
        if (lines.length < 11) {
            // //System.out.println(lines.length+"  lines.length ");
            return false;
        }
        //String[] lineArray = line.split("\\t");
        String[] lineArray = line.split("\\t");
        String[] check = SAMCheck.split("\\s+");
        int length = Math.min(lineArray.length, check.length);
        return check(lineArray, check, length);
    }

    public static String getNoNullLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("")) {
                continue;
            } else {
                break;
            }

        }
        return line;
    }

    public static String[] getAnnotationLine(BufferedReader br) throws IOException {
        String line;
        ArrayList<String> result = new ArrayList<String>();
        br.mark(1);
        while ((line = br.readLine()) != null) {


            if (line.equals("")) {
            } else if (line.startsWith("#")) {
                result.add(line);
                break;
            } else {
                br.reset();
                break;
            }
        }
        return result.toArray(new String[0]);
    }

    public static String getNoAnnotationLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("") || line.startsWith("#") || line.startsWith("@")) {
                continue;
            } else {
                break;
            }

        }
        return line;

    }

    public static boolean checkSurportedAlignFile(String format) {
        for (String surportedFormat : surportedAlignFormat) {
            if (surportedFormat.equals(format)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBAM(String filename) {
        boolean bam = false;
        try {
            BufferedInputStream bufferedStream = new BufferedInputStream(new FileInputStream(new File(filename)));
            bam = BlockCompressedInputStream.isValidFile(bufferedStream);
            bufferedStream.close();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return bam;
    }

    public static boolean checkIndexBAM(String filename) {

        if (!(new File(filename + ".bai").exists())) {
            return false;
        }
        return checkBAM(filename);
    }

    public static String[] getFileContent(String filname) {
        String[] result = new String[4];
        try {
            FileReader fr = new FileReader(filname);
            BufferedReader br = new BufferedReader(fr);

            String token;
            StringTokenizer nizer;


            result[0] = getNoAnnotationLine(br);
            result[1] = getNoNullLine(br);
            result[2] = getNoNullLine(br);
            result[3] = getNoNullLine(br);

            br.close();
            fr.close();
        } catch (Exception e) {
            ReportInfo.reportError(filname, e);
        }
        return result;
    }

    public static boolean checkBLASTNByContent(String content) {

        if (content.startsWith(StringRep.BLASTN)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkTANDEMByContent(String content) {
        if (content.startsWith("Tandem Repeats Finder")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkCPG130ByContent(String content) {
        if (content.startsWith("CGI")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check3COLUMNByContent(String content) {
        String[] contentArray = content.split("\t");
        if (contentArray.length == 3 && Character.isDigit(contentArray[0].charAt(0)) && Character.isDigit(contentArray[1].charAt(0))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check4COLUMNByContent(String content) {
        String[] contentArray = content.split("\t");
        if (contentArray.length == 4 && Character.isDigit(contentArray[0].charAt(0)) && Character.isDigit(contentArray[1].charAt(0))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkBLASTNM8ByContent(String content) {
        String[] contentArray = content.split("\t");
        if (contentArray.length == 12) {
            return true;
        }
        return false;
    }

    public static boolean checkCONTIGINFOByContent(String content) {
        String[] contentArray = content.split("\t");
        if (contentArray.length == 12) {
            return true;
        }
        return false;
    }
    public static String[] KnownFormats = {StringRep.BAM, StringRep.BED, StringRep.GFF, StringRep.SOAP2, StringRep.GELI, StringRep.VCF,
        StringRep.MAQ, StringRep.SAM, StringRep.PTT, StringRep.BLASTN, StringRep.FASTA, StringRep.TANDEM,
        StringRep.CPG130, StringRep.THREECOLUMN, StringRep.FOURCOLUMN, StringRep.GFF, StringRep.BLASTNM8,
        StringRep.CONTIGINFO, StringRep.SNPS, StringRep.CLUSTER, StringRep.PTT
    };

    public static boolean checkFASTAByContent(String content) {
        if (content.startsWith(">")) {
            return true;
        }
        return false;
    }

    public static boolean checkByContent(String filename, String format) {
        boolean isThisFormat = false;
        try {
            if (filename == null) {
                // System.out.println("null");
                return false;
            }
            if (!new File(filename).exists()) {
                // System.out.println("exists");
                return false;
            }
            if (format.equals(StringRep.BAM)) {
                if (checkBAM(filename)) {
                    return true;
                }
                return false;
            }
            String[] content = getFileContent(filename);
            if (content[2] == null) {
                // System.out.println("content2");
                //return false;
            }

            if (format.equals(StringRep.CONTIGINFO)) {

                if (content[2] != null && content[2].startsWith("number of reads:")) {
                    return true;
                }
                return false;
            }
            if (format.equals(StringRep.SNPS)) {
                if (content[2].startsWith("NUCMER") && content[4] != null && content[4].contains("[P1]")) {
                    return true;
                }
                return false;
            }
            if (format.equals(StringRep.PTT)) {
                if (content[2].startsWith("Location") && content[3] != null && checkPTTByContent(content[3])) {
                    return true;
                }
                return false;
            }
            if (format.equals(StringRep.CLUSTER)) {
                if (content[2].startsWith("NUCMER") && content[4] != null && !content[4].contains("[P1]")) {
                    return true;
                }
                return false;
            }
            Class c = FileFormat.class;
            Method m1 = c.getDeclaredMethod("check" + format + "ByContent", String.class);
            isThisFormat = (Boolean) m1.invoke(null, content[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return isThisFormat;
    }

    public static boolean isSurportedSNP(String fileType) {
        if (fileType.equals(StringRep.GFF) || fileType.equals(StringRep.VCF)) {
            return true;
        }
        return false;
    }

    public static String checkFileFormatByContent(String filename) {
        String format = StringRep.UNKNOWN;
//System.out.println("checkFileFormatByContent");

        if (filename == null) {

            return StringRep.UNEXIST;
        }
        if (!new File(filename).exists()) {
            return StringRep.UNEXIST;
        }
        try {
            Class c = FileFormat.class;
            Method m1 = c.getDeclaredMethod("checkByContent", String.class, String.class);
            for (String knownFormat : KnownFormats) {
                //     // System.out.println(knownFormat);
                Boolean isThisFormat = (Boolean) m1.invoke(null, filename, knownFormat);
                if (isThisFormat) {
                    return knownFormat;
                }

            }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        return format;
    }

    public static void main(String args[]) {
        // System.out.println(checkFileFormatByContent("e://primer3_core.exe"));
    }
}
