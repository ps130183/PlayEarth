package com.km.rmbank.module.main.appoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.entity.CheckDateEntity;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.ScenicServiceModel;
import com.km.rmbank.mvp.presenter.ScenicServicePresenter;
import com.km.rmbank.mvp.view.IScenicServiceView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActionOutdoorActivity extends BaseActivity<IScenicServiceView, ScenicServicePresenter> implements IScenicServiceView {
    @BindView(R.id.simple_tb_title_name)
    TextView titleName;

    private WebView mWebView;

    private String activityId;
    private String actionId;
    private String clubId;

    private WindowBottomDialog mShareDialog;
    private ShareDto mShareDto;

    private String personNum;
    private String starDate;
    private String price;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_outdoor;
    }

    @Override
    protected ScenicServicePresenter createPresenter() {
        return new ScenicServicePresenter(new ScenicServiceModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Constant.userLoginInfo.isEmpty()) {
                    showToast("请登录后再分享");
                    startActivity(LoginActivity.class);
                    return false;
                }
                if (item.getItemId() == R.id.share){
                    if (mShareDialog == null){
                        throw new IllegalArgumentException("初始化分享弹出框失败！！！");
                    } else {
                        mShareDialog.show();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        activityId = getIntent().getStringExtra("activityId");
        clubId = getIntent().getStringExtra("scenicId");

        initWebView();
        initShareDialog();
        //手机的物理按键返回按钮
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        });
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
     *
     * @return
     */
    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack()) {
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
    private void initWebView() {
        mWebView = mViewManager.findView(R.id.webView);
        final ProgressBar mProgressBar = mViewManager.findView(R.id.progressBar);
        WebViewUtils.setUpWebViewDefaults(mWebView);
        mWebView.addJavascriptInterface(this, "wzdq");
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
        mWebView.loadUrl(ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/Platform/index.html?activityId="
                + activityId
                + "&token=" + Constant.userLoginInfo.getToken()
                + "&clubId=" + clubId);
//        mWebView.loadUrl("http://www.wanzhuandiqiu.com/accounts/Platform/index.html");
    }

    private void initShareDialog(){
        mShareDialog = new WindowBottomDialog(mInstance,"取消","分享微信好友","分享朋友圈");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i){
                    case 0:
                        getPresenter().taskShare();
                        UmengShareUtils.openShare(mInstance,mShareDto, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1:
                        getPresenter().taskShare();
                        UmengShareUtils.openShare(mInstance,mShareDto, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }

    /**
     * 立即报名
     *
     * @param view
     */
    public void applyAction(View view) {
        mWebView.loadUrl("javascript:information()");
    }

    /**
     * 拨打客服电话
     * @param phone
     */
    @JavascriptInterface
    public void getphone(final String phone) {
        if (TextUtils.isEmpty(phone)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * 立即报名 获取相应的数据信息
     * @param time
     * @param price
     * @param personNum
     * @param id
     */
    @JavascriptInterface
    public void infor(String time,String price,String personNum,String id){
//        if (time > 0){
//            return;
//        }
        starDate = time;
        this.price = price;
        this.personNum = personNum;
        actionId = id;
        LogUtils.d(time + "   ----  " + price + "   ----   " + personNum);
        getPresenter().getPlatformTicketListOfScenic(actionId,clubId,activityId);
    }

    /**
     * 分享活动
     * @param particulars
     * @param title
     * @param thumbnail
     * @param url
     */
    @JavascriptInterface
    public void getshare(String particulars,String title,String thumbnail,String url){
        LogUtils.d("particulars === " + particulars);
        if (mShareDto == null){
            mShareDto = new ShareDto();
            mShareDto.setContent(particulars);
            mShareDto.setTitle(title);
            mShareDto.setSharePicUrl(thumbnail);
            mShareDto.setPageUrl(url);
        }
    }

    @Override
    public void showTicketList(List<TicketDto> ticketDtos) {
        LogUtils.d(ticketDtos.toString());
        Bundle bundle = getIntent().getExtras();
        bundle.putParcelableArrayList("ticketList", (ArrayList<? extends Parcelable>) ticketDtos);
        bundle.putInt("payType", 2);
        int personNum = Integer.parseInt(this.personNum);
        bundle.putInt("personNum", personNum);
        bundle.putFloat("price",Float.parseFloat(price));
        bundle.putString("actionId",actionId);
//        CheckDateEntity entity = checkDateEntities.get(0);
//        String startDate = DateUtils.getInstance().getDate(entity.getYear(), entity.getMonth(), entity.getDayOfMonty());
        bundle.putString("startDate", this.starDate);
        startActivity(PaymentActivity.class, bundle);
    }

    @Override
    public void applyFreeTeaSuccess() {

    }
}
