/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Table;

import java.util.HashMap;

/**
 *
 * @author lenovo
 */
public class Operator {

    public enum RelationType {
        //  eq, ne, lt, gt, le,ge;

        equal, large, small, range, not_null, none {

            @Override
            public String toString() {
                return "";
            }
        }
    }

    public enum LogicType {

        AND, OR,
        none {

            @Override
            public String toString() {
                return "";
            }
        }
    }
}
