package com.example.randomphotoselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String[] REQUIRED_PERMISSIONS;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};
        } else {
            REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
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
    private List<String> shuffledVideoPaths = new ArrayList<>();
    private List<String> shuffledPhotoPaths = new ArrayList<>();
    private int currentVideoIndex = 0;
    private int currentPhotoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomImageView = findViewById(R.id.randomImageView);
        videoView = findViewById(R.id.videoView);
        Button videoButton = findViewById(R.id.videoButton);
        Button stopVideoButton = findViewById(R.id.stopVideoButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize RandomVideoSelector and RandomPhotoSelector with the directory paths
        String videoDirectoryPath = "/storage/emulated/0/Relaxation/Videos"; // Replace with your actual directory path
        String photoDirectoryPath = "/storage/emulated/0/Relaxation/Photos"; // Replace with your actual directory path
        randomVideoSelector = new RandomVideoSelector(videoDirectoryPath);
        randomPhotoSelector = new RandomPhotoSelector(photoDirectoryPath);
        random = new Random();

        // Shuffle the video and photo paths
        shuffledVideoPaths = randomVideoSelector.getVideoPaths();
        shuffledPhotoPaths = randomPhotoSelector.getImagePaths();
        Collections.shuffle(shuffledVideoPaths);
        Collections.shuffle(shuffledPhotoPaths);

        // Log the length of shuffled video paths
        Log.d("MainActivity", "Number of shuffled video paths: " + shuffledVideoPaths.size());
        
        // Log the shuffled video paths
        Log.d("MainActivity", "Shuffled Video Paths:");
        for (String path : shuffledVideoPaths) {
            Log.d("MainActivity", path);
        }
        
        // // Initialize RandomVideoSelector with the directory path
        videoButton.setOnClickListener(v -> playNextRandomVideo());
        stopVideoButton.setOnClickListener(v -> stopVideo());
        
        Button randomButton = findViewById(R.id.randomButton);
        Button autoRandomButton = findViewById(R.id.autoRandomButton);
        Button scrollButton = findViewById(R.id.scrollButton);
        

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
                setupRecyclerView(photoDirectoryPath, false); // Set up RecyclerView for photos
            }
        });

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        } else {
            // Permissions are already granted, proceed with the app logic
            initializeApp();
        }
    }

    private void playNextRandomVideo() {
        if (shuffledVideoPaths.isEmpty()) {
            Toast.makeText(this, "No videos found.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentVideoIndex >= shuffledVideoPaths.size()) {
            currentVideoIndex = 0; // Reset the index if it exceeds the list size
        }

        String videoPath = shuffledVideoPaths.get(currentVideoIndex);
        currentVideoIndex++;
        Log.d("MainActivity", "Playing video: " + videoPath);
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            Log.d("MainActivity", "Video prepared: " + videoPath);
            // Adjust the VideoView dimensions based on the video dimensions
            int videoWidth = mp.getVideoWidth();
            int videoHeight = mp.getVideoHeight();
            if (videoWidth > 0 && videoHeight > 0) {
                ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = (int) ((float) videoHeight / videoWidth * videoView.getWidth());
                videoView.setLayoutParams(layoutParams);
            }
            videoView.start();
        });
        videoView.setOnCompletionListener(mp -> {
            Log.d("MainActivity", "Video completed: " + videoPath);
            playNextRandomVideo();
        });
        videoView.setVisibility(View.VISIBLE);
        randomImageView.setVisibility(View.GONE);
    }

    private void displayNextRandomPhoto() {
        if (shuffledPhotoPaths.isEmpty()) {
            Toast.makeText(this, "No photos found.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPhotoIndex >= shuffledPhotoPaths.size()) {
            currentPhotoIndex = 0; // Reset the index if it exceeds the list size
        }

        String photoPath = shuffledPhotoPaths.get(currentPhotoIndex);
        currentPhotoIndex++;
        Log.d("MainActivity", "Displaying photo: " + photoPath);
        Uri photoUri = Uri.parse(photoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(photoUri.getPath());
        randomImageView.setImageBitmap(bitmap);
        randomImageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
    }

    private void stopVideo() {
        if (videoView.isPlaying()) {
            Log.d("MainActivity", "Stopping video playback");
            videoView.stopPlayback();
        }
        videoView.setVisibility(View.GONE);
        randomImageView.setVisibility(View.VISIBLE);
    }

    private void initializeApp() {
        // Initialize your app logic here
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                // Permissions granted, proceed with the app logic
                initializeApp();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setupRecyclerView(String directoryPath, boolean isVideo) {
        try {
            List<String> paths;
            if (isVideo) {
                RandomVideoSelector videoSelector = new RandomVideoSelector(directoryPath); // Specify your folder path here
                paths = videoSelector.getVideoPaths();
            } else {
                RandomPhotoSelector photoSelector = new RandomPhotoSelector(directoryPath); // Specify your folder path here
                paths = photoSelector.getImagePaths();
            }

            if (paths.isEmpty()) {
                Toast.makeText(this, "No files found in the specified folder.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Shuffle the paths to display them in random order
            Collections.shuffle(paths);

            RecyclerView.Adapter<?> adapter;
            if (isVideo) {
                adapter = new VideoAdapter(this, paths);
            } else {
                adapter = new ImageAdapter(this, paths);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            // Add scroll listener to handle video playback
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

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }
}