package com.km.rmbank.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class TeamMemberAdapter extends BaseAdapter<MyTeamDto.MemberDtoListBean> implements BaseAdapter.IAdapter<TeamMemberAdapter.ViewHolder> {

    public TeamMemberAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_team_sub);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MyTeamDto.MemberDtoListBean userEntity = getItemData(position);
        holder.tvUserNickName.setText(userEntity.getName());
        GlideUtils.loadImageOnPregress(holder.ivUserPortrait,userEntity.getPortraitUrl(),null);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_user_portrait)
        GlideImageView ivUserPortrait;
        @BindView(R.id.tv_user_nick_name)
        TextView tvUserNickName;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
