package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Warning extends SolidObject{

    private final List<String> message = new ArrayList<String>();
    private Tooltip tooltip;


    public Warning(Layer layer) {
        super(layer,new Coordinate(0,0));
        AddToLayer(layer);
    }

    public Warning(VisualObject vo) {
        super(vo.getLayer(), vo.getCorners()[0]);
        AddToLayer(vo.getLayer());
        vo.setWarning(this);
    }

    @Override
    public String getImagePath() {
        return "/VObjects/warning-sign.256x256.png";
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate realCorner = getRealCorner(maincorner);

        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(realCorner.getX(),realCorner.getY());
        corners[1]=new Coordinate(realCorner.getX()+20, realCorner.getY());
        corners[2]=new Coordinate(realCorner.getX(), realCorner.getY()+20);
        corners[3]=new Coordinate(realCorner.getX()+20, realCorner.getY()+20);
        setCorners(corners);
    }

    public void ChangePosition(Coordinate maincorner) {
        super.ChangePosition(getRealCorner(maincorner));
    }

    private Coordinate getRealCorner(Coordinate maincorner) {
        Coordinate realCorner = new Coordinate(maincorner);
        realCorner.add(new Coordinate(maincorner.getX()<25? 10: -20, maincorner.getY()<25? 10: -25));
        return realCorner;
    }

    @Override
    public String getTypeString(){
        return "Warning";
    }

    @Override
    public void AddToLayer(Layer layer) {
        layer.addWarning(this);
    }

    @Override
    public ImageView getImageView(){
        ImageView imageView = super.getImageView();
        Tooltip toolTip = new Tooltip(this.genWarningMsg());
        Tooltip.install(imageView, toolTip);
        this.tooltip = toolTip;
        return imageView;
    }

    public String genWarningMsg(){
        StringBuilder toolTipTxt = new StringBuilder();
        toolTipTxt.append("Warning!").append("\n");
        if (message != null){
            for (String s : message) {
                toolTipTxt.append(s).append("\n");
            }
        }
        return toolTipTxt.toString();
    }

    public List<String> getMessage() {
        return message;
    }

    public void addMessage(String message) {
        this.message.add(message);
        this.tooltip.setText(this.genWarningMsg());
    }

    public void addWarningMsg(String msg){}

    @Override
    public int Check(){
        return 0;
    }

    public void showDialoge() {
        Object[] options = {"Delete warning",
                "Close"};
        int response = JOptionPane.showOptionDialog(new JFrame(),
                genWarningMsg(),
                "Messages",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]);
        if (response == JOptionPane.YES_OPTION) {
            this.getLayer().removeWarning(this);
        }
    }
}
