module com.openjfx.dfdeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.swing;


    exports com.openjfx.dfdeditor;
    exports com.openjfx.dfdeditor.Model.DataConverting.Model;
    exports com.openjfx.dfdeditor.Model;
    exports com.openjfx.dfdeditor.Model.VOs;
    opens com.openjfx.dfdeditor to javafx.fxml;
    opens com.openjfx.dfdeditor.Model to javafx.fxml;
    opens com.openjfx.dfdeditor.Model.VOs to javafx.fxml;
    exports com.openjfx.dfdeditor.Controllers;
    opens com.openjfx.dfdeditor.Controllers to javafx.fxml;
    exports com.openjfx.dfdeditor.FileManagement;
    opens com.openjfx.dfdeditor.FileManagement to javafx.fxml;
}