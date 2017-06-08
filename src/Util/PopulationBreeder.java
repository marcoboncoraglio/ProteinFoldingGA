package Util;

import Model.Chain;
import Model.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by marco on 08/06/17.
 */
public class PopulationBreeder {
    private Population population;
    private final int mutationRate = 5;
    private final int crossoverRate = 60;
    private final int numberOfElites = 5;

    public PopulationBreeder(Population p) {
        population = p;
    }

    //TODO: Rework fitnessProportionalSelection
    public void fitnessProportionalSelection() {
        Random randGenerator = new Random();
        double randNum;

        ArrayList<Chain> selectedPopulation = new ArrayList<>();
        float totalFiness = population.getEvaluator().measureTotalFitness();

        //to obtain as many chains in the next generation as in the current one, replace each chain
        for (Chain c : population.getChainPopulation()) {
            //generate random number between 0 and total fitness of all chains
            randNum = randGenerator.nextFloat() * totalFiness;

            int index = 0;
            //roulette wheel selection
            for (int i = 0; i < population.getChainPopulation().size(); i++) {
                randNum -= population.getChainPopulation().get(i).getEvaluator().getCurrentFitness();
                if (randNum <= 0) {
                    index = i;
                    break;
                }
            }
            selectedPopulation.add(population.getChainPopulation().get(index));
        }

        population.generation++;
        population.setChainPopulation(selectedPopulation);
    }

    public void fitnessProportialSelectionWithElitism() {
        //see fitnessProportionalSelection for comments
        Random randGenerator = new Random();
        double randNum;

        ArrayList<Chain> selectedPopulation = new ArrayList<>();


        float totalFitness = population.getEvaluator().measureTotalFitness();

        for (int i = numberOfElites; i < population.getChainPopulation().size(); i++) {
            randNum = randGenerator.nextFloat() * totalFitness;


            int index = 0;
            for (int j = 0; j < population.getChainPopulation().size(); j++) {
                randNum -= population.getChainPopulation().get(i).getEvaluator().getCurrentFitness();
                if (randNum <= 0) {
                    index = j;
                    break;
                }
            }
            selectedPopulation.add(population.getChainPopulation().get(index));
        }


        //array sorted by descending fitness
        Object[] oarr = population.getChainPopulation().stream().sorted((chain, t1) -> {
            if (chain.getEvaluator().getCurrentFitness() > t1.getEvaluator().getCurrentFitness())
                return -1;
            else if (chain.getEvaluator().getCurrentFitness() == t1.getEvaluator().getCurrentFitness())
                return 0;
            else return 1;
        }).toArray();

        //guarantee place for the fittest chains
        for (int i = 0; i < numberOfElites; i++) {
            selectedPopulation.add((Chain) oarr[i]);
        }

        population.generation++;
        population.setChainPopulation(selectedPopulation);

    }

    public void randomResettingMutation() {
        int totalMutations = population.populationSize * population.getChainString().length() * mutationRate / 100;
        int chainMutationIndex;

        Random rand = new Random();

        for (int i = 0; i < totalMutations; i++) {
            chainMutationIndex = rand.nextInt(population.populationSize * population.getChainPopulation().size());

            for (Chain c : population.getChainPopulation()) {
                if (chainMutationIndex - population.getChainString().length() < 0) {
                    int mutateDirection = rand.nextInt(4);
                    c.getDirections().set(chainMutationIndex, Chain.Direction.values()[mutateDirection]);
                    c.generateChain();
                    c.getEvaluator().measureFitness();
                    break;
                } else {
                    chainMutationIndex -= c.getAmminoChain().size();
                }
            }
        }
    }

    public void onePointCrossover() {
        int totalCrossoversPairs = population.getChainPopulation().size()/2 * crossoverRate / 100;   //population size
        int crossoverIndex;

        Random rand = new Random();

        for (int i = 0; i < totalCrossoversPairs; i++) {

            List<Chain.Direction> dir1new = new ArrayList<>();
            List<Chain.Direction> dir2new = new ArrayList<>();

            //the index at which the crossover happens
            crossoverIndex = rand.nextInt(population.getChainString().length()-1) +1;

            //grab two random chains from our population
            int chain1Index = rand.nextInt(population.getChainPopulation().size());
            int chain2Index = rand.nextInt(population.getChainPopulation().size());
            Chain c1 = new Chain(population.getChainPopulation().get(chain1Index));
            Chain c2 = new Chain(population.getChainPopulation().get(chain2Index));

            //copy first half
            for (int j = 0; j < crossoverIndex; j++) {
                dir1new.add(c1.getDirections().get(j));
                dir2new.add(c2.getDirections().get(j));
            }

            //copy second half
            for (int k = crossoverIndex-1; k < c1.getDirections().size()-1; k++) {
                dir1new.add(c2.getDirections().get(k));
                dir2new.add(c1.getDirections().get(k));
            }

            //set onePointCrossover chains and generate them
            c1.setDirections(dir1new);
            c2.setDirections(dir2new);
            population.getChainPopulation().set(chain1Index, c1);
            population.getChainPopulation().set(chain1Index, c2);

            c1.generateChain();
            c2.generateChain();
            c1.getEvaluator().measureFitness();
            c2.getEvaluator().measureFitness();
        }
    }

    //TODO: rank based selection not yet implemented
    public void rankBasedSelection() {
        Random rand = new Random();

        //array sorted by ascending fitness
        Object[] oarr = population.getChainPopulation().stream().sorted((chain, t1) -> {
            if (chain.getEvaluator().getCurrentFitness() > t1.getEvaluator().getCurrentFitness())
                return 1;
            else if (chain.getEvaluator().getCurrentFitness() == t1.getEvaluator().getCurrentFitness())
                return 0;
            else return -1;
        }).toArray();


        HashMap<Integer, Chain> map = new HashMap<>();
        int totalChance = 0;
        int selectionIndex;

        //map chains to their chance of survival and add up total chance
        for (int i = 1; i < oarr.length + 1; i++) {
            map.put(i * 5, (Chain) oarr[i - 1]);
            totalChance += i * 5;
        }

        ArrayList<Chain> selectedPopulation = new ArrayList<>();

    }
}
