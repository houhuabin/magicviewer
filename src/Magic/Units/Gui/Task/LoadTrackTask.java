/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Task;

import Magic.Units.Track.Track;
import java.util.ArrayList;
import utils.ForMagic;

/**
 *
 * @author lenovo
 */
public class LoadTrackTask extends SimpleTask {

    private ArrayList<String> filenames;
    private Track[] tracks;
    //  private int oldTrackNum;

    public LoadTrackTask(ArrayList<String> filenames, Track[] tracks, ArrayList<String> tasknames) {
        this.filenames = filenames;
        this.tracks = tracks;
        this.taskNames = tasknames;

    }

    public void runTask(int paramInt) throws Exception {
        //  System.out.println("paramInt:"+paramInt);
        if (!isOK) {
            return;
        }
        tracks[paramInt] = ForMagic.getTrack(filenames.get(paramInt), this);
        //  oldTrackNum++;
    }

    public int getTaskCount() {
        return filenames.size();
    }

    public void cancelTask() {
        this.isOK = false;
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i] != null) {
                tracks[i].ok2Read = false;
            }
        }
    }
}
