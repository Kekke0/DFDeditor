package Model;

public class Coordinate {
    private double X, Y;

    public Coordinate() {
        X = 0;
        Y = 0;
    }

    public Coordinate(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public void add(Coordinate distance){
        this.setX(this.X+distance.getX());
        this.setY(this.Y+distance.getY());
    }

    public void reverse(){
        this.setX(getX() * -1);
        this.setY(getY() * -1);
    }

}
