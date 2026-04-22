package model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import model.Filter;

public class PrewittFilter implements Filter {

    @Override
    public Image apply(Image input) {
        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        WritableImage output = new WritableImage(w, h);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        int[][] Gx = {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };

        int[][] Gy = {
                {-1, -1, -1},
                {0, 0, 0},
                {1, 1, 1}
        };

        for (int x = 1; x < w - 1; x++) {
            for (int y = 1; y < h - 1; y++) {

                double sumX = 0;
                double sumY = 0;

                // convolution 3x3
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {

                        Color c = reader.getColor(x + i, y + j);

                        // convertir en gris
                        double gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;

                        sumX += gray * Gx[i + 1][j + 1];
                        sumY += gray * Gy[i + 1][j + 1];
                    }
                }

                // magnitude du gradient
                double magnitude = Math.sqrt(sumX * sumX + sumY * sumY);

                // normalisation (0 → 1)
                magnitude = Math.min(1.0, magnitude);

                writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "Prewitt";
    }
}