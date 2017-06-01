import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    //TODO: Fix extremely oscillating average fitness and highest fitness per generation
    //TODO: Check connections should be fixed, it currently does not check if two amminoacids are nearby
    public static void main(String args[]) {

        Population p = new Population("101010011110101000101101011100101110110110111000101011101011101010010101110110111100100");

        p.initPopulation();
        new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        while (p.getGeneration() < 50) {

            p.fitnessProportionalSelection();
            p.randomResettingMutation();
            p.onePointCrossover();
            //output.printData();
        }

        new ChainVisualizer(p.getEvaluator().getAllTimeFittest());
        System.out.println("Overlapping: " + p.getEvaluator().getAllTimeFittest().getEvaluator().getOverlapping());
    }
}
