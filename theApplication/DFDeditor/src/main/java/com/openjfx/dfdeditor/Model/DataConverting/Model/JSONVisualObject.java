package com.openjfx.dfdeditor.Model.DataConverting.Model;

import com.openjfx.dfdeditor.Model.Coordinate;

public class JSONVisualObject {
    private String Type, ID, Name, OrgUnit;
    private Coordinate[] Corners;
    private Boolean Dissociable, Multiplied;
    private JSONVisualObject[] LowerLayer;

    public JSONVisualObject(){}
    public JSONVisualObject(String type, String ID, String name, Coordinate[] corners) {
        Type = type;
        this.ID = ID;
        Name = name;
        Corners = corners;
    }

    public JSONVisualObject(String type, String ID, String name, Coordinate[] corners, String orgUnit, Boolean dissociable, JSONVisualObject[] lowerLayer) {
        Type = type;
        this.ID = ID;
        Name = name;
        OrgUnit = orgUnit;
        Corners = corners;
        Dissociable = dissociable;
        LowerLayer = lowerLayer;
    }


    public JSONVisualObject(String type, String ID, String name, Coordinate[] corners, Boolean multiplied) {
        Type = type;
        this.ID = ID;
        Name = name;
        Corners = corners;
        Multiplied = multiplied;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
    }

    public Coordinate[] getCorners() {
        return Corners;
    }

    public void setCorners(Coordinate[] corners) {
        Corners = corners;
    }

    public Boolean getDissociable() {
        return Dissociable;
    }

    public void setDissociable(Boolean dissociable) {
        Dissociable = dissociable;
    }

    public Boolean getMultiplied() {
        return Multiplied;
    }

    public void setMultiplied(Boolean multiplied) {
        Multiplied = multiplied;
    }

    public JSONVisualObject[] getLowerLayer() {
        return LowerLayer;
    }

    public void setLowerLayer(JSONVisualObject[] lowerLayer) {
        LowerLayer = lowerLayer;
    }
}
