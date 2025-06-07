package com.example.randomphotoselector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StopwatchActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button controlButton;
    private Handler handler;
    private long startTime = 0L;
    private boolean isRunning = false;
    private GestureDetector gestureDetector;
    private boolean longPressDetected = false;
    private int longPressCount = 0; // Tracks the number of consecutive long presses
    private static final int REQUIRED_LONG_PRESSES = 10; // Number of long presses required to open the activity
    private static final long LONG_PRESS_TIMEOUT = 2000; // Timeout to reset the count (in milliseconds)
    private Handler longPressHandler = new Handler();
    private Handler resetHandler = new Handler(); // Handler to reset the long press count after timeout

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

        // Set up long press detection
        controlButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    longPressHandler.postDelayed(() -> {
                        longPressCount++;
                        // Toast.makeText(StopwatchActivity.this, "Long press detected: " + longPressCount, Toast.LENGTH_SHORT).show();

                        if (longPressCount == REQUIRED_LONG_PRESSES) {
                            Toast.makeText(StopwatchActivity.this, "Opening LoginActivity", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StopwatchActivity.this, LoginActivity.class);
                            startActivity(intent);
                            resetLongPressCount();
                        }
                    }, ViewConfiguration.getLongPressTimeout());
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    longPressHandler.removeCallbacksAndMessages(null); // Cancel long press detection if the button is released
                }
                if (isRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                return true;
            }
        });
    }

    private void resetLongPressCount() {
        longPressCount = 0; // Reset the long press count
        Toast.makeText(this, "Long press count reset", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
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
        timerTextView.setText("00:00");
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
            timerTextView.setText(String.format("%02d:%02d", mins, secs));
            handler.postDelayed(this, 0);
        }
    };
}