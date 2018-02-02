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
        GlideUtils.loadImage(mContext,userEntity.getPortraitUrl(),holder.ivUserPortrait);
        if (!TextUtils.isEmpty(userEntity.getReferrerPhone()) && !"0".equals(userEntity.getReferrerPhone())){
            holder.tvReferrerMan.setVisibility(View.VISIBLE);
            String referrerPhone = "由<font color='#0099cf'>" + userEntity.getReferrerPhone() +"</font>邀请";
            CharSequence csReferrer ;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                csReferrer = Html.fromHtml(referrerPhone, Html.FROM_HTML_MODE_LEGACY);
            } else {
                csReferrer = Html.fromHtml(referrerPhone);
            }
            holder.tvReferrerMan.setText(csReferrer);
        } else {
            holder.tvReferrerMan.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_user_portrait)
        ImageView ivUserPortrait;
        @BindView(R.id.tv_user_nick_name)
        TextView tvUserNickName;
        @BindView(R.id.tv_referrer_man)
        TextView tvReferrerMan;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
