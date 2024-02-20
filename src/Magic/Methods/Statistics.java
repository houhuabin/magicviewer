/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Magic.Methods;

import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author QIJ
 */
public class Statistics {

    public static int getMin(int[] data)
    {
        if(data==null || data.length==0) return 0;
        int min=Integer.MAX_VALUE;
        for(int i=0;i<data.length;i++)
        {
            if(min>data[i]) min=data[i];
        }
        return min;
    }

    public static int getMax(int[] data)
    {
        if(data==null || data.length==0) return 0;
        int max=0;
        for(int i=0;i<data.length;i++)
        {
            if(max<data[i]) max=data[i];
        }
        return max;
    }

    public static double getMean(int[] data)
    {
        if(data==null || data.length==0) return 0;
        double mean=0;
        for(int i=0;i<data.length;i++)
        {
            mean+=data[i];
        }
        return mean/data.length;
    }

    public static double getDev(int[] data)
    {
        if(data==null || data.length==0) return 0;
        double mean=getMean(data);
        double dev=0;
        for(int i=0;i<data.length;i++)
        {
            dev+=(data[i]-mean)*(data[i]-mean);
        }
        return Math.sqrt(dev/data.length);
    }

    public static double getMin(double[] data)
    {
        if(data==null || data.length==0) return 0;
        double min=Integer.MAX_VALUE;
        for(int i=0;i<data.length;i++)
        {
            if(min>data[i]) min=data[i];
        }
        return min;
    }

    public static double getMax(double[] data)
    {
        if(data==null || data.length==0) return 0;
        double max=0;
        for(int i=0;i<data.length;i++)
        {
            if(max<data[i]) max=data[i];
        }
        return max;
    }

    public static double getMean(double[] data)
    {
        if(data==null || data.length==0) return 0;
        double mean=0;
        for(int i=0;i<data.length;i++)
        {
            mean+=data[i];
        }
        return mean/data.length;
    }



    public static double getDev(double[] data)
    {
        if(data==null || data.length==0) return 0;
        double mean=getMean(data);
        double dev=0;
        for(int i=0;i<data.length;i++)
        {
            dev+=(data[i]-mean)*(data[i]-mean);
        }
        return Math.sqrt(dev/data.length);
    }

    public static int getMinFromHist(Vector<Point> hist)
    {
        if(hist==null || hist.size()==0) return 0;
        int min=Integer.MAX_VALUE;
        for(int i=0;i<hist.size();i++)
        {
            if(min>hist.elementAt(i).x) min=hist.elementAt(i).x;
        }
        return min;
    }

    public static int getMaxFromHist(Vector<Point> hist)
    {
        if(hist==null || hist.size()==0) return 0;
        int max=0;
        for(int i=0;i<hist.size();i++)
        {
            if(max<hist.elementAt(i).x) max=hist.elementAt(i).x;
        }
        return max;
    }

    public static double getMeanFromHist(Vector<Point> hist)
    {
        if(hist==null || hist.size()==0) return 0;
        double mean=0;
        double sum=0;
        for(int i=0;i<hist.size();i++)
        {
            mean+=hist.elementAt(i).x*hist.elementAt(i).getY();
            sum+=hist.elementAt(i).getY();
        }
        return mean/sum;
    }

    public static double getDevFromHist(Vector<Point> hist)
    {
        if(hist==null || hist.size()==0) return 0;
        double mean=getMeanFromHist(hist);
        double dev=0;
        double sum=0;
        for(int i=0;i<hist.size();i++)
        {
            dev+=(hist.elementAt(i).x-mean)*(hist.elementAt(i).x-mean)*hist.elementAt(i).getY();
            sum+=hist.elementAt(i).getY();
        }
        return Math.sqrt(dev/sum);
    }
}
