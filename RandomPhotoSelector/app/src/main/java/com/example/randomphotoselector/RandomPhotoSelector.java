package com.example.randomphotoselector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPhotoSelector {
    private final String directoryPath;

    public RandomPhotoSelector(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file)) {
                        imagePaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
        return imagePaths;
    }

    public String getRandomImagePath() {
        List<String> imagePaths = getImagePaths();
        if (!imagePaths.isEmpty()) {
            Random random = new Random();
            return imagePaths.get(random.nextInt(imagePaths.size()));
        }
        return null;
    }

    private boolean isImageFile(File file) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        for (String extension : imageExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}