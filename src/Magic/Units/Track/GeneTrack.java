/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Piece.NSEPiece;
import Magic.Units.Piece.Piece;
import Magic.IO.DataRep;
import Magic.Units.Color.ColorRep;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Task.SimpleTask;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.GeneticUnit.CDSPiece;
import Magic.Units.Piece.GeneticUnit.GenePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.Utr3Piece;
import Magic.Units.Piece.GeneticUnit.Utr5Piece;
import Magic.WinMain.MagicFrame;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ReportInfo;

/**
 *
 * @author Huabin Hou
 */
public class GeneTrack extends ArrowTrack {
    //seqname  source   start   end   score  strand   frame    attribute

    public int showSubZoom = 11;
    private int minRowHeight = 80;

    public GeneTrack() {
        super();       // setStaticPara(LinearP);
        this.trackSet.pieceHeight = 12;
        this.trackSet.COLOR = ColorRep.warorange;
        this.trackSet.trackHeight = 240;
        // //System.out.println(trackSet.COLOR + "              trackSet.COLOR-------------------------");
    }

    /*public void paint(Graphics2D g) {
    if (pieces == null) {
    return;
    }
    for (Piece piece : this.pieces) {
    paintImplement(g, piece);
    }

    }*/
    @Override
    public void paintImplement(Graphics2D g, Piece piece) {
        setPieceViewPropoery(piece);
        Shape shape = getGeneShape(piece);

        if (trackSet.COLOR != null) {

            g.setColor(trackSet.COLOR);
        }
        if (shape == null) {
            return;
        }
        if (piece.viewPiece.color != null) {
            g.setColor(piece.viewPiece.color);
        }
        if (shape != null) {
            g.fill(shape);
        }



        /*  if (piece instanceof NSEPiece) {
        if (((NSEPiece) piece).subPieceList != null) {
        for (Piece nse : ((NSEPiece) piece).subPieceList) {
        {
        if (Log.instance().annoPara.ZOOM < showSubZoom && (Log.instance().annoPara.VIEW_MODE.equals(StringRep.NONOVERLAP))) {
        nse.viewPiece.vertical = piece.viewPiece.vertical;
        paintSub(g, nse);

        }

        }
        }

        }
        }*/
        paintGene(piece, g);
        g.setComposite(AlphaComposite.getInstance(10, 1));
    }

    private void paintGene(Piece gene, Graphics2D g) {

        if (gene.geneticPiece instanceof GenePiece) {
           
            g.setColor(CDSPiece.color);
           /* for (CDSPiece cds : ((GenePiece) (gene.geneticPiece)).CDSs) {

                Shape shape = getShape(cds, gene.viewPiece.vertical);
                g.fill(shape);
            }*/
            paintElements(((GenePiece) (gene.geneticPiece)).CDSs,gene.viewPiece.vertical,g);
            g.setColor(Utr5Piece.color);
           // Shape shape = getShape(((GenePiece) (gene.geneticPiece)).utr5, gene.viewPiece.vertical);
             paintElements(((GenePiece) (gene.geneticPiece)).utr5,gene.viewPiece.vertical,g);
          //  g.fill(shape);
           //  System.out.println("------CDSPiece------------"+shape);
            g.setColor(Utr3Piece.color);
            paintElements(((GenePiece) (gene.geneticPiece)).utr3,gene.viewPiece.vertical,g);
          //  g.fill(shape);
           // shape = getShape(((GenePiece) (gene.geneticPiece)).utr3, gene.viewPiece.vertical);

           // g.fill(shape);
        }
    }

    public void paintElements(ArrayList elements, int vertical,Graphics2D g)
    {
      for (Object cds : elements) {

                Shape shape = getShape((GeneticPiece) cds,vertical);
                g.fill(shape);
         }
    }

    /*  public void paintSub(Graphics2D g, Piece piece) {
    setPieceViewPropoery(piece);
    Shape shape = getSubShape(piece);
    g.setComposite(AlphaComposite.getInstance(10, 0.9F));

    if (trackSet.COLOR != null) {

    g.setColor(trackSet.COLOR);
    }
    if (shape == null) {
    return;
    }
    if (piece.viewPiece.color != null) {
    g.setColor(piece.viewPiece.color);
    ////System.out.println(piece.viewPiece.color + " --------------------------------piece.viewPiece.color");
    }
    if (shape != null) {
    g.fill(shape);
    }


    }


     */
    @Override
    public void getTrack(String infile, String contig,TaskBase task) {
        try {

//            MagicFrame.instance.monitor.setVisible(true);


            //  Class cls = Class.forName("Magic.Units.Piece." + format + "Piece");
            HashMap<String, ArrayList<Piece>> itemsTemp = getTrackItems(infile, contig,task);
            // System.out.println(itemsTemp.size() + "======itemsTemp======="+contig);
            this.iteratorPieces.set(itemsTemp);
            if (itemsTemp == null || itemsTemp.get(contig) == null) {
                return;
            }
            this.currentPieces = itemsTemp.get(contig).toArray(new Piece[0]);


            this.trackSet.maxVertical = DataRep.findCluster((Piece[]) currentPieces);

            // System.out.println(trackSet.maxVertical+"--"+trackSet.pieceHeight+"--"+trackSet.pieceInterval);
            int newHaight = ((this.trackSet.maxVertical + 1) << 1) * (this.trackSet.pieceHeight + this.trackSet.pieceInterval);
            // System.out.println(newHaight+"======newHaight===");
            this.trackSet.trackHeight = Math.min(this.trackSet.trackHeight, newHaight);
            this.trackSet.trackHeight = Math.max(this.trackSet.trackHeight, minRowHeight);
            // System.out.println(trackSet.trackHeight+"======trackSet.trackHeight===");
            //System.out.println(this.trackSet.trackHeight);

        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    @Override
    public void setPieceViewPropoery(Piece piece) {

        if (piece == null || piece.geneticPiece == null || piece.geneticPiece == null) {
            return;
        }
        piece.viewPiece.x2 = (piece.geneticPiece.end - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM;
        piece.viewPiece.x1 = (piece.geneticPiece.start - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM;

        if (Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) {
            if (piece.geneticPiece.strand) {
                //  System.out.println(this.trackSet.READ_HEIGHT+"-------------------"+piece.viewPiece.x2 );
                // piece.viewPiece.x2 += this.trackSet.READ_HEIGHT;
                //System.out.println(this.trackSet.READ_HEIGHT+"    "+piece.viewPiece.x2 );
            } else {
                //  piece.viewPiece.x1 += this.trackSet.READ_HEIGHT;
            }
        }


        int y_site = 0;
        if (Log.instance().annoPara.VIEW_MODE.equals(StringRep.SINGLE_LINE)) {
            y_site = this.middleY - this.trackSet.pieceHeight / 2;
        } else if (Log.instance().annoPara.VIEW_MODE.equals(StringRep.NONOVERLAP)) {
            if (piece.geneticPiece.strand) {
                y_site = this.middleY - (this.trackSet.pieceHeight + this.trackSet.pieceInterval) * (piece.viewPiece.vertical + 1) - 1;
            } else {
                y_site = this.middleY + (this.trackSet.pieceHeight + this.trackSet.pieceInterval) * piece.viewPiece.vertical + this.trackSet.pieceInterval + 1;
            }
        }
        piece.viewPiece.y1 = y_site;
        piece.viewPiece.y2 = piece.viewPiece.y1 + this.trackSet.pieceHeight;
    }
}
