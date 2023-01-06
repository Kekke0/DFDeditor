package Model.VOs;

import Model.Coordinate;

public class ExternalElement extends MultipliableObjects{

    public ExternalElement(String ID, Coordinate maincorner) {
        super(ID, maincorner);
    }

    public ExternalElement(String ID, Coordinate maincorner, boolean multiplied) {
        super(ID, maincorner, multiplied);
    }
}
