/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui;

import Magic.Units.Color.ColorRep;
import Magic.Units.File.Parameter.Log;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.SamViewUtil.Insert;

/**
 *
 * @author Huabin Hou
 */
public class Axi {

    public Font AXIS_FONT = new Font("Monospace", Font.PLAIN, 16);
    FontMetrics fm;
    public Color c;
    private int showStringThreshhold = 3;

    public Axi(Font AXIS_FONT, FontMetrics fm) {
        this.AXIS_FONT = AXIS_FONT;
        this.fm = fm;
        c = ColorRep.AxiColor;
    }

    public Axi(Font AXIS_FONT, FontMetrics fm, Color c) {
        this.AXIS_FONT = AXIS_FONT;
        this.fm = fm;
        this.c = c;
    }

    public void draw(Graphics g, int x1, int y1, int min, int max, int step) {
        int num = (max - min + 1);

        int x2 = x1 + num * step;
        g.setColor(c);
        g.setFont(AXIS_FONT);
        g.drawLine(x1, y1, x2 - step, y1);
        for (int i = 0; i < num; i++) {
            int currentValue = min + i;
            //   g.setColor(LogBean.getInstance().global.AxiColor);
            int textW = fm.stringWidth(String.valueOf(currentValue));
            if (currentValue % 5 != 0) {
                g.drawLine((x1 + i * step), y1, (x1 + i * step), y1 + 3);
            } else if (currentValue % 10 != 0) {
                //g.drawString(currentValue+"", x1 + i * step - textW / 2, y1 - 1);
                g.drawLine((x1 + i * step), y1, (x1 + i * step), y1 + 5);
            } else {
                g.drawLine((x1 + i * step), y1, (x1 + i * step), y1 + 8);
                //  g.setColor(LogBean.getInstance().global.darkGoldColor);
                g.drawString(currentValue + "", x1 + i * step - textW / 2, y1 - 1);

            }
        }
        if (max % step * 10 != 0) {
            //int textW = fm.stringWidth(String.valueOf(max));
            g.drawLine((x1 + (num - 1) * step), y1, (x1 + (num - 1) * step), y1 + 8);
            g.drawString(max + "", x1 + (num - 1) * step, y1 - 1);

        }

    }

    public void drawRef(Graphics g, int x1, int y1, int min, int max, int step, ArrayList<Insert> insertPoint) {
        int num = (max - min + 1);

        int x2 = x1 + num * step;
        int insertedNum = 0;
        g.setColor(c);
        g.setFont(AXIS_FONT);
        g.drawLine(x1, y1, x2 - step, y1);
        for (int i = 0; i < num; i++) {
            int currentValue = min + i;
            int textW = fm.stringWidth(String.valueOf(currentValue));
            // int insertedCurrentValue=currentValue+insertedNum;
            int index = i;

            g.setColor(Color.BLACK);
            if (insertPoint == null) {
                return;
            }
            for (final Insert element : insertPoint) {
                if (element.posion == i) {
                    index = i + insertedNum;
                    g.fillRect(index * step, y1, step * element.len, 10);
                    insertedNum += element.len;
                }
            }
            g.setColor(c);
            index = i + insertedNum;

            if (currentValue % 10 != 0) {
                g.drawLine((x1 + index * step), y1, (x1 + index * step), y1 + 3);
            } else if (currentValue % 20 != 0) {
                //g.drawString(currentValue+"", x1 + i * step - textW / 2, y1 - 1);
                g.drawLine((x1 + index * step), y1, (x1 + index * step), y1 + 5);
            } else {
                g.drawLine((x1 + index * step), y1, (x1 + index * step), y1 + 8);
                //  g.setColor(LogBean.getInstance().global.darkGoldColor);
                if (Log.instance().alignPara.DNA_FONT.getSize() > showStringThreshhold || currentValue % 80 == 0) {
                    g.drawString(currentValue + "", x1 + index * step - textW / 2, y1 - 1);
                }

            }
        }
        if (step == 0) {
            return;
        }
        if (max % step * 10 != 0) {
            //int textW = fm.stringWidth(String.valueOf(max));
            g.drawLine((x1 + (num - 1 + insertedNum) * step), y1, (x1 + (num - 1 + insertedNum) * step), y1 + 8);
            g.drawString(max + "", x1 + (num - 1 + insertedNum) * step, y1 - 1);

        }

    }

    public void drawAnno(Graphics g, int x1, int y1, int x2, int y2, int min, int max, int interval, int zoom) {

        //int x2=x1+num*interval;

        g.setColor(c);
        g.setFont(AXIS_FONT);
        // g.setColor(ColorRep.darkgolden);
        //g.drawRect(x1, y1, x2 - x1, 5);
        // g.setColor(Color.lightGray);
        //g.fillRect(x1 + 1, y1 + 1, x2 - x1 - 2, 4);
        g.drawLine(x1, y1, x2, y2);
        int num = (x2 - x1 + 1);

        for (int i = 0; i < num; i++) {
            int currentValue = (min + i) << zoom;
            int currentX = x1 + i;
            int textW = fm.stringWidth(String.valueOf(currentValue));
            if (currentValue % (interval << zoom) == 0) {
                // g.drawLine(currentX, y1, currentX, y1 + 3);
            }

            if (currentValue % ((interval << zoom) * 2) == 0) {
                g.drawLine(currentX, y1, currentX, y1 + 8);
                g.drawString(currentValue + "", currentX - textW / 2, y1 - 3);

            }
        }
        if (max % interval != 0) {
            g.drawLine(x2, y1, x2, y1 + 12);
            g.drawString(max + "", x2, y1 - 3);

        }

    }
}
