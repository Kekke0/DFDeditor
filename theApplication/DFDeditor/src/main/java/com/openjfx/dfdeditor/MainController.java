package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.Layer;
import Model.VOs.DataBase;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainController {
    private String Tool="Mouse";
    private VisualObject PreviewVO;
    @FXML
    public Button dataadd;
    @FXML
    public Layer Drawable;

    public void setTool(String tool) {
        this.Tool = tool;
    }

    public String getTool() {
        return Tool;
    }

    public VisualObject getPreviewVO() {
        return PreviewVO;
    }

    public void setPreviewVO(VisualObject previewVO) {
        PreviewVO = previewVO;
    }

    public void databadder(ActionEvent e) {
        setTool("Databadder");
        setPreviewVO(new DataBase(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void setEventHandlers(){
        Drawable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Databadder"->{
                        DataBase dtb = new DataBase(Drawable.getLevel()+"sa", new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                        Drawable.addVO(dtb);

                        setTool("Mouse");
                    }
                    case "Mouse"->{
                        //Select action
                    }
                    default -> {

                    }
                }
            }
        });


        Drawable.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Databadder"->{
                        getPreviewVO().getImageView().setX(mouseEvent.getX());
                        getPreviewVO().getImageView().setY(mouseEvent.getY());
                    }
                    case "Mouse"->{
                        //Select action
                    }
                    default -> {

                    }
                }
            }
        });

        Drawable.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Random"->{

                    }
                    case "Mouse"->{
                    }
                    default -> {
                        Drawable.getChildren().add(getPreviewVO().getImageView());
                    }
                }
            }
        });

        Drawable.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Random"->{

                    }
                    case "Mouse"->{
                    }
                    default -> {
                        Drawable.getChildren().remove(getPreviewVO().getImageView());
                    }
                }
            }
        });
    }

}
