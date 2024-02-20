/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import utils.ReportInfo;

/**
 *
 * @author lenovo
 */
public class TextFile {

    public FileWriter fw;
    public BufferedWriter bw;

    public TextFile(String file) {
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public void write(String str) {
        try {
            bw.write(str);
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public void close() {
        try {
            bw.close();
            fw.close();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }
}
