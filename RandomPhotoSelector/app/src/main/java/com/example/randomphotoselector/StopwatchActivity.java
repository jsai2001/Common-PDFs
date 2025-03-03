package com.example.randomphotoselector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StopwatchActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button controlButton;
    private Handler handler;
    private long startTime = 0L;
    private boolean isRunning = false;
    private GestureDetector gestureDetector;
    private boolean longPressDetected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        // Make the activity full screen
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        timerTextView = findViewById(R.id.timerTextView);
        controlButton = findViewById(R.id.controlButton);
        handler = new Handler();

        gestureDetector = new GestureDetector(this, new GestureListener());

        controlButton.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        controlButton.setOnClickListener(v -> {
            if (isRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(updateTimerThread, 0);
        isRunning = true;
        controlButton.setText("Pause");
    }

    private void pauseTimer() {
        handler.removeCallbacks(updateTimerThread);
        isRunning = false;
        controlButton.setText("Start");
    }

    private void resetTimer() {
        handler.removeCallbacks(updateTimerThread);
        startTime = 0L;
        timerTextView.setText("00:00:00");
        isRunning = false;
        controlButton.setText("Start");
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMillis = System.currentTimeMillis() - startTime;
            int secs = (int) (timeInMillis / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (timeInMillis % 1000);
            timerTextView.setText(String.format("%02d:%02d:%03d", mins, secs, milliseconds));
            handler.postDelayed(this, 0);
        }
    };

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            longPressDetected = true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (longPressDetected) {
                Intent intent = new Intent(StopwatchActivity.this, LoginActivity.class);
                startActivity(intent);
                longPressDetected = false;
            } else {
                resetTimer();
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
            return true;
        }
    }
}