package com.openjfx.dfdeditor.EditConrollers;

import Model.EditinStage;
import Model.VOs.ExternalElement;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class UEditController {
    VisualObject Edited;
    @FXML
    public TextField IDField;
    @FXML
    public TextField NameField;
    @FXML
    public Button Cancel;
    @FXML
    public Button Apply;
    private EditinStage EStage;

    public void addStage(EditinStage stage){
        EStage = stage;
    }

    public void onCancel(ActionEvent e) {
        EStage.close();
    }
    public void setEdited(VisualObject vo){
        Edited = vo;
    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setImage();
        EStage.close();
    }
}
