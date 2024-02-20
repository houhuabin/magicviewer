/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Tree;

import java.awt.Point;

/**
 *
 * @author QIJ
 */
public class Node {
    public String id;
    public int count;
    public Node parent;
    public Node left_child;
    public Node right_child;

    public int level;
    public double depth;
    public int data_size;

    public Node(String name) {
        id = name;
        count = 1;
        parent=null;
        left_child = null;
        right_child = null;
        level=0;
        depth=0;
        data_size=0;
    }

    public Node(String name, Node left, Node right) {
        id = name;
        count = left.count+right.count;
        left_child = left;
        right_child = right;
        left.parent=this;
        right.parent=this;
        level=0;
        depth=0;
        data_size=left.data_size+right.data_size;
    }

    public String toString() {
        String s=id+": count="+count+" child=";
        if(left_child!=null) s+=left_child.id;
        else s+="NULL";
        s+=",";
        if(right_child!=null) s+=right_child.id;
        else s+="NULL";
        s+=" parent=";
        if(parent!=null) s+=parent.id;
        else s+="NULL";
        s+=" level="+level+" depth="+depth;//+" range="+line_range.x+","+line_range.y;

        return s;
    }
}
