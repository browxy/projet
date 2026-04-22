package model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class ImageModel {

    private Image image;
    private Image originalImage;
    private String imagePath;

    private List<Filter> filters = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public ImageModel(Image image) {
        this.image = image;
        this.originalImage = image;
        this.imagePath = image.getUrl();
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image getImage() {
        return image;
    }

    public void applyFilter(Filter filter) {
        image = filter.apply(image);
        filters.add(filter);
    }

    public List<String> getFilterNames() {
        List<String> names = new ArrayList<>();
        for (Filter f : filters) {
            names.add(f.getName());
        }
        return names;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    public void reset() {
        image = originalImage;
        filters.clear();
    }
}