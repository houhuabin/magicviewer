package Magic.Units.Color;

import Magic.Units.File.Parameter.Log;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import utils.RefelectUtil;
import utils.ReportInfo;

public class ColorRep {

    public Color getInfoColor() {
        Color c = (Color) UIManager.get("info");
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 190);
    }
    public static final Color lightgray = Color.lightGray;
    public static final Color red = Color.red;
    public static final Color gray = Color.gray;
    public static final Color green = Color.green;
    public static final Color pink = Color.pink;

    public static final Color aquamarine = new Color(127, 255, 212);
    public static final Color darkorange = new Color(255, 140, 0);   
    public static final Color cadetblue = new Color(95, 158, 160);
   public static final Color snow = new Color(255, 250, 250);



    public static final Color gold = new Color(255, 215, 0);
    public static Color darkgolden = new Color(160, 120, 70);
    public static Color warblue = new Color(30, 144, 255);//春天的绿色
    public static Color warorange = new Color(255, 165, 0);//橙色
    public static Color carrot = new Color(246, 244, 236);

    public static Color AxiColor = gold;//金
    public static Color cds = new Color(244, 163, 163);//
    public static Color utr = new Color(200, 109, 109);//


    public static float gradiantValue = 0.5F;
    public static Color GrayColor[] = {
        new Color(0, 0, 0), new Color(80, 80, 80),
        new Color(100, 100, 100), new Color(120, 120, 120),
        new Color(140, 140, 140), new Color(160, 160, 160),
        new Color(180, 180, 180), new Color(210, 210, 210),
        new Color(255, 255, 255)};

    public Vector<Color> getAllColors() {
        Vector<Color> vector = new Vector<Color>();
        try {

            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (RefelectUtil.isThisField(field, Color.class)) {
                    Color color = (Color) field.get(this);
                    vector.add(color);
                }
            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return vector;
    }

    public static Color getNearColor(Color color) {
        Vector<Color> all = new ColorRep().getAllColors();
        int min_index = 0;
        double min = Math.pow(10, 10);
        double distance = 0;
        for (int i = 0; i < all.size(); i++) {
            distance = getDistance(all.elementAt(i), color);
            if (distance < min) {
                min = distance;
                min_index = i;
            }
        }
        return all.elementAt(min_index);
    }

    public static double getDistance(Color c1, Color c2) {
        double distance = 0;
        distance += (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed());
        distance += (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen());
        distance += (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
        distance = Math.pow(distance, 0.5);

        return distance;
    }

    public static Color charToColor(char base) {
        switch (base) {
            case 'A':
                return lightgray;
            case 'T':
                return lightgray;
            case 'C':
                return lightgray;
            case 'G':
                return lightgray;
            default:
                return red;

        }

    }
    public static int maxMappingQuality = 40;

    public static Color getReadArrowColor(int mappingQuality) {
        int usedQuality = Math.min(maxMappingQuality, mappingQuality);
        usedQuality = Math.max(usedQuality, 0);
        int colorNum = usedQuality * 6;
        return new Color(colorNum, colorNum, colorNum);

    }

    public static Color[] getDarkerArrayColor(Color c, int arrayLenth) {
        Color[] result = new Color[arrayLenth];
        int redValue = c.getRed();
        int blueValue = c.getBlue();
        int greenValue = c.getGreen();
        for (int i = 0; i < arrayLenth; i++) {
            result[i] = new Color(redValue * (i + 1) / arrayLenth, greenValue * (i + 1) / arrayLenth, blueValue * (i + 1) / arrayLenth);

        }
        return result;
    }

    public static Color getDrakerColor(Color c, float gradiantValue) {
        int redValue = c.getRed();
        int blueValue = c.getBlue();
        int greenValue = c.getGreen();
        // //System.out.println(redValue*(1-gradiantValue)+"----------------------"+greenValue*(1-gradiantValue)+"-------------------"+blueValue*(1-gradiantValue));
        Color result = new Color((int) Math.min(redValue * (1 - gradiantValue), 255), (int) Math.min(greenValue * (1 - gradiantValue), 255), (int) Math.min(blueValue * (1 - gradiantValue), 255));
        return result;
    }

    public static Color getDrakerColor(Color c) {
        int redValue = c.getRed();
        int blueValue = c.getBlue();
        int greenValue = c.getGreen();
        // //System.out.println(redValue*(1-gradiantValue)+"----------------------"+greenValue*(1-gradiantValue)+"-------------------"+blueValue*(1-gradiantValue));
        Color result = new Color((int) Math.min(redValue * (1 - gradiantValue), 255), (int) Math.min(greenValue * (1 - gradiantValue), 255), (int) Math.min(blueValue * (1 - gradiantValue), 255));
        return result;
    }

    public static Color getBrighterColor(Color c) {
        int redValue = c.getRed();
        int blueValue = c.getBlue();
        int greenValue = c.getGreen();
        Color result = new Color((int) Math.min(redValue * (1 + gradiantValue), 255), (int) Math.min(greenValue * (1 + gradiantValue), 255), (int) Math.min(blueValue * (+gradiantValue), 255));
        return result;
    }

    public static Color[] getArrayColor(Color c1, Color c2, int arrayLenth) {
        Color[] result = new Color[arrayLenth];
        int subRedValue1 = c1.getRed() - c2.getRed();

        int subGgreenValue1 = c1.getGreen() - c2.getGreen();
        int subBlueValue1 = c1.getBlue() - c2.getBlue();

        for (int i = 0; i < arrayLenth; i++) {
            // //System.out.println(c1.getRed() - subGgreenValue1 * i / arrayLenth);
            result[i] = new Color(c1.getRed() - subRedValue1 * i / (arrayLenth - 1), c1.getGreen() - subGgreenValue1 * i / (arrayLenth - 1), c1.getBlue() - subBlueValue1 * i / (arrayLenth - 1));

        }

        return result;
    }

    public static Color getBaseColor(String base) {
        //if(light)
        //{

        /* if(base.toUpperCase().equals("A"))
        {
        return Log.instance().alignPara.aColor;
        } else  if(base.toUpperCase().equals("T"))
        {
        return Log.instance().alignPara.tColor;
        }else  if(base.toUpperCase().equals("C"))
        {
        return Log.instance().alignPara.cColor;
        }else  if(base.toUpperCase().equals("G"))
        {
        return Log.instance().alignPara.gColor;
        }else  if(base.toUpperCase().equals("N"))
        {
        return Log.instance().alignPara.nColor;
        }*/
        //}else
        {
            if (base.toUpperCase().equals("A")) {
                return Log.instance().alignPara.aLightColor;
            } else if (base.toUpperCase().equals("T")) {
                return Log.instance().alignPara.tLightColor;
            } else if (base.toUpperCase().equals("C")) {
                return Log.instance().alignPara.cLightColor;
            } else if (base.toUpperCase().equals("G")) {
                return Log.instance().alignPara.gLightColor;
            } else if (base.toUpperCase().equals("N")) {
                return Log.instance().alignPara.nLightColor;
            }

        }
        return Log.instance().alignPara.NColor;
    }

    public static void main(String[] argv) {
        Color[] red = getArrayColor(new Color(51, 0, 0), Color.red, 5);
        for (Color c : red) {
            //System.out.println(c);
        }

    }
}
