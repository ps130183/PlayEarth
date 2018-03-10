package com.km.rmbank.module.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;

import butterknife.BindView;

public class AgreementActivity extends BaseActivity {

    private String titleName = "";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        titleName = getIntent().getStringExtra("titleName");
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent(titleName);
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {


        String agreementUrl = getIntent().getStringExtra("agreementUrl");
        webView.loadUrl(agreementUrl);
    }
}
