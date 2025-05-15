package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;

import java.io.IOException;
import java.util.Objects;

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

    @Override
    public int Check(){
        int error = super.Check();
        int count = 0;
        for(VisualObject vo:getLayer().getVOs()){
            if ((Objects.equals(vo.getName(), this.getName()) || Objects.equals(vo.getID(), this.getID())
                    && vo.getTypeString().equals(this.getTypeString()))) {
                count++;
            }
        }

        if ((count > 1) != Multiplied){
            addWarningMsg(Multiplied?"This object is marked as multiplied, but there is only one instance of it.":"This object shuld be marked as multiplied.");
            error++;
        }

        return error;
    }

}
