package com.km.rmbank.module.main.personal.member.goodsmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.km.rmbank.R;
import com.km.rmbank.adapter.RepleaseGoodsListAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.model.ReleaseGoodsModel;
import com.km.rmbank.mvp.presenter.ReleaseGoodsPresenter;
import com.km.rmbank.mvp.view.IReleaseGoodsView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;

public class RepleaseGoodsListFragment extends BaseFragment<IReleaseGoodsView,ReleaseGoodsPresenter> implements  IReleaseGoodsView,RepleaseGoodsListAdapter.onClickSoldOutListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static RepleaseGoodsListFragment newInstance(Bundle bundle) {
        RepleaseGoodsListFragment fragment = new RepleaseGoodsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_replease_goods_list;
    }

    @Override
    protected ReleaseGoodsPresenter createPresenter() {
        return new ReleaseGoodsPresenter(new ReleaseGoodsModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().loadRepleaseGoods(1);
    }

    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview,RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
        final RepleaseGoodsListAdapter adapter = new RepleaseGoodsListAdapter(getContext());
        adapter.setMode(Attributes.Mode.Single);
        mRecyclerview.setAdapter(adapter);

        adapter.addOnClickSoldOutListener(this);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                if (adapter.getNextPage() > 1){
                    getPresenter().loadRepleaseGoods(adapter.getNextPage());
                }
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsDto>() {

            @Override
            public void onItemClick(GoodsDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("productNo",itemData.getProductNo());
                startActivity(CreateNewGoodsActivity.class,bundle);
            }
        });
    }

    @Override
    public void showRepleaseGoods(List<GoodsDto> goodsEntities, int pageNo) {
        RepleaseGoodsListAdapter adapter = (RepleaseGoodsListAdapter) mRecyclerview.getAdapter();
        adapter.addData(goodsEntities,pageNo);
    }

    @Override
    public void goodsSoldOutSuccess(GoodsDto goodsDto, SwipeLayout swipeLayout) {
//        swipeLayout.close(true);
        getPresenter().loadRepleaseGoods(1);
//        swipeLayout.setSwipeEnabled(false);
//        showToast("下架");
//        goodsDto.setStatus(2);
//        mRecyclerview.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void clickSoldOut(final GoodsDto entity, final int position, final SwipeLayout mSwipeLayout) {
        DialogUtils.showDefaultAlertDialog("是否要下架该产品？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
//                showToast("下架");
                mSwipeLayout.close(true);
                getPresenter().goodsSoldOut(entity,mSwipeLayout);
            }
        });
    }
}
