/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Task;

import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public abstract interface TaskBase {

    public abstract int getTaskCount();

    public abstract void runTask(int paramInt)
            throws Exception;

    public abstract boolean isIndeterminate();

    public abstract int getMaximum();

    public abstract String getMessage();
     public abstract void setMessage(String message);
      public abstract void appendMessage(String message);

    public abstract int getValue();

    public abstract void cancelTask();

    public abstract ArrayList<String> getNames();

    public abstract void setMax(int max);
     public abstract void setValue(int value);
     public abstract boolean okToRun();
}
