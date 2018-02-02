package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.oldrecycler.BaseAdapter;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawAccountAdapter extends BaseAdapter<WithDrawAccountDto> implements BaseAdapter.IAdapter<WithDrawAccountAdapter.ViewHolder> {

    private OnDeleteWithdrawListener onDeleteWithdrawListener;
    private OnEditWithdrawListener onEditWithdrawListener;

    public WithDrawAccountAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_withdraw_account);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final WithDrawAccountDto withDrawAccountDto = getItemData(position);
        holder.tvUserName.setText(withDrawAccountDto.getName());
        holder.tvUserPhone.setText(withDrawAccountDto.getWithdrawPhone());
        holder.tvTypeName.setText(withDrawAccountDto.getTypeName());
        holder.tvAccount.setText(withDrawAccountDto.getWithdrawNumber());

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditWithdrawListener != null){
                    onEditWithdrawListener.editWithdraw(withDrawAccountDto);
                }
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteWithdrawListener != null){
                    onDeleteWithdrawListener.deleteWithdraw(withDrawAccountDto);
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_user_phone)
        TextView tvUserPhone;
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_account)
        TextView tvAccount;

        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_edit)
        TextView tvEdit;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnDeleteWithdrawListener{
        void deleteWithdraw(WithDrawAccountDto withDrawAccountDto);
    }

    public interface OnEditWithdrawListener{
        void editWithdraw(WithDrawAccountDto withDrawAccountDto);
    }

    public void setOnDeleteWithdrawListener(OnDeleteWithdrawListener onDeleteWithdrawListener) {
        this.onDeleteWithdrawListener = onDeleteWithdrawListener;
    }

    public void setOnEditWithdrawListener(OnEditWithdrawListener onEditWithdrawListener) {
        this.onEditWithdrawListener = onEditWithdrawListener;
    }
}
