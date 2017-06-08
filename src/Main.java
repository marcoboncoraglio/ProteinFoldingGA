import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("0101000110100101010110101011010010010111001101101001110101001000101110101");

        p.initPopulation();
        //PopulationVisualizer visualizer = new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        do {
            p.getBreeder().fitnessProportialSelectionWithElitism();
            p.getBreeder().randomResettingMutation();
            p.getBreeder().onePointCrossover();
            //output.printData();
        } while (p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping() != 0);

        new ChainVisualizer(p.getEvaluator().getHighestRecordedFitnessChain());
        System.out.println("Final Overlapping: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping());
        System.out.println("Final Fitness: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getCurrentFitness());
    }
}
