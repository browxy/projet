package model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import model.Filter;

import java.security.SecureRandom;

public class EncryptFilter implements Filter {

    private String password;

    public EncryptFilter(String password) {
        this.password = password;
    }

    @Override
    public Image apply(Image input) {
        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        WritableImage output = new WritableImage(w, h);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        int seed = password.hashCode();
        java.util.Random random = new java.util.Random(seed); // 🔥 IMPORTANT

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                Color c = reader.getColor(x, y);

                int r = (int)(c.getRed() * 255);
                int g = (int)(c.getGreen() * 255);
                int b = (int)(c.getBlue() * 255);

                int key = random.nextInt(256);

                int newR = r ^ key;
                int newG = g ^ key;
                int newB = b ^ key;

                writer.setColor(x, y, Color.rgb(newR, newG, newB));
            }
        }

        return output;
    }

    @Override
    public String getName() {
        return "Encrypt";
    }
}