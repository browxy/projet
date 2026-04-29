package com.imagemanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import com.imagemanager.model.ImageModel;
import com.imagemanager.model.filters.*;
import com.imagemanager.service.ImageService;

public class FilterController {

    private MainController mainController;
    private final ImageService imageService = new ImageService();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private ImageModel getModel() {
        return mainController != null ? mainController.getModel() : null;
    }

    private void updateImageView() {
        if (mainController != null) {
            mainController.updateImageView();
        }
    }

    @FXML
    public void applyGrayscale() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new GrayscaleFilter());
            updateImageView();
        }
    }

    @FXML
    public void applySepia() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new SepiaFilter());
            updateImageView();
        }
    }

    @FXML
    public void applyRGBSwap() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new RGBSwapFilter());
            updateImageView();
        }
    }

    @FXML
    public void applyPrewitt() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new PrewittFilter());
            updateImageView();
        }
    }

    @FXML
    public void rotateRight() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new RotateFilter(true));
            updateImageView();
        }
    }

    @FXML
    public void rotateLeft() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new RotateFilter(false));
            updateImageView();
        }
    }

    @FXML
    public void applyHorizontalSymmetry() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new SymmetryFilter(true));
            updateImageView();
        }
    }

    @FXML
    public void applyVerticalSymmetry() {
        if (getModel() != null) {
            imageService.applyFilter(getModel(), new SymmetryFilter(false));
            updateImageView();
        }
    }

    @FXML
    public void encryptImage() {
        if (getModel() != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Encrypt");
            dialog.setHeaderText("Enter password to encrypt");

            dialog.showAndWait().ifPresent(password -> {
                imageService.applyEncryptFilter(getModel(), new EncryptFilter(password));
                updateImageView();
                if (mainController != null && mainController.getLibraryController() != null) {
                    mainController.getLibraryController().updateImageInfo("Image sécurisée et sauvegardée");
                }
            });
        }
    }

    @FXML
    public void decryptImage() {
        if (getModel() != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Decrypt");
            dialog.setHeaderText("Enter password to decrypt");

            dialog.showAndWait().ifPresent(password -> {
                imageService.applyDecryptFilter(getModel(), new DecryptFilter(password));
                updateImageView();
                if (mainController != null && mainController.getLibraryController() != null) {
                    mainController.getLibraryController().updateImageInfo("Image déchiffrée et restaurée");
                }
            });
        }
    }

    @FXML
    public void resetImage() {
        if (getModel() != null) {
            imageService.resetImage(getModel());
            updateImageView();
        }
    }
}

