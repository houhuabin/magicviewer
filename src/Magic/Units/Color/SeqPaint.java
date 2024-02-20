/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Color;

import Magic.Units.File.Parameter.Log;

;

import java.awt.Color;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

/**
 *
 * @author Huabin Hou
 */
public class SeqPaint {

    public static void paintRef(String text, String gradientModel, int x, int y, Graphics2D g) {
        BufferedImage baseBackGround = BaseImage.instance().getHighlightBackGround(text,Log.instance().alignPara.qualityGradientSize-1);
        g.drawImage(baseBackGround, x, y, null);
        if (Log.instance().alignPara.showRefString) {
            BufferedImage strImage = BaseImage.instance().getStringImage(text);
            if (strImage != null) {
                g.drawImage(strImage, x, y, null);
            }
        }

    }

    public static void paintRead(String text,int qualityIndex, int x, int y, Graphics2D g) {

        BufferedImage baseBackGround = BaseImage.instance().getBackGround(text,qualityIndex);
        g.drawImage(baseBackGround, x, y, null);
        if (Log.instance().alignPara.showReadString) {
            BufferedImage strImage = BaseImage.instance().getStringImage(text);
            if (strImage != null) {
                g.drawImage(strImage, x, y, null);
            }
        }
    }

    public static void paintHighlightRead(String text, int x, int y, int qualityIdex, Graphics2D g) {

        BufferedImage baseBackGround = BaseImage.instance().getHighlightBackGround(text,qualityIdex);
        g.drawImage(baseBackGround, x, y, null);
        if (Log.instance().alignPara.showHighlightString) {
            BufferedImage strImage = BaseImage.instance().getStringImage(text);
            if (strImage != null) {
                g.drawImage(strImage, x, y, null);
            }
        }
    }

    public static void paintPile(String text, int x, int y, int height, Graphics2D g) {

        BufferedImage pileImage = BaseImage.instance().getPileBackGround(text, height);
        g.drawImage(pileImage, x, y, null);

    }

   
}
