package com.km.rmbank.module.realname;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;

import butterknife.BindView;

public class CertifyRulesActivity extends BaseActivity {

    @BindView(R.id.webView)
    public WebView mWebView;
    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_certify_rules;
    }

    @Override
    public String getTitleContent() {
        return "实名认证须知";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress <= 90) {
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.loadUrl("http://wanzhuandiqiu.com/accounts/renzheng.html");
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return false;
                } else {
                    finish();
                    return true;
                }
            }
        });

    }

    /**
     * 已阅读 跳到下一页 拍照上传
     * @param view
     */
    public void nextPage(View view) {
        startActivity(IdentityVerificationActivity.class);
    }
}
