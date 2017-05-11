import Model.Population;
import Util.PopulationFileOutput;

/**
 * Created by marco on 11/05/17.
 */
public class Main {

    public static void main(String args[]) {
        Population p = new Population("1001010");

        p.initPopulation();
        PopulationFileOutput fileOutput = new PopulationFileOutput(p);

        while(p.getEvaluator().measureAverageFitness() < 2){
            fileOutput.printData();
        }

    }
}
