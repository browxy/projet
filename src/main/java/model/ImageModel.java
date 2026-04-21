package model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class ImageModel {

    private Image image;
    private List<Filter> filters = new ArrayList<>();

    public ImageModel(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void applyFilter(Filter filter) {
        image = filter.apply(image);
        filters.add(filter);
    }
}