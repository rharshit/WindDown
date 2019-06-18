package com.rharshit.winddown.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Blur;
import com.rharshit.winddown.Util.Notification;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class NotificationView extends LinearLayout {

    private final String appName;
    private final Drawable icon;
    float dimen = getResources().getDimension(R.dimen.notification_icon_dimen);
    float dimenBlur = getResources().getDimension(R.dimen.notification_icon_blur_dimen);
    float dimenDiff = dimenBlur - dimen;
    float radius = getResources().getInteger(R.integer.notification_icon_blur_radius);
    float pad = getResources().getDimension(R.dimen.notification_icon_padding);
    private TextView packageName;
    private Notification notification;
    private HashMap<String, ArrayList<String>> groupNotifications;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationView(Context context, Notification notification, Drawable icon,
                            String appName, String ticker) {
        this(context, notification, icon, appName);
        groupNotifications = new HashMap<>();
        addToHashMap(notification.getKey(), ticker, notification.isOngoing());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationView(Context context, Notification notification, Drawable icon,
                            String appName) {
        super(context);

        this.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setClipChildren(false);
        this.setClipToOutline(false);
        this.setClipToPadding(false);
        this.setOrientation(VERTICAL);
        this.notification = notification;

        this.appName = appName;
        this.icon = icon;

        RelativeLayout rvIcon = new RelativeLayout(context);
        rvIcon.setLayoutParams(new LayoutParams((int) (dimenBlur + 2 * radius),
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView ivIcon = new ImageView(context);
        ImageView ivIconBlur = new ImageView(context);

        ivIcon.setLayoutParams(new LayoutParams((int) (dimenBlur + 2 * radius), (int) dimenBlur));
        ivIconBlur.setLayoutParams(new LayoutParams((int) (dimenBlur + 2 * radius),
                (int) (dimenBlur + 2 * radius)));

        if (icon != null) {
            ivIcon.setImageDrawable(icon);
            ivIconBlur.setImageBitmap(Blur.transform(context, icon, radius, dimen, dimenBlur));
        } else {
            Log.e(TAG, "NotificationView: NULL Icon");
        }

        ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivIconBlur.setScaleType(ImageView.ScaleType.CENTER);
        ivIcon.setPadding((int) (pad + dimenDiff / 2), 0, (int) (pad + dimenDiff / 2), 0);
        ivIconBlur.setPadding(0, 0, 0, 0);

        rvIcon.addView(ivIconBlur);
        rvIcon.addView(ivIcon);

        this.addView(rvIcon);

        Typeface typeface = getResources().getFont(R.font.light);
        packageName = new TextView(context);
        packageName.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        packageName.setText(appName);
        packageName.setGravity(Gravity.CENTER_HORIZONTAL);
        packageName.setPadding(0, 0, 0, 0);
        packageName.setTextSize(16.0f);
        packageName.setTypeface(typeface);
        packageName.setLines(2);
        packageName.setTextColor(com.rharshit.winddown.Util.Color.getColor(icon, (int) dimen));
        packageName.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.addView(packageName);
        packageName.setTranslationY(-2 * radius);
    }

    private void addToHashMap(String key, String ticker, boolean ongoing) {
        ArrayList<String> notifs = groupNotifications.get(key);
        if (notifs == null) {
            notifs = new ArrayList<>();
        }
        if (ongoing && notifs.size() > 0) {
            notifs.set(0, ticker);
        } else {
            notifs.add(ticker);
        }
        groupNotifications.put(key, notifs);
    }

    private boolean removeFromHashMap(String key, String ticker, boolean ongoing) {
        ArrayList<String> notifs = groupNotifications.get(key);
        groupNotifications.remove(key);
        return groupNotifications.isEmpty();
    }

    public String getGroupKey() {
        return notification.getGroupKey();
    }

    public String getKey() {
        return notification.getKey();
    }

    public String getPackageName() {
        return notification.getPackageName();
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void updateNotification(Notification n, String ticker) {
        addToHashMap(n.getKey(), ticker, n.isOngoing());
    }

    public boolean removeNotification(Notification n, String ticker) {
        return removeFromHashMap(n.getKey(), ticker, n.isOngoing());
    }

    public ArrayList<String> getNotifications() {
        ArrayList<String> notifications = new ArrayList<>();
        for (String key : groupNotifications.keySet()) {
            for (String ticker : groupNotifications.get(key)) {
                Log.d(TAG, "getNotifications: Key: " + key + " Ticker: " + ticker);
                notifications.add(ticker);
            }
        }
        return notifications;
    }

    public void updateColor() {
        packageName.setTextColor(com.rharshit.winddown.Util.Color.getColor(icon, (int) dimen));
    }
}
