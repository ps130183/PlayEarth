package com.ps.mrcyclerview.delegate;

import android.support.annotation.LayoutRes;

/**
 * Created by PengSong on 18/6/4.
 */

public class FooterDelegate implements ItemDelegate {

    private @LayoutRes
    int footerLayoutRes;

    public FooterDelegate(int footerLayoutRes) {
        this.footerLayoutRes = footerLayoutRes;
    }

    @Override
    public int getItemViewRes() {
        return footerLayoutRes;
    }
}
