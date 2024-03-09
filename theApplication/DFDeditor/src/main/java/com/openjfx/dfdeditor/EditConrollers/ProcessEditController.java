package com.openjfx.dfdeditor.EditConrollers;
import Model.EditingStage;
import Model.VOs.VProcess;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ProcessEditController {
    @FXML
    public TextField OrgUnit;
    VProcess Edited;
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
        Edited = (VProcess) vo;
        IDField.setText(Edited.getID());
        NameField.setText(Edited.getName());
        MultyBox.setSelected(Edited.isDissociable());
        OrgUnit.setText(Edited.getOrgUnit());

    }

    public void onApply(ActionEvent e) {
        Edited.setID(IDField.getText());
        Edited.setName(NameField.getText());
        Edited.setDissociable(MultyBox.isSelected());
        Edited.setOrgUnit(OrgUnit.getText());
        Edited.setImage();
        Edited.getParent().setSelected(Edited);
        EStage.close();
    }
}
