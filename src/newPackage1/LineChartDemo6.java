package newPackage1;

import java.awt.*;
import java.util.ArrayList;

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

import static javax.swing.UIManager.get;
//import org.jfree.ui.Spacer;

/**
 * Created by Егор on 08.11.2016.
 */
public class LineChartDemo6  extends  ApplicationFrame{
    public LineChartDemo6(String title) {

        super(title);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
        setContentPane(chartPanel);

    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private XYDataset createDataset() {
        XYSeries series1 = new XYSeries("First");
        XYSeriesCollection dataset = new XYSeriesCollection();
        ArrayList<Double> listOfData = Data.getData();
        double sh = 1.0;
        for(Double x:listOfData){
            series1.add(sh,x);
            sh++;
        }
        dataset.addSeries(series1);


        ArrayList<String> listOfName = new ArrayList<>();
        for(int i=0;i<200-49;i++){
            listOfName.add("series"+String.valueOf(i+3));
        }


        final XYSeries series2 = new XYSeries("Second");
        double d[] = Regres.getRegres(1.0,1.0,listOfData,20,0);
        series2.add(1.0,d[0]);
        series2.add(200.0,d[1]);
        dataset.addSeries(series2);

        XYSeries series3 = new XYSeries(listOfData.get(0)); // создание ножествва прмямых, которые являются касательными к функции F(x)
        d = Regres.getRegres(1.0, //СТАРТ ПО ИКСУ (ОТКУДА НАЧИНАЕМ СТРОИТЬ регессеонную прямую
                1.0, //ШАГ ПО ИКСУ
                listOfData, //множество по Y
                100, // количество точек, по которым строим регрессию
                0);          // начальный индекс Y
        series3.add(1.0,d[0]);
        series3.add(20.0,d[1]);
        dataset.addSeries(series3);

        for(int i=1;i<200-49;i++){
         series3 = new XYSeries(listOfName.get(i));
            d = Regres.getRegres(1.0+i,1.0,listOfData,50,i);
            series3.add(1.0+i,d[0]);
            series3.add(20.0+i,d[1]);
            dataset.addSeries(series3);
        }




        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the data for the chart.
     *
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart Demo 6",      // chart title
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

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();// задаем стиль точкам
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        for(int i =1;i<200;i++){
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
            renderer.setSeriesPaint(i,Color.red,true);
        }
        renderer.setSeriesPaint(0,Color.blue,true);
        Shape cross = ShapeUtilities.createRegularCross(3, 1);
        renderer.setSeriesShape(0, cross);


        for(int i=1;i<200;i++){
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
        }
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    *
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
   /* public static void main(final String[] args) {

        final LineChartDemo6 demo = new LineChartDemo6("Line Chart Demo 6");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }*/

}
