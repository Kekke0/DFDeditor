package Model.VOs;

import DataConverting.Model.JSONVisualObject;
import Model.Coordinate;
import Model.Layer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

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

    @Override
    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners(), Multiplied);
    }

}
