package com.ps.mrcyclerview.delegate;

import android.support.annotation.LayoutRes;

/**
 * Created by PengSong on 18/6/9.
 */

public class HeaderDelegate implements ItemDelegate {

    private @LayoutRes int headerLayoutRes;

    public HeaderDelegate(int headerLayoutRes) {
        this.headerLayoutRes = headerLayoutRes;
    }

    @Override
    public int getItemViewRes() {
        return headerLayoutRes;
    }
}
