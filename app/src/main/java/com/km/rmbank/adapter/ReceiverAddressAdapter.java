package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ruffian.library.RTextView;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressAdapter extends BaseAdapter<ReceiverAddressDto> implements BaseAdapter.IAdapter<ReceiverAddressAdapter.ViewHolder> {

    private onSetDefaultListener onSetDefaultListener;
    private onAddressChangeListener onAddressChangeListener;

    //选择收货地址
    private boolean selectOtherAddress;

    public ReceiverAddressAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_receiver_address);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, int position) {
        final ReceiverAddressDto receiverAddressDto = getItemData(position);
        if (selectOtherAddress){
            holder.cbDefault.setVisibility(View.GONE);
        } else {
            holder.cbDefault.setChecked(receiverAddressDto.isDefault() == 1);
            holder.cbDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSetDefaultListener != null){
                        onSetDefaultListener.setDefault(receiverAddressDto);
                    }
                }
            });

            holder.editAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressChangeListener != null){
                        onAddressChangeListener.editReceiverAddress(receiverAddressDto);
                    }
                }
            });

            holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddressChangeListener != null){
                        DialogUtils.showDefaultAlertDialog("确定删除收货地址？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                onAddressChangeListener.deleteReceiverAddress(receiverAddressDto);
                            }
                        });
                    }
                }
            });
        }
        holder.tvReceiverName.setText(receiverAddressDto.getReceivePerson());
        holder.tvReceiverAddress.setText(receiverAddressDto.getReceiveAddress());


    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_receiver_name)
        TextView tvReceiverName;

        @BindView(R.id.tv_receiver_address)
        TextView tvReceiverAddress;

        @BindView(R.id.cb_default)
        CheckBox cbDefault;

        @BindView(R.id.editAddress)
        RTextView editAddress;
        @BindView(R.id.deleteAddress)
        RTextView deleteAddress;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void setSelectOtherAddress(boolean selectOtherAddress) {
        this.selectOtherAddress = selectOtherAddress;
    }

    public interface onSetDefaultListener{
        void setDefault(ReceiverAddressDto receiverAddressDto);
    }

    public void setOnSetDefaultListener(ReceiverAddressAdapter.onSetDefaultListener onSetDefaultListener) {
        this.onSetDefaultListener = onSetDefaultListener;
    }

    public interface onAddressChangeListener {
        void deleteReceiverAddress(ReceiverAddressDto receiverAddressDto);
        void editReceiverAddress(ReceiverAddressDto receiverAddressDto);
    }



    public void setOnAddressChangeListener(onAddressChangeListener onAddressChangeListener) {
        this.onAddressChangeListener = onAddressChangeListener;
    }
}
