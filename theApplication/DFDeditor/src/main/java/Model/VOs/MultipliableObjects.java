package Model.VOs;

import Model.Coordinate;

public class MultipliableObjects extends VisualObject{
    private boolean Multiplied;

    public MultipliableObjects(String ID, Coordinate[] corners) {
        super(ID, corners);
    }

    public MultipliableObjects(String ID, Coordinate[] corners, boolean multiplied) {
        super(ID, corners);
        Multiplied = multiplied;
    }
}
