package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.Layer;
import Model.VOs.*;
import Model.VOs.VProcess;
import ToolHandlers.Setter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    public BorderPane BP;
    private final Setter setter_ = new Setter();
    private VisualObject PreviewVO;
    @FXML
    public Button externaladd;
    @FXML
    public Button processadd;
    @FXML
    public Button dataadd;

    @FXML
    public Button BackUp;

    @FXML
    public Button Down;

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
        setter_.SetToAddMode(Drawable, new Flow(Drawable, new Coordinate(0,0)));
    }

    public void databadder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new DataBase(Drawable,Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void processAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new VProcess(Drawable,Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }
    public void extrnalAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new ExternalElement(Drawable,Drawable.getLevel()+"#0#0",new Coordinate(0,0)));
    }

    public void BackingUp(ActionEvent e) {
        if (Drawable.getParentLayer() == null) {
            return;
        }
        Drawable = Drawable.getParentLayer();
        BP.setCenter(Drawable);
        Drawable.getChildren().add(BackUp);
        Drawable.getChildren().add(Down);
        BackUp.setVisible(Drawable.getParentLayer() != null);
    }

    public void GettingDeeper(ActionEvent e) {
        Layer drawable = Drawable.getSelected().GetLayer();
        if (drawable==null) {
            return;
        }
        Drawable = drawable;
        Drawable.getChildren().add(BackUp);
        Drawable.getChildren().add(Down);
        Drawable.setLevelDown(Down);
        BP.setCenter(Drawable);
        BackUp.setVisible(Drawable.getParentLayer() != null);
        Down.setVisible(false);
        //VAAAAAA
    }


    public void setKeyBindings(Scene scene) {
        this.scene_=scene;
        Drawable.setLevelDown(Down);
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

    public void saving(ActionEvent actionEvent) {
    }

    public void loading(ActionEvent actionEvent) {
    }

    public void exporting(ActionEvent actionEvent) {
    }
}
