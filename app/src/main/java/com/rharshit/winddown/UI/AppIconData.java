package com.rharshit.winddown.UI;

import android.graphics.drawable.Drawable;
import android.view.View;

public class AppIconData {

    Drawable icon;
    String name;
    View.OnClickListener onClick;

    public AppIconData(Drawable icon, String name, View.OnClickListener onClick) {
        this.icon = icon;
        this.name = name;
        this.onClick = onClick;
    }
}
