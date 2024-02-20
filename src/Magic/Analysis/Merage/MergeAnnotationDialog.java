/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionDialog.java
 *
 * Created on Feb 25, 2009, 7:53:15 PM
 */
package Magic.Analysis.Merage;

import Magic.Analysis.Base.TrackRelatedWalk.MergePiece;
import utils.swing.ExpandPanel.ExpandDialogAbstract;
import utils.swing.ExpandPanel.ExpandPanelAbstract;


import Magic.IO.PieceOutputFactory;
import Magic.WinMain.MagicFrame;
import Magic.Units.Track.Track;
import Magic.Units.File.FileFormat;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.Gui.Task.SimpleTask;
import Magic.Units.Gui.Task.SwingWorker;
import java.util.ArrayList;
import utils.swing.ExpandPanel.SettingPanel;
import java.util.Vector;
import javax.swing.JPanel;
import utils.ReportInfo;
import utils.swing.SwingUtil;

/**
 *
 * @author QIJ
 */
public class MergeAnnotationDialog extends ExpandDialogAbstract {

    private MagicFrame trackFrame;
    private MergeFormatPanel bedPanel;
    public ArrayList<Track> annoTracks = new ArrayList<Track>();
    public Track mainAnnoTrack;
    // private String outPut;
    //  private Piece[] snps;
    private Vector<JPanel> panels;

    /** Creates new form OptionDialog */
    public MergeAnnotationDialog(javax.swing.JFrame parent, ArrayList<Track> annoTracks, Track mainAnnoTrack) {
        super(parent, false);
        initComponents();

        trackFrame = (MagicFrame) parent;
        this.annoTracks = annoTracks;
        this.mainAnnoTrack = mainAnnoTrack;
        initValues();

    }

    public void initValues() {
        Vector<String> names = new Vector<String>();
        panels = new Vector<JPanel>();
        Vector<Boolean> expand = new Vector<Boolean>();


        //add mian  track
        String format = FileFormat.checkFileFormatByContent(mainAnnoTrack.trackSet.filename);
        bedPanel = new MergeFormatPanel(trackFrame, mainAnnoTrack, false);
        names.add(format + ":" + mainAnnoTrack.trackSet.filename);
        panels.add(bedPanel);
        expand.add(true);

        //add anotationsTrack
        for (int i = 0; i < annoTracks.size(); i++) {
            Track annoTrack = annoTracks.get(i);
            format = FileFormat.checkFileFormatByContent(annoTrack.trackSet.filename);
            bedPanel = new MergeFormatPanel(trackFrame, annoTrack, false);
            names.add(format + ":" + annoTrack.trackSet.filename);
            panels.add(bedPanel);
            //if (i == 0) {
            // expand.add(true);
            // } else {
            expand.add(false);
            // }
        }

        //add setting track
        names.add("Main setting");
        panels.add(new MergeSettingtPanel(this));
        expand.add(true);

        SettingPanel exp_panel = new SettingPanel(names, panels, expand, this);
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(exp_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(exp_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        this.pack();

        //Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        // int x = 300;
        //  int y = 100;
        //  this.setLocation(x, y);     //为dialog设置位置
        SwingUtil.setLocation(this);

        /* Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x=screen.width-this.getPreferredSize().width-20;
        int y=100;
        this.setLocation(x, y);*/
        this.setVisible(true);
    }

    public void update(MagicFrame parent) {
        trackFrame = parent;
        bedPanel.update(trackFrame);
    }

    public ArrayList<String> getLoadingStepNames() {
        ArrayList<String> stepNames = new ArrayList<String>();
        stepNames.add("Prepare");
        stepNames.add("Calculate");
        stepNames.add("Output");
        return stepNames;
    }

    public void submit() {
        this.setVisible(false);

        SimpleTask lt = new SimpleTask(getLoadingStepNames()) {

            MergeAnnoImpl mai;
            ArrayList<MergePiece> merge;

            public void runTask(int paramInt) throws Exception {
                switch (paramInt) {
                    case 0:
                        for (JPanel panel : panels) {
                            if (!((ExpandPanelAbstract) panel).setting()) {
                                break;
                            }
                        }

                        mai = new MergeAnnoImpl(trackFrame);
                        mai.prepare(annoTracks);
                        break;
                    case 1:
                        merge = mai.mergeTracks(mainAnnoTrack, annoTracks);
                        break;
                    case 2:
                        // trackFrame.monitor.next();
                        PieceOutputFactory of = new PieceOutputFactory();
                        of.outputMerge(MergePara.outputFile, FileFormat.GFFFormat, merge, this);
                        break;

                }

                mainAnnoTrack.trackSet.filename = MergePara.outputFile;
                this.appendMessage("Output Completed...\n");

            }
        };
        NewProgress monitor = new NewProgress("Annotate variation", StringRep.START, lt);
        monitor.startTask();
        if (monitor.getReturnStatus() != ForEverStatic.RET_OK) {
            if (monitor.getReturnStatus() == ForEverStatic.RET_ERR) {
                ReportInfo.reportError(null, monitor.getException());
            }
            return;
        }



        trackFrame.reloadAnnotation();
        trackFrame.updateAnnotation();
        this.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 362, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 388, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
