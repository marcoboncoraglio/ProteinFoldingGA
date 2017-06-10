import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("100101001010010101001011001111010010010100100101011110101001010100101101011101010010101101101100110111");

        p.initPopulation();
        PopulationVisualizer visualizer = new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        do {
            p.getBreeder().fitnessProportionalSelection();
            p.getBreeder().randomResettingMutation();
            p.getBreeder().onePointCrossover();
            //output.printData();
        } while (p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getCurrentFitness() < 10);

        new ChainVisualizer(p.getEvaluator().getHighestRecordedFitnessChain());
        System.out.println(p.getEvaluator().getHighestRecordedFitnessChain());
        System.out.println("Final Overlapping: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping());
        System.out.println("Final Fitness: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getCurrentFitness());
    }
}
