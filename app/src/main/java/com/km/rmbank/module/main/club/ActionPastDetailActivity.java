package com.km.rmbank.module.main.club;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ActionPastDetailModel;
import com.km.rmbank.mvp.presenter.ActionPastDetailPresenter;
import com.km.rmbank.mvp.view.IActionPastDetailView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.glidelib.progress.OnGlideImageViewListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ActionPastDetailActivity extends BaseActivity<IActionPastDetailView,ActionPastDetailPresenter> implements IActionPastDetailView {

    @BindView(R.id.simple_tb_title_name)
    TextView tvTitle;
//
//    @BindView(R.id.tv_action_name)
//    TextView tvActionName;
//    @BindView(R.id.tv_club_name)
//    TextView tvClubName;
//    @BindView(R.id.iv_club_logo)
//    ImageView ivClubLogo;
//    @BindView(R.id.tv_release_time)
//    TextView tvReleaseTime;
//    @BindView(R.id.rv_action_past_details)
//    RecyclerView rvActionPastDetails;

//    @BindView(R.id.jzv_player)
//    JZVideoPlayerStandard jzvPlayer;


    private String actionPastId;

//    private String clubId;
//    private boolean isMyClub;

    private ShareDto mShareDto;

    private SimpleTitleBar simpleTitleBar;

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;

    private DialogUtils
    .CustomBottomDialog mShareDialog;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_past_detail;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mShareDto == null){
                    showToast("获取分享内容失败");
                    return false;
                }
                if (mShareDialog == null){
                    throw new IllegalArgumentException("初始化分享弹出框失败！！！");
                }
                mShareDialog.show();
                return true;
            }
        });
    }

    @Override
    protected ActionPastDetailPresenter createPresenter() {
        return new ActionPastDetailPresenter(new ActionPastDetailModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        actionPastId = getIntent().getStringExtra("actionPastId");
        String activityId = getIntent().getStringExtra("activityId");
//////        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
//////        initActionPastDetails();
        getPresenter().getActionPastDetails(actionPastId,activityId);


        getWindow().setFlags(//强制打开GPU渲染
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setUpWebViewDefaults(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public Bitmap getDefaultVideoPoster() {
                if (this == null) {
                    return null;
                }

                //这个地方是加载h5的视频列表 默认图   点击前的视频图
                return BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.mipmap.icon_player);
            }

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress <= 90) {
                    mProgressBar.setProgress(newProgress);
                }
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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        initShareDialog();
//        if (!TextUtils.isEmpty(actionPastId)){
//            mWebView.loadUrl(ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/information/html/doyouwanttodate3.html?pId=" + actionPastId);
//        } else if(!TextUtils.isEmpty(activityId)){
//            mWebView.loadUrl(ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/information/html/doyouwanttodate4.html?activityId=" + activityId);
//        } else {
//            showToast("获取不到资讯的内容！");
//            finish();
//        }

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }


    /**
     * 分享提示框
     */
    private void initShareDialog(){
        mShareDialog = new DialogUtils.CustomBottomDialog(mInstance,"取消","分享微信好友","分享朋友圈");
        mShareDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(false);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }

    @Override
    public void showActionPastDetails(final ActionPastDto actionPastDto) {
        tvTitle.setText(actionPastDto.getTitle());

        mShareDto = new ShareDto();
        mShareDto.setTitle(TextUtils.isEmpty(actionPastDto.getClubName()) ? "玩转地球商旅学苑" : actionPastDto.getClubName());
        mShareDto.setContent(actionPastDto.getTitle());
        mShareDto.setPageUrl(actionPastDto.getWebDynamicUrl());
        mShareDto.setSharePicUrl(actionPastDto.getAvatarUrl());

        mWebView.loadUrl(actionPastDto.getWebDynamicUrl() + "&topApp=1");
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
    }

    
}
