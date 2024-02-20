/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * 使用JFreeChart绘制饼图
 * @author qiujy
 */
public class PieChartTest {

    /**
     * step1:创建数据集对象
     * @return
     */
    public static PieDataset createDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("java程序设计语言", 10000);
        dataset.setValue("JSP基础与案例开发详解", 20000);
        dataset.setValue("struts基础与案例开发详解", 30000);
        dataset.setValue("精通JSF", 40000);

        return dataset;
    }

    /**
     * step2:创建图表
     * @param dataset
     * @return
     */
    public static JFreeChart createChart(PieDataset dataset) {
         JFreeChart chart = ChartFactory.createPieChart3D(
        //JFreeChart chart = ChartFactory.createPieChart(
                "原创图书销量统计", // 图表标题
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
    public static void drawToFrame(JFreeChart chart){
        //输出图表到Swing Frame
        ChartFrame frame = new ChartFrame("原创图书销量统计", chart);
        frame.pack();
        frame.setVisible(true);
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
            try {   fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     

   public static void main(String[] args) throws FileNotFoundException {
       // step1:创建数据集对象
       PieDataset dataset = createDataSet();

       // step2:创建图表
        JFreeChart chart = createChart(dataset);

        // step3: 输出图表到Swing窗口
        drawToFrame(chart);
       // step3: 输出图表到磁盘
       drawToOutputStream("D:\\mybook-pie.png", chart);
   }
}
