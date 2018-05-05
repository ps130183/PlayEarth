package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.animator.ShowViewAnimator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class MyTeamParentAdapter extends BaseAdapter<MyTeamDto> implements BaseAdapter.IAdapter<MyTeamParentAdapter.ViewHolder> {

    private onClickUserListener onClickUserListener;

    public MyTeamParentAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_team_parent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MyTeamDto teamEntity = getItemData(position);
        holder.tvTeamName.setText(teamEntity.getRoleName());

        holder.teamMemberAdapter.addData(teamEntity.getMemberDtoList());
        holder.teamMemberAdapter.setItemClickListener(new ItemClickListener<MyTeamDto.MemberDtoListBean>() {

            @Override
            public void onItemClick(MyTeamDto.MemberDtoListBean itemData, int position) {
                if (onClickUserListener != null){
                    onClickUserListener.clickUser(itemData,position);
                }
            }
        });
        holder.tvMemberNumber.setText(teamEntity.getNum()+"");
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;

        @BindView(R.id.rv_member)
        RecyclerView rvMember;
        TeamMemberAdapter teamMemberAdapter;

        @BindView(R.id.rl_team)
        RelativeLayout rlTeam;
        @BindView(R.id.tv_member_number)
        TextView tvMemberNumber;
        private ShowViewAnimator mAnimator;

        public ViewHolder(View itemView) {
            super(itemView);
            initMember();
        }

        private void initMember(){
            RVUtils.setLinearLayoutManage(rvMember, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvMember,RVUtils.DIVIDER_COLOR_DEFAULT,1);
            teamMemberAdapter = new TeamMemberAdapter(mContext);
            rvMember.setAdapter(teamMemberAdapter);
            rvMember.setVisibility(View.GONE);
            mAnimator = new ShowViewAnimator();
        }

        @OnClick({R.id.rl_team, R.id.tv_team_name, R.id.tv_member_number})
        public void clickTeam(View view){
//            rvMember.setVisibility(rvMember.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            mAnimator.showViewByAnimator(rvMember,null);
        }
    }

    public interface onClickUserListener{
        void clickUser(MyTeamDto.MemberDtoListBean itemData, int position);
    }

    public void setOnClickUserListener(MyTeamParentAdapter.onClickUserListener onClickUserListener) {
        this.onClickUserListener = onClickUserListener;
    }

    /**
     * 返回团队 人数 规模
     * @return
     */
    public int getMemberCount(){
        int count  = 0;
        for (MyTeamDto myTeamDto : getAllData()){
            count+=myTeamDto.getNum();
        }
        return count;
    }

}
