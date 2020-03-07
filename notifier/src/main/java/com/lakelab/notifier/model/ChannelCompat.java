package com.lakelab.notifier.model;

import android.Manifest;
import android.app.Notification;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ChannelCompat {

    private static final Uri DEFAULT_SOUND = Settings.System.DEFAULT_NOTIFICATION_URI;
    private static final long[] DEFAULT_VIBRATE_PATTERN = {0, 250, 250, 250};
    private static final int DEFAULT_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_ON_MS = 0;
    private static final int DEFAULT_OFF_MS = 500;

    @RequiresApi(21)
    @IntDef(value = {
            Notification.VISIBILITY_PUBLIC,
            Notification.VISIBILITY_PRIVATE,
            Notification.VISIBILITY_SECRET,
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface Visibility {
    }

    @RequiresApi(16)
    @IntDef(value = {
            Notification.PRIORITY_DEFAULT,
            Notification.PRIORITY_LOW,
            Notification.PRIORITY_MIN,
            Notification.PRIORITY_HIGH,
            Notification.PRIORITY_MAX
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }


    public ChannelCompat(String channelId) {
        this.channelId = channelId;
    }

    private final String channelId;

    private Uri sound = DEFAULT_SOUND;
    private long[] vibrationPattern = DEFAULT_VIBRATE_PATTERN;
    @Priority
    private int priority;
    private boolean enableVibration,
            enableSound,
            enableShowLights;
    @ColorInt
    private int lightColor = DEFAULT_COLOR;

    @RequiresApi(21)
    @Visibility
    private int lockScreenVisibility = Notification.VISIBILITY_PUBLIC;
    private int lightOnMs = DEFAULT_ON_MS;
    private int lightOffMs = DEFAULT_OFF_MS;

    public boolean isEnableShowLights() {
        return enableShowLights;
    }

    public ChannelCompat setEnableShowLights(boolean enableShowLights) {
        this.enableShowLights = enableShowLights;
        return this;
    }

    public int getLightColor() {
        return lightColor;
    }

    public ChannelCompat setLightColor(int lightColor) {
        this.lightColor = lightColor;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public Uri getSound() {
        return sound;
    }

    public ChannelCompat setSound(@NonNull Uri sound) {
        this.sound = sound;
        return this;
    }

    public long[] getVibrationPattern() {
        return vibrationPattern;
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public ChannelCompat setVibrationPattern(@NonNull long[] vibrationPattern) {
        this.vibrationPattern = vibrationPattern;
        return this;
    }

    @RequiresApi(16)
    @Priority
    public int getPriority() {
        return priority;
    }

    @RequiresApi(16)
    public ChannelCompat setPriority(@Priority int priority) {
        this.priority = priority;
        return this;
    }

    public boolean isEnableVibration() {
        return enableVibration;
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public ChannelCompat setEnableVibration(boolean enableVibration) {
        this.enableVibration = enableVibration;
        return this;
    }

    public boolean isEnableSound() {
        return enableSound;
    }

    public ChannelCompat setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
        return this;
    }

    @RequiresApi(21)
    public int getLockScreenVisibility() {
        return lockScreenVisibility;
    }

    @RequiresApi(21)
    public ChannelCompat setLockScreenVisibility(@Visibility int lockScreenVisibility) {
        this.lockScreenVisibility = lockScreenVisibility;
        return this;
    }

    public int getLightOnMs() {
        return lightOnMs;
    }

    public void setLightOnMs(int lightOnMs) {
        this.lightOnMs = lightOnMs;
    }

    public int getLightOffMs() {
        return lightOffMs;
    }

    public void setLightOffMs(int lightOffMs) {
        this.lightOffMs = lightOffMs;
    }
}
