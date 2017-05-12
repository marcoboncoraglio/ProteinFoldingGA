package Util;

import Model.Chain;

/**
 * Created by marco on 11/05/17.
 */
public class ChainFitnessEvaluator {

    Chain chain;
    int overlapping;

    public ChainFitnessEvaluator(Chain chain) {
        this.chain = chain;
    }

    public void checkConnections(){
        //check for overlapping amminoacids

        int overlapping = 0;

        for (int i = 0; i < chain.getAmminoChain().size(); i++) {
            for (int j = 0; j < chain.getAmminoChain().size(); j++) {
                if (chain.getAmminoChain().get(i) != chain.getAmminoChain().get(j) &&
                        chain.getAmminoChain().get(i).getX() == chain.getAmminoChain().get(j).getX() &&
                        chain.getAmminoChain().get(i).getY() == chain.getAmminoChain().get(j).getY()) {
                    overlapping++;
                }
            }
        }
    }

    public double measureFitness(){
        double fitness = 0;

        for (int i = 0; i < chain.getAmminoChain().size(); i++) {
            for (int j = 0; j < chain.getAmminoChain().size(); j++) {
                //are both hydrophobic and not connected?
                if (chain.getAmminoChain().get(i).isHydrophobic() && chain.getAmminoChain().get(j).isHydrophobic() && !chain.getAmminoChain().get(i).isConnected(chain.getAmminoChain().get(j))) {
                    if (chain.getAmminoChain().get(i).getX() == chain.getAmminoChain().get(j).getX() - 1 && chain.getAmminoChain().get(i).getY() == chain.getAmminoChain().get(j).getY()
                            || chain.getAmminoChain().get(i).getX() == chain.getAmminoChain().get(j).getX() + 1 && chain.getAmminoChain().get(i).getY() == chain.getAmminoChain().get(j).getY()
                            || chain.getAmminoChain().get(i).getY() == chain.getAmminoChain().get(j).getY() - 1 && chain.getAmminoChain().get(i).getX() == chain.getAmminoChain().get(j).getX()
                            || chain.getAmminoChain().get(i).getY() == chain.getAmminoChain().get(j).getY() + 1 && chain.getAmminoChain().get(i).getX() == chain.getAmminoChain().get(j).getX()) {
                        fitness++;

                        //System.out.println("Minimal Energy Bond found between: " + i + ", " + j);
                    }

                }
            }

        }

        if(overlapping > 0){
            //System.out.println(overlapping);
            return 0.1;
        }
        return fitness / 2;
    }
}
