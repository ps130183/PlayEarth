package com.km.rmbank.module.main.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.presenter.MessagePresenter;
import com.km.rmbank.mvp.view.IMessageView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ruffian.library.RTextView;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<IMessageView,MessagePresenter> implements IMessageView {

    @BindView(R.id.recyclerview)
    MRecyclerView<MessageDto> mRecyclerview;


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

        mRecyclerview.addContentLayout(R.layout.item_rv_home_message, new ItemViewConvert<MessageDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, MessageDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.tv_message_title,mData.getTitle());
                holder.setText(R.id.tv_message_time,mData.getFormatCreateDate());
                holder.setText(R.id.tv_message_content,mData.getContent());

                RTextView messageType = holder.findView(R.id.messageType);
                messageType.setText(mData.getHeader());

                //1账户消息  2、3推送消息  4活动消息
                if (mData.getContentType() == 1){//账户
                    messageType.setText("账户");
                    messageType.setBackgroundColorNormal(Color.parseColor("#ffa250"));
                } else if (mData.getContentType() == 2 || mData.getContentType() == 3){//推送
                    messageType.setBackgroundColorNormal(Color.parseColor("#72a9ff"));
                } else if (mData.getContentType() == 4){//活动
                    messageType.setBackgroundColorNormal(Color.parseColor("#f7bd00"));
                }
            }

        }).create();
        mRecyclerview.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getMessage(nextPage);
            }
        });
        getPresenter().getMessage(1);
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {
        mRecyclerview.loadDataOfNextPage(messageDtos);
    }


}
