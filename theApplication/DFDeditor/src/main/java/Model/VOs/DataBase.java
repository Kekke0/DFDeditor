package Model.VOs;

import Model.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DataBase extends MultipliableObjects{

    public DataBase(String ID, Coordinate maincorner) {
        super(ID, maincorner);
    }


    public DataBase(String ID, Coordinate maincorner, boolean multiplied) {
        super(ID, maincorner, multiplied);
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


}
