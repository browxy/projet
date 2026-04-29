package com.imagemanager.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import com.imagemanager.model.Filter;
import com.imagemanager.model.ImageModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class ImageService {

    public ImageModel createImageModel(Image image) {
        return new ImageModel(image);
    }

    public void applyFilter(ImageModel model, Filter filter) {
        if (model != null) {
            model.applyFilter(filter);
        }
    }

    public void applyEncryptFilter(ImageModel model, Filter encryptFilter) {
        if (model != null) {
            model.applyEncryptFilter(encryptFilter);
            saveImageToDisk(model.getImage(), model.getImagePath());
        }
    }

    public void applyDecryptFilter(ImageModel model, Filter decryptFilter) {
        if (model != null) {
            model.applyDecryptFilter(decryptFilter);
            saveImageToDisk(model.getImage(), model.getImagePath());
        }
    }

    private void saveImageToDisk(Image image, String imagePath) {
        if (imagePath == null) return;
        try {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            PixelReader reader = image.getPixelReader();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    bImage.setRGB(x, y, reader.getArgb(x, y));
                }
            }
            File file = new File(new URI(imagePath));
            String ext = imagePath.toLowerCase().endsWith(".png") ? "png" : "jpg";
            if (ext.equals("jpg")) {
                BufferedImage rgbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                rgbImage.getGraphics().drawImage(bImage, 0, 0, null);
                ImageIO.write(rgbImage, ext, file);
            } else {
                ImageIO.write(bImage, ext, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetImage(ImageModel model) {
        if (model != null) {
            model.reset();
        }
    }

    public Image getImage(ImageModel model) {
        return model != null ? model.getImage() : null;
    }
}

