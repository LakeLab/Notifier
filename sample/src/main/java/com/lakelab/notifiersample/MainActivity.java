package com.lakelab.notifiersample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.lakelab.notifier.Notifier;
import com.lakelab.notifier.model.ChannelCompat;
import com.lakelab.notifier.model.NotifierBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity {

    private static final String DEFAULT_CHANNEL_ID = "Sample channel id";
    private static final String DEFAULT_GROUP_ID = "Sample group id";
    private static final String DEFAULT_GROUP_ID_2 = "Sample group id_2";
    private static final String DEFAULT_CHANNEL_NAME = "Sample channel name";
    private static final String DEFAULT_CHANNEL_DESCRIPTION
            = "This channel is sample channel";

    private final ChannelCompat CHANNEL_COMPAT =
            new ChannelCompat(DEFAULT_CHANNEL_ID)
                    .setLightColor(Color.YELLOW)
                    .setEnableSound(true).setEnableVibration(true).setEnableShowLights(false)
                    .setVibrationPattern(new long[]{2000L, 1000L, 2000L, 1000L});

    private final NotifierBase NOTIFIER_BASE = new NotifierBase("Title", "Text"
            , R.drawable.ic_android_black_24dp);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    DEFAULT_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(DEFAULT_CHANNEL_DESCRIPTION);
            channel.setShowBadge(true);
            Notifier.registerChannel(channel);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            CHANNEL_COMPAT.setLockScreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            CHANNEL_COMPAT.setPriority(Notification.PRIORITY_MAX);
        }
        Notifier.registerChannelDataForCompat(CHANNEL_COMPAT);
        Notifier.setGroupNotification(2, 123,
                Notifier.with(DEFAULT_CHANNEL_ID)
                        .toNormal(NOTIFIER_BASE)
                        .toCompatOptions()
                        .setGroupFromAPI20(DEFAULT_GROUP_ID)
                        .setGroupSummaryFromAPI20(true)
                        .build());
        Notifier.setGroupNotification(2, 1234,
                Notifier.with(DEFAULT_CHANNEL_ID)
                        .toNormal(NOTIFIER_BASE)
                        .toCompatOptions()
                        .setGroupFromAPI20(DEFAULT_GROUP_ID_2)
                        .setGroupSummaryFromAPI20(true)
                        .build());

    }

    public void onNormalNotification(View v) {
        Notifier.with(DEFAULT_CHANNEL_ID).toNormal(NOTIFIER_BASE)
                .setClickAction(getPendingIntent())
                .toCompatOptions()
                .setGroupFromAPI20(DEFAULT_GROUP_ID_2)
                .show(1);
    }

    public void onCustomNotification(View v) {
        Notifier.with(DEFAULT_CHANNEL_ID)
                .toNormal(NOTIFIER_BASE, getCustomView("Title", "Text",
                        R.drawable.ic_android_black_24dp))
                .setClickAction(getPendingIntent())
                .toCompatOptions()
                .setGroupFromAPI20(DEFAULT_GROUP_ID_2)
                .show(2);
    }

    public void onBigPictureNotification(View v) {

        Notifier.with(DEFAULT_CHANNEL_ID)
                .toRich(NOTIFIER_BASE)
                .setBigPicture("Big Content Title",
                        "Summary Text",
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                .setClickAction(getPendingIntent())
                .toCompatOptions()
                .setGroupFromAPI20(DEFAULT_GROUP_ID)
                .show(3);
    }

    public void onBigPictureNotificationWithBigLargeIcon(View v) {

        Notifier.with(DEFAULT_CHANNEL_ID)
                .toRich(NOTIFIER_BASE)
                .setBigPicture("Big Content Title",
                        "Summary Text",
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher),
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                .setClickAction(getPendingIntent())
                .toCompatOptions()
                .setGroupFromAPI20(DEFAULT_GROUP_ID)
                .show(4);
    }

    public void onBigTextNotification(View v) {
        Notifier.with(DEFAULT_CHANNEL_ID)
                .toRich(NOTIFIER_BASE)
                .setBigText("Big Content Title",
                        "Summary Text",
                        "Bigggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg Text")
                .setClickAction(getPendingIntent())
                .setColor(Color.parseColor("#ff0000"))
                .show(5);
    }

    public void onInboxNotification(View v) {
        Notifier.with(DEFAULT_CHANNEL_ID)
                .toRich(NOTIFIER_BASE)
                .setInbox("Big Content Title",
                        "Summary Text",
                        "line 1", "line 2", "line 3", "line 4")
                .setClickAction(getPendingIntent())
                .show(6);
    }

    public final int BASE_PENDING_INTENT_REQUEST_CODE = 1004;
    public final String BASE_PENDING_INTENT_EXTRA_KEY = "Sample extra key";
    public final String BASE_PENDING_INTENT_EXTRA_VALUE = "Sample extra value";

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(BASE_PENDING_INTENT_EXTRA_KEY, BASE_PENDING_INTENT_EXTRA_VALUE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(MainActivity.this,
                BASE_PENDING_INTENT_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private RemoteViews getCustomView(String title, String content,
                                      int resId) {
        RemoteViews remoteViews = new RemoteViews(MainActivity.this.getPackageName(),
                R.layout.view_custom_notification);

        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.contents, content);
        remoteViews.setTextViewText(R.id.date,
                new SimpleDateFormat("YYYY/MM/dd", Locale.getDefault())
                        .format(Calendar.getInstance().getTime()));
        remoteViews.setImageViewResource(R.id.icon, resId);
        remoteViews.setTextColor(R.id.title, Color.BLUE);
        remoteViews.setTextColor(R.id.date, Color.BLUE);
        remoteViews.setTextColor(R.id.contents, Color.BLUE);
        return remoteViews;
    }


}
