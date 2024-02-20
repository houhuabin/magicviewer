/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Units.Track.Track;
import Magic.Units.Piece.Piece;
import Magic.Units.File.Parameter.AnnoPara;
import Magic.Units.File.Parameter.Log;
import java.awt.Graphics2D;

/**
 *
 * @author Huabin Hou
 */
public class PointTrack extends Track {

    public void paint(Graphics2D g, Piece piece) {
////System.out.println("ddddddddddddddddddd paint");
        g.setColor(this.trackSet.COLOR);
        if (piece.viewPiece.color != null) {
            g.setColor(piece.viewPiece.color);
        }
        int x_site = (piece.geneticPiece.start >> Log.instance().annoPara.ZOOM);
        int y_site = this.middleY - this.trackSet.pieceHeight;
        ////System.out.println(x_site + "   %%%%%$$$$$$$$ " + READ_HEIGHT);
        g.drawLine(x_site, y_site, x_site, this.middleY);
         g.drawLine(x_site, this.middleY-25, x_site, this.middleY);
    //g.fill(piece.shape);

    }
}
