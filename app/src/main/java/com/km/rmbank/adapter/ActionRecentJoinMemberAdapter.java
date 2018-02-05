package com.km.rmbank.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.km.rmbank.R;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.ps.glidelib.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/7/8.
 */

public class ActionRecentJoinMemberAdapter extends BaseAdapter<ActionMemberDto> implements BaseAdapter.IAdapter<ActionRecentJoinMemberAdapter.ViewHolder> {

    private boolean isMyClub;

    public ActionRecentJoinMemberAdapter(Context mContext, boolean isMyClub) {
        super(mContext, R.layout.item_rv_action_recent_member);
        setiAdapter(this);
        this.isMyClub = isMyClub;
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        ActionMemberDto actionMemberDto = getItemData(position);
        if (TextUtils.isEmpty(actionMemberDto.getHeadImage())){
            GlideUtils.loadImage(mContext, R.mipmap.icon_default_protrait,holder.ivProtrait);
        } else {
            GlideUtils.loadImage(mContext,actionMemberDto.getHeadImage(),holder.ivProtrait);
        }

        holder.tvMmeberName.setText(actionMemberDto.getRegistrationName());
        holder.tvMmeberPhone.setText(isMyClub ? actionMemberDto.getRegistrationPhone() : hidePhone(actionMemberDto.getRegistrationPhone()));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.iv_protrait)
        ImageView ivProtrait;
        @BindView(R.id.tv_member_name)
        TextView tvMmeberName;
        @BindView(R.id.tv_member_phone)
        TextView tvMmeberPhone;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 隐藏 手机号 中间4位
     * @param phone
     * @return
     */
    public static String hidePhone(String phone){
        if (TextUtils.isEmpty(phone)){
            return "";
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
