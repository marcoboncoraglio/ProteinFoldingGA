package Util;

import Model.Chain;

/**
 * Created by marco on 11/05/17.
 */
public class ChainFitnessEvaluator {

    Chain chain;
    int overlapping;
    private double currentFitness;

    public ChainFitnessEvaluator(Chain chain) {
        this.chain = chain;
    }

    public int getOverlapping() {
        return overlapping;
    }

    //TODO: Rework to check direction list. Increment overlapping in cases like direction.at(n-1) = south, direction.at(n) = north etc...
    private void checkConnections() {

        this.overlapping = 0;
        Chain tmpChain = new Chain(chain);
        tmpChain.generateChain();

        for (int i = 1; i < tmpChain.getDirections().size(); i++) {
            switch (tmpChain.getDirections().get(i)) {
                case EAST:
                    if (tmpChain.getDirections().get(i - 1) == Chain.Direction.WEST) {
                        overlapping++;
                    }
                    break;
                case WEST:
                    if (tmpChain.getDirections().get(i - 1) == Chain.Direction.EAST) {
                        overlapping++;
                    }
                    break;
                case NORTH:
                    if (tmpChain.getDirections().get(i - 1) == Chain.Direction.SOUTH) {
                        overlapping++;
                    }
                    break;
                case SOUTH:
                    if (tmpChain.getDirections().get(i - 1) == Chain.Direction.NORTH) {
                        overlapping++;
                    }
                    break;
            }
        }

        for (int i = 0; i < tmpChain.getAmminoChain().size(); i++) {
            for (int j = 0; j < tmpChain.getAmminoChain().size(); j++) {
                //if not equal and physically overlapping
                if ((i != j) &&
                        tmpChain.getAmminoChain().get(i).getX() == tmpChain.getAmminoChain().get(j).getX() &&
                        tmpChain.getAmminoChain().get(i).getY() == tmpChain.getAmminoChain().get(j).getY()) {
                    overlapping++;
                }
            }
        }

        overlapping = overlapping / 2;
    }

    public double getCurrentFitness() {
        return currentFitness;
    }

    public void measureFitness() {
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
            currentFitness = (fitness / 2) / Math.pow(overlapping+1, 4);
        } else if (fitness == 0 && overlapping == 0) {
            currentFitness = 1;
        } else {
            currentFitness = fitness / 2;
        }
    }
}
