package Model.VOs;

import Model.Coordinate;
import Model.Layer;

public class MultipliableObjects extends VisualObject{
    private boolean Multiplied;

    public MultipliableObjects(Layer parent,String ID, Coordinate maincorner) {
        super(parent, ID, maincorner);
    }

    public MultipliableObjects(Layer parent, String ID, Coordinate maincorner, boolean multiplied) {
        super(parent, ID, maincorner);
        Multiplied = multiplied;
    }

    public boolean isMultiplied() {
        return Multiplied;
    }

    public void setMultiplied(boolean multiplied) {
        Multiplied = multiplied;
    }
}
