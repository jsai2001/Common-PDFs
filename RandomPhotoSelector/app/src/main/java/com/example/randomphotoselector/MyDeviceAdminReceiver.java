package com.example.randomphotoselector;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Device Admin Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d("MyDeviceAdminReceiver", "Device admin disabled");
        // Perform necessary actions, such as deleting app data
        deleteAppData(context);
    }

    private void deleteAppData(Context context) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear " + context.getPackageName());
            Log.d("MyDeviceAdminReceiver", "App data deleted successfully");
        } catch (Exception e) {
            Log.e("MyDeviceAdminReceiver", "Failed to delete app data", e);
        }
    }
}