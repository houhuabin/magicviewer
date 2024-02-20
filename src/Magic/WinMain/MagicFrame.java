/*
 * 
 *
 * 
 */
package Magic.WinMain;

import Magic.Units.Gui.PopTip;
import Magic.Analysis.Filter.FilterAnnotationMain;
import Magic.Analysis.Genotyping.GenotyperJDialog;
import Magic.Analysis.Merage.MergeAnnotationMain;
import Magic.Analysis.Primer.PrimerDesignDialog;
import Magic.Analysis.Statistic.PaintChart;
import Magic.IO.DataRep;
import Magic.Options.Annotation.*;
import Magic.Units.Gui.Task.SwingWorker;
import Magic.Units.IO.SeqData;
import Magic.Units.Gui.Icons;
import Magic.Units.IO.ViewerLog;
import Magic.IO.ReadData;
import Magic.IO.WriteData;
import Magic.Options.Alignment.AlignmentOptionDialog;
import Magic.Options.Main.MainOptionDialog;
import Magic.Search.AnnoListDialog;
import Magic.Units.File.Parameter.StringRep;

import java.awt.*;

import java.awt.event.*;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;


import Magic.Units.File.Parameter.Log;
import Magic.Units.Color.BaseImage;
import Magic.Units.Track.Track;
import Magic.Units.File.FileFormat;
import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.LogImplement;
import Magic.Units.Gui.Windows.AnnoPartCenter;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.IO.SeqPanelData;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.Units.Piece.Piece;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import utils.FormatCheck;
import utils.ReportInfo;
import utils.SamViewUtil;
import utils.SystemUtil;
import utils.ForMagic;
import utils.swing.SwingUtil;

/**
 *
 * @author Huabin Hou
 */
public class MagicFrame extends javax.swing.JFrame {

    public volatile int PROGRESS = 0;
    public AnnoAssemblyPanel annoAssemblyPanel;
    public AlignAssemblyPanel alignAssemblyPanel;
    public DataRep dataRep;
    public SeqData seqData;
    public int HORIZ_UNIT = 100;
    public int ZOOM_STEP = 1;
    //public String LOG_PATH = System.getProperty("user.dir");
    public String GENOME_PATH = System.getProperty("user.dir");
    //public String CURRENT_PATH = System.getProperty("user.dir");
    public AnnotationOptionDialog annotation_option_dialog;
    public AlignmentOptionDialog alignment_option_dialog;
    public MainOptionDialog mainOptionDialog;
    public AnnoListDialog annoListDialog;
    public Vector<ViewerLog> viewerLogs = new Vector<ViewerLog>();
    public int current_contig = -1;
    public ViewerLog currentLog = null;
    public int gotoPosion = 1;
    public int alignmentPosion = 1;
    public int annoPosionStart;
    public int annoPosionEnd;
    public int annoEndPosionExtra = 1000;//for insurance
    public PopTip poptip = new PopTip();
    // private boolean isUpdateingPosion = false;

    public void setMenuItemDisable() {
        if (!checkFilterEnable()) {
            jMenuItemFilter.setEnabled(false);
            jMenuItemPrimer.setEnabled(false);

        } else {
            jMenuItemFilter.setEnabled(true);
            jMenuItemPrimer.setEnabled(true);

        }
        if (!checkAnnotationEnable()) {
            jMenuItemAnnotation.setEnabled(false);

        } else {
            jMenuItemAnnotation.setEnabled(true);

        }

        if (!checkStatEnable()) {
            jMenuItemStat.setEnabled(false);
        } else {
            jMenuItemStat.setEnabled(true);

        }

        if (seqData == null || seqData.seqPanelDatas.size() == 0) {
            jMenuItemGenotype.setEnabled(false);
            jAlignSet.setEnabled(false);

        } else {
            jMenuItemGenotype.setEnabled(true);
            jAlignSet.setEnabled(true);

        }


        if (dataRep == null || dataRep.tracks == null) {
            jMenuItemImport.setEnabled(false);
            jAnnoParaSet.setEnabled(false);
            jAnnoTrackSet.setEnabled(false);
        } else {
            jAnnoParaSet.setEnabled(true);
            jAnnoTrackSet.setEnabled(true);
            jMenuItemImport.setEnabled(true);
        }
    }

    public boolean checkStatEnable() {
        if (dataRep == null || dataRep.trackNum < 1) {
            return false;
        }
        boolean selected = false;
        for (int i = 0; i < dataRep.trackNum; i++) {
            if (dataRep.tracks[i].trackSet.name.equals(StringRep.SNPS)) {
                Piece piece = dataRep.tracks[i].iteratorPieces.iterator().next();
                if (((SNPPiece) piece.geneticPiece).snpType != null) {
                    return true;
                }
            }
        }
        return selected;
    }

    public MagicFrame() {

        initComponents();
        annoAssemblyPanel = new AnnoAssemblyPanel(this);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(annoAssemblyPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(annoAssemblyPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));



        // assembly.add(ConsolePane.getInstance(), BorderLayout.SOUTH);
        alignAssemblyPanel = new AlignAssemblyPanel(this);
        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(alignAssemblyPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(alignAssemblyPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));




        // setSize(LogBean.getInstance().global.screenLen, LogBean.getInstance().global.screenHeight);
        settingPara();
        SwingUtil.setLocation(this);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                isExit(false);
            }
        });
        Toolkit.getDefaultToolkit().addAWTEventListener(GlobalAWTEventListener.getInstance(), AWTEvent.KEY_EVENT_MASK);
    }

    public int loadLog(LogImplement log) {
        if (log == null) {
            return ForEverStatic.RET_ERR;
        }

        viewerLogs = log.viewerLogVector;
        setContig(0);

        return loadAll(currentLog);

    }

    public void setjComboBoxContigName() {
        java.util.List<String> cotigNames = SamViewUtil.getReferenceNames(Log.instance().reference.refile);
        jComboBoxContigName.removeAllItems();
        for (String cotigName : cotigNames) {
            jComboBoxContigName.addItem(cotigName);
            if (cotigName.equals(currentLog.referContigName)) {
                //  System.out.println(currentLog.referContigName+"-------------------set select item--------------------");
                jComboBoxContigName.setSelectedItem(cotigName);
            }
        }
    }

    public void settingPara() {

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenLen = Toolkit.getDefaultToolkit().getScreenSize().width;
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());

        //去掉任务栏的高度
        Rectangle desktopBounds = new Rectangle(
                screenInsets.left, screenInsets.top,
                screenLen - screenInsets.left - screenInsets.right,
                screenHeight - screenInsets.top - screenInsets.bottom);

        this.setLocation(0, 0);

        ForEverStatic.desktopBoundsHeight = desktopBounds.height;
        ForEverStatic.desktopBoundsWidth = desktopBounds.width;
        ForEverStatic.screenHeight = screenHeight;
        ForEverStatic.screenLen = screenLen;
        this.setSize(desktopBounds.width, desktopBounds.height);
        setAnnoEndPosion();
    }

    public void setContig(int index) {
        current_contig = index;
        currentLog = viewerLogs.elementAt(index);
        if (!currentLog.ok) {
            return;
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        vert1 = new javax.swing.JScrollBar();
        horiz1 = new javax.swing.JScrollBar();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        horiz2 = new javax.swing.JScrollBar();
        jToolBar3 = new javax.swing.JToolBar();
        open_button = new javax.swing.JButton();
        open_button1 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        zoomin_button2 = new javax.swing.JButton();
        zoomout_button2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        zoomin_button1 = new javax.swing.JButton();
        zoomout_button1 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxContigName = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCoordinator = new javax.swing.JTextField();
        goto_button1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();
        previous = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        next = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        previous1 = new javax.swing.JButton();
        next2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jLabel9 = new javax.swing.JLabel();
        next1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemBSMAP = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemImport = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jCheckBoxMenuItemAnno = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemPiple = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemRef = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemAlign = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemGenotype = new javax.swing.JMenuItem();
        jMenuItemFilter = new javax.swing.JMenuItem();
        jMenuItemAnnotation = new javax.swing.JMenuItem();
        jMenuItemStat = new javax.swing.JMenuItem();
        jMenuItemPrimer = new javax.swing.JMenuItem();
        Settings = new javax.swing.JMenu();
        jAnnoParaSet = new javax.swing.JMenuItem();
        jAnnoTrackSet = new javax.swing.JMenuItem();
        jAlignSet = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemMagicHome = new javax.swing.JMenuItem();
        jMenuItemSamtoolsHome = new javax.swing.JMenuItem();
        jMenuItemPicardHome = new javax.swing.JMenuItem();
        jMenuItemGATKHome = new javax.swing.JMenuItem();

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("MagicViewer: Integrated Solution for Next-generation Sequencing Data Visualization and Genetic Variation Detection and Annotation");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jSplitPane1.setOneTouchExpandable(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setFont(new java.awt.Font("Times New Roman", 0, 12));
        jPanel4.setPreferredSize(new java.awt.Dimension(900, 800));
        jPanel4.setLayout(new java.awt.BorderLayout());

        vert1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        vert1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                vert1AdjustmentValueChanged(evt);
            }
        });

        horiz1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        horiz1.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        horiz1.setPreferredSize(new java.awt.Dimension(48, 15));
        horiz1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                horiz1MouseClicked(evt);
            }
        });
        horiz1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                horiz1AdjustmentValueChanged(evt);
            }
        });
        horiz1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                horiz1MouseDragged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel8Layout.createSequentialGroup()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(vert1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(horiz1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1327, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(vert1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(horiz1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setTopComponent(jPanel8);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel3ComponentResized(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 1327, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 255, Short.MAX_VALUE)
        );

        horiz2.setFont(new java.awt.Font("Times New Roman", 0, 12));
        horiz2.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        horiz2.setPreferredSize(new java.awt.Dimension(48, 15));
        horiz2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                horiz2AdjustmentValueChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2Layout.createSequentialGroup()
                .add(horiz2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1317, Short.MAX_VALUE)
                .add(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(horiz2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar3.setRollover(true);
        jToolBar3.setMaximumSize(new java.awt.Dimension(436, 32769));

        open_button.setFont(new java.awt.Font("Times New Roman", 0, 12));
        open_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/open_small.png"))); // NOI18N
        open_button.setMnemonic('o');
        open_button.setToolTipText("Load workspace");
        open_button.setFocusable(false);
        open_button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        open_button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        open_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_buttonActionPerformed(evt);
            }
        });
        jToolBar3.add(open_button);
        open_button.getAccessibleContext().setAccessibleName("open new file");
        open_button.getAccessibleContext().setAccessibleDescription("open new file");

        open_button1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        open_button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/import.png"))); // NOI18N
        open_button1.setMnemonic('o');
        open_button1.setToolTipText("Import Annotation");
        open_button1.setFocusable(false);
        open_button1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        open_button1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        open_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_button1ActionPerformed(evt);
            }
        });
        jToolBar3.add(open_button1);

        jSeparator7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator7.setAutoscrolls(true);
        jSeparator7.setMaximumSize(new java.awt.Dimension(2, 30));
        jSeparator7.setMinimumSize(new java.awt.Dimension(2, 30));
        jSeparator7.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator7.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator7);

        zoomin_button2.setFont(new java.awt.Font("Times New Roman", 0, 12));
        zoomin_button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/green_zoom_in.png"))); // NOI18N
        zoomin_button2.setMnemonic('g');
        zoomin_button2.setToolTipText("Alignment Zoom In");
        zoomin_button2.setFocusable(false);
        zoomin_button2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomin_button2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomin_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomin_button2ActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomin_button2);

        zoomout_button2.setFont(new java.awt.Font("Times New Roman", 0, 12));
        zoomout_button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/green_zoom_out.png"))); // NOI18N
        zoomout_button2.setMnemonic('g');
        zoomout_button2.setToolTipText("Alignment Zoom Out");
        zoomout_button2.setFocusable(false);
        zoomout_button2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomout_button2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomout_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomout_button2ActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomout_button2);

        jSeparator4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator4.setOpaque(true);
        jSeparator4.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator4.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator4);

        zoomin_button1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        zoomin_button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/zoom_in.png"))); // NOI18N
        zoomin_button1.setMnemonic('g');
        zoomin_button1.setToolTipText("Annotation Zoom In");
        zoomin_button1.setFocusable(false);
        zoomin_button1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomin_button1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomin_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomin_button1ActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomin_button1);

        zoomout_button1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        zoomout_button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/zoom_out.png"))); // NOI18N
        zoomout_button1.setMnemonic('g');
        zoomout_button1.setToolTipText("Annotation Zoom Out");
        zoomout_button1.setFocusable(false);
        zoomout_button1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomout_button1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomout_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomout_button1ActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomout_button1);

        jSeparator5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator5.setAutoscrolls(true);
        jSeparator5.setMaximumSize(new java.awt.Dimension(2, 30));
        jSeparator5.setMinimumSize(new java.awt.Dimension(2, 30));
        jSeparator5.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator5.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator5);

        jLabel1.setText(" Current Contig ");
        jToolBar3.add(jLabel1);

        jComboBoxContigName.setMaximumSize(new java.awt.Dimension(100, 25));
        jComboBoxContigName.setMinimumSize(new java.awt.Dimension(80, 25));
        jComboBoxContigName.setPreferredSize(new java.awt.Dimension(80, 25));
        jComboBoxContigName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxContigNameItemStateChanged(evt);
            }
        });
        jComboBoxContigName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxContigNameActionPerformed(evt);
            }
        });
        jToolBar3.add(jComboBoxContigName);

        jLabel3.setText("  Coordinate ");
        jToolBar3.add(jLabel3);

        jTextFieldCoordinator.setMaximumSize(new java.awt.Dimension(150, 25));
        jTextFieldCoordinator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCoordinatorActionPerformed(evt);
            }
        });
        jTextFieldCoordinator.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCoordinatorFocusGained(evt);
            }
        });
        jTextFieldCoordinator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCoordinatorKeyPressed(evt);
            }
        });
        jToolBar3.add(jTextFieldCoordinator);

        goto_button1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        goto_button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goto.png"))); // NOI18N
        goto_button1.setMnemonic('g');
        goto_button1.setToolTipText("Go To");
        goto_button1.setFocusable(false);
        goto_button1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        goto_button1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        goto_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goto_button1ActionPerformed(evt);
            }
        });
        jToolBar3.add(goto_button1);

        jLabel2.setText("   ");
        jToolBar3.add(jLabel2);

        jSeparator6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator6.setOpaque(true);
        jSeparator6.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator6.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator6);

        jLabel4.setText("   ");
        jToolBar3.add(jLabel4);

        previous.setFont(new java.awt.Font("Times New Roman", 0, 12));
        previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left.png"))); // NOI18N
        previous.setMnemonic('g');
        previous.setToolTipText("Previous Page(Hot Key: left)");
        previous.setFocusable(false);
        previous.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previous.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previous.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previousMousePressed(evt);
            }
        });
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });
        jToolBar3.add(previous);

        jLabel5.setMaximumSize(new java.awt.Dimension(8, 15));
        jLabel5.setMinimumSize(new java.awt.Dimension(8, 15));
        jToolBar3.add(jLabel5);

        next.setFont(new java.awt.Font("Times New Roman", 0, 12));
        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/right.png"))); // NOI18N
        next.setMnemonic('g');
        next.setToolTipText("Next  Page(Hot Key: right)");
        next.setFocusable(false);
        next.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        next.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nextMousePressed(evt);
            }
        });
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        jToolBar3.add(next);

        jLabel6.setText("   ");
        jToolBar3.add(jLabel6);

        jSeparator8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator8.setOpaque(true);
        jSeparator8.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator8.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator8);

        jLabel7.setText("   ");
        jToolBar3.add(jLabel7);

        previous1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        previous1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/leftd.png"))); // NOI18N
        previous1.setMnemonic('g');
        previous1.setToolTipText("Previous Window");
        previous1.setFocusable(false);
        previous1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previous1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previous1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previous1MousePressed(evt);
            }
        });
        previous1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previous1ActionPerformed(evt);
            }
        });
        jToolBar3.add(previous1);

        next2.setFont(new java.awt.Font("Times New Roman", 0, 12));
        next2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rightd.png"))); // NOI18N
        next2.setMnemonic('g');
        next2.setToolTipText("Next Window");
        next2.setFocusable(false);
        next2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        next2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        next2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                next2MousePressed(evt);
            }
        });
        next2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next2ActionPerformed(evt);
            }
        });
        jToolBar3.add(next2);

        jLabel10.setText("   ");
        jToolBar3.add(jLabel10);

        jSeparator9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator9.setOpaque(true);
        jSeparator9.setPreferredSize(new java.awt.Dimension(2, 30));
        jSeparator9.setSeparatorSize(new java.awt.Dimension(2, 30));
        jToolBar3.add(jSeparator9);

        jLabel9.setText("   ");
        jToolBar3.add(jLabel9);

        next1.setFont(new java.awt.Font("Times New Roman", 0, 12));
        next1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Chat.png"))); // NOI18N
        next1.setMnemonic('g');
        next1.setToolTipText("Annotation List ");
        next1.setFocusable(false);
        next1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        next1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        next1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                next1MousePressed(evt);
            }
        });
        next1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next1ActionPerformed(evt);
            }
        });
        jToolBar3.add(next1);

        jLabel8.setText("   ");
        jToolBar3.add(jLabel8);

        jPanel1.add(jToolBar3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItemBSMAP.setText("New Project");
        jMenuItemBSMAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBSMAPActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemBSMAP);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setText("Load");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOpen);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Save");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Save As");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator2);

        jMenuItemImport.setText("Import");
        jMenuItemImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemImport);

        jMenuItem7.setText("Export Annotation");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem16.setText("Export Alignment");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);
        jMenu1.add(jSeparator3);

        jMenuItem12.setText("Close");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem12);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Quit");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar1.add(jMenu1);

        jMenuView.setText("View ");

        jCheckBoxMenuItemAnno.setSelected(true);
        jCheckBoxMenuItemAnno.setText("Show Annotation");
        jCheckBoxMenuItemAnno.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxMenuItemAnnoStateChanged(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemAnno);

        jCheckBoxMenuItemPiple.setSelected(true);
        jCheckBoxMenuItemPiple.setText("Show Coverage");
        jCheckBoxMenuItemPiple.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxMenuItemPipleStateChanged(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemPiple);

        jCheckBoxMenuItemRef.setSelected(true);
        jCheckBoxMenuItemRef.setText("Show Reference");
        jCheckBoxMenuItemRef.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxMenuItemRefStateChanged(evt);
            }
        });
        jCheckBoxMenuItemRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemRefActionPerformed(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemRef);

        jCheckBoxMenuItemAlign.setSelected(true);
        jCheckBoxMenuItemAlign.setText("Show Alignment");
        jCheckBoxMenuItemAlign.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxMenuItemAlignStateChanged(evt);
            }
        });
        jMenuView.add(jCheckBoxMenuItemAlign);

        jMenuBar1.add(jMenuView);

        jMenu4.setText("Analysis ");

        jMenuItemGenotype.setText("Genotype Calling");
        jMenuItemGenotype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenotypeActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemGenotype);

        jMenuItemFilter.setText("Variation Filter");
        jMenuItemFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFilterActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemFilter);

        jMenuItemAnnotation.setText("Variation Annotation");
        jMenuItemAnnotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAnnotationActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemAnnotation);

        jMenuItemStat.setText("Statistics");
        jMenuItemStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStatActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemStat);

        jMenuItemPrimer.setText("Primer Design ");
        jMenuItemPrimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPrimerActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemPrimer);

        jMenuBar1.add(jMenu4);

        Settings.setText("Settings");

        jAnnoParaSet.setText("Annotation Parameters");
        jAnnoParaSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnnoParaSetActionPerformed(evt);
            }
        });
        Settings.add(jAnnoParaSet);

        jAnnoTrackSet.setText("Annotation Tracks");
        jAnnoTrackSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnnoTrackSetActionPerformed(evt);
            }
        });
        Settings.add(jAnnoTrackSet);

        jAlignSet.setText("Alignment Parameters");
        jAlignSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAlignSetActionPerformed(evt);
            }
        });
        Settings.add(jAlignSet);

        jMenuItem2.setText("Project Parameters");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        Settings.add(jMenuItem2);

        jMenuBar1.add(Settings);

        jMenu2.setText("Help");

        jMenuItemMagicHome.setText("MagicViewer Home");
        jMenuItemMagicHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMagicHomeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemMagicHome);

        jMenuItemSamtoolsHome.setText("Samtools Home");
        jMenuItemSamtoolsHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSamtoolsHomeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemSamtoolsHome);

        jMenuItemPicardHome.setText("Picard Home");
        jMenuItemPicardHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPicardHomeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemPicardHome);

        jMenuItemGATKHome.setText("GATK Home");
        jMenuItemGATKHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGATKHomeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemGATKHome);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
// TODO add your handling code here:
    if (annoAssemblyPanel == null || alignAssemblyPanel.isNull()) {
        return;
    }
    setBarsAmount();
}//GEN-LAST:event_formComponentShown

private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
// TODO add your handling code here:
    if (annoAssemblyPanel == null || alignAssemblyPanel == null || alignAssemblyPanel.isNull()) {
        return;
    }
    setBarsAmount();
    // System.out.println("---------------assembly getWidth---------------------"+assemblyPanel.getWidth());
}//GEN-LAST:event_formComponentResized

    public void setContigByName(String refer_name) {
        if (refer_name == null) {
            return;
        }
        for (int i = 0; i < viewerLogs.size(); i++) {
            //  System.out.println(viewerLogs.elementAt(i).referContigName+"----------referContigName-----------------");
            if (viewerLogs.elementAt(i).referContigName.equals(refer_name)) {

                setContig(i);
            }
        }
        //System.out.println(refer_name + "=================refer_name ==========");
        Log.instance().reference.changeContig(refer_name);
    }

private void open_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_buttonActionPerformed
    openLog();
}//GEN-LAST:event_open_buttonActionPerformed

private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
    openLog();
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemOpenActionPerformed

private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
    isExit(false);
    // TODO add your handling code here:
}//GEN-LAST:event_jMenu1ActionPerformed

private void jMenuItemGenotypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenotypeActionPerformed
    new GenotyperJDialog(this);

}//GEN-LAST:event_jMenuItemGenotypeActionPerformed

private void jMenuItemBSMAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBSMAPActionPerformed

    new NewBAMJDialog(this);

}//GEN-LAST:event_jMenuItemBSMAPActionPerformed

private void goto_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goto_button1ActionPerformed
    // TODO add your handling code here:

    if (dataRep != null) {
        if (!FormatCheck.isPositiveInteger(jTextFieldCoordinator.getText().trim())) {
            return;
        }
        int goto_site = Integer.valueOf(jTextFieldCoordinator.getText().trim()).intValue();
        gotoSiteAndValidate(goto_site, true);
    }
}//GEN-LAST:event_goto_button1ActionPerformed

private void zoomin_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomin_button1ActionPerformed
    annoAssemblyPanel.zoomIn();   // TODO add your handling code here:
}//GEN-LAST:event_zoomin_button1ActionPerformed

private void zoomout_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomout_button1ActionPerformed
    annoAssemblyPanel.zoomOut();  // TODO add your handling code here:
}//GEN-LAST:event_zoomout_button1ActionPerformed

private void jComboBoxContigNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxContigNameActionPerformed
}//GEN-LAST:event_jComboBoxContigNameActionPerformed

private void jCheckBoxMenuItemAnnoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemAnnoStateChanged
    if (jCheckBoxMenuItemAnno.isSelected()) {
        annoAssemblyPanel.setVisible(true);

    } else {
        annoAssemblyPanel.setVisible(false);
    }

    // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxMenuItemAnnoStateChanged

private void jCheckBoxMenuItemAlignStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemAlignStateChanged

    if (jCheckBoxMenuItemAlign.isSelected()) {
        alignAssemblyPanel.align_panel.setVisible(true);

    } else {
        alignAssemblyPanel.align_panel.setVisible(false);
    }
    // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxMenuItemAlignStateChanged

private void jCheckBoxMenuItemPipleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemPipleStateChanged
    if (jCheckBoxMenuItemPiple.isSelected()) {
        alignAssemblyPanel.pipleup_panel.setVisible(true);
    } else {
        alignAssemblyPanel.pipleup_panel.setVisible(false);
    }   // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxMenuItemPipleStateChanged

private void jComboBoxContigNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxContigNameItemStateChanged

    if (evt.getStateChange() == ItemEvent.SELECTED) {
        if (!currentLog.referContigName.equals((String) jComboBoxContigName.getSelectedItem()) && jComboBoxContigName.getItemCount() > 0) {
            System.out.println(currentLog.referContigName + "=====================" + jComboBoxContigName.getSelectedItem());

            final SwingWorker worker = new SwingWorker() {

                public Object construct() {
                    setContigByName((String) jComboBoxContigName.getSelectedItem());
                    reload();
                    return null;
                }

                @Override
                public void finished() {
                    reflash();
                    //  annoAssemblyPanel.setMaxWidth(Log.instance().reference.currentContigLen >> Log.instance().annoPara.ZOOM);
                    //      System.out.println(Log.instance().reference.currentContigName + "-----------annotationPanel.MAX_WIDTH----------" + annotationPanel.MAX_WIDTH);
                    // setBarsAmount();
                    // setBarsValue();
                }
            };        
            worker.start();
        }
    }
}//GEN-LAST:event_jComboBoxContigNameItemStateChanged

private void jTextFieldCoordinatorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCoordinatorFocusGained

    // TODO add your handling code here:
    jTextFieldCoordinator.selectAll();
}//GEN-LAST:event_jTextFieldCoordinatorFocusGained

private void jTextFieldCoordinatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCoordinatorActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldCoordinatorActionPerformed

private void jTextFieldCoordinatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCoordinatorKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        if (dataRep != null) {

            if (!FormatCheck.isPositiveInteger(jTextFieldCoordinator.getText().trim())) {
                return;
            }
            int goto_site = Integer.valueOf(jTextFieldCoordinator.getText().trim()).intValue();

            gotoSiteAndValidate(goto_site, true);
        }
    }

    // TODO add your handling code here:
}//GEN-LAST:event_jTextFieldCoordinatorKeyPressed

private void jMenuItemGATKHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGATKHomeActionPerformed

    SwingUtil.openURL("http://www.broadinstitute.org/gsa/wiki/index.php/The_Genome_Analysis_Toolkit");

    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemGATKHomeActionPerformed

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
    if (dataRep == null) {
        return;
    }
    dataRep.dataTrunkToLog();
    saveLog();
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem3ActionPerformed

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
    String filename = SwingUtil.getSaveFileByChooser("Save Project", new String[]{"log"}, this);
    if (filename == null) {
        return;
    }
    Log.instance().projectProperty.log_file = filename;
    saveLog();
}//GEN-LAST:event_jMenuItem4ActionPerformed

private void jMenuItemMagicHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMagicHomeActionPerformed
    SwingUtil.openURL("http://bioinformatics.zj.cn/magicviewer/");    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemMagicHomeActionPerformed

private void jMenuItemPicardHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPicardHomeActionPerformed
    SwingUtil.openURL("http://picard.sourceforge.net/index.shtml/");
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemPicardHomeActionPerformed

private void jMenuItemSamtoolsHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSamtoolsHomeActionPerformed
    SwingUtil.openURL("http://samtools.sourceforge.net/");    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemSamtoolsHomeActionPerformed

private void jAnnoParaSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnnoParaSetActionPerformed

    annotation_option_dialog = new AnnotationOptionDialog(this, false, this);
    annotation_option_dialog.setVisible(true);
    annotation_option_dialog.requestFocus();
    annotation_option_dialog.toFront();    // TODO add your handling code here:
}//GEN-LAST:event_jAnnoParaSetActionPerformed

private void open_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_button1ActionPerformed
    importAnnotation();    // TODO add your handling code here:
}//GEN-LAST:event_open_button1ActionPerformed

private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed

    System.exit(0);
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem9ActionPerformed

private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
    exportImage(new MagicPanel[]{annoAssemblyPanel.annotationPanel});    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem7ActionPerformed

private void jMenuItemImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportActionPerformed
    importAnnotation();

    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemImportActionPerformed

private void jAlignSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAlignSetActionPerformed

    if (alignment_option_dialog == null) {
        alignment_option_dialog = new AlignmentOptionDialog(this);
        alignment_option_dialog.setVisible(true);
        return;
    }
    if (!alignment_option_dialog.isVisible()) {
        alignment_option_dialog.setVisible(true);
    }
    if (!alignment_option_dialog.isFocused()) {
        alignment_option_dialog.requestFocus();
    }
    alignment_option_dialog.toFront();    // TODO add your handling code here:
    // TODO add your handling code here:
}//GEN-LAST:event_jAlignSetActionPerformed

    public boolean checkAnnotationEnable() {
        if (!checkFilterEnable()) {
            return false;
        }
        if (dataRep == null || dataRep.tracks.length < 2) {
            return false;
        }
        return true;
    }

private void jMenuItemAnnotationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAnnotationActionPerformed

    ArrayList<Track> options = new ArrayList<Track>();
    Track selectedOption = null;
    boolean selected = false;
    for (int i = 0; i < dataRep.trackNum; i++) {
        if (dataRep.tracks[i].trackSet.name.equals(StringRep.SNPS) && selected == false) {
            selectedOption = dataRep.tracks[i];
            selected = true;
        } else {
            options.add(dataRep.tracks[i]);
        }

    }
    new MergeAnnotationMain(this, options, selectedOption);
}//GEN-LAST:event_jMenuItemAnnotationActionPerformed

private void zoomin_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomin_button2ActionPerformed

    int fontSize = Log.instance().alignPara.DNA_FONT.getSize();
    fontSize++;
    setAligmentFontSize(fontSize);
}//GEN-LAST:event_zoomin_button2ActionPerformed

    public void assemblyPanelRepaint() {

        if (seqData != null && seqData.reference != null) {
            final SwingWorker worker = new SwingWorker() {

                public Object construct() {
                    alignAssemblyPanel.reflash();
                    return null;
                }

                @Override
                public void finished() {
                    alignAssemblyPanel.align_panel.validate();
                    setBarsAmount();
                    // horiz2.setValue(assemblyPanel.align_panel.);
                }
            };
        
            worker.start();

        }    // TODO add your handling code here:
    }

private void zoomout_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomout_button2ActionPerformed

    int fontSize = Log.instance().alignPara.DNA_FONT.getSize();
    if (fontSize > 1) {
        fontSize--;
        setAligmentFontSize(fontSize);
    }
}//GEN-LAST:event_zoomout_button2ActionPerformed

private void jMenuItemPrimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPrimerActionPerformed

    ArrayList<Track> options = new ArrayList<Track>();
    Track selectedOption = null;
    boolean selected = false;
    for (int i = 0; i < dataRep.trackNum; i++) {
        //   //System.out.println(dtrunk.tracks[i].trackSet.name+"--------------dtrunk.tracks[i].trackSet.name----------------------");
        if (dataRep.tracks[i].trackSet.name.equals(StringRep.SNPS) && selected == false) {
            selectedOption = dataRep.tracks[i];
            selected = true;
        }
    }
    new PrimerDesignDialog(this, selectedOption);    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemPrimerActionPerformed

    public void setPosionsByAnnoPosion() {
        int gotoPosionTemp = Math.min(Log.instance().reference.currentContigLen, annoPosionStart);
        gotoPosion = gotoPosionTemp - ((horiz1.getWidth() / 2) << Log.instance().annoPara.ZOOM);
        alignmentPosion = annoPosionStart + (annoAssemblyPanel.axiPanel.slider.posionX << Log.instance().annoPara.ZOOM);
        // System.out.println(annoAssemblyPanel.axiPanel.slider.posionX+"-------------------annoAssemblyPanel.axiPanel.slider.posionX-----------------");
        setAnnoEndPosion();
        //  gotoPosion = annoPosion + ((horiz2.getWidth() / 2) << Log.instance().annoPara.ZOOM);
    }

    public void setAnnoEndPosion() {
        annoPosionEnd = annoPosionStart + (horiz1.getWidth() << Log.instance().annoPara.ZOOM) + annoEndPosionExtra;
    }

    public void setPosionsByGotoPosion(boolean ifSetVer1Value) {
        setAlnPosionByGoto();
        if ((Log.instance().reference.currentContigLen - gotoPosion) >> Log.instance().annoPara.ZOOM < horiz1.getWidth() / 2) {
            annoPosionStart = Math.max(0, Log.instance().reference.currentContigLen - ((horiz1.getWidth()) << Log.instance().annoPara.ZOOM));
        } else {
            annoPosionStart = Math.max(0, gotoPosion - ((horiz1.getWidth() / 2) << Log.instance().annoPara.ZOOM));
        }
        if (ifSetVer1Value) {
            ForMagic.setVer1Value(annoPosionStart >> Log.instance().annoPara.ZOOM);
        }
        setSliderPosionByGoto();
        setAnnoEndPosion();
    }

    public void setAlnPosionByGoto() {
        // System.out.println((Log.instance().reference.currentContigLen - gotoPosion)+"   "+gotoPosion);
        if ((Log.instance().reference.currentContigLen - gotoPosion) >> Log.instance().annoPara.ZOOM < annoAssemblyPanel.axiPanel.slider.totalWidth) {
            alignmentPosion = Log.instance().reference.currentContigLen - (annoAssemblyPanel.axiPanel.slider.totalWidth << Log.instance().annoPara.ZOOM);
        } else {
            alignmentPosion = Math.max(0, gotoPosion - (Log.instance().global.alignmentWindowLen / 2));
        }
    }

    public void setSliderPosionByGoto() {
        //  System.out.println(annoPosion+"============================="+alignmentPosion);

        if (annoAssemblyPanel.axiPanel.slider == null) {
            return;
        }

        annoAssemblyPanel.axiPanel.slider.posionX = (alignmentPosion - annoPosionStart) >> Log.instance().annoPara.ZOOM;
        // System.out.println(alignmentPosion + "  -------------------      "+ annoPosionStart);
        horiz2.setValue((gotoPosion - alignmentPosion) * Log.instance().alignPara.baseWidth - horiz2.getWidth() / 2);
    }

    public void setSliderInnerPosion() {
        //  System.out.println(annoPosion+"============================="+alignmentPosion);

        if (annoAssemblyPanel.axiPanel.slider == null) {
            return;
        }
        annoAssemblyPanel.axiPanel.slider.innerPosionX = horiz2.getValue() >> Log.instance().annoPara.ZOOM;
    }

    /*   public void setPosionsByGoto(int gotoPo) {
    alignmentPosion =  Math.max(0, gotoPo - horiz2.getWidth() / 2 / Log.instance().alignPara.baseWidth);
    gotoPosion = gotoPo;
    annoAssemblyPanel.axiPanel.slider.posionX = horiz1.getWidth() / 2 - annoAssemblyPanel.axiPanel.slider.width / 2;
    annoPosion = Math.max(0, gotoPosion - ((horiz1.getWidth() / 2) << Log.instance().annoPara.ZOOM));

    }*/
    public void gotoPieceSite(Piece annoFocusePiece) {
        ((AnnoPartCenter) annoAssemblyPanel.annotationPanel.partCenter).focusedPiece = annoFocusePiece;
        gotoSiteAndValidate(annoFocusePiece.geneticPiece.start, true);
    }

    public void gotoSiteAndValidate(int p, boolean ifSetVer1Value) {
        if (p <= 0 || p > Log.instance().reference.currentContigLen) {
            ReportInfo.reportInformation("input site out of range");
            return;
        }
        //  System.out.println(gotoPosion + "___________________=gotoPosion_________________________");
        gotoSite(p, ifSetVer1Value);
    }

    public void gotoSite(int p, boolean ifSetVer1Value) {
        if (p <= 0 || p > Log.instance().reference.currentContigLen) {
            return;
        }
        boolean ifReload = true;
        if (gotoPosion == p) {
            ifReload = false;
        }
        gotoPosion = p;
        // System.out.println(gotoPosion+"+++++++++++++++++++++++++=gotoPosion+++++++++++++++++++++++++");
        annoAssemblyPanel.selectX = gotoPosion;
        setPosionsByGotoPosion(ifSetVer1Value);
        gotoPosion(ifSetVer1Value, ifReload);
    }

    public void gotoPosion(final boolean ifSetHoriz1, final boolean ifReload) {
        if (dataRep == null) {
            return;
        }
        //System.out.println((annoPosionStart >> Log.instance().annoPara.ZOOM)+"----------------");
        final int annoPosion = annoPosionStart;
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                //  annoAssemblyPanel.translate(annoPosionStart >> Log.instance().annoPara.ZOOM, annoAssemblyPanel.dy);
                if (ifReload) {
                    reloadAligment();
                }
                return null;
            }

            public void finished() {


                alignAssemblyPanel.reflash();
                if (ifSetHoriz1) {
                    //F2orMaigc.setVer1Value(annoPosion >> Log.instance().annoPara.ZOOM);
                    ForMagic.setVer1Value(gotoToHor1());

                }
            }
        };
        worker.start();
        annoAssemblyPanel.translate(annoPosion >> Log.instance().annoPara.ZOOM, annoAssemblyPanel.dy);
    }

    public void reloadAligment() {

        //SystemUtil.printCurrentTime("ref start");
        LogImplement log = Log.instance();

        seqData = new SeqData();
        // System.out.println(alignmentPosion + "--------" + Log.instance().reference.currentContigLen);
        //  System.out.println(alignmentPosion+"-========================-----"+gotoPosion);
        dataRep.getSeqTrackAndRef(Math.max(1, alignmentPosion), log, seqData);//need modify
        if (Log.instance().viewMode == Log.instance().global.MethyMode) {
            // seqData.methyRefSeq = seqData.reference.replaceAll("C", "T");
        } else {
            seqData.methyRefSeq = null;
        }


        if (seqData.reference == null) {
            return;
        }
        //  seqData.refer_end = seqData.refer_start + seqData.reference.length();

//        float coverage = Log.instance().global.coverage;
        //   float columnPanelHeight = ForEverStatic.desktopBoundsHeight / 2;
        //  float adjustHeight = columnPanelHeight / (coverage * Log.instance().global.covToMaxHeight);
        // adjustHeight = 1;
        //seqDataColumn = seqData;

        dataRep.getColumnTrack(Log.instance().global.alignmentWindowLen, seqData.seqPanelDatas);
        // SystemUtil.printCurrentTime("ref end");
        //System.out.println(seqData.seqPanelData.seqEntries.pieces.length+"  ------pieces.length-----");
        //System.out.println("");
    }

private void vert1AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_vert1AdjustmentValueChanged
    // TODO add your handling code here:
    annoAssemblyPanel.translate(horiz1.getValue(), vert1.getValue());
}//GEN-LAST:event_vert1AdjustmentValueChanged

private void jCheckBoxMenuItemRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemRefActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxMenuItemRefActionPerformed

private void jCheckBoxMenuItemRefStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemRefStateChanged
    if (jCheckBoxMenuItemRef.isSelected()) {
        alignAssemblyPanel.ref_panel.setVisible(true);

    } else {
        alignAssemblyPanel.ref_panel.setVisible(false);
    }

    // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxMenuItemRefStateChanged

    private void closeProject() {
        jComboBoxContigName.removeAllItems();
        this.dataRep = null;
        this.seqData = null;
        this.annoAssemblyPanel.annotationPanel.tracks = null;
        this.annoAssemblyPanel.clearScreen();
        this.annoAssemblyPanel.reflash();
        this.alignAssemblyPanel.clearScreen();
    }

private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed

    closeProject();
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem12ActionPerformed

private void jAnnoTrackSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnnoTrackSetActionPerformed
    setDataSets();    // TODO add your handling code here:
}//GEN-LAST:event_jAnnoTrackSetActionPerformed

private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed

    exportImage(new MagicPanel[]{alignAssemblyPanel.pipleup_panel, alignAssemblyPanel.ref_panel, alignAssemblyPanel.align_panel});
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem16ActionPerformed

private void horiz2AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_horiz2AdjustmentValueChanged
    final SwingWorker worker = new SwingWorker() {

        public Object construct() {
            alignAssemblyPanel.translateX(horiz2.getValue());
            return null;
        }

        public void finished() {
            //
            setSliderInnerPosion();
            annoAssemblyPanel.reflash();
            //assemblyPanel.privateRepaint();
        }
    };
    worker.start();
}//GEN-LAST:event_horiz2AdjustmentValueChanged

    public boolean checkFilterEnable() {
        if (dataRep == null || dataRep.trackNum < 1) {
            return false;
        }
        boolean selected = false;
        for (int i = 0; i < dataRep.trackNum; i++) {
            // System.out.println(i);

            if (dataRep.tracks[i] == null) {
                System.out.println(i + " is null");
            }
            if (dataRep.tracks[i].trackSet.name.equals(StringRep.SNPS)) {
                selected = true;
            }
        }
        return selected;
    }

private void jMenuItemFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFilterActionPerformed

    Track selectedOption = null;
    boolean selected = false;
    for (int i = 0; i < dataRep.trackNum; i++) {
        if (dataRep.tracks[i].trackSet.name.equals(StringRep.SNPS) && selected == false) {
            selectedOption = dataRep.tracks[i];
            selected = true;
        }
    }
    new FilterAnnotationMain(this, selectedOption);
}//GEN-LAST:event_jMenuItemFilterActionPerformed

private void jPanel3ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel3ComponentResized
    if (dataRep == null) {
        return;
    }
    alignAssemblyPanel.reflash();
    // TODO add your handling code here:
}//GEN-LAST:event_jPanel3ComponentResized

private void horiz1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_horiz1MouseDragged
//System.out.println("------------horiz1MouseDragged---------------------");
    if (dataRep == null) {
        return;
    }
    // System.out.println(horiz1.getValue()+"  --------------horiz1  max-------------------      "+ horiz1.getMaximum()+"  -----------zoom ----------  "+Log.instance().annoPara.ZOOM);
    // System.out.println(horiz1.getMaximum()+"  --------------horiz1  zoom-------------------      "+ Log.instance().annoPara.ZOOM);
    horiz1GotoAnnoPosion();
    // TODO add your handling code here:
}//GEN-LAST:event_horiz1MouseDragged

    private void horiz1GotoAnnoPosion() {
        gotoSite(hor1ToGoto(), false);
    }

    public int gotoToHor1() {
        float percent = ((float) horiz1.getMaximum()) / (horiz1.getMaximum() - horiz1.getVisibleAmount());

        //   int gotoP = Math.max(1, (int) (horiz1.getValue() * percent) << (Log.instance().annoPara.ZOOM));
        int horiz = (int) ((gotoPosion >> Log.instance().annoPara.ZOOM) / percent);
        return horiz;
    }

    public int hor1ToGoto() {
        float percent = ((float) horiz1.getMaximum()) / (horiz1.getMaximum() - horiz1.getVisibleAmount());

        int gotoP = Math.max(1, (int) (horiz1.getValue() * percent) << (Log.instance().annoPara.ZOOM));
        return gotoP;
    }

    /*  private void gotoPosionByPosion(final int annoPosion, final boolean ifSetHoriz1) {
    this.annoPosionStart = annoPosion;
    annoPosionEnd = annoPosion + (horiz1.getWidth() << Log.instance().annoPara.ZOOM) + annoEndPosionExtra;

    final SwingWorker worker = new SwingWorker() {

    public Object construct() {
    setPosionsByAnnoPosion();
    reloadAligment();
    return null;
    }

    @Override
    public void finished() {
    annoAssemblyPanel.translate(horiz1.getValue(), annoAssemblyPanel.dy);
    alignAssemblyPanel.reflash();
    if (ifSetHoriz1) {
    ForMaigc.setVer1Value(annoPosion >> Log.instance().annoPara.ZOOM);
    }
    }
    };
    worker.start();
    }*/
private void horiz1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_horiz1MouseClicked

    float percent = ((float) evt.getX()) / ((float) horiz1.getWidth());

    ForMagic.setVer1Value((int) (percent * horiz1.getMaximum()));

    if (dataRep == null) {
        return;
    }
    horiz1GotoAnnoPosion();

}//GEN-LAST:event_horiz1MouseClicked

    protected void gotoPrevious(int len) {
        if (dataRep == null) {
            return;
        }
        int newGotoPosion = Math.max(gotoPosion - len, 1);
        //gotoPosionByPosion(newAnnoPosion, true);
        gotoSite(newGotoPosion, true);
    }

    protected int getMaxAnnoPosion() {
        return Log.instance().reference.currentContigLen - horiz2.getWidth() / Log.instance().alignPara.baseWidth;
    }

    protected void gotoNext(int len) {
        if (dataRep == null) {
            return;
        }
        int newGotoPosion = Math.min(gotoPosion + len, getMaxAnnoPosion());
        gotoSite(newGotoPosion, true);
    }

    /* private void gotoPosionByPosion2(final int annoPosion) {
    this.annoPosionStart = annoPosion;
    annoPosionEnd = annoPosion + (horiz1.getWidth() << Log.instance().annoPara.ZOOM) + annoEndPosionExtra;
    final SwingWorker worker = new SwingWorker() {

    public Object construct() {
    setPosionsByAnnoPosion();
    reloadAligment();
    return null;
    }

    @Override
    public void finished() {
    annoAssemblyPanel.translate(horiz1.getValue(), annoAssemblyPanel.dy);
    alignAssemblyPanel.clearReflash();

    ForMaigc.setVer1Value(annoPosion >> Log.instance().annoPara.ZOOM);

    }
    };
    worker.start();
    }*/
private void nextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMousePressed
    gotoNext(Log.instance().global.adjustWindowSize);
}//GEN-LAST:event_nextMousePressed

private void previousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousMousePressed
    gotoPrevious(Log.instance().global.adjustWindowSize);
}//GEN-LAST:event_previousMousePressed

private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_previousActionPerformed

private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_nextActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed


    if (mainOptionDialog == null) {
        mainOptionDialog = new MainOptionDialog(this);
        mainOptionDialog.setVisible(true);
        return;
    }
    if (!mainOptionDialog.isVisible()) {
        mainOptionDialog.setVisible(true);
    }
    if (!mainOptionDialog.isFocused()) {
        mainOptionDialog.requestFocus();
    }
    mainOptionDialog.toFront();    // TODO add your handling code here:
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItem2ActionPerformed

private void horiz1AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_horiz1AdjustmentValueChanged
    if (dataRep == null || horiz1.getValueIsAdjusting()) {
        return;
    }
    horiz1GotoAnnoPosion();
    // TODO add your handling code here:
}//GEN-LAST:event_horiz1AdjustmentValueChanged

private void next1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next1MousePressed
    // TODO add your handling code here:
}//GEN-LAST:event_next1MousePressed

private void next1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next1ActionPerformed
    annoListDialog = new AnnoListDialog(this);
    annoListDialog.setVisible(true);
    annoListDialog.requestFocus();
    annoListDialog.toFront();    // TODO add your handling code here:2    // TODO add your handling code here:
}//GEN-LAST:event_next1ActionPerformed

private void previous1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previous1MousePressed
    gotoPrevious(Log.instance().global.adjustWindowSize * Log.instance().alignPara.baseWidth);    // TODO add your handling code here:
}//GEN-LAST:event_previous1MousePressed

private void previous1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previous1ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_previous1ActionPerformed

private void next2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next2MousePressed
    gotoNext(Log.instance().global.adjustWindowSize * Log.instance().alignPara.baseWidth);       // TODO add your handling code here:
}//GEN-LAST:event_next2MousePressed

private void next2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next2ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_next2ActionPerformed

private void jMenuItemStatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStatActionPerformed

    PaintChart.paint();
    // TODO add your handling code here:
}//GEN-LAST:event_jMenuItemStatActionPerformed
    public void setAligmentFontSize(int fontSize) {
        //  //System.out.println(fontSize+"--------fontSize------------------");
        int oldFontWidth = Log.instance().alignPara.baseWidth;
        Font oldFont = Log.instance().alignPara.DNA_FONT;
        Font newFont = new Font(oldFont.getFamily(), oldFont.getStyle(), fontSize);

        setAligmentFont(oldFontWidth, oldFont, newFont);

    }

    public void setAligmentFont(int oldFontWidth, Font oldFont, Font newFont) {

        Log.instance().alignPara.DNA_FONT = newFont;
        Log.instance().alignPara.baseWidth = alignAssemblyPanel.getFontMetrics(newFont).charWidth('A');
        Log.instance().alignPara.baseHeight = alignAssemblyPanel.getFontMetrics(newFont).getAscent();
        BaseImage.reset();
        for (SeqPanelData seqPanelData : seqData.seqPanelDatas) {
            seqPanelData.pileupEntries.setTrackViewPropoery();
            seqPanelData.seqEntries.setTrackViewPropoery();
        }
        int centerAlignIndex = (horiz2.getValue() + alignAssemblyPanel.align_panel.getWidth() / 2) / oldFontWidth;


        int currenIndex = centerAlignIndex - alignAssemblyPanel.align_panel.getWidth() / 2 / Log.instance().alignPara.baseWidth;

        int newHoriz2Value = currenIndex * Log.instance().alignPara.baseWidth;

        horiz2.setValue(newHoriz2Value);
        assemblyPanelRepaint();
        annoAssemblyPanel.axiPanel.updateSlider();
        annoAssemblyPanel.reflash();
    }

    public void openLog() {
        final String logFile = SwingUtil.getOpenFileByChooser("Load Project", new String[]{"log"}, this);
        if (logFile == null || logFile.equals("")) {
            return;
        }

        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                try {
                    Log.setInstance((LogImplement) ReadData.openObject(logFile));
                    Log.ifChangeWorkerSpace(logFile);
                } catch (java.io.InvalidClassException ex) {
                    ReportInfo.reportInformation("The software version is not accordance with LOG file!\n Perhaps you can create a new project to solve this problem.");
                    stop();
                    return null;
                } catch (Exception e) {
                    ReportInfo.reportError("", e);
                }

                BaseImage.reset();
                int state = loadLog(Log.instance());
                if (state != ForEverStatic.RET_OK) {
                    //System.out.println("state:"+state);
                    stop();
                }
                return null;
            }

            @Override
            public void finished() {
                reflash();
            }
        };
    
        worker.start();

    }

    public void openDefaultLog() {
        final String logFile = SwingUtil.getOpenFileByChooser("Load Project", new String[]{"log"}, this);
        if (logFile == null || logFile.equals("")) {
            return;
        }

        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                try {
                    Log.setInstance((LogImplement) ReadData.openObject(logFile));
                    Log.ifChangeWorkerSpace(logFile);
                } catch (java.io.InvalidClassException ex) {
                    ReportInfo.reportInformation("The software version is not accordance with LOG file!\n Perhaps you can create a new project to solve this problem.");
                    stop();
                    return null;
                } catch (Exception e) {
                    ReportInfo.reportError("", e);
                }

                BaseImage.reset();
                int state = loadLog(Log.instance());
                if (state != ForEverStatic.RET_OK) {
                    //System.out.println("state:"+state);
                    stop();
                }
                return null;
            }

            @Override
            public void finished() {
                reflash();
            }
        };
        worker.start();

    }

    public void isExit(boolean saved) {
        if (dataRep != null) {
            int answer = JOptionPane.showConfirmDialog(this, "Save the workspace?", "Save", JOptionPane.YES_NO_OPTION);
            switch (answer) {
                case JOptionPane.YES_OPTION: {
                    saveLog();
                    break;
                }
                case JOptionPane.NO_OPTION: {
                    break;
                }
                case JOptionPane.CLOSED_OPTION: {
                    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    return;
                }
            }
        }


        this.dispose();
        if (alignment_option_dialog != null) {
            alignment_option_dialog.dispose();
        }
        if (annotation_option_dialog != null) {
            annotation_option_dialog.dispose();
        }
    }

    public int loadAll(final ViewerLog log) {
        dataRep = new DataRep(log);
        int getTrackStat = dataRep.getTracks();
        if (getTrackStat != ForEverStatic.RET_OK) {
            // System.out.println(getTrackStat);
            return getTrackStat;
        }
        reloadAligment();
        // System.out.println(getTrackStat);
        return getTrackStat;
    }

    public void reflash() {
        if (dataRep.tracks == null) {
            return;
        }
        setMenuItemDisable();

        setBarsAmount();

        setjComboBoxContigName();
        updateAnnotation();
        alignAssemblyPanel.reflash();
        setBarsValue();
    }

    public void reload() {
        reloadAnnotation();
        reloadAligment();
        alignAssemblyPanel.reflash();
    }

    public void reloadAnnotation() {
        if (dataRep == null) {
            return;
        }
        dataRep.setTrackByCurrentContig();
    }

    /*public void updateSubPanels() {
    // //System.out.println("Processing Data");


    updateAnnotation();
    alignAssemblyPanel.align_panel.setData();
    alignAssemblyPanel.ref_panel.setData();
    alignAssemblyPanel.pipleup_panel.setData();
    setMenuItemDisable();

    }*/
    public void updateAnnotation() {
        if (dataRep != null && dataRep.tracks != null) {
            annoAssemblyPanel.loadData(dataRep);
            annoAssemblyPanel.reflash();
        }

    }

    public void setBarsValue() {
        int splitHeight = Math.min(annoAssemblyPanel.getMaxHeight(), 400);
        jSplitPane1.setDividerLocation(splitHeight);

        vert1.setValues(vert1.getValue(), splitHeight, 0, annoAssemblyPanel.getMaxHeight());
        horiz1.setValues(horiz1.getValue(), getWidth(), 0, annoAssemblyPanel.getMaxWidth());

        vert1.repaint();
        horiz1.repaint();
    }

    public int getDividerLocation() {
        return jSplitPane1.getDividerLocation();
    }

    public void setBarsAmount() {

        annoAssemblyPanel.validate();
        if (Log.instance().reference != null) {
            //  System.out.println("setMaxWidth: "+(Log.instance().reference.currentContigLen >> Log.instance().annoPara.ZOOM));
            annoAssemblyPanel.setMaxWidth(Log.instance().reference.currentContigLen >> Log.instance().annoPara.ZOOM);
        }

        // System.out.println("max width: " + annoAssemblyPanel.getMaxWidth());
        SwingUtil.setMaigicPanelScroll(annoAssemblyPanel, horiz1, vert1);
        // System.out.println(horiz1.getMaximum()+"  "+annoAssemblyPanel.getMaxWidth()+" "+annoAssemblyPanel.getWidth());
       /* SwingUtil.setMaigicPanelScrollImpl(annoAssemblyPanel, vert1);

        horiz1.setMaximum(Log.instance().reference.currentContigLen);
        horiz1.setVisibleAmount(annoAssemblyPanel.getWidth());
        horiz1.setb*/

        horiz1.setUnitIncrement(2);
        vert1.setUnitIncrement(2);
        horiz1.setVisible(true);



        SwingUtil.setMaigicPanelScrollImpl(alignAssemblyPanel.align_panel, horiz2);
        horiz2.setUnitIncrement(Log.instance().alignPara.baseWidth);
        horiz2.setVisible(true);

        alignAssemblyPanel.align_panel.setScrollBar();
        //  SwingUtil.setMaigicPanelScrollImpl(assemblyPanel.align_panel, assemblyPanel.align_panel.jScrollBar1);
        //  assemblyPanel.align_panel.jScrollBar1.setUnitIncrement(10);
    }

    public void receiveFont(String which, Font font) {
        //System.out.println(which + "\t" + font.toString());
        if (which.equals("NAME")) {
            annoAssemblyPanel.annotationPanel.tagFont = font;
        } else if (which.equals("AXIS")) {
            annoAssemblyPanel.axiPanel.AXIS_FONT = font;
        } else {
            return;
        }
        annoAssemblyPanel.reflash();
    }

    public void receiveViewMode(String info) {
        if (Log.instance().annoPara.VIEW_MODE.equals(info)) {
            return;
        }
        //System.out.println("Setting as " + info);
        Log.instance().annoPara.VIEW_MODE = info;


        annoAssemblyPanel.reflash();
    }

    public void receiveArrowMode(String info) {
        if (Log.instance().annoPara.ARROW_MODE.equals(info)) {
            return;
        }
        //System.out.println("Setting as " + info);
        Log.instance().annoPara.ARROW_MODE = info;

        annoAssemblyPanel.reflash();

    }

    public void receiveRowHeight(final int index, final int value) {
        if (dataRep == null || index >= dataRep.trackNum) {
            return;
        }
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                dataRep.tracks[index].trackSet.trackHeight = value;
                annoAssemblyPanel.setSite();

                //
                return null;
            }

            @Override
            public void finished() {
                annoAssemblyPanel.reflash();
            }
        };
        worker.start();

    }

    public void receiveRowBG(int index, Color color) {
        if (dataRep == null || index >= dataRep.trackNum) {
            return;
        }


        dataRep.tracks[index].backGround = color;
        annoAssemblyPanel.reflash();
    }

    public void receiveReadsHeight(int index, int value) {
        if (dataRep == null || index >= dataRep.trackNum) {
            return;
        }
        dataRep.tracks[index].trackSet.pieceHeight = value;

        annoAssemblyPanel.reflash();
    }

    public void receiveReadsInterval(int index, int value) {
        if (dataRep == null || index >= dataRep.trackNum) {
            return;
        }
        dataRep.tracks[index].trackSet.pieceInterval = value;

        annoAssemblyPanel.reflash();
    }

    public void receiveReadsColor(int index, Color color) {
        if (dataRep == null || index >= dataRep.trackNum) {
            return;
        }
        dataRep.tracks[index].trackSet.COLOR = color;
        annoAssemblyPanel.reflash();
    }

    public void receiveEntriesChanges() {
        annoAssemblyPanel.clearData();
        annoAssemblyPanel.loadData(dataRep);
        annoAssemblyPanel.setSite();

        annoAssemblyPanel.reflash();

        alignAssemblyPanel.align_panel.setData();
        alignAssemblyPanel.ref_panel.setData();

        if (alignment_option_dialog != null) {
            alignment_option_dialog.update(this);
        }
        if (annotation_option_dialog != null) {
            annotation_option_dialog.update(this);
        }
    }

    /* public void mySetValue(int x) {

    if (x > 200) {
    ForMaigc.setVer1Value((x - 200) >> Log.instance().annoPara.ZOOM);
    } else {
    ForMaigc.setVer1Value(0);
    }
    //annotationPanel.should_save_workspace = true;
    }*/
    public JScrollBar getHorizontal() {
        return horiz1;
    }

    public JScrollBar getVertical() {
        return vert1;
    }

    public void saveLog() {

        if (Log.instance().projectProperty.log_file == null) {
            Log.instance().projectProperty.log_file = ForEverStatic.CURRENT_PATH + ForEverStatic.FILE_SEPARATOR + "WORKSPACE.LOG";
        }
        ////System.out.println("logBean.projectProperty.log_file:        "+logBean.projectProperty.log_file);
        WriteData.saveObject(Log.instance().projectProperty.log_file, Log.instance(), false);

    }

    public void importAnnotation() {
        final String newAnnoFile = SwingUtil.getOpenFileByChooser("Import Annotation", null, this);
        if (newAnnoFile == null || newAnnoFile.equals("")) {
            return;
        }
        String format = FileFormat.checkFileFormatByContent(newAnnoFile);

        if (!FileFormat.checkAnnotationFormat(format)) {
            ReportInfo.reportValidate("Format:" + format + " not surported, please check");
            return;
        }
        importFile(newAnnoFile);

    }

    public void importFile(final String newAnnoFile) {
        final ArrayList<String> readfiles = new ArrayList<String>();
        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                readfiles.add(newAnnoFile);
                dataRep.addTrackData(readfiles);

                return null;
            }

            public void finished() {
                reflash();
            }
        };
        //  monitor = new StepProgress(this, worker);
        worker.start();
    }

    public void exportImage(MagicPanel[] panel) {

        String filename = SwingUtil.getSaveFileByChooser("Export Image", new String[]{"pdf"}, this);
        if (filename == null) {
            return;
        }

        SwingUtil.exportVerticalPanelsImage(panel, filename);
    }

    public void setDataSets() {
        if (dataRep == null) {
            return;
        }
        TrackOptionDialog set_option = new TrackOptionDialog(this, false);
        set_option.setVisible(true);
    }

    public static void main(String args[]) {

        try {
            //System.out.println("CURRENT   DIR:   "   +   System.getProperty("user.dir"));
            SystemUtil.addLibClassPath();

            PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
            PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
            PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
            PlasticLookAndFeel.setTabStyle("Metal");
            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            UIManager.put("DesktopIconUI", "javax.swing.plaf.metal.MetalDesktopIconUI");
            //  UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

            Icons.initialize("/icons", ".png");


            instance = new MagicFrame();
            instance.setVisible(true);
            instance.setMenuItemDisable();
            //  if (SystemUtil.checkUpdate()) {
            //  new CheckUpdate();
            // }
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
            }
        });
    }
    public static MagicFrame instance;

    public void resetAssembly() {
        BaseImage.reset();
        assemblyPanelRepaint();
    }
    /**
     * @param args the command line arguments
     */
    //private javax.swing.JScrollPane jScrollPane2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Settings;
    private javax.swing.JButton goto_button1;
    public javax.swing.JScrollBar horiz1;
    public javax.swing.JScrollBar horiz2;
    private javax.swing.JMenuItem jAlignSet;
    private javax.swing.JMenuItem jAnnoParaSet;
    private javax.swing.JMenuItem jAnnoTrackSet;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAlign;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAnno;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPiple;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemRef;
    private javax.swing.JComboBox jComboBoxContigName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemAnnotation;
    private javax.swing.JMenuItem jMenuItemBSMAP;
    private javax.swing.JMenuItem jMenuItemFilter;
    private javax.swing.JMenuItem jMenuItemGATKHome;
    private javax.swing.JMenuItem jMenuItemGenotype;
    private javax.swing.JMenuItem jMenuItemImport;
    private javax.swing.JMenuItem jMenuItemMagicHome;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemPicardHome;
    private javax.swing.JMenuItem jMenuItemPrimer;
    private javax.swing.JMenuItem jMenuItemSamtoolsHome;
    private javax.swing.JMenuItem jMenuItemStat;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextFieldCoordinator;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton next;
    private javax.swing.JButton next1;
    private javax.swing.JButton next2;
    private javax.swing.JButton open_button;
    private javax.swing.JButton open_button1;
    private javax.swing.JButton previous;
    private javax.swing.JButton previous1;
    private javax.swing.JScrollBar vert1;
    private javax.swing.JButton zoomin_button1;
    private javax.swing.JButton zoomin_button2;
    private javax.swing.JButton zoomout_button1;
    private javax.swing.JButton zoomout_button2;
    // End of variables declaration//GEN-END:variables
}
