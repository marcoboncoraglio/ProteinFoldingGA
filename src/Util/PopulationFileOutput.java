package Util;

import Model.Population;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by marco on 12/05/17.
 */
public class PopulationFileOutput {
    private Population p;

    public PopulationFileOutput(Population p) {
        this.p = p;
    }

    public void printData() {
        try {
            FileWriter fw = new FileWriter("PopulationData", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(p.getEvaluator().measureAverageFitness()
                    + ";" + p.getEvaluator().getHighestFitnessChain().getEvaluator().getCurrentFitness()
                    + ";" + p.getEvaluator().getHighestRecordedFitness());
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
