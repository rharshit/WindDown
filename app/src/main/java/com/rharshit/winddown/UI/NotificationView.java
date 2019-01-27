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

import static android.content.ContentValues.TAG;

public class NotificationView extends LinearLayout {

    private Notification notification;

    public NotificationView(Context context, Notification notification, Drawable icon, String appName) {
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

    public String getGroupKey(){
        return notification.getGroupKey();
    }

    public String getKey(){
        return notification.getKey();
    }
}
