package com.example.randomphotoselector;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomVideoSelector {

    private final String directoryPath;
    private final List<String> videoPaths;
    private final Random random;

    public RandomVideoSelector(String directoryPath) {
        this.directoryPath = directoryPath;
        this.videoPaths = new ArrayList<>();
        this.random = new Random();
        loadVideoPaths();
    }

    private void loadVideoPaths() {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                Log.d("RandomVideoSelector", "Total files found: " + files.length);
                for (File file : files) {
                    Log.d("RandomVideoSelector", "Found file: " + file.getAbsolutePath());
                    if (isVideoFile(file)) {
                        videoPaths.add(file.getAbsolutePath());
                        Log.d("RandomVideoSelector", "Added video file: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private boolean isVideoFile(File file) {
        String[] videoExtensions = {".mp4", ".mkv", ".avi", ".mov", ".wmv"};
        for (String extension : videoExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getVideoPaths() {
        return videoPaths;
    }

    public String getRandomVideoPath() {
        if (videoPaths.isEmpty()) {
            return null;
        }
        int index = random.nextInt(videoPaths.size());
        return videoPaths.get(index);
    }
}