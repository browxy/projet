package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import model.*;
import java.io.File;

public class SaveManager {

    public static void save(ImageModel model, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            ImageData data = new ImageData();
            data.imagePath = model.getImagePath();
            data.tags = model.getTags();
            data.filters = model.getFilterNames();

            mapper.writeValue(new File(path), data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageModel load(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ImageData data = mapper.readValue(new File(path), ImageData.class);

            if (data.imagePath == null || data.imagePath.isEmpty()) {
                System.err.println("Image path is null or empty in JSON file");
                return null;
            }

            ImageModel model = new ImageModel(new Image(data.imagePath));

            for (String tag : data.tags) {
                model.addTag(tag);
            }

            for (String f : data.filters) {
                switch (f) {
                    case "Grayscale":
                        model.applyFilter(new model.filters.GrayscaleFilter());
                        break;
                    case "Sepia":
                        model.applyFilter(new model.filters.SepiaFilter());
                        break;
                    case "RGB Swap":
                        model.applyFilter(new model.filters.RGBSwapFilter());
                        break;
                    case "Prewitt":
                        model.applyFilter(new model.filters.PrewittFilter());
                        break;
                    case "Rotate Right":
                        model.applyFilter(new model.filters.RotateFilter(true));
                        break;
                    case "Rotate Left":
                        model.applyFilter(new model.filters.RotateFilter(false));
                        break;
                    case "Horizontal Symmetry":
                        model.applyFilter(new model.filters.SymmetryFilter(true));
                        break;
                    case "Vertical Symmetry":
                        model.applyFilter(new model.filters.SymmetryFilter(false));
                        break;
                    case "Encrypt":
                        System.err.println("Warning: Encrypt filter cannot be restored without password");
                        break;
                    default:
                        System.err.println("Unknown filter: " + f);
                        break;
                }
            }

            return model;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}