package com.km.rmbank.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.CheckBox;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.oldrecycler.BaseAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/5/18.
 */

public class GoodsTypeRmAdaapter extends BaseAdapter<HomeGoodsTypeDto> implements BaseAdapter.IAdapter<GoodsTypeRmAdaapter.ViewHolder> {

    private OnGoodsTypeCheckedListener onGoodsTypeCheckedListener;

    public GoodsTypeRmAdaapter(Context mContext) {
        super(mContext, R.layout.item_rv_rmshop_goods_type);
        setiAdapter(this);
    }

    public GoodsTypeRmAdaapter(Context mContext, @LayoutRes int itemLayoutRes) {
        super(mContext, itemLayoutRes);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, final int position) {
        final HomeGoodsTypeDto entity = getItemData(position);
        holder.cbGoodsType.setText(entity.getProductTypeName());
        holder.cbGoodsType.setChecked(entity.isChecked());
        holder.cbGoodsType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGt(entity);
                if (entity.isChecked() && onGoodsTypeCheckedListener != null){
                    onGoodsTypeCheckedListener.checkedGoodsType(entity,position);
                }
            }
        });

    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.cb_goods_type)
        CheckBox cbGoodsType;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void checkGt(HomeGoodsTypeDto entity) {
        List<HomeGoodsTypeDto> goodsTypeEntities = getAllData();
        for (HomeGoodsTypeDto goodsTypeEntity : goodsTypeEntities) {
            if (entity.getProductTypeName().equals(goodsTypeEntity.getProductTypeName())) {
                goodsTypeEntity.setChecked(true);
            } else {
                goodsTypeEntity.setChecked(false);
            }
        }
        notifyDataChanged();
    }

    /**
     * 设置默认选中
     */
    public void setDefaultChecked(String id){
        int position = getPositionById(id);
        if (position < 0){
            return;
        }
        HomeGoodsTypeDto entity = getAllData().get(position);
        entity.setChecked(true);
        notifyDataChanged();
        if (entity.isChecked() && onGoodsTypeCheckedListener != null){//更新对应的二级分类
            onGoodsTypeCheckedListener.checkedGoodsType(entity,position);
        }
    }

    /**
     * 设置默认选中
     */
    public void setDefaultCheckedFirst(){
        if (getItemCount() == 0){
            return;
        }
        clearChecked();
        HomeGoodsTypeDto entity = getAllData().get(0);
        entity.setChecked(true);
        notifyDataChanged();
        if (entity.isChecked() && onGoodsTypeCheckedListener != null){//更新对应的二级分类
            onGoodsTypeCheckedListener.checkedGoodsType(entity,0);
        }
    }

    /**
     * 根据id获取相应位置的数据
     * @param id
     * @return
     */
    private int getPositionById(String id){
        int position = -1;
        List<HomeGoodsTypeDto> homeGoodsTypeDtos = getAllData();
        for (int i = 0; i < homeGoodsTypeDtos.size(); i++){
            if (id.equals(homeGoodsTypeDtos.get(i).getId())){
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 获取选中的类型
     * @return
     */
    public HomeGoodsTypeDto getChecked(){
        List<HomeGoodsTypeDto> goodsTypeDtos = getAllData();
        HomeGoodsTypeDto checkedGoods = null;
        for (HomeGoodsTypeDto goodsTypeDto : goodsTypeDtos){
            if (goodsTypeDto.isChecked()){
                checkedGoods = goodsTypeDto;
                break;
            }
        }
        return checkedGoods;
    }

    public void clearChecked(){
        List<HomeGoodsTypeDto> goodsTypeDtos = getAllData();
        for (HomeGoodsTypeDto goodsTypeDto : goodsTypeDtos){
            goodsTypeDto.setChecked(false);
        }
        notifyDataSetChanged();
    }


    public interface OnGoodsTypeCheckedListener{
        void checkedGoodsType(HomeGoodsTypeDto entity, int position);
    }

    public void setOnGoodsTypeCheckedListener(OnGoodsTypeCheckedListener onGoodsTypeCheckedListener) {
        this.onGoodsTypeCheckedListener = onGoodsTypeCheckedListener;
    }
}
