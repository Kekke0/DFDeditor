package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.Layer;
import Model.VOs.*;
import Model.VOs.Process;
import ToolHandlers.Setter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class MainController {
    private final Setter setter_ = new Setter();
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

    public VisualObject getPreviewVO() {
        return PreviewVO;
    }

    public Setter Setter() {
        return setter_;
    }

    public void setPreviewVO(VisualObject previewVO) {
        PreviewVO = previewVO;
    }

    public void flowadder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new Flow(new Coordinate(0,0)));
    }

    public void databadder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new DataBase(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void processAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new Process(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }
    public void extrnalAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new ExternalElement(Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
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
