package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.event.OtherAddressEvent;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.module.main.personal.address.ReceiverAddressActivity;
import com.km.rmbank.mvp.model.CreateShopOrderModel;
import com.km.rmbank.mvp.presenter.CreateShopOrderPresenter;
import com.km.rmbank.mvp.view.ICreateShopOrderView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.Constant;
import com.rey.material.widget.Switch;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderActivity extends BaseActivity<ICreateShopOrderView,CreateShopOrderPresenter> implements ICreateShopOrderView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.tv_receiver_address)
    TextView tvReceiverAddress;

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @BindView(R.id.swich)
    Switch aSwitch;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;

    //当前可用积分
    private String curIntegral = "0";
    private String totalMoney = "0";

    private List<ShoppingCartDto> shoppingCartDtos;
    private ReceiverAddressDto receiverAddressDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_order;
    }

    @Override
    public String getTitleContent() {
        return "完善订单";
    }

    @Override
    protected CreateShopOrderPresenter createPresenter() {
        return new CreateShopOrderPresenter(new CreateShopOrderModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        shoppingCartDtos = getIntent().getParcelableArrayListExtra("checkedGoods");
        if (shoppingCartDtos != null && shoppingCartDtos.size() > 0){
            curIntegral = shoppingCartDtos.get(0).getProductList().get(0).getTotal();
            tvIntegral.setText("选择可用积分："+curIntegral);
        }
        initRv();
        showOrderDatas(shoppingCartDtos);

        aSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if ("0".equals(curIntegral)){//积分为0 的时候不做计算
                    return;
                }
                BigDecimal btm = new BigDecimal(totalMoney);//总金额
                BigDecimal bint = new BigDecimal(curIntegral);//可用积分
                BigDecimal result;
                if (checked){
                    btm = btm.setScale(0,BigDecimal.ROUND_UP);
                    result = btm.subtract(bint);
                    if (result.doubleValue() < 0){
                        result = new BigDecimal("0.00");
                    }
                } else {
                    result = btm;
                }
                tvTotalMoney.setText(String.valueOf(result.doubleValue()));
            }
        });

        getPresenter().getDefaultReceiverAddress();
    }

    private void initRv(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new ShoppingCartParentListAdapter(this);
        adapter.setShowFreight(true);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showDefaultReceiverAddress(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
        tvReceiverName.setText(receiverAddressDto.getReceivePerson());
        tvReceiverPhone.setText(receiverAddressDto.getReceivePersonPhone());
        tvReceiverAddress.setText(receiverAddressDto.getReceiveAddress());
    }

    @Override
    public void showOrderDatas(List<ShoppingCartDto> cartEntities) {
        adapter.addData(cartEntities);
        totalMoney = adapter.getTotalMoneyOnCreateOrder();
        tvTotalMoney.setText(totalMoney);
    }

    @Override
    public void submitOrderSuccess(PayOrderDto payOrderDto) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("payOrderDto",payOrderDto);
            startActivity(PaymentActivity.class,bundle);
    }

    @OnClick(R.id.tv_submit_order)
    public void submitOrder(View view){

        String[] params = getOrderParams();
        getPresenter().submitOrder(params[0],params[1],receiverAddressDto.getId(),params[2],params[3]);
//        toNextActivity(PaymentActivity.class);
    }

    @OnClick({R.id.tv_receiver_name, R.id.tv_receiver_phone, R.id.tv_receiver_address, R.id.iv_other_address})
    public void getOtherAddress(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("select_other_address",true);
        startActivity(ReceiverAddressActivity.class,bundle);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void otherAddress(OtherAddressEvent event){
        showDefaultReceiverAddress(event.getReceiverAddressDto());
    }

    /**
     * 获取提交订单 的相应参数
     * @return
     */
    private String[] getOrderParams(){
        List<ShoppingCartDto> shoppingCartDtos = adapter.getAllData();
        String[] params = new String[4];
        StringBuffer productNos = new StringBuffer();
        StringBuffer productCounts = new StringBuffer();
        BigDecimal freight = new BigDecimal("0");
        for (ShoppingCartDto cartDto : shoppingCartDtos){
            freight = freight.add(new BigDecimal(cartDto.getFreight()));
            for (GoodsDetailsDto detailsDto : cartDto.getProductList()){
                productNos.append(detailsDto.getProductNo()).append("#");
                productCounts.append(detailsDto.getProductInShopCarCount()).append("#");
            }
        }

        productNos.replace(productNos.length() - 1, productNos.length(), "");
        productCounts.replace(productCounts.length() - 1, productCounts.length(), "");
        params[0] = productNos.toString();
        params[1] = productCounts.toString();
        params[2] = String.valueOf(freight.doubleValue());
        if (aSwitch.isChecked()){
            params[3] = curIntegral;
        } else {
            params[3] = "0";
        }
        return params;
    }


}
