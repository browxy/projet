package service;

import model.ImageModel;
import util.SaveManager;

public class PersistenceService {

    private static final String DEFAULT_FILE = "data.json";

    public void save(ImageModel model) {
        save(model, DEFAULT_FILE);
    }

    public void save(ImageModel model, String filePath) {
        if (model != null) {
            SaveManager.save(model, filePath);
        }
    }

    public ImageModel load() {
        return load(DEFAULT_FILE);
    }

    public ImageModel load(String filePath) {
        return SaveManager.load(filePath);
    }
}
