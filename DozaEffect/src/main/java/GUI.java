import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        double[] u = {0.0062, 0.0067, 0.0075, 0.0083, 0.0091,
                0.010, 0.011, 0.012, 0.013, 0.015,
                0.016, 0.018, 0.020, 0.022, 0.024,
                0.026, 0.029, 0.031, 0.035, 0.039,
                0.042, 0.046, 0.050, 0.056, 0.061,
                0.067, 0.074, 0.080, 0.087, 0.095,
                0.100, 0.110, 0.125, 0.140, 0.154,
                0.167, 0.182, 0.200, 0.222, 0.250,
                0.278, 0.303, 0.333, 0.370, 0.400,
                0.435, 0.476, 0.500, 0.526, 0.556,
                0.625, 0.667, 0.769, 0.833, 0.909, 1.000};
        double[] w = {0.0, 0.0, 0.0, 0.0, 0.0,
                2.0/6.0, 2.0/6.0, 0.0, 1.0/6.0, 2.0/6.0,
                2.0/6.0, 2.0/6.0, 3.0/6.0, 2.0/6.0, 1.0/6.0,
                3.0/6.0, 3.0/6.0, 1.0/6.0, 2.0/6.0, 3.0/6.0,
                1.0/6.0, 2.0/6.0, 2.0/6.0, 2.0/6.0, 3.0/6.0,
                2.0/6.0, 2.0/6.0, 4.0/6.0, 2.0/6.0, 1.0/6.0,
                3.0/6.0, 1.0/6.0, 3.0/6.0, 2.0/6.0, 3.0/6.0,
                2.0/6.0, 4.0/6.0, 4.0/6.0, 4.0/6.0, 3.0/6.0,
                5.0/6.0, 3.0/6.0, 4.0/6.0, 5.0/6.0, 2.0/5.0,
                5.0/6.0, 4.0/6.0, 2.0/6.0, 4.0/6.0, 3.0/6.0,
                5.0/6.0, 4.0/6.0, 5.0/6.0, 8.0/12.0, 12.0/12.0, 12.0/12.0};

        XYSeriesCollection dataset = new XYSeriesCollection();
        JFrame frame = new JFrame("Доза-Эффект");
        JPanel input_panel = new JPanel(new GridLayout(4, 2, 0, 5));
        input_panel.add(new JLabel("h:"));
        JTextField h_tf = new JTextField();
        input_panel.add(h_tf);
        input_panel.add(new JLabel("k:"));
        JTextField k_tf = new JTextField();
        input_panel.add(k_tf);
        input_panel.add(new JLabel("Ядро"));
        String[] cores = {"Епанечникова",
                "квадратичное",
                "равномерное",
                "треугольное",
                "косинус-ядро",
                "Лапласа",
                "Гауссово"};
        JComboBox comboBox = new JComboBox(cores);
        input_panel.add(comboBox);
        JButton draw = new JButton("Нарисовать");
        JButton clear = new JButton("Очистить");
        input_panel.add(draw);
        input_panel.add(clear);

        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XYSeries nw_points = new XYSeries("Оценка Надарая-Ватсона");
                XYSeries knn_points = new XYSeries("Оценка kNN");
                NW nw = new NW(comboBox.getSelectedIndex(), 56, u, w, Double.parseDouble(h_tf.getText()));
                KNN knn = new KNN(comboBox.getSelectedIndex(),56,u,w,Integer.parseInt(k_tf.getText()));
                for(double i = -0.5; i < 1.15; i+=0.025){
                    nw_points.add(i, nw.F_n(i));
                    knn_points.add(i,knn.F_n(i));
                }
                dataset.addSeries(nw_points);
                dataset.addSeries(knn_points);
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataset.removeAllSeries();
            }
        });

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);
        chart.setBackgroundPaint(Color.WHITE);
        final XYPlot plot = chart.getXYPlot();
        plot.setDataset(0,dataset);
        plot.setBackgroundPaint(new Color(232, 232, 232));
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(0,renderer);

        frame.add(input_panel, BorderLayout.WEST);
        frame.add(new ChartPanel(chart), BorderLayout.CENTER);
        frame.pack();
        frame.setSize(800,500);
        frame.show();
    }
}
