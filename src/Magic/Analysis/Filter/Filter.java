/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Filter;

import Magic.Analysis.Base.TrackRelatedWalk;
import Magic.Analysis.Table.Operator;
import Magic.Analysis.Table.Operator.LogicType;
import Magic.Analysis.Merage.MergeAnnoImpl;
import Magic.Units.File.FileFormat.PieceField;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.IteratorPieces;
import Magic.Units.Track.Track;
import Magic.WinMain.MagicFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import utils.FormatCheck;

/**
 *
 * @author lenovo
 */
public class Filter {

    public Operator.RelationType relation;
    public Operator.LogicType logic;
    public String filterValue;
    public TaskBase task;

    public void filterTrack(Track snpTrack, TaskBase task) {
        this.task = task;
        //parent.monitor.reSet("Filtering " + StringRep.START, false, TrackRelatedWalk.getTrackSize(snpTrack));
        HashMap<String, FieldProperty> hm = snpTrack.pieceFieldProperty;
       filterPieces(hm,snpTrack.iteratorPieces);
    }

    public void filterPieces(HashMap<String, FieldProperty> hm, IteratorPieces pieces) {

        Iterator<Piece> iter = pieces.iterator();
        while (iter.hasNext()) {
            task.setValue(task.getValue()+1);
          //  parent.monitor.setProgress(parent.monitor.getProgress() + 1);
            Piece piece = iter.next();
            // System.out.println(piece.getDetailFieldValue("DP")+"========"+piece.geneticPiece.start);
            if (!matchPiece(hm, piece)) {
                iter.remove();
                //  System.out.println(piece.geneticPiece.start + "--------------------remove");
            }
        }
    }

    public boolean matchPiece(HashMap<String, FieldProperty> hm, Piece piece) {
        Iterator it = hm.entrySet().iterator();
        boolean result = true;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String fieldName = (String) entry.getKey();
            FieldProperty fp = (FieldProperty) entry.getValue();
            if (fp != null && fp.filter != null) {
                // System.out.println(fieldName + "--------" + fp.filter.filterValue);
                result = logicOperator(result, matchField(fieldName, fp, piece), fp.filter.logic);
                if (result == false) {
                    // System.out.println(fieldName+"=======fieldName=========="+fp.filter.logic+"---------------"+piece.geneticPiece.start);
                    return false;
                }
                // System.out.println(result+"------result--------"+matchField(fieldName, fp, piece)+"---"+fieldName);
            }

        }
        return result;
    }

    public boolean logicOperator(boolean yuan, boolean xin, LogicType logic) {
        //System.out.println(value+"---------matchProperty-----------------"+filter.filterValue+"-----------"+tpye+"-------"+filter.relation);
        switch (logic) {
            case AND: {
                return yuan && xin;
            }
            case OR: {
                return xin || yuan;
            }
            case none: {
                return true;
            }
            default:
                return false;
        }

    }

    public boolean matchField(String fieldName, FieldProperty fp, Piece piece) {
        if (fp != null) {
            Object value = piece.getDetailFieldValue(fieldName);
            if (!FieldProperty.isNotNullField(value)) {
              //  System.out.println(value + "  " + filterValue);
                return FieldProperty.nullValuePolocy();
            }

            if (matchProperty(value.toString(), fp.filter)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchProperty(String value, Filter filter) {

        String tpye = FormatCheck.getCompareType(value, filter.filterValue);

        value = value.trim();
        filter.filterValue = filter.filterValue.trim();

        //System.out.println(value+"---------matchProperty-----------------"+filter.filterValue+"-----------"+tpye+"-------"+filter.relation);
        switch (filter.relation) {
            case equal: {
                return FieldProperty.isEqual(value, filter.filterValue, tpye);
            }
            case not_null: {
                return FieldProperty.isNotNullField(value);
            }

            case large: {
                // System.out.println(FieldProperty.isLargeThan(value, filter.filterValue, tpye)+"----------islage---------");
                return FieldProperty.isLargeThan(value, filter.filterValue, tpye);
            }

            case small: {
                return FieldProperty.isSmallThan(value, filter.filterValue, tpye);
            }
            case range: {
                return FieldProperty.isInRange(value, filter.filterValue, tpye);
            }


            default:
                return false;
        }

    }
}
