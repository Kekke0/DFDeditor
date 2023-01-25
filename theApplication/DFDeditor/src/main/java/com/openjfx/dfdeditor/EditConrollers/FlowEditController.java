package com.openjfx.dfdeditor.EditConrollers;

import Model.EditinStage;
import Model.VOs.DataBase;
import Model.VOs.Flow;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FlowEditController {
    public CheckBox MultyBox;
    public TextField IDField;
    public TextField NameField;
    private Flow Edited;
    private EditinStage EStage;



    public void setEdited(VisualObject vo){
        Edited = (Flow) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isOnesided());
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
        Edited.setOnesided(MultyBox.isSelected());
        Edited.aligneArrows();
        EStage.close();
    }
}
