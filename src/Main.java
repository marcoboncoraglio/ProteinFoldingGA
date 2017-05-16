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

        Chain c = new Chain("1001000");
        c.generateDirections();
        c.generateChain();

        while(c.getEvaluator().measureFitness() < 0.2){
            c.generateDirections();
            c.generateChain();
        }

        //c.printChainConnections();
        System.out.println(c.getEvaluator().measureFitness());

        new ChainVisualizer(c);


    }
}
