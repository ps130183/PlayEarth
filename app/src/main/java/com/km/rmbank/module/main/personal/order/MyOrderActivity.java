package com.km.rmbank.module.main.personal.order;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyOrderAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.OrderModel;
import com.km.rmbank.mvp.presenter.OrderPresenter;
import com.km.rmbank.mvp.view.IOrderView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends BaseActivity<IOrderView,OrderPresenter> implements IOrderView {

    @BindView(R.id.orderRecycler)
    RecyclerView mRecyclerView;
    private String orderStatus = "";

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        String title = "";
        int orderType = getIntent().getIntExtra("orderType",0);
        orderStatus = "" + orderType;
        switch (orderType){
            case 0:
                title = "全部订单";
                orderStatus = "";
                break;
            case 1:
                title = "待付款";
                break;
            case 2:
                title = "待发货";
                break;
            case 3:
                title = "待收货";
                break;
            case 4:
                title = "已完成";
                break;
        }

        simpleTitleBar.setTitleContent(title);
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter(new OrderModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final MyOrderAdapter adapter = new MyOrderAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                if (adapter.getNextPage() > 1 ){
                    getPresenter().loadOrder(adapter.getNextPage(),orderStatus);
                }
            }
        });
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<OrderDto>() {
            @Override
            public void onItemClick(OrderDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderDto",itemData);
                bundle.putBoolean("isUser",true);
                startActivity(OrderDetailsActivity.class,bundle);
            }

        });

        adapter.setOnClickBtnActionListener(new MyOrderAdapter.onClickBtnActionListener() {
            @Override
            public void clickBtnAction(OrderDto orderDto,int status) {
                switch (status){
                    case 1://去付款
                        getPresenter().getPayOrder(orderDto);
                        break;
                    case 2:
                }
            }
        });

        getPresenter().loadOrder(1,orderStatus);
    }

    @Override
    public void showOrderList(List<OrderDto> orderEntities, int page) {
        MyOrderAdapter adapter = (MyOrderAdapter) mRecyclerView.getAdapter();
        adapter.addData(orderEntities,page);
    }

    @Override
    public void getPayOrderSuccess(PayOrderDto payOrderDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        startActivity(PaymentActivity.class,bundle);
    }
}
