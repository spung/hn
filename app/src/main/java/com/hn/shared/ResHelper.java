package com.hn.shared;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

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
}
