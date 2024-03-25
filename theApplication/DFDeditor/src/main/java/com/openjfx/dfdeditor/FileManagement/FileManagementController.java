package com.openjfx.dfdeditor.FileManagement;

import DataConverting.VOtoJSONconverter;
import Model.Layer;
import com.openjfx.dfdeditor.MainController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class FileManagementController {

    public ToggleGroup FileType;
    public TextField FilePath;
    private FileMenu STAGE;
    private Layer LAYER;

    public void addStage(FileMenu stage){
        STAGE = stage;
    }

    public void setLayer(Layer layer){
        LAYER = layer;
    }
    public void CancelFileBrowser(ActionEvent actionEvent) {
        STAGE.close();
    }

    public void ExportFile(ActionEvent actionEvent) {
        File file = new File(FilePath.getText());
        try {
            WritableImage writableImage = new WritableImage(LAYER.widthProperty().intValue(), LAYER.heightProperty().intValue());
            LAYER.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error!");
        }
        STAGE.close();
    }

    public void LoadFile(ActionEvent actionEvent) {
        File file = new File(FilePath.getText());
        LAYER = new Layer();

        try (FileInputStream fis=new FileInputStream (file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, StandardCharsets.UTF_8);
            str = str.substring(1,str.length()-1);
            for (String svo: str.split(";")) {
                VOtoJSONconverter.JSONtoVO(LAYER,svo).AddToLayer(LAYER);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // MainController.Drawable = LAYER;
        // MainController.BP.setCenter(LAYER);

        STAGE.close();
    }

    public void SaveFile(ActionEvent actionEvent) {
        Layer layer = LAYER;
        while (layer.getParentLayer() != null){
            layer = layer.getParentLayer();
        }
        try (PrintWriter writer = new PrintWriter(FilePath.getText())) {
            writer.print("[");
            writer.print(VOtoJSONconverter.VOtoJSON(layer.getVOs().get(0)));
            for (int i = 1; i < layer.getVOs().size(); i++) {
                writer.print(";" + VOtoJSONconverter.VOtoJSON(layer.getVOs().get(i)));
            }
            for (int i = 1; i < layer.getFlows().size(); i++) {
                writer.print(";" + VOtoJSONconverter.VOtoJSON(layer.getFlows().get(i)));
            }
            writer.print("]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        STAGE.close();
    }

    public void BrowseFiles(ActionEvent actionEvent) {
        FileChooser file = new FileChooser();
        file.setTitle("Browse");


        FilePath.setText(file.showOpenDialog(STAGE).getAbsolutePath());
    }
}
