/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Tree;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author QIJ
 */
public class TreeMethods {
    public static void traversal(Node node) {
        if(node==null) return;

        //right first, left second
        traversal(node.right_child);
        traversal(node.left_child);
        
        //System.out.println(node.toString());
    }

    public static void setLevel(Node node) {
        if(node==null) return;

        //right first, left second
        setLevel(node.right_child);
        setLevel(node.left_child);

        if(node.left_child==null || node.right_child==null) node.level=0;
        else node.level=Math.max(node.left_child.level, node.right_child.level)+1;
    }

    public static int setDepth(Node node,int current_line) {
        if(node==null) return current_line;

        //right first, left second
        current_line=setDepth(node.right_child,current_line);
        current_line=setDepth(node.left_child,current_line);

        if(node.left_child==null || node.right_child==null) {
            node.depth=current_line;
            current_line++;
        } else {
            double len=node.left_child.depth-node.right_child.depth;
            node.depth=node.right_child.depth+len*node.left_child.count/node.count;
        }
        return current_line;
    }

    public static void getID2Leaves(Node node,HashMap<String,Node> id2leaf) {
        if(node==null) return;

        //right first, left second
        getID2Leaves(node.right_child,id2leaf);
        getID2Leaves(node.left_child,id2leaf);

        if(node.left_child==null || node.right_child==null) {
            id2leaf.put(node.id,node);
        }
        return;
    }

    public static Vector<Node> getPath(Node node) {
        Vector<Node> path=new Vector<Node>();
        if(node==null) return path;
        while(node.parent!=null) {
            node=node.parent;
            path.add(node);
        }
        Vector<Node> reverse=new Vector<Node>();
        for(int i=path.size()-1;i>=0;i--) reverse.add(path.elementAt(i));
        return reverse;
    }

    public static Node getLatestCommonAncestor(Vector<Node> collections) {
        Node lca=null;
        if(collections.size()==0) return lca;
        else if(collections.size()==1) return collections.firstElement();

        Vector<Node>[] pathes=new Vector[collections.size()];
        for(int i=0;i<collections.size();i++) {
            pathes[i]=getPath(collections.elementAt(i));
            ////System.out.println(i+"= "+pathes[i]);
        }

        //set lca as root
        lca=pathes[0].firstElement();
        int min=Integer.MAX_VALUE;
        for(int i=0;i<collections.size();i++) {
            if(min>pathes[i].size()) min=pathes[i].size();
        }

        for(int i=0;i<min;i++) {
            Node first=pathes[0].elementAt(i);
            ////System.out.println(i+" first= "+first);
            for(int j=1;j<collections.size();j++) {
                if(!pathes[j].elementAt(i).equals(first)) {
                    return first.parent;
                }
            }
            lca=first;
        }
        return lca;
    }
}
