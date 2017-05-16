package Model;

import Util.ChainFitnessEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by marco on 11/05/17.
 */

enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}

public class Chain {
    private List<Direction> directions;
    private List<Amminoacid> amminoChain;
    private String chainString;
    private ChainFitnessEvaluator evaluator;

    public Chain(String chainString) {
        this.chainString = chainString;
        this.evaluator = new ChainFitnessEvaluator(this);

    }

    public Chain(List<Amminoacid> amminoChain, String chainString) {
        this.amminoChain = amminoChain;
        this.chainString = chainString;
        this.evaluator = new ChainFitnessEvaluator(this);
    }

    public ChainFitnessEvaluator getEvaluator() {
        return evaluator;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Amminoacid> getAmminoChain() {
        return amminoChain;
    }

    public void setAmminoChain(List<Amminoacid> amminoChain) {
        this.amminoChain = amminoChain;
    }

    public String getChainString() {
        return chainString;
    }

    public void setChainString(String chainString) {
        this.chainString = chainString;
    }

    public void generateDirections() {
        Random rand = new Random();
        directions = new ArrayList<>();

        for (int i = 0; i < chainString.length(); i++) {
            switch (rand.nextInt(3)) {
                case 0:
                    directions.add(Direction.NORTH);
                    break;
                case 1:
                    directions.add(Direction.SOUTH);
                    break;
                case 2:
                    directions.add(Direction.EAST);
                    break;
                case 3:
                    directions.add(Direction.WEST);
                    break;
            }
        }
    }

    public boolean readChar(int i) {
        if (chainString.charAt(i) == '0')
            return true;
        else return false;
    }

    public void generateChain() {
        generateDirections();

        if (amminoChain != null)
            amminoChain.removeAll(amminoChain);
        else amminoChain = new ArrayList<>();
        amminoChain.add(new Amminoacid(5, 5, readChar(0)));

        for (int i = 1; i < chainString.length(); i++) {
            switch (directions.get(i - 1)) {
                case WEST:
                    amminoChain.add(new Amminoacid(amminoChain.get(i - 1).getX() - 1, amminoChain.get(i - 1).getY(), readChar(i)));
                    break;
                case EAST:
                    amminoChain.add(new Amminoacid(amminoChain.get(i - 1).getX() + 1, amminoChain.get(i - 1).getY(), readChar(i)));
                    break;
                case NORTH:
                    amminoChain.add(new Amminoacid(amminoChain.get(i - 1).getX(), amminoChain.get(i - 1).getY() + 1, readChar(i)));
                    break;
                case SOUTH:
                    amminoChain.add(new Amminoacid(amminoChain.get(i - 1).getX(), amminoChain.get(i - 1).getY() - 1, readChar(i)));
                    break;
            }
        }

        for (int i = 0; i < amminoChain.size() - 1; i++) {
            amminoChain.get(i).setRight(amminoChain.get(i + 1));
            amminoChain.get(i + 1).setLeft(amminoChain.get(i));
        }
    }

    public void printChainConnections(){
        for(int i=0; i<amminoChain.size(); i++){
            if(i == 0){
                System.out.println(i);
                System.out.println("X: " + amminoChain.get(i).getX() + " Y:" + amminoChain.get(i).getY() + " Hydrophobic: " +amminoChain.get(i).isHydrophobic());
                System.out.println("Right X: " + amminoChain.get(i).getRight().getX() + " Right Y:" + amminoChain.get(i).getRight().getY() + " Hydrophobic: " +amminoChain.get(i).getRight().isHydrophobic());
            }

            else if(i == amminoChain.size()-1){
                System.out.println(i);
                System.out.println("X: " + amminoChain.get(i).getX() + " Y:" + amminoChain.get(i).getY() + " Hydrophobic: " +amminoChain.get(i).isHydrophobic());
                System.out.println("Left X: " + amminoChain.get(i).getLeft().getX() + " Left Y:" + amminoChain.get(i).getLeft().getY() + " Hydrophobic: " +amminoChain.get(i).getLeft().isHydrophobic());
            }

            else{
                System.out.println(i);
                System.out.println("X: " + amminoChain.get(i).getX() + " Y:" + amminoChain.get(i).getY() + " Hydrophobic: " +amminoChain.get(i).isHydrophobic());
                System.out.println("Left X: " + amminoChain.get(i).getLeft().getX() + " Left Y:" + amminoChain.get(i).getLeft().getY() + " Hydrophobic: " +amminoChain.get(i).getLeft().isHydrophobic());
                System.out.println("Right X: " + amminoChain.get(i).getRight().getX() + " Right Y:" + amminoChain.get(i).getRight().getY() + " Hydrophobic: " +amminoChain.get(i).getRight().isHydrophobic());
            }

        }
    }
}
