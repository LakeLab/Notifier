package com.lakelab.notifier.impl;

import android.app.Notification;

public interface ShowImpl {
    void show(int notificationId);

    Notification build();
}
