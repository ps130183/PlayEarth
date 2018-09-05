package com.km.rmbank.module.main.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SPUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.card.UserCardActivity;
import com.km.rmbank.module.main.fragment.home.SearchCompanyActivity;
import com.km.rmbank.module.main.personal.profession.ProfessionIntroduceActivity;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.model.SearchCompanyModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.presenter.SearchCompanyPresenter;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.mvp.view.ISearchCompanyView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.RefreshUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

/**
 * 首页搜人脉  搜企业
 */
public class HomeSearchCompanyFragment extends BaseFragment<ISearchCompanyView, SearchCompanyPresenter> implements ISearchCompanyView {

    private WebView mWebView;

    private int isShow = 0;

    public static HomeSearchCompanyFragment newInstance(Bundle bundle) {
        HomeSearchCompanyFragment fragment = new HomeSearchCompanyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected SearchCompanyPresenter createPresenter() {
        return new SearchCompanyPresenter(new SearchCompanyModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_search_company;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initWebView();

        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getUserInfo();
            }
        });
    }


    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }


    /**
     * 初始化 webview
     */
    private void initWebView(){
        mWebView = mViewManager.findView(R.id.webView);
        final ProgressBar mProgressBar = mViewManager.findView(R.id.progressBar);
        WebViewUtils.setUpWebViewDefaults(mWebView);
        mWebView.addJavascriptInterface(this,"wzdq");
        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress <= 90) {
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                if (Constant.userInfo == null){
                    showToast("获取不到用户信息，请登录后再试！");
                    result.cancel();
                    return true;
                }

                result.cancel();
                return true;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // 加载某些网站的时候会报:ERR_CONNECTION_REFUSED,因此需要在这里取消进度条的显示
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                startActivity(SearchCompanyActivity.class,bundle);
                return true;
            }
        });
        getPresenter().getUserInfo();

    }


    /**
     * 跳名片
     */
    @JavascriptInterface
    public void cardInfoClick(String userId){
        getPresenter().getUserCardById(userId);
    }

    @JavascriptInterface
    public void showNoOpen(String message){
        showToast(message);
    }

    /**
     * 企业认证
     */
    @JavascriptInterface
    public void gotoCompanyAuth(){
        if (Constant.userInfo == null){
            showToast("请登录后再试！");
            startActivity(LoginActivity.class);
            return;
        }
        startActivity(ProfessionIntroduceActivity.class);
    }



    @Override
    public void showOtherUserInfo(UserInfoDto otherUserInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCard",otherUserInfo);
        startActivity(UserCardActivity.class,bundle);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfo) {
        isShow = SPUtils.getInstance().getInt("isShow",0);
        mWebView.loadUrl(ApiConstant.API_BASE_URL+ ApiConstant.API_MODEL + "/accounts/enterprise/index.html?fun=1&userId="+ userInfo.getId() + "&isShow="+isShow);
        if (isShow == 0){
            SPUtils.getInstance().put("isShow",-1);
        }
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        isShow = SPUtils.getInstance().getInt("isShow",0);
        mWebView.loadUrl(ApiConstant.API_BASE_URL+ ApiConstant.API_MODEL + "/accounts/enterprise/index.html?fun=1&userId=&isShow="+isShow);
        if (isShow == 0){
            SPUtils.getInstance().put("isShow",-1);
        }
    }
}
