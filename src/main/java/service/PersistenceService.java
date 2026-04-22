package service;

import model.ImageData;
import model.ImageLibrary;
import model.ImageModel;
import util.SaveManager;

public class PersistenceService {

    private static final String LIBRARY_FILE = "library.json";

    public void saveLibrary(ImageLibrary library) {
        saveLibrary(library, LIBRARY_FILE);
    }

    public void saveLibrary(ImageLibrary library, String filePath) {
        if (library != null) {
            SaveManager.saveLibrary(library, filePath);
        }
    }

    public ImageLibrary loadLibrary() {
        return loadLibrary(LIBRARY_FILE);
    }

    public ImageLibrary loadLibrary(String filePath) {
        return SaveManager.loadLibrary(filePath);
    }

    public ImageData convertToImageData(ImageModel model) {
        if (model == null) return null;

        ImageData data = new ImageData();
        data.imagePath = model.getImagePath();
        data.fileName = extractFileName(model.getImagePath());
        data.tags = model.getTags();
        data.filters = model.getFilterNames();
        data.width = (int) model.getImage().getWidth();
        data.height = (int) model.getImage().getHeight();

        return data;
    }

    private String extractFileName(String path) {
        if (path == null) return null;
        int lastSlash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }
}
