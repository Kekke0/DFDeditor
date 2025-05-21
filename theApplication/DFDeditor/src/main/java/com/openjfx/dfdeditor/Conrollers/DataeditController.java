package com.openjfx.dfdeditor.Conrollers;


import com.openjfx.dfdeditor.Model.EditingStage;
import com.openjfx.dfdeditor.Model.VOs.DataBase;
import com.openjfx.dfdeditor.Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class DataeditController extends UEditController {
    public CheckBox MultyBox;
    public TextField IDField;
    public TextField NameField;
    public CheckBox PhysicalBox;
    private DataBase Edited;
    private EditingStage EStage;



    public void setEdited(VisualObject vo){
        Edited = (DataBase) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isMultiplied());
        PhysicalBox.setSelected(Edited.isPhysical());
    }
    public void addStage(EditingStage stage){
        EStage = stage;
    }

    public void onCancel(ActionEvent e) {
        EStage.close();
    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setMultiplied(MultyBox.isSelected());
        Edited.setPhysical(PhysicalBox.isSelected());
        Edited.setImage();
        Edited.placeTexts();
        EStage.close();
    }
}
