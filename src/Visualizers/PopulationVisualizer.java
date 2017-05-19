package Visualizers;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import Model.Population;

/**
 * Created by marco on 13/05/17.
 */
public class PopulationVisualizer {
    private Population population;
    private int currentGeneration;
    private double averageFitness;
    private double currentHigh;
    private double allTimeHigh;

    public PopulationVisualizer(Population p) {
        // Create a chart:
        this.population = p;

        Chart2D chart = new Chart2D();
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        ITrace2D averageFitnessChart = new Trace2DLtd(5000, "Average Fitness");
        ITrace2D currentHighest = new Trace2DLtd(5000, "Current Highest");
        ITrace2D allTimeHighest = new Trace2DLtd(5000, "All Time highest");
        averageFitnessChart.setColor(Color.ORANGE);
        currentHighest.setColor(Color.BLUE);
        allTimeHighest.setColor(Color.RED);

        // Add the trace to the chart. This has to be done before adding points (deadlock prevention):
        chart.addTrace(averageFitnessChart);
        chart.addTrace(currentHighest);
        chart.addTrace(allTimeHighest);

        // Make it visible:
        // Create a frame.
        JFrame frame = new JFrame("Population Statistics");
        // add the chart to the frame:
        frame.getContentPane().add(chart);
        frame.setSize(800, 600);

        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.setVisible(true);


        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                currentHigh = population.getEvaluator().getHighestFitnessChain().getEvaluator().measureFitness(); //maybe optimize a bit?
                allTimeHigh = population.getEvaluator().getHighestRecordedFitness();
                currentGeneration = population.getGeneration();
                averageFitness = population.getEvaluator().measureAverageFitness();
                averageFitnessChart.addPoint(currentGeneration, averageFitness);
                currentHighest.addPoint(currentGeneration, currentHigh);
                allTimeHighest.addPoint(currentGeneration, allTimeHigh);
            }

        };

        timer.schedule(task, 1, 2);
    }
}

