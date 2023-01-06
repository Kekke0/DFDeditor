package Model.VOs;

import Model.Coordinate;

public class MultipliableObjects extends VisualObject{
    private boolean Multiplied;

    public MultipliableObjects(String ID, Coordinate maincorner) {
        super(ID, maincorner);
    }

    public MultipliableObjects(String ID, Coordinate maincorner, boolean multiplied) {
        super(ID, maincorner);
        Multiplied = multiplied;
    }

    public boolean isMultiplied() {
        return Multiplied;
    }

    public void setMultiplied(boolean multiplied) {
        Multiplied = multiplied;
    }
}
