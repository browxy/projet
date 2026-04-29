package com.imagemanager.model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import com.imagemanager.model.Filter;

public class RotateFilter implements Filter {

    private boolean clockwise;

    public RotateFilter(boolean clockwise) {
        this.clockwise = clockwise;
    }

    @Override
    public Image apply(Image input) {
        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        WritableImage output = new WritableImage(h, w); // inversé

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                Color color = reader.getColor(x, y);

                if (clockwise) {
                    writer.setColor(h - 1 - y, x, color);
                } else {
                    writer.setColor(y, w - 1 - x, color);
                }
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return clockwise ? "Rotate Right" : "Rotate Left";
    }
}
