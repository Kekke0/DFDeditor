package Model.VOs;

import Model.Coordinate;
import Model.Layer;
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
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
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
        if(isDissociable())
            return "/VObjects/Process.png";
        return "/VObjects/EProcess.png";
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
