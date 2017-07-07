package com.hn.shared;

import android.content.Context;
import android.support.annotation.DimenRes;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class ResHelper {
    public static int getDimen(Context context, @DimenRes int dimen) {
        return (int) context.getResources().getDimension(dimen);
    }
}
