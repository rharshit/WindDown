package com.rharshit.winddown.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Notification;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class NotificationView extends LinearLayout {

    private Notification notification;

    private HashMap<String, ArrayList<String>> groupNotifications;

    public NotificationView(Context context, Notification notification, Drawable icon,
                            String appName, String ticker){
        this(context, notification, icon, appName);
        groupNotifications = new HashMap<>();
        addToHashMap(notification.getKey(), ticker);
    }

    public NotificationView(Context context, Notification notification, Drawable icon,
                            String appName) {
        super(context);

        this.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOrientation(VERTICAL);
        this.notification = notification;

        float dimen = getResources().getDimension(R.dimen.notification_icon_dimen);
        float pad = getResources().getDimension(R.dimen.notification_icon_padding);

        ImageView ivIcon = new ImageView(context);
        ivIcon.setLayoutParams(new LayoutParams((int) dimen, (int) dimen));
        ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivIcon.setPadding((int) pad, (int) pad, (int) pad, (int) pad);
        if(icon!=null){
            ivIcon.setImageDrawable(icon);
        } else {
            Log.e(TAG, "NotificationView: NULL Icon");
        }
        this.addView(ivIcon);

        TextView packageName = new TextView(context);
        packageName.setText(appName);
        packageName.setGravity(Gravity.CENTER_HORIZONTAL);
        packageName.setPadding(0, 0, 0, (int) pad);
        packageName.setTextSize(12.0f);
        this.addView(packageName);
    }

    private void addToHashMap(String key, String ticker) {
        ArrayList<String> notifs = groupNotifications.get(key);
        if(notifs == null){
            notifs = new ArrayList<>();
        }
        notifs.add(ticker);
        groupNotifications.put(key, notifs);
    }

    public String getGroupKey(){
        return notification.getGroupKey();
    }

    public String getKey(){
        return notification.getKey();
    }

    public String getPackageName(){
        return notification.getPackageName();
    }

    public void updateNotification(Notification n, String ticker) {
        addToHashMap(n.getKey(), ticker);
    }

    public ArrayList<String> getNotifications() {
        ArrayList<String> notifications = new ArrayList<>();
        for(String key : groupNotifications.keySet()){
            for(String ticker : groupNotifications.get(key)){
                Log.d(TAG, "getNotifications: Key: " + key + " Ticker: " + ticker);
                notifications.add(ticker);
            }
        }
        return notifications;
    }
}
