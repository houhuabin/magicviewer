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
public abstract class SimpleTask implements TaskBase {

    protected int maximum;
    protected int value;
    protected boolean isOK;
    protected String message;
     protected ArrayList<String> taskNames;
     private int taskNum=1;

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleTask() {
        this.maximum = 0;
        this.value = 0;
        this.isOK = true;
    }
    public SimpleTask(ArrayList<String> taskNames) {
        this.maximum = 0;
        this.value = 0;
        this.isOK = true;
        this.taskNames=taskNames;
        this.taskNum=taskNames.size();
    }

    public boolean isOK() {
        return isOK;
    }

    public void cancelTask() {
        this.isOK = false;
    }

    public boolean isIndeterminate() {
        return false;
    }

    public int getMaximum() {
        return this.maximum;
    }

    public int getValue() {
        return this.value;
    }

    public int getTaskCount() {
        return taskNum;
    }

    public boolean okToRun() {
        return this.isOK;
    }

    public String getMessage() {
        return message;
    }

    public void setMax(int max) {
        this.maximum = max;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void appendMessage(String message) {
        this.message += message;
    }

    public ArrayList<String> getNames() {
        return taskNames;
        //  throw new UnsupportedOperationException("Not supported yet.");
    }
}
