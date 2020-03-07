package com.lakelab.notifier.impl;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import android.widget.RemoteViews;

public interface RichImpl {

    OptionImpl setCustomBigContentView(@NonNull RemoteViews customBigContentView);

    OptionImpl setCustomBigContentView(@NonNull RemoteViews customBigContentView,
                                       boolean isDecoratedCustomStyle);

    OptionImpl setBigText(@NonNull CharSequence bigContentTitle,
                          @NonNull CharSequence summaryText,
                          @NonNull CharSequence bigText);

    OptionImpl setBigPicture(@NonNull CharSequence bigContentTitle,
                             @NonNull CharSequence summaryText,
                             @NonNull Bitmap bigPicture);

    OptionImpl setBigPicture(@NonNull CharSequence bigContentTitle,
                             @NonNull CharSequence summaryText,
                             @NonNull Bitmap bigPicture,
                             @NonNull Bitmap bigLargeIcon);

    OptionImpl setInbox(@NonNull CharSequence bigContentTitle,
                        @NonNull CharSequence summaryText,
                        CharSequence... lines);

}
