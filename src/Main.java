import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("101010101010110110110101010011100110");

        p.initPopulation();
        PopulationVisualizer visualizer = new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        do {
            p.getBreeder().randomResettingMutation();
            p.getBreeder().onePointCrossover();
            p.getBreeder().fitnessProportionalSelectionWithElitism();
            //output.printData();
        } while (p.getEvaluator().getHighestFitnessEver().getEvaluator().getCurrentFitness() < 1000
                && p.getEvaluator().getHighestFitnessEver().getEvaluator().getOverlapping() == 0);
        //why the fuck are we jumping out

        //new ChainVisualizer(p.getEvaluator().getHighestFitnessEver());
        //System.out.println(p.getEvaluator().getHighestFitnessEver());
        System.out.println("Final Overlapping: " + p.getEvaluator().getHighestFitnessEver().getEvaluator().getOverlapping());
        System.out.println("Final Fitness: " + p.getEvaluator().getHighestFitnessEver().getEvaluator().getCurrentFitness());
    }
}
