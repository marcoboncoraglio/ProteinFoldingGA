package Model;

/**
 * Created by marco on 11/05/17.
 */

public class Amminoacid {
    private Amminoacid left;
    private Amminoacid right;
    private boolean isHydrophobic;
    private int x;
    private int y;

    public Amminoacid(int x, int y, boolean isHydrophobic) {
        this.x = x;
        this.y = y;
        this.isHydrophobic = isHydrophobic;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Amminoacid getLeft() {
        return left;
    }

    public Amminoacid getRight() {
        return right;
    }

    public boolean isHydrophobic() {
        return isHydrophobic;
    }


    public void setLeft(Amminoacid left) {
        this.left = left;
    }

    public void setRight(Amminoacid right) {
        this.right = right;
    }

    public boolean isConnected(Amminoacid a){
        if(a.getRight() == this.right || a.getLeft() == this.left)
            return true;
        else return false;
    }
}
