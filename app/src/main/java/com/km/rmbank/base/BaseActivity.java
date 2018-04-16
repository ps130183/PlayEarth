package com.km.rmbank.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.dto.AppVersionDto;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.mvp.base.MvpPresenter;
import com.km.rmbank.mvp.base.MvpView;
import com.km.rmbank.mvp.base.PresenterDelegateImpl;
import com.km.rmbank.mvp.base.ProxyPresenter;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.retrofit.FileDownLoad;
import com.km.rmbank.retrofit.RetrofitManager;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogLoading;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.NavigationBarUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.ViewUtils;
import com.laojiang.retrofithttp.weight.downfilesutils.FinalDownFiles;
import com.laojiang.retrofithttp.weight.downfilesutils.action.FinalDownFileResult;
import com.laojiang.retrofithttp.weight.downfilesutils.downfiles.DownInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;


/**
 * Created by PengSong on 17/12/21.
 */

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends AppCompatActivity implements MvpView {

    protected BaseActivity mInstance = this;
    protected ViewManager mViewManager;

    private BaseTitleBar mBaseTitleBar;

    private Toast mToast;

    private ProxyPresenter<P> proxyPresenter;

    private DialogLoading dialogLoading;


    protected XRefreshView mXRefreshView;

    private OnClickBackLisenter onClickBackLisenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
        mViewManager = new ViewManager(this, R.layout.base_activity_layout);
        setContentView(mViewManager.getContentView());

        if (statusBarTextColorIsDark()){
            //设置状态栏背景色为白色，并且字体、图标颜色为深色
            SystemBarHelper.tintStatusBar(this, Color.WHITE,0f);
            SystemBarHelper.setStatusBarDarkMode(this);
        } else {
            SystemBarHelper.setStatusBarDarkMode(this);
        }

        LinearLayout mainContent = mViewManager.findView(R.id.mainContent);
        int screenHeight = ScreenUtils.getScreenHeight();
        int statusBarHeiht = BarUtils.getStatusBarHeight();
        //虚拟按键
        if (NavigationBarUtils.hasNavBar(this)){
            int height = NavigationBarUtils.getNavigationBarHeight(this);
            mainContent.getLayoutParams().height = screenHeight - statusBarHeiht - height;
        } else {
            LogUtils.i("没有虚拟按键");
//            mainContent.getLayoutParams().height = screenHeight - statusBarHeiht;
        }

        FrameLayout baseTitleBar = mViewManager.findView(R.id.base_title_bar);
        FrameLayout baseContent = mViewManager.findView(R.id.base_content);
//        baseContent.getLayoutParams().height = 1000;

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

        mXRefreshView = findViewById(R.id.xRefreshView);
        ButterKnife.bind(this);
        onFinally(savedInstanceState);

    }

    protected P createPresenter() {
        return null;
    }

    public final P getPresenter(){
        return proxyPresenter.getPresenter();
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏 强制
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(this);
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proxyPresenter.onDestroy();
        EventBusUtils.unregister(this);
        if (dialogLoading != null && dialogLoading.isShowing()){
            dialogLoading.dismiss();
        }
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
     * 状态栏的字体颜色是否是深色
     * @return
     */
    public boolean statusBarTextColorIsDark(){
        return true;
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
        return R.mipmap.icon_arrow_left_black_block;
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
        if (mXRefreshView != null && !mXRefreshView.mPullRefreshing){
            mXRefreshView.startRefresh();
        }
        if (dialogLoading == null){
            dialogLoading = new DialogLoading(this);
        }
        dialogLoading.show();
    }

    @Override
    public void hideLoading() {
        if (mXRefreshView != null && mXRefreshView.mPullRefreshing){
            mXRefreshView.stopRefresh();
        }
        if (dialogLoading != null){
            dialogLoading.hide();
        }
    }


    @Override
    public void showError(String message) {
        if (mXRefreshView != null && mXRefreshView.mPullRefreshing){
            mXRefreshView.stopRefresh(false);
        }
        if (this.getClass() == HomeActivity.class){
            return;
        }
        showToast(message);
    }

    @Override
    public void userIsNotLogin() {
        Constant.userLoginInfo.clear();
        if (this.getClass() == HomeActivity.class){
            return;
        }
        startActivity(LoginActivity.class);
    }

    /**
     * 启动activity
     * @param activityClass
     */
    public void startActivity(Class activityClass){
        startActivity(activityClass,null);
    }

    /**
     * 启动activity
     * @param activityClass
     * @param bundle
     */
    public void startActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    //按手机的物理返回键
    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && onClickBackLisenter != null) {//物理返回键
            return onClickBackLisenter.onClickBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setOnClickBackLisenter(OnClickBackLisenter onClickBackLisenter) {
        this.onClickBackLisenter = onClickBackLisenter;
    }

    public interface OnClickBackLisenter {
        boolean onClickBack();
    }


    //默认的evenBus接收
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void defaultMethod(String s) {

    }

    /**
     * 检测是否有新版本的app
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadApp(final DownloadAppEvent event) {
        if (!this.equals(event.getActivity())) {
            return;
        }
        FileDownLoad.getInstance().checkAppVersion(AppUtils.getAppVersionCode(this))
                .subscribe(new Observer<AppVersionDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AppVersionDto appVersionDto) {
                        updateApp(appVersionDto);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof BaseModel.APIException){
                            BaseModel.APIException exception = (BaseModel.APIException) e;
                            if (!event.getActivity().getClass().equals(HomeActivity.class)){
                                showToast(exception.message);
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 更新app
     * @param appVersionDto
     */
    private void updateApp(final AppVersionDto appVersionDto) {
        if (1 == appVersionDto.getFoce()){//强制更新
            downloadApp(appVersionDto);
        } else {//不强制更新
            DialogUtils.showDefaultAlertDialog("检测到新版本 " + appVersionDto.getVersionView() + "，是否更新？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    downloadApp(appVersionDto);
                }

            });
        }

    }

    private void downloadApp(final AppVersionDto appVersionDto){
        String path = AppUtils.getDownloadAppPath("wzdq-resplease-" + System.currentTimeMillis() + "-" + appVersionDto.getVersionView() + ".apk");
        String url = appVersionDto.getAppUrl();
        new FinalDownFiles(false, mInstance, url,
                path,
                new FinalDownFileResult() {

                    @Override
                    public void onSuccess(DownInfo downInfo) {
                        super.onSuccess(downInfo);
                        LogUtils.i("成功==",downInfo.toString());
                        installApp(downInfo.getSavePath());
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        DialogUtils.DismissLoadDialog();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtils.showDownloadDialog(mInstance, "检测到有新版本，正在下载 ..." , false);
                    }

                    @Override
                    public void onPause() {
                        super.onPause();
                    }

                    @Override
                    public void onStop() {
                        super.onStop();
                        DialogUtils.DismissLoadDialog();
                    }

                    @Override
                    public void onLoading(long readLength, long countLength) {
                        super.onLoading(readLength, countLength);
                        LogUtils.i("下载过程=="," countLength = "+countLength+"    readLength = " +readLength);
                        DialogUtils.setProgressValue((int) ((readLength * 100) / countLength));
                    }

                    @Override
                    public void onErroe(Throwable e) {
                        super.onErroe(e);
                        DialogUtils.DismissLoadDialog();
                    }
                });
    }

    /**
     * 安装app文件
     *
     * @param path
     */
    private void installApp(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File apk = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(mInstance, "com.km.rmbank.fileprovider", apk);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        }

        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
