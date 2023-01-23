package Model.VOs;

import Model.Coordinate;

public class ExternalElement extends MultipliableObjects{

    public ExternalElement(String ID, Coordinate maincorner) {
        super(ID, maincorner);
    }

    public ExternalElement(String ID, Coordinate maincorner, boolean multiplied) {
        super(ID, maincorner, multiplied);
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
}
