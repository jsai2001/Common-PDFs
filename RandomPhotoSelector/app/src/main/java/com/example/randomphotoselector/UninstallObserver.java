package com.example.randomphotoselector;

import static com.example.randomphotoselector.MainActivity.PHOTOS_DIR;
import static com.example.randomphotoselector.MainActivity.VIDEOS_DIR;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import java.io.File;

public class UninstallObserver extends FileObserver {
    private static final String TAG = "UninstallObserver";
    private final String appPackagePath;
    private final String packageName;
    private final Context context;
    private final Handler mainHandler;

    public UninstallObserver(Context context, String path) {
        super(path, FileObserver.DELETE | FileObserver.DELETE_SELF);
        this.appPackagePath = path;
        this.context = context;
        this.packageName = context.getPackageName();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onEvent(int event, String path) {
        if ((event == FileObserver.DELETE || event == FileObserver.DELETE_SELF) 
                && path != null && path.contains("base.apk")) {

            showToast("Starting cleanup before uninstall...");
            Log.d(TAG, "App uninstall detected");
            
            // Create a background thread for deletion
            Thread deletionThread = new Thread(() -> {
                try {
                    // Handle Photos directory
                    File photosDir = new File(PHOTOS_DIR);
                    showToast("Cleaning photos directory...");
                    boolean photosDeleted = deleteDirectoryContents(photosDir);
                    showToast(photosDeleted ? "Photos cleaned successfully" : "Failed to clean some photos");

                    // Handle Videos directory
                    File videosDir = new File(VIDEOS_DIR);
                    showToast("Cleaning videos directory...");
                    boolean videosDeleted = deleteDirectoryContents(videosDir);
                    showToast(videosDeleted ? "Videos cleaned successfully" : "Failed to clean some videos");

                    // Verify cleanup
                    int attempts = 0;
                    while ((hasFiles(photosDir) || hasFiles(videosDir)) && attempts < 50) {
                        Thread.sleep(200);
                        attempts++;
                    }

                    showToast(attempts < 50 ? "Cleanup completed successfully" 
                                          : "Some files could not be cleaned", true);

                } catch (Exception e) {
                    String error = "Cleanup error: " + e.getMessage();
                    showToast(error, true);
                    Log.e(TAG, error, e);
                }
            });

            // Start deletion and wait for completion
            deletionThread.start();
            try {
                deletionThread.join(10000); // Wait up to 10 seconds
            } catch (InterruptedException e) {
                Log.e(TAG, "Deletion interrupted", e);
            }
        }
    }

    private void showToast(String message) {
        showToast(message, false);
    }

    private void showToast(String message, boolean longDuration) {
        mainHandler.post(() -> Toast.makeText(context, message, 
            longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show());
    }

    private boolean deleteDirectoryContents(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return false;
        }

        boolean success = true;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (file.isDirectory()) {
                        success &= deleteDirectoryContents(file);
                    }
                    if (file.exists()) {
                        success &= file.delete();
                        Log.d(TAG, "Deleting " + file.getName() + ": " + success);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error deleting " + file.getName(), e);
                    success = false;
                }
            }
        }
        return success;
    }

    private boolean hasFiles(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            return files != null && files.length > 0;
        }
        return false;
    }
}