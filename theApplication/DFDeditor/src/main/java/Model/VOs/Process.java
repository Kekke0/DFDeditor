package Model.VOs;

import Model.Coordinate;
import Model.Layer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Process extends VisualObject{
    private String OrgUnit;
    private boolean Dissociable;
    private Layer LowerLayer;

    public Process(String ID, Coordinate maincorner) {
        super(ID, maincorner);
        setDissociable(false);
    }

    public void inicalizeTexts() {
        setTexts(new Text[3]);
        getTexts()[0]=new Text(getName());
        getTexts()[1]=new Text(getName());
        getTexts()[2]=new Text(getOrgUnit());
    }

    @Override
    protected void placeTexts() {
        getTexts()[2].setFont(new Font(20));
        getTexts()[2].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())*(1.3)/3);
        getTexts()[2].setY(this.getCorners()[0].getY() + 25);
        getTexts()[2].setStrokeWidth(100);

        getTexts()[1].setFont(new Font(20));
        getTexts()[1].setX(this.getCorners()[0].getX() + 50);
        getTexts()[1].setY(this.getCorners()[3].getY() - 20);
        getTexts()[1].setWrappingWidth(150);

        getTexts()[0].setFont(new Font(20));
        getTexts()[0].setX(this.getCorners()[0].getX() + 10);
        getTexts()[0].setY(this.getCorners()[0].getY() + 25);
        getTexts()[0].setWrappingWidth(50);
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
        getTexts()[2].setText(OrgUnit);
    }

    public boolean isDissociable() {
        return Dissociable;
    }

    public void setDissociable(boolean dissociable) {
        Dissociable = dissociable;
    }

    public Layer getLowerLayer() {
        return LowerLayer;
    }

    public void setLowerLayer(Layer lowerLayer) {
        setDissociable(true);
        LowerLayer = lowerLayer;
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+200, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+100);
        corners[3]=new Coordinate(maincorner.getX()+200, maincorner.getY()+100);
        setCorners(corners);
    }

    @Override
    public String getImagePath() {
        return isDissociable() ? "/VObjects/Process.png" : "/VObjects/EProcess.png";
    }

    @Override
    public void showConsol() {
        super.showConsol();
        if (isDissociable()) {
            System.out.print(": \n");
            getLowerLayer().consolShow();
        }
    }
    @Override
    public String getTypeString(){
        return "PR";
    }
}
