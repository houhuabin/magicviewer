/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Filter;

import Magic.Analysis.Filter.Filter;
import Magic.Analysis.Table.Operator;
import Magic.Analysis.Table.Operator.RelationType;
import utils.FormatCheck;
import utils.ReportInfo;

/**
 *
 * @author lenovo
 */
public class FieldProperty {

    public boolean select = false;
    public String defaultValue = "";
    public String fieldComparType = "Number";
    public Filter filter;

    public static boolean isNotNullField(Object value) {
        if (value == null || value.toString().equals("") || value.toString().equals(".")) {
            return false;
        }
        return true;
    }

    public static boolean isEqual(String value, String filterValue, String type) {

        if (compareTo(value, filterValue, type) == RelationType.equal) {
            return true;
        }
        return false;
    }

    //如果值不为空返回true.如果值为空根据策略返回响应的值，暂时为true，既如果为空也算符合条件
    public static boolean nullValuePolocy() {
        return true;
    }

    public static boolean isLargeThan(String value, String filterValue, String type) {
        //System.out.println(value+"----------------"+filterValue+"-----------"+type);
        if (!isNotNullField(value)) {
            return nullValuePolocy();
        } else {

            if (compareTo(value, filterValue, type) == RelationType.large) {
                // System.out.println(true);
                return true;
            }
            return false;

        }
    }

    public static boolean isSmallThan(String value, String filterValue, String type) {
        //System.out.println(value+"--------------"+filterValue+"----"+type);
         if (!isNotNullField(value)) {
             //System.out.println(value+"  "+filterValue);
            return nullValuePolocy();
        } else {
             if (compareTo(value, filterValue, type) == RelationType.small) {
                return true;
            }
            return false;

        }
    }

    public static boolean isInRange(String value, String filterValue, String type) {

        if (!isNotNullField(value)) {
            return nullValuePolocy();
        }

        String[] filterValueArray = filterValue.split(",");
        if (filterValueArray.length != 2) {
            // System.out.println(filterValue + "-------------------is not in range format-----------------");
            // ReportInfo.reportValidate("The input is not in range format, the range format is two values separated by comma");
            return false;
        }

        //  System.out.println(value +"-------"+filterValueArray[0]+"---"+filterValueArray[1]+"-------"+type);
        if ((compareTo(value, filterValueArray[0], type) == RelationType.large) && (compareTo(value, filterValueArray[1], type) == RelationType.small)) {
            //  System.out.println(value + "-------" + filterValueArray[0] + "---" + filterValueArray[1] + "-------" + type + "   true");
            return true;
        }
        //  System.out.println(compareTo(value, filterValueArray[0], type) + "-------" + compareTo(value, filterValueArray[1], type) + "--    " + value + "-------" + filterValueArray[0] + "---" + filterValueArray[1] + "-------" + type + "   f");
        return false;
    }

    public static RelationType compareTo(String value, String filterValue, String type) {


        if (value == null && filterValue == null) {
            return RelationType.equal;
        } else if (value == null) {
            return RelationType.large;
        } else if (filterValue == null) {
            return RelationType.small;
        }


        if (type.equals("Number")) {
            if (Double.valueOf(value) == Double.valueOf(filterValue)) {
                return RelationType.equal;
            } else {
                //System.out.println((int) (Double.valueOf(value).compareTo(Double.valueOf(filterValue)))+"-----"+value+"----------"+filterValue);
                return numberToRelationType((int) (Double.valueOf(value).compareTo(Double.valueOf(filterValue))));
            }

        } else if (type.equals("String")) {
            if (value.equals(filterValue)) {
                return RelationType.equal;
            } else {
                return numberToRelationType(value.compareTo(filterValue));
            }
        }
        return RelationType.equal;
    }

    public static RelationType numberToRelationType(int i) {
        if (i == 0) {
            return RelationType.equal;
        } else if (i == 1) {
            return RelationType.large;
        } else if (i == -1) {
            return RelationType.small;
        }
        return RelationType.equal;

    }
}
