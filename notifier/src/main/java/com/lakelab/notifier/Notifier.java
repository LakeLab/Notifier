package com.lakelab.notifier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.lakelab.notifier.impl.BaseImpl;
import com.lakelab.notifier.model.ChannelCompat;
import com.lakelab.notifier.model.NotifierGroup;

import java.util.HashMap;

public final class Notifier {

    static final HashMap<String, NotifierGroup> groupInfo = new HashMap<>();
    static final HashMap<String, ChannelCompat> channels = new HashMap<>();


    private Notifier() {
    }

    public static void registerChannelDataForCompat(ChannelCompat channel) {
        channels.put(channel.getChannelId(), channel);
    }

    public static void deleteChannelDataForCompat(String channelId) {
        channels.remove(channelId);
    }


    public static void setContext(Context context) {
        ContextInjections.injectContext(context);
    }

    @RequiresApi(26)
    public static void registerChannel(NotificationChannel channel) {
        NotificationManager manager = ContextInjections.getApplicationContext()
                .getSystemService(NotificationManager.class);
        if (manager == null) {
            return;
        }
        manager.createNotificationChannel(channel);
    }

    @RequiresApi(26)
    private static void deleteChannel(String channelId) {
        NotificationManager manager = ContextInjections.getApplicationContext()
                .getSystemService(NotificationManager.class);
        if (manager == null) {
            return;
        }
        NotificationChannel notificationChannel
                = manager.getNotificationChannel(channelId);
        if (notificationChannel != null) {
            manager.deleteNotificationChannel(channelId);
        }
    }

    public static void setGroupNotification(int groupCount,
                                            int groupNotificationId,
                                            @NonNull Notification notification) {
        if (Build.VERSION.SDK_INT < 20 || notification.getGroup() == null) {
            return;
        }
        groupInfo.put(notification.getGroup(),
                new NotifierGroup(groupCount, groupNotificationId, notification));
    }

    public static BaseImpl with(@NonNull String channelId) {
        return new NotifierDataManager(channelId);
    }

}
