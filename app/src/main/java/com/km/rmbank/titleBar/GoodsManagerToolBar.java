package com.km.rmbank.titleBar;


import android.support.annotation.MenuRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.base.ViewManager;

/**
 * Created by PengSong on 18/1/24.
 */

public class GoodsManagerToolBar extends BaseTitleBar {

    private Toolbar toolbar;
    private @MenuRes int mRightMenuRes;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    private View.OnClickListener clickListener;
    @Override
    public int getTitleBarViewRes() {
        return R.layout.title_bar_goods_manager;
    }

    @Override
    public void createTitleBar(ViewManager viewManager) {
        toolbar = viewManager.findView(R.id.toolBar);

        toolbar.inflateMenu(mRightMenuRes);
        if (onMenuItemClickListener != null){
            toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onClick(v);
                }
            }
        });
    }

    public void setRightMenu(@MenuRes int mRightMenuRes, Toolbar.OnMenuItemClickListener onMenuItemClickListener){
        this.mRightMenuRes = mRightMenuRes;
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setBackClickListener(View.OnClickListener onClickListener){
        this.clickListener = onClickListener;
    }

}
