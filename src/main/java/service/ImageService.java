package service;

import javafx.scene.image.Image;
import model.Filter;
import model.ImageModel;

public class ImageService {

    public ImageModel createImageModel(Image image) {
        return new ImageModel(image);
    }

    public void applyFilter(ImageModel model, Filter filter) {
        if (model != null) {
            model.applyFilter(filter);
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
