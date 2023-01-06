package com.openjfx.dfdeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-view.fxml"));
        Scene mainscene = new Scene(loader.load());

        MainController mainController = loader.getController();
        mainController.setEventHandlers();

        stage.setTitle("DFDeditor");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Menu/inprogress.png")));
        stage.getIcons().add(icon);
        stage.setScene(mainscene);
        stage.show();
    }



}
