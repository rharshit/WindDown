package com.rharshit.winddown.UI;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.Util.Notification;

public class NotificationView extends LinearLayout {

    private Notification notification;

    public NotificationView(Context context, Notification notification) {
        super(context);

        this.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOrientation(HORIZONTAL);
        this.notification = notification;

        TextView packageName = new TextView(context);
        packageName.setText(notification.getPackageName());
        packageName.setTextSize(16.0f);
        this.addView(packageName);
    }

    public String getGroupKey(){
        return notification.getGroupKey();
    }

    public String getKey(){
        return notification.getKey();
    }
}
