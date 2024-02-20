/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Piece.NSEPiece;
import Magic.Units.Piece.Piece;
import Magic.Units.Color.SeqPaint;

import java.awt.Color;

import java.lang.reflect.Field;
import utils.ReportInfo;
import Magic.Units.File.Parameter.Log;
import Magic.Units.File.Parameter.StringRep;
import java.awt.Graphics2D;

/**
 *
 * @author Huabin Hou
 */
public class PileupTrack extends Track {

    public int FONT_WIDTH;
    public int averageCover;
    public int maxCover;
    public float heightAdjust=1;

    public PileupTrack() {

        trackSet.name = StringRep.READS;
        trackSet.format = StringRep.BIS;
    }

    public void paint(Graphics2D g) {
        for (Piece piece : this.currentPieces) {
            paintImplement(g, piece);
        }
    }

  
    public void paintImplement(Graphics2D g, Piece nsepiece) {
        //subPaint(g, nsepiece, 0);
        for (Piece piece : ((NSEPiece) nsepiece).subPieceList) {
            subPaint(g, piece, 1);
        }
    }

    public void subPaint(Graphics2D g, Piece piece, int depth) {

        int height = (int) (piece.viewPiece.y2) + 1;
        try {

            if (depth > 0) {
                //  //System.out.println(piece.viewPiece.x1 + "----------piece.viewPiece.x1-----------");
                SeqPaint.paintPile(piece.viewPiece.content, piece.viewPiece.x1, piece.viewPiece.y1, height, g);
            }

        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }
    }

    @Override
    public void setPieceViewPropoery(Piece nsepiece) {
        setSubPieceViewPropoeryX(nsepiece, (NSEPiece) nsepiece);
        for (Piece piece : ((NSEPiece) nsepiece).subPieceList) {
            setSubPieceViewPropoeryX(piece, (NSEPiece) nsepiece);
        }

    }

    public void setTrackViewPropoeryY() {
        for (Piece piece : currentPieces) {
            setPieceViewPropoeryY(piece);
        }

    }

    public void setPieceViewPropoeryY(Piece nsepiece) {
        setSubPieceViewPropoeryX(nsepiece, (NSEPiece) nsepiece);
        for (Piece piece : ((NSEPiece) nsepiece).subPieceList) {
            setSubPieceViewPropoeryY(piece, (NSEPiece) nsepiece);
        }

    }

    public void setSubPieceViewPropoeryX(Piece piece, NSEPiece nsepiece) {
        piece.viewPiece.x1 = nsepiece.geneticPiece.start * Log.instance().alignPara.baseWidth;
        piece.viewPiece.x2 = piece.viewPiece.x1 + Log.instance().alignPara.baseWidth;
    }

    public void setSubPieceViewPropoeryY(Piece piece, NSEPiece nsepiece) {
        piece.viewPiece.y1 = (int) (piece.viewPiece.y1 * heightAdjust);
        piece.viewPiece.y2 = (int) (piece.viewPiece.y2 * heightAdjust);
    }
}
