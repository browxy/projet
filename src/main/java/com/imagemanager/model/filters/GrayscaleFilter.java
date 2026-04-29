package com.imagemanager.model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import com.imagemanager.model.Filter;

public class GrayscaleFilter implements Filter {

    @Override
    public Image apply(Image input) {
        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        WritableImage output = new WritableImage(w, h);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Color c = reader.getColor(x, y);
                double gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                writer.setColor(x, y, new Color(gray, gray, gray, c.getOpacity()));
            }
        }
        return output;
    }

    @Override
    public String getName() {
        return "Grayscale";
    }
}
