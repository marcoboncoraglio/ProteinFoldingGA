package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Util.PopulationFitnessEvaluator;

/**
 * Created by marco on 11/05/17.
 */
public class Population {
    private List<Chain> chainPopulation;
    private String chainString;
    private int generation;
    public int populationSize = 100;
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
            if(c.getEvaluator().measureFitness() < 1){
                c.generateChain();
            }
        }
    }

    public void setChainPopulation(List<Chain> chainPopulation) {
        this.chainPopulation = chainPopulation;
    }

    public ArrayList<Chain> weightedSelection() { //THIS IS STILL WRONG?
        Random randGenerator = new Random();
        float randNum = randGenerator.nextFloat() * evaluator.measureTotalFitness();

        ArrayList<Chain> selectedPopulation = new ArrayList();
        int index = 0;

        for(Chain c : chainPopulation) {
            for (int i = 0; i < chainPopulation.size(); i++) {
                randNum -= chainPopulation.get(i).getEvaluator().measureFitness();
                if (randNum <= 0) {
                    index = 0;
                }
            }
            selectedPopulation.add(new Chain(chainPopulation.get(index).getAmminoChain(), chainString));
        }


        generation++;

        return selectedPopulation;
    }
}

//write to file: average fitness per generation, highest fitness per generation, all time highest fitness!
//mutation and crossover still to implement!

//if I have free time, use java 2d graphics for chain view