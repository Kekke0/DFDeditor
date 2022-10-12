package Model.VOs;

import Model.Coordinate;

public class ExternalElement extends MultipliableObjects{
    public ExternalElement(Coordinate[] corners) {
        super(corners);
    }

    public ExternalElement(Coordinate[] corners, boolean multiplied) {
        super(corners, multiplied);
    }
}
