package com.openjfx.dfdeditor;

import Model.Coordinate;
import Model.FileManagementType;
import Model.Layer;
import Model.VOs.*;
import Model.VOs.Process;
import ToolHandlers.Setter;
import com.openjfx.dfdeditor.FileManagement.FileMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

    public Setter Setter() {
        return setter_;
    }


    public void flowadder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new Flow(Drawable, new Coordinate(0,0)));
    }

    public void databadder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new DataBase(Drawable,new Coordinate(0,0)));
    }

    public void processAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new Process(Drawable,new Coordinate(0,0)));
    }
    public void extrnalAdder(ActionEvent e) {
        setter_.SetToAddMode(Drawable, new ExternalElement(Drawable,new Coordinate(0,0)));
    }

    public void BackingUp() {
        if (Drawable.getParentLayer() == null) {
            return;
        }
        ChangingLayer(Drawable.getParentLayer());
    }

    public void GettingDeeper(ActionEvent e) {
        Layer drawable = Drawable.getSelected().GetLayer();
        if (drawable==null) {
            return;
        }
        ChangingLayer(drawable);
        Drawable.setLevelDown(Down);
        Drawable.setBackUp(BackUp);
        Down.setVisible(false);

    }

    public void ChangingLayer(Layer layer) {
        Drawable = layer;
        BP.setCenter(Drawable);
        Drawable.getChildren().add(BackUp);
        Drawable.getChildren().add(Down);
        BackUp.setVisible(Drawable.getParentLayer() != null);
    }


    public void setKeyBindings(Scene scene) {
        Drawable.setLevelDown(Down);
        Drawable.setBackUp(BackUp);
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
        try {
            new FileMenu(FileManagementType.SAVE,Drawable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loading(ActionEvent actionEvent) {
        try {
            FileMenu fm = new FileMenu(FileManagementType.LOAD,Drawable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exporting(ActionEvent actionEvent) {
        try {
            new FileMenu(FileManagementType.EXPORT,Drawable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scanning(ActionEvent actionEvent) {
        Layer topLayer = Drawable;
        while (topLayer.getParentLayer() != null){
            topLayer = topLayer.getParentLayer();
        }
        int created = topLayer.Check();
        JOptionPane.showMessageDialog(new Frame(),created>0?"While scanning thew diagram the system found "+created +" errors.":"No error was found in the diagram.",
                "Result of Scanning",created>0?JOptionPane.WARNING_MESSAGE:JOptionPane.INFORMATION_MESSAGE);

    }
}
