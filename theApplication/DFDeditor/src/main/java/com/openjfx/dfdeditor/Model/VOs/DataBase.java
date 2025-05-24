package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.text.Font;

import java.io.IOException;

public class DataBase extends MultipliableObject {

    private boolean physical = false;

    public DataBase(Layer parent, Coordinate maincorner) {
        super(parent, maincorner);
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
        if (isPhysical()) {
            if(isMultiplied())
                return "/com/openjfx/dfdeditor/VObjects/MultirDBvFID.png";
            return "/com/openjfx/dfdeditor/VObjects/DBvFID.png";
        }
        if(isMultiplied())
            return "/com/openjfx/dfdeditor/VObjects/MultirDBvID.png";
        return "/com/openjfx/dfdeditor/VObjects/DBvID.png";
    }
    @Override
    public String getTypeString(){
        return "DB";
    }

    @Override
    public void placeTexts() {
        getTexts()[1].setFont(new Font(15));
        getTexts()[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/3);
        getTexts()[1].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())*2/3);
        getTexts()[1].setWrappingWidth(200);

        getTexts()[0].setFont(new Font(15));
        getTexts()[0].setX(this.getCorners()[0].getX() +  (isMultiplied()?5:10));
        getTexts()[0].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())*2/3);
        getTexts()[0].setWrappingWidth(50);
    }

    @Override
    public int Check(){
        int errors = super.Check();

        if(getID() != null &&(!getID().startsWith("D") || !getID().startsWith("M") || !getID().startsWith("T"))){
            errors++;
            addWarningMsg("A Database id must start with one of the following: \n \"D\" if its digital, an \"M\" if its manual, or a \"T\" if its temporary!\n");
        }
        return errors;
    }

    public boolean isPhysical() {
        return physical;
    }

    public void setPhysical(boolean physical) {
        this.physical = physical;
    }

    @Override
    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners(), isMultiplied(), isPhysical());
    }
}
