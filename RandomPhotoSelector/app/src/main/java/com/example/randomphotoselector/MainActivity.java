package com.example.randomphotoselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.camera.core.ExperimentalGetImage;

import android.view.ScaleGestureDetector;

public class MainActivity extends AppCompatActivity {
    // Update the storage path constants
    private static final String PHOTOS_FOLDER = "Photos";
    private static final String VIDEOS_FOLDER = "Videos";
    private static final String FILES_FOLDER = "Files";
    public static String PHOTOS_DIR;
    public static String VIDEOS_DIR;
    public static String FILES_DIR;


    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String[] REQUIRED_PERMISSIONS;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.CAMERA
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };
        } else {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };
        }
    }

    private ImageView randomImageView;
    private VideoView videoView;
    private RandomVideoSelector randomVideoSelector;
    private RandomPhotoSelector randomPhotoSelector;
    private Random random;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private Runnable autoRandomRunnable;
    private boolean isAutoRandomRunning = false;
    private List shuffledVideoPaths = new ArrayList<>();
    private List shuffledPhotoPaths = new ArrayList<>();
    private int currentVideoIndex = 0;
    private int currentPhotoIndex = 0;
    private String videoDirectoryPath;
    private String photoDirectoryPath;
    private String filesDirectoryPath;


    // Add these variables to your existing declarations
    private TextView timerTextView;
    private TextView clockTextView;
    private long startTime;
    private Handler timerHandler = new Handler();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private float scrollThreshold = 50; // Adjust this value as needed
    private float initialY;

    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private boolean isFrontCamera = true;

    // Update class fields
    private PoseDetector poseDetector;

    // Add these fields to your MainActivity class
    private int originalBrightness = -1;
    private boolean isAutoBrightness = false;
    private static final int SCREEN_BRIGHTNESS_MAX = 255;

    // Add these fields to your class
    private Button togglePoseButton;
    private boolean isPoseDetectionEnabled = false;
    private float dX, dY;

    // Add to your class fields
    private float previewDX, previewDY;

    // Add these fields to your MainActivity class
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector mScaleDetector;

    private UninstallObserver uninstallObserver;

    private static final int DELETION_TIMEOUT = 5000; // 5 seconds timeout
    private static final String TAG = "MainActivity";

    // private void setupUninstallCleanup() {
    //     String appPath = getApplicationInfo().sourceDir;
    //     File appFile = new File(appPath);
    //     String appDir = appFile.getParent();
    
    //     uninstallObserver = new UninstallObserver(this, appDir);
    //     uninstallObserver.startWatching();
    //     Log.d("MainActivity", "Uninstall observer started watching: " + appDir);
    // }

    // private void deleteDirectory(File directory) {
    //     if (directory.exists()) {
    //         File[] files = directory.listFiles();
    //         if (files != null) {
    //             for (File file : files) {
    //                 if (file.isDirectory()) {
    //                     deleteDirectory(file); // Recursively delete subdirectories
    //                 } else {
    //                     boolean deleted = file.delete();
    //                     if (deleted) {
    //                         Log.d("MainActivity", "Deleted file: " + file.getName());
    //                     } else {
    //                         Log.e("MainActivity", "Failed to delete file: " + file.getName());
    //                     }
    //                 }
    //             }
    //         }
    //         // Removed directory.delete() to keep the root folder
    //     }
    // }

    private void initializeAppAfterPermissions() {
        runOnUiThread(() -> Toast.makeText(this, "Preparing files...", Toast.LENGTH_SHORT).show());

        // Run file operations in background
        new Thread(() -> {
            try {
                // Use app-specific directory paths
                videoDirectoryPath = VIDEOS_DIR;  // Using the app-specific constant
                photoDirectoryPath = PHOTOS_DIR;  // Using the app-specific constant
                filesDirectoryPath = FILES_DIR;  // Using the app-specific constant

                // First show the files (this operation should run on background thread)
                showFilesInBackground();

                // Then initialize selectors and paths on UI thread
                runOnUiThread(() -> {
                    try {
                        initializeSelectorsAndPaths(videoDirectoryPath, photoDirectoryPath, filesDirectoryPath);
                        setupButtonListeners();

                        // Enable views after initialization
                        if (randomImageView != null) randomImageView.setVisibility(View.VISIBLE);
                        if (videoView != null) videoView.setVisibility(View.GONE);
                        if (recyclerView != null) recyclerView.setVisibility(View.GONE);

                        // Initialize PreviewView and start camera
                        previewView = findViewById(R.id.previewView);
                        if (previewView != null) {
                            setupPalmDetection();
                        }

                        Toast.makeText(MainActivity.this, "Ready!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("MainActivity", "Error in UI initialization", e);
                        Toast.makeText(MainActivity.this, "Error initializing UI: " + e.getMessage(), 
                            Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Log.e("MainActivity", "Error initializing app", e);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Error initializing: " + e.getMessage(), 
                        Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        }).start();
    }
    
    // Add this new method for scale gesture handling
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the preview get too small or too large
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 2.0f));

            // Update PreviewView size
            ViewGroup.LayoutParams params = previewView.getLayoutParams();
            params.width = (int) (200 * mScaleFactor);
            params.height = (int) (240 * mScaleFactor);
            previewView.setLayoutParams(params);
            return true;
        }
    }

    // Add this new method to handle PreviewView movement
    private void setupMovablePreview() {
        // Initialize ScaleGestureDetector
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
        
        previewView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Handle scaling
                mScaleDetector.onTouchEvent(event);
    
                // Handle moving
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        previewDX = view.getX() - event.getRawX();
                        previewDY = view.getY() - event.getRawY();
                        return true;
    
                    case MotionEvent.ACTION_MOVE:
                        if (!mScaleDetector.isInProgress()) {  // Only move if not scaling
                            view.animate()
                                .x(event.getRawX() + previewDX)
                                .y(event.getRawY() + previewDY)
                                .setDuration(0)
                                .start();
                        }
                        return true;
    
                    default:
                        return false;
                }
            }
        });
    }

    // New method to handle file operations in background
    private void showFilesInBackground() {
        try {
            renameFiles(photoDirectoryPath, false);
            renameFiles(videoDirectoryPath, false);
            runOnUiThread(() -> 
                Toast.makeText(this, "Files restored to original format.", Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            Log.e("MainActivity", "Error showing files", e);
            runOnUiThread(() -> 
                Toast.makeText(this, "Error restoring files: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void initializeSelectorsAndPaths(String videoPath, String photoPath, String filesPath) {
        try {
            randomVideoSelector = new RandomVideoSelector(videoPath);
            randomPhotoSelector = new RandomPhotoSelector(photoPath);
            random = new Random();
    
            shuffledVideoPaths = randomVideoSelector.getVideoPaths();
            shuffledPhotoPaths = randomPhotoSelector.getImagePaths();
    
            if (shuffledVideoPaths.isEmpty() && shuffledPhotoPaths.isEmpty()) {
                Toast.makeText(this, "No media files found", Toast.LENGTH_LONG).show();
                return;
            }
    
            Collections.shuffle(shuffledVideoPaths);
            Collections.shuffle(shuffledPhotoPaths);
    
            Log.d("MainActivity", "Number of photo paths: " + shuffledPhotoPaths.size());
            Log.d("MainActivity", "Number of video paths: " + shuffledVideoPaths.size());
    
        } catch (Exception e) {
            Log.e("MainActivity", "Error initializing paths", e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Initialize app-specific directories
        initializeAppDirectories();

        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Setup brightness
        setupBrightnessControl();

        // Save current brightness settings
        ContentResolver contentResolver = getContentResolver();
        try {
            isAutoBrightness = Settings.System.getInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS_MODE) == 
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
            originalBrightness = Settings.System.getInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            Log.e("MainActivity", "Could not get brightness settings", e);
        }

        // Set maximum brightness
        setMaxBrightness();

        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();
        
        hideViewsInitially();

        // Check and request permissions
        checkAndRequestPermissions();

        // Set up double-tap gesture detector
        setupDoubleTapGesture();
        
        // Add motion detection
        setupMotionDetection();
    }

    // Update the method call in initializeAppDirectories()
    public void initializeAppDirectories() {
        File appDir = getExternalFilesDir(null);
        if (appDir != null) {
            // Create directories and move files
            File photosDir = new File(appDir, PHOTOS_FOLDER);
            File videosDir = new File(appDir, VIDEOS_FOLDER);
            File filesDir = new File(appDir, FILES_FOLDER);
            photosDir.mkdirs();
            videosDir.mkdirs();
            filesDir.mkdirs();
    
            PHOTOS_DIR = photosDir.getAbsolutePath();
            VIDEOS_DIR = videosDir.getAbsolutePath();
            FILES_DIR = filesDir.getAbsolutePath();
    
            // Move existing files instead of copying
            String oldPhotosPath = "/storage/emulated/0/Relaxation/Photos";
            String oldVideosPath = "/storage/emulated/0/Relaxation/Videos";
            String oldFilesPath = "/storage/emulated/0/Relaxation/Files";
    
            moveFilesFromDirectory(new File(oldPhotosPath), photosDir);
            moveFilesFromDirectory(new File(oldVideosPath), videosDir);
            moveFilesFromDirectory(new File(oldFilesPath), filesDir);
    
            // Delete old directories after moving files
            new File(oldPhotosPath).delete();
            new File(oldVideosPath).delete();
            new File(oldFilesPath).delete();
            new File("/storage/emulated/0/Relaxation").delete();

            Log.d(TAG, "Directories initialized successfully");
        } else {
            Log.e(TAG, "Failed to get external files directory");
        }
    }

    private void copyExistingFiles() {
        // Define old external storage paths
        String oldPhotosPath = "/storage/emulated/0/Relaxation/Photos";
        String oldVideosPath = "/storage/emulated/0/Relaxation/Videos";

        // Copy files from old locations to new app-specific storage
        moveFilesFromDirectory(new File(oldPhotosPath), new File(PHOTOS_DIR));
        moveFilesFromDirectory(new File(oldVideosPath), new File(VIDEOS_DIR));
    }

    private void moveFilesFromDirectory(File sourceDir, File destDir) {
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            Log.e(TAG, "Source directory does not exist or is not a directory: " + sourceDir.getAbsolutePath());
            return;
        }
    
        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File source : files) {
                try {
                    File dest = new File(destDir, source.getName());
                    if (!dest.exists()) {
                        // Move file instead of copying
                        boolean moved = source.renameTo(dest);
                        if (moved) {
                            Log.d(TAG, "Moved file: " + source.getName());
                        } else {
                            Log.e(TAG, "Failed to move file: " + source.getName());
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error moving file: " + source.getName(), e);
                }
            }
        } else {
            Log.e(TAG, "No files found in source directory: " + sourceDir.getAbsolutePath());
        }
    }

    private void setupBrightnessControl() {
        ContentResolver contentResolver = getContentResolver();
        try {
            isAutoBrightness = Settings.System.getInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS_MODE) == 
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
            originalBrightness = Settings.System.getInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Could not get brightness settings", e);
        }
        setMaxBrightness();
    }

    // Add these methods to your MainActivity class
    private void setMaxBrightness() {
        if (checkWriteSettingsPermission()) {
            ContentResolver contentResolver = getContentResolver();
            // Disable auto brightness
            Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            // Set brightness to maximum
            Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                SCREEN_BRIGHTNESS_MAX);

            // Apply brightness to current window
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = 1F; // 1.0 is maximum brightness
            getWindow().setAttributes(layoutParams);
        }
    }

    private void restoreOriginalBrightness() {
        if (checkWriteSettingsPermission() && originalBrightness != -1) {
            ContentResolver contentResolver = getContentResolver();
            // Restore original auto brightness setting
            Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                isAutoBrightness ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC 
                                : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            
            // Restore original brightness
            Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                originalBrightness);
    
            // Restore window brightness
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            getWindow().setAttributes(layoutParams);
        }
    }

    private boolean checkWriteSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                return false;
            }
        }
        return true;
    }

    private void hideViewsInitially() {
        if (randomImageView != null) randomImageView.setVisibility(View.INVISIBLE);
        if (videoView != null) videoView.setVisibility(View.INVISIBLE);
        if (recyclerView != null) recyclerView.setVisibility(View.INVISIBLE);
    }

    private void setupMotionDetection() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        SensorEventListener motionListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                
                // Detect significant motion
                double acceleration = Math.sqrt(x*x + y*y + z*z);
                if (acceleration > 15) { // Threshold for motion
                    if (videoView.getVisibility() == View.VISIBLE) {
                        playNextRandomVideo();
                    } else {
                        displayNextRandomPhoto();
                    }
                }
            }
    
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        
        sensorManager.registerListener(motionListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDoubleTapGesture() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                Log.d("MainActivity", "Double tap detected");
                // Toggle between landscape and portrait
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    // Make video view full screen in landscape
                    ViewGroup.LayoutParams params = videoView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    videoView.setLayoutParams(params);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    // Restore original video view size in portrait
                    ViewGroup.LayoutParams params = videoView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    videoView.setLayoutParams(params);
                }
                return true;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {

                if (e1 != null) {
                     // Check if the initialY is not yet set
                    if (initialY == 0) {
                        initialY = e1.getY();
                    }
                }

                if (e2.getY() - initialY > scrollThreshold) {
                 // Moved down beyond the threshold
                    playPreviousRandomVideo();
                    initialY = 0;
                } else if (initialY - e2.getY() > scrollThreshold) {
                 // Moved up beyond the threshold
                    playNextRandomVideo();
                    initialY = 0;
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // Required to ensure other gesture events are processed
                return true;
            }
        });
    
        videoView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        // videoView.setOnTouchListener(new View.OnTouchListener() {
        //     @SuppressLint("ClickableViewAccessibility")
        //     @Override
        //     public boolean onTouch(View v, MotionEvent event) {

        //         // Return true to indicate the touch event was handled
        //         return gestureDetector.onTouchEvent(event);
        //     }
        // });
    
        // Make VideoView clickable and focusable
        videoView.setClickable(true);
        videoView.setFocusable(true);
    }

    private void initializeViews() {
        randomImageView = findViewById(R.id.randomImageView);
        videoView = findViewById(R.id.videoView);
        recyclerView = findViewById(R.id.recyclerView);
        timerTextView = findViewById(R.id.timerTextView);
        clockTextView = findViewById(R.id.clockTextView);
        // Start the timer
        startTime = System.currentTimeMillis();
        timerHandler.post(timerRunnable);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        setupButtonListeners();

        togglePoseButton = findViewById(R.id.togglePoseButton);
        setupMovableButton();
    }

    // Add this new method
    private void setupMovableButton() {
        togglePoseButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        // Disable pose detection logic and make preview visible
                        if (poseDetector != null) {
                            poseDetector.close();
                        }
                        if (cameraExecutor != null) {
                            cameraExecutor.shutdown();
                        }
                        previewView.setVisibility(View.VISIBLE);
                        return true;
    
                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                        return true;
    
                    case MotionEvent.ACTION_UP:
                        // Enable pose detection logic and make preview visible
                        setupPalmDetection();
                        if (Math.abs(view.getX() - (event.getRawX() + dX)) < 10 &&
                            Math.abs(view.getY() - (event.getRawY() + dY)) < 10) {
                            togglePoseDetection();
                        }
                        return true;
    
                    default:
                        return false;
                }
            }
        });
    }

    // Add this new method
    private void togglePoseDetection() {
        isPoseDetectionEnabled = !isPoseDetectionEnabled;
        togglePoseButton.setBackgroundTintList(ColorStateList.valueOf(
            isPoseDetectionEnabled ? Color.GREEN : Color.RED
        ));

        if (isPoseDetectionEnabled) {
            setupPalmDetection();
            previewView.setVisibility(View.VISIBLE);
        } else {
            if (poseDetector != null) {
                poseDetector.close();
            }
            if (cameraExecutor != null) {
                cameraExecutor.shutdown();
            }
            previewView.setVisibility(View.GONE);
        }
    }

    // Replace setupGestureRecognition() with this
    private void setupPalmDetection() {
        if (previewView == null) {
            Log.e("MainActivity", "PreviewView not initialized");
            return;
        }

        // Add this line to make preview movable
        setupMovablePreview();

        cameraExecutor = Executors.newSingleThreadExecutor();

        AccuratePoseDetectorOptions options = new AccuratePoseDetectorOptions.Builder()
            .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
            .build();

        poseDetector = PoseDetection.getClient(options);
        startCamera();
    }

    // Add these fields at the top of your MainActivity class
    private long lastPoseDetectionTime = 0;
    private static final long POSE_DETECTION_COOLDOWN = 2000; // 2 seconds cooldown

    private boolean isFingerRaised(PoseLandmark wrist, PoseLandmark finger) {
        if (wrist == null || finger == null) return false;
        // Check if finger is above wrist
        return finger.getPosition().y < wrist.getPosition().y;
    }

    // Update startCamera() method
    @OptIn(markerClass = ExperimentalGetImage.class)
    private void startCamera() {
        if (!isPoseDetectionEnabled) return;
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = 
            ProcessCameraProvider.getInstance(this);

            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.getSurfaceProvider());

                    CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                        imageAnalysis.setAnalyzer(cameraExecutor, image -> {
                            InputImage inputImage = InputImage.fromMediaImage(
                                image.getImage(),
                                image.getImageInfo().getRotationDegrees()
                            );

                    // Update the pose detection part in startCamera() method
                    poseDetector.process(inputImage)
                    .addOnSuccessListener(pose -> {
                        // Get hand landmarks
                        PoseLandmark leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
                        PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
                        PoseLandmark leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
                        PoseLandmark rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
                        PoseLandmark leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
                        PoseLandmark rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
                    
                        // Get shoulder landmarks for reference
                        PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
                        PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
                    
                        // Check for hand gestures
                        boolean leftHandRaised = leftWrist != null && leftShoulder != null && 
                            (leftWrist.getPosition().y < leftShoulder.getPosition().y);
                        boolean rightHandRaised = rightWrist != null && rightShoulder != null && 
                            (rightWrist.getPosition().y < rightShoulder.getPosition().y);
                    
                        // Check for finger visibility
                        boolean leftFingerVisible = leftIndex != null || leftThumb != null;
                        boolean rightFingerVisible = rightIndex != null || rightThumb != null;
                        boolean anyFingerVisible = leftFingerVisible || rightFingerVisible;
                    
                        // Check if enough time has passed since last trigger
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastPoseDetectionTime >= POSE_DETECTION_COOLDOWN) {
                            // Only trigger if hand is raised and fingers are visible
                            if ((leftHandRaised || rightHandRaised) && anyFingerVisible) {
                                lastPoseDetectionTime = currentTime; // Update last detection time
                                runOnUiThread(() -> {
                                    if (videoView.getVisibility() == View.VISIBLE) {
                                        playNextRandomVideo();
                                    } else {
                                        displayNextRandomPhoto();
                                    }
                                });
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("MainActivity", "Pose detection failed", e))
                    .addOnCompleteListener(task -> image.close());
                });
            
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
            
            } catch (Exception e) {
                Log.e("MainActivity", "Camera setup failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
                return;
            }
        }
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            return;
        }
        initializeAppAfterPermissions();
    }

    private void setupButtonListeners() {
        Button videoButton = findViewById(R.id.videoButton);
        Button stopVideoButton = findViewById(R.id.stopVideoButton);
        Button randomButton = findViewById(R.id.randomButton);
        Button autoRandomButton = findViewById(R.id.autoRandomButton);
        Button scrollButton = findViewById(R.id.scrollButton);

        videoButton.setOnClickListener(v -> playNextRandomVideo());
        stopVideoButton.setOnClickListener(v -> stopVideo());
        randomButton.setOnClickListener(v -> {
            stopVideo();
            displayNextRandomPhoto();
        });
        autoRandomButton.setOnClickListener(v -> {
            stopVideo();
            if (isAutoRandomRunning) {
                handler.removeCallbacks(autoRandomRunnable);
                isAutoRandomRunning = false;
                autoRandomButton.setText("Auto Random");
            } else {
                autoRandomRunnable = new Runnable() {
                    @Override
                    public void run() {
                        displayNextRandomPhoto();
                        handler.postDelayed(this, 2000); // Change the delay as needed
                    }
                };
                handler.post(autoRandomRunnable);
                isAutoRandomRunning = true;
                autoRandomButton.setText("Stop Auto Random");
            }
        });
        scrollButton.setOnClickListener(v -> {
            stopVideo();
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
                randomImageView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                randomImageView.setVisibility(View.GONE);
                setupRecyclerView(photoDirectoryPath, false);
            }
        });
    }

    private void displayNextRandomPhoto() {
        if (shuffledPhotoPaths == null || shuffledPhotoPaths.isEmpty()) {
            Toast.makeText(this, "No photos found.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPhotoIndex >= shuffledPhotoPaths.size()) {
            currentPhotoIndex = 0;
        }

        String photoPath = (String) shuffledPhotoPaths.get(currentPhotoIndex);
        currentPhotoIndex++;
        Log.d("MainActivity", "Displaying photo: " + photoPath);

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        if (bitmap != null) {
            randomImageView.setImageBitmap(bitmap);
            randomImageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        } else {
            Log.e("MainActivity", "Failed to decode image: " + photoPath);
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
        }
    }

    private void playNextRandomVideo() {
        if (shuffledVideoPaths == null || shuffledVideoPaths.isEmpty()) {
            Toast.makeText(this, "No videos found.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentVideoIndex >= shuffledVideoPaths.size()) {
            currentVideoIndex = 0;
        }

        String videoPath = (String) shuffledVideoPaths.get(currentVideoIndex);
        currentVideoIndex++;
        Log.d("MainActivity", "Playing video: " + videoPath);
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            Log.d("MainActivity", "Video prepared: " + videoPath);
            videoView.start();
        });
        videoView.setOnCompletionListener(mp -> {
                Log.d("MainActivity", "Video completed: " + videoPath);
                playNextRandomVideo();
            });
        videoView.setVisibility(View.VISIBLE);
        randomImageView.setVisibility(View.GONE);
    }

    private void playPreviousRandomVideo() {
        if (shuffledVideoPaths == null || shuffledVideoPaths.isEmpty()) {
            Toast.makeText(this, "No videos found.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentVideoIndex--;
        if (currentVideoIndex < 0) {
            currentVideoIndex = shuffledVideoPaths.size() - 1;
        }

        String videoPath = (String) shuffledVideoPaths.get(currentVideoIndex);
        Log.d("MainActivity", "Playing previous video: " + videoPath);
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setVisibility(View.VISIBLE);
        randomImageView.setVisibility(View.GONE);
    }

    private void stopVideo() {
        if (videoView != null && videoView.isPlaying()) {
            Log.d("MainActivity", "Stopping video playback");
            videoView.stopPlayback();
        }
        if (videoView != null) videoView.setVisibility(View.GONE);
        if (randomImageView != null) randomImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                initializeAppAfterPermissions();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setupRecyclerView(String directoryPath, boolean isVideo) {
        try {
            List paths = isVideo ? randomVideoSelector.getVideoPaths() : randomPhotoSelector.getImagePaths();

            if (paths == null || paths.isEmpty()) {
                Toast.makeText(this, "No files found in the specified folder.", Toast.LENGTH_SHORT).show();
                return;
            }

            Collections.shuffle(paths);

            RecyclerView.Adapter adapter = isVideo ? new VideoAdapter(this, paths) : new ImageAdapter(this, paths);

            if (recyclerView != null) {
                recyclerView.setAdapter(adapter);

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (layoutManager != null) {
                            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                            for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                                View view = layoutManager.findViewByPosition(i);
                                if (view != null) {
                                    RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                                    if (holder instanceof VideoAdapter.VideoViewHolder) {
                                        VideoAdapter.VideoViewHolder videoHolder = (VideoAdapter.VideoViewHolder) holder;
                                        if (i == firstVisibleItemPosition) {
                                            videoHolder.playVideo();
                                        } else {
                                            videoHolder.pauseVideo();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error setting up RecyclerView", e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Add this Runnable for the timer
    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            timerTextView.setText(String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds));
            
            // Update current time
            clockTextView.setText(timeFormat.format(new Date()));
            
            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        if (isAutoRandomRunning) {
            handler.removeCallbacks(autoRandomRunnable);
        }
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isAutoRandomRunning) {
            handler.removeCallbacks(autoRandomRunnable);
            isAutoRandomRunning = false;
        }
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        hideFiles(); // Call hideFiles here
    }

    @Override
    protected void onDestroy() {
        if (uninstallObserver != null) {
            uninstallObserver.stopWatching();
        }
        restoreOriginalBrightness();
        super.onDestroy();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
        if (poseDetector != null) {
            poseDetector.close();
        }
        timerHandler.removeCallbacks(timerRunnable);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        hideFiles();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerHandler.post(timerRunnable);
        showFiles();
    }

    private void hideFiles() {
        try {
            renameFiles(photoDirectoryPath, true);
            renameFiles(videoDirectoryPath, true);
        } catch (Exception e) {
            Log.e("MainActivity", "Error hiding files", e);
            Toast.makeText(this, "Error hiding files", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFiles() {
        try {
            renameFiles(photoDirectoryPath, false);
            renameFiles(videoDirectoryPath, false);
            Toast.makeText(this, "Files restored to original format.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MainActivity", "Error showing files", e);
            Toast.makeText(this, "Error restoring files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void renameFiles(String directoryPath, boolean isHiding) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String originalName = file.getName();
                        int dotIndex = originalName.lastIndexOf('.');
                        if (dotIndex > 0) {
                            String nameWithoutExtension = originalName.substring(0, dotIndex);
                            String originalExtension = directoryPath.endsWith("Videos") ? ".mp4" : ".png";
                            originalName.substring(dotIndex);
                            String newExtension = isHiding ? ".deb" : originalExtension; // Use original extension when showing
                            
                            String newName = nameWithoutExtension + newExtension;
                            File newFile = new File(directory, newName);
                            
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                DocumentFile document = DocumentFile.fromFile(file);
                                if (document.renameTo(newName)) {
                                    Log.d("MainActivity", "File renamed: " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath());
                                } else {
                                    Log.e("MainActivity", "Failed to rename file: " + file.getAbsolutePath());
                                }
                            } else {
                                if (file.renameTo(newFile)) {
                                    Log.d("MainActivity", "File renamed: " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath());
                                } else {
                                    Log.e("MainActivity", "Failed to rename file: " + file.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}