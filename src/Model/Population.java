package Model;

import Util.PopulationBreeder;
import Util.PopulationFitnessEvaluator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by marco on 11/05/17.
 */

//TODO: measure genetic diversity (how many chains are the same in population)
public class Population {
    private List<Chain> chainPopulation;
    private final String chainString;
    public int generation;
    public int populationSize = 100;
    private PopulationBreeder breeder;
    private PopulationFitnessEvaluator evaluator;

    public List<Chain> getChainPopulation() {
        return chainPopulation;
    }

    public Population(String linearChain) {
        this.chainString = linearChain;
        this.chainPopulation = new ArrayList<>();
        this.generation = 0;
        evaluator = new PopulationFitnessEvaluator(this);
        breeder = new PopulationBreeder(this);
    }

    public String getChainString() {
        return chainString;
    }

    public Population(Population p) {
        this.chainPopulation = p.chainPopulation;
        this.chainString = p.chainString;
        this.generation = p.generation;
        this.populationSize = p.populationSize;
        this.evaluator = p.evaluator;
        breeder = p.getBreeder();
    }

    public PopulationBreeder getBreeder() {
        return breeder;
    }

    public PopulationFitnessEvaluator getEvaluator() {
        return evaluator;
    }

    //generate chains with less overlappings?
    public void initPopulation() {
        System.out.println("Initializing population...");

        for (int i = 0; i < populationSize; i++) {
            chainPopulation.add(new Chain(chainString));
        }

        for (Chain c : chainPopulation) {
            //do{
                c.generateDirections();
                c.generateChain();
            //    c.getEvaluator().measureFitness();
            //}while(c.getEvaluator().getOverlapping() > 10);
        }
    }

    public void setChainPopulation(List<Chain> chainPopulation) {
        this.chainPopulation = chainPopulation;
    }

    public int getGeneration() {
        return generation;
    }
}