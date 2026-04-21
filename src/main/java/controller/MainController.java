package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.ImageModel;
import model.filters.GrayscaleFilter;
import model.filters.SepiaFilter;

import java.io.File;

public class MainController {

    @FXML
    private ImageView imageView;

    private ImageModel model;

    @FXML
    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image img = new Image(file.toURI().toString());
            model = new ImageModel(img);
            imageView.setImage(img);
        }
    }

    @FXML
    public void applyGrayscale() {
        if (model != null) {
            model.applyFilter(new GrayscaleFilter());
            imageView.setImage(model.getImage());
        }
    }
    @FXML
    public void applySepia() {
        if (model != null) {
            model.applyFilter(new SepiaFilter());
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void resetImage() {
        if (model != null) {
            imageView.setImage(model.getImage());
        }
    }
}