module com.openjfx.dfdeditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens Model to javafx.fxml;
    opens com.openjfx.dfdeditor to javafx.fxml;
    exports com.openjfx.dfdeditor;
}