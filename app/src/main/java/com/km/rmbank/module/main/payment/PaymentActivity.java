package com.km.rmbank.module.main.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.alipay.AlipayUtils;
import com.km.rmbank.alipay.PayResult;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.event.PaySuccessEvent;
import com.km.rmbank.event.WXPayResult;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.PaymentModel;
import com.km.rmbank.mvp.presenter.PaymentPresenter;
import com.km.rmbank.mvp.view.IPaymentView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.wxpay.WxUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class PaymentActivity extends BaseActivity<IPaymentView,PaymentPresenter> implements IPaymentView {

    private boolean[] paymentTypes = {false,false,false,false};

    @BindView(R.id.cb_balance)
    CheckBox cbBalance;
    @BindView(R.id.cb_weichat)
    CheckBox cbWeiChat;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.cb_bank)
    CheckBox cbBank;

    @BindView(R.id.tv_balance_intro)
    TextView tvBalanceIntro;

    @BindView(R.id.ll_pay_balance)
    RelativeLayout llPayBalance;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    private PayOrderDto mPayOrderDto;
    //为0：商品 支付  3：体验会员，2：合伙人会员 5：约咖
    private int paymentForObj;
    private String mAmount;//支付金额
    private int payType = 0;//默认0：商品，1：会员支付

    private boolean becomeVip2 = false;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("收银台");
    }

    @Override
    protected PaymentPresenter createPresenter() {
        return new PaymentPresenter(new PaymentModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init(){
        paymentForObj = getIntent().getIntExtra("paymentForObj",0);
        mAmount = getIntent().getStringExtra("amount");
        mPayOrderDto = getIntent().getParcelableExtra("payOrderDto");
        becomeVip2 = getIntent().getBooleanExtra("becomeVip2",false);
        payType = getIntent().getIntExtra("payType",0);

        if (payType == 1){//认证会员支付时，隐藏余额支付 和 积分
            llPayBalance.setVisibility(View.GONE);
            createPayOrderSuccess(mPayOrderDto);
        } else { //商品支付
            createPayOrderSuccess(mPayOrderDto);
            //普通用户隐藏 余额支付
            if ("4".equals(Constant.userInfo.getRoleId()) || "3".equals(Constant.userInfo.getRoleId())){
                llPayBalance.setVisibility(View.GONE);
            } else {
                llPayBalance.setVisibility(View.VISIBLE);
            }
        }


    }

    @OnCheckedChanged({R.id.cb_balance, R.id.cb_weichat, R.id.cb_alipay, R.id.cb_bank})
    public void  choosePaymentType(CompoundButton buttonView, boolean isChecked){
        if (isChecked){
            switch (buttonView.getId()){
                case R.id.cb_balance:
                    refreshPaymentType(0);
                    break;
                case R.id.cb_weichat:
                    refreshPaymentType(1);
                    break;
                case R.id.cb_alipay:
                    refreshPaymentType(2);
                    break;
                case R.id.cb_bank:
                    refreshPaymentType(3);
                    break;
            }
        }

    }
    private void refreshPaymentType(int position){
        for (int i = 0; i < paymentTypes.length; i++){
            if (position == i){
                paymentTypes[i] = true;
            } else {
                paymentTypes[i] = false;
            }
        }
        cbBalance.setChecked(paymentTypes[0]);
        cbWeiChat.setChecked(paymentTypes[1]);
        cbAlipay.setChecked(paymentTypes[2]);
        cbBank.setChecked(paymentTypes[3]);
    }

    @OnClick(R.id.btn_to_pay)
    public void toPay(View view){
        int position = -1;
        for (int i = 0; i < paymentTypes.length; i++){
            if (paymentTypes[i]){
                position = i;
            }
        }
        switch (position){
            case 0://余额
                DialogUtils.showDefaultAlertDialog("是否使用余额支付？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        getPresenter().payBalance(mPayOrderDto.getPayNumber());
                    }
                });
                break;
            case 1://微信
                getPresenter().getWeiChatParams(mPayOrderDto.getPayNumber());
                break;
            case 2://支付宝
//                AlipayUtils
                getPresenter().getAliPayOrder(mPayOrderDto.getPayNumber());
                break;
            case 3://银行卡
                break;

            default:
                showToast("请选择支付方式");
                break;
        }
    }

    @Override
    public void createPayOrderSuccess(PayOrderDto payOrderDto) {
        mPayOrderDto = payOrderDto;
        tvAmount.setText(payOrderDto.getSumPrice() + "元");
        if ("0".equals(mPayOrderDto.getSumPrice())){ //积分抵扣以后 0元 直接跳到成功页面
            EventBusUtils.post(new PaySuccessEvent(paymentForObj));
        }
    }

    @Override
    public void getAlipayParamsSuccess(String alipayParamsDto) {
        AlipayUtils.toPay(PaymentActivity.this,alipayParamsDto)
                .subscribe(new Consumer<PayResult>() {
                    @Override
                    public void accept(@NonNull PayResult authResult) throws Exception {
                        switch (authResult.getResultStatus()){
                            case "9000"://支付成功
                                paySuccess(true);
                                break;
                            case "8000"://支付结果未知（可能成功）
                            case "6004":
                                break;
                            case "4000"://支付成功
                                showToast("支付失败");
                                break;
                            case "5000"://支付成功
                                showToast("重复请求");
                                break;
                            case "6001"://支付成功
                                break;
                            case "6002"://支付成功
                                showToast("网络连接出错");
                                break;


                        }
                    }
                });
    }

    @Override
    public void getWeiCharParamsSuccess(WeiCharParamsDto weicharParams) {
//        Logger.d(weicharParams.toString());
        if (WxUtil.check(this,WxUtil.getWXAPI(this))){
            WxUtil.toPayByWXAPI(weicharParams);
        }
    }

    @Override
    public void payBalanceSuccess() {
        paySuccess(false);
    }

    @Override
    public void checkSuccess() {
        paySuccess(false);
    }

    @Override
    public void showUserBalance(UserBalanceDto userBalanceDto) {
        tvBalanceIntro.setText("您的可用余额是" + userBalanceDto.getBalance() + "元");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxpayResult(WXPayResult result){
        if (result.getBaseResp().errCode == 0){//支付成功
            paySuccess(true);
        } else {
            showToast("支付失败");
        }
    }

    /**
     * 支付成功
     */
    private void paySuccess(boolean isCheck){
        if (isCheck){
            getPresenter().checkPayResult(mPayOrderDto.getPayNumber());
        } else {
            if (payType != 0){//会员充值
                startActivity(PaySuccessActivity.class);
                finish();
            } else {
                startActivity(HomeActivity.class);
                EventBusUtils.post(new PaySuccessEvent(paymentForObj));
                finish();
            }
        }
    }

    
}
