import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("01010001011010");

        p.initPopulation();
        new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        while (p.getEvaluator().getAllTimeFittestFitness() < 3) {

            p.fitnessProportionalSelection();
            //p.randomResettingMutation();
            //p.onePointCrossover();
            //output.printData();
        }

        new ChainVisualizer(p.getEvaluator().getHighestRecordedFitnessChain());
        System.out.println("Overlapping: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping());
    }
}
