package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.oldrecycler.BaseSwipeRvAdapter;
import com.ps.glidelib.GlideUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartSubListAdapter extends BaseSwipeRvAdapter<GoodsDetailsDto> implements BaseSwipeRvAdapter.IAdapter<ShoppingCartSubListAdapter.ViewHolder> {

    private boolean isShoppingCart;

    private ShoppingCartParentListAdapter parentListAdapter;
    private int positionOnParent;
    private OnSubCheckedListener onSubCheckedListener;
    private onUpdateCountForGoods onUpdateCountForGoods;
    private OnDeleteGoodsListener onDeleteGoodsListener;

    public ShoppingCartSubListAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_shopping_cart_sub_list);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, final int position) {
        final GoodsDetailsDto entity = getItemData(position);

        GlideUtils.loadImage(mContext,entity.getThumbnailUrl(),holder.ivGoodsImage);
        holder.tvGoodsName.setText(entity.getName());
        holder.tvGoodsCount.setText(""+entity.getProductInShopCarCount());

        BigDecimal price = new BigDecimal(entity.getPrice()+"");

        holder.tvTotalMoney.setText(""+price.multiply(new BigDecimal(entity.getProductInShopCarCount()+"")).doubleValue());

        holder.mCheckBox.setChecked(entity.isChecked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setChecked(isChecked);
//                parentListAdapter.checkParentBySub(positionOnParent,isChecked);
                onSubCheckedListener.onSubChecked(isChecked);
            }
        });

        holder.etBuyGoodsCount.setText(""+entity.getProductInShopCarCount());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUpdateCountForGoods != null){
                    onUpdateCountForGoods.updateCountOfGoods(entity,"1");
                }
            }
        });
        holder.btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUpdateCountForGoods != null){
                    onUpdateCountForGoods.updateCountOfGoods(entity,"2");
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteGoodsListener != null){
                    onDeleteGoodsListener.deleteGoods(entity,position);
                }
            }
        });

    }

    class ViewHolder extends BaseSwipeViewHolder{

        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_total_money)
        TextView tvTotalMoney;

        @BindView(R.id.ll_add_cut)
        LinearLayout llAddCut;

        @BindView(R.id.et_buy_goods_count)
        EditText etBuyGoodsCount;
        @BindView(R.id.btn_add)
        Button btnAdd;
        @BindView(R.id.btn_cut)
        Button btnCut;

        @BindView(R.id.tv_delete)
        TextView tvDelete;
        public ViewHolder(View itemView) {
            super(itemView);

            mCheckBox.setVisibility(isShoppingCart ? View.VISIBLE: View.GONE);
            llAddCut.setVisibility(isShoppingCart ? View.VISIBLE: View.GONE);
        }

        @OnClick(R.id.checkbox)
        public void checkBox(View view){
//            parentListAdapter.setSubClick(true);
        }
    }

    /**
     * 是否是购物车
     * @param shoppingCart
     */
    public void setShoppingCart(boolean shoppingCart) {
        isShoppingCart = shoppingCart;
    }
    //    public void setParentListAdapter(ShoppingCartParentListAdapter parentListAdapter,int positionOnParent) {
//        this.parentListAdapter = parentListAdapter;
//        this.positionOnParent = positionOnParent;
//    }

    interface OnSubCheckedListener{
        void onSubChecked(boolean isChecked);
    }

    public void setOnSubCheckedListener(OnSubCheckedListener onSubCheckedListener) {
        this.onSubCheckedListener = onSubCheckedListener;
    }

    public interface onUpdateCountForGoods{
        void updateCountOfGoods(GoodsDetailsDto goodsDto, String optionType);
    }

    public void setOnUpdateCountForGoods(ShoppingCartSubListAdapter.onUpdateCountForGoods onUpdateCountForGoods) {
        this.onUpdateCountForGoods = onUpdateCountForGoods;
    }

    public interface OnDeleteGoodsListener{
        void deleteGoods(GoodsDetailsDto goodsDetailsDto, int positionOnSub);
    }

    public void setOnDeleteGoodsListener(OnDeleteGoodsListener onDeleteGoodsListener) {
        this.onDeleteGoodsListener = onDeleteGoodsListener;
    }
}
