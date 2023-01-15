package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.Layer;
import Model.VOs.DataBase;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class MainController {
    private String Tool="Mouse";
    private int selectedCorner_;
    private VisualObject PreviewVO;
    private Coordinate grabPosition;
    private boolean dragging;
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
                        Drawable.setSeleccted(dtb);

                        Drawable.getChildren().remove(getPreviewVO().getImageView());
                        setTool("Mouse");
                    }
                    case "Mouse"->{
                        Drawable.Select(mouseEvent.getX(),mouseEvent.getY());
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
                        getPreviewVO().ChangePosition(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                    }
                    case "Mouse"->{
                    }
                    default -> {

                    }
                }
            }
        });

        Drawable.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Resize"->{
                        Drawable.ResizeSelected(selectedCorner_,mouseEvent.getX(), mouseEvent.getY());
                    }
                    case "Mouse"->{
                        if (dragging){
                            Coordinate moveto = new Coordinate(mouseEvent.getX(), mouseEvent.getY());
                            moveto.add(grabPosition);
                            Drawable.ChangeSelectedPosition(moveto);
                        }
                    }
                    default -> {

                    }
                }
            }
        });

        Drawable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "Databadder"->{
                    }
                    case "Mouse"->{
                        if (Drawable.getSeleccted()!=null){
                            if (Drawable.getSeleccted().isInside(mouseEvent.getX(), mouseEvent.getY())) {
                                dragging = true;
                                grabPosition = VisualObject.Distancing(Drawable.getSeleccted().getCorners()[0], new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                                grabPosition.reverse();
                                return;
                            }
                            int corner=Drawable.IsinCornerGui(mouseEvent.getX(), mouseEvent.getY());
                            if (corner!=-1){
                                selectedCorner_=corner;
                                setTool("Resize");
                            }
                        }
                    }
                    default -> {

                    }
                }
                //System.out.println("Tool: "+getTool()+" Dragging: "+dragging+", Selected corner: "+ selectedCorner_ );
            }
        });

        Drawable.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragging = false;
                if (Objects.equals(getTool(), "Resize"))
                    setTool("Mouse");

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
                dragging= false;
            }
        });
    }

    public void setKeyBindings(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case DELETE->{
                        if (Drawable.getSeleccted()!=null)
                            Drawable.DeleteSelected();

                    }
                    default -> {
                    }
                }

            }
        });
    }

}
