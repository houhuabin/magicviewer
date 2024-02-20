/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.File.Parameter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class ReadFile implements Serializable {
    private ArrayList<String> reads;

    public void setReads(ArrayList<String> reads) {
        this.reads = reads;
    }

    public ArrayList<String> getReads() {
        return reads;
    }
}
