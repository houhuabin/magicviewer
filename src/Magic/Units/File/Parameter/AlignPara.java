/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File.Parameter;

import Magic.Units.Color.BaseImage;
import Magic.Units.Color.ColorRep;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 *
 * @author Huabin Hou
 */
public class AlignPara implements Serializable {

    public int qualityGradientSize=5;

    public boolean showReadString = true;
    public boolean showHighlightString = true;
    public boolean showRefString = true;

    public boolean showPopTip=true;
    public boolean showReadBorder=true;
    public boolean showReadArrow=true;
    //public int HEIGHT[] = new int[3];
    public Integer baseWidth = 11;
    public Integer baseHeight = 17;
    public Font DNA_FONT = new Font("Dialog", Font.PLAIN, 16);
    public Color DNA_COLOR = Color.white;
    public Font AXIS_FONT = new Font("Helvetica", Font.PLAIN, 10);
    /*  public Color AColor = new Color(160, 75, 160);//道奇蓝
    public Color TColor = new Color(170, 110, 0);//暗金色
    public Color CColor = new Color(0, 170, 0);//春天的绿色
    public Color GColor = new Color(0, 130, 170);//橙色
    public Color NColor = Color.darkGray;*/

    public Color AColor = Color.lightGray;//道奇蓝
    public Color TColor = Color.lightGray;//暗金色
    public Color CColor = Color.lightGray;//春天的绿色
    public Color GColor = Color.lightGray;//橙色
    public Color NColor = Color.lightGray;
    /*  public Color aColor = new Color(160, 75, 160);//道奇蓝
    public Color tColor = new Color(170, 110, 0);//暗金色
    public Color cColor = new Color(0, 170, 0);//春天的绿色
    public Color gColor = new Color(0, 130, 170);//橙色
    public Color nColor = Color.darkGray;*/
   /* public  Color AColor = new Color(239, 130, 238);
    public  Color TColor = new Color(255, 165, 0);
    public  Color CColor =new Color(0, 255, 0);//春天的绿色
    public  Color GColor =  new Color(0, 191, 255);//橙色
    public  Color NColor = Color.gray;*/
    public Color aLightColor = new Color(239, 130, 238);
    public Color tLightColor = new Color(255, 165, 0);
    public Color cLightColor = new Color(0, 255, 0);//春天的绿色
    public Color gLightColor = new Color(0, 191, 255);//橙色
    public Color nLightColor = Color.gray;
    //public Color HightLight = Color.red;
    public Color Border = ColorRep.darkgolden;
    public Color Arrow = Color.lightGray;
    //public BaseImage baseImage = new BaseImage(DNA_FONT_WIDTH, DNA_FONT_HEIGHT);
    public Color HightLight[] = {new Color(51, 0, 0), new Color(102, 0, 0), new Color(153, 0, 0), new Color(204, 0, 0), Color.red};
    /*public Color AColor[] = {new Color(51, 0, 0), new Color(102, 0, 0), new Color(153, 0, 0), new Color(204, 0, 0), Color.red};
    public Color TColor[] = {new Color(51, 0, 0), new Color(102, 0, 0), new Color(153, 0, 0), new Color(204, 0, 0), Color.red};
    public Color CColor[] = {new Color(51, 0, 0), new Color(102, 0, 0), new Color(153, 0, 0), new Color(204, 0, 0), Color.red};
    public Color GColor[] = {new Color(51, 0, 0), new Color(102, 0, 0), new Color(153, 0, 0), new Color(204, 0, 0), Color.red};
    public Color NColor[] = { new Color(0, 0, 0),    new Color(52, 52, 52),    new Color(105, 105, 105),    new Color(157, 157, 157),    new Color(210, 210, 210)};*/

    /*  public Color ReadGrayColor[] = {
    new Color(0, 0, 0),
    new Color(52, 52, 52),
    new Color(105, 105, 105),
    new Color(157, 157, 157),
    new Color(210, 210, 210)
    };*/
    public int PIPLE_HEIGHT[];


    public static void main(String[] args)
    {

    }
}
