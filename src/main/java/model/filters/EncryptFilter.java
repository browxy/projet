package model.filters;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import model.Filter;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        try {
            // Utiliser SHA-256 pour générer une seed à partir du mot de passe
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            // Créer SecureRandom avec SHA1PRNG et la seed
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(hash);

            // Créer une liste des positions de pixels
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < w * h; i++) {
                positions.add(i);
            }

            // Mélanger les positions avec le générateur sécurisé
            Collections.shuffle(positions, new java.util.Random() {
                @Override
                public int nextInt(int bound) {
                    return random.nextInt(bound);
                }
            });

            // Appliquer la permutation des pixels
            for (int i = 0; i < w * h; i++) {
                int originalX = i % w;
                int originalY = i / w;

                int newPos = positions.get(i);
                int newX = newPos % w;
                int newY = newPos / w;

                Color color = reader.getColor(originalX, originalY);
                writer.setColor(newX, newY, color);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, retourner l'image originale
            return input;
        }

        return output;
    }

    @Override
    public String getName() {
        return "Encrypt";
    }
}