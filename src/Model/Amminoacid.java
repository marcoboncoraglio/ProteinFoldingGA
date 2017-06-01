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

    Amminoacid(int x, int y, boolean isHydrophobic) {
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

    Amminoacid getLeft() {
        return left;
    }

    Amminoacid getRight() {
        return right;
    }

    public boolean isHydrophobic() {
        return isHydrophobic;
    }

    void setLeft(Amminoacid left) {
        this.left = left;
    }

    void setRight(Amminoacid right) {
        this.right = right;
    }

    public boolean isConnected(Amminoacid a) {
        return a.getRight() == this || a.getLeft() == this;
    }
}
