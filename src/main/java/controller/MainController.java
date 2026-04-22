package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.ImageModel;
import model.filters.GrayscaleFilter;
import model.filters.SepiaFilter;
import model.filters.RGBSwapFilter;
import model.filters.PrewittFilter;
import model.filters.RotateFilter;
import model.filters.SymmetryFilter;

import java.io.File;

public class MainController {

    @FXML
    private TextField tagField;

    @FXML
    private ImageView imageView;

    private ImageModel model;

    @FXML
    public void addTag() {
        if (model != null && !tagField.getText().isEmpty()) {
            model.addTag(tagField.getText());
            tagField.clear();
        }
    }

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
    public void applyRGBSwap() {
        if (model != null) {
            model.applyFilter(new RGBSwapFilter());
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void applyPrewitt() {
        if (model != null) {
            model.applyFilter(new PrewittFilter());
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void rotateRight() {
        if (model != null) {
            model.applyFilter(new RotateFilter(true));
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void rotateLeft() {
        if (model != null) {
            model.applyFilter(new RotateFilter(false));
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void applyHorizontalSymmetry() {
        if (model != null) {
            model.applyFilter(new SymmetryFilter(true));
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void applyVerticalSymmetry() {
        if (model != null) {
            model.applyFilter(new SymmetryFilter(false));
            imageView.setImage(model.getImage());
        }
    }

    @FXML
    public void resetImage() {
        if (model != null) {
            model.reset();
            imageView.setImage(model.getImage());
        }
    }
}