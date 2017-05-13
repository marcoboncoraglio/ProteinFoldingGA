package Model;

import Util.PopulationFitnessEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by marco on 11/05/17.
 */
public class Population {
    private List<Chain> chainPopulation;
    private String chainString;
    private int generation;
    public int populationSize = 30;
    private PopulationFitnessEvaluator evaluator;

    public List<Chain> getChainPopulation() {
        return chainPopulation;
    }

    public Population(String linearChain) {
        this.chainString = linearChain;
        this.chainPopulation = new ArrayList<>();
        this.generation = 0;
        evaluator = new PopulationFitnessEvaluator(this);
    }

    public PopulationFitnessEvaluator getEvaluator() {
        return evaluator;
    }

    public void initPopulation() {

        for (int i = 0; i < populationSize; i++) {
            chainPopulation.add(new Chain(chainString));
        }

        for (Chain c : chainPopulation) {
            c.generateChain();
            if (c.getEvaluator().measureFitness() < 1) {
                c.generateChain();
            }
        }
    }

    public void setChainPopulation(List<Chain> chainPopulation) {
        this.chainPopulation = chainPopulation;
    }

    public void weightedSelection() {
        Random randGenerator = new Random();
        float randNum = 0;

        ArrayList<Chain> selectedPopulation = new ArrayList();
        int index = 0;
        double fitnessPerChain;

        for(Chain c: chainPopulation) {
            randNum = randGenerator.nextFloat() * evaluator.measureTotalFitness();
            fitnessPerChain = c.getEvaluator().measureFitness();

            for (int i = 0; i < chainPopulation.size(); i++) {
                randNum -= fitnessPerChain;
                if (randNum <= 0) {
                    index = i;
                    break;
                }
            }

            selectedPopulation.add(new Chain(chainPopulation.get(index).getAmminoChain(), chainString));

        }


        generation++;

        this.setChainPopulation(selectedPopulation);
    }

    public int getGeneration() {
        return generation;
    }
}

//write to file: average fitness per generation, highest fitness per generation, all time highest fitness!
//mutation and crossover still to implement!

//if I have free time, use java 2d graphics for chain view