package com.km.rmbank.customview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.lang.ref.WeakReference;

/**
 * Created by PengSong on 18/3/28.
 */

public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<View> {

    private float delayY = 0;
    private float bgHeight=0;
    private float delayChildY = 0;

    public HeaderFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgHeight = ConvertUtils.dp2px(230);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header) {
//            dependentView = new WeakReference<>(dependency);
//            bgHeight = child.getY() + child.getHeight();
            int width = ScreenUtils.getScreenWidth(parent.getContext()) / 2;
            child.setPivotX(width);
            child.setPivotY(ConvertUtils.dp2px(20));
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (delayY == 0){
            delayY = dependency.getY() - bgHeight;
            delayChildY = child.getY();
        }

        float dy = dependency.getY() - delayY;
        dy = dy < 0 ? 0 : dy;

        float scale = dy / bgHeight;
        child.setScaleX(scale);
        child.setScaleY(scale);
//        LogUtils.d("scale === " + scale + "   childY === " + child.getY());

        float cy = -delayChildY * (1 - scale) + ConvertUtils.dp2px(20);
//        LogUtils.d("cy === " + cy);

        child.setTranslationY(cy);

        return true;
    }

}