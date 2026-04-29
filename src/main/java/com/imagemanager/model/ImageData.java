package com.imagemanager.model;

import java.util.Date;
import java.util.List;

public class ImageData {
    public String imagePath;
    public String fileName;
    public List<String> tags;
    public List<String> filters;
    public int width;
    public int height;
    public Date createdDate;
    public Date modifiedDate;

    public ImageData() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }
}
