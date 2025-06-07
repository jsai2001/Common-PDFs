package com.example.randomphotoselector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SHUTDOWN.equals(action) || Intent.ACTION_REBOOT.equals(action) || Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.d("DeviceEventReceiver", "Device event received: " + action);
            // Perform necessary actions, such as deleting app data
            deleteAppData(context);
        }
    }

    private void deleteAppData(Context context) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear " + context.getPackageName());
            Log.d("DeviceEventReceiver", "App data deleted successfully");
        } catch (Exception e) {
            Log.e("DeviceEventReceiver", "Failed to delete app data", e);
        }
    }
}