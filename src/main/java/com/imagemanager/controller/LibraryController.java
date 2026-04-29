package com.imagemanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.imagemanager.model.ImageData;
import com.imagemanager.model.ImageLibrary;
import com.imagemanager.service.PersistenceService;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> libraryList;

    @FXML
    private Label imageInfoLabel;

    private MainController mainController;
    private ImageLibrary library;
    private final PersistenceService persistenceService = new PersistenceService();

    @FXML
    public void initialize() {
        library = persistenceService.loadLibrary();
        refreshLibraryList();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ImageLibrary getLibrary() {
        return library;
    }

    public void updateImageInfo(String info) {
        if (imageInfoLabel != null) {
            imageInfoLabel.setText(info);
        }
    }

    public void refreshLibraryList() {
        if (libraryList != null && library != null) {
            libraryList.getItems().clear();
            libraryList.getItems().addAll(
                library.getAllImages().stream()
                    .map(img -> img.fileName + " [" + (img.tags != null ? String.join(", ", img.tags) : "") + "]")
                    .collect(Collectors.toList())
            );
        }
    }

    @FXML
    public void searchByTag() {
        if (library != null && searchField != null) {
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
            } else {
                refreshLibraryList();
                updateImageInfo("Affiche toutes les images");
            }
        }
    }

    @FXML
    public void loadSelectedFromLibrary() {
        if (libraryList != null && library != null && mainController != null) {
            String selected = libraryList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String fileName = selected.split(" \\[")[0];
                ImageData data = library.getAllImages().stream()
                    .filter(img -> fileName.equals(img.fileName))
                    .findFirst()
                    .orElse(null);

                if (data != null && data.imagePath != null) {
                    mainController.loadImageData(data);
                }
            }
        }
    }

    @FXML
    public void deleteSelectedFromLibrary() {
        if (libraryList != null && library != null) {
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
    }
}
