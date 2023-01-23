package com.openjfx.dfdeditor.EditConrollers;
import Model.EditinStage;
import Model.VOs.DataBase;
import Model.VOs.ExternalElement;
import Model.VOs.Process;
import Model.VOs.VisualObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ProcessEditController {
    @FXML
    public TextField OrgUnit;
    Process Edited;
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
    private EditinStage EStage;

    public void addStage(EditinStage stage){
        EStage = stage;
    }

    public void onCancel(ActionEvent e) {
        EStage.close();
    }
    public void setEdited(VisualObject vo){
        Edited = (Process) vo;
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
        EStage.close();
    }
}
