package com.lakelab.notifier.impl;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface CompatOptionImpl extends ShowImpl {
    int BADGE_ICON_NONE = 0;
    int BADGE_ICON_SMALL = 1;
    int BADGE_ICON_LARGE = 2;
    int GROUP_ALERT_ALL = 0;
    int GROUP_ALERT_CHILDREN = 1;
    int GROUP_ALERT_SUMMARY = 2;

    @IntDef(value = {
            BADGE_ICON_NONE,
            BADGE_ICON_SMALL,
            BADGE_ICON_LARGE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconType {
    }

    @IntDef(value = {
            GROUP_ALERT_ALL,
            GROUP_ALERT_CHILDREN,
            GROUP_ALERT_SUMMARY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    CompatOptionImpl setGroupFromAPI20(@NonNull String groupId);

    CompatOptionImpl setGroupSummaryFromAPI20(boolean isGroupSummary);

    CompatOptionImpl setGroupAlertBehaviorFromAPI26(@GroupAlertBehavior int groupAlertBehavior);

    CompatOptionImpl setColorizedFromAPI26(boolean colorize);

    CompatOptionImpl setBadgeIconTypeFromAPI26(@IconType int iconType);

    CompatOptionImpl setShowWhenFromAPI17(boolean showWhen);
}
