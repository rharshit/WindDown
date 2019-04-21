package com.rharshit.winddown.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppIcon extends LinearLayout {

    private static int width = 0;
    private final Context mContext;
    private final ImageView appIcon;
    private final String appName;
    private final TextView tvAppName;
    public TextView debug;

    public AppIcon(Context context, int w, int h, Drawable icon, String name, OnClickListener onClick) {
        super(context);
        mContext = context;
        width = w;

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));

        appIcon = new ImageView(mContext);
        appIcon.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, width));
        appIcon.setImageDrawable(icon);

        appName = name;
        tvAppName = new TextView(mContext);
        tvAppName.setText(appName);
        tvAppName.setTextSize(40.0f);
        tvAppName.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(appIcon);
        this.addView(tvAppName);
        this.setOnClickListener(onClick);

        debug = new TextView(mContext);
        this.addView(debug);
    }


    public void updateView() {
        int[] pos = new int[2];
        this.getLocationInWindow(pos);

        int x = pos[0];
        int mid = width / 2;
        x += mid;
        int d = mid - x;
        d = d < 0 ? -d : d;
        d = d > mid ? mid : d;
        float scale = (float) java.lang.Math.sqrt(1.0f - ((float) d / (float) width));
        scale += 0.05;
        scale = scale > 1 ? 1 : scale;

        appIcon.setScaleX(scale);
        appIcon.setScaleY(scale);
        tvAppName.setScaleX(scale);
        tvAppName.setScaleY(scale);
        appIcon.setAlpha(scale);
        tvAppName.setAlpha(scale);
    }
}
