package com.km.rmbank.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.km.rmbank.R;
import com.km.rmbank.mvp.base.MvpPresenter;
import com.km.rmbank.mvp.base.MvpView;
import com.km.rmbank.mvp.base.PresenterDelegateImpl;
import com.km.rmbank.mvp.base.ProxyPresenter;

/**
 * Created by PengSong on 18/1/10.
 */

public abstract class BaseFragment<V extends MvpView, P extends MvpPresenter<V>> extends Fragment implements MvpView {

    protected ViewManager mViewManager;

    private Toast mToast;

    private ProxyPresenter<P> proxyPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes = getLayoutRes();
        View view = getRootView(inflater,container,layoutRes);

        //页面的控件管理器
        mViewManager = new ViewManager(view);

        //presenter代理
        PresenterDelegateImpl<V,P> presenterDelegate = new PresenterDelegateImpl<V,P>(createPresenter(), (V) this);
        proxyPresenter = new ProxyPresenter(presenterDelegate);
        proxyPresenter.oncreate(savedInstanceState);

        onCreateView(savedInstanceState);
        return view;
    }

    private View getRootView(LayoutInflater inflater,ViewGroup parent,@LayoutRes int layoutRes) {
        return inflater.inflate(layoutRes,parent,false);
    }

    /**
     * 获取布局文件Id
     * @return
     */
    public abstract  @LayoutRes int getLayoutRes();

    /**
     * 创建presenter
     * @return
     */
    protected P createPresenter() {
        return null;
    }

    /**
     * 获得presenter
     * @return
     */
    public final P getPresenter(){
        return proxyPresenter.getPresenter();
    }

    /**
     * 页面初始化完成，开始真正的业务代码
     * @param savedInstanceState
     */
    public abstract void onCreateView(@Nullable Bundle savedInstanceState);


    /**
     * 打印提示信息
     *
     * @param message
     */
    protected void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        showToast(message);
    }
}

