package com.km.rmbank.titleBar;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.base.ViewManager;

/**
 * Created by PengSong on 18/1/19.
 */

public class GoodsToolBar extends BaseTitleBar {

    private OnClickBackListener onClickBackListener;

    @Override
    public int getTitleBarViewRes() {
        return R.layout.title_bar_goods;
    }

    @Override
    public void createTitleBar(ViewManager viewManager) {
        Toolbar toolbar = viewManager.findView(R.id.toolBar);
        toolbar.setNavigationIcon(R.mipmap.icon_arrow_left_black_block);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBackListener != null){
                    onClickBackListener.clickBack();
                }
            }
        });
    }

    public interface OnClickBackListener{
        void clickBack();
    }

    public void setOnClickBackListener(OnClickBackListener onClickBackListener) {
        this.onClickBackListener = onClickBackListener;
    }
}
