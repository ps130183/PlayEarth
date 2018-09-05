package com.km.rmbank.module.main.advert;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.module.main.appoint.ActionOutdoorActivity;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.module.main.appoint.AppointAfternoonTeaActivity;
import com.km.rmbank.module.main.personal.account.UserAccountDetailsActivity;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.retrofit.Response;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;

import butterknife.BindView;

public class AdvertisingActivity extends BaseActivity {

    @BindView(R.id.simple_tb_title_name)
    TextView titleName;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_advertising;
    }



    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initWebView();
    }

    private void initWebView(){
        WebView mWebView = mViewManager.findView(R.id.webView);
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
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url + "?fun=1");
    }

    @JavascriptInterface
    public void jumpAction(String data){
        Gson gson = new Gson();
        AdvertisDto advertisDto = gson.fromJson(data,AdvertisDto.class);
        showToast(advertisDto.getAdUrl());
        Bundle bundle = new Bundle();
        switch (advertisDto.getType()){
            case "1"://下午茶、晚宴
                bundle.putString("actionId",advertisDto.getId());
                startActivity(AppointAfternoonTeaActivity.class,bundle);
                break;
            case "2"://路演活动
                bundle.putString("actionId",advertisDto.getId());
                startActivity(ActionRecentInfoActivity.class,bundle);
                break;

            case "4"://户外基地
                bundle.putString("activityId",advertisDto.getId());
                bundle.putString("scenicId",advertisDto.getId());
                startActivity(ActionOutdoorActivity.class,bundle);
                break;

            case "5"://资讯
                bundle.putString("actionPastId",advertisDto.getId());
                startActivity(ActionPastDetailActivity.class,bundle);
                break;
        }
    }
}
