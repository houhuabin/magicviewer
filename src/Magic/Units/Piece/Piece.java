package Magic.Units.Piece;

import Magic.Analysis.Filter.FieldProperty;
import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Piece.GeneticUnit.BasePiece;
import Magic.Units.File.FileFormat;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.ReadPiece;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.Units.Piece.ViewPiece.ViewPiece;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import utils.ForMagic;
import utils.RefelectUtil;
import utils.ReportInfo;


public class Piece {

    public GeneticPiece geneticPiece;
    public ViewPiece viewPiece = new ViewPiece();
    public FilePiece filePiece;

    public Piece() {
    }

    public Piece(Piece oldPiece) {
        viewPiece.parent = oldPiece.viewPiece.parent;
        geneticPiece = (GeneticPiece) oldPiece.geneticPiece.clone();
        filePiece = (FilePiece) oldPiece.filePiece.clone();
    }

    public Piece(GeneticPiece geneticPiece, FilePiece filePiece) {
        this.geneticPiece = geneticPiece;
        this.filePiece = filePiece;
    }

    public Piece(ViewPiece viewPiece, FilePiece filePiece) {
        this.viewPiece = viewPiece;
        this.filePiece = filePiece;
    }

    public HashMap<String, String> getGroup(String group) {
        String[] groupsElements = group.split(";\\s?");
        HashMap hm = new HashMap();
        for (String groupsElement : groupsElements) {
            String[] temp = groupsElement.split("=");
            if (temp.length == 2) {
                if (temp[0].equals("SNPType")) {
                    if (geneticPiece instanceof SNPPiece) {
                        ((SNPPiece) geneticPiece).snpType = SNPType.valueOf(temp[1]);
                    }
                } else {
                    hm.put(groupsElement.split("=")[0], groupsElement.split("=")[1]);
                }

            } else if (groupsElement.split("\\s+").length == 2) {
                hm.put(groupsElement.split("\\s+")[0], groupsElement.split("\\s+")[1]);
            }
        }
        return hm;
    }
    //   public ArrayList<Piece> mergePiece = new ArrayList<Piece>(); DetailField 把group grouptwo 里的每个元素都看成 field;

    public Object getDetailFieldValue(String fieldName) {
        Object result = null;
        try {
            if (fieldName.equals(PieceField.strand.name())) {

                Field field = geneticPiece.getClass().getField(fieldName);
                result = field.get(this.geneticPiece);
                if (result == null) {
                    return null;
                }

                if (result.toString().equals("true")) {
                    //  System.out.println(result+"-===============================--");
                    result = "+";
                } else {
                    result = "-";
                }
            }
            if (geneticPiece == null) {
                System.out.println(fieldName + "-----fieldName----------");
            } else if (RefelectUtil.containField(fieldName, geneticPiece.getClass())) {

                ////System.out.println(geneticPiece.getClass().getName()+"--------geneticPiece.getClass()-----------------");
                Field field = geneticPiece.getClass().getField(fieldName);
                result = field.get(this.geneticPiece);



            } else if (RefelectUtil.containField(fieldName, this.filePiece.getClass())) {
                Field field = this.filePiece.getClass().getField(fieldName);
                result = field.get(this.filePiece);
            } else if (this.filePiece instanceof GFFPiece) {
                if (((GFFPiece) (this.filePiece)).group.containsKey(fieldName)) {
                    result = ((GFFPiece) (this.filePiece)).group.get(fieldName);
                }
            } else if (this.filePiece instanceof VCFPiece) {
                if (((VCFPiece) (this.filePiece)).group.containsKey(fieldName)) {
                    result = ((VCFPiece) (this.filePiece)).group.get(fieldName);
                } else if (((VCFPiece) (this.filePiece)).groupTwo.containsKey(fieldName)) {
                    result = ((VCFPiece) (this.filePiece)).groupTwo.get(fieldName);
                }/*else if(fieldName.equals("AB"))
                {
                HashMap sm = ((VCFPiece) (this.filePiece)).group;
                Set keys=sm.keySet();
                for(Object key:keys)
                {
                System.out.println(key+"---------vcf no--------------");

                }
                System.out.println("============================================");
                }*/
            } else if (RefelectUtil.containField(fieldName, viewPiece.getClass())) {
                Field field = viewPiece.getClass().getField(fieldName);
                result = field.get(this.viewPiece);
            }

        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        return result;
    }

    public static boolean isNullField(String value) {
        if (value == null || value.equals(".") || value.equals("")) {
            return true;
        }
        return false;
    }

    public Integer treatIntegerFieldValue(String value) {
        if (isNullField(value)) {
            return null;

        }
        return Integer.valueOf(value);
    }

    public String toTitleString() {

        Object result = null;
        if (geneticPiece.id != null && !geneticPiece.id.equals("")) {
            result = "" + geneticPiece.id + "\n";
        } else {
            if (this.filePiece instanceof GFFPiece) {

                Object obj = getDetailFieldValue("ID");
                // System.out.println(id+"------id---------------");
                if (obj != null && !obj.toString().equals("")) {
                    result = obj.toString();
                }
            }

        }
        if (result == null || result.equals("")) {
            result = viewPiece.parent.trackSet.name;
        }
        return result.toString();
    }

    public String toContentString() {
        String results = "";

        if (geneticPiece.start == geneticPiece.end) {
            results += "Site: " + geneticPiece.start + "\n";
        } else {
            results += "From: " + geneticPiece.start + " to: " + geneticPiece.end + "\n";
        }

        if (geneticPiece instanceof ReadPiece) {
            results += "\nSequence: " + geneticPiece.sequence;
            results += "\nQuality: " + ((ReadPiece) geneticPiece).quality;
            results += "\nCIGAR: " + ((ReadPiece) geneticPiece).cigar;
        } else if (geneticPiece instanceof SNPPiece) {

            Object dp = this.getDetailFieldValue("DP");
            if (dp != null && !dp.equals("")) {
                results += "DP: " + dp + "\n";
            }

            if (((SNPPiece) geneticPiece).snpType != null) {
                results += "\nSnpType: " + ((SNPPiece) geneticPiece).snpType;
            }
        }
        return results;
    }

    public String toPopTipString() {
        String results = "";
        results += toTitleString() + "  ";
        results += toContentString();
        return results;
    }

    public String toSearchString() {
        String results = "";
        results += toTitleString() + "  ";
        results += toContentString().replace("\n", "  ");
        return results;
    }

    public String getDefaultFieldValue(PieceField filedName) {
        if (filedName == PieceField.source) {
            return StringRep.PROJECT_NAME;
        } else if (filedName == PieceField.group) {
            return "";
        }
        return ".";
    }

    public ArrayList<String> getExtraFieldName(PieceField[] formatFieldNames) {
        ArrayList<String> extraField = new ArrayList();

        java.util.Iterator it = viewPiece.parent.pieceFieldProperty.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            FieldProperty fp = (FieldProperty) entry.getValue();
            if (fp.select) {
                if (!ForMagic.containField(formatFieldNames, (String) entry.getKey())) {
                    // System.out.println( entry.getKey());
                    extraField.add((String) entry.getKey());
                }
            }
        }
        return extraField;
    }

    public HashMap<String, String> getOutPutGroupValue(PieceField[] formatFieldNames, boolean ifExtra) {
        HashMap<String, String> result = null;
        try {
            Field field = filePiece.getClass().getField("group");
            result = (HashMap<String, String>) field.get(this.filePiece);

            if (ifExtra) {
                ArrayList<String> extraFields = getExtraFieldName(formatFieldNames);
                for (String extaFieldName : extraFields) {
                    Object value = getDetailFieldValue(extaFieldName);
                    if (value != null) {
                        result.put(extaFieldName, value.toString());
                    }
                }
            }

        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return result;
    }

    public String getFormatFieldValue(PieceField[] formatFieldNames, int i, boolean ifExtra) {
        String result = "";
        try {
            PieceField formatFieldName = formatFieldNames[i];

            if (formatFieldName == PieceField.group) {
                // Field field = filePiece.getClass().getField("group");
                // HashMap<String, String>  group = (HashMap<String, String>) field.get(this.filePiece);
                result += getOutPutGroupString(getOutPutGroupValue(formatFieldNames, ifExtra));
                // System.out.println(getOutPutGroupString((HashMap<String, String>) field.get(this.filePiece)));

            } else if (formatFieldName == PieceField.groupTwo) {
                Field field = filePiece.getClass().getField("groupTwo");
                result += getOutPutGroupTwoString((HashMap<String, String>) field.get(this.filePiece), ":");
            } else {
                FieldProperty fp = (FieldProperty) viewPiece.parent.pieceFieldProperty.get(formatFieldName.name());

                Object value = getDetailFieldValue(formatFieldName.name());
                //fp == null 为没有设置该field，比如vcf格式 转为gff format define里没有feature属性,但是其geneticPiece里其实是有feature的
                if ((fp != null && fp.select) || fp == null) {

                    if (value != null) {
                        result += value;
                    }
                }
                if (value == null || value.equals("") || (fp != null && !fp.select)) {
                    //  System.out.println(result+"----------piece field getDefaultFieldValue-------");
                    result += getDefaultFieldValue(formatFieldName);
                }
            }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return result;
    }

    public static String getOutPutValueToFile(String formatFieldName, Object value) {
        if (formatFieldName.equals("strand")) {
            if ((Boolean) value == true) {
                return "'+'";
            } else {
                return "'-'";
            }
        }
        return value.toString();
    }

    public String toFormatString(PieceField[] formatFields, boolean ifExtra) {
        String result = "";
        try {
            ////System.out.println(viewPiece.parent.trackSet.selectField+"--------viewPiece.parent.trackSet.selectField------------------------");
            // String[] formatFields = formatDefine.split("\\s+");
            //先输出所有这种格式的field


            // ArrayList<String> formatArrayList = new ArrayList();
            for (int i = 0; i < formatFields.length; i++) {
                result += getFormatFieldValue(formatFields, i, ifExtra);

                if (i < formatFields.length - 1) {
                    result += "\t";
                }
                //formatArrayList.add(formatFields[i].name());
            }
            //  System.out.println();



            // return result;


            /*    //如果要输出的field 不包含再这种格式，比如snp 输入是vcf,输出是gff格式。则vcf格式里的FILTER	INFO等field 要再group里输出。
            java.util.Iterator it = viewPiece.parent.pieceFieldProperty.entrySet().iterator();
            while (it.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            FieldProperty fp = (FieldProperty) entry.getValue();
            if (fp.select) {
            if (!formatArrayList.contains((String) entry.getKey())) {
            if (((String) entry.getKey()).equals("groupStart")) {
            continue;
            }
            result += getExtraFieldString(formatFields, (String) entry.getKey());
            }
            }

            }*/


        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return result;
    }

    public String getOutPutGroupString(HashMap<String, String> group) {
        String result = "";

        Iterator it = group.entrySet().iterator();
        while (it.hasNext()) {

            Entry et = (Entry) it.next();
            FieldProperty fp = (FieldProperty) viewPiece.parent.pieceFieldProperty.get((String) et.getKey());
            if (fp != null && fp.select) {
                result += et.getKey() + "=" + et.getValue() + "; ";
            }
        }
        return result;
    }

    public String getOutPutGroupTwoString(HashMap<String, String> group, String separator) {
        String keys = "";
        String values = "";

        Iterator it = group.entrySet().iterator();
        while (it.hasNext()) {
            Entry et = (Entry) it.next();
            FieldProperty fp = (FieldProperty) viewPiece.parent.pieceFieldProperty.get((String) et.getKey());
            if (fp != null && fp.select) {
                keys += et.getKey() + separator;
                values += et.getValue() + separator;
                // System.out.println(keys + " " + fp.select);
            }

        }
        keys = keys.substring(0, (keys.length() - 1));
        values = values.substring(0, values.length() - 1);
        return keys + "\t" + values;
    }

    /* public String getExtraFieldString(PieceField[] formatDefine, String fieldName) {

    if (formatDefine == (FileFormat.GFFFormat) || formatDefine == FileFormat.VCFFormat) {
    Object fieldvalue = getDetailFieldValue(fieldName);
    if (fieldvalue != null) {
    return fieldName + "=" + fieldvalue.toString() + "; ";
    } else {
    return "";
    }

    } else {
    return "";
    }
    }*/
    public String toGroupString() {
        String result = "";
        try {
            // //System.out.println(viewPiece.parent.trackSet.selectField + "--------viewPiece.parent.trackSet.selectField------------------------");
            java.util.Iterator it = viewPiece.parent.pieceFieldProperty.entrySet().iterator();
            while (it.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                FieldProperty fp = (FieldProperty) entry.getValue();
                if (fp.select) {
                    Object value = getDetailFieldValue((String) entry.getKey());

                    if (value != null) {
                        value = getOutPutValueToFile((String) entry.getKey(), value);
                        result += (String) entry.getKey() + "=" + value + "; ";
                    }
                }

            }

        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        return result;
    }

    public boolean contains(int x, int y) {
        if (viewPiece.x1 <= x && viewPiece.x2 >= x) {
            if (viewPiece.y1 <= y && viewPiece.y2 >= y) {
                return true;
            }
        }
        return false;
    }
}
