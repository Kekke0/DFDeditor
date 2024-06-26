package com.openjfx.dfdeditor.FileManagement;

import DataConverting.VOtoJSONconverter;
import Model.Layer;
import com.openjfx.dfdeditor.MainController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
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
    public Text Error;
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
        boolean butonVis = LAYER.getLevelDown().isVisible();
        LAYER.getLevelDown().setVisible(false);
        LAYER.setSelected(null);
        String path = FilePath.getText();
        RadioButton radioButton = (RadioButton)FileType.getSelectedToggle();
        String filetype = radioButton.getText();
        if (!path.endsWith(filetype)){
            path = path + "." +filetype;
        }
        File file = new File(path);
        try {
            WritableImage writableImage = new WritableImage(LAYER.widthProperty().intValue(), LAYER.heightProperty().intValue());
            LAYER.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, filetype, file);
            System.out.println(filetype);
        } catch (IOException ex) {
            Error.setText("Something went wrong");
            return;
        }

        LAYER.getLevelDown().setVisible(butonVis);
        STAGE.close();
    }

    public void LoadFile(ActionEvent actionEvent) {
        File file = new File(FilePath.getText());
        LAYER.setSelected(null);
        LAYER.setParentLayer(null);
        LAYER.removeAll();

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
            Error.setText("File not found!");
            return;
        }

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Browse");

        File file =fileChooser.showOpenDialog(STAGE);
        if (file != null) {
            FilePath.setText(file.getAbsolutePath());
        }
    }
}
