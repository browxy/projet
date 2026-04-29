package com.imagemanager.service;

import com.imagemanager.model.ImageModel;
import java.util.List;

public class TagService {

    public void addTag(ImageModel model, String tag) {
        if (model != null && tag != null && !tag.isEmpty()) {
            model.addTag(tag);
        }
    }

    public List<String> getTags(ImageModel model) {
        return model != null ? model.getTags() : List.of();
    }
}

