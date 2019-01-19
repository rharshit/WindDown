package com.rharshit.winddown.UI;

import android.content.Context;
import android.content.Intent;
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
    private ImageView appIcon;
    private String appName;

    public AppIcon(Context context, int width, int height, OnClickListener onClick) {
        super(context);
        mContext = context;

        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(width, width));

        TextView tmp1 = new TextView(mContext);
        tmp1.setText("Hello");
        tmp1.setTextSize(40.0f);

        TextView tmp2 = new TextView(mContext);
        tmp2.setText("World");
        tmp2.setTextSize(40.0f);

        this.addView(tmp1);
        this.addView(tmp2);

        tmp1.setOnClickListener(onClick);
    }
}
