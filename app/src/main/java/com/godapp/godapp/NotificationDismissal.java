package com.godapp.godapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationDismissal extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ALARM RECEIVE", "OnReceive STOP");
        NotificationUtils notificationUtils = new NotificationUtils(context);
        notificationUtils.stopAlarm();
        NotificationManager notificationManager = notificationUtils.getManager();
        notificationManager.cancelAll();
    }
}
