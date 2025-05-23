package com.openjfx.dfdeditor.Controllers;

import com.openjfx.dfdeditor.Model.EditingStage;
import com.openjfx.dfdeditor.Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private EditingStage EStage;

    public void addStage(EditingStage stage){
        EStage = stage;
    }

    public void onCancel(ActionEvent e) {
        EStage.close();
    }
    public void setEdited(VisualObject vo){
        Edited = vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setImage();
        EStage.close();
    }
}
