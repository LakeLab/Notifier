package com.lakelab.notifier.model;

import android.graphics.Bitmap;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotifierBase {
    @DrawableRes
    public final int smallIconResId;
    public final String title, text;
    @Nullable
    public final Bitmap largeIcon;


    public NotifierBase(@NonNull String title,
                        @Nullable String text,
                        @DrawableRes int smallIconResId) {
        this.title = title;
        this.text = text;
        this.smallIconResId = smallIconResId;
        this.largeIcon = null;

    }

    public NotifierBase(@NonNull String title,
                        @Nullable String text,
                        @DrawableRes int smallIconResId,
                        @NonNull Bitmap largeIcon) {
        this.title = title;
        this.text = text;
        this.smallIconResId = smallIconResId;
        this.largeIcon = largeIcon;
    }
}
