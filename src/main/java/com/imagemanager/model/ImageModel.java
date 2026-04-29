package com.imagemanager.model;

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

    public void applyEncryptFilter(Filter encryptFilter) {
        image = encryptFilter.apply(originalImage);
        originalImage = image; // L'image encryptée DEVIENT l'image d'origine
        
        // On ne garde que "Encrypt" (et les autres filtres appliqués avant sont maintenant "cuits" dans l'image d'origine)
        filters.clear();
        filters.add(encryptFilter); 
    }

    public void applyDecryptFilter(Filter decryptFilter) {
        image = decryptFilter.apply(originalImage);
        originalImage = image; // L'image decryptée DEVIENT la nouvelle image d'origine
        
        // On retire le filtre Encrypt
        filters.removeIf(f -> f.getName().equals("Encrypt"));
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
