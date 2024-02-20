/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class URLClassLoaderUtil {

    URLClassLoader classLoader = null;// URLClassLoader类载入器
    private String jarFileName;
    private boolean isFile = true;
    List<String> jars = new ArrayList<String>(0);

    public URLClassLoaderUtil(String jarFileName) {
        this.setJarFileName(jarFileName);
        this.inti();
    }

    public URLClassLoaderUtil(String jarFileName, boolean isFile) {
        this.setJarFileName(jarFileName);
        this.setFile(isFile);
        this.inti();
    }

    /**
     *初始化，读取文件信息，并将jar文件路径加入到classpath
     */
    private void inti() {
        // 添加jar文件路径到classpath
        if (this.isFile) {
            File f = new File(jarFileName);
            addPath(f.toURI().toString());
            jars.add(f.getAbsolutePath());
        } else {
            ReadJarFile df = new ReadJarFile(jarFileName, new String[]{"jar",
                        "zip"});
            this.jars = df.getFiles();
            List<URL> jarURLs = df.getFilesURL();
            URL[] URLs = jarURLs.toArray(new URL[0]);
            addPath(URLs);
        }
    }

    /**
     *回叫方法，class操作
     *
     *@paramcallBack
     */
    public void callBack(ClassCallBack callBack) {
        for (String s : this.jars) {
            loadClass(s, callBack);
        }
    }

    /**
     *添加单个jar路径到classpath
     *
     *@paramjarURL
     */
    private void addPath(String jarURL) {
        try {
            classLoader = new URLClassLoader(
                    new URL[]{new URL(jarURL)},
                    Thread.currentThread().getContextClassLoader());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     *添加jar路径到classpath
     *
     *@paramjarURLs
     */
    private void addPath(URL[] jarURLs) {
        /* URL[] urls = new URL[jarURLs.length];
        for (int i = 0; i < jarURLs.length; i++) {
        try {
        urls[i] = new URL(jarURLs[i].toString());
        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        }*/
        classLoader = new URLClassLoader(jarURLs,
                    Thread.currentThread().getContextClassLoader());
    }

    /**
     *动态载入class
     *
     *@paramjarFileName
     *@paramcallBack
     */
    private void loadClass(String jarFileName, ClassCallBack callBack) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> en = jarFile.entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            String name = je.getName();
            String s5 = name.replace('/', '.');
            if (s5.lastIndexOf(".class") > 0) {
                String className = je.getName().substring(0,
                        je.getName().length() - ".class".length()).replace('/',
                        '.');
                Class c = null;
                try {
                    c = this.classLoader.loadClass(className);
                    // System.out.println(className);
                } catch (ClassNotFoundException e) {
                    //System.out.println("NO CLASS: " + className+"-------"+je.getName());
                    // continue;
                } catch (NoClassDefFoundError e) {
                    // System.out.println("NO CLASS: " + className+"-------"+je.getName());
                    // continue;
                }
                if (callBack != null) {
                    callBack.operate(c);
                }
            }
        }
    }

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public boolean isFile() {
        return isFile;
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setFile(boolean isFile) {
        this.isFile = isFile;
    }
}


