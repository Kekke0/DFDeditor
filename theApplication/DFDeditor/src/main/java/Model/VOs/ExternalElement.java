package Model.VOs;

import Model.Coordinate;
import Model.Layer;
import javafx.scene.text.Font;

public class ExternalElement extends MultipliableObjects{

    public ExternalElement(Layer parent, String ID, Coordinate maincorner) {
        super(parent,ID, maincorner);
    }

    public ExternalElement(Layer parent, String ID, Coordinate maincorner, boolean multiplied) {
        super(parent,ID, maincorner, multiplied);
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+100, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+50);
        corners[3]=new Coordinate(maincorner.getX()+100, maincorner.getY()+50);
        setCorners(corners);
    }

    @Override
    public String getImagePath() {
        if(isMultiplied())
            return "/VObjects/MultiExternal.png";
        return "/VObjects/External.png";
    }
    @Override
    public String getTypeString(){
        return "EE";
    }

    protected void placeTexts() {
        getTexts()[1].setFont(new Font(20));
        getTexts()[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/3);
        getTexts()[1].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())/1.5);
    }
}
