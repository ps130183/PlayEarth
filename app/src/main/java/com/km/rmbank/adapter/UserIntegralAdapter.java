package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.oldrecycler.BaseAdapter;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class UserIntegralAdapter extends BaseAdapter<IntegralDetailsDto> implements BaseAdapter.IAdapter<UserIntegralAdapter.ViewHolder> {

    public UserIntegralAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_integral_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        IntegralDetailsDto integralDetailsDto = getItemData(position);
        holder.tvContent.setText(integralDetailsDto.getContent());
        holder.tvTime.setText(integralDetailsDto.getDate());
        String type =integralDetailsDto.getTradeDirection();
        switch (type){
            case "0":
            case "1":
                holder.tvMoney.setText("+"+integralDetailsDto.getAmount());
                break;
            case "2":
                holder.tvMoney.setText("-"+integralDetailsDto.getAmount());
                break;
        }
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
