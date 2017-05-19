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

        Population p = new Population("1000100");

        p.initPopulation();
        new PopulationVisualizer(p);

        while (p.getGeneration() < 2) {
            p.mutate();
            p.weightedSelection();
        }


    }
}
