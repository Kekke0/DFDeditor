package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class Process extends SolidObject{
    private String OrgUnit;
    private boolean Dissociable;
    private Layer LowerLayer;

    public Process(Layer parent, Coordinate maincorner) {
        super(parent, maincorner);
        LowerLayer = new Layer();
        LowerLayer.setParentLayer(parent);
        setDissociable(false);
    }

    public void initializeTexts() {
        setTexts(new Text[3]);
        getTexts()[0]=new Text(getID());
        getTexts()[1]=new Text(getName());
        getTexts()[2]=new Text(getOrgUnit());
    }

    @Override
    protected void placeTexts() {
        getTexts()[2].setFont(new Font(15));
        getTexts()[2].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())*(1.3)/3);
        getTexts()[2].setY(this.getCorners()[0].getY() + 22);
        getTexts()[2].setStrokeWidth(100);

        getTexts()[1].setFont(new Font(15));
        getTexts()[1].setX(this.getCorners()[0].getX() + 50);
        getTexts()[1].setY(this.getCorners()[3].getY() - 20);
        getTexts()[1].setWrappingWidth(150);

        getTexts()[0].setFont(new Font(15));
        getTexts()[0].setX(this.getCorners()[0].getX() + 10);
        getTexts()[0].setY(this.getCorners()[0].getY() + 22);
        getTexts()[0].setWrappingWidth(50);
    }

    public String getOrgUnit() {
        return OrgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        OrgUnit = orgUnit;
        getTexts()[2].setText(OrgUnit);
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
        setDissociable(true);
        LowerLayer = lowerLayer;
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+200, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+100);
        corners[3]=new Coordinate(maincorner.getX()+200, maincorner.getY()+100);
        setCorners(corners);
    }

    @Override
    public String getImagePath() {
        return isDissociable() ? "/com/openjfx/dfdeditor/VObjects/Process.png" : "/com/openjfx/dfdeditor/VObjects/EProcess.png";
    }

    @Override
    public Layer GetLayer(){
        if (LowerLayer == null) {
            return super.GetLayer();
        }
        return LowerLayer;
    }

    @Override
    public JSONVisualObject transformToJVO() throws IOException {
        int Vsize = LowerLayer.getVOs().size()+ LowerLayer.getFlows().size();
        JSONVisualObject[] layer = new JSONVisualObject[Vsize];
        int i = 0;
        for (VisualObject vo:LowerLayer.getVOs()) {
            layer[i] = vo.transformToJVO();
            i++;
        }
        for (VisualObject vo:LowerLayer.getFlows()) {
            layer[i] = vo.transformToJVO();
            i++;
        }

        return new JSONVisualObject(getTypeString(), getID(), getName(), getCorners(),String.valueOf(OrgUnit),Dissociable,layer);
    }

    @Override
    public void showConsol() {
        super.showConsol();
        if (isDissociable()) {
            System.out.print(": \n");
            getLowerLayer().consolShow();
        }
    }
    @Override
    public String getTypeString(){
        return "PR";
    }

    @Override
    public int Check(){
        int errors = super.Check();

        if (isDissociable()) {
            errors += LowerLayer.Check();

            if (LowerLayer.getVOs().size() == 0 && LowerLayer.getFlows().size() == 0) {
                addWarningMsg("This process is marked as dissociable but there is no implementation of lower layer.");
                errors++;
            }
        }


        return errors;
    }
}
