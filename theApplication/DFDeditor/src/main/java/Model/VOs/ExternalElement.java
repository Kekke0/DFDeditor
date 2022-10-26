package Model.VOs;

import Model.Coordinate;

public class ExternalElement extends MultipliableObjects{

    public ExternalElement(String ID, Coordinate[] corners) {
        super(ID, corners);
    }

    public ExternalElement(String ID, Coordinate[] corners, boolean multiplied) {
        super(ID, corners, multiplied);
    }
}
