package com.km.rmbank.titleBar;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.base.ViewManager;


/**
 * Created by PengSong on 17/12/21.
 */

public class SimpleTitleBar extends BaseTitleBar {

    private Context mContext;

    private Toolbar mToolBar;

    private @DrawableRes int mLeftIcon;
    private View.OnClickListener mLeftClickListener;

    private String mTitleContent;

    private @DrawableRes int mRightImgRes;
    private String mRightMenuContent;
    private View.OnClickListener mRightMenuClickListener;

    private @MenuRes int mRightMenuRes;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    private int toolBarBackgroundColor = 0;
    private int titleColor = 0;

    private TextView mTitle;

    public SimpleTitleBar(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getTitleBarViewRes() {
        return R.layout._default_title_bar;
    }

    @Override
    public void createTitleBar(final ViewManager viewManager) {
        mToolBar = viewManager.findView(R.id.toolBar);
        mToolBar.setPopupTheme(R.style.PopupMenu);

        if (toolBarBackgroundColor != 0){
            mToolBar.setBackgroundColor(toolBarBackgroundColor);
            viewManager.findView(R.id.line).setVisibility(View.GONE);
        }

        //设置标题
        viewManager.setText(R.id.simple_tb_title_name, TextUtils.isEmpty(mTitleContent) ? "" : mTitleContent);
        if (titleColor != 0){
            mTitle = viewManager.findView(R.id.simple_tb_title_name);
            TextView right = viewManager.findView(R.id.simple_tb_tv_right);
            right.setTextColor(titleColor);
            mTitle.setTextColor(titleColor);
        }
        //左侧按钮 返回
        if (mLeftIcon > 0){
            mToolBar.setNavigationIcon(mLeftIcon);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLeftClickListener != null){
                        mLeftClickListener.onClick(view);
                    }
                }
            });
        }

        //右侧按钮  图标
        if (mRightImgRes > 0){
            ImageView rightImg = viewManager.getImageView(R.id.simple_tb_iv_right);
            rightImg.setVisibility(View.VISIBLE);
            rightImg.setImageResource(mRightImgRes);
            rightImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRightMenuClickListener != null){
                        mRightMenuClickListener.onClick(view);
                    }
                }
            });
        } else if (!TextUtils.isEmpty(mRightMenuContent)){
            TextView rightMenu = viewManager.findView(R.id.simple_tb_tv_right);
            rightMenu.setVisibility(View.VISIBLE);
            rightMenu.setText(mRightMenuContent);
            rightMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRightMenuClickListener != null){
                        mRightMenuClickListener.onClick(view);
                    }
                }
            });
        } else if (mRightMenuRes > 0){
            mToolBar.inflateMenu(mRightMenuRes);
            if (onMenuItemClickListener != null){
                mToolBar.setOnMenuItemClickListener(onMenuItemClickListener);
            }
        }



    }

    public void setToolBarTitle(String title){
        mTitle.setText(title);
    }

    public SimpleTitleBar setLeftIcon(int mLeftIcon) {
        this.mLeftIcon = mLeftIcon;
        return this;
    }

    public SimpleTitleBar setLeftClickListener(View.OnClickListener mLeftClickListener) {
        this.mLeftClickListener = mLeftClickListener;
        return this;
    }

    public SimpleTitleBar setTitleContent(String mTitleContent) {
        this.mTitleContent = mTitleContent;
        return this;
    }

    public SimpleTitleBar setRightImgRes(int mRightImgRes) {
        this.mRightImgRes = mRightImgRes;
        return this;
    }

    public SimpleTitleBar setRightMenuContent(String mRightMenuContent) {
        this.mRightMenuContent = mRightMenuContent;
        return this;
    }

    public SimpleTitleBar setRightMenuClickListener(View.OnClickListener mRightMenuClickListener) {
        this.mRightMenuClickListener = mRightMenuClickListener;
        return this;
    }

    public SimpleTitleBar setRightMenuRes(int mRightMenuRes) {
        this.mRightMenuRes = mRightMenuRes;
        return this;
    }

    public SimpleTitleBar setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
        return this;
    }

    public void setToolBarBackgroundColor(int toolBarBackgroundColor) {
        this.toolBarBackgroundColor = toolBarBackgroundColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
}
