package com.km.rmbank.module.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.event.EvaluateSuccessEvent;
import com.km.rmbank.mvp.presenter.GoodsEvaluatePresenter;
import com.km.rmbank.mvp.view.IGoodsEvaluateView;
import com.km.rmbank.utils.EventBusUtils;
import com.ps.glidelib.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsEvaluateActivity extends BaseActivity<IGoodsEvaluateView,GoodsEvaluatePresenter> implements IGoodsEvaluateView {

    @BindView(R.id.et_evaluate)
    EditText etEvaluate;


    private OrderDto mOrderDto;

    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGodosName;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_goods_evaluate;
    }


    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mOrderDto = getIntent().getParcelableExtra("orderDto");
        initView(mOrderDto);
    }

    private void initView(OrderDto orderDto){
        tvShopName.setText(orderDto.getShopName());
        GlideUtils.loadImage(this,orderDto.getThumbnailUrl(),ivGoods);
        tvGodosName.setText(orderDto.getProductName());
        tvGoodsCount.setText(orderDto.getProductCount()+"");
        tvTotalMoney.setText(orderDto.getTotalPrice());
    }

    @OnClick(R.id.btn_evaluate)
    public void evaluate(View view){
        String content = etEvaluate.getText().toString();
        if (TextUtils.isEmpty(content)){
            showToast("请填写评论的内容");
            return;
        }
        getPresenter().evaluateGoods(mOrderDto.getOrderNo(),content);
    }

    @Override
    public void evaluateSuccess() {
        showToast("评论成功");
        EventBusUtils.post(new EvaluateSuccessEvent(mOrderDto.getOrderNo()));
        finish();
    }


    @Override
    public void showUserEvaluate(List<EvaluateDto> evaluateDtos, int pageNo) {

    }
}
