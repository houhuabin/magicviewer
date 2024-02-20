/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Piece.Piece;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.Parameter.AnnoPara;
import Magic.Units.File.Parameter.Log;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

/**
 *
 * @author Huabin Hou
 */
public class ArrowTrack extends Track {


    public Shape getGeneShape(Piece piece) {

        return getArrowShape(piece.viewPiece.x1, piece.viewPiece.y1, piece.viewPiece.x2, this.trackSet.pieceHeight, piece.geneticPiece.strand,0.4f);
    }

    public Polygon getArrowShape(int x, int y, int x2, int h, boolean strand,float heightPercent) {
        /*int w =x2-x;
        if(w<h) w=h;
        Polygon p=new Polygon();
        if(strand)
        {
        p.addPoint(x+(w-h), y+h+h/2);
        p.addPoint(x+w, y+h/2);
        p.addPoint(x+(w-h), y-h/2);
        if(w>h) {
        p.addPoint(x+(w-h), y);
        p.addPoint(x, y);
        p.addPoint(x, y+h);
        p.addPoint(x+(w-h), y+h);
        }
        }
        else
        {
        p.addPoint(x+h, y+h+h/2);
        p.addPoint(x, y+h/2);
        p.addPoint(x+h, y-h/2);

        if(w>h) {
        p.addPoint(x+h, y);
        p.addPoint(x+w, y);
        p.addPoint(x+w, y+h);
        p.addPoint(x+h, y+h);
        }
        }
        return p;*/


        Polygon p = new Polygon();
        if (strand) {
            if (Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) {
                p.addPoint(x2, y + h);
                p.addPoint(x2 + h, y + h / 2);
                p.addPoint(x2, y);
            }
            // if (w > h) {
          
            p.addPoint(x2, (int) (y + h * heightPercent));
            p.addPoint(x, (int) (y + h * heightPercent));
            p.addPoint(x, (int) (y + h - h * heightPercent));
            p.addPoint(x2, (int) (y + h - h * heightPercent));
            // }
        } else {
            if (Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) {
                p.addPoint(x, y);
                p.addPoint(x - h, y + h / 2);
                p.addPoint(x, y + h);
            }

            // if (w > h) {
            p.addPoint(x, (int) (y + h * heightPercent));
            p.addPoint(x2, (int) (y + h * heightPercent));
            p.addPoint(x2, (int) (y + h - h * heightPercent));
            p.addPoint(x, (int) (y + h - h * heightPercent));
            // }
        }
        return p;
        /*  int w = x2 - x;
        Polygon p = new Polygon();
        if (strand) {
        if (Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) {
        p.addPoint(x2- h, y + h);
        p.addPoint(x2, y + h / 2);
        p.addPoint(x2- h, y);
        }
        if (w > h) {
        p.addPoint(x2- h, y + h * heightPercent);
        p.addPoint(x, y + h * heightPercent);
        p.addPoint(x, y + h - h * heightPercent);
        p.addPoint(x2- h, y + h - h * heightPercent);
        }
        } else {
        if (Log.instance().annoPara.ARROW_MODE.equals(StringRep.SHOW_ARROW)) {
        p.addPoint(x, y);
        p.addPoint(x - h, y + h / 2);
        p.addPoint(x, y + h);
        }

        if (w > h) {
        p.addPoint(x, y + h * heightPercent);
        p.addPoint(x2, y + h * heightPercent);
        p.addPoint(x2, y + h - h * heightPercent);
        p.addPoint(x, y + h - h * heightPercent);
        }
        }
        return p;*/

    }
}
