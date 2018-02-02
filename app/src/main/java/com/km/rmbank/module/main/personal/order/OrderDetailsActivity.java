package com.km.rmbank.module.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.event.EvaluateSuccessEvent;
import com.km.rmbank.mvp.model.OrderDetailsModel;
import com.km.rmbank.mvp.presenter.OrderDetailsPresenter;
import com.km.rmbank.mvp.view.IOrderDetailsView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DialogUtils;
import com.ps.glidelib.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity<IOrderDetailsView,OrderDetailsPresenter> implements IOrderDetailsView {

    private OrderDto mOrderDto;

    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;

    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;

    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.tv_receiver_address)
    TextView tvReceiverAddress;

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.btn_action)
    Button btnAction;

    @BindView(R.id.et_express_company)
    EditText etExpressCompany;
    @BindView(R.id.et_express_number)
    EditText etExpressNumber;
    @BindView(R.id.ll_express)
    LinearLayout llExpress;

    @BindView(R.id.tv_money_dis)
    TextView tvMoneyDis;

    private boolean isUser;
    private int mStatus;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("订单详情");
    }

    @Override
    protected OrderDetailsPresenter createPresenter() {
        return new OrderDetailsPresenter(new OrderDetailsModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mOrderDto = getIntent().getParcelableExtra("orderDto");
        isUser = getIntent().getBooleanExtra("isUser",false);
        getPresenter().getOrderDetail(mOrderDto);
    }

    private void loadOrderData(){
        tvShopName.setText(mOrderDto.getShopName());
        GlideUtils.loadImage(this,mOrderDto.getThumbnailUrl(),ivGoods);
        tvGoodsName.setText(mOrderDto.getProductName());

        tvGoodsCount.setText(mOrderDto.getProductCount()+"");
        tvTotalMoney.setText(mOrderDto.getTotalPrice());

        tvOrderNumber.setText(mOrderDto.getOrderNo());

        tvReceiverName.setText(mOrderDto.getReceivePerson());
        tvReceiverPhone.setText(mOrderDto.getReceivePersonPhone());
        tvReceiverAddress.setText(mOrderDto.getReceiveAddress());

        mStatus = mOrderDto.getStatus();
        showExpress(false,false);
        tvMoneyDis.setText(" 件商品  实付款：¥ ");
        switch (mStatus){
            case 1://待付款
                tvOrderStatus.setText("待付款");
                btnAction.setVisibility(View.GONE);
                tvMoneyDis.setText(" 件商品  需付款：¥ ");
                break;
            case 2://已支付  待发货
                btnAction.setVisibility(View.VISIBLE);
                if (!isUser) {//商家
                    tvOrderStatus.setText("待发货");
                    btnAction.setText("确认发货");
                    showExpress(true,true);
                } else {
                    showExpress(false,false);
                    tvOrderStatus.setText("待发货");
//                    btnAction.setText("确认收货");
                    btnAction.setVisibility(View.GONE);
                }
                break;
            case 3://已发货
                tvOrderStatus.setText("待收货");
                showExpress(true,false);
                if (isUser) {
                    btnAction.setVisibility(View.VISIBLE);
                    btnAction.setText("确认收货");
                } else {
                    btnAction.setVisibility(View.GONE);
                }
                break;
            case 4://已完成
                showExpress(true,false);
                tvOrderStatus.setText("待评价");
                if (isUser){
                    btnAction.setVisibility(View.VISIBLE);
                    btnAction.setText("去评价");
                } else {
                    btnAction.setVisibility(View.GONE);
                }

                break;
            case 5://待评价
                showExpress(true,false);
                tvOrderStatus.setText("已完成");
                btnAction.setVisibility(View.GONE);
                break;
        }
        if (mStatus >= 3){
            etExpressCompany.setText(mOrderDto.getExpressCompany());
            etExpressNumber.setText(mOrderDto.getCourierNumber());
        }
    }

    /**
     * 显示快递公司  和 获取焦点
     * @param isShow
     * @param focusable
     */
    private void showExpress(boolean isShow,boolean focusable){
        llExpress.setVisibility(isShow ? View.VISIBLE : View.GONE);
//        etExpressCompany.setVisibility(isShow ? View.VISIBLE : View.GONE);
//        etExpressNumber.setVisibility(isShow ? View.VISIBLE : View.GONE);

        etExpressCompany.setFocusable(focusable);
        etExpressCompany.setFocusableInTouchMode(focusable);
        etExpressNumber.setFocusable(focusable);
        etExpressNumber.setFocusableInTouchMode(focusable);
    }

    @OnClick(R.id.btn_action)
    public void btnAction(View view){
        if (isUser){
            switch (mStatus){
                case 2://确认收货
                    showToast("商家尚未发货");
                    break;
                case 3: //确认收货
                    DialogUtils.showDefaultAlertDialog("是否已经收货？", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            getPresenter().confirmReceiverGoods(mOrderDto);
                        }
                    });
                    break;
                case 4://去评价
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("orderDto",mOrderDto);
                    startActivity(GoodsEvaluateActivity.class,bundle);
                    break;
            }
        } else {
            if (mStatus == 2){//发货
                final String expressCompany = etExpressCompany.getText().toString();
                final String expressNumber = etExpressNumber.getText().toString();
                if (TextUtils.isEmpty(expressCompany) || TextUtils.isEmpty(expressNumber)){
                    showToast("请填写快递信息");
                    return;
                }
                mOrderDto.setExpressCompany(expressCompany);
                mOrderDto.setCourierNumber(expressNumber);
                DialogUtils.showDefaultAlertDialog("是否要发货？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        getPresenter().shopSendGoods(mOrderDto,expressCompany,expressNumber);
                    }
                });
            }
        }
    }

    @Override
    public void initOrderDetail(OrderDto orderDto) {
        mOrderDto = orderDto;
        loadOrderData();
    }

    @Override
    public void sendGoodsSuccess() {
        mOrderDto.setStatus(3);
        loadOrderData();
    }

    @Override
    public void receiverGoodsSuccess() {
        mOrderDto.setStatus(4);
        loadOrderData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void evaluateSuccess(EvaluateSuccessEvent evaluateSuccessEvent){
        if (evaluateSuccessEvent.getOrderNo().equals(mOrderDto.getOrderNo())){
            mOrderDto.setStatus(5);
            loadOrderData();
        }
    }
}
