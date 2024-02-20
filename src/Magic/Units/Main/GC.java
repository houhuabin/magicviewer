/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Units.Main;

import Magic.Units.Color.ColorRep;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Huabin Hou
 */
public class GC {

     public int GC_WIN_LEN = 400;
    public int GC_WIN_STEP = 200;
    public  char[] genome;

    public GC(int GC_WIN_LEN,int GC_WIN_STEP,char[] genome) {
        this.GC_WIN_LEN=GC_WIN_LEN;
        this.GC_WIN_STEP= GC_WIN_STEP;
        this.genome = genome;
    }
       public GC(int GC_WIN_LEN,char[] genome) {
        this.GC_WIN_LEN=GC_WIN_LEN;
        this.genome = genome;
    }


      public void paint(Graphics2D g) {
        ////System.out.println("print hist");

        Shape shape = getShape(getGCContentHist());
        g.setColor(ColorRep.red);
        g.fill(shape);
    }

       public Shape getShape(double[] hist) {
        int x1, y1;
        x1 = y1 = 0;

        int radius = 100;
        double angle = 0;

        Polygon shape = new Polygon();
        //Point start=new Point();
        for (int j = 0; j < hist.length; j++) {
            angle = j * 2 * Math.PI / hist.length;

            x1 = j * 10;
          
            y1 = (int) (hist[j] * 100 + 110);
             shape.addPoint(x1, y1);
        }

        return shape;
    }



    public double[] getGCContentHist() {
      
        int num = (genome.length ) / GC_WIN_LEN;
        if (num < 1) {
            num = 1;
        }
        //System.out.println(num + " getGCContentHist");
        double[] hist = new double[num];

        for (int i = 0; i < hist.length; i++) {
            hist[i] = 0;
        }
        int site, start, end, count;
        site = start = end = count  = 0;
        for (int i = 0; i < hist.length; i++) {
         
            start =  i * GC_WIN_LEN;
            end = start + GC_WIN_LEN-1;
            if (end >= genome.length) {
                end = genome.length - 1;
            }
            count = 0;
            for (int j = start; j <= end; j++) {
               
                if (genome[j] == 'G' || genome[j] == 'g' || genome[j] == 'C' || genome[j] == 'c') {
                    count++;
                }
            }

            hist[i] = 1.0 * count / (end - start + 1);           
        }
          subtractMiddle4Hist(hist);
        normalize4Hist(hist);
        return hist;
    }

    public double[] getGCSkewHist() {
       
        int num = (genome.length - GC_WIN_LEN * 2) / GC_WIN_STEP;
        if (num < 1) {
            num = 1;
        }
        //System.out.println(num);
        double[] hist = new double[num];

        for (int i = 0; i < hist.length; i++) {
            hist[i] = 0;
        }
        int site, start, end, count, g_num;
        site = start = end = count = g_num = 0;
        for (int i = 0; i < hist.length; i++) {
            site = GC_WIN_LEN + i * GC_WIN_STEP;
            start = site - GC_WIN_LEN;
            end = site + GC_WIN_LEN;
            if (start < 0) {
                start = 0;
            }
            if (end >= genome.length) {
                end = genome.length - 1;
            }
            count = 0;
            g_num = 0;
            for (int j = start; j <= end; j++) {
                if (genome[j] == 'G' || genome[j] == 'g') {
                    g_num++;
                }
                if (genome[j] == 'G' || genome[j] == 'g' || genome[j] == 'C' || genome[j] == 'c') {
                    count++;
                }
            }
            if (count > 0) {
                hist[i] = 1.0 * (g_num - (count - g_num)) / count;
            }
        }
           
        subtractMiddle4Hist(hist);
        normalize4Hist(hist);
        return hist;
    }

    public void subtractMiddle4Hist(double[] hist) {
        double middle = 0;
        for (int i = 0; i < hist.length; i++) {
            middle += hist[i];
        }
        middle /= hist.length;
        for (int i = 0; i < hist.length; i++) {
            hist[i] -= middle;
        }
    }

    public void normalize4Hist(double[] hist) {
        double max = 0;
        for (int i = 0; i < hist.length; i++) {
            if (max < hist[i]) {
                max = hist[i];
            }
        }
        for (int i = 0; i < hist.length; i++) {
            hist[i] /= max;
        }
    }



}
