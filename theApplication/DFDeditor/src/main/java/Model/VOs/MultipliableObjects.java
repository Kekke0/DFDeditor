package Model.VOs;

import Model.Coordinate;

public class MultipliableObjects extends VisualObject{
    private boolean Multiplied;

    public MultipliableObjects(Coordinate[] corners) {
        super(corners);
    }

    public MultipliableObjects(Coordinate[] corners, boolean multiplied) {
        super(corners);
        Multiplied = multiplied;
    }
}
