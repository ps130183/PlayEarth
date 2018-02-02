package com.km.rmbank.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.oldrecycler.BaseSwipeRvAdapter;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/26.
 */

public class RepleaseGoodsListAdapter extends BaseSwipeRvAdapter<GoodsDto> implements BaseSwipeRvAdapter.IAdapter<RepleaseGoodsListAdapter.ViewHolder> {

    //下架
    private onClickSoldOutListener onClickSoldOutListener;

    public RepleaseGoodsListAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_replease_goods);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {

        final GoodsDto entity = getItemData(position);
        GlideUtils.loadImage(mContext,entity.getThumbnailUrl(),holder.ivGoodsImage);
        holder.tvGoodsName.setText(entity.getName());
        holder.tvGoodsPrice.setText(entity.getPrice()+"");
        if (!TextUtils.isEmpty(entity.getSubtitle())){
            holder.tvGoodsSubTitle.setVisibility(View.VISIBLE);
            holder.tvGoodsSubTitle.setText(entity.getSubtitle());
        } else {
            holder.tvGoodsSubTitle.setVisibility(View.GONE);
        }



        holder.tvSoldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSoldOutListener != null){
                    onClickSoldOutListener.clickSoldOut(entity,position,holder.getmSwiperLayout());
                }
            }
        });

        //'状态0审核中1审核通过2已下架3审核拒绝',
        int status = entity.getStatus();
        switch (status){
            case 0://审核中
                holder.tvGoodsStatus.setText("审核中");
                holder.getmSwiperLayout().setSwipeEnabled(false);
                break;
            case 1://审核通过
                holder.tvGoodsStatus.setText("审核通过");
                holder.getmSwiperLayout().setSwipeEnabled(true);
                break;
            case 2://已下架
                holder.tvGoodsStatus.setText("已下架");
                holder.getmSwiperLayout().setSwipeEnabled(false);
                break;
            case 3://审核拒绝
                holder.tvGoodsStatus.setText("审核拒绝");
                holder.getmSwiperLayout().setSwipeEnabled(false);
                break;

            default:
                holder.tvGoodsStatus.setText("");
                break;
        }
    }

    class ViewHolder extends BaseSwipeViewHolder{

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_sub_title)
        TextView tvGoodsSubTitle;

        @BindView(R.id.tv_goods_status)
        TextView tvGoodsStatus;

        //下架
        @BindView(R.id.tv_sold_out)
        TextView tvSoldOut;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 下架 按钮点击监听
     */
    public interface onClickSoldOutListener{
        void clickSoldOut(GoodsDto entity, int position, SwipeLayout mSwipeLayout);
    }

    public void addOnClickSoldOutListener(onClickSoldOutListener listener){
        this.onClickSoldOutListener = listener;
    }
}
