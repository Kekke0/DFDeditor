package com.openjfx.dfdeditor.Controllers;

import com.openjfx.dfdeditor.Model.EditingStage;
import com.openjfx.dfdeditor.Model.VOs.ExternalElement;
import com.openjfx.dfdeditor.Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ExternalEditController {
    ExternalElement Edited;
    @FXML
    public CheckBox MultyBox;
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
        Edited = (ExternalElement) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isMultiplied());
    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setMultiplied(MultyBox.isSelected());
        Edited.setImage();
        EStage.close();
    }
}
