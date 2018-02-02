package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class MyOrderAdapter extends BaseAdapter<OrderDto> implements BaseAdapter.IAdapter<MyOrderAdapter.ViewHolder> {

    private boolean isUser = true;
    private onClickBtnActionListener onClickBtnActionListener;

    public MyOrderAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_order_list);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final OrderDto orderDto = getItemData(position);

        //'订单状态1尚未支付2支付成功3已发货4已完成5已评价',
        setOrderStatus(holder, orderDto.getStatus());
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBtnActionListener != null){
                    onClickBtnActionListener.clickBtnAction(orderDto,orderDto.getStatus());
                }
            }
        });
        holder.tvShopName.setText(orderDto.getShopName());
        GlideUtils.loadImage(mContext, orderDto.getThumbnailUrl(),holder.ivGoods);
        holder.tvGoodsName.setText(orderDto.getProductName());
        holder.tvGoodsCount.setText(orderDto.getProductCount() + "");
        holder.tvTotalMoney.setText(orderDto.getTotalPrice() + "");
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_total_money)
        TextView tvTotalMoney;

        @BindView(R.id.btn_action)
        Button btnAction;
        @BindView(R.id.rl_action)
        RelativeLayout rlAction;

        @BindView(R.id.tv_money_dis)
        TextView tvMoneyDis;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    public void setUser(boolean user) {
        isUser = user;
    }

    private void setOrderStatus(ViewHolder holder, int status) {
        holder.tvMoneyDis.setText(" 件商品  实付款：¥ ");
        switch (status) {
            case 1://待付款
                holder.tvOrderStatus.setText("待付款");
                holder.btnAction.setVisibility(View.VISIBLE);
                holder.btnAction.setText("去支付");
                holder.rlAction.setVisibility(View.VISIBLE);
                holder.tvMoneyDis.setText(" 件商品  需付款：¥ ");
                break;
            case 2://已支付  待发货
                if (!isUser) {//商家
                    holder.tvOrderStatus.setText("待发货");
                } else {
                    holder.tvOrderStatus.setText("待发货");
                }
                holder.rlAction.setVisibility(View.GONE);
                break;
            case 3://已发货
                holder.tvOrderStatus.setText("待收货");
                holder.rlAction.setVisibility(View.GONE);
//                if (isUser) {
//                    holder.btnAction.setVisibility(View.VISIBLE);
//                    holder.btnAction.setText("确认收货");
//                } else {
//
//                }
                break;
            case 4://已完成
                holder.tvOrderStatus.setText("待评价");
                holder.rlAction.setVisibility(View.GONE);
                break;
            case 5://待评价
                holder.tvOrderStatus.setText("已完成");
                holder.rlAction.setVisibility(View.GONE);
                break;
        }
    }

    public interface onClickBtnActionListener{
        void clickBtnAction(OrderDto orderDto, int status);
    }

    public void setOnClickBtnActionListener(MyOrderAdapter.onClickBtnActionListener onClickBtnActionListener) {
        this.onClickBtnActionListener = onClickBtnActionListener;
    }
}
