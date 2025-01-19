package Model.VOs;

import Model.Coordinate;
import Model.Layer;
import javafx.scene.text.Font;

public class DataBase extends MultipliableObject {

    public DataBase(Layer parent, Coordinate maincorner) {
        super(parent, maincorner);
    }


    public DataBase(Layer parent, Coordinate maincorner, boolean multiplied) {
        super(parent, maincorner, multiplied);
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+150, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+50);
        corners[3]=new Coordinate(maincorner.getX()+150, maincorner.getY()+50);
        setCorners(corners);
    }

    @Override
    public String getImagePath() {
        if(isMultiplied())
            return "/VObjects/MultirDBvID.png";
        return "/VObjects/DBvID.png";
    }
    @Override
    public String getTypeString(){
        return "DB";
    }

    @Override
    public void placeTexts() {
        getTexts()[1].setFont(new Font(20));
        getTexts()[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/3);
        getTexts()[1].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())*2/3);
        getTexts()[1].setWrappingWidth(200);

        getTexts()[0].setFont(new Font(20));
        int eltolas = isMultiplied()?10:15 ;
        getTexts()[0].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/eltolas);
        getTexts()[0].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())*2/3);
        getTexts()[0].setWrappingWidth(50);
    }

    @Override
    public int Check(){
        int errors = super.Check();

        if(getID() == null){
            errors++;
            addWarningMsg("Missing ID");
        } else if(!getID().startsWith("D") || !getID().startsWith("M") || !getID().startsWith("T")){
            errors++;
            addWarningMsg("A Data base id must start with one of the following: \n \"D\" if its digital, an \"M\" if its manual, or a \"T\" if its temporary!\n");
        }
        return errors;
    }
}
