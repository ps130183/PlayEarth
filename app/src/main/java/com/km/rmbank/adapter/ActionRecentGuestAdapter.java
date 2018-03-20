package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/8.
 */

public class ActionRecentGuestAdapter extends BaseAdapter<ActionDto.ActionGuestBean> implements BaseAdapter.IAdapter<ActionRecentGuestAdapter.ViewHolder> {

    public ActionRecentGuestAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_action_recent_guest);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionDto.ActionGuestBean bean = getItemData(position);
        GlideUtils.loadCircleImageByUrl(holder.ivGuestProtrait,bean.getAvatarUrl());
        holder.tvGuestIntro.setText(bean.getTitle());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_guest_protrait)
        GlideImageView ivGuestProtrait;
        @BindView(R.id.tv_guest_intro)
        TextView tvGuestIntro;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
