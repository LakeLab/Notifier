package com.lakelab.notifier.impl;

import android.app.Notification;
import android.app.PendingIntent;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.widget.RemoteViews;

public interface OptionImpl extends ShowImpl {

    CompatOptionImpl toCompatOptions();

    OptionImpl setWhen(long whenTimeMillis);

    OptionImpl setAutoCancel(boolean autoCancel);

    OptionImpl setClickAction(@NonNull PendingIntent clickAction);

    OptionImpl setOnlyAlertOnce(boolean isOnlyAlertOnce);

    OptionImpl setCustomHeadsUpView(@NonNull RemoteViews bigCustomHeadUpView);

    OptionImpl setColor(@ColorInt int color);

    OptionImpl setPublicVersion(@NonNull Notification notification);

}
