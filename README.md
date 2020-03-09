# Notifier

Notifier is an easy and fast open source notification library for android!

- No android support library for notifcation.
- Easy to set custom notification.
- No initialize code (Don't need to set context for notification).
- Decreased separation code for covering all API Version.

## Download

You can use Gradle :
```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.lakelab:notifier:0.0.1'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.lakelab</groupId>
  <artifactId>notifier</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```

## How do i use Notifier?

### Basic usage

```java
Notifier.with(DEFAULT_CHANNEL_ID)
        .toNormal(new NotifierBase("title", "text", R.mipmap.ic_launcher))
        .setClickAction(getPendingIntent())
        .show(NOTIFICATION_ID);
```

### Channel

**You need to add a notification channel from API 26.**

So You can use this library for registering or creating the notification channel as follows.

```java

if (Build.VERSION.SDK_INT >= 26) {
    NotificationChannel channel = new NotificationChannel(
            DEFAULT_CHANNEL_ID,
            DEFAULT_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH);
    channel.setDescription(DEFAULT_CHANNEL_DESCRIPTION);
    channel.setShowBadge(true);
    Notifier.registerChannel(channel);
}
```

Additional data can be set for compatibility in API 26 and below.

```java
private final ChannelCompat CHANNEL_COMPAT =
        new ChannelCompat(DEFAULT_CHANNEL_ID)
                .setLightColor(Color.YELLOW)
                .setEnableSound(true).setEnableVibration(true).setEnableShowLights(false)
                .setVibrationPattern(new long[]{2000L, 1000L, 2000L, 1000L});
                
if (Build.VERSION.SDK_INT >= 21) {
    CHANNEL_COMPAT.setLockScreenVisibility(Notification.VISIBILITY_PUBLIC);
}
if (Build.VERSION.SDK_INT >= 16) {
    CHANNEL_COMPAT.setPriority(Notification.PRIORITY_MAX);
}
Notifier.registerChannelDataForCompat(CHANNEL_COMPAT);
```

### Style
Supported style : BigPicture, BigText, Inbox

```java
//Big picture notification
Notifier.with(DEFAULT_CHANNEL_ID)
        .toRich(new NotifierBase("title", "text", R.mipmap.ic_launcher))
        .setBigPicture("Big Content Title",
                        "Summary Text",
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher),
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
        .setClickAction(getPendingIntent())
        .show(NOTIFICATION_ID);
        
// Big text notification
Notifier.with(DEFAULT_CHANNEL_ID)
        .toRich(new NotifierBase("title", "text", R.mipmap.ic_launcher))
        .setBigText("Big Content Title",
                    "Summary Text",
                    "Biggggggggggggggggggggggggggggggggggggggggggg Text")
        .setClickAction(getPendingIntent())
        .setColor(Color.parseColor("#ff0000"))
        .show(NOTIFICATION_ID);

// Inbox notification
Notifier.with(DEFAULT_CHANNEL_ID)
        .toRich(new NotifierBase("title", "text", R.mipmap.ic_launcher))
        .setInbox("Big Content Title",
                "Summary Text",
                "line 1", "line 2", "line 3", "line 4")
        .setClickAction(getPendingIntent())
        .show(NOTIFICATION_ID);
```


## To do

-[ ] Message style notification.
-[ ] Notification Action.
-[ ] Document for additional options.
