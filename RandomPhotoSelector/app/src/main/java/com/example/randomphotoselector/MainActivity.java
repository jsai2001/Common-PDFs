package com.example.randomphotoselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String[] REQUIRED_PERMISSIONS;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            REQUIRED_PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    // Add these variables to your existing declarations
    private TextView timerTextView;
    private TextView clockTextView;
    private long startTime;
    private Handler timerHandler = new Handler();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();

        // Check and request permissions
        checkAndRequestPermissions();

        // Set up double-tap gesture detector
        setupDoubleTapGesture();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDoubleTapGesture() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                Log.d("MainActivity", "Double tap detected");
                playNextRandomVideo();
                return true;
            }
    
            @Override
            public boolean onDown(MotionEvent e) {
                // Required to ensure other gesture events are processed
                return true;
            }
        });
    
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Return true to indicate the touch event was handled
                return gestureDetector.onTouchEvent(event);
            }
        });
    
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

    private void initializeAppAfterPermissions() {
        videoDirectoryPath = "/storage/emulated/0/Relaxation/Videos";
        photoDirectoryPath = "/storage/emulated/0/Relaxation/Photos";

        initializeSelectorsAndPaths(videoDirectoryPath, photoDirectoryPath);
    }

    private void initializeSelectorsAndPaths(String videoPath, String photoPath) {
        randomVideoSelector = new RandomVideoSelector(videoPath);
        randomPhotoSelector = new RandomPhotoSelector(photoPath);
        random = new Random();

        shuffledVideoPaths = randomVideoSelector.getVideoPaths();
        shuffledPhotoPaths = randomPhotoSelector.getImagePaths();

        Collections.shuffle(shuffledVideoPaths);
        Collections.shuffle(shuffledPhotoPaths);

        Log.d("MainActivity", "Number of photo paths: " + shuffledPhotoPaths.size());
        Log.d("MainActivity", "Number of video paths: " + shuffledVideoPaths.size());
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
        super.onDestroy();
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