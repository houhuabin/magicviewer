/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Alignment;

import java.io.Serializable;

/**
 *
 * @author Huabin Hou
 */
public class MappingInfoBean implements Serializable{

   //  public  boolean HAVE_454=false;
    public  boolean USE_MIN_PERCENT=false;
    public  boolean USE_MIN_IDENTITY=true;
    public  double MIN_PERCENT=0.90;
    public  double MIN_IDENTITY=0.95;
   
    public  boolean INCLUDING_HETERO=false;

    public   int WIN_LEN=100;	//length of sliding window for clustalw
    public   int WIN_STEP=50;		//step of sliding window for clustalw
   public   int MAX_COVERAGE=100;
  //  public  boolean HAVE_SOLEXA=false;

}
