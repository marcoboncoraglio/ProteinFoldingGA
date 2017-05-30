package Util;

import Model.Chain;
import Model.Population;

/**
 * Created by marco on 11/05/17.
 */
public class PopulationFitnessEvaluator {
    Population p;
    private double highestRecordedFitness = 0;
    private float totalFitness = 0;

    public PopulationFitnessEvaluator(Population p) {
        this.p = p;
    }

    public float measureTotalFitness() {
        float total = 0;

        for (Chain c : p.getChainPopulation()) {
            total += c.getEvaluator().measureFitness();
        }

        totalFitness = total;
        return total;
    }

    public double getHighestRecordedFitness() {
        return highestRecordedFitness;
    }

    public float measureAverageFitness() {
        return totalFitness / p.populationSize;
    }

    public Chain getHighestFitnessChain() {
        double highestValue = 0;
        int index = 0;

        for (int i = 0; i < p.getChainPopulation().size(); i++) {
            if (p.getChainPopulation().get(i).getEvaluator().measureFitness() > highestValue) {
                highestValue = p.getChainPopulation().get(i).getEvaluator().measureFitness();
                if (highestValue > highestRecordedFitness) {
                    highestRecordedFitness = highestValue;
                }
                index = i;
            }
        }

        return p.getChainPopulation().get(index);
    }
}
