import Model.Population;
import Util.PopulationFileOutput;
import Visualizers.ChainVisualizer;
import Visualizers.PopulationVisualizer;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {
        int amoutOfGenerations = 500;
        Population p = new Population("10010100001");
        p.initPopulation();
        PopulationVisualizer pVis = new PopulationVisualizer(p, amoutOfGenerations);


        while (p.getGeneration() < amoutOfGenerations) {
            p.weightedSelection();
            System.out.println(p.getEvaluator().measureAverageFitness());
        }


    }
}
