package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import model.filters.EncryptFilter;
import service.ImageService;
import service.PersistenceService;
import service.TagService;
import javafx.scene.control.ListView;

import java.io.File;

public class MainController {

    @FXML
    private TextField tagField;

    @FXML
    private ImageView imageView;

    private ImageModel model;

    @FXML
    private ListView<String> tagList;

    // Services
    private final ImageService imageService = new ImageService();
    private final TagService tagService = new TagService();
    private final PersistenceService persistenceService = new PersistenceService();

    @FXML
    public void loadFromJson() {
        ImageModel loaded = persistenceService.load();
        if (loaded != null) {
            model = loaded;
            imageView.setImage(imageService.getImage(model));
            tagList.getItems().setAll(tagService.getTags(model));
        }
    }

    @FXML
    public void addTag() {
        String tag = tagField.getText();
        tagService.addTag(model, tag);
        tagList.getItems().setAll(tagService.getTags(model));
        tagField.clear();
    }

    @FXML
    public void saveImage() {
        persistenceService.save(model);
    }

    @FXML
    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image img = new Image(file.toURI().toString());
            model = imageService.createImageModel(img);
            imageView.setImage(img);
        }
    }

    @FXML
    public void applyGrayscale() {
        imageService.applyFilter(model, new GrayscaleFilter());
        updateImageView();
    }

    @FXML
    public void applySepia() {
        imageService.applyFilter(model, new SepiaFilter());
        updateImageView();
    }

    @FXML
    public void applyRGBSwap() {
        imageService.applyFilter(model, new RGBSwapFilter());
        updateImageView();
    }

    @FXML
    public void applyPrewitt() {
        imageService.applyFilter(model, new PrewittFilter());
        updateImageView();
    }

    @FXML
    public void rotateRight() {
        imageService.applyFilter(model, new RotateFilter(true));
        updateImageView();
    }

    @FXML
    public void rotateLeft() {
        imageService.applyFilter(model, new RotateFilter(false));
        updateImageView();
    }

    @FXML
    public void applyHorizontalSymmetry() {
        imageService.applyFilter(model, new SymmetryFilter(true));
        updateImageView();
    }

    @FXML
    public void applyVerticalSymmetry() {
        imageService.applyFilter(model, new SymmetryFilter(false));
        updateImageView();
    }

    @FXML
    public void encryptImage() {
        if (model != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Encrypt/Decrypt");
            dialog.setHeaderText("Enter password");

            dialog.showAndWait().ifPresent(password -> {
                imageService.applyFilter(model, new EncryptFilter(password));
                updateImageView();
            });
        }
    }

    @FXML
    public void resetImage() {
        imageService.resetImage(model);
        updateImageView();
    }

    private void updateImageView() {
        if (model != null) {
            imageView.setImage(imageService.getImage(model));
        }
    }
}