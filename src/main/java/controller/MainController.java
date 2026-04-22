package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.ImageData;
import model.ImageLibrary;
import model.ImageModel;
import model.filters.*;
import service.ImageService;
import service.PersistenceService;
import service.TagService;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TextField tagField;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<String> tagList;

    @FXML
    private ListView<String> libraryList;

    @FXML
    private Label imageInfoLabel;

    private ImageModel model;
    private ImageLibrary library;

    // Services
    private final ImageService imageService = new ImageService();
    private final TagService tagService = new TagService();
    private final PersistenceService persistenceService = new PersistenceService();

    @FXML
    public void initialize() {
        // Charger la bibliothèque au démarrage
        library = persistenceService.loadLibrary();
        refreshLibraryList();
    }

    @FXML
    public void addTag() {
        String tag = tagField.getText();
        tagService.addTag(model, tag);
        tagList.getItems().setAll(tagService.getTags(model));
        tagField.clear();
    }

    @FXML
    public void saveToLibrary() {
        if (model != null) {
            ImageData data = persistenceService.convertToImageData(model);
            library.addImage(data);
            persistenceService.saveLibrary(library);
            refreshLibraryList();
            updateImageInfo("Image ajoutée à la bibliothèque");
        }
    }

    @FXML
    public void loadLibrary() {
        library = persistenceService.loadLibrary();
        refreshLibraryList();
        updateImageInfo("Bibliothèque chargée: " + library.size() + " images");
    }

    @FXML
    public void searchByTag() {
        String tag = searchField.getText();
        if (tag != null && !tag.isEmpty()) {
            List<ImageData> results = library.searchByTag(tag);
            libraryList.getItems().clear();
            libraryList.getItems().addAll(
                results.stream()
                    .map(img -> img.fileName + " [" + String.join(", ", img.tags) + "]")
                    .collect(Collectors.toList())
            );
            updateImageInfo("Trouvé: " + results.size() + " images avec tag '" + tag + "'");
        }
    }

    @FXML
    public void loadSelectedFromLibrary() {
        String selected = libraryList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String fileName = selected.split(" \\[")[0];
            ImageData data = library.getAllImages().stream()
                .filter(img -> fileName.equals(img.fileName))
                .findFirst()
                .orElse(null);

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
                    if (data.filters != null) {
                        for (String filterName : data.filters) {
                            applyFilterByName(filterName);
                        }
                    }

                    imageView.setImage(model.getImage());
                    tagList.getItems().setAll(tagService.getTags(model));
                    updateImageInfo("Chargé: " + data.fileName + " (" + data.width + "x" + data.height + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                    updateImageInfo("Erreur: " + e.getMessage());
                }
            }
        }
    }

    private void applyFilterByName(String filterName) {
        switch (filterName) {
            case "Grayscale":
                imageService.applyFilter(model, new GrayscaleFilter());
                break;
            case "Sepia":
                imageService.applyFilter(model, new SepiaFilter());
                break;
            case "RGB Swap":
                imageService.applyFilter(model, new RGBSwapFilter());
                break;
            case "Prewitt":
                imageService.applyFilter(model, new PrewittFilter());
                break;
            case "Rotate Right":
                imageService.applyFilter(model, new RotateFilter(true));
                break;
            case "Rotate Left":
                imageService.applyFilter(model, new RotateFilter(false));
                break;
            case "Horizontal Symmetry":
                imageService.applyFilter(model, new SymmetryFilter(true));
                break;
            case "Vertical Symmetry":
                imageService.applyFilter(model, new SymmetryFilter(false));
                break;
        }
    }

    @FXML
    public void deleteSelectedFromLibrary() {
        String selected = libraryList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String fileName = selected.split(" \\[")[0];
            ImageData data = library.getAllImages().stream()
                .filter(img -> fileName.equals(img.fileName))
                .findFirst()
                .orElse(null);

            if (data != null) {
                library.removeImage(data);
                persistenceService.saveLibrary(library);
                refreshLibraryList();
                updateImageInfo("Image supprimée de la bibliothèque");
            }
        }
    }

    private void refreshLibraryList() {
        if (libraryList != null && library != null) {
            libraryList.getItems().clear();
            libraryList.getItems().addAll(
                library.getAllImages().stream()
                    .map(img -> img.fileName + " [" + (img.tags != null ? String.join(", ", img.tags) : "") + "]")
                    .collect(Collectors.toList())
            );
        }
    }

    private void updateImageInfo(String info) {
        imageInfoLabel.setText(info);
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
            dialog.setTitle("Encrypt");
            dialog.setHeaderText("Enter password to encrypt");

            dialog.showAndWait().ifPresent(password -> {
                imageService.applyFilter(model, new EncryptFilter(password));
                updateImageView();
            });
        }
    }

    @FXML
    public void decryptImage() {
        if (model != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Decrypt");
            dialog.setHeaderText("Enter password to decrypt");

            dialog.showAndWait().ifPresent(password -> {
                imageService.applyFilter(model, new DecryptFilter(password));
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