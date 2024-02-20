/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.File.Parameter;

import Magic.Units.File.FastaFile;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Huabin Hou
 */
public class GlobalSetting implements Serializable {
    //把reference 分成n个refBeanLen长度的小reference,每个代表一个refrenceBean
  //  public FastaFile reference;
    public int SimpleMode = 0;
    public int MethyMode = 1;
    public int currentWinStart = 0;//readBis 初始 windowslen
   // public int coverage = 10;
    public int covToMaxHeight = 40;
  
    public int alignmentWindowLen = 1200;
    public int adjustWindowSize=100;
    public int readsInterver = 8;//read 间距

    //this variant allow the view size larger than genome size
    
      public int columnDisplayHeight = 50;    
    public int Qualty_SCORE_ADD = 64;
   
    /** A return status code - returned if OK button has been pressed */
     
}
