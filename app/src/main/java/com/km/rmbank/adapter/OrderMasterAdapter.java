package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by PengSong on 17/10/17.
 */

public class OrderMasterAdapter extends BaseAdapter<MasterDto> implements BaseAdapter.IAdapter<OrderMasterAdapter.ViewHolder> {

    public OrderMasterAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_order_master);
        setiAdapter(this);
    }


    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MasterDto masterDto = getItemData(position);
        GlideUtils.loadImageOnPregress(holder.ivProtrait,masterDto.getPortraitUrl(),null);
        holder.tvMasterTitle.setText(masterDto.getHeadings());
        holder.tvMasterName.setText(masterDto.getName());
        holder.tvMasterHint.setText(masterDto.getIntroduce());
        holder.tvPersonNumber.setText(masterDto.getNum() + "人拜访过");
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_protrait)
        GlideImageView ivProtrait;
        @BindView(R.id.tv_master_title)
        TextView tvMasterTitle;
        @BindView(R.id.tv_master_name)
        TextView tvMasterName;
        @BindView(R.id.tv_master_hint)
        TextView tvMasterHint;
        @BindView(R.id.tv_person_number)
        TextView tvPersonNumber;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
