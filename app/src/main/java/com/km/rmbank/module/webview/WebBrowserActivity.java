package com.km.rmbank.module.webview;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class WebBrowserActivity extends BaseActivity {

    private TextView titleName;

    private WebView mWebView;
    private WindowBottomDialog mShareDialog;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_web_browser;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        super.onCreateTitleBar(titleBar);
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mShareDialog != null){
                    mShareDialog.show();
                }
                return true;
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        titleName = mViewManager.findView(R.id.simple_tb_title_name);
        mWebView = mViewManager.findView(R.id.webView);

        initShareDialog();

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
                titleName.setText(title);
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

        String webUrl = getIntent().getStringExtra("webUrl");
        mWebView.loadUrl(webUrl);
    }


    @JavascriptInterface
    public void defaultMethod(){
    }

    private void initShareDialog(){
        final ShareDto mShareDto = new ShareDto();
        String title = getIntent().getStringExtra("titleName");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String webUrl = getIntent().getStringExtra("webUrl");
        mShareDto.setTitle(title);
        mShareDto.setContent("");
        mShareDto.setSharePicUrl(imageUrl);
        mShareDto.setPageUrl(webUrl);
        mShareDialog = new WindowBottomDialog(mInstance,"取消","分享微信好友","分享到朋友圈");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i){
                    case 0:
                        UmengShareUtils.openShare(mInstance,mShareDto, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        UmengShareUtils.openShare(mInstance,mShareDto, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }
}
