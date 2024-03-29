package Model.VOs;

import Model.Coordinate;
import Model.Layer;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Flow extends VisualObject{
    private String[] Connected= new String[2];
    private Coordinate Start;
    private Coordinate End;
    private Boolean onesided;
    private Line[] image_;

    public Flow(Layer parent, Coordinate start) {
        super(parent, "flow",start);
    }

    public Boolean isOnesided() {
        return onesided;
    }

    public void setOnesided(Boolean onesided) {
        this.onesided = onesided;
    }


    public String[] getConnected() {
        return Connected;
    }

    public void setConnected(String[] connected) {
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

    public void addConnection(String connection, boolean side){
        if (side){
            Connected[0]=connection;
            return;
        }
        Connected[1]=connection;
    }

    public Line[] getimage() {
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

        image_[1].setStartX(End.getX());
        image_[1].setStartY(End.getY());
        image_[1].setEndX(End.getX()-10);
        image_[1].setEndY(End.getY()-10);

        image_[2].setStartX(End.getX());
        image_[2].setStartY(End.getY());
        image_[2].setEndX(End.getX()-10);
        image_[2].setEndY(End.getY()+10);

        image_[3].setStartX(Start.getX());
        image_[3].setStartY(Start.getY());
        image_[3].setEndX(Start.getX()+10);
        image_[3].setEndY(Start.getY()-10);

        image_[4].setStartX(Start.getX());
        image_[4].setStartY(Start.getY());
        image_[4].setEndX(Start.getX()+10);
        image_[4].setEndY(Start.getY()+10);

    }

    @Override
    public void ResizeCorners(int corner, Double x, Double y) {
        getCorners()[corner].setX(x);
        getCorners()[corner].setY(y);
        resizeImageView();
    }


    private void arrowheadCalculation(){
        double xCoefficient = image_[0].getEndY()-image_[0].getStartY();
        double yCoefficient = image_[0].getEndX()-image_[0].getStartX();
        double rhs = xCoefficient * image_[0].getStartX() + yCoefficient * image_[0].getStartY();
        //(a)*(x-x1)=(b)⋅(y-y1)

        double RADIUS = 10;
        double yminus = image_[0].getEndY();
        double xminus = image_[0].getEndX();
        //(x-u)2+(y-v)2=r2
        double a,b,c;


    }

    private double masodfoku(double a,double b,double c){

        return 0;
    }

    @Override
    public void AddToLayer(Layer layer) {
        layer.addFlow(this);
    }
}
