package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.oldrecycler.BaseAdapter;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypeAdapter extends BaseAdapter<HomeGoodsTypeDto> implements BaseAdapter.IAdapter<GoodsTypeAdapter.ViewHolder> {


    public GoodsTypeAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_type);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, final int position) {
        final HomeGoodsTypeDto goodsTypeDto = getItemData(position);
        holder.cbGoodsType.setText(goodsTypeDto.getProductTypeName());
//        holder.cbGoodsType.setChecked(goodsTypeDto.isChecked());
        holder.cbGoodsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(goodsTypeDto);
            }
        });
        holder.cbGoodsType.setBackgroundResource(goodsTypeDto.getBackgroundRes());
        holder.ivCheckedGoodsType.setVisibility(goodsTypeDto.isChecked() ? View.VISIBLE : View.GONE);

        if (itemClickListener != null) {
            holder.cbGoodsType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(goodsTypeDto, position);
                }
            });
        }

    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.cb_goods_type)
        CheckBox cbGoodsType;
        @BindView(R.id.iv_checked_goods_type)
        ImageView ivCheckedGoodsType;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void setChecked(HomeGoodsTypeDto typeDto) {
        for (HomeGoodsTypeDto goodsTypeDto : getAllData()) {
            goodsTypeDto.setChecked(false);
        }
        typeDto.setChecked(true);
        notifyDataChanged();
    }

    /**
     * 返回当前选中的类型
     *
     * @return
     */
    public HomeGoodsTypeDto getCheckedGoodsType() {
        for (HomeGoodsTypeDto goodsTypeDto : getAllData()) {
            if (goodsTypeDto.isChecked()) {
                return goodsTypeDto;
            }
        }
        return new HomeGoodsTypeDto("");
    }

    public void setDefaultChecked(HomeGoodsTypeDto goodsTypeDto) {
        if (goodsTypeDto == null) {
            return;
        }
        for (HomeGoodsTypeDto typeDto : getAllData()) {
            if (typeDto.getProductTypeName().equals(goodsTypeDto.getProductTypeName())) {
                typeDto.setChecked(true);
                break;
            }
        }

        notifyDataChanged();
    }
}
