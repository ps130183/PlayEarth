package com.km.rmbank.module.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DataCleacManager;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.rey.material.widget.Switch;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends BaseActivity {

//    @BindView(R.id.swich_usercard)
//    Switch swichUsercard;

    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("设置");
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
//        swichUsercard.setChecked(Constant.isAllowUserCard);

//        swichUsercard.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(Switch view, boolean checked) {
//                mPresenter.updateAllowUserCard(checked);
//            }
//        });

        try {
            tvCacheSize.setText(DataCleacManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_clear_cache)
    public void clearCache(View view){
//        SPUtils.getInstance().clear();
        DialogUtils.showDefaultAlertDialog("是否清除缓存？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                Observable.just(1)
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                                DataCleacManager.clearAllCache(SettingActivity.this);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                                LogUtils.d("缓存已清空");
                                try {
                                    tvCacheSize.setText(DataCleacManager.getTotalCacheSize(SettingActivity.this));
                                    showToast("缓存已清除");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

    }

//    @OnClick(R.id.tv_push_message_setting)
//    public void pushMessageSetting(View view){
//        showToast("设置缓存推送消息");
//    }

    @OnClick(R.id.tv_about)
    public void aboutMe(View view){
//        showToast("关于我们");
        startActivity(AboutMeActivity.class);
    }

    @OnClick(R.id.tv_user_agreement)
    public void userAgreement(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","用户协议");
        bundle.putString("agreementUrl", ApiConstant.API_BASE_URL + ApiConstant.API_MODEL +"/member/agreement/view");
        startActivity(AgreementActivity.class,bundle);
    }

    @OnClick(R.id.tv_logout)
    public void logout(View view){
        DialogUtils.showDefaultAlertDialog("是否要退出登录", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
//                showToast("退出登录");
                Constant.userLoginInfo.clear();
//                EventBusUtils.post(new LogoutEntity(true));
                startActivity(LoginActivity.class);
            }
        });
    }

    /**
     * 检测app版本
     * @param view
     */
    @OnClick(R.id.tv_update)
    public void updateApp(View view){
        EventBusUtils.post(new DownloadAppEvent(this));
    }

//    @Override
//    public void showAllowUserCardResult(boolean isAllow) {
//        swichUsercard.setChecked(isAllow);
//        Constant.isAllowUserCard = isAllow;
//    }

}
