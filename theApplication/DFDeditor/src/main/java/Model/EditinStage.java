package Model;

import Model.VOs.VisualObject;
import com.openjfx.dfdeditor.EditConrollers.DataeditController;
import com.openjfx.dfdeditor.EditConrollers.ExternalEditController;
import com.openjfx.dfdeditor.EditConrollers.ProcessEditController;
import com.openjfx.dfdeditor.EditConrollers.UEditController;
import com.openjfx.dfdeditor.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EditinStage extends Stage {
    private VisualObject EditedVO;

    public EditinStage(VisualObject editedVO) throws IOException {
        EditedVO=editedVO;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getView()));
        Scene editscene = new Scene(loader.load());

        setControllerBasics(loader);

        this.setTitle("Editor");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Menu/inprogress.png")));
        this.getIcons().add(icon);
        this.setScene(editscene);
        this.show();
    }

    private void setControllerBasics(FXMLLoader loader) {

        switch (EditedVO.getTypeString()) {
            case "DB" ->{
                DataeditController controller = loader.getController();
                controller.addStage(this);
                controller.setEdited(EditedVO);}
            case "PR" ->{
                ProcessEditController controller = loader.getController();
                controller.addStage(this);
                controller.setEdited(EditedVO);}
            case "EE" ->{
                ExternalEditController controller = loader.getController();
                controller.addStage(this);
                controller.setEdited(EditedVO);}
            default -> {}
        }
    }

    private String getView() {
        switch (EditedVO.getTypeString()) {
            case "DB" ->{ return "/com/openjfx/dfdeditor/EditViews/DataEdit-view.fxml";}
            case "EE" ->{return "/com/openjfx/dfdeditor/EditViews/ExternalEdit-view.fxml";}
            case "PR" ->{return "/com/openjfx/dfdeditor/EditViews/ProcessEdit-view.fxml";}
            default -> {return "/com/openjfx/dfdeditor/EditViews/UEdit-view.fxml";}
        }
    }
}
