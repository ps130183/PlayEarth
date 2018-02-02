package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.GoodsEvluateAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.mvp.model.GoodsEvaluateModel;
import com.km.rmbank.mvp.presenter.GoodsEvaluatePresenter;
import com.km.rmbank.mvp.view.IGoodsEvaluateView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.List;

import butterknife.BindView;

public class GoodsEvaluateFragment extends BaseFragment<IGoodsEvaluateView,GoodsEvaluatePresenter> implements IGoodsEvaluateView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_all_evaluate)
    TextView tvAllEvaluate;

    private GoodsDetailsDto goodsDetailsDto;
    public static GoodsEvaluateFragment newInstance(Bundle bundle) {
        GoodsEvaluateFragment fragment = new GoodsEvaluateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected GoodsEvaluatePresenter createPresenter() {
        return new GoodsEvaluatePresenter(new GoodsEvaluateModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_evaluate;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        goodsDetailsDto = getArguments().getParcelable("goodsDetailsDto");
        tvAllEvaluate.setText("全部("+ goodsDetailsDto.getCommentNum() +")");
        initRecyclerview();
    }



    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final GoodsEvluateAdapter adapter = new GoodsEvluateAdapter(getContext());
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getUserEvaluate(goodsDetailsDto.getProductNo(),adapter.getNextPage());
            }
        });
        getPresenter().getUserEvaluate(goodsDetailsDto.getProductNo(),adapter.getNextPage());
    }

    @Override
    public void showUserEvaluate(List<EvaluateDto> evaluateDtos, int pageNo) {
        GoodsEvluateAdapter adapter = (GoodsEvluateAdapter) mRecyclerview.getAdapter();
        adapter.addData(evaluateDtos,pageNo);
    }

    @Override
    public void evaluateSuccess() {

    }


}
