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

    public void checkConnections() {
        //check for overlapping amminoacids

        this.overlapping = 0;
        Chain tmpChain = new Chain(chain);
        tmpChain.generateChain();

        for (int i = 0; i < tmpChain.getAmminoChain().size(); i++) {
            for (int j = 0; j < tmpChain.getAmminoChain().size(); j++) {
                if (tmpChain.getAmminoChain().get(i) != tmpChain.getAmminoChain().get(j) &&
                        tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX() &&
                        tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY()) {
                    overlapping++;
                }
            }
        }
    }

    public double measureFitness() {
        checkConnections();
        Chain tmpChain = new Chain(chain);
        tmpChain.generateChain();
        double fitness = 0;

        for (int i = 0; i < tmpChain.getAmminoChain().size(); i++) {
            for (int j = 0; j < tmpChain.getAmminoChain().size(); j++) {
                //are both hydrophobic and not connected?
                if ((tmpChain.getAmminoChain().get(i).isHydrophobic() && tmpChain.getAmminoChain().get(j).isHydrophobic()) && !tmpChain.getAmminoChain().get(i).isConnected(tmpChain.getAmminoChain().get(j))) {
                    //are they neighbours
                    if (tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX() - 1 && tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY()
                            || tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX() + 1 && tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY()
                            || tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY() - 1 && tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX()
                            || tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY() + 1 && tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX()) {
                        fitness++;

                    }

                }
            }

        }

        if (overlapping > 0) {
            return fitness/(overlapping*1000);
        }
        return fitness / 2;
    }
}
