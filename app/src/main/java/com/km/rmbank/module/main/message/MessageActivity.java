package com.km.rmbank.module.main.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.presenter.MessagePresenter;
import com.km.rmbank.mvp.view.IMessageView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ruffian.library.RTextView;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<IMessageView,MessagePresenter> implements IMessageView {

    @BindView(R.id.recyclerview)
    MRecyclerView<MessageDto> mRecyclerview;

    private String contentType;


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

        contentType = getIntent().getStringExtra("contentType");
        mRecyclerview.addContentLayout(R.layout.item_rv_home_message, new ItemViewConvert<MessageDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final MessageDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.tv_message_title,mData.getTitle());
                holder.setText(R.id.tv_message_time,mData.getFormatCreateDate());
                holder.setText(R.id.tv_message_content,mData.getContent());

                SwipeLayout swipeLayout = holder.findView(R.id.swipe_layout);
                if (swipeLayout != null) {
                    //set show mode.
                    swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                    swipeLayout.setSwipeEnabled(true);
                    swipeLayout.setClickToClose(true); //点击其他区域关闭侧滑
                }

                holder.findView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否删除这条消息?", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                getPresenter().deleteMessage(mData);
                            }
                        });

                    }
                });

                holder.findView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("messageDto",mData);
                        startActivity(MessageDetailsActivity.class,bundle);
                    }
                });
            }

        }).create();
        mRecyclerview.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getMessage(nextPage,contentType);
            }
        });

        getPresenter().getMessage(1,contentType);
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {
        if (pageNo == 1){
            mRecyclerview.clear();
        }
        mRecyclerview.loadDataOfNextPage(messageDtos);
    }

    @Override
    public void showMessageAl(MessageAllDto messageAllDto) {

    }

    @Override
    public void updateMessageResult() {

    }

    @Override
    public void deleteSuccess(MessageDto messageDto) {
        mRecyclerview.delete(messageDto);
    }


}
