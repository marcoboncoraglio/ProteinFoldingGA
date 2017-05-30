import Model.Chain;
import Model.Population;
import Util.PopulationFileOutput;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {

        Population p = new Population("101010011110101000101101011100101110110110111000101011101011101010010101110110111100100");

        p.initPopulation();

        //concurrency issues :(
        //new PopulationVisualizer(p);

        while (p.getGeneration() < 200) {
            p.mutate();
            p.weightedSelection();
        }

        new ChainVisualizer(p.getEvaluator().getHighestFitnessChain());
    }
}
