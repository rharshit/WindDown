package com.rharshit.winddown.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.palette.graphics.Palette;

import com.rharshit.winddown.R;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Color {

    public static int getColor(Drawable img, int dimen) {
        return getColor(convertToBitmap(img, dimen));
    }

    public static int getColor(Bitmap bmp) {
        Palette palette = createPaletteSync(bmp);
        Palette.Swatch swatch;
        int theme = Theme.getTheme();
        switch (theme) {
            case R.style.AppThemeDark:
                swatch = palette.getDarkVibrantSwatch();
                if (swatch == null) {
                    swatch = palette.getDarkMutedSwatch();
                }
                break;
            case R.style.AppThemeLight:
                swatch = palette.getLightVibrantSwatch();
                if (swatch == null) {
                    swatch = palette.getLightMutedSwatch();
                }
                break;
            default:
                swatch = palette.getVibrantSwatch();
                if (swatch == null) {
                    swatch = palette.getMutedSwatch();
                }
                break;
        }
        if (swatch != null) {
            float[] hsl = swatch.getHsl();
            hsl[2] = (theme == R.style.AppThemeDark
                    ? max(0.75f, hsl[2])
                    : min(0.75f, hsl[2]));
            return android.graphics.Color.HSVToColor(hsl);
        } else {
            switch (theme) {
                case R.style.AppThemeDark:
                    swatch = palette.getLightVibrantSwatch();
                    break;
                case R.style.AppThemeLight:
                    swatch = palette.getDarkVibrantSwatch();
                    break;
            }
        }
        if (swatch != null) {
            float[] hsl = swatch.getHsl();
            hsl[2] = 1 - hsl[2];
            hsl[2] = (theme == R.style.AppThemeDark
                    ? max(0.75f, hsl[2])
                    : min(0.75f, hsl[2]));
            return android.graphics.Color.HSVToColor(hsl);
        }
        swatch = palette.getDominantSwatch();
        if (swatch != null) {
            float[] hsl = swatch.getHsl();
            hsl[2] = 1 - hsl[2];
            hsl[2] = (theme == R.style.AppThemeDark
                    ? max(0.75f, hsl[2])
                    : min(0.75f, hsl[2]));
            return android.graphics.Color.HSVToColor(hsl);
        }
        return android.graphics.Color.parseColor("#808080");
    }

    private static Bitmap convertToBitmap(Drawable drawable, int dimen) {
        Bitmap mutableBitmap = Bitmap.createBitmap(dimen, dimen, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, dimen, dimen);
        drawable.draw(canvas);


        return mutableBitmap;
    }

    private static Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }
}
