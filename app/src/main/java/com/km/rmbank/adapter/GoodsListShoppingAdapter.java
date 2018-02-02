package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class GoodsListShoppingAdapter extends BaseAdapter<GoodsDto> implements BaseAdapter.IAdapter<GoodsListShoppingAdapter.ViewHolder> {

    public GoodsListShoppingAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_shop_goods);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        GoodsDto goodsDto = getItemData(position);
        GlideUtils.loadImage(mContext,goodsDto.getThumbnailUrl(),holder.ivGoods);

        holder.tvGoodsName.setText(goodsDto.getName());
        holder.tvGoodsPrice.setText("¥"+goodsDto.getPrice());
        holder.tvSubTitle.setText(goodsDto.getSubtitle());
        holder.tvAccess.setText(goodsDto.getAccess());
        if (goodsDto.getRole() == 2 && (goodsDto.getType() == 1 || goodsDto.getType() == 0)){
            holder.ivIsVip2.setVisibility(View.VISIBLE);
        } else {
            holder.ivIsVip2.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_sub_title)
        TextView tvSubTitle;

        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;

        @BindView(R.id.tv_access)
        TextView tvAccess;

        @BindView(R.id.iv_is_vip2)
        ImageView ivIsVip2;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
