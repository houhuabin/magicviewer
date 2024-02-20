/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Piece.Piece;
import Magic.Units.IO.SeqData;
import Magic.Units.Color.ColorRep;
import Magic.Units.Color.SeqPaint;
import Magic.Units.Piece.GeneticUnit.ReadPiece;
import Magic.Units.File.Parameter.Log;
import Magic.WinMain.MagicFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import utils.ForMagic;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class AlignTrack extends Track {

    public SeqData seqData;

    public AlignTrack() {
        super();

    }

    public void paint(Graphics2D g) {

        for (Piece piece : this.currentPieces) {
            paintImplement(g, piece);
        }
    }

    public void paintImplement(Graphics2D g, Piece piece) {


        if (!isInAlnDisplay(piece)) {
            return;
        }

        int y = piece.viewPiece.y1;
        int x = piece.viewPiece.x1;
        int len = ((ReadPiece) piece.geneticPiece).windowEnd - ((ReadPiece) piece.geneticPiece).windowStart;
        if (len == 0) {
           // System.out.println(piece.geneticPiece.id);
        }

        g.setColor(Color.lightGray);

        for (int s = 0; s < piece.geneticPiece.sequence.length(); s++) {
            if (s + ((ReadPiece) piece.geneticPiece).windowStart - 1 < 0) {
                continue;
            }
            if (s + ((ReadPiece) piece.geneticPiece).windowStart > Log.instance().global.alignmentWindowLen) {
                continue;
            }
            int baseQualityIndex = ((((ReadPiece) (piece.geneticPiece)).quality.charAt(s) - Log.instance().global.Qualty_SCORE_ADD) / 9) + 1;
            //本来是qual > 8为了减少类的数量 提高响应效率
            baseQualityIndex = ForMagic.getValidatedQualityIndex(baseQualityIndex);


            String currentBase = ((ReadPiece) (piece.geneticPiece)).sequence.substring(s, s + 1);

            if (((ReadPiece) (piece.geneticPiece)).sequence.charAt(s) == seqData.reference.charAt(s + ((ReadPiece) piece.geneticPiece).windowStart - 1 + seqData.refSub)) {
                try {
                    SeqPaint.paintRead(currentBase, baseQualityIndex, x + s * Log.instance().alignPara.baseWidth, y, g);
                } catch (Exception ex) {
                    ReportInfo.reportError("", ex);
                }
            } else if ((((ReadPiece) (piece.geneticPiece)).sequence.charAt(s) == '-')) {

                g.setColor(ColorRep.snow);
                g.fillRect(x + s * Log.instance().alignPara.baseWidth, y, Log.instance().alignPara.baseWidth, Log.instance().alignPara.baseHeight);
                g.setColor(Color.red);
                g.fillRect(x + s * Log.instance().alignPara.baseWidth, (y + Log.instance().alignPara.baseHeight / 2 - 2), Log.instance().alignPara.baseWidth, 4);
                g.setColor(Color.lightGray);
            } else {
                SeqPaint.paintHighlightRead(currentBase, x + s * Log.instance().alignPara.baseWidth, y, baseQualityIndex, g);
            }
        }
        Polygon arrow = null;
        Polygon border =null;



        if (Log.instance().alignPara.showReadArrow) {
            //  g.setColor(Log.instance().alignPara.Arrow);
            border = new Polygon();
            arrow = new Polygon();
          //  int baseQualityIndex = ((((ReadPiece) (piece.geneticPiece)).mappingQuality) / 9) + 1;
         //   Color[] darkerColors = ColorRep.getDarkerArrayColor(Log.instance().alignPara.Arrow, Log.instance().alignPara.qualityGradientSize);
            g.setColor(ColorRep.getReadArrowColor(((ReadPiece) (piece.geneticPiece)).mappingQuality));
            // System.out.println(x+"   "+y+"   "+len+"   ");


            if (piece.geneticPiece.strand) {
                arrow.addPoint(x + Log.instance().alignPara.baseWidth * len, y);
                arrow.addPoint(x + Log.instance().alignPara.baseWidth * len, y + Log.instance().alignPara.baseHeight);
                arrow.addPoint(x + Log.instance().alignPara.baseWidth * (len + 1), y + Log.instance().alignPara.baseHeight / 2);

                border.addPoint(x, y);
                border.addPoint(x + Log.instance().alignPara.baseWidth * len, y);              
                border.addPoint(x + Log.instance().alignPara.baseWidth * (len + 1), y + Log.instance().alignPara.baseHeight / 2);               
                border.addPoint(x + Log.instance().alignPara.baseWidth * len, y + Log.instance().alignPara.baseHeight);
                border.addPoint(x, y + Log.instance().alignPara.baseHeight);               
                g.fill(arrow);
            } else {
                arrow.addPoint(x, y);
                arrow.addPoint(x, y + Log.instance().alignPara.baseHeight);
                arrow.addPoint(x - Log.instance().alignPara.baseWidth, y + Log.instance().alignPara.baseHeight / 2);
                
                border.addPoint(x + Log.instance().alignPara.baseWidth * len, y);
                border.addPoint(x, y);
                border.addPoint(x - Log.instance().alignPara.baseWidth, y + Log.instance().alignPara.baseHeight / 2);               
                border.addPoint(x, y + Log.instance().alignPara.baseHeight);
                border.addPoint(x + Log.instance().alignPara.baseWidth * len, y + Log.instance().alignPara.baseHeight);
                g.fill(arrow);
            }
        }

        if (Log.instance().alignPara.showReadBorder) {



            g.setColor(Log.instance().alignPara.Border);
            // System.out.println(x+"   "+y+"   "+len+"   ");
            //  
             if (border != null) {
              g.drawPolygon(border);
            }else
            {
            g.drawRect(x, y, Log.instance().alignPara.baseWidth * len, Log.instance().alignPara.baseHeight);
            }
        }
        g.setColor(Color.BLACK);
    }

    @Override
    public void setPieceViewPropoery(Piece piece) {
        piece.viewPiece.x1 = (((ReadPiece) piece.geneticPiece).windowStart - 1) * Log.instance().alignPara.baseWidth;
        piece.viewPiece.x2 = (((ReadPiece) piece.geneticPiece).windowEnd - 1) * Log.instance().alignPara.baseWidth;
        piece.viewPiece.y1 = (piece.viewPiece.vertical) * Log.instance().alignPara.baseHeight;
        piece.viewPiece.y2 = (piece.viewPiece.vertical + 1) * Log.instance().alignPara.baseHeight;
    }
}
