package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.EditinStage;
import Model.Layer;
import Model.VOs.*;
import Model.VOs.Process;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    private String Tool="Mouse";
    private int selectedCorner_;
    private VisualObject PreviewVO;
    private Coordinate grabPosition;
    private boolean dragging;
    @FXML
    public Button externaladd;
    @FXML
    public Button processadd;
    @FXML
    public Button dataadd;
    @FXML
    public Layer Drawable;

    @FXML
    private Scene scene_;

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

    public void flowadder(ActionEvent e) {
        setTool("FlowAdder");
        setPreviewVO(new Flow(new Coordinate(0,0)));
    }

    public void databadder(ActionEvent e) {
        setTool("Databadder");
        setPreviewVO(new DataBase(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void processAdder(ActionEvent e) {
        setTool("ProcessAdder");
        setPreviewVO(new Process(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }
    public void extrnalAdder(ActionEvent e) {
        setTool("ExternalAdder");
        setPreviewVO(new ExternalElement(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void setEventHandlers(){
        Drawable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton()== MouseButton.PRIMARY) {
                    switch (getTool()) {
                        case "FlowAdder" -> {
                            Flow flw = new Flow(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                            Drawable.addFlow(flw);
                            Drawable.setSelected(flw);

                            Drawable.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "Databadder" -> {
                            DataBase dtb = new DataBase(Drawable.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            Drawable.addVO(dtb);
                            Drawable.setSelected(dtb);

                            Drawable.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "ExternalAdder" -> {
                            ExternalElement extel = new ExternalElement(Drawable.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            Drawable.addVO(extel);
                            Drawable.setSelected(extel);

                            Drawable.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "ProcessAdder" -> {
                            Process proc = new Process(Drawable.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            Drawable.addVO(proc);
                            Drawable.setSelected(proc);

                            Drawable.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "Mouse" -> {
                            Drawable.Select(mouseEvent.getX(), mouseEvent.getY());
                        }
                        default -> {

                        }
                    }
                } else if (mouseEvent.getButton()==MouseButton.SECONDARY){
                    if(Drawable.getSelected()!= null && Drawable.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())){
                        try {
                            new EditinStage(Drawable.getSelected());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Drawable.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (getTool()){
                    case "FlowAdder":
                    case "ExternalAdder":
                    case "ProcessAdder":
                    case "Databadder":
                        getPreviewVO().ChangePosition(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                        break;
                    default :
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
                    case "Mouse"->{
                        if (Drawable.getSelected()!=null){
                            int corner=Drawable.IsinCornerGui(mouseEvent.getX(), mouseEvent.getY());
                            if (corner!=-1){
                                selectedCorner_=corner;
                                setTool("Resize");
                                return;
                            }
                            if (Drawable.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())) {
                                dragging = true;
                                grabPosition = VisualObject.Distancing(Drawable.getSelected().getCorners()[0], new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                                grabPosition.reverse();
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
        this.scene_=scene;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case DELETE->{
                        if (Drawable.getSelected()!=null)
                            Drawable.DeleteSelected();

                    }
                    default -> {
                    }
                }

            }
        });
    }

}
