package com.rharshit.winddown.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class Blur {
    private static float SCALE = 0.50f;

    public static Bitmap transform(Context context, Bitmap source, float radius,
                                   float dimen, float dimenBlur) {
        return transform(context, source, radius, dimen, dimen, dimenBlur, dimenBlur);
    }

    public static Bitmap transform(Context context, Bitmap source, float radius,
                                   float dimenX, float dimenY, float dimenBlurX, float dimenBlurY) {
        return transform(context, source, radius, dimenX, dimenY, dimenBlurX, dimenBlurY, SCALE);
    }

    public static Bitmap transform(Context context, Bitmap source, float radius,
                                   float dimenX, float dimenY, float dimenBlurX, float dimenBlurY, float s) {
        Bitmap sourceBitmap = Bitmap.createScaledBitmap(source,
                (int) (dimenX * s),
                (int) (dimenY * s), false);

        Bitmap blurredBitmap;
        blurredBitmap = Bitmap.createBitmap(sourceBitmap);

        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,
                sourceBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        script.setInput(input);
        script.setRadius(radius);
        script.forEach(output);

        output.copyTo(blurredBitmap);
        source.recycle();

        return Bitmap.createScaledBitmap(blurredBitmap, (int) (dimenBlurX + 2 * radius),
                (int) (dimenBlurY + 2 * radius), false);
    }

    public static Bitmap transform(Context context, Drawable source, float radius,
                                   float dimenX, float dimenY, float dimenBLurX, float dimenBLurY) {
        return transform(context, convertToBitmap(source, (int) dimenX, (int) dimenY, radius),
                radius, dimenX, dimenY, dimenBLurX, dimenBLurY);
    }

    public static Bitmap transform(Context context, Drawable source, float radius, float dimen, float dimenBLur) {
        return transform(context, convertToBitmap(source, (int) dimen, (int) dimen, radius), radius, dimen, dimen, dimenBLur, dimenBLur);
    }

    public static Bitmap transform(Context context, Drawable source, float radius, float dimen, float dimenBLur, float s) {
        return transform(context, convertToBitmap(source, (int) dimen, (int) dimen, radius), radius, dimen, dimen, dimenBLur, dimenBLur, s);
    }

    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels, float radius) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels + (int) (4 * radius / SCALE), heightPixels + (int) (4 * radius / SCALE), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds((int) (2 * radius / SCALE), (int) (2 * radius / SCALE),
                widthPixels + (int) (2 * radius / SCALE), heightPixels + (int) (2 * radius / SCALE));
        drawable.draw(canvas);

        return mutableBitmap;
    }
}