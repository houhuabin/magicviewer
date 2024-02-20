/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Color;

import Magic.Units.Color.DiamondBackGround.GradientModel;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.AnnoPara;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ForMagic;

/**
 *
 * @author lenovo
 */
public class BaseImage {

    public GradientModel simpleModel = GradientModel.none;
    public GradientModel lightModel = GradientModel.cross;
    public int alpha = 0;
    public BufferedImage unknownImage;
    //public BufferedImage[] hightLightBackGround;
    private HashMap<String, BufferedImage[]> baseBackGround = new HashMap();
    private HashMap<String, BufferedImage> baseString = new HashMap();
    private HashMap<String, BufferedImage[]> lightBackGround = new HashMap();
    private int showStringThreshhold = 9;
    public int width;
    public int height;
    private static BaseImage baseImage = new BaseImage(Log.instance().alignPara.baseWidth, Log.instance().alignPara.baseHeight);

    public static BaseImage instance() {
        if (baseImage == null) {
        }
        return baseImage;
    }

    public static void reset() {
        baseImage = new BaseImage(Log.instance().alignPara.baseWidth, Log.instance().alignPara.baseHeight);
    }

    public BaseImage(int width, int height) {
        this.width = width;
        this.height = height;
        unknownImage = getLightDnaBackground(lightModel, Log.instance().alignPara.NColor);
       /* hightLightBackGround = new BufferedImage[Log.instance().alignPara.HightLight.length];
        for (int i = 0; i < Log.instance().alignPara.HightLight.length; i++) {
            hightLightBackGround[i] = getLightDnaBackground(simpleModel, Log.instance().alignPara.HightLight[i]);
        }*/




        BufferedImage[] ABackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.AColor, Log.instance().alignPara.qualityGradientSize);
        BufferedImage TBackGround[] = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.TColor,Log.instance().alignPara.qualityGradientSize);
        BufferedImage CBackGround[] = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.CColor,Log.instance().alignPara.qualityGradientSize);
        BufferedImage GBackGround[] = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.GColor,Log.instance().alignPara.qualityGradientSize);
        BufferedImage NBackGround[] = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.NColor,Log.instance().alignPara.qualityGradientSize);
        baseBackGround.put("A", ABackGround);
        baseBackGround.put("T", TBackGround);
        baseBackGround.put("C", CBackGround);
        baseBackGround.put("G", GBackGround);
        baseBackGround.put("N", NBackGround);

        BufferedImage[] aBackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.aLightColor, Log.instance().alignPara.qualityGradientSize);
        BufferedImage[] tBackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.tLightColor, Log.instance().alignPara.qualityGradientSize);
        BufferedImage[] cBackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.cLightColor, Log.instance().alignPara.qualityGradientSize);
        BufferedImage[] gBackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.gLightColor, Log.instance().alignPara.qualityGradientSize);
        BufferedImage[] nBackGround = getDarkerDnaBackground(simpleModel, Log.instance().alignPara.nLightColor, Log.instance().alignPara.qualityGradientSize);
        lightBackGround.put("A", aBackGround);
        lightBackGround.put("T", tBackGround);
        lightBackGround.put("C", cBackGround);
        lightBackGround.put("G", gBackGround);
        lightBackGround.put("N", nBackGround);

        BufferedImage AString = getDnaStringImage("A", Color.white, width, height);
        BufferedImage TString = getDnaStringImage("T", Color.white, width, height);
        BufferedImage CString = getDnaStringImage("C", Color.white, width, height);
        BufferedImage GString = getDnaStringImage("G", Color.white, width, height);
        BufferedImage NString = getDnaStringImage("N", Color.white, width, height);
        //   hightLightBackGround =getDnaImage("", crossModel, Log.instance().alignPara.HightLight, width, height);
        baseString.put("A", AString);
        baseString.put("T", TString);
        baseString.put("C", CString);
        baseString.put("G", GString);
        baseString.put("N", NString);

    }

    public BufferedImage getLightDnaBackground(GradientModel model, Color c) {
        return new DiamondBackGround(model, c, width, height, alpha).getImage();
    }



    public BufferedImage[] getDarkerDnaBackground(GradientModel model, Color c,int size) {
        BufferedImage[] backGround = new BufferedImage[size];
        
        Color[] darkerColors = ColorRep.getDarkerArrayColor(c, size);

        for (int i = 0; i < size; i++) {
            DiamondBackGround dbg = new DiamondBackGround(model, c, width, height, alpha);
          //  System.out.println(darkerColors[i]+"----------darkerColors------------"+darkerColors[i+1]);
            dbg.setColor(darkerColors[i]);
            backGround[i] = dbg.getImage();
        }
        return backGround;
    }

    public BufferedImage getPileBackGround(String base, int pileheight) {

        return new DiamondBackGround(GradientModel.cross, ColorRep.getBaseColor(base), width, pileheight, alpha).getImage();

    }

    public BufferedImage getDnaStringImage(String text, Color c, int width, int height) {
        return new StringImage(text, Log.instance().alignPara.DNA_FONT, Log.instance().alignPara.DNA_COLOR, width, height).getImage();
    }

   public BufferedImage getHighlightBackGround(String base,int qulityIndex) {
       
        if (lightBackGround.containsKey(base)) {
             
            return lightBackGround.get(base)[ForMagic.getValidatedQualityIndex(qulityIndex)];
        } else {
            return unknownImage;
        }
    }
    

   /* public BufferedImage getLightBackGround(String base,int qulityIndex) {
            if (lightBackGround.containsKey(base)) {
                return lightBackGround.get(base);
            } else {
                return unknownImage;
            }   
    }*/
     public BufferedImage getBackGround(String base, int qualityIndex) {

        if (baseBackGround.containsKey(base)) {
           
            //return baseBackGround.get(base)[ForMaigc.getValidatedQualityIndex(qualityIndex)];
          //  System.out.println("base");
             return baseBackGround.get(base)[qualityIndex];
        } else {
            return unknownImage;
        }
    }

    public BufferedImage getStringImage(String base) {
        if (Log.instance().alignPara.DNA_FONT.getSize() < showStringThreshhold) {
            return null;
        }
        if (baseString.containsKey(base)) {
            return baseString.get(base);
        } else {
            return new StringImage(base, Log.instance().alignPara.DNA_FONT, Log.instance().alignPara.DNA_COLOR, width, height).getImage();
        }
    }
}
