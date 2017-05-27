package Package2;

import javafx.geometry.Point3D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;
import p4.SplitPoints;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Егор on 12.11.2016.
 */
public class MyGraphic extends ApplicationFrame {
    public static HashMap<Double,Double> FMap  = new HashMap<>();
    public static int listMapSizeofData = 0;
    public MyGraphic(String title) {

        super(title);

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
        setContentPane(chartPanel);
    }
    private XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("points");
        ArrayList<ArrayList<Point3D>>  listOfList = SplitPoints.splitPoints(7,1,p4.ReadData.readData());

        int i=1;
        for(ArrayList<Point3D> a:listOfList){
            double mas[] = p4.MultiRegress.regress(a);
            series1.add((double) i,mas[2]);
            i++;
        }
        dataset.addSeries(series1);
        /*HashMap<Double,Double> mapPoints = Package2.ReadData.readData();
        for(Map.Entry<Double,Double> map : mapPoints.entrySet()){
            series1.add(map.getKey(),map.getValue());
        }
        dataset.addSeries(series1);

        ArrayList<HashMap<Double,Double>> listMaps = SplitMap.splitMap(7,1,mapPoints);
        listMapSizeofData=listMaps.size();
        double[] dXY;


        XYSeries series2;
        for(int i = 0;i<listMaps.size();i++){
          series2 = new XYSeries(i);
         dXY = Regress.regressAnalys(listMaps.get(i),0);
            series2.add(dXY[0],dXY[2]);
            series2.add(dXY[1],dXY[3]);
            dataset.addSeries(series2);

        }

        XYSeries series3;
        for(int i = 0;i<listMaps.size();i++){
            series3 = new XYSeries(i+listMaps.size()+1);
            dXY = Regress.regressAnalys(listMaps.get(i),0);
            series3.add(dXY[4],dXY[5]);
            dataset.addSeries(series3);
        }*/

       /* for(Map.Entry<Double,Double> m: FMap.entrySet()){
            series3 = new XYSeries(m.getKey());
            series3.add(m.getKey(),m.getValue());
            dataset.addSeries(series3);
        }*/
        /*double[] dd = Regress.regressAnalys(mapPoints);
        XYSeries series2 = new XYSeries("points2");
        series2.add(6.0,dd[0]);
        series2.add(200.0,dd[1]);
        dataset.addSeries(series2);*/

        return dataset;

    }

    private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Mygraph",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                false,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);

        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();// задаем стиль точкам
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0,Color.blue,true);


        for(int i =1;i<=listMapSizeofData;i++){
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
            renderer.setSeriesPaint(i,Color.red,true);
        }

       /* for(int i =listMapSizeofData;i<1000;i++){
            renderer.setSeriesLinesVisible(i, false);
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesPaint(i,Color.black,true);
        }*/
      plot.setRenderer(renderer);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;

    }
    public static void main(final String[] args) {

        final MyGraphic demo = new MyGraphic("MyGraphic");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}
