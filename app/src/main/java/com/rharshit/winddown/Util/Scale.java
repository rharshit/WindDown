package com.rharshit.winddown.Util;

import android.graphics.Bitmap;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class Scale {
    public static double getScale(int width, int height, int desiredWidth, int desiredHeight) {
        double scale = Math.min((double) desiredWidth / width, (double) desiredHeight / height);
        Log.d(TAG, "getScale: " + scale);
        return scale;
    }

    public static Bitmap scaleBitmap(Bitmap src, int desiredWidth, int desiredHeight) {
        int width = src.getWidth();
        int height = src.getHeight();

        double scale = getScale(width, height, desiredWidth, desiredHeight);

        return Bitmap.createScaledBitmap(src, (int) (width * scale),
                (int) (height * scale), false);
    }

}
