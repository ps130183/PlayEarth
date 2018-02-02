package com.km.rmbank.oldrecycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kamangkeji on 17/1/22.
 */

public class ViewUtils {
    public static View getView(Context context, @LayoutRes int layouRes){
        return View.inflate(context,layouRes,null);
    }

    public static View getView(LayoutInflater inflater, ViewGroup container, @LayoutRes int layoutRes){
        return inflater.inflate(layoutRes,container,false);
    }
}
