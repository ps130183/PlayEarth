package com.km.rmbank.module.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.AttentionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.mvp.model.AttentionModel;
import com.km.rmbank.mvp.presenter.AttentionPresenter;
import com.km.rmbank.mvp.view.IAttentionView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.List;


public class MyAttentionActivity extends BaseActivity<IAttentionView,AttentionPresenter> implements IAttentionView {

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAttentionGoods(1);
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("我的关注");
    }

    @Override
    protected AttentionPresenter createPresenter() {
        return new AttentionPresenter(new AttentionModel());
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_attention;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initAttentionGoods();
    }

    public void initAttentionGoods() {

        MRecyclerView<AttentionDto> mRecyclerView = mViewManager.findView(R.id.attentionRecycler);
        mRecyclerView.addContentLayout(R.layout.item_rv_my_attention, new ItemViewConvert<AttentionDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, AttentionDto mData, int position, @NonNull List<Object> payloads) {
                if (mData.getType() == 1){//商品
//
                } else {//俱乐部
                    GlideImageView imageView = holder.findView(R.id.iv_goods);
                    GlideUtils.loadImageOnPregress(imageView,mData.getThumbnailUrl(),null);
                    holder.setText(R.id.tv_club_name,mData.getName());
                    holder.setText(R.id.tv_attention_number,"粉丝：" + (TextUtils.isEmpty(mData.getFans()) ? "0" : mData.getFans()));
                }
            }
        }).create();
        mRecyclerView.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAttentionGoods(nextPage);
            }
        });
        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AttentionDto itemData = (AttentionDto) mData;
                Bundle bundle = new Bundle();
                if (itemData.getType() == 1){
                    bundle.putString("productNo",itemData.getProductNo());
                    startActivity(GoodsActivity.class,bundle);
                } else {
                    getPresenter().getClubInfo(itemData.getProductNo());
                }
            }
        });
    }

    @Override
    public void getAttentionGoodsSuccess(List<AttentionDto> goodsDtos, int pageNo) {
        MRecyclerView<AttentionDto> mRecyclerView = mViewManager.findView(R.id.attentionRecycler);
        mRecyclerView.loadDataOfNextPage(goodsDtos);
    }

    @Override
    public void getAttentionGoodsSuccess1(List<GoodsDto> goodsDtos, int pageNo) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }
}
