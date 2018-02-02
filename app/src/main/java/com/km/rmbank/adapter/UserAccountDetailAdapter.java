package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.oldrecycler.BaseAdapter;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailAdapter extends BaseAdapter<UserAccountDetailDto> implements BaseAdapter.IAdapter<UserAccountDetailAdapter.ViewHolder> {

    public UserAccountDetailAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_account_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        UserAccountDetailDto userAccountDetailDto = getItemData(position);
        holder.tvExplain.setText(userAccountDetailDto.getExplain());
        holder.tvMoney.setText(userAccountDetailDto.getAmount());
        holder.tvTime.setText(userAccountDetailDto.getCreateDate());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_explain)
        TextView tvExplain;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
