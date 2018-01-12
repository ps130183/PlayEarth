package com.km.rmbank.mvp.base;

/**
 * Created by PengSong on 18/1/8.
 */

public interface MvpPresenter<V extends MvpView> {
    /**
     * 绑定View
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑View
     */
    void detachView();
}
