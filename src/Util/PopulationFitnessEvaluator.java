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
    private Chain highestRecordedFitnessChain;
    private float allTimeFittestFitness;

    public float getAllTimeFittestFitness() {
        return allTimeFittestFitness;
    }

    public PopulationFitnessEvaluator(Population p) {
        this.p = p;
        initFittest();
    }

    private void initFittest(){
        highestRecordedFitnessChain = new Chain(p.getChainString());
        highestRecordedFitnessChain.generateDirections();
        highestRecordedFitnessChain.generateChain();
    }

    public Chain getHighestRecordedFitnessChain() {
        return highestRecordedFitnessChain;
    }

    public float measureTotalFitness() {
        float total = 0;

        for (Chain c : p.getChainPopulation()) {
            c.getEvaluator().measureFitness();
            total += c.getEvaluator().getCurrentFitness();
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
            if (p.getChainPopulation().get(i).getEvaluator().getCurrentFitness() > highestValue) {
                highestValue = p.getChainPopulation().get(i).getEvaluator().getCurrentFitness();
                if (highestValue > highestRecordedFitness) {
                    highestRecordedFitness = highestValue;
                }
                index = i;
            }
        }

        allTimeFittestFitness = (float) highestRecordedFitnessChain.getEvaluator().getCurrentFitness();
        if(p.getChainPopulation().get(index).getEvaluator().getCurrentFitness() > allTimeFittestFitness){
            highestRecordedFitnessChain = new Chain(p.getChainPopulation().get(index));
        }

        return p.getChainPopulation().get(index);
    }
}
