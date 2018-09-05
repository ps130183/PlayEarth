package com.km.rmbank.module.main.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.entity.PayTypeEntity;
import com.km.rmbank.event.PaySuccessEvent;
import com.km.rmbank.event.PayWanYanVenueEvent;
import com.km.rmbank.event.WXPayResult;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.module.main.personal.book.BookVenueManageActivity;
import com.km.rmbank.mvp.model.PaymentModel;
import com.km.rmbank.mvp.presenter.PaymentPresenter;
import com.km.rmbank.mvp.view.IPaymentView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.wxpay.WxUtil;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class PaymentActivity extends BaseActivity<IPaymentView, PaymentPresenter> implements IPaymentView {

    @BindView(R.id.line)
    View line;
    @BindView(R.id.peopleNumber)
    TextView peopleNumber;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    private PayOrderDto mPayOrderDto;

    private int payType = 0;//默认0：商品，1：会员支付,2:基地活动报名,3:通讯录绑定，4预约晚宴场地

    private List<PayTypeEntity> payTypeEntities;

    //基地活动
//    ScenicServiceDto mServiceDto;
    private List<TicketDto> mTicketList;
    private int totalPersonNum;
    private int personNum;
    private int personal = 0;
    private float totalPrice = 0;
    private float actionPrice = 0;
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

    private void init() {
        mPayOrderDto = getIntent().getParcelableExtra("payOrderDto");
        payType = getIntent().getIntExtra("payType", 0);


        if (payType == 1) {//认证会员支付时，隐藏余额支付 和 积分
//            llPayBalance.setVisibility(View.GONE);
            createPayOrderSuccess(mPayOrderDto);
        } else if (payType == 2) {//参加基地活动等  报名
            initScenicOrder();
        } else if (payType == 3) {//绑定通讯录人员
            line.setVisibility(View.GONE);
            peopleNumber.setVisibility(View.VISIBLE);
            int number = getIntent().getIntExtra("number", 0);
            String content = "可邀请人脉数  <font color='#3285ff'>" + number + "</font>  个";
            peopleNumber.setText(Html.fromHtml(content));
            createPayOrderSuccess(mPayOrderDto);
        } else { //商品支付   4预约晚宴场地
            createPayOrderSuccess(mPayOrderDto);
        }

        getPresenter().getBalance();
        initPayTypeRecycler();

    }

    /**
     * 初始化支付方式的列表
     */
    private void initPayTypeRecycler() {
        final RecyclerView payTypeRecycler = mViewManager.findView(R.id.payTypeRecycler);
        //取消item的刷新动画
//        ((SimpleItemAnimator)payTypeRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        payTypeEntities = new ArrayList<>();
        payTypeEntities.add(new PayTypeEntity(R.mipmap.icon_pay_weixin, "微信支付"));
        payTypeEntities.add(new PayTypeEntity(R.mipmap.icon_pay_alipay, "支付宝"));
        if (payType == 0 || payType == 2) {
            payTypeEntities.add(new PayTypeEntity(R.mipmap.icon_pay_yuer, "余额支付"));
        }
//        payTypeEntities.add(new PayTypeEntity(R.mipmap.icon_pay_weixin,"微信支付"));

        RecyclerAdapterHelper<PayTypeEntity> mHelper = new RecyclerAdapterHelper<>(payTypeRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_payment_type, payTypeEntities, new RecyclerAdapterHelper.CommonConvert<PayTypeEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, PayTypeEntity mData, final int position) {
                        ImageView payImage = holder.findView(R.id.payImage);
                        String tag = mData.getPayImageRes() + "";
                        if (!tag.equals(payImage.getTag())) {
                            payImage.setTag(mData.getPayImageRes() + "");
                            payImage.setImageResource(mData.getPayImageRes());
                        }

                        holder.setText(R.id.payName, mData.getPayName());
                        CheckBox checkBox = holder.findView(R.id.checkbox);
                        checkBox.setChecked(mData.isChecked());
                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < payTypeEntities.size(); i++) {
                                    PayTypeEntity entity = payTypeEntities.get(i);
                                    if (position == i) {
                                        entity.setChecked(true);
                                    } else {
                                        entity.setChecked(false);
                                    }
                                }
                                //局部刷新
                                payTypeRecycler.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<PayTypeEntity>() {
            @Override
            public void onItemClick(CommonViewHolder holder, PayTypeEntity data, int position) {
                for (int i = 0; i < payTypeEntities.size(); i++) {
                    PayTypeEntity entity = payTypeEntities.get(i);
                    if (position == i) {
                        entity.setChecked(true);
                    } else {
                        entity.setChecked(false);
                    }
                }
                //局部刷新
                payTypeRecycler.getAdapter().notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, PayTypeEntity data, int position) {
                return false;
            }

        });
    }

    /**
     * 获取选中的支付方式
     *
     * @return
     */
    private int getPayTypePosition() {
        int position = -1;
        for (int i = 0; i < payTypeEntities.size(); i++) {
            PayTypeEntity entity = payTypeEntities.get(i);
            if (entity.isChecked()) {
                position = i;
                break;
            }
        }
        return position;
    }


    /**
     * 初始化基地活动 订单 信息
     */
    private void initScenicOrder() {
        checkTicketNos = new SparseArray<>();
        //优惠券列表
        mTicketList = getIntent().getParcelableArrayListExtra("ticketList");
//        mServiceDto = getIntent().getParcelableExtra("scenicService");
        totalPersonNum = getIntent().getIntExtra("personNum", 1);
        personNum = totalPersonNum;
        actionPrice = getIntent().getFloatExtra("price",0);
        totalPrice = actionPrice * personNum;
        tvAmount.setText(totalPrice + "");


        LinearLayout llTicket = mViewManager.findView(R.id.ll_ticket);
        llTicket.setVisibility(View.VISIBLE);
        if (mTicketList == null || mTicketList.size() == 0) {
            llTicket.setVisibility(View.GONE);
        }
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (TicketDto ticketDto : mTicketList){
            if (totalPersonNum == 1 && "6".equals(ticketDto.getTicketId())){

            } else {
                ticketDtos.add(ticketDto);
            }

        }
        RecyclerView ticketRecycler = mViewManager.findView(R.id.ticketList);
        final RecyclerAdapterHelper<TicketDto> mHelper = new RecyclerAdapterHelper<>(ticketRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_payement_ticket_list, ticketDtos, new RecyclerAdapterHelper.CommonConvert<TicketDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final TicketDto mData, final int position) {
                        GlideImageView imageView = holder.findView(R.id.ticketLogo);
                        CircleProgressView progressView = holder.findView(R.id.progressView);
                        GlideUtils.loadImageOnPregress(imageView, mData.getTicketLogo(), progressView);

                        holder.setText(R.id.ticketName, mData.getName());
                        final String type = mData.getType();
                        String num = mData.getNum();
                        TextView ticketContent = holder.findView(R.id.ticketContent);
                        ticketContent.setVisibility(View.VISIBLE);
                        if ("1".equals(type)) {//自己用
                            ticketContent.setText("可用次数：" + num);
                        } else if ("2".equals(type)) {//朋友用
                            ticketContent.setText("可邀请人数：" + num);
                        } else {
                            ticketContent.setVisibility(View.GONE);
                        }

                        holder.setText(R.id.useDate, "优惠券使用期限：" + DateUtils.getInstance().getDateToYMD(mData.getValidateTime()) + "止");

                        final CheckBox checkBox = holder.findView(R.id.rightCheck);
                        checkBox.setChecked(mData.isChecked());
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {//选中
                                    if (!mData.isChecked()) {
                                        mData.setChecked(true);
                                    }
                                    if ("6".equals(mData.getTicketId())) {//朋友用
                                        if (totalPersonNum == 1) {
                                            mData.setChecked(false);
                                            checkBox.setChecked(false);
                                            return;
                                        }
                                        int num = Integer.parseInt(mData.getNum());
                                        personNum -= totalPersonNum - 1 < num ? totalPersonNum - 1 : num;
                                        if (personNum < 0) {
                                            personNum = 0;
                                        }
                                        checkTicketNos.append(position, mData.getTicketNo());
                                    } else if (personal == 0){//自己用的券
                                        personal = 1;
                                        personNum--;
                                        checkTicketNos.append(position, mData.getTicketNo());
                                    } else {
                                        mData.setChecked(false);
                                        checkBox.setChecked(false);
                                    }

                                } else {//取消选中
                                    if (mData.isChecked()) {
                                        mData.setChecked(false);
                                    }
                                    if ("6".equals(mData.getTicketId())) {//朋友用
                                        if (totalPersonNum == 1) {
                                            mData.setChecked(true);
                                            checkBox.setChecked(true);
                                            return;
                                        }
                                        personNum += (totalPersonNum - 1);
                                    } else if (personal == 1){//自己用的券
                                        personal = 0;
                                        personNum++;
                                    } else {
                                        checkBox.setChecked(true);
                                    }
                                    checkTicketNos.remove(position);
                                }
                                totalPrice = actionPrice * personNum;
                                tvAmount.setText(totalPrice + "");
                            }
                        });

                    }
                }).create();

    }

//    @OnCheckedChanged({R.id.cb_balance, R.id.cb_weichat, R.id.cb_alipay, R.id.cb_bank})
//    public void  choosePaymentType(CompoundButton buttonView, boolean isChecked){
//        if (isChecked){
//            switch (buttonView.getId()){
//                case R.id.cb_balance:
//                    refreshPaymentType(0);
//                    break;
//                case R.id.cb_weichat:
//                    refreshPaymentType(1);
//                    break;
//                case R.id.cb_alipay:
//                    refreshPaymentType(2);
//                    break;
//                case R.id.cb_bank:
//                    refreshPaymentType(3);
//                    break;
//            }
//        }
//
//    }
//    private void refreshPaymentType(int position){
//        for (int i = 0; i < paymentTypes.length; i++){
//            if (position == i){
//                paymentTypes[i] = true;
//            } else {
//                paymentTypes[i] = false;
//            }
//        }
//        cbBalance.setChecked(paymentTypes[0]);
//        cbWeiChat.setChecked(paymentTypes[1]);
//        cbAlipay.setChecked(paymentTypes[2]);
//        cbBank.setChecked(paymentTypes[3]);
//    }

    @OnClick(R.id.btn_to_pay)
    public void toPay(View view) {
        if (payType == 2) {//报名基地活动
            String startDate = getIntent().getStringExtra("startDate");
//            if (startDate <= 0 ){
//                showToast("获取不到举办时间，请稍后再试！");
//                return;
//            }
            if (TextUtils.isEmpty(startDate)){
                showToast("获取不到举办时间，请稍后再试！");
                return;
            }
            long startTime = Long.valueOf(startDate);
            String startTime1 = DateUtils.getInstance().getDate(startTime);
            String actionId = getIntent().getStringExtra("actionId");
            StringBuffer ticketNos = new StringBuffer();
            for (int i = 0; i < checkTicketNos.size(); i++) {
                ticketNos.append(checkTicketNos.valueAt(i)).append("#");
            }
            if (checkTicketNos.size() > 0) {
                ticketNos.deleteCharAt(ticketNos.length() - 1);
            }
            getPresenter().applyScenicAction(actionId, totalPersonNum + "", startTime1, "", totalPrice + "", ticketNos.toString());
            return;
        }

        switch (getPayTypePosition()) {
            case 0://微信
                getPresenter().getWeiChatParams(mPayOrderDto.getPayNumber());
                break;
            case 1://支付宝
//                AlipayUtils
                getPresenter().getAliPayOrder(mPayOrderDto.getPayNumber());
                break;
            case 2://余额
                DialogUtils.showDefaultAlertDialog("是否使用余额支付？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        getPresenter().payBalance(mPayOrderDto.getPayNumber());
                    }
                });
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
        if ("0".equals(mPayOrderDto.getSumPrice())) { //积分抵扣以后 0元 直接跳到成功页面
            EventBusUtils.post(new PaySuccessEvent(payType));
        }
    }

    @Override
    public void getAlipayParamsSuccess(String alipayParamsDto) {
        AlipayUtils.toPay(PaymentActivity.this, alipayParamsDto)
                .subscribe(new Consumer<PayResult>() {
                    @Override
                    public void accept(@NonNull PayResult authResult) throws Exception {
                        switch (authResult.getResultStatus()) {
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
        if (WxUtil.check(this, WxUtil.getWXAPI(this))) {
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
//        tvBalanceIntro.setText("您的可用余额是" + userBalanceDto.getBalance() + "元");
    }

    @Override
    public void applyScenicResult(PayOrderDto payOrderDto) {
        mPayOrderDto = payOrderDto;
        if (payOrderDto.getSumPrice().equals("0") || payOrderDto.getSumPrice().equals("0.0")) {
            paySuccess(false);
            return;
        }

        switch (getPayTypePosition()) {
            case 0://微信
                getPresenter().getWeiChatParams(mPayOrderDto.getPayNumber());
                break;
            case 1://支付宝
//                AlipayUtils
                getPresenter().getAliPayOrder(mPayOrderDto.getPayNumber());
                break;
            case 2://余额
                DialogUtils.showDefaultAlertDialog("是否使用余额支付？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        getPresenter().payBalance(mPayOrderDto.getPayNumber());
                    }
                });
                break;
            case 3://银行卡
                break;

            default:
                showToast("请选择支付方式");
                break;
        }
    }

    @Override
    public void showPayResult(String result) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxpayResult(WXPayResult result) {
        if (result.getBaseResp().errCode == 0) {//支付成功
            paySuccess(true);
        } else {
            showToast("支付失败");
        }
    }

    /**
     * 支付成功
     */
    private void paySuccess(boolean isCheck) {
        if (isCheck) {
            getPresenter().checkPayResult(mPayOrderDto.getPayNumber());
        } else {
            if (payType == 1) {//会员充值
                Bundle bundle = new Bundle();
                bundle.putString("payNumber",mPayOrderDto.getPayNumber());
                startActivity(PayMemberSuccessActivity.class,bundle);
            } else if (payType == 2){ //基地活动报名等
                Bundle bundle = new Bundle();
                String hint1;
                String hint2;
                if (totalPrice == 0) {
                    hint1 = "报名成功";
                    hint2 = "请到“我的-活动”中查看";
                } else {
                    hint1 = "支付成功";
                    hint2 = "请到“我的-活动”中查看";
                }
                bundle.putString("hint1", hint1);
                bundle.putString("hint2", hint2);
                startActivity(PaySuccessActivity.class, bundle);
                finish();
            } else if (payType == 3) { //通讯录绑定支付
                Bundle bundle1 = getIntent().getExtras();
                startActivity(PayContractsOrderSuccessActivity.class, bundle1);
                return;
            } else if (payType == 4){//晚宴场地预定  缴费 结果
                BookVenueApplyDto mBookVenueApplyDto = getIntent().getParcelableExtra("VenueApplyDto");
                startActivity(BookVenueManageActivity.class);
                EventBusUtils.post(new PayWanYanVenueEvent(mBookVenueApplyDto));
                finish();
                return;
            } else {
                startActivity(HomeActivity.class);
                EventBusUtils.post(new PaySuccessEvent(payType));
                finish();
            }
        }
    }


}
