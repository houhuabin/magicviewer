package Magic.Units.Track;

import Magic.Analysis.Filter.FieldProperty;
import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.Piece.Piece;
import Magic.IO.MonitoredFileReadImplement;
import Magic.Units.Color.ColorRep;

import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.FileFormat;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Task.SimpleTask;
import Magic.Units.Gui.Task.TaskBase;
import Magic.Units.Main.Tag;
import Magic.Units.Piece.FilePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.WinMain.AnnotationPanel;
import Magic.WinMain.MagicFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import utils.ReportInfo;

public class Track {

    public TrackSet trackSet = new TrackSet();
    public Color backGround;
    public int middleY;
    public boolean changed;
    public IteratorPieces iteratorPieces = new IteratorPieces();
    public Piece[] currentPieces;//当前congtig的
    public Color selectColor = Color.cyan;
   public Vector<Integer> selected_vector = new Vector<Integer>();
    public HashMap<String, FieldProperty> pieceFieldProperty = new HashMap();

    public boolean ok2Read=true;

    public Tag tag;

    

    public Track() {
        trackSet.name = "";
        trackSet.filename = "";
        trackSet.format = "";
        trackSet.pieceHeight = 5;
        trackSet.pieceInterval = 1;
        trackSet.trackHeight = 60;
        // COLOR = Color.BLUE;
        backGround = Color.WHITE;
        middleY = 1;
        changed = false;
        currentPieces = null;
    }

    public void setTagLocation() {

        int site = middleY;
        tag.setLocation(2, site - tag.height / 2);
    }

    public void paintTag(Graphics2D g) {

        Dimension d = MagicFrame.instance.annoAssemblyPanel.getSize();
        AnnotationPanel annotationPanel = MagicFrame.instance.annoAssemblyPanel.annotationPanel;


        if (!tag.visible) {
            //System.out.println("!tag.visible");
            return;
        }

        g.setColor(tag.foreground);

        g.setComposite(AlphaComposite.getInstance(10, 0.5F));
        g.setColor(tag.background);

        RoundRectangle2D.Float rect1 = new RoundRectangle2D.Float(tag.x, tag.y, tag.width, tag.height, 10, 10);
        //g.fillRoundRect(tag.x, tag.y, tag.width, tag.height, 10, 10);
        g.fill(rect1);
        //  composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 100 / 100.0F);
        g.setComposite(AlphaComposite.getInstance(10, 1));

        if (tag.bound_visible) {
            g.setColor(ColorRep.darkgolden);
            g.draw(rect1);
        }
        g.setColor(tag.foreground);
        g.setFont(tag.font);
        int w = annotationPanel.getFontMetrics(tag.font).stringWidth(tag.text);
        int h = annotationPanel.getFontMetrics(tag.font).getAscent();
        g.drawString(tag.text, tag.x + tag.width / 2 - w / 2, tag.y + tag.height / 2 + h / 2);

    }

    public Track(Track oldTrack) {
        this.trackSet = oldTrack.trackSet;

    }

    public void getTrack(String fileName, String contig,TaskBase task) {
    //    MagicFrame.instance.monitor.setVisible(true);
        HashMap<String, ArrayList<Piece>> itemsTemp = getTrackItems(fileName, contig, task);
        this.iteratorPieces.set(itemsTemp);
    }

    public HashMap<String, ArrayList<Piece>> getTrackItems(String infile, String contig,TaskBase task) {
        //  SystemUtil.printCurrentTime();
        String format = FileFormat.checkFileFormatByContent(infile);
        //  FileFormat ff = new FileFormat();
        //   FileFormat.PieceField[] formatDefine = ff.getFormatDefineString(format);
        HashMap result = new HashMap();

        try {
            MonitoredFileReadImplement br = new MonitoredFileReadImplement(infile, task);

            Class cls = Class.forName("Magic.Units.Piece." + format + "Piece");
            FilePiece filePiece = (FilePiece) cls.newInstance();
            Method m1 = cls.getMethod("readTrackFile", MonitoredFileReadImplement.class, Track.class, HashMap.class);
            m1.invoke(filePiece, br, this, result);        

            br.close();
        } catch (Exception ex) {
            ReportInfo.reportError("", ex);
        }

        return result;
    }
    /*
    public HashMap<String, ArrayList<Piece>> getTrackItems(String infile, String contig) {
    //  SystemUtil.printCurrentTime();
    String format = FileFormat.checkFileFormatByContent(infile);
    FileFormat ff = new FileFormat();
    FileFormat.PieceField[] formatDefine = ff.getFormatDefineString(format);
    HashMap result = new HashMap();

    try {
    MonitoredFileReadImplement br = new MonitoredFileReadImplement(infile, MagicFrame.instance.monitor);

    OnelineFormatParse olfp = new OnelineFormatParse(formatDefine);
    HashMap onlineFormat = olfp.getOnlineFormat();

    int lineNum = 0;
    Class cls = Class.forName("Magic.Units.Piece." + format + "Piece");
    String line;
    while ((line = br.readLine()) != null) {
    //   System.out.println(line);
    Piece piece;
    ++lineNum;

    if (line.startsWith("#")) {
    continue;
    }
    if (formatDefine == FileFormat.PTTFormat) {
    if (line.startsWith("Contig")) {
    this.hmtemp.put("contig", line.split("\\s+")[1]);
    }

    if (!(FormatCheck.isLocation(line.split("\\t+")[0]))) {
    continue;
    }

    }

    if (formatDefine == FileFormat.BEDFormat) {
    piece = new NSEPiece();
    } else {
    piece = new Piece();
    }

    piece.viewPiece.parent = this;

    piece.filePiece = ((FilePiece) cls.newInstance());
    setValue(line, formatDefine, piece, onlineFormat, result);
    }

    br.close();
    } catch (Exception ex) {
    ReportInfo.reportError("", ex);
    }

    return result;
    }

    public BasePiece getGeneticPiece() {
    return new GeneticUnitPiece();
    }

    public void setValue(String line, PieceField[] formatDefine, Piece piece, HashMap onlineFormat, HashMap<String, ArrayList<Piece>> result) {

    String fieldVale[] = line.split("\\t+");
    if (fieldVale[2].toLowerCase().equals("cds") && formatDefine == FileFormat.GFFFormat) {
    piece.geneticPiece = new CDSPiece();
    } else {
    piece.geneticPiece = getGeneticPiece();
    }
    if (formatDefine == FileFormat.PTTFormat) {

    /// piece.geneticPiece.chrom = (String) hmtemp.get("contig");
    addPiece(result, piece.geneticPiece.chrom, piece);
    //  System.out.println( piece.geneticPiece.chrom+"============= piece.geneticPiece.chrom==========");
    }



    for (int i = 0; i < fieldVale.length; i++) {
    if (fieldVale[i].equals(".")) {
    continue;
    }
    PieceField fieldNam = (PieceField) onlineFormat.get(i);
    if (fieldNam != null) {
    if (setGeneticFieldValue(fieldNam, piece, fieldVale[i], result)) {
    } else {
    setFileFieldValue(fieldNam, piece, fieldVale[i], result);
    }
    }
    }
    }

    public boolean setGeneticFieldValue(PieceField fieldNam, Piece piece, String fieldValue, HashMap<String, ArrayList<Piece>> result) {
    boolean haveSet = false;
    if (fieldNam == PieceField.chrom) {
    piece.geneticPiece.chrom = fieldValue;
    addPiece(result, fieldValue, piece);
    haveSet = true;
    } else if (fieldNam == PieceField.strand) {
    if (fieldValue.equals("+")) {
    piece.geneticPiece.strand = true;
    } else {
    piece.geneticPiece.strand = false;
    }
    haveSet = true;
    } else if (fieldNam == PieceField.id) {
    piece.geneticPiece.id = fieldValue;
    haveSet = true;
    } else if (fieldNam == PieceField.start) {
    piece.geneticPiece.start = Integer.valueOf(fieldValue);
    haveSet = true;
    } else if (fieldNam == PieceField.end) {
    piece.geneticPiece.end = Integer.valueOf(fieldValue);
    haveSet = true;
    } else if (fieldNam == PieceField.sequence) {
    piece.geneticPiece.sequence = fieldValue;
    haveSet = true;
    } else if (fieldNam == PieceField.feature) {
    ((GeneticUnitPiece) piece.geneticPiece).feature = fieldValue;
    haveSet = true;
    } else if (fieldNam == PieceField.frame) {
    // System.out.println(fieldValue+"frame");
    ((GeneticUnitPiece) piece.geneticPiece).frame = Integer.valueOf(fieldValue);
    haveSet = true;
    }
    return haveSet;
    }*/

    public static void addPiece(HashMap<String, ArrayList<Piece>> result, String contigName, Piece piece) {
        if (result.containsKey(contigName)) {
            ((ArrayList) result.get(contigName)).add(piece);
        } else {
            // System.out.println(" new chrom   "+contigName);
            ArrayList piecArrayList = new ArrayList();
            piecArrayList.add(piece);
            result.put(contigName, piecArrayList);
        }
    }

    public static HashMap<String, String> getGroup(String group, Piece piece) {
        String[] groupsElements = group.split(";\\s?");
        HashMap hm = new HashMap();
        for (String groupsElement : groupsElements) {
            String[] temp = groupsElement.split("=");
            if (temp.length == 2) {
                if (temp[0].equals("SNPType")) {
                    if (piece.geneticPiece instanceof SNPPiece) {
                        ((SNPPiece) piece.geneticPiece).snpType = SNPType.valueOf(temp[1]);
                    }
                } else {
                    hm.put(groupsElement.split("=")[0], groupsElement.split("=")[1]);
                }

            } else if (groupsElement.split("\\s+").length == 2) {
                hm.put(groupsElement.split("\\s+")[0], groupsElement.split("\\s+")[1]);
            }
        }
        return hm;
    }
    /*
    public void setFileFieldValue(PieceField fieldNam, Piece piece, String fieldValue, HashMap<String, ArrayList<Piece>> result) {
    try {
    if (fieldNam == PieceField.group) {

    Field field = piece.filePiece.getClass().getField(fieldNam.name());
    field.set(piece.filePiece, getGroup(fieldValue, piece));

    } else if (fieldNam == PieceField.groupEnd) {
    Field field = piece.filePiece.getClass().getField("groupStart");
    String groupStart = (String) field.get(piece.filePiece);

    Field groupField = piece.filePiece.getClass().getField("groupTwo");
    groupField.set(piece.filePiece, getGroupTwo(groupStart, fieldValue));
    } else if (fieldNam == PieceField.score) {

    if (!fieldValue.equals("0")) {
    if (piece.filePiece instanceof BEDPiece) {
    int index = (int) (9 - Integer.parseInt(fieldValue) / 112);
    piece.viewPiece.color = ColorRep.GrayColor[index];
    }
    }
    Field field = piece.filePiece.getClass().getField(fieldNam.name());
    field.set(piece.filePiece, RefelectUtil.constructFromString(RefelectUtil.getUnderlyingType(field), fieldValue));

    } else if (fieldNam == PieceField.color) {
    if (!fieldValue.equals("0")) {
    String[] tempSt2 = fieldValue.split(",");
    piece.viewPiece.color = new Color(Integer.parseInt(tempSt2[0]), Integer.parseInt(tempSt2[1]), Integer.parseInt(tempSt2[1]));
    }

    } else if (fieldNam == PieceField.thickStart) {
    //  subPiece = new NSEPiece[1];
    NSEPiece subPieceThick = NSEPiece.class.newInstance();
    subPieceThick.geneticPiece.strand = piece.geneticPiece.strand;
    // subPieceThick.geneticPiece.chrom = piece.geneticPiece.chrom;
    subPieceThick.geneticPiece.start = Integer.parseInt(fieldValue);
    ((NSEPiece) piece).subPieceList.add(subPieceThick);

    } else if (fieldNam == PieceField.location) {
    //  subPiece = new NSEPiece[1];
    String[] location = fieldValue.split("\\.\\.");
    piece.geneticPiece.start = Integer.valueOf(location[0]);
    piece.geneticPiece.end = Integer.valueOf(location[1]);


    } else if (fieldNam == PieceField.thickEnd) {
    NSEPiece subPieceThick = (NSEPiece) ((NSEPiece) piece).subPieceList.lastElement();
    subPieceThick.geneticPiece.end = Integer.parseInt(fieldValue);
    subPieceThick.viewPiece.color = thickColor;
    } else if (fieldNam == PieceField.blockCount) {
    // subPiece = (NSEPiece) cls.newInstance();
    subPiece = new NSEPiece[Integer.parseInt(fieldValue)];
    } else if (fieldNam == PieceField.blockSizes) {
    String[] blockLen = fieldValue.split(",");
    for (int m = 0; m < subPiece.length; m++) {
    subPiece[m] = new NSEPiece();
    subPiece[m].geneticPiece.strand = piece.geneticPiece.strand;
    subPiece[m].geneticPiece.end = Integer.parseInt(blockLen[m]);

    }
    } else if (fieldNam == PieceField.blockStarts) {
    String[] blockStart = fieldValue.split(",");
    for (int m = 0; m < subPiece.length; m++) {
    subPiece[m].geneticPiece.start = Integer.parseInt(blockStart[m]) + piece.geneticPiece.start;
    subPiece[m].geneticPiece.end = subPiece[m].geneticPiece.end + subPiece[m].geneticPiece.start - 1;

    subPiece[m].viewPiece.color = blockColor;
    ((NSEPiece) piece).subPieceList.add(subPiece[m]);
    }
    } //else if (RefelectUtil.containField(fieldNam, piece.geneticPiece.getClass())) {
    // Field field = piece.geneticPiece.getClass().getField(fieldNam);
    // field.set(piece.geneticPiece, RefelectUtil.constructFromString(RefelectUtil.getUnderlyingType(field), fieldValue));
    //  }
    else {
    Field field = piece.filePiece.getClass().getField(fieldNam.name());
    field.set(piece.filePiece, RefelectUtil.constructFromString(RefelectUtil.getUnderlyingType(field), fieldValue));
    }
    } catch (Exception ex) {
    ReportInfo.reportError("", ex);
    }
    }

    //把 类似gff group field 的key value 转换成HashMap
    public static HashMap<String, String> getGroupTwo(String groupStart, String groupEnd) {
    String[] groupStartElements = groupStart.split(":");
    String[] groupEndElements = groupEnd.split(":");
    HashMap<String, String> hm = new HashMap();
    for (int i = 0; i < groupStartElements.length; i++) {
    hm.put(groupStartElements[i], groupEndElements[i]);
    }
    return hm;
    }*/

    public void paint(Graphics2D g) {
//        synchronized (blocks_in_window) {
        if (currentPieces == null) {
            return;
        }
        for (Piece piece : this.currentPieces) {

            paintImplement(g, piece);
        }
        if (Log.instance().annoPara.showTag) {
            // System.out.println("--------cshowTag--");
            Font NAME_FONT = MagicFrame.instance.annoAssemblyPanel.annotationPanel.tagFont;
            tag = new Tag(trackSet.name, new Point(-100, -100), NAME_FONT, MagicFrame.instance.annoAssemblyPanel.getFontMetrics(NAME_FONT));
            setTagLocation();
            paintTag(g);
        } else {
            //System.out.println("--------no show--");
        }

        // }
    }

    public boolean isInAlnDisplay(Piece piece) {
        if (piece.viewPiece.x2 < MagicFrame.instance.alignAssemblyPanel.dx || piece.viewPiece.x1 > MagicFrame.instance.alignAssemblyPanel.dx + MagicFrame.instance.alignAssemblyPanel.getWidth()) {
            return false;
        } else if (piece.viewPiece.y2 < MagicFrame.instance.alignAssemblyPanel.dy || piece.viewPiece.y1 > MagicFrame.instance.alignAssemblyPanel.dy + MagicFrame.instance.alignAssemblyPanel.getWidth()) {
        }
        return true;
    }

    public boolean isInAnoDisplay(Piece piece) {
        if (piece.geneticPiece.end < MagicFrame.instance.annoPosionStart || piece.geneticPiece.start > MagicFrame.instance.annoPosionEnd) {
            return false;
        }
        return true;
    }

    public void paintSelect(Graphics2D g, Piece piece) {
        g.setColor(selectColor);
        Shape shape = getShape(piece.geneticPiece, piece.viewPiece.vertical);
        if (shape != null) {
            Rectangle rect = shape.getBounds();
            Rectangle bg_rect = new Rectangle(rect.x - 2, rect.y - 2, rect.width + 4, rect.height + 4);
            Area bg_area = new Area(bg_rect);
            bg_area.subtract(new Area(rect));
            g.fill(bg_area);
        }
    }

    public void paintImplement(Graphics2D g, Piece piece) {
        Shape shape = getShape(piece.geneticPiece, piece.viewPiece.vertical);
        if (shape == null) {
            return;
        }
        if (trackSet.COLOR != null) {
            g.setColor(trackSet.COLOR);
        }
        g.fill(shape);
    }

    public void paitTrack(Graphics2D g, int dx, int width) {
        paintBaseLine(g, 0, width);
        paintMiddleLine(g, 0, width);
    }

    public void paintMiddleLine(Graphics2D g, int dx, int width) {
        // //System.out.println("paintBaseLine  -----------------------------");
        g.setColor(ColorRep.GrayColor[7]);
        g.drawLine(dx, this.middleY, width, this.middleY);
    }

    public void paintInit() {
    }

    public void paintBaseLine(Graphics2D g, int dx, int width) {
        g.setColor(ColorRep.GrayColor[6]);
        g.drawLine(dx, this.middleY + this.trackSet.trackHeight / 2 - 1, width, this.middleY + this.trackSet.trackHeight / 2 - 1);
    }

    public Shape getShape(GeneticPiece geneticPiece, int vertical) {

        int x2 = (geneticPiece.end - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM;
        int x1 = (geneticPiece.start - MagicFrame.instance.annoPosionStart) >> Log.instance().annoPara.ZOOM;

        int width = (x2 - x1);
        if (width < 1) {
            width = 1;
        }
        int x_site = x1;
        int y_site = 0;
        if (Log.instance().annoPara.VIEW_MODE.equals(StringRep.TRIM)) {
            if (vertical > 1) {
                return null;
            }
        }
        if (Log.instance().annoPara.VIEW_MODE.equals(StringRep.SINGLE_LINE)) {
            y_site = this.middleY + this.trackSet.trackHeight / 2 - this.trackSet.pieceHeight - 1;
        } else if (Log.instance().annoPara.VIEW_MODE.equals(StringRep.NONOVERLAP)) {
            if (geneticPiece.strand) {
                y_site = this.middleY - (this.trackSet.pieceHeight + this.trackSet.pieceInterval) * (vertical + 1) - 1;
            } else {
                y_site = this.middleY + (this.trackSet.pieceHeight + this.trackSet.pieceInterval) * vertical + this.trackSet.pieceInterval + 1;
            }
        }
        if (y_site + this.trackSet.pieceHeight / 2 < this.middleY - this.trackSet.trackHeight / 2) {
            //   //System.out.println(y_site + "       " + this.SITE + "  ========================   " + this.trackSet.ROW_HEIGHT);
            return null;
        }
        if (y_site - this.trackSet.pieceHeight / 2 > this.middleY + this.trackSet.trackHeight / 2) {
            //  //System.out.println(y_site + "    " + this.SITE + "   --------------------     " + this.trackSet.ROW_HEIGHT + "     " + piece.viewPiece.vertical + "   " + piece.geneticPiece.id);
            return null;
        }

        return (Shape) new Rectangle(x_site, y_site, width, this.trackSet.pieceHeight);
    }

    public void setTrackViewPropoery() {
        if (currentPieces == null) {
            return;
        }
        for (Piece piece : currentPieces) {
            setPieceViewPropoery(piece);
        }

    }

    public void setPieceViewPropoery(Piece piece) {
    }
}
