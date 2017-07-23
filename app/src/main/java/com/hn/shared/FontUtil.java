package com.hn.shared;

import android.content.Context;
import android.graphics.Typeface;

import com.hn.R;

/**
 * Created by stevenpungdumri on 7/23/17.
 */

public class FontUtil {
    public static Typeface getDefaultFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), ResHelper.getString(context, R.string.font_regular));
    }
}
