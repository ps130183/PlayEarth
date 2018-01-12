package com.km.rmbank.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.km.rmbank.R;
import com.km.rmbank.mvp.base.MvpPresenter;
import com.km.rmbank.mvp.base.MvpView;
import com.km.rmbank.mvp.base.PresenterDelegateImpl;
import com.km.rmbank.mvp.base.ProxyPresenter;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.SystemBarHelper;


/**
 * Created by PengSong on 17/12/21.
 */

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends AppCompatActivity implements MvpView {

    protected ViewManager mViewManager;

    private BaseTitleBar mBaseTitleBar;

    private Toast mToast;

    private ProxyPresenter<P> proxyPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewManager = new ViewManager(this, R.layout.base_activity_layout);
        setContentView(mViewManager.getContentView());
        //设置状态栏背景色为白色，并且字体、图标颜色为深色
        SystemBarHelper.tintStatusBar(this,0xffffff,0);
        SystemBarHelper.setStatusBarDarkMode(this);

        FrameLayout baseTitleBar = mViewManager.findView(R.id.base_title_bar);
        FrameLayout baseContent = mViewManager.findView(R.id.base_content);

        //标题栏区域
        mBaseTitleBar = getBaseTitleBar();
        if (mBaseTitleBar != null && mBaseTitleBar.getTitleBarViewRes() > 0) {
            View titleBarView = LayoutInflater.from(this).inflate(mBaseTitleBar.getTitleBarViewRes(), null, false);
            baseTitleBar.addView(titleBarView);
            onCreateTitleBar(mBaseTitleBar);
            mBaseTitleBar.createTitleBar(mViewManager);
        }

        //主内容区域
        View contentView = LayoutInflater.from(this).inflate(getContentViewRes(), null, false);
        baseContent.addView(contentView);

        PresenterDelegateImpl<V,P> presenterDelegate = new PresenterDelegateImpl<V,P>(createPresenter(), (V) this);
        proxyPresenter = new ProxyPresenter(presenterDelegate);
        proxyPresenter.oncreate(savedInstanceState);

        onFinally(savedInstanceState);
    }

    protected P createPresenter() {
        return null;
    }

    public final P getPresenter(){
        return proxyPresenter.getPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mPresenter != null){
//            mPresenter.clearSubscription();
//        }
//        mPresenter.detachView();
        proxyPresenter.onDestroy();
    }

    /**
     * 获取titleBar的布局
     *
     * @return
     */
    public BaseTitleBar getBaseTitleBar() {
        SimpleTitleBar titleBar = new SimpleTitleBar(this);
        titleBar.setLeftIcon(getLeftIcon()).setLeftClickListener(getLeftClickListener())
                .setTitleContent(getTitleContent());
        return titleBar;
    }


    /**
     * 获取页面的内容布局
     *
     * @return
     */
    public abstract @LayoutRes
    int getContentViewRes();

    /**
     * titleBar 的处理
     *
     * @param titleBar
     */
    protected void onCreateTitleBar(BaseTitleBar titleBar){};

    /**
     * activity的默认加载结束 ，正式 加载页面的具体内容
     *
     * @param savedInstanceState
     */
    public abstract void onFinally(@Nullable Bundle savedInstanceState);

    /**
     * 获取左侧图标
     *
     * @return
     */
    public int getLeftIcon() {
        return R.drawable.ic_arrow_left_white;
    }

    /**
     * 左侧按钮 点击事件
     *
     * @return
     */
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }

    /**
     * 页面标题信息
     *
     * @return
     */
    public String getTitleContent() {
        return "";
    }


    /**
     * 打印提示信息
     *
     * @param message
     */
    protected void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
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
