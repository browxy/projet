package com.imagemanager.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagemanager.model.*;
import java.io.File;

public class SaveManager {

    public static void saveLibrary(ImageLibrary library, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), library);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageLibrary loadLibrary(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(path);
            if (file.exists()) {
                return mapper.readValue(file, ImageLibrary.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageLibrary();
    }
}
