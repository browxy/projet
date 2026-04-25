package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageLibrary {
    private List<ImageData> images;

    public ImageLibrary() {
        this.images = new ArrayList<>();
    }

    // Getters et Setters pour Jackson
    public List<ImageData> getImages() {
        return images;
    }

    public void setImages(List<ImageData> images) {
        this.images = images != null ? images : new ArrayList<>();
    }

    public void addImage(ImageData imageData) {
        images.add(imageData);
    }

    public void removeImage(ImageData imageData) {
        images.remove(imageData);
    }

    @JsonIgnore
    public List<ImageData> getAllImages() {
        return new ArrayList<>(images);
    }


    @JsonIgnore
    public ImageData findByPath(String path) {
        return images.stream()
                .filter(img -> img.imagePath != null && img.imagePath.equals(path))
                .findFirst()
                .orElse(null);
    }

    @JsonIgnore
    public int size() {
        return images.size();
    }
}
