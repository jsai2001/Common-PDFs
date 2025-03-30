package com.example.randomphotoselector;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    private static final String CORRECT_PASSWORD = "Kz9$mPq!vR2x";
    private int attemptCount = 0;
    private static final int MAX_ATTEMPTS = 3;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private static final int ADMIN_REQUEST = 1;
    private static final String OLD_PHOTOS_PATH = "/storage/emulated/0/Relaxation/Photos";
    private static final String OLD_VIDEOS_PATH = "/storage/emulated/0/Relaxation/Videos";
    private static final String OLD_FILES_PATH = "/storage/emulated/0/Relaxation/Files";
    private Handler handler = new Handler();
    private boolean isMoveOperationComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Device Admin
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);
        
        // Request admin privileges if not already granted
        if (!devicePolicyManager.isAdminActive(adminComponent)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, 
                "Admin access required for app security");
            startActivityForResult(intent, ADMIN_REQUEST);
        }

        // Hide system bars
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        
        // Hide navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                     | View.SYSTEM_UI_FLAG_FULLSCREEN
                     | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_login);

        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String enteredPassword = passwordInput.getText().toString();
            if (enteredPassword.equals(CORRECT_PASSWORD)) {
                checkDirectoriesAndProceed();
            } else {
                attemptCount++;
                if (attemptCount >= MAX_ATTEMPTS) {
                    if (devicePolicyManager.isAdminActive(adminComponent)) {
                        // Clear app data without touching external storage
                        clearAppData();
                        // Then uninstall the app
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    } else {
                        // Fallback to normal uninstall
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                    finish();
                } else {
                    Toast.makeText(this, "Wrong password! Attempts left: " + 
                        (MAX_ATTEMPTS - attemptCount), Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                }
            }
        });
    }

    private void checkDirectoriesAndProceed() {
        if (areDirectoriesEmpty()) {
            isMoveOperationComplete = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            moveFilesToAppDirectories();
            handler.postDelayed(this::checkDirectoriesAndProceed, 1000); // Check again after 5 seconds
        }
    }

    private boolean areDirectoriesEmpty() {
        return isDirectoryEmpty(new File(OLD_PHOTOS_PATH)) &&
               isDirectoryEmpty(new File(OLD_VIDEOS_PATH)) &&
               isDirectoryEmpty(new File(OLD_FILES_PATH));
    }

    private boolean isDirectoryEmpty(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            return files == null || files.length == 0;
        }
        return true;
    }

    private void moveFilesToAppDirectories() {
        File appDir = getExternalFilesDir(null);
        if (appDir != null) {
            File photosDir = new File(appDir, "Photos");
            File videosDir = new File(appDir, "Videos");
            File filesDir = new File(appDir, "Files");

            photosDir.mkdirs();
            videosDir.mkdirs();
            filesDir.mkdirs();

            moveFilesFromDirectory(new File(OLD_PHOTOS_PATH), photosDir);
            moveFilesFromDirectory(new File(OLD_VIDEOS_PATH), videosDir);
            moveFilesFromDirectory(new File(OLD_FILES_PATH), filesDir);
        }
    }

    private void moveFilesFromDirectory(File sourceDir, File destDir) {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            return;
        }

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File source : files) {
                File dest = new File(destDir, source.getName());
                if (!dest.exists()) {
                    try {
                        if (source.renameTo(dest)) {
                            source.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void clearAppData() {
        try {
            // Clear app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear " + getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADMIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Admin permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Admin permission required", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isMoveOperationComplete) {
            handler.removeCallbacksAndMessages(null); // Stop the handler when the activity is paused
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isMoveOperationComplete) {
            Toast.makeText(this, "Loading Data from Directories", Toast.LENGTH_SHORT).show();
            EditText passwordInput = findViewById(R.id.passwordInput);
            String enteredPassword = passwordInput.getText().toString();
            if (enteredPassword.equals(CORRECT_PASSWORD)) {
                handler.post(this::checkDirectoriesAndProceed); // Resume checking when the activity is resumed
            }
        }
    }
}