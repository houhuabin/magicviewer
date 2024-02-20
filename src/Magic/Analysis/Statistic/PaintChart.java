/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Statistic;

import Magic.Analysis.Merage.MergePara.SNPType;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.Units.Piece.Piece;
import Magic.Units.Track.SNPTrack;
import Magic.Units.Track.Track;
import Magic.WinMain.MagicFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JDialog;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import utils.ColectionUtil;
import utils.ReportInfo;
import utils.swing.SwingUtil;

/**
 *
 * @author Administrator
 */
public class PaintChart {

    /**
     * step1:创建数据集对象
     * @return
     */
    /* public static PieDataset createDataSet() {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("java程序设计语言", 10000);
    dataset.setValue("JSP基础与案例开发详解", 20000);
    dataset.setValue("struts基础与案例开发详解", 30000);
    dataset.setValue("精通JSF", 40000);

    return dataset;
    }*/
    public static PieDataset trackToDataSet(Track track) {


        Iterator<Piece> it = track.iteratorPieces.iterator();
        ArrayList<Integer> number = new ArrayList<Integer>(SNPType.values().length);
        for (int i = 0; i < SNPType.values().length; i++) {
            number.add(0);
        }

        while (it.hasNext()) {
            Piece piece = it.next();
            SNPType snpType = ((SNPPiece) piece.geneticPiece).snpType;
            int index = snpType.ordinal();
            number.set(index, number.get(index) + 1);
        }

        return getDatasetByColection(ColectionUtil.getString(SNPType.values()), number);
    }

    public static DefaultPieDataset getDatasetByColection(ArrayList<String> snpTypes, ArrayList<Integer> number) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int size = snpTypes.size();
        for (int i = 0; i < size; i++) {
            dataset.setValue(snpTypes.get(i), number.get(i));
        }

        return dataset;
    }

    /**
     * step2:创建图表
     * @param dataset
     * @return
     */
    public static JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(
                //JFreeChart chart = ChartFactory.createPieChart(
                title, // 图表标题
                dataset, // 数据集
                true, // 是否显示图例
                true, // 是否显示工具提示
                true // 是否生成URL
                );

        //设置标题字体==为了防止中文乱码：必须设置字体
        //  chart.setTitle(new TextTitle("原创图书销量统计", new Font("黑体", Font.ITALIC, 22)));
        //设置图例的字体==为了防止中文乱码：必须设置字体
        //  chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
        // 获取饼图的Plot对象(实际图表)
        PiePlot plot = (PiePlot) chart.getPlot();

        //图形边框颜色
        plot.setBaseSectionOutlinePaint(Color.GRAY);
        //图形边框粗细
        plot.setBaseSectionOutlineStroke(new BasicStroke(0.0f));
        //设置饼状图的绘制方向，可以按顺时针方向绘制，也可以按逆时针方向绘制
        plot.setDirection(Rotation.ANTICLOCKWISE);
        //设置绘制角度(图形旋转角度)
        plot.setStartAngle(70);
        //设置突出显示的数据块
        plot.setExplodePercent("One", 0.1D);
        //设置背景色透明度
        plot.setBackgroundAlpha(0.7F);
        // 设置前景色透明度
        plot.setForegroundAlpha(0.65F);
        //设置区块标签的字体==为了防止中文乱码：必须设置字体
        // plot.setLabelFont(new Font("隶书", Font.PLAIN, 12));
        // 扇区分离显示,对3D图不起效
        plot.setExplodePercent(dataset.getKey(3), 0.1D);
        // 图例显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),
                new DecimalFormat("0.00%")));
        // 图例显示百分比
        // plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        // 指定显示的饼图为：圆形(true) 还是椭圆形(false)
        plot.setCircular(true);
        // 没有数据的时候显示的内容
        plot.setNoDataMessage("No data...");

        //设置鼠标悬停提示
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        //设置热点链接
        //  plot.setURLGenerator(new StandardPieURLGenerator("detail.jsp"));

        return chart;
    }

    /**
     * [color=olive]step3: 输出图表到Swing Frame[/color]
     * @param chart
     */
    public static void drawToFrame(JFreeChart chart) {
        //输出图表到Swing Frame
        JDialog jd=new JDialog(MagicFrame.instance,false);
         jd.add(new ChartPanel(chart));
         jd.setSize(800, 600);
         SwingUtil.setLocation(jd);
        /*ChartFrame frame = new ChartFrame("MagicViewer Statistics", chart);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();
        frame.toFront();*/
    }

    /**
     * [color=olive]step3: 输出图表到指定的磁盘[/color]
     * @param destPath
     * @param chart
     */
    public static void drawToOutputStream(String destPath, JFreeChart chart) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destPath);
            // ChartUtilities.writeChartAsJPEG(
            ChartUtilities.writeChartAsPNG(fos, // 指定目标输出流
                    chart, // 图表对象
                    600, // 宽
                    400, // 高
                    null); // ChartRenderingInfo信息
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Track getSNPTrack() {

        for (Track track : MagicFrame.instance.dataRep.tracks) {
            if (track instanceof SNPTrack) {
                Piece piece=track.iteratorPieces.iterator().next();
                if(((SNPPiece)piece.geneticPiece).snpType!=null)
                {
                    return track;
                }
            }
        }

        return null;
    }

    public static void paint() {   // step1:创建数据集对象
        //  MagicFrame.instance.monitor = new StepProgress("Loading Annotation", StringRep.START, 1, null);
 Track track=getSNPTrack();
 if(track!=null){
        PieDataset dataset = trackToDataSet(track);

        // step2:创建图表
        JFreeChart chart = createChart(dataset, "SNP Type Statistics");

        // step3: 输出图表到Swing窗口
        drawToFrame(chart);
        // step3: 输出图表到磁盘
        //drawToOutputStream("D:\\mybook-pie.png", chart);
 }else
 {
    ReportInfo.reportInformation("SNP  track is null or SNP track is not annotationed!");
 }

    }

    public static void main(String[] args) throws FileNotFoundException {
        // step1:创建数据集对象
      /*  Track track = ForMaigc.getTrack("E:\\project\\magicinsight\\chrY\\chrY.sorted.clean.gff");
        PieDataset dataset = trackToDataSet(track);

        // step2:创建图表
        JFreeChart chart = createChart(dataset, "SNP Type Statistics");

        // step3: 输出图表到Swing窗口
        drawToFrame(chart);
        // step3: 输出图表到磁盘
        //drawToOutputStream("D:\\mybook-pie.png", chart);*/
    }
}
