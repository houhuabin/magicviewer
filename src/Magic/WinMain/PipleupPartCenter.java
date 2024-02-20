/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.WinMain;

import Magic.Units.Gui.Windows.AlnMagicPartCenter;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.InfoBase;
import Magic.Units.Gui.Windows.MagicPartCenter;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Piece.Piece;
import java.awt.Graphics2D;
import utils.SamViewUtil;
/**
 *
 * @author lenovo
 */
public class PipleupPartCenter extends AlnMagicPartCenter{

    public PipleupPartCenter(MagicPanel magicPanel)
    {
        super(magicPanel);
        
    }

    public void showReadInfo(int x, int y, Piece piece) {
        if (piece == null || parent.tracks == null) {
            return;
        }
        InfoBase rbi = new InfoBase();
        int posion = (x + parent.dx) / Log.instance().alignPara.baseWidth.intValue();
        rbi.title = "Base Coverage:";
        rbi.content = new String[2];
        rbi.content[0] = "posion: " + SamViewUtil.consesusToOrigin(posion + ((PileupPanel)parent).seqData.windowStart, ((PileupPanel)parent).seqData);

        if (rbi.content[1] != null) {
            if (!(rbi.content[1].equals(""))) {
                rbi.content[1] = piece.viewPiece.content.replace("null", "");
            }
        }


        rbi.content[1] = piece.viewPiece.content;
       MagicFrame.instance.poptip.popupInfo(rbi, x, y, parent);
    }
      public void paint(Graphics2D g) {
          
      }

}
