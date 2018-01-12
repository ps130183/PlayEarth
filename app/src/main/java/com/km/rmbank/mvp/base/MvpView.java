package com.km.rmbank.mvp.base;

/**
 * Created by PengSong on 18/1/8.
 */

public interface MvpView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载动画
     */
    void hideLoading();

    /**
     * 显示错误信息
     * @param message
     */
    void showError(String message);

}
