package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Connection;
import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.IOException;

public class Flow extends VisualObject{
    private Connection[] Connected= new Connection[2];
    private Coordinate Start;
    private Coordinate End;
    private Boolean onesided;
    private Line[] image_;

    public Flow(Layer parent, Coordinate start) {
        super(parent,start);
    }

    public Boolean isOnesided() {
        return onesided;
    }

    public void setOnesided(Boolean onesided) {
        this.onesided = onesided;
    }


    public Connection[] getConnected() {
        return Connected;
    }

    public void setConnected(Connection[] connected) {
        Connected[0] = connected[0];
        Connected[1] = connected[1];
    }

    public Coordinate getStart() {
        return Start;
    }

    public void setStart(Coordinate start) {
        Start = start;
    }

    public Coordinate getEnd() {
        return End;
    }

    public void setEnd(Coordinate end) {
        End = end;
    }

    public void addConnection(Connection connection, boolean side){
        int sideint = side?0:1;
        if (Connected[sideint] != null){
            Connected[sideint].RemoveFromBoth();
        }
        Connected[sideint]=connection;
    }

    public void RemoveConnection(int side){
        if (Connected[side] == null){
            return;
        }
        Connected[side].RemoveFromBoth();
    }

    public void RemoveFromConnection(int side){
        Connected[side]= null;
    }

    @Override
    public Node[] getimage() {
        return image_;
    }

    public void setBasicCorners(Coordinate maincorner) {
        Start = maincorner;
        End = new Coordinate(Start.getX()+100,Start.getY());
        inicalizeImage();
    }

    private void inicalizeImage() {
        image_ = new Line[5];
        image_[0]=new Line(Start.getX(), Start.getY(), End.getX(), End.getY());
        image_[1]=new Line(End.getX(), End.getY(),End.getX()-10, End.getY()-10);
        image_[2]=new Line(End.getX(), End.getY(),End.getX()-10, End.getY()+10);
        image_[3]=new Line(Start.getX(), Start.getY(), Start.getX()+10, Start.getY()-10);
        image_[4]=new Line(Start.getX(), Start.getY(), Start.getX()+10, Start.getY()+10);
        setOnesided(true);
        image_[3].setVisible(false);
        image_[4].setVisible(false);

    }

    public boolean isInside(double x, double y){
        return (getStart().getX() + 20 > x && getStart().getY() + 20 > y && getStart().getX() - 20 < x && getStart().getY() - 20 < y)
                || (getEnd().getX() + 20 > x && getEnd().getY() + 20 > y && getEnd().getX() - 20 < x && getEnd().getY() - 20 < y);
    }
    public Coordinate[] getCorners(){
        Coordinate[] corners= new Coordinate[2];
        corners[0]=Start;
        corners[1]=End;

        return corners;
    }

    @Override
    public String getImagePath() {
        return "/VObjects/Flow.png";
    }

    @Override
    public String getTypeString(){
        return "FL";
    }


    @Override
    public void resizeImageView(){
        getImageView().setX(this.Start.getX());
        getImageView().setY(this.Start.getY());
        image_[0].setStartX(this.Start.getX());
        image_[0].setStartY(this.Start.getY());
        image_[0].setEndX(this.End.getX());
        image_[0].setEndY(this.End.getY());
        aligneArrows();
        placeTexts();
    }

    @Override
    protected void placeTexts() {
        getTexts()[1].setFont(new Font(20));
        getTexts()[1].setX(this.getCorners()[0].getX() + (-20) + (this.getCorners()[1].getX()-this.getCorners()[0].getX())/2);
        getTexts()[1].setY(this.getCorners()[0].getY() + 20 +(this.getCorners()[1].getY()-this.getCorners()[0].getY())/2);
    }

    public void aligneArrows() {
        image_[3].setVisible(!isOnesided());
        image_[4].setVisible(!isOnesided());

        Coordinate[] arrows = arrowEnds(End,Start);
        image_[1].setStartX(End.getX());
        image_[1].setStartY(End.getY());
        image_[1].setEndX(arrows[0].getX());
        image_[1].setEndY(arrows[0].getY());

        image_[2].setStartX(End.getX());
        image_[2].setStartY(End.getY());
        image_[2].setEndX(arrows[1].getX());
        image_[2].setEndY(arrows[1].getY());

        arrows = arrowEnds(Start,End);
        image_[3].setStartX(Start.getX());
        image_[3].setStartY(Start.getY());
        image_[3].setEndX(arrows[0].getX());
        image_[3].setEndY(arrows[0].getY());

        image_[4].setStartX(Start.getX());
        image_[4].setStartY(Start.getY());
        image_[4].setEndX(arrows[1].getX());
        image_[4].setEndY(arrows[1].getY());

    }

    public static Coordinate[] arrowEnds(Coordinate origo, Coordinate endPoint){

        double prec = (15/Coordinate.distanceBetween(origo,endPoint))+1;

        Coordinate quarterPoint = new Coordinate((origo.getX() - endPoint.getX()) / prec, (origo.getY() - endPoint.getY()) / prec);
        quarterPoint.add(endPoint);

        return Coordinate.intersectionArea(origo,Coordinate.distanceBetween(origo,quarterPoint)*1.5, quarterPoint,Coordinate.distanceBetween(origo,quarterPoint));
    }

    @Override
    public void ResizeCorners(int corner, Double x, Double y) {
        getCorners()[corner].setX(x);
        getCorners()[corner].setY(y);
        resizeImageView();
        MoveConnecteds();
    }

    public void ChangePosition(Coordinate maincorner) {
        if (maincorner.getX()<0) maincorner.setX(0);
        if (maincorner.getY()<0) maincorner.setY(0);
        Coordinate distance= Distancing(getCorners()[0],maincorner);
        for (Coordinate corner: getCorners()) {
            corner.add(distance);
        }
        resizeImageView();
        if (Connected[0]!=null) {
            Connected[0].RemoveFromBoth();
        }
        if (Connected[1]!=null) {
            Connected[1].RemoveFromBoth();
        }
        MoveConnecteds();
    }

    public double getLength() {
        return Coordinate.distanceBetween(getCorners()[0],getCorners()[1]);
    }


    @Override
    public void AddToLayer(Layer layer) {
        layer.addFlow(this);
    }

    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners(), onesided);
    }

    public void MoveConnecteds() {
        if (getWarning() != null){
            getWarning().ChangePosition(getCorners()[0]);
        }
    }

    public int Check(){
        int error = super.Check();

        if (this.Connected[0] != null && this.Connected[1] != null) {
            String[] types = new String[2];
            types[0] = this.Connected[0].getConnected().getTypeString();
            types[1] = this.Connected[0].getConnected().getTypeString();
            if (!types[1].equals("PR") && !types[0].equals("PR")){
                    error++;
                    addWarningMsg("This two object cannot be connected directly. Insert a Process to explain their interaction.");
            }

        }


        return 1;
    }
}
