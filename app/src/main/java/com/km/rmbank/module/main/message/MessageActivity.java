package com.km.rmbank.module.main.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MessageAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.presenter.MessagePresenter;
import com.km.rmbank.mvp.view.IMessageView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<IMessageView,MessagePresenter> implements IMessageView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_message;
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(new MessageModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("消息中心");
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecyclerview();
    }

    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final MessageAdapter adapter = new MessageAdapter(this);
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getMessage(adapter.getNextPage());
            }
        });
        getPresenter().getMessage(adapter.getNextPage());
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {
        MessageAdapter adapter = (MessageAdapter) mRecyclerview.getAdapter();
        adapter.addData(messageDtos,pageNo);
    }


}
