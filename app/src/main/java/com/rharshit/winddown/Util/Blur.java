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
    private static float BLUR_FACTOR = 3f;
    private static float SCALE = 1 / BLUR_FACTOR;

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
        Bitmap pad = addPadding(source, radius, dimenX, dimenY, dimenBlurX, dimenBlurY);
        return process(context, pad, radius, dimenX, dimenY, dimenBlurX, dimenBlurY, s);
    }

    public static Bitmap process(Context context, Bitmap source, float radius,
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

        Bitmap rtn = Bitmap.createScaledBitmap(blurredBitmap, (int) (dimenBlurX + 2 * radius),
                (int) (dimenBlurY + 2 * radius), false);
        return rtn;
    }

    public static Bitmap transform(Context context, Bitmap source, float radius, float dimen, float dimenBLur, float s) {
        return transform(context, source, radius, dimen, dimen, dimenBLur, dimenBLur, s);
    }

    public static Bitmap transform(Context context, Drawable source, float radius,
                                   float dimenX, float dimenY, float dimenBlurX, float dimenBllurY) {
        return process(context, convertToBitmap(source, (int) dimenX, (int) dimenY, radius),
                radius, dimenX, dimenY, dimenBlurX, dimenBllurY);
    }

    private static Bitmap process(Context context, Bitmap source, float radius, float dimenX, float dimenY, float dimenBlurX, float dimenBlurY) {
        return process(context, source, radius, dimenX, dimenY, dimenBlurX, dimenBlurY, SCALE);
    }

    public static Bitmap transform(Context context, Drawable source, float radius, float dimen, float dimenBLur) {
        return process(context, convertToBitmap(source, (int) dimen, (int) dimen, radius), radius, dimen, dimen, dimenBLur, dimenBLur);
    }

    public static Bitmap transform(Context context, Drawable source, float radius, float dimen, float dimenBLur, float s) {
        return process(context, convertToBitmap(source, (int) dimen, (int) dimen, radius), radius, dimen, dimen, dimenBLur, dimenBLur, s);
    }

    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels, float radius) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels + (int) (4 * radius / SCALE), heightPixels + (int) (4 * radius / SCALE), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds((int) (2 * radius / SCALE), (int) (2 * radius / SCALE),
                widthPixels + (int) (2 * radius / SCALE), heightPixels + (int) (2 * radius / SCALE));
        drawable.draw(canvas);

        return mutableBitmap;
    }

    private static Bitmap addPadding(Bitmap source, float radius, float dimenX, float dimenY,
                                     float dimenBlurX, float dimenBlurY) {
        int dimen = Math.max(source.getWidth(), source.getHeight());
        Bitmap mutableBitmap = Bitmap.createBitmap((dimen + (int) (4 * radius / SCALE)),
                (dimen + (int) (4 * radius / SCALE)), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawBitmap(source, (int) (2 * radius / SCALE) + (dimen - source.getWidth()) / 2,
                (int) (2 * radius / SCALE) + (dimen - source.getHeight()) / 2, null);

        return mutableBitmap;
    }
}