package model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import model.Filter;

public class SymmetryFilter implements Filter {

    private boolean horizontal; // true = miroir horizontal, false = vertical

    public SymmetryFilter(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public Image apply(Image input) {
        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        WritableImage output = new WritableImage(w, h);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                Color color = reader.getColor(x, y);

                if (horizontal) {
                    // miroir gauche/droite
                    writer.setColor(w - 1 - x, y, color);
                } else {
                    // miroir haut/bas
                    writer.setColor(x, h - 1 - y, color);
                }
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return horizontal ? "Horizontal Symmetry" : "Vertical Symmetry";
    }
}