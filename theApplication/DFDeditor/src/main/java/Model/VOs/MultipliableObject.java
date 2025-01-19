package Model.VOs;

import DataConverting.Model.JSONVisualObject;
import Model.Coordinate;
import Model.Layer;

import java.io.IOException;

public class MultipliableObject extends SolidObject{
    private boolean Multiplied;

    public MultipliableObject(Layer parent, Coordinate maincorner) {
        super(parent, maincorner);
    }

    public MultipliableObject(Layer parent, Coordinate maincorner, boolean multiplied) {
        super(parent, maincorner);
        Multiplied = multiplied;
    }

    public boolean isMultiplied() {
        return Multiplied;
    }

    public void setMultiplied(boolean multiplied) {
        Multiplied = multiplied;
    }

    @Override
    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners(), Multiplied);
    }

}
