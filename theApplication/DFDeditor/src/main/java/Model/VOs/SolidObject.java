package Model.VOs;

import DataConverting.Model.JSONVisualObject;
import Model.Connection;
import Model.Coordinate;
import Model.Layer;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class SolidObject extends  VisualObject{
    private final Map<Flow, Connection> Connections = new HashMap<>();

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
                if (x>=getCorners()[3].getX()+90) {
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

    public JSONVisualObject transformToJVO() throws IOException {
        return new JSONVisualObject(getTypeString(),getID(), getName(), getCorners());
    }

    public int Check(){
        super.Check();
        return 0;
    }
}
