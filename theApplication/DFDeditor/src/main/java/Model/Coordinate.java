package Model;

public class Coordinate {
    private int X, Y;

    public Coordinate(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public void add(Coordinate distance){
        this.setX(X+distance.getX());
        this.setY(Y+distance.getY());
    }

}
