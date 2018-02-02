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

public class MyAttentionAdapter extends BaseAdapter<GoodsDto> implements BaseAdapter.IAdapter<MyAttentionAdapter.ViewHolder> {

    public MyAttentionAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_attention);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        GoodsDto goodsDto = getItemData(position);
        if (goodsDto.getType() == 1){//商品
            GlideUtils.loadImage(mContext,goodsDto.getThumbnailUrl(),holder.ivGoods);
            holder.tvGoodsPrice.setVisibility(View.VISIBLE);
            holder.tvSubTitle.setVisibility(View.VISIBLE);
            holder.tvGoodsName.setVisibility(View.VISIBLE);
            holder.tvClubName.setVisibility(View.GONE);
            holder.tvGoodsName.setText(goodsDto.getName());
            holder.tvGoodsPrice.setText("¥"+goodsDto.getPrice());
            holder.tvSubTitle.setText(goodsDto.getSubtitle());
        } else {//俱乐部
            GlideUtils.loadImage(mContext,goodsDto.getThumbnailUrl(),holder.ivGoods);
            holder.tvGoodsPrice.setVisibility(View.GONE);
            holder.tvSubTitle.setVisibility(View.GONE);
            holder.tvGoodsName.setVisibility(View.GONE);
            holder.tvClubName.setVisibility(View.VISIBLE);
            holder.tvClubName.setText(goodsDto.getName());
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

        @BindView(R.id.tv_club_name)
        TextView tvClubName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
