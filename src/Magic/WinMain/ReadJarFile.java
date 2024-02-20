/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class ReadJarFile {

    List<String> jars = new ArrayList<String>(0);
    List<URL> jarURLs = new ArrayList<URL>(0);

    public ReadJarFile(String jarFilePath, final String[] filters) {
        File libRoot = new File(jarFilePath);
        // read all *.jar files in the lib folder to array
        File[] libs = libRoot.listFiles(new FileFilter() {

            public boolean accept(File dir) {
                String name = dir.getName().toLowerCase();
                boolean isAccept = false;
                for (String filter : filters) {
                    isAccept = isAccept || name.endsWith(filter);
                }
                return isAccept;
            }
        });

        for (int i = 0; i < libs.length; i++) {
            try {
                jars.add(libs[i].getAbsolutePath());
                jarURLs.add(libs[i].toURI().toURL());
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<String> getFiles() {
        return jars;
    }

    public List<URL> getFilesURL() {
        return jarURLs;
    }
}
