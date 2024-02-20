/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.swing;

import Magic.Units.File.Parameter.ForEverStatic;
import Magic.Units.File.Parameter.Log;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import utils.ReportInfo;

/**
 *
 * @author Administrator
 */
public class FileChoose {

    private int dialogType;
    private String path;
    private String title;
    private String fileTypes[];
    private JFrame parent;
    //  private boolean mltiSelectionEnabled = false;

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }

    public void setFileTypes(String[] fileTypes) {
        this.fileTypes = fileTypes;
    }

    //  public void setMltiSelectionEnabled(boolean mltiSelectionEnabled) {
    // this.mltiSelectionEnabled = mltiSelectionEnabled;
    //  }
    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FileChoose(int dialogType, String path, String title, final String fileTypes[], JFrame parent) {
        this.dialogType = dialogType;
        this.path = path;
        this.title = title;
        this.fileTypes = fileTypes;
        this.parent = parent;
    }

    public String getFileNameImplement() {
        String name = null;
        try {
            JFileChooser filechooser = new JFileChooser();

            filechooser.setCurrentDirectory(new File(path));
            // filechooser.setMultiSelectionEnabled(mltiSelectionEnabled);
            if (title != null) {
                filechooser.setDialogTitle(title);
                filechooser.setDialogType(dialogType);
            }

            if (fileTypes != null) {
                for (final String type : fileTypes) {

                    filechooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

                        String filetype2[] = type.split("\\|");

                        public boolean accept(File f) {
                            for (String type2 : filetype2) {
                                if (f.getName().matches(".+\\." + type2) || f.isDirectory()) {
                                    return true;
                                }
                            }

                            return false;
                        }

                        public String getDescription() {
                            return "*." + type.replace("|", " or *.");
                        }
                    });
                }
            }
            int answer = 0;
            if (dialogType == JFileChooser.OPEN_DIALOG) {
                answer = filechooser.showOpenDialog(parent);
            } else {
                answer = filechooser.showSaveDialog(parent);
            }
            switch (answer) {
                case JFileChooser.APPROVE_OPTION:
                    break;
                case JFileChooser.CANCEL_OPTION:
                    return null;
            }
            File file = filechooser.getSelectedFile();
            if (file == null) {
                return null;
            }
            name = file.getAbsolutePath();
            ForEverStatic.CURRENT_PATH = file.getPath();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        return name;
    }

    public String[] getFileNamesImplement() {
        String[] name = null;
        try {
            JFileChooser filechooser = new JFileChooser();

            filechooser.setCurrentDirectory(new File(path));
             filechooser.setMultiSelectionEnabled(true);
            if (title != null) {
                filechooser.setDialogTitle(title);
                filechooser.setDialogType(dialogType);

            }
            if (fileTypes != null) {
                for (final String type : fileTypes) {

                    filechooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

                        String filetype2[] = type.split("\\|");

                        public boolean accept(File f) {
                            for (String type2 : filetype2) {
                                if (f.getName().matches(".+\\." + type2) || f.isDirectory()) {
                                    return true;
                                }
                            }

                            return false;
                        }

                        public String getDescription() {
                            return "*." + type.replace("|", " or *.");
                        }
                    });
                }
            }
            int answer = 0;
            if (dialogType == JFileChooser.OPEN_DIALOG) {
                answer = filechooser.showOpenDialog(parent);
            } else {
                answer = filechooser.showSaveDialog(parent);
            }
            switch (answer) {
                case JFileChooser.APPROVE_OPTION:
                    break;
                case JFileChooser.CANCEL_OPTION:
                    return null;
            }
            File[] files = filechooser.getSelectedFiles();
            if (files == null) {
                return null;
            }
            if (files.length == 0) {
                System.out.println("files.length==0");
                return null;
            }

            name = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                name[i] = files[i].getAbsolutePath();
            }

            ForEverStatic.CURRENT_PATH = files[0].getPath();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }

        return name;
    }
}
