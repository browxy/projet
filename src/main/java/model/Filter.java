package model;

import javafx.scene.image.Image;

public interface Filter {
    Image apply(Image input);
    String getName();
}