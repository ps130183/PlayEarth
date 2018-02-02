package com.km.rmbank.module.main.personal.member.goodsmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyOrderAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.module.main.personal.order.OrderDetailsActivity;
import com.km.rmbank.mvp.model.OrderManagerModel;
import com.km.rmbank.mvp.presenter.OrderManagerPresenter;
import com.km.rmbank.mvp.view.IOrderManagerView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;

import java.util.List;

import butterknife.BindView;

public class OrderManagerFragment extends BaseFragment<IOrderManagerView,OrderManagerPresenter> implements IOrderManagerView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    public static OrderManagerFragment newInstance(Bundle args) {
        OrderManagerFragment fragment = new OrderManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_manager;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    protected OrderManagerPresenter createPresenter() {
        return new OrderManagerPresenter(new OrderManagerModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getSellOrder(1);
    }

    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        final MyOrderAdapter adapter = new MyOrderAdapter(getContext());
        adapter.setUser(false);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getSellOrder(adapter.getNextPage());
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<OrderDto>() {
            @Override
            public void onItemClick(OrderDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderDto",itemData);
                startActivity(OrderDetailsActivity.class,bundle);
            }
        });
    }

    @Override
    public void getSellOrderSuccess(List<OrderDto> goodsDtos, int pageNo) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(goodsDtos,pageNo);
    }
}
