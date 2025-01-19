package com.openjfx.dfdeditor.FileManagement;

import Model.EditingStage;
import Model.FileManagementType;
import Model.Layer;
import com.openjfx.dfdeditor.EditConrollers.DataeditController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Model.FileManagementType.LOAD;

public class FileMenu extends Stage {

    FileManagementType TYPE;

    public FileMenu(FileManagementType type, Layer layer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getView(type)));
        Scene scene = new Scene(loader.load());

        FileManagementController controller = loader.getController();
        controller.addStage(this);
        controller.setLayer(layer);

        this.setTitle("File manager");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Menu/inprogress.png")));
        this.getIcons().add(icon);
        this.setScene(scene);
        this.show();
    }

    private String getView(FileManagementType type) {
        TYPE = type;
        switch (type) {
            case LOAD -> { return "/com/openjfx/dfdeditor/FileManagement/LoadFromFile.fxml";}
            case SAVE ->{return "/com/openjfx/dfdeditor/FileManagement/SaveToFile.fxml";}
            case EXPORT ->{return "/com/openjfx/dfdeditor/FileManagement/ExportToFile.fxml";}
            default -> {throw new RuntimeException();}
        }
    }
}
