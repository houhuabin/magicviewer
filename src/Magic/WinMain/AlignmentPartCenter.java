/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import Magic.Units.Gui.Windows.AlnMagicPartCenter;
import Magic.Units.Gui.Windows.MagicPartCenter;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.InfoBase;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Piece.GeneticUnit.ReadPiece;

import java.awt.event.MouseEvent;

import javax.swing.text.BadLocationException;
import utils.ReportInfo;
import utils.SamViewUtil;

/**
 *
 * @author lenovo
 */
public class AlignmentPartCenter extends AlnMagicPartCenter {

    public MagicFrame magicFrame;
    private AlignmentPanel alignmentPanel;

    public AlignmentPartCenter(MagicPanel magicPanel) {
        super(magicPanel);
        alignmentPanel = (AlignmentPanel) magicPanel;
        magicFrame = alignmentPanel.magicFrame;
    }

    public void showReadInfo(int x, int y) throws BadLocationException {
        if (parent == null || parent.ifPaint == false || x < 0 || parent.tracks == null || parent.tracks[0] == null || focusedPiece == null || alignmentPanel.seqData == null) {
            MagicFrame.instance.poptip.setVisible(false);
            return;
        }
        InfoBase rbi = new InfoBase();

        int posion = ((x + parent.dx) / Log.instance().alignPara.baseWidth) + 1;

        rbi.content = new String[4];
        rbi.title = focusedPiece.geneticPiece.id;
        rbi.content[0] = "Posion: " + SamViewUtil.consesusToOrigin(posion + alignmentPanel.seqData.windowStart - 1, alignmentPanel.seqData);//ref posion

        int readPosiont = posion - ((ReadPiece) focusedPiece.geneticPiece).windowStart + 1;
        rbi.content[1] = "Base Quality: " + String.valueOf(((ReadPiece) (focusedPiece.geneticPiece)).quality.charAt(readPosiont - 1) - Log.instance().global.Qualty_SCORE_ADD);
        rbi.content[2] = "CIGAR: " + ((ReadPiece) (focusedPiece.geneticPiece)).cigar;
        rbi.content[3] = "Mapping Quality: " + ((ReadPiece) (focusedPiece.geneticPiece)).mappingQuality;
        rbi.read = ((ReadPiece) (focusedPiece.geneticPiece)).sequence;
        rbi.quality = ((ReadPiece) (focusedPiece.geneticPiece)).quality;
        rbi.strand = focusedPiece.geneticPiece.strand;
        int refStart = ((ReadPiece) focusedPiece.geneticPiece).windowStart - 1;
        if (alignmentPanel.seqData.reference == null) {
            return;
        }
        rbi.refSeq = alignmentPanel.seqData.reference.substring(refStart + alignmentPanel.seqData.refSub, ((ReadPiece) focusedPiece.geneticPiece).windowEnd - 1 + alignmentPanel.seqData.refSub);
        rbi.posion = readPosiont + alignmentPanel.seqData.refSub;
        MagicFrame.instance.poptip.popupInfo(rbi, x, y, parent);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // TODO add your handling code here:

        if (alignmentPanel.seqData == null) {
            return;
        }
        parent.requestFocus();
        int x = evt.getX();
        int y = evt.getY();

        //   int line = alignmentPanel.getCurrentLine(parent.dy + y);
        //  int index = alignmentPanel.getCurrentIndex(parent.dx + x);

        if (evt.getButton() == MouseEvent.BUTTON3) {
            PopupMenu(evt);
        } else {
            int index = alignmentPanel.getCurrentIndex(parent.dx + x);
            MagicFrame.instance.annoAssemblyPanel.selectX = index + alignmentPanel.seqData.windowStart;
            alignmentPanel.setSelectColumShape(index);
        }
        alignmentPanel.setUpdateBuffer(true);
        alignmentPanel.repaint();
        MagicFrame.instance.annoAssemblyPanel.reflash();
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        //  System.out.println("--------mouseMoved----------------");
        if (alignmentPanel.seqData == null) {
            //System.out.println(alignmentPanel+"=======alignmentPanel======");
            return;
        }
        alignmentPanel.requestFocus();
        int x = evt.getX();
        int y = evt.getY();
        focusedPiece = alignmentPanel.getFocusedPiece(x, y);

        setPiece(focusedPiece);
        int index = alignmentPanel.getCurrentIndex(x);
        if (evt.getButton() == MouseEvent.BUTTON3) {

            alignmentPanel.start.setLocation(0, index);
            alignmentPanel.end.setLocation(alignmentPanel.getMaxHeight(), index + 1);
            alignmentPanel.selectColum = true;
        } else {
            try {
                showReadInfo(x, y);
            } catch (BadLocationException ex) {
                ReportInfo.reportError("", ex);
            }
        }

        alignmentPanel.repaint();
    }
}
