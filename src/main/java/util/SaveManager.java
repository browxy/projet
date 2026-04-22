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
            data.imagePath = model.getImage().getUrl();
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
                }
            }

            return model;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}