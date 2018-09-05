package com.km.rmbank.module.main.fragment.home;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.WebViewUtils;

public class HomeRecommendCompanyDetailsActivity extends BaseActivity {

    private WebView mWebView;

    private TextView mTitle;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home_recommend_company_details;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        //手机的物理按键返回按钮
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                if (mWebView != null && mWebView.canGoBack()){
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        });

        mTitle = mViewManager.findView(R.id.simple_tb_title_name);
        initWebView();

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    /**
     * 检测标题栏的左侧返回按钮
     * @return
     */
    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack()){
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        };
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
                mTitle.setText(title);
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

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url",url);
//                startActivity(SearchCompanyActivity.class,bundle);
//                return super.shouldOverrideUrlLoading(view, url);
//            }
        });
        String id = getIntent().getStringExtra("id");
        mWebView.loadUrl(ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/enterprise/html/details.html?id=" + id + "&userId=" + Constant.userInfo.getId());
    }


    @JavascriptInterface
    public void getIsLogin(){
//        if (Constant.userInfo == null){
        showToast("获取不到用户信息，请登录后再试！");
//        }
    }
}
