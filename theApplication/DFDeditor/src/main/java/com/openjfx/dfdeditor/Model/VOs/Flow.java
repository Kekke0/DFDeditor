package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.DataConverting.Model.JSONVisualObject;
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
    private boolean physical = false;
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
        image_ = new Line[11];
        image_[0]=new Line(Start.getX(), Start.getY(), End.getX(), End.getY());
        image_[1]=new Line(End.getX(), End.getY(),End.getX()-10, End.getY()-10);
        image_[2]=new Line(End.getX(), End.getY(),End.getX()-10, End.getY()+10);
        image_[3]=new Line(Start.getX(), Start.getY(), Start.getX()+10, Start.getY()-10);
        image_[4]=new Line(Start.getX(), Start.getY(), Start.getX()+10, Start.getY()+10);
        setOnesided(true);
        image_[3].setVisible(false);
        image_[4].setVisible(false);

        /// Paralels
        image_[5] = Coordinate.CreateNewLine(Coordinate.createParallelLines(Start,End,-5.0));
        image_[6] = Coordinate.CreateNewLine(Coordinate.createParallelLines(Start,End,5.0));

        /// Connectors
        /// 5-1
        image_[7] = new Line(image_[1].getEndX(), image_[1].getEndY(), image_[5].getEndX(), image_[5].getEndY());
        /// 5-3
        image_[8] = new Line(image_[3].getEndX(), image_[3].getEndY(), image_[5].getStartX(), image_[5].getStartY());
        /// 6-2
        image_[9]  = new Line(image_[2].getEndX(), image_[2].getEndY(), image_[6].getEndX(), image_[6].getEndY());
        /// 6-4
        image_[10]  = new Line(image_[4].getEndX(), image_[4].getEndY(), image_[6].getStartX(), image_[6].getStartY());


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
        return "/com/openjfx/dfdeditor/VObjects/Flow.png";
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
        getTexts()[1].setFont(new Font(15));
        getTexts()[1].setX(this.getCorners()[0].getX() + (-20) + (this.getCorners()[1].getX()-this.getCorners()[0].getX())/2);
        getTexts()[1].setY(this.getCorners()[0].getY() + 20 +(this.getCorners()[1].getY()-this.getCorners()[0].getY())/2);

        getTexts()[0].setFont(new Font(15));
        getTexts()[0].setX(this.getCorners()[0].getX() + (-20) + (this.getCorners()[1].getX()-this.getCorners()[0].getX())/2);
        getTexts()[0].setY(this.getCorners()[0].getY() - 10 +(this.getCorners()[1].getY()-this.getCorners()[0].getY())/2);
    }

    public void aligneArrows() {
        image_[3].setVisible(!isOnesided());
        image_[4].setVisible(!isOnesided());
        image_[0].setVisible(!isPhysical());
        for (int i = 5; i < 10; i++) {
            image_[i].setVisible(isPhysical());
        }
        image_[10].setVisible(isPhysical() && !isOnesided());

        /// Paralels
        Coordinate[] parallelNeg =  Coordinate.createParallelLines(Start,End,-5.0);
        Coordinate[] parallelPoz =  Coordinate.createParallelLines(Start,End,5.0);

        Coordinate[] EndArrows = GetArrowEnds(End,Start);
        Coordinate.AlignLinetoCoordinates(image_[1],End,EndArrows[0]);
        Coordinate.AlignLinetoCoordinates(image_[2],End,EndArrows[1]);

        Coordinate[] StartArrows = GetArrowEnds(Start,End);
        Coordinate.AlignLinetoCoordinates(image_[3],Start,StartArrows[0]);
        Coordinate.AlignLinetoCoordinates(image_[4],Start,StartArrows[1]);

        Coordinate negIntersection1 = Coordinate.GetIntersectionOfLines(parallelNeg[0],parallelNeg[1],EndArrows[0],EndArrows[1]);
        parallelNeg[1] = negIntersection1== null? parallelNeg[1]:negIntersection1;
        Coordinate pozIntersection1 =  Coordinate.GetIntersectionOfLines(parallelPoz[0],parallelPoz[1],EndArrows[0],EndArrows[1]);
        parallelPoz[1] = pozIntersection1 == null? parallelPoz[1]:pozIntersection1;
        if (!this.isOnesided()) {
            Coordinate negIntersection0 = Coordinate.GetIntersectionOfLines(parallelNeg[0],parallelNeg[1],StartArrows[0],StartArrows[1]);
            parallelNeg[0] = negIntersection0 == null ? parallelNeg[0] :negIntersection0;
            Coordinate pozIntersection0 = Coordinate.GetIntersectionOfLines(parallelPoz[0],parallelPoz[1],StartArrows[0],StartArrows[1]);
            parallelPoz[0] = pozIntersection0 == null ? parallelPoz[0] :pozIntersection0;
        }
        Coordinate.AlignLinetoCoordinates(image_[5],parallelNeg[0],parallelNeg[1]);
        Coordinate.AlignLinetoCoordinates(image_[6], parallelPoz[0], parallelPoz[1]);

        /// Connectors
        /// 5-1
        Coordinate.AlignLinetoCoordinates(image_[7],EndArrows[1],parallelNeg[1]);
        //image_[7] = new Line(image_[1].getEndX(), image_[1].getEndY(), image_[5].getEndX(), image_[5].getEndY());
        /// 6-2
        //image_[9]  = new Line(image_[2].getEndX(), image_[2].getEndY(), image_[6].getEndX(), image_[6].getEndY());
        Coordinate.AlignLinetoCoordinates(image_[9],EndArrows[0],parallelPoz[1]);
        /// 5-3
        //image_[8] = new Line(image_[3].getEndX(), image_[3].getEndY(), image_[5].getStartX(), image_[5].getStartY());
        Coordinate.AlignLinetoCoordinates(image_[8], isOnesided() ? parallelPoz[0] : StartArrows[0], parallelNeg[0]);
        /// 6-4
        //image_[10]  = new Line(image_[4].getEndX(), image_[4].getEndY(), image_[6].getStartX(), image_[6].getStartY());
        Coordinate.AlignLinetoCoordinates(image_[10],StartArrows[1],parallelPoz[0]);

    }

    public static Coordinate[] GetArrowEnds(Coordinate origo, Coordinate endPoint){

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

    public void ChangePosition(Coordinate newPosition) {
        if (newPosition.getX()<0) newPosition.setX(0);
        if (newPosition.getY()<0) newPosition.setY(0);
        Coordinate distance= Distancing(getCorners()[0], newPosition);
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
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners(), isOnesided(), isPhysical());
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


        return error;
    }

    public boolean isPhysical() {
        return physical;
    }

    public void setPhysical(boolean physical) {
        this.physical = physical;
    }
}
