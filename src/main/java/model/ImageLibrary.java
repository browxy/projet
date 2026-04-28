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
    public List<ImageData> searchByTag(String tag) {
        return images.stream()
                .filter(img -> img.tags != null && img.tags.contains(tag))
                .collect(Collectors.toList());
    }


    @JsonIgnore
    public int size() {
        return images.size();
    }
}
