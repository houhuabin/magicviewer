/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import java.io.*;
import java.net.*;
import java.lang.reflect.*;
import utils.SystemUtil;

/**
 *
 * @author lenovo
 */
public class Boot {

    public static void main(final String[] args) throws Exception {
        SystemUtil.addLibClassPath();
        MagicFrame.main(new String[0]);
        //SystemUtil.addLibClassPath();
        // check that the lib folder exists



        /*ClassLoader c1 = Thread.currentThread().getContextClassLoader();
        System.out.println("ClassLoader=" + c1);
        File libRoot = new File(LIB_FOLDER);
        if (!libRoot.exists()) {
        throw new Exception("No 'lib' folder exists!");
        }
        // read all *.jar files in the lib folder to array
        File[] libs = libRoot.listFiles(new FileFilter() {

        public boolean accept(File dir) {
        String name = dir.getName().toLowerCase();
        return name.endsWith("jar") || name.endsWith("zip");
        }
        });

        URL[] urls = new URL[libs.length];
        // fill the urls array with URLs to library files found in libRoot

        for (int i = 0; i < libs.length; i++) {
        // System.out.println(libs[i].getAbsolutePath());
        urls[i] = new URL("file", null, libs[i].getAbsolutePath());
        }
        // create a new classloader and use it to load our app.
        classLoader = new URLClassLoader(urls,
        Thread.currentThread().
        getContextClassLoader());
        // get the main method in our application to bring up the app.
        final Method mtd = classLoader.loadClass(APP_MAIN_CLASS).getMethod("main",
        new Class[]{String[].class});
        // Using thread to launch the main 'loop' so that the current Main method
        // can return while the app is starting
        new Thread(new Runnable() {

        public void run() {
        try {
        mtd.invoke(null, new Object[]{args});
        } // forward the args
        catch (Exception e) {
        throw new RuntimeException(e);
        }
        }
        }, "AppMain").start();
        // Give the app some time to start before returning from main.
        // This doesn't delay the starting in any way
        Thread.sleep(1000);*/

       /* URLClassLoaderUtil cloasload = new URLClassLoaderUtil("lib", false);
        cloasload.callBack(null);
        classLoader = cloasload.getClassLoader();
        System.out.println("boot class load________________"+classLoader);
        Class c1 = classLoader.loadClass(APP_MAIN_CLASS);
        System.out.println("c1_______________"+c1.getClassLoader());
        MagicFrame asm1 = (MagicFrame) c1.newInstance();
        asm1.start();
        System.out.println("asm1_______________"+asm1.getClass().getClassLoader());

        /* final Method mtd = classLoader.loadClass(APP_MAIN_CLASS).getMethod("main",
        new Class[]{String[].class});

        new Thread(new Runnable() {
        public void run() {
        try {
        mtd.invoke(null, new Object[]{args});
        } // forward the args
        catch (Exception e) {
        throw new RuntimeException(e);
        }
        }
        }, "AppMain").start();
        // Give the app some time to start before returning from main.
        // This doesn't delay the starting in any way
        Thread.sleep(1000);*/
    }
    private static final String LIB_FOLDER = "lib";
    private static final String APP_MAIN_CLASS = "Magic.WinMain.MagicFrame";
    private static ClassLoader classLoader;
}
