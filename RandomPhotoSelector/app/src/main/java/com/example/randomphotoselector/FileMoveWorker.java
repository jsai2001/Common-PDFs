package com.example.randomphotoselector;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;

public class FileMoveWorker extends Worker {

    private static final String TAG = "FileMoveWorker";

    public FileMoveWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get source and destination paths from input data
        String sourcePath = getInputData().getString("sourcePath");
        String destPath = getInputData().getString("destPath");

        if (sourcePath == null || destPath == null) {
            Log.e(TAG, "Source or destination path is null");
            return Result.failure();
        }

        File sourceDir = new File(sourcePath);
        File destDir = new File(destPath);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            Log.e(TAG, "Source directory does not exist or is not a directory: " + sourcePath);
            return Result.failure();
        }

        // Use batch processing to move files
        moveFilesInBatches(sourceDir, destDir, 100); // Process 100 files at a time

        // Return success
        return Result.success();
    }

    private void moveFilesInBatches(File sourceDir, File destDir, int batchSize) {
        File[] files = sourceDir.listFiles();
        if (files != null) {
            int totalFiles = files.length;
            int filesMoved = 0;
        
            for (int i = 0; i < totalFiles; i += batchSize) {
                int end = Math.min(i + batchSize, totalFiles);
                for (int j = i; j < end; j++) {
                    File file = files[j];
                    if (file.isDirectory()) {
                        // Recursively process subdirectories
                        moveFilesInBatches(file, destDir, batchSize);
                        file.delete(); // Delete empty subdirectory
                    } else {
                        // Move the file to the destination directory
                        File destFile = new File(destDir, file.getName());
                        if (!destFile.exists()) {
                            boolean moved = file.renameTo(destFile);
                            if (moved) {
                                Log.d(TAG, "Moved file: " + file.getAbsolutePath() + " to " + destFile.getAbsolutePath());
                            } else {
                                Log.e(TAG, "Failed to move file: " + file.getAbsolutePath());
                            }
                        } else {
                            Log.w(TAG, "File already exists in destination: " + destFile.getAbsolutePath());
                        }
                    }
                
                    // Update progress
                    filesMoved++;
                    int progress = (int) ((filesMoved / (float) totalFiles) * 100);
                    setProgressAsync(new Data.Builder().putInt("progress", progress).build());
                }
            }
        }
    }
}