package com.rharshit.winddown.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rharshit.winddown.R;

public class AppIconViewHolder extends RecyclerView.ViewHolder {

    ImageView appIcon;
    TextView tvAppName;

    public AppIconViewHolder(@NonNull View itemView) {
        super(itemView);

        appIcon = itemView.findViewById(R.id.iv_app_icon);
        tvAppName = itemView.findViewById(R.id.tv_app_name);
    }
}
