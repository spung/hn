package com.hn.shared;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class ResHelper {
    public static int getDimen(Context context, @DimenRes int dimen) {
        return (int) context.getResources().getDimension(dimen);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        return context.getResources().getDrawable(drawableRes);
    }

    public static String getString(Context context, @StringRes int stringRes) {
        return context.getResources().getString(stringRes);
    }

    public static int getColor(Context context, @ColorRes int colorRes) {
        return context.getResources().getColor(colorRes);
    }
}
