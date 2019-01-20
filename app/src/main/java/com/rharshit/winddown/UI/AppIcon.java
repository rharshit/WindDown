package com.rharshit.winddown.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.Phone.Phone;
import com.rharshit.winddown.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class AppIcon extends LinearLayout {

    private final Context mContext;
    private final ImageView appIcon;
    private final String appName;
    private final TextView tvAppName;

    public AppIcon(Context context, int width, int height, Drawable icon, String name, OnClickListener onClick) {
        super(context);
        mContext = context;

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(width, width));

        appIcon = new ImageView(mContext);
        appIcon.setLayoutParams(new LinearLayoutCompat.LayoutParams(width/2, width/2));
        appIcon.setImageDrawable(icon);

        appName = name;
        tvAppName = new TextView(mContext);
        tvAppName.setText(appName);
        tvAppName.setTextSize(40.0f);

        this.addView(appIcon);
        this.addView(tvAppName);
        this.setOnClickListener(onClick);
    }
}
