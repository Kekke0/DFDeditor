package com.openjfx.dfdeditor.EditConrollers;


import Model.EditinStage;
import Model.VOs.DataBase;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DataeditController extends UEditController {
    public CheckBox MultyBox;
    public TextField IDField;
    public TextField NameField;
    public Button Cancel;
    public Button Apply;
    private DataBase Edited;
    private EditinStage EStage;



    public void setEdited(VisualObject vo){
        Edited = (DataBase) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isMultiplied());
    }
    public void addStage(EditinStage stage){
        EStage = stage;
    }

    public void onCancel(ActionEvent e) {
        EStage.close();
    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setMultiplied(MultyBox.isSelected());
        Edited.setImage();
        Edited.placeTexts();
        EStage.close();
    }
}
