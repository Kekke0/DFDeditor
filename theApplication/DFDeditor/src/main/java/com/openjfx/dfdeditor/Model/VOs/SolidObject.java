package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Connection;
import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class SolidObject extends  VisualObject{
    private final Map<Flow, Connection> Connections = new HashMap<>();
    private OpenProcess owner_;

    public SolidObject(Layer parent, Coordinate maincorner) {
        super(parent,maincorner);
    }

    public void AddConnection(Connection cn){
        Connections.put(cn.getConnecting(),cn);
    }

    public void RemoveConnection(Flow fl){
        Connections.get(fl).RemoveFromBoth();
    }
    public void RemoveFromConnection(Connection cn){
        Connections.remove(cn.getConnecting());
    }

    @Override
    public void AddToLayer(Layer layer) {
        layer.addVO(this);
    }

    public void MoveConnecteds() {
        if (getWarning() != null){
            getWarning().ChangePosition(getCorners()[0]);
        }

        if (Connections == null){
            return;
        }

        for (Connection cn:Connections.values()) {
            cn.AlignToConnected();
        }
    }

    public void ResizeCorners(int corner, Double x, Double y) {

        Coordinate distance= Distancing(getCorners()[corner],new Coordinate(x, y));
        switch (corner){
            case 0->{
                if (y<= getCorners()[2].getY()-50){
                    getCorners()[1].setY(getCorners()[1].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x<=getCorners()[1].getX()-90) {
                    getCorners()[2].setX(getCorners()[2].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            case 1->{
                if (y<= getCorners()[3].getY()-50){
                    getCorners()[0].setY(getCorners()[0].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x>=getCorners()[0].getX()+90) {
                    getCorners()[3].setX(getCorners()[3].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            case 2->{
                if (y>= getCorners()[0].getY()+50) {
                    getCorners()[3].setY(getCorners()[3].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x<=getCorners()[3].getX()-90){
                    getCorners()[0].setX(getCorners()[0].getX()+distance.getX());
                    getCorners()[corner].setX(x);
                }

            }
            case 3->{
                if (y>= getCorners()[0].getY()+50) {
                    getCorners()[2].setY(getCorners()[2].getY() + distance.getY());
                    getCorners()[corner].setY(y);
                }
                if (x>=getCorners()[2].getX()+90) {
                    getCorners()[1].setX(getCorners()[1].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            default -> {

            }
        }


        resizeImageView();
    }

    public void resizeImageView(){
        getImageView().setX(this.getCorners()[0].getX());
        getImageView().setY(this.getCorners()[0].getY());
        getImageView().setFitHeight(this.getCorners()[2].getY()-this.getCorners()[0].getY());
        getImageView().setFitWidth(this.getCorners()[1].getX()-this.getCorners()[0].getX());
        placeTexts();
        MoveConnecteds();
    }

    @Override
    public Node[] getimage() {
        Node[] image = new Node[1];
        image[0] = getImageView();
        return image;
    }

    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners());
    }

    public int Check(){
        super.Check();

        int errors = 0;
        if(getName() == null || getName().isEmpty()){
            errors++;
            addWarningMsg("Missing Name");
        }
        if(getID() == null || getID().isEmpty()){
            errors++;
            addWarningMsg("Missing ID  ");
        }
        return errors;
    }


    public void ChangePosition(Coordinate newPosition, boolean storable) {
        if (storable) {
            this.ChangePosition(newPosition);
        } else {
            super.ChangePosition(newPosition);
        }
    }

    @Override
    public void ChangePosition(Coordinate newPosition) {
        super.ChangePosition(newPosition);
        OpenProcess owner = getLayer().IsInsideOfOpenProcess(newPosition);
        this.setOwner(owner);
        getLayer().HighlightInnerLines(owner);

    }

    public SolidObject getOwner() {
        return owner_;
    }

    public void setOwner(OpenProcess owner) {
        if (this.owner_!= null){
            this.owner_.RemoveStored(this);
        }
        this.owner_ = owner;
        if (owner != null) {
            owner.AddStored(this);
        }
    }

    public void ReAdd() {
        getLayer().removeVO(this);
        getLayer().addVO(this);
    }
}
