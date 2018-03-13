package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.adapter.GoodsTypeRmAdaapter;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.module.main.shop.SearchActivity;
import com.km.rmbank.module.main.shop.ShoppingCartActivity;
import com.km.rmbank.mvp.model.ShopModel;
import com.km.rmbank.mvp.presenter.ShopPresenter;
import com.km.rmbank.mvp.view.IShopView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.km.rmbank.utils.animator.ShowViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeShopFragment extends BaseFragment<IShopView,ShopPresenter> implements IShopView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    private String[] titles = {"全部分类","手机","数码","服装鞋帽","交通工具","母婴产品","家用电器","家居用品","海鲜产品","奥特曼"};

    @BindView(R.id.iv_more)
    ImageView ivMore;

//    @BindView(R.id.rv_goodslist)
//    RecyclerView rvGoodsList;

    @BindView(R.id.goodsTypes)
    SlidingTabLayout goodsTypesTab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    public static HomeShopFragment newInstance(Bundle bundle) {
        HomeShopFragment fragment = new HomeShopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_shop;
    }

    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter(new ShopModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        getPresenter().getGodosTypes();
    }

    private void initViewPager(List<HomeGoodsTypeDto> homeGoodsTypeDtos){
        List<Fragment> fragmentList = new ArrayList<>();
        List<CustomTabEntity> titleList = new ArrayList<>();
        for (HomeGoodsTypeDto goodsTypeDto : homeGoodsTypeDtos){
            Bundle bundle = new Bundle();
            bundle.putParcelable("goodsType",goodsTypeDto);
            fragmentList.add(ShopGoodsListFragment.newInstance(bundle));
            TabEntity tabEntity = new TabEntity(goodsTypeDto.getProductTypeName(),0,0);
            titleList.add(tabEntity);
        }
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(adapter);
        goodsTypesTab.setViewPager(viewPager);
    }

    /**
     * 打开购物车
     * @param view
     */
    @OnClick(R.id.iv_more)
    public void moreClick(View view){
        startActivity(ShoppingCartActivity.class);
    }


    /**
     * 初始化 选择商品类型
     */
    private void initRvGt1(){

    }

    @OnClick({R.id.rl_search, R.id.tv_search})
    public void search(View view){
        startActivity(SearchActivity.class);
    }


    @Override
    public void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos) {
        if (goodsTypeDtos == null || goodsTypeDtos.size() == 0){
            showToast("获取商品分类失败");
            return;
        }
        initViewPager(goodsTypeDtos);
    }

    @Override
    public void showGoodsList(int pageNo, List<GoodsDto> goodsDtos) {
//        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) rvGoodsList.getAdapter();
//        adapter.addData(goodsDtos,pageNo);
    }

    @Override
    public void addShoppingCartSuccess() {

    }

}

