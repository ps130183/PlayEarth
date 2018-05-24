package com.km.rmbank.module.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.swipe.util.Attributes;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ReceiverAddressAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.event.OtherAddressEvent;
import com.km.rmbank.mvp.model.ReceiverAddressModel;
import com.km.rmbank.mvp.presenter.ReceiverAddressPresenter;
import com.km.rmbank.mvp.view.IReceiverAddressView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiverAddressActivity extends BaseActivity<IReceiverAddressView,ReceiverAddressPresenter> implements IReceiverAddressView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private boolean selecteOtherAddress;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_receiver_address;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("收货地址");

        simpleTitleBar.setRightMenuContent("添加");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateReceiverAddressActivity.class);
            }
        });
    }

    @Override
    protected ReceiverAddressPresenter createPresenter() {
        return new ReceiverAddressPresenter(new ReceiverAddressModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        selecteOtherAddress = getIntent().getBooleanExtra("select_other_address",false);
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().loadReceiverAddressData();
    }

    public void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        ReceiverAddressAdapter addressAdapter = new ReceiverAddressAdapter(this);
        addressAdapter.setSelectOtherAddress(selecteOtherAddress);
        mRecyclerView.setAdapter(addressAdapter);
        addressAdapter.setItemClickListener(new BaseAdapter.ItemClickListener<ReceiverAddressDto>() {
            @Override
            public void onItemClick(ReceiverAddressDto itemData, int position) {
                if (selecteOtherAddress){
                    EventBusUtils.post(new OtherAddressEvent(itemData));
                    finish();
                }
            }
        });

        //设置默认
        addressAdapter.setOnSetDefaultListener(new ReceiverAddressAdapter.onSetDefaultListener() {
            @Override
            public void setDefault(ReceiverAddressDto receiverAddressDto) {
                getPresenter().setDefaultReceiverAddress(receiverAddressDto);
            }
        });

        //删除
        addressAdapter.setOnAddressChangeListener(new ReceiverAddressAdapter.onAddressChangeListener() {
            @Override
            public void deleteReceiverAddress(ReceiverAddressDto receiverAddressDto) {
                getPresenter().deleteReceiverAddress(receiverAddressDto);
            }

            @Override
            public void editReceiverAddress(ReceiverAddressDto receiverAddressDto) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("receiverAddressDto",receiverAddressDto);
                startActivity(CreateReceiverAddressActivity.class,bundle);
            }
        });
    }

    @Override
    public void showReceiverAddressList(List<ReceiverAddressDto> receiverAddressDtos) {
        ReceiverAddressAdapter addressAdapter = (ReceiverAddressAdapter) mRecyclerView.getAdapter();
        addressAdapter.addData(receiverAddressDtos);
    }

    @Override
    public void setDefaultReceiverAddressSuccess() {
        getPresenter().loadReceiverAddressData();
    }

    @Override
    public void deleteReceiverSuccess(ReceiverAddressDto receiverAddressDto) {
        ReceiverAddressAdapter adapter = (ReceiverAddressAdapter) mRecyclerView.getAdapter();
        adapter.removeData(receiverAddressDto);
    }
}
