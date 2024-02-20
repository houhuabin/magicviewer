/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Color.ColorRep;

import Magic.Units.Piece.Piece;
import Magic.Units.File.Parameter.ForEverStatic;
import utils.ReportInfo;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Task.SimpleTask;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.GeneticUnit.BasePiece;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.WinMain.MagicFrame;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Huabin Hou
 */
public class SNPTrack extends Track {

    public int windowLen = 3;//移位 fold=8
    public int heightAdjustThreshold = 80;
    public int currentWindowContain = 0;
    public int lastWindw = 0;
    public float circlePercent = 0.15f;
    public int x_site = 0;
    public int y_site = 0;
    private float radium = 0;
    public float maxDP = 1;

    public SNPTrack() {
        super();
        this.trackSet.pieceHeight = 60;
        this.trackSet.COLOR = ColorRep.darkorange;
        this.trackSet.trackHeight = 90;

    }

    @Override
    public void paintMiddleLine(Graphics2D g, int dx, int width) {
    }

    @Override
    public void getTrack(String infile, String contig,TaskBase task) {
        try {
            // this.trackmonitor = monitor;
//            MagicFrame.instance.monitor.setVisible(true);

            // String format = FileFormat.checkFileFormatByContent(infile);

            // //System.out.println(format+"format----------------------");

            //  Class cls = Class.forName("Magic.Units.Piece." + format + "Piece");
            //为了取消读取任务的时候不会显示读取一半的内容 分两步执行
            HashMap<String, ArrayList<Piece>> itemsTemp = getTrackItems(infile, contig,task);
            this.iteratorPieces.set(itemsTemp);
            //    System.out.println(contig + "------------------contig------------------------");
            ArrayList<Piece> tem = itemsTemp.get(contig);
            if (tem != null) {
                this.currentPieces = tem.toArray(new Piece[0]);
                for (Piece piece : this.currentPieces) {
                    Object dp = piece.getDetailFieldValue("DP");
                    //int height= 1;                   
                    if (dp != null && !dp.equals("")) {
                        maxDP = Math.max(maxDP, (float) Float.valueOf((String) dp));
                    }
                    piece.geneticPiece.end = piece.geneticPiece.start;

                }
            }

            //System.out.println("maxDP:"+maxDP);


        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    public BasePiece getGeneticPiece() {
        return new SNPPiece();
    }

    @Override
    public void paintInit() {
        lastWindw = 0;
        currentWindowContain = 0;

    }

    public Color getSnpColor(SNPPiece sp) {
        if (sp.snpType == null) {

            return Log.instance().annoPara.nullSnpColor;
        }
        //System.out.println("sp.snpType == " + sp.snpType);
        switch (sp.snpType) {
            case intergenic:
                return Log.instance().annoPara.intergenicSnpColor;
            case intron:
                return Log.instance().annoPara.intronSnpColor;
            case missense:
                return Log.instance().annoPara.missenseSnpColor;
            case nonsense:
                return Log.instance().annoPara.nonsenseSnpColor;
            case readthrough:
                return Log.instance().annoPara.readthroughSnpColor;
            case spliceSite:
                return Log.instance().annoPara.spliceSiteSnpColor;
            case synonymous:
                return Log.instance().annoPara.synonymousSnpColor;
            case UTR3:
                return Log.instance().annoPara.UTR3SnpColor;
            case UTR5:
                return Log.instance().annoPara.UTR5SnpColor;
            default:
                return Log.instance().annoPara.nullSnpColor;
        }
    }

    @Override
    public void paintImplement(Graphics2D g, Piece piece) {
        // //System.out.println(blocks_in_window.size()+"======================blocks_in_window.size()===========================");
        // if (Log.instance().reference.currentContigLen>>(Log.instance().linearPara.ZOOM+4) > Log.instance().global.screenLen) {
        if (piece == null) {
            return;
        }
        setPieceViewPropoery(piece);
        if (!isInAnoDisplay(piece)) {
            return;
        }
        Shape shape = getShape(piece);
        radium = this.trackSet.pieceHeight * circlePercent;

        if (true) {

            if (shape == null) {
                return;
            }
            if (trackSet.COLOR != null) {
                g.setColor(trackSet.COLOR);

            }

            Ellipse2D circle = new Ellipse2D.Double();

            circle.setFrameFromCenter(x_site, y_site - radium, x_site + radium, y_site);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g.draw(shape);
            //  g.draw(circle);
            //  circle.setFrameFromCenter(x_site, y_site - radium, x_site + radium + 1, y_site + 1);
            g.draw(circle);
            g.setColor(Color.lightGray);
            g.fill(circle);
            //  System.out.println(piece.geneticPiece.getClass());
            // if (piece.geneticPiece instanceof SNPPiece) {

            Color pieColor = getSnpColor((SNPPiece) piece.geneticPiece);
            g.setColor(pieColor);
            //  }
            int startAngle = -90;
            int angleLen = 360;

            Object AB = piece.getDetailFieldValue("AB");
            if (AB != null && !AB.toString().equals("")) {
                angleLen = (int) (360 * (1 - Float.valueOf(AB.toString())));
                startAngle = 90 - angleLen;
            }


            g.fill(new Arc2D.Double(x_site - radium, y_site - radium * 2, radium * 2, radium * 2, startAngle, angleLen, Arc2D.PIE));
            //  g.fillArc(x_site, y_site, (int)radium, (int)radium, 0, 1);
            // 

        } else {
            int realWidowLen = ((1 << Log.instance().annoPara.ZOOM) << windowLen);

            int currentWindow = piece.geneticPiece.start / realWidowLen;
            if (currentWindow > lastWindw) {
                drawWindow(g);
                currentWindowContain = 0;
                lastWindw = currentWindow;
            } else {
                currentWindowContain++;
            }

        }

    }

    public void drawWindow(Graphics2D g) {

        int realWidowLen = ((1 << Log.instance().annoPara.ZOOM) << windowLen);
        x_site = realWidowLen * lastWindw >> Log.instance().annoPara.ZOOM;
        y_site = this.middleY + this.trackSet.trackHeight / 2 - 1;
        int width = 1 << windowLen;
        float percent = (float) currentWindowContain / (currentPieces.length / (ForEverStatic.desktopBoundsWidth >> windowLen));
        //   //System.out.println(percent);
        int height = (int) (percent * this.trackSet.pieceHeight);
        Rectangle rec = new Rectangle(x_site, y_site - height, width, height);
        g.setColor(trackSet.COLOR);
        g.draw(rec);
    }

    public Shape getShape(Piece piece) {

        x_site = ((piece.geneticPiece.start - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM);
        y_site = this.middleY + this.trackSet.trackHeight / 2 - 1;

        Object dp = piece.getDetailFieldValue("DP");
        //int height= 1;
        float DPpercent = 0;
        if (dp != null && !dp.equals("")) {
            DPpercent = ((float) Float.valueOf((String) dp)) / maxDP;
        }
        float height = (this.trackSet.pieceHeight * (1 - circlePercent * 2)) * DPpercent;
        float y1 = y_site - this.trackSet.pieceHeight * circlePercent * 2;
        float y2 = y1 - height;
        return (Shape) new Line2D.Double(x_site, y1, x_site, y2);
    }

    @Override
    public void setPieceViewPropoery(Piece piece) {
        piece.viewPiece.x1 = (int) (((piece.geneticPiece.start - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM) - radium);
        piece.viewPiece.x2 = (int) (piece.viewPiece.x1 + 2 * radium);
        piece.viewPiece.y1 = y_site - this.trackSet.pieceHeight;
        piece.viewPiece.y2 = y_site;
    }
}
