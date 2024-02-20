/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File.Parameter;

//import SeeEgg.Units.TrackSet;
import Magic.Units.Alignment.*;
import Magic.Units.File.Parameter.AlignPara;
import Magic.Units.File.Parameter.GlobalSetting;
import java.io.File;
import java.lang.reflect.Field;
import utils.FileUtil;
import utils.RefelectUtil;
import utils.ReportInfo;

;

import Magic.Units.IO.ViewerLog;

import java.io.Serializable;

import java.util.Vector;

/**
 *
 * @author Huabin Hou
 */
public class Log implements Serializable {

    private static LogImplement log = new LogImplement();

    public static LogImplement instance() {
        if (log == null) {
            // log= new Log();
        }
        return log;
    }

    public static void ifChangeWorkerSpace(String workerSpeceFile) {
        String oldWorkSpacePath = log.projectProperty.workspacePath;
        String newWorkSpacePath = FileUtil.getParentPath(workerSpeceFile);
        // System.out.println(newWorkSpacePath+"===new old========"+oldWorkSpacePath);
        if (!oldWorkSpacePath.equals(newWorkSpacePath)) {

            changeWorkerSpaceField(log, oldWorkSpacePath, newWorkSpacePath);
        }


    }

    public static void changeWorkerSpaceField(Object obj, String oldWorkSpacePath, String newWorkSpacePath) {
        try {
            Class clz = obj.getClass();
            Field[] clzFields = clz.getDeclaredFields();


            for (int i = 0; i < clzFields.length; i++) {
                Field field = clzFields[i];

                Class type = field.getType();

                if (type.equals(String.class)) {

                    String value = (String) field.get(obj);
                    if (value != null) {
                        // changeFileSeparator(value);                      

                        String newvalue = value.replace(oldWorkSpacePath, newWorkSpacePath);
                        if (!((String) field.get(obj)).contains(File.separator)) {
                           // System.out.println("----------contain-------------");

                            newvalue = changeFileSeparator(newvalue);
                          //  System.out.println(newvalue);
                        }
                        field.set(obj, newvalue);
                    }

                    //  System.out.println(newvalue + "--+++++++--" + value + "+++" + field.getName());
                } else if (type.equals(Vector.class)) {
                    //  System.out.println("Vector------------------------------" + field.getName());
                    Vector vc = (Vector) field.get(obj);
                    if (vc == null) {
                        continue;
                    }
                    for (Object subObj : vc) {
                        changeWorkerSpaceField(subObj, oldWorkSpacePath, newWorkSpacePath);
                    }
                } else {
                    Object subObj = field.get(obj);
                    if (subObj instanceof LogAbstract) {
                        //  System.out.println(subObj.getClass().getName()+"======");
                        changeWorkerSpaceField(subObj, oldWorkSpacePath, newWorkSpacePath);
                    }
                }
                // System.out.println(type+"++++++++++type++++++++");
            }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

    }

    public static String changeFileSeparator(String subObj) {
        return subObj.replaceAll("\\\\|/", File.separator);

    }

    public static void setInstance(LogImplement logBean) {
        log = logBean;
    }

    public static void main(String[] argv) {

        File hhb = new File("E:\\project\\magicinsight\\distribute\\doc.php");
        System.out.println(hhb.getAbsolutePath());

        System.out.println(hhb.getAbsolutePath().contains(File.separator));


        Field[] clzFields = ViewerLog.class.getDeclaredFields();
        // System.out.println(clzFields.length + "\n\n");
        for (Field field : clzFields) {

            Class type = field.getType();
            //  System.out.println(field.getName() + "   +++++++++++++     " + type);
        }

        /*String old = "E:\\project\\magicinsight\\test\\chrM";
        String value = "E:\\project\\magicinsight\\test\\chrM\\hhb";
        String news = "E:\\project\\magicinsight\\chrM";
        System.out.println(value.replace(old, news));*/
    }
}
