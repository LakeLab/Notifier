package com.lakelab.notifier.impl;

import androidx.annotation.NonNull;
import android.widget.RemoteViews;

import com.lakelab.notifier.model.NotifierBase;

public interface BaseImpl {
    OptionImpl toNormal(@NonNull NotifierBase baseData);

    OptionImpl toNormal(@NonNull NotifierBase baseData,
                        @NonNull RemoteViews customView);

    RichImpl toRich(@NonNull NotifierBase baseData);

    RichImpl toRich(@NonNull NotifierBase baseData,
                    @NonNull RemoteViews customView);
}
