/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.File.Parameter;

import Magic.Units.Alignment.ProjectProperty;
import Magic.Units.File.FastaFile;
import Magic.Units.IO.ViewerLog;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author lenovo
 */
public class LogImplement implements Serializable{
    public int step = 0;
    public int viewMode = 0;
    public Vector<ViewerLog> viewerLogVector = new Vector<ViewerLog>();
    public FastaFile reference;
    public ReadFile reads=new ReadFile();
    public ProjectProperty projectProperty = new ProjectProperty();
    public AlignPara alignPara = new AlignPara();
    public AnnoPara annoPara = new AnnoPara();
    public GlobalSetting global = new GlobalSetting();
   
}
