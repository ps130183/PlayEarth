package com.km.rmbank.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by PengSong on 18/1/8.
 */

public class PresenterDelegateImpl<V extends MvpView, P extends MvpPresenter<V>> implements IPresenterDelegate<P> {

    private P mvpPresenter;
    private V mvpView;

    public PresenterDelegateImpl(P mvpPresenter, V mvpView) {
        this.mvpPresenter = mvpPresenter;
        this.mvpView = mvpView;
    }

    @Override
    public void oncreate(@Nullable Bundle savedInstanceState) {
        if (mvpPresenter != null) {
            mvpPresenter.attachView(mvpView);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public P getPresenter() {
        return mvpPresenter;
    }

}
