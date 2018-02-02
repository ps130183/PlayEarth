package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ShoppingCartParentListAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.model.ShoppingCartModel;
import com.km.rmbank.mvp.presenter.ShoppingCartPresenter;
import com.km.rmbank.mvp.view.IShoppingCartView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ShoppingCartActivity extends BaseActivity<IShoppingCartView,ShoppingCartPresenter> implements IShoppingCartView, ShoppingCartParentListAdapter.onCheckedAllListener, ShoppingCartParentListAdapter.OnSubItemClcikListener, ShoppingCartParentListAdapter.onUpdateGoodsCount, ShoppingCartParentListAdapter.OnSubDeleteGoodsListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ShoppingCartParentListAdapter adapter;

    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    private boolean isCheckAllTouch = false;

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected ShoppingCartPresenter createPresenter() {
        return new ShoppingCartPresenter(new ShoppingCartModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("购物车");
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new ShoppingCartParentListAdapter(this);
        adapter.setShoppingCart(true);
        adapter.setTotalMoney(tvTotalMoney);
        mRecyclerView.setAdapter(adapter);
        adapter.setCheckedAllListener(this);
        adapter.setOnSubItemClcikListener(this);
        adapter.setOnUpdateGoodsCount(this);
        adapter.setOnSubDeleteGoodsListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().loadShoppingCartDatas();
    }

    @Override
    public void showShoppingCartDatas(List<ShoppingCartDto> shoppingCartEntities) {
        adapter.addData(shoppingCartEntities);
    }

    @Override
    public void updateGoodsCountSuccess(GoodsDetailsDto goodsDto, String optiontType) {
        ShoppingCartParentListAdapter adapter = (ShoppingCartParentListAdapter) mRecyclerView.getAdapter();
        if ("1".equals(optiontType)){
            goodsDto.setProductInShopCarCount(goodsDto.getProductInShopCarCount()+1);
        } else {
            goodsDto.setProductInShopCarCount(goodsDto.getProductInShopCarCount()-1);
        }
        adapter.setTotalMoney();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void createOrderSuccess(List<ShoppingCartDto> shoppingCartDtos) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("checkedGoods", (ArrayList<? extends Parcelable>) shoppingCartDtos);
        startActivity(CreateOrderActivity.class,bundle);
    }

    @Override
    public void deleteSuccess(int positionOnParent, int positionOnSub) {
//        mPresenter.loadShoppingCartDatas();
        adapter.deleteGoodsSuccess(positionOnParent,positionOnSub);
    }


    @Override
    public void onCheckedAll(boolean isCheckedAll) {
        isCheckAllTouch = false;
        cbCheckAll.setChecked(isCheckedAll);
    }

    @OnCheckedChanged(R.id.cb_check_all)
    public void checkAll(CompoundButton buttonView, boolean isChecked){
        if (isCheckAllTouch){
            adapter.checkAll(isChecked);
        }
    }
    @OnTouch(R.id.cb_check_all)
    public boolean onCbCheckAllTouch(View v, MotionEvent event){
        isCheckAllTouch = true;
        return false;
    }

    @Override
    public void onSubItemClick(GoodsDetailsDto itemData, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("productNo",itemData.getProductNo());
        startActivity(GoodsActivity.class,bundle);
    }

    @OnClick(R.id.tv_payment)
    public void payMent(View view){
//        showToast("去付款");
        getPresenter().createOrder(adapter.getAllCheckedGoodsProductNo());
    }

    @Override
    public void updateGoodsCount(GoodsDetailsDto productNo, String optionType) {
        getPresenter().updateGoodsCount(productNo,optionType);
    }

    @Override
    public void deleteGoods(GoodsDetailsDto goodsDetailsDto,int positionOnParent, int positionOnSub) {
        getPresenter().deleteGoods(goodsDetailsDto,positionOnParent,positionOnSub);
    }


}
