package com.km.rmbank.titleBar;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.base.ViewManager;

/**
 * Created by PengSong on 18/3/12.
 */

public class HomeNewToolBar extends BaseTitleBar {

    private OnMapClickListener onMapClickListener;
    @Override
    public int getTitleBarViewRes() {
        return R.layout.title_bar_home_new;
    }

    @Override
    public void createTitleBar(ViewManager viewManager) {
        Toolbar mToolbar = viewManager.findView(R.id.toolBar);
        mToolbar.inflateMenu(R.menu.toolbar_home_new_map);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (onMapClickListener != null){
                    onMapClickListener.clickMap();
                }
                return false;
            }
        });
    }

    public void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        this.onMapClickListener = onMapClickListener;
    }

    public interface OnMapClickListener{
        void clickMap();
    }
}
