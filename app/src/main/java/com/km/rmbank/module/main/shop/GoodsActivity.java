package com.km.rmbank.module.main.shop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.ConfirmGoodsNumberEvent;
import com.km.rmbank.event.GoodsDetailNumberEvent;
import com.km.rmbank.mvp.model.GoodsModel;
import com.km.rmbank.mvp.presenter.GoodsPresenter;
import com.km.rmbank.mvp.view.IGoodsView;
import com.km.rmbank.titleBar.GoodsToolBar;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.animator.ShowViewAnimator;
import com.ps.glidelib.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsActivity extends BaseActivity<IGoodsView,GoodsPresenter> implements IGoodsView {

    private String[] mTitle = {"商品", "详情", "评价"};
    private GoodsDetailsDto mGoodsDetails;
    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tv_follow)
    TextView tvFollow;

    private String productNo;

    private boolean isFollow;


    //以下是 选择商品的数量
    @BindView(R.id.rl_set_goods_number)
    RelativeLayout rlSetGoodsNumbenr;
    @BindView(R.id.ll_set_goods_number)
    LinearLayout llSetGoodsNumber;
    @BindView(R.id.et_buy_goods_count)
    EditText etBuyGoodsCount;

    @BindView(R.id.iv_goods_image)
    ImageView ivGoodsImage;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTilte;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_goods;
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter(new GoodsModel());
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return new GoodsToolBar();
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        GoodsToolBar goodsToolBar = (GoodsToolBar) titleBar;
        goodsToolBar.setOnClickBackListener(new GoodsToolBar.OnClickBackListener() {
            @Override
            public void clickBack() {
                finish();
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        productNo = getIntent().getStringExtra("productNo");
        getPresenter().getGoodsDetails(productNo);
    }


    @Override
    public void showGoodsDetails(GoodsDetailsDto goodsDetailsDto) {
        hideLoading();
        mGoodsDetails = goodsDetailsDto;

        //选择商品数量
        GlideUtils.loadImage(this,mGoodsDetails.getThumbnailUrl(),ivGoodsImage);
        tvGoodsTilte.setText(mGoodsDetails.getName());

        isFollow = "0".equals(goodsDetailsDto.getIsfollow()) ? false : true;
        initViewPager(goodsDetailsDto);
        initBottom();
        initShowGoodsNumber();
    }

    @Override
    public void followGoodsSuccess() {
        isFollow = !isFollow;
        setFollowGoods();
    }

    @Override
    public void addShoppingCartSuccess() {
        showToast("已加入购物车");
    }

//    @Override
//    public void showShopUserCartInfo(UserCardDto userCardDto) {
//        if (userCardDto.isEmpty2()){
//            showToast("该商家尚未编辑名片");
//            return;
//        }
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("userCardDto",userCardDto);
//        bundle.putString("friendPhone",userCardDto.getMobilePhone());
//        startActivity(UserCardInfoActivity.class,bundle);
//    }


    /**
     * 加载商品信息、详情和评价 三个fragment
     *
     * @param goodsDetailsDto
     */
    private void initViewPager(GoodsDetailsDto goodsDetailsDto) {

        List<CustomTabEntity> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(new TabEntity(title,0,0));
        }

        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goodsDetailsDto", goodsDetailsDto);
        fragments.add(GoodsInfoFragment.newInstance(bundle));//商品信息

        bundle = new Bundle();
        bundle.putStringArrayList("goodsDetails", (ArrayList<String>) goodsDetailsDto.getProductDetailList());
        fragments.add(GoodsDetailsFragment.newInstance(bundle));//商品详情

        bundle = new Bundle();
        bundle.putParcelable("goodsDetailsDto", goodsDetailsDto);
        fragments.add(GoodsEvaluateFragment.newInstance(bundle));//评价信息
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(this.getSupportFragmentManager(), fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }

    /**
     * 初始化 下部功能区 按钮
     */
    private void initBottom() {
        setFollowGoods();
    }

    /**
     * 设置改商品是否被关注
     */
    private void setFollowGoods() {
        Drawable top;
        if (isFollow) {
            top = getResources().getDrawable(R.mipmap.ic_followed_60);
            tvFollow.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        } else {
            top = getResources().getDrawable(R.mipmap.ic_unfollow_60);
        }
        tvFollow.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
    }

    @OnClick(R.id.tv_follow)
    public void followGoods(View view) {
        if (mGoodsDetails != null) {
            getPresenter().followGoods(mGoodsDetails.getProductNo());
        }
    }


    @OnClick(R.id.tv_add_shopping_cart)
    public void addShippingCart(View view){
        if (mGoodsDetails == null){
            return;
        }
        getPresenter().addShoppingCart(mGoodsDetails.getProductNo(),goodsCount+"");
    }

    @OnClick(R.id.tv_shopping_cart)
    public void toShoopinCart(View view){
        startActivity(ShoppingCartActivity.class);
    }

//    @OnClick(R.id.tv_shop_info)
//    public void shopInfo(View view){
////        if (true){
////            showToast("客服暂未开通");
////            return;
////        }
//        getPresenter().getShopUserCartInfo(mGoodsDetails.getShopId());
//    }

    /** ----------------- 以下是 选择商品数量 --------------------- */

    @OnClick(R.id.rl_set_goods_number)
    public void onRlTouch(View v){
        EventBusUtils.post(new GoodsDetailNumberEvent(goodsCount));
    }

    @OnClick(R.id.ll_set_goods_number)
    public void onllTouch(View v){

    }

    private ShowViewAnimator animator;
    private void initShowGoodsNumber(){
        animator = new ShowViewAnimator();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showGoodsNumber(GoodsDetailNumberEvent event){
        goodsCount = event.getNumber();
        addOrCutGoodsCount(0);
        if (rlSetGoodsNumbenr.getVisibility() == View.GONE){
            rlSetGoodsNumbenr.setVisibility(View.VISIBLE);
        }

        animator.showViewByAnimator(llSetGoodsNumber, new ShowViewAnimator.onHideListener() {
            @Override
            public void hide() {
                rlSetGoodsNumbenr.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void addNumber(View view){
        addOrCutGoodsCount(1);
    }

    @OnClick(R.id.btn_cut)
    public void  cutNumber(View view){
        addOrCutGoodsCount(-1);
    }

    @OnClick(R.id.btn_confirm)
    public void confirmNumber(View view){
        EventBusUtils.post(new GoodsDetailNumberEvent(goodsCount));
        EventBusUtils.post(new ConfirmGoodsNumberEvent(goodsCount));
    }

    private int goodsCount = 1;
    /**
     * 选择商品数量  加 或者 减
     *
     * @param count
     */
    private void addOrCutGoodsCount(int count) {

//        int stock = mGoodsDetails.getResidualStock();
        if (goodsCount > 0 ) {
            goodsCount += count;
        }
        etBuyGoodsCount.setText(goodsCount + "");
    }

    /** ----------------- 以上是 选择商品数量 --------------------- */

}
