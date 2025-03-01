package com.example.randomphotoselector;

import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class PanicButtonWidget extends AppWidgetProvider {
    private static final String ACTION_PANIC = "com.example.randomphotoselector.PANIC_ACTION";
    private static final String TAG = "PanicButtonWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Create panic button intent
            Intent intent = new Intent(context, PanicButtonWidget.class);
            intent.setAction(ACTION_PANIC);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Get the layout for the widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_panic_button);
            views.setOnClickPendingIntent(R.id.panicButton, pendingIntent);

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        if (ACTION_PANIC.equals(intent.getAction())) {
            Log.d(TAG, "Panic button activated");
            executePanic(context);
        }
    }

    private void executePanic(Context context) {
        try {
            // Get DevicePolicyManager
            DevicePolicyManager dpm = (DevicePolicyManager) 
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName adminComponent = new ComponentName(context, MyDeviceAdminReceiver.class);

            if (dpm.isAdminActive(adminComponent)) {
                // Clear app data first
                String packageName = context.getPackageName();
                Process process = Runtime.getRuntime().exec("pm clear " + packageName);
                process.waitFor();
                Log.d(TAG, "App data cleared");

                // Then trigger uninstall
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE)
                    .setData(Uri.parse("package:" + packageName))
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(uninstallIntent);
            } else {
                Toast.makeText(context, "Admin permission required", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error during panic execution", e);
        }
    }
}