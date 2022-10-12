package Model.VOs;

import Model.Coordinate;
import Model.Layer;

public class Process extends VisualObject{
    private String OrgUnit;
    private boolean Dissociable;
    private Layer LowerLayer;

    public Process(Coordinate[] corners) {
        super(corners);
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
    }

    public boolean isDissociable() {
        return Dissociable;
    }

    public void setDissociable(boolean dissociable) {
        Dissociable = dissociable;
    }

    public Layer getLowerLayer() {
        return LowerLayer;
    }

    public void setLowerLayer(Layer lowerLayer) {
        LowerLayer = lowerLayer;
    }
}
