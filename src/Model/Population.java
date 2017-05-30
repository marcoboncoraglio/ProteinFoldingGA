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
    private int mutationRate = 1;
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
            c.generateDirections();
            c.generateChain();
            if (c.getEvaluator().measureFitness() < 1) {
                c.generateDirections();
                c.generateChain();
            }
        }
    }

    private void setChainPopulation(List<Chain> chainPopulation) {
        this.chainPopulation = chainPopulation;
    }

    public void weightedSelection() {
        Random randGenerator = new Random();
        float randNum;

        ArrayList<Chain> selectedPopulation = new ArrayList<>();
        int index = 0;
        double fitnessPerChain;

        for (Chain c : chainPopulation) {
            randNum = randGenerator.nextFloat() * evaluator.measureTotalFitness();
            fitnessPerChain = c.getEvaluator().measureFitness();

            for (int i = 0; i < chainPopulation.size(); i++) {
                randNum -= fitnessPerChain;
                if (randNum <= 0) {
                    index = i;
                    break;
                }
            }

            selectedPopulation.add(chainPopulation.get(index));

        }


        generation++;

        this.setChainPopulation(selectedPopulation);
    }

    public int getGeneration() {
        return generation;
    }

    public void mutate() {
        int totalMutations = populationSize * chainString.length() * mutationRate / 100;
        int chainMutationIndex;

        Random rand = new Random();

        for (int i = 0; i < totalMutations; i++) {
            chainMutationIndex = rand.nextInt(populationSize * chainString.length());

            for (Chain c : chainPopulation) {
                if (chainMutationIndex - chainString.length() < 0) {
                    int mutateDirection = rand.nextInt(4);
                    c.getDirections().set(chainMutationIndex, Chain.Direction.values()[mutateDirection]);
                    c.generateChain();
                    break;
                } else {
                    chainMutationIndex -= c.getAmminoChain().size();
                }
            }
        }

    }
}