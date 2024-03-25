module com.openjfx.dfdeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.swing;


    exports DataConverting.Model;
    exports Model;
    exports Model.VOs;
    opens Model to javafx.fxml;
    opens Model.VOs to javafx.fxml;
    opens com.openjfx.dfdeditor to javafx.fxml;
    exports com.openjfx.dfdeditor;
    exports com.openjfx.dfdeditor.EditConrollers;
    opens com.openjfx.dfdeditor.EditConrollers to javafx.fxml;
    exports com.openjfx.dfdeditor.FileManagement;
    opens com.openjfx.dfdeditor.FileManagement to javafx.fxml;
}