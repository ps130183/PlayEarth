package com.km.rmbank.module.main.book;

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
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;

import butterknife.BindView;

public class BookVenueSitDetailActivity extends BaseActivity {

    @BindView(R.id.simple_tb_title_name)
    TextView titleName;

    private WebView mWebView;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_sit_detail;
    }

    @Override
    public String getTitleContent() {
        return "地点详情";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initWebView();
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
                titleName.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                if (Constant.userInfo == null){
                    showToast("获取不到用户信息，请登录后再试！");
                    result.cancel();
                    return true;
                }
                Bundle bundle = getIntent().getExtras();
                startActivity(SelectVenueTimeActivity.class,bundle);
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
        });

        String pId = getIntent().getStringExtra("placeId");
        mWebView.loadUrl(ApiConstant.API_BASE_URL+ ApiConstant.API_MODEL + "/accounts/site/index.html?pId=" + pId);
    }

    @JavascriptInterface
    public void book(){
        showToast("获取不到用户信息，请登录后再试！");
    }
}
