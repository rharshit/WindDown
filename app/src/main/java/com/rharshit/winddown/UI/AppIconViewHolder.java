package com.rharshit.winddown.UI;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rharshit.winddown.R;

public class AppIconViewHolder extends RecyclerView.ViewHolder {

    ImageView appIcon;
    TextView tvAppName;

    public AppIconViewHolder(@NonNull View itemView) {
        super(itemView);

        appIcon = itemView.findViewById(R.id.iv_app_icon);
        tvAppName = itemView.findViewById(R.id.tv_app_name);
    }

    public void updateView() {
        int[] pos = new int[2];
        appIcon.getLocationInWindow(pos);

        int width = appIcon.getWidth();
        int x = pos[0];
        int mid = width / 2;
        x += mid;
        int d = mid - x;
        d = d < 0 ? -d : d;
        d = d > mid ? mid : d;
        float scale = (float) java.lang.Math.sqrt(1.0f - ((float) d / (float) width));
        scale += 0.05;
        scale = scale > 1 ? 1 : scale;

        if (Float.isNaN(scale)) {
            scale = 1;
        }

        appIcon.setScaleX(scale);
        appIcon.setScaleY(scale);
        tvAppName.setScaleX(scale);
        tvAppName.setScaleY(scale);
        appIcon.setAlpha(scale);
        tvAppName.setAlpha(scale);
    }
}
