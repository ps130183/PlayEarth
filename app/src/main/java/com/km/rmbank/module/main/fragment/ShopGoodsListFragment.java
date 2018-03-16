package com.km.rmbank.module.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.mvp.model.ShopModel;
import com.km.rmbank.mvp.presenter.ShopPresenter;
import com.km.rmbank.mvp.view.IShopView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.RefreshUtils;

import java.util.List;

import butterknife.BindView;

public class ShopGoodsListFragment extends BaseFragment<IShopView,ShopPresenter> implements IShopView {

    @BindView(R.id.rv_goodslist)
    RecyclerView rvGoodsList;
    private String isInIndextActivity = "";
    private int orderBy = 0;
    private String roleId = "";

    private HomeGoodsTypeDto goodsTypeDto;

    public ShopGoodsListFragment() {
        // Required empty public constructor
    }

    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter(new ShopModel());
    }

    public static ShopGoodsListFragment newInstance(Bundle bundle) {
        ShopGoodsListFragment fragment = new ShopGoodsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop_goods_list;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        goodsTypeDto = getArguments().getParcelable("goodsType");
        if (goodsTypeDto != null){
            isInIndextActivity = goodsTypeDto.getId();
        }
        initGoodsList();
        initXRefresh();
    }

    private void initXRefresh(){
        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getGoodsList(1);
            }
        });
        showLoading();
    }


    public void initGoodsList() {
        RVUtils.setLinearLayoutManage(rvGoodsList, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGoodsList);
        final GoodsListShoppingAdapter adapter = new GoodsListShoppingAdapter(getContext());
        rvGoodsList.setAdapter(adapter);
        adapter.addLoadMore(rvGoodsList, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
//                Logger.d(GoodsFragment.this.tabname);
                getGoodsList(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {
            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo", itemData.getProductNo());
                startActivity(GoodsActivity.class, bundle);
            }

        });
        adapter.setOnClickShopCardListener(new GoodsListShoppingAdapter.OnClickShopCardListener() {
            @Override
            public void clickShopcard(GoodsDto goodsDto) {
                getPresenter().addShoppingCart(goodsDto.getProductNo(),"1");
            }
        });
    }

    private void getGoodsList(int PageNo){
        getPresenter().getGoodsList(PageNo,isInIndextActivity,orderBy,roleId);
    }

    @Override
    public void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos) {

    }

    @Override
    public void showGoodsList(int pageNo, List<GoodsDto> goodsDtos) {
        hideLoading();
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) rvGoodsList.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }

    @Override
    public void addShoppingCartSuccess() {
        showToast("已加入购物车");
    }
}
