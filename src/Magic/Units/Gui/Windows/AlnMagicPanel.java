/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Windows;

import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.IO.SeqData;
import Magic.Units.File.Parameter.Log;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import utils.SamViewUtil;

/**
 *
 * @author lenovo
 */
public class AlnMagicPanel extends MagicPanel {

    public SeqData seqData;
    public int seqPanelIndex=0;
  

    @Override
    public void paintBuffer(Graphics2D g) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCurrentIndex(int value) {
        return (value) / Log.instance().alignPara.baseWidth;
    }

    public void setSelectedArea() {

        SELECTED_SHAPE = null;
        int x = START.y * Log.instance().alignPara.baseWidth;
        int y = 0;
        if (selectColum == true) {
            y = START.x * Log.instance().alignPara.baseHeight;
        } else {
            y = START.x * Log.instance().alignPara.baseHeight;
            if (START.x < 0) {
                y = 0;
            }

        }
        int w = Math.abs(END.y - START.y) * Log.instance().alignPara.baseWidth;
        int h = Math.abs(END.x - START.x + 1) * Log.instance().alignPara.baseHeight;
        if (w == 0 || h == 0) {
            return;
        }
        SELECTED_SHAPE = (Shape) new Rectangle(x, y, w, h);
    }

    public void setSelectColumShape(int index) {
        // int index = getCurrentIndex(x);
        start.setLocation(0, index);
        end.setLocation(getMaxHeight(), index + 1);
        selectColum = true;
        reOrderStartEndPoint();
        setSelectedArea();
    }

    public void setShapeByOrign(int orignPosion) {
        //System.out.println(orignPosion+"---------orignPosion--------------------");
        int cosencesPosion = SamViewUtil.originToConsesus(orignPosion, seqData);
        // System.out.println(cosencesPosion+"---------cosencesPosion--------------------");
        int x = cosencesPosion - seqData.windowStart;
        //  System.out.println(x + "---------x -   refStart-------------------");
        // System.out.println(seqData.reference);
        setSelectColumShape(x);
    }
}
