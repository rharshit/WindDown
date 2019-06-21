package com.rharshit.winddown.UI;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.core.graphics.ColorUtils;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class GradientBackground extends GradientDrawable {

    private static final float GRADIENT_FACTOR = 0.35f;
    private int[] colors;
    private int theme;

    public GradientBackground() {
        theme = Theme.getTheme();

        colors = new int[2];
        colors[1] = theme == R.style.AppThemeDark
                ? Color.parseColor("#000000")
                : Color.parseColor("#FFFFFF");
        colors[0] = theme == R.style.AppThemeDark
                ? Color.parseColor("#111111")
                : Color.parseColor("#EEEEEE");

        this.setOrientation(Orientation.BL_TR);
        this.setColors(colors);
    }

    public void setColor(int color) {
        colors = new int[2];
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        float[] hsl = new float[3];
        ColorUtils.RGBToHSL(r, g, b, hsl);

        hsl[1] /= 2f;

        colors[0] = ColorUtils.HSLToColor(hsl);
        hsl[2] = theme == R.style.AppThemeDark
                ? max(0, hsl[2] - GRADIENT_FACTOR)
                : min(1, hsl[2] + GRADIENT_FACTOR);
        colors[1] = ColorUtils.HSLToColor(hsl);

        this.setColors(colors);
    }
}
