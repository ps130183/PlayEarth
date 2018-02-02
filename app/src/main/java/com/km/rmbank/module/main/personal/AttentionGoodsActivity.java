package com.km.rmbank.module.main.personal;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyAttentionAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.mvp.model.AttentionModel;
import com.km.rmbank.mvp.presenter.AttentionPresenter;
import com.km.rmbank.mvp.view.IAttentionView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;

public class AttentionGoodsActivity extends BaseActivity<IAttentionView,AttentionPresenter> implements IAttentionView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private MyAttentionAdapter adapter;

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
        return R.layout.activity_attention_goods;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initAttentionGoods();
    }

    public void initAttentionGoods() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        adapter = new MyAttentionAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getAttentionGoods(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {
            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                if (itemData.getType() == 1){
                    bundle.putString("productNo",itemData.getProductNo());
                    startActivity(GoodsActivity.class,bundle);
                } else {
                    getPresenter().getClubInfo(itemData.getProductNo());
                }


            }

        });
//        mPresenter.getAttentionGoods(adapter.getNextPage());
    }

    @Override
    public void getAttentionGoodsSuccess(List<GoodsDto> goodsDtos, int pageNo) {
        adapter.addData(goodsDtos,pageNo);
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }
}
