package com.lakelab.notifier.model;

import android.app.Notification;
import androidx.annotation.NonNull;

public final class NotifierGroup {
    public final int groupCount;
    public final int groupNotificationId;
    public final Notification groupNotification;

    public NotifierGroup(int groupCount,
                         int groupNotificationId,
                         @NonNull Notification groupNotification) {
        this.groupNotificationId = groupNotificationId;
        this.groupCount = groupCount;
        this.groupNotification = groupNotification;
    }
}
