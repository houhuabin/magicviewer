/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.swing;

import utils.swing.*;
import Magic.Units.File.Parameter.ForEverStatic;
import utils.*;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Gui.Windows.MagicPanel;
import Magic.Units.Piece.Piece;
import Magic.WinMain.AlignmentPanel;
import com.lowagie.text.pdf.DefaultFontMapper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.ListModel;
import org.broadinstitute.sting.utils.cmdLine.ArgumentDefinition;


/**
 *
 * @author Huabin Hou
 */
public class SwingUtil {
    public static ArrayList<String> getFilesByModel(ListModel listmodel) {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < listmodel.getSize(); i++) {
            names.add(listmodel.getElementAt(i).toString());
        }
        return names;
    }
    public static void openURL(String htmlUrl) {
        try {
            Desktop desktop = Desktop.getDesktop();

            URI uri = new URI(htmlUrl);
            desktop.browse(uri);
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public enum ComponentOrient {

        vertical, horizontal
    };

    public static void setComponentParral(javax.swing.GroupLayout layout, Component[] child, ComponentOrient ifHorital) {
        SequentialGroup sequentialGroup = layout.createSequentialGroup();
        for (int i = 0; i < child.length; i++) {
            sequentialGroup.addComponent(child[i]);
        }


        ParallelGroup parallelGroup = layout.createParallelGroup();
        for (int i = 0; i < child.length; i++) {
            parallelGroup.addComponent(child[i]);
        }

        if (ifHorital == ComponentOrient.vertical) {
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(parallelGroup));
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(sequentialGroup));
        } else {
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(parallelGroup));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(sequentialGroup));
        }
    }

    public static void copyToClipboard(String str) {
        StringSelection selection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    public static void copyPieceToClipboard(Piece piece) {
        copyToClipboard(piece.toString());

    }

    public void setNoEventJscrollValue(JScrollBar js, int value) {
        js.setValueIsAdjusting(true);
        js.setValue(value);
        js.setValueIsAdjusting(false);
    }

    public static void setLocation(Component com) {

        Dimension d = com.getSize();
        int x = (ForEverStatic.desktopBoundsWidth - d.width) / 2;     //计算
        int y = (ForEverStatic.desktopBoundsHeight - d.height) / 2;
        com.setLocation(x, y);       //为dialog设置位置
        com.setVisible(true);    // TODO add your handling code here:
    }

 /*   public static String getFastaFile(JFrame parent) {
        return SwingUtil.getOpenFileByChooser(null, new String[]{"fasta|fa|"}, parent);
    }*/
    //fileType: 每个getDescription对应fileTypes,一个fileTypes 可以是多个fileType,用竖线分割

    public static String getOpenFileByChooser(String title, final String fileTypes[], JFrame parent) {
        FileChoose fc=new FileChoose(JFileChooser.OPEN_DIALOG, ForEverStatic.CURRENT_PATH, title, fileTypes, parent);
        return fc.getFileNameImplement();
    }

     public static String[] getOpenFilesByChooser(String title, final String fileTypes[], JFrame parent) {
       FileChoose fc=new FileChoose(JFileChooser.OPEN_DIALOG, ForEverStatic.CURRENT_PATH, title, fileTypes, parent);     
      return fc.getFileNamesImplement();
    }

    //fileType: 每个getDescription对应fileTypes,一个fileTypes 可以是多个fileType,用竖线分割
    public static String getSaveFileByChooser(String title, final String fileTypes[], JFrame parent) {
        FileChoose fc=new FileChoose(JFileChooser.OPEN_DIALOG, ForEverStatic.CURRENT_PATH, title, fileTypes, parent);
        String name =fc.getFileNameImplement();// getFileNameImplement(JFileChooser.SAVE_DIALOG, ForEverStatic.CURRENT_PATH, title, fileTypes, parent);
        return processSaveFileType(name, fileTypes);
    }

   

    public static String processSaveFileType(String name, String fileTypes[]) {

        boolean ifHaveHouzui = false;
        for (String type : fileTypes) {
            if(name==null)
            {
               return null;
            }
            if (name.endsWith(type)) {
                ifHaveHouzui = true;
            }
        }
        if (!ifHaveHouzui) {
            name = name + "." + fileTypes[0];
        }
        return name;
    }

    public static JPanel getColorPanel(Color c, final String cls, final String method, final Object o) {
        JPanel panel = new JPanel();

        panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel.setBackground(c);
        panel.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                JPanel panel = (JPanel) evt.getSource();
                Color color = JColorChooser.showDialog(panel, "show", panel.getBackground());
                if (color != null) {
                    panel.setBackground(color);
                    String selectBase = "";
                    JTable table = (JTable) panel.getParent();
                    int colunmNum = 0;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 1; j < table.getColumnCount(); j++) {
                            if (table.getValueAt(i, j).equals(panel)) {
                                selectBase = (String) table.getValueAt(i, 0);
                                colunmNum = j;
                                break;
                            }
                        }

                    }
                    try {
                        Class c = Class.forName(cls);
                        Method m1 = c.getDeclaredMethod(method, String.class, Color.class, Integer.class);
                        m1.invoke(o, selectBase, color, colunmNum);
                    } catch (Exception ex) {
                        ReportInfo.reportError("", ex);
                    }

                }
            }
        });

        return panel;

    }

    public static void exportVerticalPanelsImage(MagicPanel[] annotationPanels, String filename) {

        int width = annotationPanels[0].getWidth();
        int height = 0;
        for (MagicPanel annotationPanel : annotationPanels) {

            height += annotationPanel.getHeight();

        }

        com.lowagie.text.Rectangle pagesize = new com.lowagie.text.Rectangle(width, height);
        com.lowagie.text.Document document = new com.lowagie.text.Document(pagesize, 0, 0, 0, 0);
        try {
            FileOutputStream fout = new FileOutputStream(new File(filename));
            com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(document, fout);
            document.addSubject("PDF");
            document.open();
            com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.pdf.PdfTemplate tp =
                    cb.createTemplate(width, height);
            Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
            for (MagicPanel annotationPanel : annotationPanels) {
                annotationPanel.paintImplement(g2);
                g2.translate(0, annotationPanel.getHeight());

            }

            g2.dispose();
            cb.addTemplate(tp, 0, 0);
            document.close();
            fout.close();
            // annotationPanel.dy = dy;
        } catch (Exception de) {
            ReportInfo.reportError(null, de);
        }
    }

    

    public static void setMaigicPanelScroll(MagicPanel annotationPanel, javax.swing.JScrollBar horiz1, javax.swing.JScrollBar ver1) {
        setMaigicPanelScrollImpl(annotationPanel, ver1);
        setMaigicPanelScrollImpl(annotationPanel, horiz1);
    }

    public static void setMaigicPanelScrollImpl(MagicPanel annotationPanel, javax.swing.JScrollBar ver1) {
        int panelScale = 0;
        int panelMax = 0;

        if (ver1.getOrientation() == javax.swing.JScrollBar.VERTICAL) {
            panelScale = annotationPanel.getHeight();
            panelMax = annotationPanel.getMaxHeight();
        } else {
            panelScale = annotationPanel.getWidth();
            panelMax = annotationPanel.getMaxWidth();

        }

        if (panelMax <= panelScale) {

            ver1.setVisible(false);
        } else {
            ver1.setVisible(true);
        }
        ver1.setVisible(true);

      //  System.out.println("panelMax:"+ panelMax);
      //  System.out.println("VisibleAmount:"+panelScale);
        ver1.setMaximum(Math.max(panelScale, panelMax));
        ver1.setBlockIncrement(Math.max(panelScale, panelMax));
        ver1.setVisibleAmount(panelScale);

        if (ver1.getOrientation() == javax.swing.JScrollBar.HORIZONTAL) {
            // System.out.println(panelMax+"---------------"+panelScale);
        }
        // ver1.setValue(annotationPanel.dx);
    }

    public static void setExtendScroll(MagicPanel annotationPanel, javax.swing.JScrollBar ver1) {
        int panelScale = 0;
        int panelMax = 0;

        if (ver1.getOrientation() == javax.swing.JScrollBar.VERTICAL) {
            panelScale = annotationPanel.getHeight();
            panelMax = annotationPanel.getMaxHeight();
        } else {
            panelScale = annotationPanel.getWidth();
            panelMax = annotationPanel.getMaxWidth();

        }

        if (panelMax <= panelScale) {

            ver1.setVisible(false);
            return;
        } else {
            ver1.setVisible(true);
        }
        ver1.setVisible(true);
        ver1.setMaximum(panelMax + panelScale);
        ver1.setBlockIncrement(panelScale);
        ver1.setVisibleAmount(panelScale);

        if (ver1.getOrientation() == javax.swing.JScrollBar.HORIZONTAL) {
            // System.out.println(panelMax+"---------------"+panelScale);
        }
        // ver1.setValue(annotationPanel.dx);
    }
/*
   public static void init(Class bean, Component component) {
        try {
            setAttribute(bean, component);
            setAttribute(org.broadinstitute.sting.gatk.GATKArgumentCollection.class, component);
        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
    }

    //给输入参数加上 setToolTipText等
    public static void setAttribute(Class matkMoudle, Component component) {
        try {

            FieldParsingEngine mcapGenoType = new FieldParsingEngine();
            mcapGenoType.addArgumentSource(matkMoudle);


            Iterator<ArgumentDefinition> it = mcapGenoType.iterator();
            while (it.hasNext()) {
                ArgumentDefinition ad = it.next();
                //  //System.out.println(ad.source.field.getName() + " ");
                String fieldName = ad.source.field.getName();


                if (RefelectUtil.containField(fieldName, component.getClass())) {

                    Field needSetField = component.getClass().getDeclaredField(fieldName.trim());
                    needSetField.setAccessible(true);
                    Object obj = needSetField.get(component);
                    Class clzz = obj.getClass();
                    //  setToolTipText
                    Method mSetToolTipText = clzz.getMethod("setToolTipText", String.class);
                    mSetToolTipText.invoke(obj, ad.doc);

                }

            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
    }

    public static ArrayList<String> getGATKArgumentString(Class matkMoudle, Component component) {
        ArrayList<String> result = new ArrayList();
        result.add("-U");
        result.add("-nt");
        result.add("4");
        result.addAll(generateArumentString(org.broadinstitute.sting.gatk.GATKArgumentCollection.class, component));
        result.add(" -T ");
        result.add(matkMoudle.getName().substring(matkMoudle.getName().lastIndexOf(".") + 1).replace("Walker", ""));
        result.addAll(generateArumentString(matkMoudle, component));
        return result;
    }

    public static ArrayList<String> getAnnotationArgumentString(Component component) {
        ArrayList<String> result = new ArrayList();
        result.add(" -U ");
        result.addAll(generateArumentString(org.broadinstitute.sting.gatk.GATKArgumentCollection.class, component));
        result.addAll(generateArgumentByComponent(component, "RodBind"));

        result.add(" -T  VariantAnnotator");
        result.addAll(generateArumentString(org.broadinstitute.sting.gatk.walkers.annotator.VariantAnnotator.class, component));
        result.addAll(getAnnotatorString(component));

        return result;
    }

    private static HashMap getAllAnnotations() {
        HashMap allAnnotations = new HashMap<String, VariantAnnotation>();
        List<Class<? extends VariantAnnotation>> annotationClasses = PackageUtils.getClassesImplementingInterface(VariantAnnotation.class);
        for (Class c : annotationClasses) {
            try {
                VariantAnnotation annot = (VariantAnnotation) c.newInstance();
                allAnnotations.put(c.getSimpleName().toUpperCase(), annot);
                //   System.out.println(c.getSimpleName().toUpperCase() + "---------------------------------");
                if (annot instanceof StandardVariantAnnotation) {
                    // System.out.println(c.getSimpleName().toUpperCase() + "-----================---StandardVariantAnnotation------=====================-----");
                }
            } catch (InstantiationException e) {
                throw new StingException(String.format("Cannot instantiate annotation class '%s': must be concrete class", c.getSimpleName()));
            } catch (IllegalAccessException e) {
                throw new StingException(String.format("Cannot instantiate annotation class '%s': must have no-arg constructor", c.getSimpleName()));
            }
        }
        return allAnnotations;
    }

    public static ArrayList<String> getAnnotatorString(Component component) {
        Field[] componentFields = component.getClass().getDeclaredFields();
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, VariantAnnotation> allAnnotations = getAllAnnotations();
        for (Field field : componentFields) {
            if (allAnnotations.containsKey(field.getName().toUpperCase())) {
                Boolean select = (Boolean) getValueIgnoreTypeByName(field.getName(), component);
                if (select) {
                    result.add("-A");
                    result.add(field.getName().trim());
                }
            }

        }
        return result;

    }

    public static String getVariantArgumentString(Component component) {
        String result = "-U ";
        result += generateArumentString(org.broadinstitute.sting.gatk.GATKArgumentCollection.class, component);
        result += generateArgumentByComponent(component, "RodBind");

        result += "-T  VariantFiltration";
        result += generateArumentString(org.broadinstitute.sting.gatk.walkers.filters.VariantFiltrationWalker.class, component);
        result += generateArgumentByComponent(component, "Feature");
        return result;

    }

    public static ArrayList<String> generateArgumentByComponent(Component component, String type) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Field[] componentFields = component.getClass().getDeclaredFields();
            for (Field field : componentFields) {
                if (type.equals("RodBind")) {
                    result.addAll(generateRodBindArumentString(field, component));
                } else if (type.equals("Feature")) {
                    // result.addAll(generateFeatureArumentString(field, component));
                }

            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return result;

    }

    //根据JCheckBox 得到选中的Enum，要求JCheckBox 的名字==Enum的值
    public static Vector<Enum> setJCheckBoxParaByComponent(Component component, Class enmuClazz) {

        Vector<Enum> result = new Vector<Enum>();
        try {
            Hashtable selectsFields = getFieldsValueByComponent(component, javax.swing.JCheckBox.class);
            for (Iterator it = selectsFields.keySet().iterator(); it.hasNext();) {
                String selectField = (String) it.next();
                Boolean select = (Boolean) selectsFields.get(selectField);
                if (select) {
                    Enum obj = Enum.valueOf(enmuClazz, selectField);
                    result.add(obj);
                }
            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return result;
    }

    //得到某一Component中所有某一类型的数据，比如得到所有选中的JCheckBox
    public static Hashtable getFieldsValueByComponent(Component component, Class enmuClazz) {
        Hashtable result = new Hashtable();

        return getFieldsValueByComponent(component, enmuClazz, result);
    }*/

    public static boolean validateJTextNull(Component component) {
        return validateComponent(component, javax.swing.JTextField.class, "isNotNull", "is null");
    }

    public static boolean validateComponent(Component component, Class clazz, String method, String err) {
        try {
            Field[] componentFields = component.getClass().getDeclaredFields();
            for (Field field : componentFields) {
                if (field.getType().equals(clazz)) {
                    Object value = getValueIgnoreType(field, component);
                    Class c = FormatCheck.class;
                    Method m1 = c.getDeclaredMethod(method, Object.class);
                    Boolean check = (Boolean) m1.invoke(null, value);
                    if (!check) {
                        ReportInfo.reportValidate("The  " + field.getName() + " " + err + ", please check!");
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return true;
    }

    //得到某一Component中所有某一类型的数据，比如得到所有选中的JCheckBox
    public static Hashtable getFieldsValueByComponent(Component component, Class enmuClazz, Hashtable result) {

        try {
            Field[] componentFields = component.getClass().getDeclaredFields();
            for (Field field : componentFields) {
                if (field.getType().equals(enmuClazz)) {
                    Object value = getValueIgnoreType(field, component);
                    result.put(field.getName(), value);
                }
            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return result;

    }

    public static ArrayList<String> generateRodBindArumentString(Field field, Component component) {
        ArrayList<String> result = new ArrayList<String>();
        try {

            if (field.getName().equals("Variants")) {
                String value = (String) getValueIgnoreType(field, component);
                result.add("-B");
                result.add("variant,Variants," + value.trim());
            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return result;

    }

    /* public static ArrayList<String> generateFeatureArumentString(Field field, Component component) {
    ArrayList<String> result = new ArrayList<String>();
    try {

    String name = field.getName();

    if (RefelectUtil.isThisField(field, javax.swing.JCheckBox.class) && name.startsWith("Feature_")) {
    Boolean select = (Boolean) getValueIgnoreType(field, component);
    if (!select) {
    name = name.replace("Feature_", "");
    result.add("-X");
    result.add(name);

    Field[] searchFields = component.getClass().getDeclaredFields();

    int paranum = 0;
    for (Field searchField : searchFields) {
    if (searchField.getName().startsWith(name + "_")) {
    paranum++;
    String value = (String) getValueIgnoreType(searchField, component);

    if (value != null && !value.equals("")) {

    if (paranum == 1) {

    result += ":" + searchField.getName().replace(name + "_", "") + "=" + value;
    } else {
    result += "," + searchField.getName().replace(name + "_", "") + "=" + value;
    }
    }

    }

    }


    }

    }

    } catch (Exception ex) {
    ReportInfo.reportError(null, ex);
    }
    return result;

    }
     
    public static ArrayList<String> generateArumentString(Class matkMoudle, Component component) {
        ArrayList<String> result = new ArrayList<String>();
        try {

            FieldParsingEngine mcapGenoType = new FieldParsingEngine();
            mcapGenoType.addArgumentSource(matkMoudle);

            Iterator<ArgumentDefinition> it = mcapGenoType.iterator();
            while (it.hasNext()) {
                ArgumentDefinition ad = it.next();
                // System.out.println(ad.source.field.getName() + "-------ad.source.field.getName()------------");
                String fieldName = ad.source.field.getName();
                String shortName = ad.shortName;



                if (RefelectUtil.containDeclareField(fieldName, component.getClass())) {
                    Field needSetField = component.getClass().getDeclaredField(ad.source.field.getName());
                    //   System.out.println(needSetField.getName()+"  ===============");
                    if (RefelectUtil.isBooleanField(ad.source.field)) {

                        Boolean select = (Boolean) getValueIgnoreTypeByName(ad.source.field.getName(), component);
                        if (select) {
                            result.add("-" + shortName.trim());
                        }
                    } else {

                        String value = (String) getValueIgnoreTypeByName(ad.source.field.getName(), component);
                        needSetField.setAccessible(true);
                        if (value != null && !value.equals("")) {
                            // value=value.trim().replace(" ", "%20");
                            result.add("-" + shortName);
                            result.add(value.trim());
                            // System.out.println(result+"-------"+value);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return result;

    }
*/
  /*  public static boolean validateField(Class matkMoudle, Component component) {
        try {
            FieldParsingEngine parser = new FieldParsingEngine();
            parser.addArgumentSource(matkMoudle);

            Iterator<ArgumentDefinition> it = parser.iterator();
            while (it.hasNext()) {
                ArgumentDefinition ad = it.next();

                if (RefelectUtil.containField(ad.source.field.getName(), component.getClass())) {

                    if (RefelectUtil.isTextField(ad.source.field)) {
                        Object value = (String) getValueIgnoreTypeByName(ad.source.field.getName(), component);
                        if (!validateRequiredField(ad, value)) {
                            return false;
                        }
                        if (!validateNumField(ad, value)) {
                            return false;
                        }
                        if (!validateFileField(ad, value)) {
                            return false;
                        }
                    }
                    // }
                }

            }

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }

        return true;
    }
*/
    public static boolean validateRequiredField(ArgumentDefinition ad, Object value) {

        if (ad.required && (value == null || value.equals(""))) {
            ReportInfo.reportValidate("Please input " + ad.source.field.getName());
            return false;
        }
        return true;

    }

    public static boolean validateNumField(final ArgumentDefinition ad, Object value) {
        if (!RefelectUtil.isCollectionField(ad.source.field)) {
            if (RefelectUtil.isNumField(ad.source.field)) {
                if (!FormatCheck.isNumber((String) value)) {
                    ReportInfo.reportValidate("The " + ad.fullName + " must be a number");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateFileField(final ArgumentDefinition ad, Object value) {
        if (!RefelectUtil.isCollectionField(ad.source.field)) {
            if (RefelectUtil.isFileField(ad.source.field)) {
                if (!FormatCheck.isFileName((String) value)) {
                    ReportInfo.reportValidate("The " + ad.fullName + " must be a file");
                    return false;
                }
            }

        }
        return true;
    }

    public static Object getValueIgnoreTypeByName(String componentFieldName, Component component) {
        try {
            Field componentField = component.getClass().getDeclaredField(componentFieldName);

            return getValueIgnoreType(componentField, component);

        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return null;
    }

    public static Object getValueIgnoreType(Field componentField, Component component) {
        try {


            componentField.setAccessible(true);
            Object obj = componentField.get(component);
            //     //System.out.println(componentField.getType() + "---------" + componentField.getName());
            if (componentField.getType().equals(javax.swing.JCheckBox.class)) {
                Class clzz = obj.getClass();
                Method m1 = clzz.getMethod("isSelected");
                //   //System.out.println(m1.invoke(obj) + "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return (Boolean) m1.invoke(obj);

            } else if (componentField.getType().equals(javax.swing.JComboBox.class)) {
                Class clzz = obj.getClass();
                Method m1 = clzz.getMethod("getSelectedItem");
                return (String) m1.invoke(obj);

            } else if (RefelectUtil.containMethod("getText", obj.getClass())) {
                Class clzz = obj.getClass();
                Method m1 = clzz.getMethod("getText");
                return (String) m1.invoke(obj);
            }


        } catch (Exception ex) {
            ReportInfo.reportError(null, ex);
        }
        return null;
    }

    public static void exclusiveOf() {
    }

    public static void main(String[] argv) {
       // getAllAnnotations();
    }
}
