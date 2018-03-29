package com.km.rmbank.customview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;

/**
 * Created by PengSong on 18/3/29.
 */

public class HeaderTabBehavior extends CoordinatorLayout.Behavior<View> {

    private float deltaY = 0;
    private float bgHeight=0;

    public HeaderTabBehavior() {
    }

    public HeaderTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header){

            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(y);
        LogUtils.d(child.getVisibility() == View.VISIBLE);
        return true;
    }
}
