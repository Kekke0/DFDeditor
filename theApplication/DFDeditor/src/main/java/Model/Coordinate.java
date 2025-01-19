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

    public Coordinate(Coordinate corner) {

        setX(corner.getX());
        setY(corner.getY());
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

    public static Coordinate getMiddleOf(Coordinate rhs, Coordinate lhs) {
        Coordinate midle = new Coordinate((rhs.getX() - lhs.getX()) / 2, (rhs.getY() - lhs.getY()) / 2);
        midle.add(lhs);
        return midle;
    }

    public static double distanceBetween(Coordinate rhs, Coordinate lhs) {
        double x = rhs.getX() - lhs.getX();
        double y = rhs.getY() - lhs.getY();
        return Math.abs(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
    }

    public static Coordinate[] intersectionArea(Coordinate c1,double r1,Coordinate c2, double r2)
    {
        double x1,y1,x2,y2;
        x1 = c1.getX();
        y1 = c1.getY();
        x2 = c2.getX();
        y2 = c2.getY();

        double apart,d1,h;
        apart = Coordinate.distanceBetween(c1,c2); // distance between two circles
        d1 = (Math.pow(r1,2)-Math.pow(r2,2)+Math.pow(apart,2))/(2*apart);
        h = Math.sqrt(Math.pow(r1,2)-Math.pow(d1,2));

        double x3,y3;
        x3 = x1+(d1*(x2-x1))/apart;
        y3 = y1 + (d1 * (y2-y1))/apart;

        Coordinate[] intersections = new Coordinate[2];

        int x41,y41;
        x41 = (int) (x3+(h*(y2-y1))/apart);
        y41 = (int) (y3-(h*(x2-x1))/apart);
        intersections[0] = new Coordinate(x41,y41);

        int x42,y42;
        x42 = (int) (x3-(h*(y2-y1))/apart);
        y42 = (int) (y3+(h*(x2-x1))/apart);
        intersections[1] = new Coordinate(x42,y42);
        return  intersections;
    }

}
