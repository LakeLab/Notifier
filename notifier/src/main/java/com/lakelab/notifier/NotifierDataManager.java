package com.lakelab.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.lakelab.notifier.impl.BaseImpl;
import com.lakelab.notifier.impl.CompatOptionImpl;
import com.lakelab.notifier.impl.OptionImpl;
import com.lakelab.notifier.impl.RichImpl;
import com.lakelab.notifier.model.ChannelCompat;
import com.lakelab.notifier.model.NotifierBase;
import com.lakelab.notifier.model.NotifierGroup;

import java.util.ArrayList;
import java.util.List;

class NotifierDataManager implements BaseImpl, RichImpl, OptionImpl, CompatOptionImpl {

    @Nullable
    private String groupId;

    private final Notification.Builder builder;
    private final NotificationManager manager;

    NotifierDataManager(String channelId) {
        Context context = ContextInjections.getApplicationContext();
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            this.builder = new Notification.Builder(context, channelId);
        } else {
            this.builder = new Notification.Builder(context);
        }
        ChannelCompat channelCompat = Notifier.channels.get(channelId);
        if (channelCompat != null) {
            setChannelDataForCompat(channelCompat);
        }
    }

    @Override
    public CompatOptionImpl toCompatOptions() {
        return this;
    }


    @Override
    public CompatOptionImpl setGroupFromAPI20(@NonNull String groupId) {
        this.groupId = groupId;
        if (Build.VERSION.SDK_INT >= 20) {
            builder.setGroup(groupId);
        }
        return this;
    }

    @Override
    public CompatOptionImpl setGroupAlertBehaviorFromAPI26(
            @GroupAlertBehavior int groupAlertBehavior) {
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setGroupAlertBehavior(groupAlertBehavior);
        }
        return this;
    }

    @Override
    public CompatOptionImpl setGroupSummaryFromAPI20(boolean isGroupSummary) {
        if (Build.VERSION.SDK_INT >= 20) {
            builder.setGroupSummary(isGroupSummary);
        }
        return this;
    }

    @Override
    public CompatOptionImpl setShowWhenFromAPI17(boolean showWhen) {
        if (Build.VERSION.SDK_INT >= 17) {
            builder.setShowWhen(showWhen);
        }
        return this;
    }

    @Override
    public CompatOptionImpl setColorizedFromAPI26(boolean colorize) {
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setColorized(colorize);
        }
        return this;
    }

    @Override
    public CompatOptionImpl setBadgeIconTypeFromAPI26(@IconType int iconType) {
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setBadgeIconType(iconType);
        }
        return this;
    }

    @Override
    public void show(int notificationId) {
        NotifierGroup groupData =
                this.groupId != null ? Notifier.groupInfo.get(this.groupId) : null;

        boolean needGroupNotification = groupData != null
                && Build.VERSION.SDK_INT >= 24
                && needGroup(manager.getActiveNotifications(), groupData);

        manager.notify(notificationId, build(builder));

        if (needGroupNotification) {
            manager.notify(groupData.groupNotificationId,
                    groupData.groupNotification);
        }
    }

    @Override
    public RichImpl toRich(@NonNull NotifierBase baseData) {
        setBase(baseData);
        return this;
    }

    @Override
    public RichImpl toRich(@NonNull NotifierBase baseData,
                           @NonNull RemoteViews customView) {
        setBase(baseData);
        setCustom(customView);
        return this;
    }

    @Override
    public OptionImpl toNormal(@NonNull NotifierBase baseData) {
        setBase(baseData);
        return this;
    }

    @Override
    public OptionImpl toNormal(@NonNull NotifierBase baseData,
                               @NonNull RemoteViews customView) {
        setBase(baseData);
        setCustom(customView);
        return this;
    }

    @Override
    @RequiresApi(21)
    public OptionImpl setColor(@ColorInt int color) {
        builder.setColor(color);
        return this;
    }

    @Override
    @RequiresApi(24)
    public OptionImpl setCustomBigContentView(@NonNull RemoteViews customBigContentView) {
        return setCustomBigContentView(customBigContentView, false);
    }

    @Override
    @RequiresApi(24)
    public OptionImpl setCustomBigContentView(@NonNull RemoteViews customBigContentView,
                                              boolean isDecoratedCustomStyle) {
        builder.setCustomBigContentView(customBigContentView);
        if (isDecoratedCustomStyle) {
            builder.setStyle(new Notification.DecoratedCustomViewStyle());
        }
        return this;
    }

    @Override
    @RequiresApi(24)
    public OptionImpl setCustomHeadsUpView(@NonNull RemoteViews customHeadUpView) {
        builder.setCustomHeadsUpContentView(customHeadUpView);
        return this;
    }

    @Override
    public OptionImpl setClickAction(@NonNull PendingIntent clickAction) {
        builder.setContentIntent(clickAction);
        return this;
    }


    @Override
    public OptionImpl setWhen(long whenTimeMillis) {
        builder.setWhen(whenTimeMillis);
        return this;
    }

    @Override
    public OptionImpl setAutoCancel(boolean autoCancel) {
        builder.setAutoCancel(autoCancel);
        return this;
    }


    @Override
    public OptionImpl setOnlyAlertOnce(boolean isOnlyAlertOnce) {
        builder.setOnlyAlertOnce(isOnlyAlertOnce);
        return this;
    }

    @Override
    @RequiresApi(21)
    public OptionImpl setPublicVersion(@NonNull Notification notification) {
        builder.setPublicVersion(notification);
        return this;
    }

    @Override
    @RequiresApi(16)
    public OptionImpl setBigText(@NonNull CharSequence bigContentTitle,
                                 @NonNull CharSequence summaryText,
                                 @NonNull CharSequence bigText) {
        Notification.BigTextStyle style = new Notification.BigTextStyle();
        style.setBigContentTitle(bigContentTitle);
        style.setSummaryText(summaryText);
        style.bigText(bigText);
        builder.setStyle(style);
        return this;
    }


    @Override
    @RequiresApi(16)
    public OptionImpl setBigPicture(
            @NonNull CharSequence bigContentTitle,
            @NonNull CharSequence summaryText,
            @NonNull Bitmap bigPicture) {
        return bigPicture(bigContentTitle, summaryText, bigPicture, null);
    }

    @Override
    @RequiresApi(16)
    public OptionImpl setBigPicture(
            @NonNull CharSequence bigContentTitle,
            @NonNull CharSequence summaryText,
            @NonNull Bitmap bigPicture,
            @NonNull Bitmap bigLargeIcon) {
        return bigPicture(bigContentTitle, summaryText, bigPicture, bigLargeIcon);
    }


    @Override
    @RequiresApi(16)
    public OptionImpl setInbox(
            @NonNull CharSequence bigContentTitle,
            @NonNull CharSequence summaryText,
            CharSequence... lines) {
        Notification.InboxStyle style = new Notification.InboxStyle();
        style.setBigContentTitle(bigContentTitle);
        style.setSummaryText(summaryText);
        for (CharSequence line : lines) {
            style.addLine(line);
        }
        builder.setStyle(style);
        return this;
    }

    @Override
    public Notification build() {
        return build(builder);
    }


    @RequiresApi(24)
    private boolean needGroup(StatusBarNotification[] notifications,
                              @NonNull NotifierGroup groupData) {
        return getNotifications(notifications,
                groupData.groupNotification.getGroup()).length >= groupData.groupCount - 1;
    }

    private void setChannelDataForCompat(ChannelCompat channel) {

        if (Build.VERSION.SDK_INT < 26) {
            if (Build.VERSION.SDK_INT >= 16) {
                builder.setPriority(channel.getPriority());
            }
            if (channel.isEnableShowLights()) {
                builder.setLights(channel.getLightColor(),
                        channel.getLightOnMs(),
                        channel.getLightOffMs());
            }
            if (channel.isEnableSound()) {
                builder.setSound(channel.getSound());
            }
            if (channel.isEnableVibration()) {
                builder.setVibrate(channel.getVibrationPattern());
            }
            if (Build.VERSION.SDK_INT >= 21) {
                builder.setVisibility(channel.getLockScreenVisibility());
            }
        }

    }

    private Notification build(Notification.Builder builder) {
        if (Build.VERSION.SDK_INT >= 16) {
            return builder.build();
        } else {
            return builder.getNotification();
        }
    }

    private void setBase(@NonNull NotifierBase notifierBase) {
        builder.setContentTitle(notifierBase.title);
        builder.setContentText(notifierBase.text);
        builder.setSmallIcon(notifierBase.smallIconResId);
        if (notifierBase.largeIcon != null) {
            builder.setLargeIcon(notifierBase.largeIcon);
        }
    }

    private void setCustom(@NonNull RemoteViews remoteViews) {
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setCustomContentView(remoteViews);
        } else {
            builder.setContent(remoteViews);
        }
    }

    @RequiresApi(24)
    private StatusBarNotification[] getNotifications(StatusBarNotification[] notifications,
                                                     String groupId) {
        if (notifications == null || notifications.length <= 0) {
            return notifications;
        }
        Context context = ContextInjections.getApplicationContext();
        List<StatusBarNotification> notificationList = new ArrayList<>();
        for (StatusBarNotification notification : notifications) {
            if (TextUtils.equals(notification.getNotification().getGroup(), groupId)
                    && TextUtils.equals(notification.getPackageName(), context.getPackageName())) {
                notificationList.add(notification);
            }
        }
        return notificationList.toArray(new StatusBarNotification[0]);
    }

    @RequiresApi(16)
    private OptionImpl bigPicture(
            @NonNull CharSequence bigContentTitle,
            @NonNull CharSequence summaryText,
            @NonNull Bitmap bigPicture,
            @Nullable Bitmap bigLargeIcon) {
        Notification.BigPictureStyle style = new Notification.BigPictureStyle();
        style.setBigContentTitle(bigContentTitle);
        style.setSummaryText(summaryText);
        style.bigPicture(bigPicture);
        if (bigLargeIcon != null) {
            style.bigLargeIcon(bigLargeIcon);
        }
        builder.setStyle(style);
        return this;
    }
}
