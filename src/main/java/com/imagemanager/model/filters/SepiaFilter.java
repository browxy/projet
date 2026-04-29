package com.imagemanager.model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import com.imagemanager.model.Filter;

public class SepiaFilter implements Filter {

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

                double r = c.getRed();
                double g = c.getGreen();
                double b = c.getBlue();

                // Formule Sepia
                double newR = 0.393 * r + 0.769 * g + 0.189 * b;
                double newG = 0.349 * r + 0.686 * g + 0.168 * b;
                double newB = 0.272 * r + 0.534 * g + 0.131 * b;

                // Limiter entre 0 et 1
                newR = Math.min(1.0, newR);
                newG = Math.min(1.0, newG);
                newB = Math.min(1.0, newB);

                writer.setColor(x, y, new Color(newR, newG, newB, c.getOpacity()));
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "Sepia";
    }
}
