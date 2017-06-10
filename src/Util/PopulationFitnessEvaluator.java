package Util;

import Model.Chain;
import Model.Population;

/**
 * Created by marco on 11/05/17.
 */
public class PopulationFitnessEvaluator {
    Population p;
    private float totalFitness = 0;
    private Chain highestRecordedFitnessChain;
    private Chain highestFitnessInGeneration;
    private float allTimeFittestFitness;

    public float getAllTimeFittestFitness() {
        return allTimeFittestFitness;
    }

    public PopulationFitnessEvaluator(Population p) {
        this.p = p;
        initFittest();
    }

    public Chain getHighestFitnessInGeneration() {
        return highestFitnessInGeneration;
    }

    private void initFittest(){
        highestRecordedFitnessChain = new Chain(p.getChainString());
        highestFitnessInGeneration = new Chain(p.getChainString());
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
        measureHighestFitnessChains();
        return total;
    }

    public double getHighestRecordedFitness() {
        return allTimeFittestFitness;
    }

    public float measureAverageFitness() {
        return totalFitness / p.populationSize;
    }

    //TODO: I think this is wrong, make this into measure function, call in measuretotal?
    public void measureHighestFitnessChains() {

        //array sorted by ascending fitness
        Chain highestFitnessChain = (Chain) p.getChainPopulation().stream().sorted((chain, t1) -> {
            if (chain.getEvaluator().getCurrentFitness() > t1.getEvaluator().getCurrentFitness())
                return -1;
            else if (chain.getEvaluator().getCurrentFitness() == t1.getEvaluator().getCurrentFitness())
                return 0;
            else return 1;
        }).toArray()[0];

        highestFitnessChain.getEvaluator().measureFitness();
        highestRecordedFitnessChain.getEvaluator().measureFitness();
        if(highestFitnessChain.getEvaluator().getCurrentFitness() > highestRecordedFitnessChain.getEvaluator().getCurrentFitness()){
            highestRecordedFitnessChain = new Chain(highestFitnessChain);
        }

        highestFitnessInGeneration = new Chain(highestFitnessChain);
    }
}
