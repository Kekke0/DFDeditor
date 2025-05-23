package com.openjfx.dfdeditor.Controllers;

import com.openjfx.dfdeditor.Model.EditingStage;
import com.openjfx.dfdeditor.Model.VOs.Flow;
import com.openjfx.dfdeditor.Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FlowEditController {
    public CheckBox MultyBox;
    public TextField IDField;
    public TextField NameField;
    public CheckBox PhysicalBox;
    private Flow Edited;
    private EditingStage EStage;



    public void setEdited(VisualObject vo){
        Edited = (Flow) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isOnesided());
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
        Edited.setOnesided(MultyBox.isSelected());
        Edited.setPhysical(PhysicalBox.isSelected());
        Edited.aligneArrows();
        EStage.close();
    }
}
