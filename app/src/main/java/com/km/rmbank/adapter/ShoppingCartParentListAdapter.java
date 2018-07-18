package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.dialog.DialogUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTouch;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartParentListAdapter extends BaseAdapter<ShoppingCartDto> implements BaseAdapter.IAdapter<ShoppingCartParentListAdapter.ViewHolder>, BaseAdapter.ItemClickListener<GoodsDetailsDto> {

    private boolean isShoppingCart;
    private boolean isShowFreight;//是否显示运费  默认不显示

    private TextView totalMoney;

    private boolean isParentCheckChange;
    private onCheckedAllListener checkedAllListener;
    private OnSubItemClcikListener onSubItemClcikListener;
    private onUpdateGoodsCount onUpdateGoodsCount;
    private OnSubDeleteGoodsListener onSubDeleteGoodsListener;

    public ShoppingCartParentListAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_shopping_cart_list);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {
        final ShoppingCartDto cartEntity = getItemData(position);

        holder.tvShopName.setText(cartEntity.getShopName());

        holder.tvFreight.setText("¥ " + cartEntity.getFreight() + " 元");

        holder.subListAdapter.addData(cartEntity.getProductList());
//        holder.subListAdapter.setParentListAdapter(this,position);
        holder.subListAdapter.setOnSubCheckedListener(new ShoppingCartSubListAdapter.OnSubCheckedListener() {
            @Override
            public void onSubChecked(boolean isChecked) {
                checkParentBySub(position);
                setTotalMoney();
            }
        });
        holder.subListAdapter.setItemClickListener(this);

        holder.mCheckBox.setChecked(cartEntity.isChecked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartEntity.setChecked(isChecked);//修改父级的选中状态
                //修改子级选择状态
                checkAllSubByParent(cartEntity.getProductList(), isChecked, holder);
                //检测是否全选
                checkAll();
                setTotalMoney();
            }
        });

        holder.subListAdapter.setOnUpdateCountForGoods(new ShoppingCartSubListAdapter.onUpdateCountForGoods() {
            @Override
            public void updateCountOfGoods(GoodsDetailsDto productNo, String optionType) {
                if (onUpdateGoodsCount != null) {
                    onUpdateGoodsCount.updateGoodsCount(productNo, optionType);
                }
            }
        });

        holder.subListAdapter.setOnDeleteGoodsListener(new ShoppingCartSubListAdapter.OnDeleteGoodsListener() {
            @Override
            public void deleteGoods(final GoodsDetailsDto goodsDetailsDto, final int positionOnSub) {
                if (onSubDeleteGoodsListener != null){
                    DialogUtils.showDefaultAlertDialog("是否要删除该商品？", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            onSubDeleteGoodsListener.deleteGoods(goodsDetailsDto,position,positionOnSub);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(GoodsDetailsDto itemData, int position) {
        if (onSubItemClcikListener != null) {
            onSubItemClcikListener.onSubItemClick(itemData, position);
        }
    }

    public void setTotalMoney(TextView totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setTotalMoney(){
        if (totalMoney != null){
            totalMoney.setText(getTotalMoneyOnCheckChange());
        }
    }

    /**
     * 删除购物车商品 成功后
     * @param positionOnParent
     * @param positionOnSub
     */
    public void deleteGoodsSuccess(int positionOnParent, int positionOnSub){
        ShoppingCartDto shoppingCartDto = getItemData(positionOnParent);
        List<GoodsDetailsDto> goodsDetailsDtos = shoppingCartDto.getProductList();
        if (goodsDetailsDtos.size() > positionOnSub){
            goodsDetailsDtos.remove(positionOnSub);
        }
        if (goodsDetailsDtos.size() == 0){
            removeData(shoppingCartDto);
        }
        notifyDataSetChanged();
    }

    /**
     * 是否是购物车 还是 创建订单
     *
     * @param shoppingCart
     */
    public void setShoppingCart(boolean shoppingCart) {
        isShoppingCart = shoppingCart;
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_freight)
        TextView tvFreight;

        @BindView(R.id.recyclerview)
        RecyclerView rvGoods;
        ShoppingCartSubListAdapter subListAdapter;

        @BindView(R.id.rl_freight)
        RelativeLayout rlFreight;

        public ViewHolder(View itemView) {
            super(itemView);
            initRvGoods();
            mCheckBox.setVisibility(isShoppingCart ? View.VISIBLE : View.GONE);
            rlFreight.setVisibility(isShowFreight ? View.VISIBLE : View.GONE);
        }

        private void initRvGoods() {
            RVUtils.setLinearLayoutManage(rvGoods, LinearLayoutManager.VERTICAL);
            subListAdapter = new ShoppingCartSubListAdapter(mContext);
            subListAdapter.setShoppingCart(isShoppingCart);
            rvGoods.setAdapter(subListAdapter);
        }

        @OnTouch(R.id.checkbox)
        public boolean checkBox(View v, MotionEvent event) {
            isParentCheckChange = true;
            return false;
        }
    }

    /**
     * 修改子级的选中状态
     *
     * @param goodsEntities
     * @param isChecked
     * @param holder
     */
    private void checkAllSubByParent(List<GoodsDetailsDto> goodsEntities, boolean isChecked, ViewHolder holder) {
//        Logger.d("checkAllSubByParent isParentCheckChange = " + isParentCheckChange );
        if (isParentCheckChange) {
            for (GoodsDetailsDto entity : goodsEntities) {
                entity.setChecked(isChecked);
            }
            holder.subListAdapter.notifyDataSetChanged();
        } else {
            isParentCheckChange = false;
        }

    }

    /**
     * 通过选择商品 来确定 商家是否被选中
     *
     * @param position
     */
    public void checkParentBySub(final int position) {
        ShoppingCartDto cartEntity = getItemData(position);
        cartEntity.setChecked(isSubAllChecked(cartEntity.getProductList()));
        isParentCheckChange = false;
        checkAll();
        AppUtils.executeOnUIThread(0, new AppUtils.UIThread() {
            @Override
            public void onUIThread() {
                notifyItemChanged(position);
            }
        });
    }

    /**
     * 某一商家的商品 是否全部选中
     *
     * @param goodsEntities
     * @return
     */
    public boolean isSubAllChecked(List<GoodsDetailsDto> goodsEntities) {
        boolean isChecked = true;
        for (GoodsDetailsDto entity : goodsEntities) {
            if (!entity.isChecked()) {
                isChecked = false;
                break;
            }
        }
        return isChecked;
    }

    /**
     * 检测父级 商家 是否全部选中
     *
     * @return
     */
    private boolean isParentAllChecked() {
        boolean isChecked = true;
        for (ShoppingCartDto entity : getAllData()) {
            if (!entity.isChecked()) {
                isChecked = false;
                break;
            }
        }
        return isChecked;
    }

    private void checkAll() {
        if (checkedAllListener != null) {
//            Logger.d("isParentAllChecked = " + isParentAllChecked());
            checkedAllListener.onCheckedAll(isParentAllChecked());
        }

    }

    /**
     * 选中所有
     */
    public void checkAll(boolean isCheckAll) {
        for (ShoppingCartDto entity : getAllData()) {
            entity.setChecked(isCheckAll);
            for (GoodsDetailsDto entity1 : entity.getProductList()) {
                entity1.setChecked(isCheckAll);
            }
        }
        AppUtils.executeOnUIThread(0, new AppUtils.UIThread() {
            @Override
            public void onUIThread() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 是否全部选中监听
     */
    public interface onCheckedAllListener {
        void onCheckedAll(boolean isCheckedAll);
    }

    public void setCheckedAllListener(onCheckedAllListener checkedAllListener) {
        this.checkedAllListener = checkedAllListener;
    }

    public void setShowFreight(boolean showFreight) {
        isShowFreight = showFreight;
    }

    /**
     * 子级商品点击事件
     */
    public interface OnSubItemClcikListener {
        void onSubItemClick(GoodsDetailsDto itemData, int position);
    }

    public void setOnSubItemClcikListener(OnSubItemClcikListener onSubItemClcikListener) {
        this.onSubItemClcikListener = onSubItemClcikListener;
    }

    public interface onUpdateGoodsCount {
        void updateGoodsCount(GoodsDetailsDto productNo, String optionType);
    }

    public void setOnUpdateGoodsCount(ShoppingCartParentListAdapter.onUpdateGoodsCount onUpdateGoodsCount) {
        this.onUpdateGoodsCount = onUpdateGoodsCount;
    }

    public interface OnSubDeleteGoodsListener{
        void deleteGoods(GoodsDetailsDto goodsDetailsDto, int positionOnParent, int positionOnSub);
    }

    public void setOnSubDeleteGoodsListener(OnSubDeleteGoodsListener onSubDeleteGoodsListener) {
        this.onSubDeleteGoodsListener = onSubDeleteGoodsListener;
    }

    /**
     * 获取选中的商品
     *
     * @return
     */
    public List<ShoppingCartDto> getAllCheckedGoods() {
        List<ShoppingCartDto> shoppingCartDtos = new ArrayList<>();
        for (ShoppingCartDto entity : getAllData()) {
            List<GoodsDetailsDto> GoodsDetailsDtos = new ArrayList<>();
            for (com.km.rmbank.dto.GoodsDetailsDto GoodsDetailsDto : entity.getProductList()) {
                if (GoodsDetailsDto.isChecked()) {
                    GoodsDetailsDtos.add(GoodsDetailsDto);
                }
            }
            if (GoodsDetailsDtos.size() > 0) {
                ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
                shoppingCartDto.setShopName(entity.getShopName());
                shoppingCartDto.setProductList(GoodsDetailsDtos);
                shoppingCartDtos.add(shoppingCartDto);
            }
        }
        return shoppingCartDtos;
    }

    /**
     * 获取选中的商品 的商品编号 # 分割
     *
     * @return
     */
    public String getAllCheckedGoodsProductNo() {
        StringBuffer productNos = new StringBuffer();
        for (ShoppingCartDto entity : getAllData()) {
            for (com.km.rmbank.dto.GoodsDetailsDto GoodsDetailsDto : entity.getProductList()) {
                if (GoodsDetailsDto.isChecked()) {
                    productNos.append(GoodsDetailsDto.getProductNo()).append("#");
                }
            }
        }
        if (productNos.length() > 1){
            productNos.replace(productNos.length() - 1, productNos.length(), "");
        }
        return productNos.toString();
    }

    /**
     * 获取所有被选中 商品的总金额  完善订单页面 用
     *
     * @return
     */
    public String getTotalMoneyOnCreateOrder() {
        BigDecimal amount = new BigDecimal("0");
        for (ShoppingCartDto entity : getAllData()) {
            for (com.km.rmbank.dto.GoodsDetailsDto GoodsDetailsDto : entity.getProductList()) {
                BigDecimal price = new BigDecimal(GoodsDetailsDto.getPrice());
                amount = amount.add(price.multiply(new BigDecimal(GoodsDetailsDto.getProductInShopCarCount())));
            }
            amount = amount.add(new BigDecimal(entity.getFreight()));
        }
        return String.valueOf(amount.doubleValue());
    }

    /**
     * 获取所有选中商品的总金额
     * @return
     */
    private String getTotalMoneyOnCheckChange() {
        BigDecimal amount = new BigDecimal("0");
        for (ShoppingCartDto entity : getAllData()) {
            for (com.km.rmbank.dto.GoodsDetailsDto GoodsDetailsDto : entity.getProductList()) {
                if (GoodsDetailsDto.isChecked()){
                    BigDecimal price = new BigDecimal(GoodsDetailsDto.getPrice());
                    amount = amount.add(price.multiply(new BigDecimal(GoodsDetailsDto.getProductInShopCarCount())));
                }
            }
        }
        return String.valueOf(amount.doubleValue());
    }

}
