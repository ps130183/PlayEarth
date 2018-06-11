package com.km.rmbank.module.main.appoint;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.presenter.ActionRecentInfoPresenter;
import com.km.rmbank.mvp.view.IActionRecentInfoView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;

public class ActionRecentInfoActivity extends BaseActivity<IActionRecentInfoView,ActionRecentInfoPresenter> implements  IActionRecentInfoView {

    @BindView(R.id.simple_tb_title_name)
    TextView titleName;

    private WebView mWebView;

    private String actionId;
    private ActionDto mActionDto;
    private String clubId;
    private boolean isMyClub;

    private ShareDto shareDto;
    private DialogUtils.CustomBottomDialog mShareDialog;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_recent_info;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Constant.userLoginInfo.isEmpty()){
                    showToast("请登录后再分享");
                    startActivity(LoginActivity.class);
                    return false;
                }
                if (mShareDialog == null){
                    throw new IllegalArgumentException("初始化分享弹出框失败！！！");
                }
                if (item.getItemId() == R.id.share){
                    mShareDialog.show();
                }
                return false;
            }
        });
    }


    @Override
    protected ActionRecentInfoPresenter createPresenter() {
        return new ActionRecentInfoPresenter(new ActionRecentInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
        actionId = getIntent().getStringExtra("actionId");
        clubId = getIntent().getStringExtra("clubId");
        getPresenter().getActionRecentInfo(actionId);
        shareDto = new ShareDto();

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

        initShareDialog();
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

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                if (Constant.userInfo == null){
                    showToast("获取不到用户信息，请登录后再试！");
                    result.cancel();
                    return true;
                }
                long holdDate = DateUtils.getInstance().stringDateToMillis(mActionDto.getHoldDate(),DateUtils.YMDHM);
                long curDate = System.currentTimeMillis();
                if (curDate >= holdDate) {
                    showToast("报名已截止");
                    result.cancel();
                    return true;
                }
                DialogUtils.showDefaultAlertDialog("是否报名《" + mActionDto.getTitle() + "》活动？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        result.confirm();
                        getPresenter().applyAction(mActionDto.getId(), Constant.userInfo.getName(), Constant.userInfo.getMobilePhone());
                    }
                });
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
        mWebView.loadUrl(ApiConstant.API_BASE_URL+ ApiConstant.API_MODEL + "/accounts/About/html/doyouwanttodate2.html?pId="
                + actionId
                + "&token=" + Constant.userLoginInfo.getToken()
                + "&clubId=" + clubId);
    }


    @JavascriptInterface
    public void getIsLogin(){
//        if (Constant.userInfo == null){
            showToast("获取不到用户信息，请登录后再试！");
//        }
    }
    /**
     * 初始化分享弹出框
     */
    private void initShareDialog(){
        mShareDialog = new DialogUtils.CustomBottomDialog(mInstance,"取消","分享微信好友","分享朋友圈");
        mShareDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i){
                    case 0://分享到微信好友
                        UmengShareUtils.openShare(mInstance,shareDto,SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://分享朋友圈
                        UmengShareUtils.openShare(mInstance,shareDto,SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {
        mActionDto = actionDto;

        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());
    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId));
    }

    @Override
    public void followClubSuccess(boolean isFollow) {

    }

    @Override
    public void addActiveValueSuccess(String result) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }

}
