package com.openjfx.dfdeditor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    public static Stage mainStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-view.fxml"));
        Scene mainscene = new Scene(loader.load());

        MainController mainController = loader.getController();
        mainController.Setter().SetToMouseMode(mainController.Drawable);
        mainController.setKeyBindings(mainscene);

        stage.setTitle("DFDeditor");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Menu/inprogress.png")));
        stage.getIcons().add(icon);
        stage.setScene(mainscene);
        stage.show();
    }


}
