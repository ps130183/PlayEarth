package com.youth.banner.transformer;

import android.view.View;

/**
 * Created by PengSong on 18/8/24.
 */

public class ThreePagerTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View page, float position) {
        float v = Math.abs(position - 0.33f);
        float v1 = (float) (2 * (v * v));
        page.setScaleY(1 - v1);
        page.setScaleX(1 - v1);
    }
}
