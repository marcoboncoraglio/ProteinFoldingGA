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
    private final int mutationRate = 1;
    private final int crossoverRate = 1;
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

    public String getChainString() {
        return chainString;
    }

    public Population(Population p) {
        this.chainPopulation = p.chainPopulation;
        this.chainString = p.chainString;
        this.generation = p.generation;
        this.populationSize = p.populationSize;
        this.evaluator = p.evaluator;
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

    public void fitnessProportionalSelection() {
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
            chainMutationIndex = rand.nextInt(populationSize * chainPopulation.size());

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

    public void onePointCrossover(){
        int totalCrossovers = populationSize * crossoverRate/100;
        int crossoverIndex;

        List<Chain.Direction> dir1new = new ArrayList<>();
        List<Chain.Direction> dir2new = new ArrayList<>();

        Random rand = new Random();

        //for each onePointCrossover
        for(int i =0; i< totalCrossovers; i++){
            crossoverIndex = rand.nextInt(chainString.length());

            //grab two random chains from our population
            int chain1Index = rand.nextInt(chainPopulation.size());
            int chain2Index = rand.nextInt(chainPopulation.size());
            Chain c1 = chainPopulation.get(chain1Index);
            Chain c2 = chainPopulation.get(chain2Index);

            //copy first half
            for (int j=0; j<crossoverIndex; j++){
                dir1new.add(c1.getDirections().get(j));
                dir2new.add(c2.getDirections().get(j));
            }

            //copy second half
            for(int k=chainPopulation.size(); k-crossoverIndex >= 0; k-- ){
                dir1new.add(c2.getDirections().get(k));
                dir2new.add(c1.getDirections().get(k));
            }

            //set onePointCrossover chains and generate them
            c1.setDirections(dir1new);
            c2.setDirections(dir2new);
            chainPopulation.get(chain1Index).generateChain();
            chainPopulation.get(chain2Index).generateChain();
        }
    }
}