package Util;

import Model.Chain;
import Model.Population;

/**
 * Created by marco on 11/05/17.
 */
public class PopulationFitnessEvaluator {
    Population p;
    private float totalFitness;
    private Chain highestFitnessEver;
    private Chain highestFitnessThisGeneration;


    public PopulationFitnessEvaluator(Population p) {
        this.p = p;
        initFittest();
    }

    private void initFittest(){
        highestFitnessEver = new Chain(p.getChainString());
        highestFitnessThisGeneration = new Chain(p.getChainString());
        highestFitnessThisGeneration.generateDirections();
        highestFitnessThisGeneration.generateChain();
        highestFitnessEver.generateDirections();
        highestFitnessEver.generateChain();
    }

    public Chain getHighestFitnessEver() {
        return highestFitnessEver;
    }

    public void measureTotalFitness() {
        float total = 0;

        for (Chain c : p.getChainPopulation()) {
            c.getEvaluator().measureFitness();
            total += c.getEvaluator().getCurrentFitness();
        }

        totalFitness = total;
    }

    public float getTotalFitness() {
        return totalFitness;
    }

    public float measureAverageFitness() {
        return totalFitness / p.populationSize;
    }

    public void findHighestFitnesses() {

        highestFitnessThisGeneration = new Chain((Chain) p.getChainPopulation().stream().sorted((chain, t1) -> {
            if (chain.getEvaluator().getCurrentFitness() > t1.getEvaluator().getCurrentFitness())
                return -1;
            else if (chain.getEvaluator().getCurrentFitness() == t1.getEvaluator().getCurrentFitness())
                return 0;
            else return 1;
        }).toArray()[0]);

        if(highestFitnessThisGeneration.getEvaluator().getCurrentFitness() > highestFitnessEver.getEvaluator().getCurrentFitness()){
            highestFitnessEver = new Chain(highestFitnessThisGeneration);
        }
    }

    public Chain getHighestFitnessThisGeneration() {
        return highestFitnessThisGeneration;
    }
}
