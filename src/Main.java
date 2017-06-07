import Model.Population;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("01010001011010101001001101001000101011001110101010101111010010011010110101000101110100100010011101100100100111");

        p.initPopulation();
        new PopulationVisualizer(p);
        //PopulationFileOutput output = new PopulationFileOutput(p);


        do {
            p.getBreeder().fitnessProportialSelectionWithElitism();
            //p.fitnessProportionalSelection();
            p.getBreeder().randomResettingMutation();
            p.getBreeder().onePointCrossover();
            //output.printData();
        } while (p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping() != 0);

        new ChainVisualizer(p.getEvaluator().getHighestRecordedFitnessChain());
        System.out.println("FINAL OVERLAPPING!!!!!!: " + p.getEvaluator().getHighestRecordedFitnessChain().getEvaluator().getOverlapping());
    }
}
