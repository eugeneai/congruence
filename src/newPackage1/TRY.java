
/**
 * Created by Егор on 08.11.2016.
 */

package newPackage1;
        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.EventQueue;
        import java.awt.Shape;
        import java.util.*;
        import javax.swing.JFrame;
        import org.jfree.chart.*;
        import org.jfree.chart.axis.NumberAxis;
        import org.jfree.chart.axis.NumberTickUnit;
        import org.jfree.chart.plot.PlotOrientation;
        import org.jfree.chart.plot.XYPlot;
        import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
        import org.jfree.data.xy.XYDataset;
        import org.jfree.data.xy.XYSeries;
        import org.jfree.data.xy.XYSeriesCollection;
        import org.jfree.util.ShapeUtilities;

public class TRY  extends JFrame{

    private static final Random rand = new Random();
    public static JFreeChart chart = ChartFactory.createScatterPlot(
            "new", "X", "Y", createDataSet(),
            PlotOrientation.VERTICAL, true, true, false);

    public TRY() {
        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {


        XYPlot xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setDomainCrosshairVisible(true); // это позволяет тыкать мышкой
        xyPlot.setRangeCrosshairVisible(true);
        adjustAxis((NumberAxis) xyPlot.getDomainAxis(), true);
        adjustAxis((NumberAxis) xyPlot.getRangeAxis(), false);
        xyPlot.setBackgroundPaint(Color.white);


        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                TRY demo = new TRY();
                demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                demo.pack();
                demo.setLocationRelativeTo(null);
                demo.setVisible(true);
            }
        });

    }
    private static void adjustAxis(NumberAxis axis, boolean vertical) {
        axis.setRange(0.0, 320.0);
        axis.setTickUnit(new NumberTickUnit(0.5));
        axis.setVerticalTickLabels(vertical);
    }

    public  static XYDataset createDataSet(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < 300; i++) {
            if(i<=150)
            series.add(i,(int)(rand.nextInt(i+1)+i));
            else
                series.add(i,(int)(-rand.nextInt(i+1)-i)+450);
        }
        dataset.addSeries(series);
        return dataset;
    }
}
