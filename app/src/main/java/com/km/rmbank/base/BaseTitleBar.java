package com.km.rmbank.base;

import android.support.annotation.LayoutRes;

/**
 * Created by PengSong on 17/12/21.
 */

public abstract class BaseTitleBar {
    public abstract  @LayoutRes int getTitleBarViewRes();

    public abstract void createTitleBar(ViewManager viewManager);
}
