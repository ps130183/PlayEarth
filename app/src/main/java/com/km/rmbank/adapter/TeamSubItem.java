package com.km.rmbank.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.MyTeamDto;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

/**
 * Created by PengSong on 18/5/10.
 */

public class TeamSubItem extends AbstractExpandableAdapterItem {

    private RelativeLayout rootView;
    private GlideImageView userPortrait;
    private TextView userName;
    private TextView userFrom;
    private TextView userPhone;

    private OnClickSubListener onClickSubListener;

    public TeamSubItem(OnClickSubListener onClickSubListener) {
        this.onClickSubListener = onClickSubListener;
    }

    @Override
    public void onExpansionToggled(boolean expanded) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_rv_my_team_sub;
    }

    @Override
    public void onBindViews(View root) {
        if (rootView == null)
            rootView = root.findViewById(R.id.rootView);
        if (userPortrait == null)
            userPortrait = root.findViewById(R.id.iv_user_portrait);
        if (userName == null)
            userName = root.findViewById(R.id.tv_user_nick_name);
        if (userFrom == null)
            userFrom = root.findViewById(R.id.from);
        if (userPhone == null)
            userPhone = root.findViewById(R.id.userPhone);
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(final Object model, int position) {
        super.onUpdateViews(model, position);
        if (model instanceof MyTeamDto.MemberDtoListBean){
            MyTeamDto.MemberDtoListBean memberDtoListBean = (MyTeamDto.MemberDtoListBean) model;
            GlideUtils.loadImageOnPregress(userPortrait,memberDtoListBean.getPortraitUrl(),null);
            userName.setText(memberDtoListBean.getName());
            userPhone.setText(memberDtoListBean.getMobilePhone());
            String fromSource;
            if ("0".equals(memberDtoListBean.getSource())){
                fromSource = "名片";
            } else if ("1".equals(memberDtoListBean.getSource())){
                fromSource = "手机通讯录";
            } else {
                fromSource = "其他";
            }
            userFrom.setText("来源：" + fromSource);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickSubListener != null){
                        onClickSubListener.onClick(model);
                    }
                }
            });
        }
    }

    public interface OnClickSubListener{
        void onClick(Object model);
    }
}
