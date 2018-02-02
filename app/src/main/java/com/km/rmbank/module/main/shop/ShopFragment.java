package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsListShoppingAdapter;
import com.km.rmbank.adapter.GoodsTypeRmAdaapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.mvp.model.ShopModel;
import com.km.rmbank.mvp.presenter.ShopPresenter;
import com.km.rmbank.mvp.view.IShopView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.km.rmbank.utils.animator.ShowViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class ShopFragment extends BaseFragment<IShopView,ShopPresenter> implements IShopView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_sub_title)
    LinearLayout llSubTitle;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
//
    @BindView(R.id.rv_gt1)
RecyclerView rvGt1;
    private String[] gt1Content = {"全部分类","手机","数码","服装鞋帽","交通工具","母婴产品","家用电器","家居用品","海鲜产品","奥特曼"};

    @BindView(R.id.rv_gt2)
    RecyclerView rvGt2;


    @BindView(R.id.iv_goods_type)
    ImageView ivGoodsType;
    @BindView(R.id.iv_sort)
    ImageView ivSort;
    @BindView(R.id.iv_vip)
    ImageView ivVip;

    @BindView(R.id.iv_more)
    ImageView ivMore;

    @BindView(R.id.tv_goods_type)
    TextView tvGoodsType;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_all_goods)
    TextView tvVip;

    @BindView(R.id.fl_select_goods_type)
    FrameLayout flSelectGoodsType;
    @BindView(R.id.ll_selec_goods_type)
    LinearLayout llSelectGoodsType;
    private ShowViewAnimator mSelectGoodsAnimator;

    @BindView(R.id.rv_select_sort)
    RecyclerView rvSelectSort;
    private String[] sorts = {"默认排序","最新发布","价格最低","价格最高","信誉最好"};
    private ShowViewAnimator mSortAnimator;

    @BindView(R.id.ll_select_vip)
    LinearLayout llSelectVip;
    private ShowViewAnimator mSelectVip;

    @BindView(R.id.rv_goodslist)
    RecyclerView rvGoodsList;

    @BindView(R.id.cb_vip1)
    CheckBox cbVip1;
    @BindView(R.id.cb_vip2)
    CheckBox cbVip2;

//    @BindView(R.id.swiper_refresh)
//    SwipeRefreshLayout mSwipeRefresh;

    private String isInIndextActivity = "";
    private int orderBy = 0;
    private String roleId = "";

    private boolean isFromHome = false;//来自首页
    private boolean isLevelOne = false;//是否是第一级
    private String levelOneId = "";//一级分类 Id
    private String levelTwoId = "";//二级分类Id

    private TopRightMenu mTopRightMenu;



    public static ShopFragment newInstance(Bundle bundle) {
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop;
    }

    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter(new ShopModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        getPresenter().getGodosTypes();
        Bundle bundle = getArguments();
//        if (bundle != null){
//            isFromHome =bundle.getBoolean("isFromHome",false);
//            isLevelOne = bundle.getBoolean("isLevelOne",false);
//            levelOneId = bundle.getString("levelOneId");
//            levelTwoId = bundle.getString("levelTwoId");
//        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initToolbarRight();

        mSelectGoodsAnimator = new ShowViewAnimator();
        mSortAnimator = new ShowViewAnimator();
        mSelectVip = new ShowViewAnimator();
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int toolbarHeihgt = toolbar.getLayoutParams().height;

                if (toolbarHeihgt + verticalOffset < toolbarHeihgt / 2){
                    llSubTitle.getLayoutParams().height = AppUtils.dip2px(getContext(),63f);
                    llSubTitle.setPadding(0, AppUtils.dip2px(getContext(),15f),0,0);
                } else if(toolbarHeihgt + verticalOffset > toolbarHeihgt / 2){
                    llSubTitle.getLayoutParams().height = AppUtils.dip2px(getContext(),48f);
                    llSubTitle.setPadding(0, 0,0,0);
                }
//                Logger.d("verticalOffset = " + verticalOffset + "  toolbar Height = " + toolbar.getLayoutParams().height);
            }
        });
//        initRvGt1();
        initSelectSort();
        initGoodsList();
        initTriangleOrientation();
        initSwipeRefresh();

        getPresenter().getGodosTypes();
    }

    @OnClick(R.id.iv_more)
    public void moreClick(View view){
        if (mTopRightMenu != null){
            mTopRightMenu.showAsDropDown(ivMore,-230,0);
        }
    }

    private void initSwipeRefresh(){
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshUtils.OnRereshListener() {
            @Override
            public void onRefresh() {
                getGoodsList(1);
            }
        });
    }

    /**
     * 初始化 右上角弹出框
     */
    private void initToolbarRight(){
        mTopRightMenu = new TopRightMenu(getActivity());

//添加菜单项
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.mipmap.ic_message_gray, "消息"));
        menuItems.add(new MenuItem(R.mipmap.ic_shopping_cart_32, "购物车"));

        mTopRightMenu
                .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
//                .addMenuItem(new MenuItem(R.mipmap.facetoface, "面对面快传"))
//                .addMenuItem(new MenuItem(R.mipmap.pay, "付款"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position){
                            case 0:
                                startActivity(MessageActivity.class);
                                break;
                            case 1:
                                startActivity(ShoppingCartActivity.class);
                                break;
                        }
                    }
                });
//                .showAsDropDown(ivMore, -225, 0);    //带偏移量
    }

    /**
     * 初始化 选择商品类型
     */
    private void initRvGt1(List<HomeGoodsTypeDto> homeGoodsTypeDtos){
        int windowHeight = AppUtils.getCurWindowHeight(getContext());
        rvGt1.getLayoutParams().height = windowHeight / 5 * 3;
        rvGt2.getLayoutParams().height = windowHeight / 5 * 3;

        //一级分类
        RVUtils.setLinearLayoutManage(rvGt1, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGt1,0xffeeeeee,2);
        final GoodsTypeRmAdaapter gt1Aadapter = new GoodsTypeRmAdaapter(getContext());
        rvGt1.setAdapter(gt1Aadapter);
        gt1Aadapter.addData(homeGoodsTypeDtos);

        //二级分类
        RVUtils.setLinearLayoutManage(rvGt2, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGt2,0xffeeeeee,2);
        final GoodsTypeRmAdaapter gt2Adapter = new GoodsTypeRmAdaapter(getContext());
        gt2Adapter.setmExistEmptyView(false);
        rvGt2.setAdapter(gt2Adapter);

        gt1Aadapter.setOnGoodsTypeCheckedListener(new GoodsTypeRmAdaapter.OnGoodsTypeCheckedListener() {
            @Override
            public void checkedGoodsType(HomeGoodsTypeDto entity, int position) {
                gt2Adapter.addData(entity.getTypeList());
            }
        });

        gt2Adapter.setOnGoodsTypeCheckedListener(new GoodsTypeRmAdaapter.OnGoodsTypeCheckedListener() {
            @Override
            public void checkedGoodsType(HomeGoodsTypeDto entity, int position) {
                isInIndextActivity = entity.getId();
                getGoodsList(1);
                cancelSelect();
                if (position == 0){
                    tvGoodsType.setText(gt1Aadapter.getChecked().getProductTypeName());
                } else {
                    tvGoodsType.setText(entity.getProductTypeName());
                }
            }
        });

        if (isFromHome){
            gt1Aadapter.setDefaultChecked(levelOneId);
            if (isLevelOne){//查询 某一级分类所有
                gt2Adapter.setDefaultCheckedFirst();
            } else {//查询某一级分类 对应的二级分类
                gt2Adapter.setDefaultChecked(levelTwoId);
            }
//            gt1Aadapter.setDefaultChecked();
//            gt2Adapter.setDefaultChecked();
            isInIndextActivity = gt2Adapter.getChecked().getId();
        } else {
            isInIndextActivity = "";
        }

        getGoodsList(1);
    }

    /**
     * 初始化 选择 排序
     */
    private void initSelectSort(){
        RVUtils.setLinearLayoutManage(rvSelectSort, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvSelectSort,0xffeeeeee,2);
        GoodsTypeRmAdaapter adaapter = new GoodsTypeRmAdaapter(getContext(), R.layout.item_rv_rmshop_sort);
        rvSelectSort.setAdapter(adaapter);

        List<HomeGoodsTypeDto> goodsTypeEntities = new ArrayList<>();
        for (int i = 0; i < sorts.length; i++){
            goodsTypeEntities.add(new HomeGoodsTypeDto(sorts[i]));
        }
        adaapter.addData(goodsTypeEntities);
        adaapter.setDefaultCheckedFirst();
        adaapter.setOnGoodsTypeCheckedListener(new GoodsTypeRmAdaapter.OnGoodsTypeCheckedListener() {
            @Override
            public void checkedGoodsType(HomeGoodsTypeDto entity, int position) {
                orderBy = position;
                getGoodsList(1);
                cancelSelect();
                tvSort.setText(entity.getProductTypeName());
            }
        });
    }

    @OnClick(R.id.fl_select_goods_type)
    public void cancelSelect(View view){
        cancelSelect();
    }

    /**
     * 关闭选择框
     */
    private void cancelSelect(){
        flSelectGoodsType.setVisibility(View.GONE);
        if (llSelectGoodsType.getVisibility() == View.VISIBLE){
            mSelectGoodsAnimator.showViewByAnimator(llSelectGoodsType,null);
            rotrationGoodsType();
        }
        if (rvSelectSort.getVisibility() == View.VISIBLE){
            mSortAnimator.showViewByAnimator(rvSelectSort,null);
            rotrationSort();
        }
        if (llSelectVip.getVisibility() == View.VISIBLE){
            mSelectVip.showViewByAnimator(llSelectVip,null);
            rotrationVip();
        }
    }

    /**
     * 初始化 选择 分类 排序 发布人  三角的方向
     */
    private void initTriangleOrientation(){
        ivGoodsType.setRotation(180f);
        ivSort.setRotation(180f);
        ivVip.setRotation(180f);
    }

    private void rotrationGoodsType(){
        float curGtRotration = ivGoodsType.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivGoodsType,"rotation",curGtRotration,targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    private void rotrationSort(){
        float curGtRotration = ivSort.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivSort,"rotation",curGtRotration,targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    private void rotrationVip(){
        float curGtRotration = ivVip.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivVip,"rotation",curGtRotration,targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    @OnClick({R.id.tv_goods_type, R.id.ll_goods_type, R.id.iv_goods_type})
    public void selectGoodsType(View view){
        if (llSelectGoodsType.getVisibility() == View.GONE){
            flSelectGoodsType.setVisibility(View.VISIBLE);
            if (rvSelectSort.getVisibility() == View.VISIBLE){
                mSortAnimator.showViewByAnimator(rvSelectSort,null);
                rotrationSort();
            }
            if (llSelectVip.getVisibility() == View.VISIBLE){
                mSelectVip.showViewByAnimator(llSelectVip,null);
                rotrationVip();
            }
        } else {
            flSelectGoodsType.setVisibility(View.GONE);
        }
        mSelectGoodsAnimator.showViewByAnimator(llSelectGoodsType,null);
        rotrationGoodsType();
    }

    @OnClick({R.id.tv_sort, R.id.ll_sort, R.id.iv_sort})
    public void selectGoodsSort(View view){
        if (rvSelectSort.getVisibility() == View.GONE){
            flSelectGoodsType.setVisibility(View.VISIBLE);
            if (llSelectGoodsType.getVisibility() == View.VISIBLE){
                mSelectGoodsAnimator.showViewByAnimator(llSelectGoodsType,null);
                rotrationGoodsType();
            }
            if (llSelectVip.getVisibility() == View.VISIBLE){
                mSelectVip.showViewByAnimator(llSelectVip,null);
                rotrationVip();
            }
        } else {
            flSelectGoodsType.setVisibility(View.GONE);
        }
        mSortAnimator.showViewByAnimator(rvSelectSort,null);
        rotrationSort();
    }

    @OnClick({R.id.tv_all_goods, R.id.ll_all_goods, R.id.iv_vip})
    public void selectVip(View view){

        GoodsTypeRmAdaapter adaapter1 = (GoodsTypeRmAdaapter) rvGt1.getAdapter();
        GoodsTypeRmAdaapter adaapter2 = (GoodsTypeRmAdaapter) rvGt2.getAdapter();
        adaapter1.clearChecked();
        adaapter2.clearAllData();

        tvGoodsType.setText("选择分类");
        tvSort.setText("默认排序");

        GoodsTypeRmAdaapter adaapterSort = (GoodsTypeRmAdaapter) rvSelectSort.getAdapter();
        adaapterSort.setDefaultCheckedFirst();

        isInIndextActivity = "";
        orderBy = 0;
        roleId = "";
        getPresenter().getGoodsList(1,isInIndextActivity,orderBy,roleId);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view){
        if (cbVip1.isChecked() && cbVip2.isChecked()){
            roleId = "";
        } else if (cbVip1.isChecked()){
            roleId = "2";
        } else if (cbVip2.isChecked()){
            roleId = "3";
        } else {
            roleId = "";
        }
        getGoodsList(1);
        cancelSelect();
    }

    @OnClick(R.id.btn_reset)
    public void reset(View view){
        cbVip1.setChecked(false);
        cbVip2.setChecked(false);
    }


    @OnClick({R.id.rl_search, R.id.tv_search})
    public void search(View view){
        startActivity(SearchActivity.class);
    }

    @Override
    public void getGoodsTypeSuccess(List<GoodsTypeDto> goodsTypeDtos) {

    }

    @Override
    public void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos) {
        initRvGt1(goodsTypeDtos);
    }

    @Override
    public void showGoodsList(int pageNo, List<GoodsDto> goodsDtos) {
        GoodsListShoppingAdapter adapter = (GoodsListShoppingAdapter) rvGoodsList.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }


    private void getGoodsList(int PageNo){
        getPresenter().getGoodsList(PageNo,isInIndextActivity,orderBy,roleId);
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
                getPresenter().getGoodsList(adapter.getNextPage(),isInIndextActivity,orderBy,roleId);
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {
            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",itemData.getProductNo());
                startActivity(GoodsActivity.class,bundle);
            }

        });
    }
}
