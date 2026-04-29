package com.imagemanager.model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import com.imagemanager.model.Filter;

public class RGBSwapFilter implements Filter {

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

                // swap : (R, G, B) → (G, B, R)
                writer.setColor(x, y, new Color(g, b, r, c.getOpacity()));
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "RGB Swap";
    }
}
