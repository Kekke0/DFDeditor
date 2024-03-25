package Model;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

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

    public void addToJSON(JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName("X");
        jgen.writeString(String.valueOf(X));
        jgen.writeFieldName("Y");
        jgen.writeString(String.valueOf(Y));
        jgen.writeEndObject();
    }

    public void reverse(){
        this.setX(getX() * -1);
        this.setY(getY() * -1);
    }

}
