import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

import java.util.Scanner;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("101010011110101000101101011100101110110110111000101011101011101010010101110110111100100");

        p.initPopulation();
        new PopulationVisualizer(p);

        while (p.getGeneration() < 500) {
            p.mutate();
            p.fitnessProportionalSelection();
            p.onePointCrossover();
            System.out.println(p.getGeneration());
        }

        new ChainVisualizer(p.getEvaluator().getAllTimeFittest());
    }
}
