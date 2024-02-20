/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionDialog.java
 *
 * Created on Feb 25, 2009, 7:53:15 PM
 */
package Magic.Analysis.Filter;

import utils.swing.ExpandPanel.ExpandDialogAbstract;
import utils.swing.ExpandPanel.ExpandPanelAbstract;
import Magic.Analysis.Merage.MergeFormatPanel;
import Magic.IO.PieceOutputFactory;
import Magic.WinMain.MagicFrame;
import Magic.Units.Track.Track;
import Magic.Units.File.FileFormat;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.Gui.Task.NewProgress;
import Magic.Units.Gui.Task.SimpleTask;
import Magic.Units.Gui.Task.SwingWorker;
import java.awt.BorderLayout;
import java.util.ArrayList;
import utils.swing.ExpandPanel.SettingPanel;
import java.util.Vector;
import javax.swing.JPanel;
import utils.ReportInfo;
import utils.swing.ExpandPanel.SubmitPanel;
import utils.swing.SwingUtil;

/**
 *
 * @author QIJ
 */
public class FilterAnnotationDialog extends ExpandDialogAbstract {

    private MagicFrame trackFrame;
    private MergeFormatPanel bedPanel;
    private Track mainAnnoTrack;
    private Vector<JPanel> panels;
    private String output;

    /** Creates new form OptionDialog */
    public FilterAnnotationDialog(javax.swing.JFrame parent, Track mainAnnoTrack, String output) {
        super(parent, false);
        initComponents();
        this.output = output;

        trackFrame = (MagicFrame) parent;

        this.mainAnnoTrack = mainAnnoTrack;
        initValues();

    }

    public void initValues() {
        Vector<String> names = new Vector<String>();
        panels = new Vector<JPanel>();
        Vector<Boolean> expand = new Vector<Boolean>();


        //add mian  track
        String format = FileFormat.checkFileFormatByContent(mainAnnoTrack.trackSet.filename);
        bedPanel = new MergeFormatPanel(trackFrame, mainAnnoTrack, true);
        bedPanel.add(new SubmitPanel(this), BorderLayout.SOUTH);
        bedPanel.setMaxHeight();
        names.add(format + ":" + mainAnnoTrack.trackSet.filename);
        panels.add(bedPanel);
        expand.add(true);


        SubmitPanel sub = new SubmitPanel(this);
        names.add("");
        panels.add(sub);
        expand.add(true);





        SettingPanel exp_panel = new SettingPanel(names, panels, expand, this);
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(exp_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(exp_panel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        this.pack();

        SwingUtil.setLocation(this);

        this.setVisible(true);
    }

    public void update(MagicFrame parent) {
        trackFrame = parent;
        bedPanel.update(trackFrame);
    }

    public ArrayList<String> getLoadingStepNames() {
        ArrayList<String> stepNames = new ArrayList<String>();
        stepNames.add("Filtering");
        stepNames.add("Output");
        return stepNames;
    }

    public void submit() {
        this.setVisible(false);


        SimpleTask lt = new SimpleTask(getLoadingStepNames()) {

            public void runTask(int paramInt) throws Exception {
                //   trackFrame.monitor = new StepProgress( "Merge Annotation", null, 1, getLoadingStepNames());
                //  trackFrame.monitor.next();
                for (JPanel panel : panels) {
                    ((ExpandPanelAbstract) panel).setting();
                }

                Filter fileter = new Filter();
                fileter.filterTrack(mainAnnoTrack, this);

                PieceOutputFactory of = new PieceOutputFactory();
                of.outputFilterTrack(output, mainAnnoTrack, this);
                mainAnnoTrack.trackSet.filename = output;


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