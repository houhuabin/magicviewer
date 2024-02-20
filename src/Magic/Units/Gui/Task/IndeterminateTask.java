/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Gui.Task;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public  class IndeterminateTask extends SimpleTask{


    public IndeterminateTask() {
        this.maximum = 0;
        this.value = 0;
        this.isOK = true;
    }

   

    public boolean isIndeterminate() {
        return false;
    }

    public void runTask(int paramInt) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> getNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
   

   
}
