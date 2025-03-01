package com.example.randomphotoselector;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String CORRECT_PASSWORD = "Kz9$mPq!vR2x";
    private int attemptCount = 0;
    private static final int MAX_ATTEMPTS = 3;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private static final int ADMIN_REQUEST = 1;

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
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
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
}