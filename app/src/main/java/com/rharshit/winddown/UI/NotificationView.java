package com.rharshit.winddown.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Notification;

import static android.content.ContentValues.TAG;

public class NotificationView extends LinearLayout {

    private Notification notification;

    public NotificationView(Context context, Notification notification, Drawable icon) {
        super(context);

        this.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOrientation(HORIZONTAL);
        this.notification = notification;

        float dimen = getResources().getDimension(R.dimen.notification_icon_dimen);

        ImageView ivIcon = new ImageView(context);
        ivIcon.setLayoutParams(new LayoutParams((int) dimen, (int) dimen));
        ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivIcon.setPadding(8,8,8,8);
        if(icon!=null){
            ivIcon.setImageDrawable(icon);
        } else {
            Log.e(TAG, "NotificationView: NULL Icon");
        }
        this.addView(ivIcon);

//        TextView packageName = new TextView(context);
//        packageName.setText(notification.getPackageName());
//        packageName.setTextSize(16.0f);
//        this.addView(packageName);
    }

    public String getGroupKey(){
        return notification.getGroupKey();
    }

    public String getKey(){
        return notification.getKey();
    }
}
