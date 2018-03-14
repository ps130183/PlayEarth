package com.km.rmbank.module.main.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.alipay.AlipayUtils;
import com.km.rmbank.alipay.PayResult;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.dto.TicketDto;
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
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.wxpay.WxUtil;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
    private int payType = 0;//默认0：商品，1：会员支付,2:基地活动报名

    private boolean becomeVip2 = false;

    //基地活动
    ScenicServiceDto mServiceDto;
    private List<TicketDto> mTicketList;
    private int totalPersonNum;
    private  int personNum;
    private float totalPrice = 0;
    private SparseArray<String> checkTicketNos;
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
        } else if (payType == 2){//参加基地活动等  报名
            initScenicOrder();
        }else { //商品支付
            createPayOrderSuccess(mPayOrderDto);
            //普通用户隐藏 余额支付
            if ("4".equals(Constant.userInfo.getRoleId()) || "3".equals(Constant.userInfo.getRoleId())){
                llPayBalance.setVisibility(View.GONE);
            } else {
                llPayBalance.setVisibility(View.VISIBLE);
            }
        }

        getPresenter().getBalance();


    }


    /**
     * 初始化基地活动 订单 信息
     */
    private void initScenicOrder(){
        checkTicketNos = new SparseArray<>();
        //优惠券列表
        mTicketList = getIntent().getParcelableArrayListExtra("ticketList");
        mServiceDto = getIntent().getParcelableExtra("scenicService");
        totalPersonNum = getIntent().getIntExtra("personNum",1);
        personNum = totalPersonNum;
        totalPrice = (float) (mServiceDto.getPrice() * personNum);
        tvAmount.setText(totalPrice + "元");


        LinearLayout llTicket = mViewManager.findView(R.id.ll_ticket);
        llTicket.setVisibility(View.VISIBLE);
        if (mTicketList == null || mTicketList.size() == 0){
            llTicket.setVisibility(View.GONE);
        }
        RecyclerView ticketRecycler = mViewManager.findView(R.id.ticketList);
        final RecyclerAdapterHelper<TicketDto> mHelper = new RecyclerAdapterHelper<>(ticketRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_payement_ticket_list, mTicketList, new RecyclerAdapterHelper.CommonConvert<TicketDto>() {
            @Override
            public void convert(CommonViewHolder holder, final TicketDto mData, final int position) {
                GlideImageView imageView = holder.findView(R.id.ticketLogo);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView,mData.getTicketLogo(),progressView);

                holder.setText(R.id.ticketName,mData.getName());
                final String type = mData.getType();
                String num = mData.getNum();
                TextView ticketContent = holder.findView(R.id.ticketContent);
                ticketContent.setVisibility(View.VISIBLE);
                if ("1".equals(type)){//自己用
                    ticketContent.setText("可用次数：" + num);
                } else if ("2".equals(type)){//朋友用
                    ticketContent.setText("可邀请人数：" + num);
                } else {
                    ticketContent.setVisibility(View.GONE);
                }

                holder.setText(R.id.useDate,"优惠券使用期限：" + DateUtils.getInstance().getDateToYMD(mData.getValidateTime()) + "止");

                CheckBox checkBox = holder.findView(R.id.rightCheck);
                checkBox.setChecked(mData.isChecked());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){//选中
                            if (!mData.isChecked()){
                                mData.setChecked(true);
                            }
                            if ("5".equals(mData.getTicketId())){//自己用
                                personNum--;
                            } else if ("6".equals(mData.getTicketId())){//朋友用
                                if (totalPersonNum == 1){
                                    mData.setChecked(false);
                                    return;
                                }
                                int num = Integer.parseInt(mData.getNum());
                                personNum -= totalPersonNum - 1 < num ? totalPersonNum - 1 : num;
                                if (personNum < 0){
                                    personNum = 0;
                                }
                            }
                            checkTicketNos.append(position,mData.getTicketNo());

                        } else {//取消选中
                            if (mData.isChecked()){
                                mData.setChecked(false);
                            }
                            if ("5".equals(mData.getTicketId())){//自己用
                                personNum++;
                            } else if ("6".equals(mData.getTicketId())){//朋友用
                                if (totalPersonNum == 1){
                                    mData.setChecked(true);
                                    return;
                                }
                                personNum += (totalPersonNum - 1);
                            }
                            checkTicketNos.remove(position);
                        }
                        totalPrice = (float) (mServiceDto.getPrice() * personNum);
                        tvAmount.setText(totalPrice + "元");
                    }
                });

            }
        }).create();

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
        if (payType == 2){//报名基地活动
            String startDate = getIntent().getStringExtra("startDate");
            StringBuffer ticketNos = new StringBuffer();
            for (int i = 0; i < checkTicketNos.size(); i++){
                ticketNos.append(checkTicketNos.valueAt(i)).append("#");
            }
            if (checkTicketNos.size() > 0) {
                ticketNos.deleteCharAt(ticketNos.length() - 1);
            }
            getPresenter().applyScenicAction(mServiceDto.getId(),totalPersonNum+"",startDate,"",totalPrice+"",ticketNos.toString());
            return;
        }
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
            EventBusUtils.post(new PaySuccessEvent(payType));
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

    @Override
    public void applyScenicResult(PayOrderDto payOrderDto) {
        mPayOrderDto = payOrderDto;
        if (payOrderDto.getSumPrice().equals("0") || payOrderDto.getSumPrice().equals("0.0")){
            paySuccess(false);
            return;
        }
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
                Bundle bundle = new Bundle();
                String hint1;
                String hint2;
                if (payType == 2){
                    if (totalPrice == 0){
                        hint1 = "报名成功";
                        hint2 = "请到“我的-已报名活动”中查看";
                    } else {
                        hint1 = "支付成功";
                        hint2 = "请到“我的-已报名活动”中查看";
                    }
                    bundle.putString("hint1",hint1);
                    bundle.putString("hint2",hint2);
                }
                startActivity(PaySuccessActivity.class,bundle);
                finish();
            } else {
                startActivity(HomeActivity.class);
                EventBusUtils.post(new PaySuccessEvent(payType));
                finish();
            }
        }
    }

    
}
