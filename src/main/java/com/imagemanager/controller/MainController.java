package com.imagemanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.imagemanager.model.ImageData;
import com.imagemanager.model.ImageModel;
import com.imagemanager.model.Filter;
import com.imagemanager.service.ImageService;
import com.imagemanager.service.PersistenceService;
import com.imagemanager.service.TagService;

import java.io.File;
import java.util.List;

public class MainController {

    @FXML
    private TextField tagField;

    @FXML
    private ImageView imageView;

    @FXML
    private Label tagList;

    @FXML
    private FilterController filterController;

    @FXML
    private LibraryController libraryController;

    private ImageModel model;

    // Services
    private final ImageService imageService = new ImageService();
    private final TagService tagService = new TagService();
    private final PersistenceService persistenceService = new PersistenceService();

    @FXML
    public void initialize() {
        if (filterController != null) {
            filterController.setMainController(this);
        }
        if (libraryController != null) {
            libraryController.setMainController(this);
        }
    }

    public ImageModel getModel() {
        return model;
    }

    public LibraryController getLibraryController() {
        return libraryController;
    }

    public void updateImageView() {
        if (model != null) {
            imageView.setImage(imageService.getImage(model));
        }
    }

    @FXML
    public void addTag() {
        String tag = tagField.getText();
        tagService.addTag(model, tag);
        updateTagDisplay();
        tagField.clear();
    }

    private void updateTagDisplay() {
        if (model != null) {
            List<String> tags = tagService.getTags(model);
            if (tags.isEmpty()) {
                tagList.setText("Aucun tag");
            } else {
                tagList.setText(String.join(", ", tags));
            }
        } else {
            tagList.setText("Aucune image chargée");
        }
    }

    @FXML
    public void saveToLibrary() {
        if (model != null && libraryController != null && libraryController.getLibrary() != null) {
            ImageData data = persistenceService.convertToImageData(model);
            libraryController.getLibrary().addImage(data);
            persistenceService.saveLibrary(libraryController.getLibrary());
            libraryController.refreshLibraryList();
            libraryController.updateImageInfo("Image ajoutée à la bibliothèque");
        }
    }

    @FXML
    public void loadLibrary() {
        if (libraryController != null) {
            libraryController.initialize(); // Reloads the library
            libraryController.updateImageInfo("Bibliothèque rechargée");
        }
    }

    public void loadImageData(ImageData data) {
        if (data != null && data.imagePath != null) {
            try {
                // Charger l'image depuis le chemin
                Image img = new Image(data.imagePath);
                model = imageService.createImageModel(img);

                // Restaurer les tags
                if (data.tags != null) {
                    for (String tag : data.tags) {
                        tagService.addTag(model, tag);
                    }
                }

                // Réappliquer les filtres sauvegardés
                if (data.filters != null && filterController != null) {
                    for (String filterName : data.filters) {
                        if (filterName.equals("Encrypt")) {
                            Filter marker = new Filter() {
                                @Override public Image apply(Image input) { return input; }
                                @Override public String getName() { return "Encrypt"; }
                            };
                            model.applyEncryptFilter(marker);
                        } else {
                            applyFilterByName(filterName);
                        }
                    }
                }

                imageView.setImage(model.getImage());
                updateTagDisplay();
                if (libraryController != null) {
                    libraryController.updateImageInfo("Chargé: " + data.fileName + " (" + data.width + "x" + data.height + ")");
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (libraryController != null) {
                    libraryController.updateImageInfo("Erreur: " + e.getMessage());
                }
            }
        }
    }

    private void applyFilterByName(String filterName) {
        if (filterController != null) {
            switch (filterName) {
                case "Grayscale": filterController.applyGrayscale(); break;
                case "Sepia": filterController.applySepia(); break;
                case "RGB Swap": filterController.applyRGBSwap(); break;
                case "Prewitt": filterController.applyPrewitt(); break;
                case "Rotate Right": filterController.rotateRight(); break;
                case "Rotate Left": filterController.rotateLeft(); break;
                case "Horizontal Symmetry": filterController.applyHorizontalSymmetry(); break;
                case "Vertical Symmetry": filterController.applyVerticalSymmetry(); break;
            }
        }
    }

    @FXML
    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image img = new Image(file.toURI().toString());
            model = imageService.createImageModel(img);
            imageView.setImage(img);
            updateTagDisplay();
            if (libraryController != null) {
                libraryController.updateImageInfo("Image chargée");
            }
        }
    }
}
