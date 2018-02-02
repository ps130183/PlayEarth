package com.km.rmbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.ViewUtils;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class GoodsEvluateAdapter extends BaseAdapter<EvaluateDto> implements BaseAdapter.IAdapter<GoodsEvluateAdapter.ViewHolder> {

    public GoodsEvluateAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_evaluate);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        EvaluateDto evaluateDto = getItemData(position);
        holder.tvShopName.setText(evaluateDto.getUserName());
        holder.tvContent.setText(evaluateDto.getContent());
        holder.tvTime.setText(evaluateDto.getFormatCreateDate());
        GlideUtils.loadImage(mContext,evaluateDto.getPortraitUrl(),holder.ivProtrait);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class EmptyViewHolder extends BaseAdapter.EmptyViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    protected BaseAdapter<EvaluateDto>.EmptyViewHolder getEmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new EmptyViewHolder(ViewUtils.getView(inflater,parent, R.layout.rc_item_empty_evaluate));
    }
}
