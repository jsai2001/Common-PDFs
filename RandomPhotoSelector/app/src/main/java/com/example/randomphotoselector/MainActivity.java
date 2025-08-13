package com.example.randomphotoselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.pose.PoseDetection;
//import com.google.mlkit.vision.pose.PoseDetector;
//import com.google.mlkit.vision.pose.PoseLandmark;
//import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.camera.core.ExperimentalGetImage;

import android.view.ScaleGestureDetector;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    // Update the storage path constants
    private static final String DESI_FOLDER = "Desi";
    private static final String FOREIGN_FOLDER = "Foreign";
    private static final String FAV_FOLDER = "Fav";
    public static String DESI_DIR;
    public static String FOREIGN_DIR;
    public static String FAV_DIR;
    private static final String PHOTOS_FOLDER = "Photos";
    private static final String VIDEOS_FOLDER = "Videos";
    private static final String FILES_FOLDER = "Files";
    public static String PHOTOS_DIR;
    public static String VIDEOS_DIR;
    public static String FILES_DIR;

    private TextView videoProgressLabel;
    private Handler videoProgressHandler = new Handler();

    private boolean isUsingDesiFolder = true; // Start with Desi folder

    private boolean isSeekInProgress = false;

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
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private RecyclerView recyclerView;
    private ImageButton muteButton;

    private RandomVideoSelector randomVideoSelector;
    private RandomPhotoSelector randomPhotoSelector;
    private Random random;
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
//    private PoseDetector poseDetector;

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

    private MediaSession mediaSession;

    private float[] playbackSpeeds = {1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f}; // Playback speed options
    private int currentSpeedIndex = 0; // Index to track the current speed
    private Button playbackSpeedButton; // Reference to the playback speed button

    private Button intervalButton;
    private int[] intervals = {1, 2, 3, 4, 5}; // Available intervals in seconds
    private int currentIntervalIndex = 4; // Default to 5 seconds
    private Handler photoChangeHandler = new Handler(); // Handler for changing photos
    private Runnable photoChangeRunnable;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener motionListener;
    private static final float MOTION_THRESHOLD = 2.0f; // Adjust as needed
    private long lastMotionTime = 0;
    private boolean isVideoViewActive = false; // Track if the VideoView is active

    private String currentlyPlayingVideoPath = null;

    private boolean isFavMode = false;
    private List<String> favVideoPaths = new ArrayList<>();

    // Add to your class fields
    private String pendingFavMovePath = null;

    private void switchCamera() {
        isFrontCamera = !isFrontCamera; // Toggle the camera
        startCamera(); // Restart the camera with the new lens facing
    }

    private void setupMediaSession() {
        mediaSession = new MediaSession(this, "RandomPhotoSelector");
    
        // Set the callback for media button events
        mediaSession.setCallback(new MediaSession.Callback() {
            @Override
            public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
                KeyEvent keyEvent = mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyEvent.getKeyCode()) {
                        case KeyEvent.KEYCODE_MEDIA_NEXT:
                            Log.d("MediaSession", "Next video gesture detected");
                            playNextRandomVideo();
                            return true;
    
                        case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                            Log.d("MediaSession", "Previous video gesture detected");
                            playPreviousRandomVideo();
                            return true;
    
                        default:
                            break;
                    }
                }
                return super.onMediaButtonEvent(mediaButtonIntent);
            }
        });
    
        // Set the playback state to allow media button events
        PlaybackState playbackState = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE |
                            PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS)
                .build();
        mediaSession.setPlaybackState(playbackState);
    
        // Activate the media session
        mediaSession.setActive(true);
    }

    private void changePlaybackSpeed() {
        currentSpeedIndex = (currentSpeedIndex + 1) % playbackSpeeds.length;
        float newSpeed = playbackSpeeds[currentSpeedIndex];
        if (exoPlayer != null) {
            exoPlayer.setPlaybackParameters(new PlaybackParameters(newSpeed));
        }
        playbackSpeedButton.setText(String.format(Locale.getDefault(), "%.2fx", newSpeed));
    }

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

                        if (randomImageView != null) randomImageView.setVisibility(View.VISIBLE);
                        if (playerView != null) playerView.setVisibility(View.GONE);
                        if (recyclerView != null) recyclerView.setVisibility(View.GONE);

                        // Hide the progress bar after initialization
                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        if (progressBar != null) progressBar.setVisibility(View.GONE);

                        // Initialize PreviewView and start camera
                        previewView = findViewById(R.id.previewView);
                        if (previewView != null) {
//                            setupPalmDetection();
                        }

                        // Move this block here:
                        if (currentlyPlayingVideoPath == null) {
                            String folder = isUsingDesiFolder ? DESI_DIR : FOREIGN_DIR;
                            List<String> videoPaths = getVideoPathsFromFolder(folder);
                            if (videoPaths != null && !videoPaths.isEmpty()) {
                                currentlyPlayingVideoPath = videoPaths.get(0);
                                playVideoAfterOrientationChange();
                            }
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
            params.width = (int) (400 * mScaleFactor);
            params.height = (int) (480 * mScaleFactor);
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

        setupMediaSession();

        setupScrollGesture();

        setupPlayerViewTouch();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        motionListener = new SensorEventListener() {
            private float[] lastValues = new float[3];
        
            @Override
            public void onSensorChanged(SensorEvent event) {
                float dx = Math.abs(event.values[0] - lastValues[0]);
                float dy = Math.abs(event.values[1] - lastValues[1]);
                float dz = Math.abs(event.values[2] - lastValues[2]);
                if (dx > MOTION_THRESHOLD || dy > MOTION_THRESHOLD || dz > MOTION_THRESHOLD) {
                    showBottomBarAndMute();
                    scheduleHideUi();
                    lastMotionTime = System.currentTimeMillis();
                }
                System.arraycopy(event.values, 0, lastValues, 0, event.values.length);
            }
        
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(motionListener, accelerometer, SensorManager.SENSOR_DELAY_UI);

        // Initialize the interval button
        intervalButton = findViewById(R.id.intervalButton);
        intervalButton.setOnClickListener(v -> changeInterval());

        // Initialize the Auto Random button
        Button autoRandomButton = findViewById(R.id.autoRandomButton);
        autoRandomButton.setOnClickListener(v -> toggleAutoRandomMode());

        if (savedInstanceState == null) {
            // First launch or after orientation change
            String folder = isUsingDesiFolder ? DESI_DIR : FOREIGN_DIR;
            List<String> videoPaths = getVideoPathsFromFolder(folder);
            if (videoPaths != null && !videoPaths.isEmpty()) {
                currentlyPlayingVideoPath = videoPaths.get(0);
                playVideoAfterOrientationChange();
            }
        }
    }

    private Handler uiHideHandler = new Handler();
    private Runnable hideUiRunnable;

    private void showBottomBarAndMute() {
        LinearLayout bottomBar = findViewById(R.id.bottomBar);
        ImageButton muteButton = findViewById(R.id.muteButton);
        FloatingActionButton switchCameraButton = findViewById(R.id.switchCameraButton);
        Button playbackSpeedButton = findViewById(R.id.playbackSpeedButton);

        if (bottomBar != null) bottomBar.setVisibility(View.VISIBLE);
        if (muteButton != null) muteButton.setVisibility(View.VISIBLE);
        if (switchCameraButton != null) switchCameraButton.setVisibility(View.VISIBLE);
        if (playbackSpeedButton != null) playbackSpeedButton.setVisibility(View.VISIBLE);
    }

    private void hideBottomBarAndMute() {
        LinearLayout bottomBar = findViewById(R.id.bottomBar);
        ImageButton muteButton = findViewById(R.id.muteButton);
        FloatingActionButton switchCameraButton = findViewById(R.id.switchCameraButton);
        Button playbackSpeedButton = findViewById(R.id.playbackSpeedButton);

        if (bottomBar != null) bottomBar.setVisibility(View.GONE);
        if (muteButton != null) muteButton.setVisibility(View.GONE);
        if (switchCameraButton != null) switchCameraButton.setVisibility(View.GONE);
        if (playbackSpeedButton != null) playbackSpeedButton.setVisibility(View.GONE);
    }

    private void scheduleHideUi() {
        if (hideUiRunnable != null) uiHideHandler.removeCallbacks(hideUiRunnable);
        hideUiRunnable = this::hideBottomBarAndMute;
        uiHideHandler.postDelayed(hideUiRunnable, 3000); // 3 seconds
    }

    private void changeInterval() {
        currentIntervalIndex = (currentIntervalIndex + 1) % intervals.length; // Cycle through intervals
        int newInterval = intervals[currentIntervalIndex];
        intervalButton.setText(newInterval + "s");

        // Restart the photo change timer with the new interval
        if (photoChangeRunnable != null) {
            photoChangeHandler.removeCallbacks(photoChangeRunnable);
            startPhotoChangeTimer();
        }
    }

    private void toggleAutoRandomMode() {
        if (intervalButton.getVisibility() == View.GONE) {
            // Enable Auto Random mode
            intervalButton.setVisibility(View.VISIBLE);
            startPhotoChangeTimer();
        } else {
            // Disable Auto Random mode
            intervalButton.setVisibility(View.GONE);
            stopPhotoChangeTimer();
        }
    }

    private void startPhotoChangeTimer() {
        photoChangeRunnable = new Runnable() {
            @Override
            public void run() {
                changePhoto(); // Method to change the photo
                photoChangeHandler.postDelayed(this, intervals[currentIntervalIndex] * 1000L); // Schedule next change
            }
        };
        photoChangeHandler.postDelayed(photoChangeRunnable, intervals[currentIntervalIndex] * 1000L);
    }

    private void stopPhotoChangeTimer() {
        if (photoChangeRunnable != null) {
            photoChangeHandler.removeCallbacks(photoChangeRunnable);
        }
    }

    private void changePhoto() {
        displayNextRandomPhoto();
        // Toast.makeText(this, "Photo changed!", Toast.LENGTH_SHORT).show();
    }

    // Update the method call in initializeAppDirectories()
    public void initializeAppDirectories() {
        File appDir = getExternalFilesDir(null);
        if (appDir != null) {
            // Create directories and move files
            File photosDir = new File(appDir, PHOTOS_FOLDER);
            File videosDir = new File(appDir, VIDEOS_FOLDER);
            File filesDir = new File(appDir, FILES_FOLDER);
            File desiDir = new File(appDir, DESI_FOLDER);
            File foreignDir = new File(appDir, FOREIGN_FOLDER);
            File favDir = new File(appDir, FAV_FOLDER);

            photosDir.mkdirs();
            videosDir.mkdirs();
            filesDir.mkdirs();
            desiDir.mkdirs();
            foreignDir.mkdirs();
            favDir.mkdirs();
    
            PHOTOS_DIR = photosDir.getAbsolutePath();
            VIDEOS_DIR = videosDir.getAbsolutePath();
            FILES_DIR = filesDir.getAbsolutePath();
            DESI_DIR = desiDir.getAbsolutePath();
            FOREIGN_DIR = foreignDir.getAbsolutePath();
            FAV_DIR = favDir.getAbsolutePath();
    
            // Move existing files instead of copying
            String oldPhotosPath = "/storage/emulated/0/Relaxation/Photos";
            String oldVideosPath = "/storage/emulated/0/Relaxation/Videos";
            String oldFilesPath = "/storage/emulated/0/Relaxation/Files";
            String oldDesiVideosPath = "/storage/emulated/0/Relaxation/Desi";
            String oldForeignVideosPath = "/storage/emulated/0/Relaxation/Foreign";

            createDirectoryIfNotExists(oldPhotosPath);
            createDirectoryIfNotExists(oldVideosPath);
            createDirectoryIfNotExists(oldFilesPath);
            createDirectoryIfNotExists(oldDesiVideosPath);
            createDirectoryIfNotExists(oldForeignVideosPath);
    
            moveFilesUsingWorkManager(new File(oldPhotosPath), photosDir);
            moveFilesUsingWorkManager(new File(oldVideosPath), videosDir);
            moveFilesUsingWorkManager(new File(oldFilesPath), filesDir);
            moveFilesUsingWorkManager(new File(oldDesiVideosPath), desiDir);
            moveFilesUsingWorkManager(new File(oldForeignVideosPath), foreignDir);
    
            // Delete old directories after moving files
            new File(oldPhotosPath).delete();
            new File(oldVideosPath).delete();
            new File(oldFilesPath).delete();
            new File(oldDesiVideosPath).delete();
            new File(oldForeignVideosPath).delete();
            new File("/storage/emulated/0/Relaxation").delete();

            Log.d(TAG, "Directories initialized successfully");
        } else {
            Log.e(TAG, "Failed to get external files directory");
        }
    }

    // Helper method to create a directory if it does not exist
    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                Log.d(TAG, "Directory created: " + path);
            } else {
                Log.e(TAG, "Failed to create directory: " + path);
            }
        } else {
            Log.d(TAG, "Directory already exists: " + path);
        }
    }

    private void copyExistingFiles() {
        // Define old external storage paths
        String oldPhotosPath = "/storage/emulated/0/Relaxation/Photos";
        String oldVideosPath = "/storage/emulated/0/Relaxation/Videos";

        // Copy files from old locations to new app-specific storage
        moveFilesUsingWorkManager(new File(oldPhotosPath), new File(PHOTOS_DIR));
        moveFilesUsingWorkManager(new File(oldVideosPath), new File(VIDEOS_DIR));
    }

    private void moveFilesUsingWorkManager(File sourceDir, File destDir) {
        if (sourceDir == null || destDir == null) {
            Log.e(TAG, "Source or destination directory is null");
            return;
        }
    
        Data inputData = new Data.Builder()
                .putString("sourcePath", sourceDir.getAbsolutePath())
                .putString("destPath", destDir.getAbsolutePath())
                .build();
    
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(FileMoveWorker.class)
                .setInputData(inputData)
                .build();
    
        WorkManager.getInstance(this).enqueue(workRequest);

        // Observe progress
        observeWorkProgress(workRequest.getId());
    }

    private void observeWorkProgress(UUID workRequestId) {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequestId)
            .observe(this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    if (workInfo != null) {
                        if (workInfo.getState().isFinished()) {
                            // Task is complete
                            Toast.makeText(MainActivity.this, "File transfer complete!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Update progress
                            Data progressData = workInfo.getProgress();
                            int progress = progressData.getInt("progress", 0);
                            updateProgressBar(progress);
                        }
                    }
                }
            });
    }

    private void updateProgressBar(int progress) {
        ProgressBar progressBar = findViewById(R.id.progressBar); // Ensure you have a ProgressBar in your layout
        progressBar.setProgress(progress);
    }

    private void moveFilesFromDirectory(File sourceDir, File destDir) {
        new Thread(() -> {
            if (!sourceDir.exists() || !sourceDir.isDirectory()) {
                Log.e(TAG, "Source directory does not exist or is not a directory: " + sourceDir.getAbsolutePath());
                return;
            }

            while (true) {
                File[] files = sourceDir.listFiles();
                if (files == null || files.length == 0) {
                    // Exit the loop if the source directory is empty
                    Log.d(TAG, "Source directory is empty: " + sourceDir.getAbsolutePath());
                    break;
                }

                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively move files from subdirectories to the source directory
                        moveFilesUsingWorkManager(file, destDir);

                        // Delete the empty subdirectory after processing
                        boolean deleted = file.delete();
                        if (deleted) {
                            Log.d(TAG, "Deleted empty subdirectory: " + file.getAbsolutePath());
                        } else {
                            Log.e(TAG, "Failed to delete subdirectory: " + file.getAbsolutePath());
                        }
                    } else {
                        // Move the file to the destination directory
                        File dest = new File(destDir, file.getName());
                        if (!dest.exists()) {
                            boolean moved = file.renameTo(dest);
                            if (moved) {
                                Log.d(TAG, "Moved file: " + file.getAbsolutePath() + " to " + dest.getAbsolutePath());
                            } else {
                                Log.e(TAG, "Failed to move file: " + file.getAbsolutePath());
                            }
                        } else {
                            Log.w(TAG, "File already exists in destination: " + dest.getAbsolutePath());
                        }
                    }
                }
            }
        }).start();
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

    private void adjustBrightness(float deltaY) {
        ContentResolver contentResolver = getContentResolver();
        try {
            int currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            int newBrightness = currentBrightness + (int) deltaY;
            newBrightness = Math.max(0, Math.min(newBrightness, SCREEN_BRIGHTNESS_MAX)); // Clamp between 0 and max brightness

            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness);

            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = newBrightness / (float) SCREEN_BRIGHTNESS_MAX;
            getWindow().setAttributes(layoutParams);

            Toast.makeText(this, "Brightness: " + newBrightness, Toast.LENGTH_SHORT).show();
        } catch (Settings.SettingNotFoundException e) {
            Log.e("MainActivity", "Error adjusting brightness", e);
        }
    }

    private void adjustVolume(float deltaY) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            int direction = deltaY > 0 ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER;
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, AudioManager.FLAG_SHOW_UI);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupScrollGesture() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (e1 == null || e2 == null) return false;

                float deltaY = e1.getY() - e2.getY(); // Positive for upward scroll, negative for downward scroll
                float x = e1.getX();
                float screenWidth = getResources().getDisplayMetrics().widthPixels;

                if (x < screenWidth / 2) {
                    // Left side: Adjust brightness
                    adjustBrightness(deltaY);
                } else {
                    // Right side: Adjust volume
                    adjustVolume(deltaY);
                }

                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // Required to ensure other gesture events are processed
                return true;
            }
        });

        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void hideViewsInitially() {
        if (randomImageView != null) randomImageView.setVisibility(View.INVISIBLE);
        if (playerView != null) playerView.setVisibility(View.INVISIBLE);
        if (recyclerView != null) recyclerView.setVisibility(View.INVISIBLE);
    }

    private String pendingDeletePath = null;

    @SuppressLint("ClickableViewAccessibility")
    private void setupDoubleTapGesture() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                float x = e.getX();
                float width = playerView.getWidth();
                
                if (isSeekInProgress) {
                    Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (x < width / 3) {
                    // Double-tap on the left portion: Seek backward by 5 seconds
                    if (exoPlayer != null && (exoPlayer.isPlaying() )) {
                        int currentPosition = (int) exoPlayer.getCurrentPosition();
                        int newPosition = Math.max(currentPosition - 5000, 0);
                        isSeekInProgress = true;
                        exoPlayer.seekTo(newPosition);
                        playerView.postDelayed(() -> isSeekInProgress = false, 1000); // 1s delay, adjust as needed
                        // Toast.makeText(MainActivity.this, "Rewind 5 seconds", Toast.LENGTH_SHORT).show();
                    }
                } else if (x > 2 * width / 3) {
                    // Double-tap on the right portion: Seek forward by 5 seconds
                    if (exoPlayer != null && (exoPlayer.isPlaying() )) {
                        int currentPosition = (int) exoPlayer.getCurrentPosition();
                        int duration = (int) exoPlayer.getDuration();;
                        int newPosition = (duration > 0) ? Math.min(currentPosition + 5000, duration - 100) : currentPosition + 5000;
                        isSeekInProgress = true;
                        exoPlayer.seekTo(newPosition);
                        playerView.postDelayed(() -> isSeekInProgress = false, 1000); // 1s delay, adjust as needed
                        // Toast.makeText(MainActivity.this, "Forward 5 seconds", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Double-tap on the center: Toggle between portrait and landscape
                    Log.d("MainActivity", "Double tap detected in center");
                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        // Make video view full screen in landscape
                        ViewGroup.LayoutParams params = playerView.getLayoutParams();
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        playerView.setLayoutParams(params);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        // Restore original video view size in portrait
                        ViewGroup.LayoutParams params = playerView.getLayoutParams();
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        playerView.setLayoutParams(params);
                    }
                }
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float deltaX = e2.getX() - e1.getX();
                float absDeltaX = Math.abs(deltaX);
                float absDeltaY = Math.abs(e2.getY() - e1.getY());

                // Only consider horizontal swipes
                if (absDeltaX > 100 && absDeltaX > absDeltaY) {
                    if (deltaX > 0) {
                        // Swipe right: Favorite after playback
                        if (currentlyPlayingVideoPath != null) {
                            pendingFavMovePath = currentlyPlayingVideoPath;
                            Toast.makeText(MainActivity.this, "Will move to Fav after playback.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Swipe left: Delete after playback
                        if (currentlyPlayingVideoPath != null) {
                            pendingDeletePath = currentlyPlayingVideoPath;
                            Toast.makeText(MainActivity.this, "Will delete after playback.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
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
    
        // Make VideoView clickable and focusable
        playerView.setClickable(true);
        playerView.setFocusable(true);
    }

    @SuppressLint("ClickableViewAccessibility")
private void setupPlayerViewTouch() {
    GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            float x = e.getX();
            float width = playerView.getWidth();

            if (isSeekInProgress) {
                Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (x < width / 3) {
                // Double-tap on the left portion: Seek backward by 5 seconds
                if (exoPlayer != null && (exoPlayer.isPlaying() )) {
                    int currentPosition = (int) exoPlayer.getCurrentPosition();
                    int newPosition = Math.max(currentPosition - 5000, 0);
                    isSeekInProgress = true;
                    exoPlayer.seekTo(newPosition);
                    playerView.postDelayed(() -> isSeekInProgress = false, 1000); // 1s delay, adjust as needed
                }
            } else if (x > 2 * width / 3) {
                // Double-tap on the right portion: Seek forward by 5 seconds
                if (exoPlayer != null && (exoPlayer.isPlaying() )) {
                    int currentPosition = (int) exoPlayer.getCurrentPosition();
                    int duration = (int) exoPlayer.getDuration();
                    int newPosition = (duration > 0) ? Math.min(currentPosition + 5000, duration - 100) : currentPosition + 5000;
                    isSeekInProgress = true;
                    exoPlayer.seekTo(newPosition);
                    playerView.postDelayed(() -> isSeekInProgress = false, 1000); // 1s delay, adjust as needed
                }
            } else {
                // Double-tap on the center: Toggle between portrait and landscape
                Log.d("MainActivity", "Double tap detected in center");
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ViewGroup.LayoutParams params = playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ViewGroup.LayoutParams params = playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    playerView.setLayoutParams(params);
                }
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e2.getX() - e1.getX();
            float absDeltaX = Math.abs(deltaX);
            float absDeltaY = Math.abs(e2.getY() - e1.getY());

            // Only consider horizontal swipes
            if (absDeltaX > 100 && absDeltaX > absDeltaY) {
                if (deltaX > 0) {
                    // Swipe right: Favorite after playback
                    if (currentlyPlayingVideoPath != null) {
                        pendingFavMovePath = currentlyPlayingVideoPath;
                        Toast.makeText(MainActivity.this, "Will move to Fav after playback.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Swipe left: Delete after playback
                    if (currentlyPlayingVideoPath != null) {
                        pendingDeletePath = currentlyPlayingVideoPath;
                        Toast.makeText(MainActivity.this, "Will delete after playback.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            if (e1 != null) {
                if (initialY == 0) {
                    initialY = e1.getY();
                }
            }

            if (e2.getY() - initialY > scrollThreshold) {
                playPreviousRandomVideo();
                initialY = 0;
            } else if (initialY - e2.getY() > scrollThreshold) {
                playNextRandomVideo();
                initialY = 0;
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    });

    playerView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    playerView.setClickable(true);
    playerView.setFocusable(true);
}

    private void initializeViews() {
        randomImageView = findViewById(R.id.randomImageView);
        playerView = findViewById(R.id.playerView);
        recyclerView = findViewById(R.id.recyclerView);
        timerTextView = findViewById(R.id.timerTextView);
        clockTextView = findViewById(R.id.clockTextView);
        videoProgressLabel = findViewById(R.id.videoProgressLabel);
        playbackSpeedButton = findViewById(R.id.playbackSpeedButton);
        FloatingActionButton switchCameraButton = findViewById(R.id.switchCameraButton);
        muteButton = findViewById(R.id.muteButton);

        // ExoPlayer setup
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        // Mute by default
        exoPlayer.setVolume(0f);
        isMuted = true;
        updateMuteIcon();

        // Start the timer
        startTime = System.currentTimeMillis();
        timerHandler.post(timerRunnable);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        setupButtonListeners();

//        setupMovableButton();

        // Set up the switch camera button
        switchCameraButton.setOnClickListener(v -> {
            switchCamera();
            if (previewView != null) {
                previewView.bringToFront();
            }
        });

        // Set up the playback speed button
        playbackSpeedButton.setOnClickListener(v -> changePlaybackSpeed());

        // Bring PreviewView to front so it's always on top
        if (previewView != null) {
            previewView.bringToFront();
        }

        muteButton.setOnClickListener(v -> toggleMute());
        updateMuteIcon();
    }

    // Add this new method
//    private void setupMovableButton() {
//        togglePoseButton.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        dX = view.getX() - event.getRawX();
//                        dY = view.getY() - event.getRawY();
//                        // Disable pose detection logic and make preview visible
//                        if (poseDetector != null) {
//                            poseDetector.close();
//                        }
//                        if (cameraExecutor != null) {
//                            cameraExecutor.shutdown();
//                        }
//                        previewView.setVisibility(View.VISIBLE);
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:
//                        view.animate()
//                            .x(event.getRawX() + dX)
//                            .y(event.getRawY() + dY)
//                            .setDuration(0)
//                            .start();
//                        return true;
//
//                    case MotionEvent.ACTION_UP:
//                        // Enable pose detection logic and make preview visible
//                        setupPalmDetection();
//                        if (Math.abs(view.getX() - (event.getRawX() + dX)) < 10 &&
//                            Math.abs(view.getY() - (event.getRawY() + dY)) < 10) {
//                            togglePoseDetection();
//                        }
//                        return true;
//
//                    default:
//                        return false;
//                }
//            }
//        });
//    }

    // Add this new method
//    private void togglePoseDetection() {
//        isPoseDetectionEnabled = !isPoseDetectionEnabled;
//        togglePoseButton.setBackgroundTintList(ColorStateList.valueOf(
//            isPoseDetectionEnabled ? Color.GREEN : Color.RED
//        ));
//
//        if (isPoseDetectionEnabled) {
//            setupPalmDetection();
//            previewView.setVisibility(View.VISIBLE);
//        } else {
//            if (poseDetector != null) {
//                poseDetector.close();
//            }
//            // Keep the camera on but stop pose detection
//            startCamera();
//        }
//    }

    // Replace setupGestureRecognition() with this
//    private void setupPalmDetection() {
//        if (previewView == null) {
//            Log.e("MainActivity", "PreviewView not initialized");
//            return;
//        }
//
//        // Add this line to make preview movable
//        setupMovablePreview();
//
//        cameraExecutor = Executors.newSingleThreadExecutor();
//
//        AccuratePoseDetectorOptions options = new AccuratePoseDetectorOptions.Builder()
//            .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
//            .build();
//
//        poseDetector = PoseDetection.getClient(options);
//        startCamera();
//    }

    // Add these fields at the top of your MainActivity class
//    private long lastPoseDetectionTime = 0;
//    private static final long POSE_DETECTION_COOLDOWN = 2000; // 2 seconds cooldown
//
//    private boolean isFingerRaised(PoseLandmark wrist, PoseLandmark finger) {
//        if (wrist == null || finger == null) return false;
//        // Check if finger is above wrist
//        return finger.getPosition().y < wrist.getPosition().y;
//    }

    // Update startCamera() method
    @OptIn(markerClass = ExperimentalGetImage.class)
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(isFrontCamera ? CameraSelector.LENS_FACING_FRONT : CameraSelector.LENS_FACING_BACK)
                    .build();

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build();

//                imageAnalysis.setAnalyzer(cameraExecutor, image -> {
//                    if (isPoseDetectionEnabled) {
//                        InputImage inputImage = InputImage.fromMediaImage(
//                            image.getImage(),
//                            image.getImageInfo().getRotationDegrees()
//                        );
//
//                        poseDetector.process(inputImage)
//                            .addOnSuccessListener(pose -> {
//
//                            })
//                            .addOnFailureListener(e -> Log.e("MainActivity", "Pose detection failed", e))
//                            .addOnCompleteListener(task -> image.close());
//                    } else {
//                        image.close();
//                    }
//                });
            
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

    private void updateVideoProgressLabel() {
        if (playerView != null && exoPlayer.isPlaying()) {
            int current = (int) (exoPlayer.getCurrentPosition() / 1000);
            int total = (int) exoPlayer.getDuration() / 1000;
            String progress = String.format(Locale.getDefault(), "%02d:%02d / %02d:%02d",
                    current / 60, current % 60, total / 60, total % 60);
            videoProgressLabel.setText(progress);
        }
        videoProgressHandler.postDelayed(this::updateVideoProgressLabel, 500);
    }

    private void updateVideoProgressLabelExo() {
        videoProgressHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && exoPlayer.isPlaying()) {
                    long current = exoPlayer.getCurrentPosition() / 1000;
                    long total = exoPlayer.getDuration() / 1000;
                    String progress = String.format(Locale.getDefault(), "%02d:%02d / %02d:%02d",
                            current / 60, current % 60, total / 60, total % 60);
                    videoProgressLabel.setText(progress);
                }
                videoProgressHandler.postDelayed(this, 500);
            }
        }, 500);
    }

    private void setupButtonListeners() {
        Button videoButton = findViewById(R.id.videoButton);
        Button stopVideoButton = findViewById(R.id.stopVideoButton);
        Button randomButton = findViewById(R.id.randomButton);
        Button autoRandomButton = findViewById(R.id.autoRandomButton);
        Button scrollButton = findViewById(R.id.scrollButton);
        Button loveButton = findViewById(R.id.loveButton);
        Button favButton = findViewById(R.id.favButton);

        videoButton.setOnClickListener(v -> {
            isFavMode = false; // Exit fav mode when switching to videos mode
            isUsingDesiFolder = !isUsingDesiFolder; // Toggle folder ONLY here
            playNextRandomVideo();
        });
        stopVideoButton.setOnClickListener(v -> stopVideo());
        randomButton.setOnClickListener(v -> {
            stopVideo();
            displayNextRandomPhoto();
        });
        autoRandomButton.setOnClickListener(v -> {
            stopVideo();
            if (intervalButton.getVisibility() == View.GONE) {
                // Enable Auto Random mode
                intervalButton.setVisibility(View.VISIBLE); // Show the interval button
                startPhotoChangeTimer(); // Start the photo change timer
                isAutoRandomRunning = true;
                autoRandomButton.setText("Stop Auto Random");
            } else {
                // Disable Auto Random mode
                intervalButton.setVisibility(View.GONE); // Hide the interval button
                stopPhotoChangeTimer(); // Stop the photo change timer
                isAutoRandomRunning = false;
                autoRandomButton.setText("Auto Random");
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
        loveButton.setOnClickListener(v -> saveCurrentVideoToFav());
        favButton.setOnClickListener(v -> playFavVideos());
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
            playerView.setVisibility(View.GONE);
        } else {
            Log.e("MainActivity", "Failed to decode image: " + photoPath);
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getVideoPathsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        List<String> videoPaths = new ArrayList<>();
    
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".mp4")) {
                        videoPaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    
        return videoPaths;
    }

    private void playNextRandomVideo() {
        if (isFavMode) {
            playNextFavVideo();
            return;
        }
        String currentFolderPath = isUsingDesiFolder ? DESI_DIR : FOREIGN_DIR;
        isVideoViewActive = true;

        List<String> videoPaths = getVideoPathsFromFolder(currentFolderPath);

        if (videoPaths == null || videoPaths.isEmpty()) {
            Toast.makeText(this, "No videos found in " + (isUsingDesiFolder ? "Desi" : "Foreign") + " folder.", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.shuffle(videoPaths);

        if (currentVideoIndex >= videoPaths.size()) {
            currentVideoIndex = 0;
        }

        String videoPath = videoPaths.get(currentVideoIndex);
        currentVideoIndex++;
        currentlyPlayingVideoPath = videoPath;

        // Set orientation/layout based on video dimensions
        int[] dims = getVideoDimensions(videoPath);
        if (dims != null && dims[0] > 0 && dims[1] > 0) {
            int videoWidth = dims[0];
            int videoHeight = dims[1];

            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;

            ViewGroup.LayoutParams params = playerView.getLayoutParams();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape, fit to screen height
                params.height = screenHeight;
                params.width = (int) ((float) screenHeight * videoWidth / videoHeight);
            } else {
                // In portrait, fit to screen width
                params.width = screenWidth;
                params.height = (int) ((float) screenWidth * videoHeight / videoWidth);
            }

            playerView.setLayoutParams(params);

            // Center vertically/horizontally in FrameLayout
            if (playerView.getParent() instanceof FrameLayout) {
                FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) playerView.getLayoutParams();
                flParams.gravity = Gravity.CENTER;
                playerView.setLayoutParams(flParams);
            }
        }

        // Prepare and play video with ExoPlayer
        playerView.setVisibility(View.VISIBLE);
        randomImageView.setVisibility(View.GONE);

        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoPath)));
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

        // Set playback speed
        float speed = playbackSpeeds[currentSpeedIndex];
        exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));

        // Listen for video end
        exoPlayer.addListener(new com.google.android.exoplayer2.Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == com.google.android.exoplayer2.Player.STATE_ENDED) {
                    playNextRandomVideo();
                    movePendingFavIfNeeded();
                }
            }
        });

        updateVideoProgressLabelExo();
    }

    private void playPreviousRandomVideo() {
    if (isFavMode) {
        playNextFavVideo(); // Or implement playPreviousFavVideo() if you want true previous in Fav mode
        return;
    }
    String currentFolderPath = isUsingDesiFolder ? DESI_DIR : FOREIGN_DIR;
    isVideoViewActive = true;

    List<String> videoPaths = getVideoPathsFromFolder(currentFolderPath);

    if (videoPaths == null || videoPaths.isEmpty()) {
        Toast.makeText(this, "No videos found in " + (isUsingDesiFolder ? "Desi" : "Foreign") + " folder.", Toast.LENGTH_SHORT).show();
        return;
    }

    currentVideoIndex--;
    if (currentVideoIndex < 0) {
        currentVideoIndex = videoPaths.size() - 1;
    }

    String videoPath = videoPaths.get(currentVideoIndex);
    currentlyPlayingVideoPath = videoPath;

    // Set orientation/layout based on video dimensions
    int[] dims = getVideoDimensions(videoPath);
    if (dims != null && dims[0] > 0 && dims[1] > 0) {
        int videoWidth = dims[0];
        int videoHeight = dims[1];

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        ViewGroup.LayoutParams params = playerView.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape, fit to screen height
            params.height = screenHeight;
            params.width = (int) ((float) screenHeight * videoWidth / videoHeight);
        } else {
            // In portrait, fit to screen width
            params.width = screenWidth;
            params.height = (int) ((float) screenWidth * videoHeight / videoWidth);
        }

        playerView.setLayoutParams(params);

        // Center vertically/horizontally in FrameLayout
        if (playerView.getParent() instanceof FrameLayout) {
            FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            flParams.gravity = Gravity.CENTER;
            playerView.setLayoutParams(flParams);
        }
    }

    // Prepare and play video with ExoPlayer
    playerView.setVisibility(View.VISIBLE);
    randomImageView.setVisibility(View.GONE);

    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoPath)));
    exoPlayer.prepare();
    exoPlayer.setPlayWhenReady(true);

    // Set playback speed
    float speed = playbackSpeeds[currentSpeedIndex];
    exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));

    // Listen for video end
    exoPlayer.addListener(new com.google.android.exoplayer2.Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int state) {
            if (state == com.google.android.exoplayer2.Player.STATE_ENDED) {
                playNextRandomVideo();
                movePendingFavIfNeeded();
            }
        }
    });

    updateVideoProgressLabelExo();
    movePendingFavIfNeeded();
}

    private void stopVideo() {
        isVideoViewActive = false;
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false); // Pause playback
            exoPlayer.stop(); // Optionally reset playback state
        }
        if (playerView != null) playerView.setVisibility(View.GONE);
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
        if (sensorManager != null && motionListener != null) {
            sensorManager.unregisterListener(motionListener);
        }
        if (isAutoRandomRunning) {
            handler.removeCallbacks(autoRandomRunnable);
        }
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false); // Pause playback
        }
        videoProgressHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isAutoRandomRunning) {
            handler.removeCallbacks(autoRandomRunnable);
            isAutoRandomRunning = false;
        }
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false); // Pause playback
            exoPlayer.stop();                  // Stop playback and reset state
        }
        if (playerView != null) {
            playerView.setVisibility(View.GONE); // Hide the player view
            playerView.setPlayer(null);          // Detach player from view
        }
        videoProgressHandler.removeCallbacksAndMessages(null);
        hideFiles(); // Call hideFiles here
    }

    @Override
    protected void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
        if (uninstallObserver != null) {
            uninstallObserver.stopWatching();
        }
        restoreOriginalBrightness();
        super.onDestroy();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
//        if (poseDetector != null) {
//            poseDetector.close();
//        }
        timerHandler.removeCallbacks(timerRunnable);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (mediaSession != null) {
            mediaSession.release();
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

    private void saveCurrentVideoToFav() {
        if (currentlyPlayingVideoPath == null) {
            Toast.makeText(this, "No video to save.", Toast.LENGTH_SHORT).show();
            return;
        }
        pendingFavMovePath = currentlyPlayingVideoPath;
        Toast.makeText(this, "Will move to Fav after playback.", Toast.LENGTH_SHORT).show();
    }

    private void playFavVideos() {
        isFavMode = true;
        isVideoViewActive = true;
        favVideoPaths = getVideoPathsFromFolder(FAV_DIR);

        if (favVideoPaths == null || favVideoPaths.isEmpty()) {
            Toast.makeText(this, "No Fav videos found.", Toast.LENGTH_SHORT).show();
            isFavMode = false;
            return;
        }

        Collections.shuffle(favVideoPaths);

        currentVideoIndex = 0;
        playNextFavVideo();
    }

    private void playNextFavVideo() {
    if (favVideoPaths == null || favVideoPaths.isEmpty()) {
        Toast.makeText(this, "No Fav videos found.", Toast.LENGTH_SHORT).show();
        return;
    }
    if (currentVideoIndex >= favVideoPaths.size()) {
        currentVideoIndex = 0;
    }
    String videoPath = favVideoPaths.get(currentVideoIndex);
    currentVideoIndex++;
    currentlyPlayingVideoPath = videoPath;

    // Set orientation/layout based on video dimensions
    int[] dims = getVideoDimensions(videoPath);
    if (dims != null && dims[0] > 0 && dims[1] > 0) {
        int videoWidth = dims[0];
        int videoHeight = dims[1];

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        ViewGroup.LayoutParams params = playerView.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape, fit to screen height
            params.height = screenHeight;
            params.width = (int) ((float) screenHeight * videoWidth / videoHeight);
        } else {
            // In portrait, fit to screen width
            params.width = screenWidth;
            params.height = (int) ((float) screenWidth * videoHeight / videoWidth);
        }

        playerView.setLayoutParams(params);

        // Center vertically/horizontally in FrameLayout
        if (playerView.getParent() instanceof FrameLayout) {
            FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            flParams.gravity = Gravity.CENTER;
            playerView.setLayoutParams(flParams);
        }
    }

    // Prepare and play video with ExoPlayer
    playerView.setVisibility(View.VISIBLE);
    randomImageView.setVisibility(View.GONE);

    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoPath)));
    exoPlayer.prepare();
    exoPlayer.setPlayWhenReady(true);

    // Set playback speed
    float speed = playbackSpeeds[currentSpeedIndex];
    exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));

    // Listen for video end
    exoPlayer.addListener(new com.google.android.exoplayer2.Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int state) {
            if (state == com.google.android.exoplayer2.Player.STATE_ENDED) {
                playNextFavVideo();
                movePendingFavIfNeeded();
            }
        }
    });

    updateVideoProgressLabelExo();
    movePendingFavIfNeeded();
}

    private void movePendingFavIfNeeded() {
        if (pendingFavMovePath != null) {
            File srcFile = new File(pendingFavMovePath);
            File destFile = new File(FAV_DIR, srcFile.getName());
            if (!destFile.exists()) {
                boolean moved = srcFile.renameTo(destFile);
                if (moved) {
                    Toast.makeText(this, "Moved to Fav!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to move to Fav.", Toast.LENGTH_SHORT).show();
                }
            }
            pendingFavMovePath = null;
        }
        // Delete if needed
        if (pendingDeletePath != null) {
            File file = new File(pendingDeletePath);
            boolean deleted = file.delete();
            if (deleted) {
                Toast.makeText(this, "Deleted video!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to delete video.", Toast.LENGTH_SHORT).show();
            }
            pendingDeletePath = null;
        }
    }    

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Re-apply video layout for the current video
        playVideoAfterOrientationChange();
    }

    private void playVideoAfterOrientationChange() {
    String videoPath = currentlyPlayingVideoPath;
    if (videoPath == null) return;

    // Set orientation/layout based on video dimensions
    int[] dims = getVideoDimensions(videoPath);
    if (dims != null && dims[0] > 0 && dims[1] > 0) {
        int videoWidth = dims[0];
        int videoHeight = dims[1];

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        ViewGroup.LayoutParams params = playerView.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape, fit to screen height
            params.height = screenHeight;
            params.width = (int) ((float) screenHeight * videoWidth / videoHeight);
        } else {
            // In portrait, fit to screen width
            params.width = screenWidth;
            params.height = (int) ((float) screenWidth * videoHeight / videoWidth);
        }

        playerView.setLayoutParams(params);

        // Center vertically/horizontally in FrameLayout
        if (playerView.getParent() instanceof FrameLayout) {
            FrameLayout.LayoutParams flParams = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            flParams.gravity = Gravity.CENTER;
            playerView.setLayoutParams(flParams);
        }
    }

    // Prepare and play video with ExoPlayer
    playerView.setVisibility(View.VISIBLE);
    randomImageView.setVisibility(View.GONE);

    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(videoPath)));
    exoPlayer.prepare();
    exoPlayer.setPlayWhenReady(true);

    // Set playback speed
    float speed = playbackSpeeds[currentSpeedIndex];
    exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));

    // Listen for video end
    exoPlayer.addListener(new com.google.android.exoplayer2.Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int state) {
            if (state == com.google.android.exoplayer2.Player.STATE_ENDED) {
                if (isFavMode) playNextFavVideo();
                else playNextRandomVideo();
                movePendingFavIfNeeded();
            }
        }
    });

    updateVideoProgressLabelExo();
    movePendingFavIfNeeded();
}

    private int[] getVideoDimensions(String videoPath) {
        android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoPath);
            int width = Integer.parseInt(retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.parseInt(retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            return new int[]{width, height};
        } catch (Exception e) {
            Log.e(TAG, "Failed to get video dimensions: " + e.getMessage());
            return null;
        } finally {
            try {
                retriever.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing MediaMetadataRetriever: " + e.getMessage());
            }
        }
    }

    private boolean isMuted = true;

    private void toggleMute() {
        isMuted = !isMuted;
        if (exoPlayer != null) {
            exoPlayer.setVolume(isMuted ? 0f : 1f);
        }
        updateMuteIcon();
    }

    private void updateMuteIcon() {
        if (muteButton != null) {
            muteButton.setImageResource(isMuted ? R.drawable.ic_volume_off : R.drawable.ic_volume_up);
        }
    }
}