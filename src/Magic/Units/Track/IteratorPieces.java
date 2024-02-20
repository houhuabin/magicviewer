/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Track;

import Magic.Analysis.Filter.FieldProperty;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Piece.Piece;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import utils.SamViewUtil;

/**
 *
 * @author Administrator
 */
public class IteratorPieces implements Iterator {

    public HashMap<String, ArrayList<Piece>> contigPieces;//所有的
    private Iterator trackIt;
    private Iterator<Piece> contigIt;

    public IteratorPieces() {
    }

    public IteratorPieces(HashMap<String, ArrayList<Piece>> contigPieces) {
        this.contigPieces = contigPieces;
        reset();
    }

    public ArrayList<Piece> getCurrentContigPieces(String contigName) {
        return contigPieces.get(contigName);
    }

    public void set(HashMap<String, ArrayList<Piece>> contigPieces) {
        this.contigPieces = contigPieces;
        reset();
    }

    public void reset() {
        trackIt = contigPieces.entrySet().iterator();
        if (trackIt != null && trackIt.hasNext()) {
            Entry entry = (Entry) trackIt.next();
            contigIt = ((ArrayList<Piece>) entry.getValue()).iterator();
        }
    }

    public HashMap<String, ArrayList<Piece>> get() {
        return contigPieces;
    }

    public boolean hasNext() {
        if (contigIt.hasNext()) {
            return true;
        } else {
            while (trackIt.hasNext()) {
                Entry entry = (Entry) trackIt.next();
                contigIt = ((ArrayList<Piece>) entry.getValue()).iterator();
                if (contigIt.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int size() {
        int i = 0;
        Iterator it2 = contigPieces.entrySet().iterator();
        while (it2.hasNext()) {
            Entry entry = (Entry) it2.next();
            ArrayList<Piece> cdss = (ArrayList<Piece>) entry.getValue();
            i += cdss.size();
        }
        return i;
    }

    public Iterator<Piece> iterator() {
        
        trackIt = contigPieces.entrySet().iterator();
        Entry entry = (Entry) trackIt.next();
        contigIt = ((ArrayList<Piece>) entry.getValue()).iterator();
        return this;
    }

  

   

    public Piece next() {
        if (contigIt.hasNext()) {
            return contigIt.next();
        } else {
            while (trackIt.hasNext()) {
                Entry entry = (Entry) trackIt.next();
                contigIt = ((ArrayList<Piece>) entry.getValue()).iterator();
                if (contigIt.hasNext()) {
                    return contigIt.next();
                }
            }
        }
        return null;
    }

    public void remove() {
        contigIt.remove();
        if (!contigIt.hasNext()) {
            if (trackIt.hasNext()) {
                Entry entry = (Entry) trackIt.next();
                contigIt = ((ArrayList<Piece>) entry.getValue()).iterator();
            }
        }
    }

   
}
