/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Piece.ViewPiece;

import Magic.Units.Track.Track;
import java.awt.Color;

/**
 *
 * @author Huabin Hou
 */
public class ViewPiece {

    public Color color = null;
    public String content = "";
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int vertical;
    public boolean filtered = true;
    public boolean focused = true;
    public Track parent;
}
