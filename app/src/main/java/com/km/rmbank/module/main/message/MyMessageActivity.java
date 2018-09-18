package com.km.rmbank.module.main.message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.dto.NoticeListDto;
import com.km.rmbank.entity.MessageTypeEntity;
import com.km.rmbank.event.RefreshMessageEvent;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.presenter.MessagePresenter;
import com.km.rmbank.mvp.view.IMessageView;
import com.km.rmbank.utils.DateUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyMessageActivity extends BaseActivity<IMessageView, MessagePresenter> implements IMessageView {

    private String[] messageTypeNames = {"推送消息", "系统消息", "账户消息", "活动消息"};
    private int[] messageTypeImages = {R.mipmap.icon_message_push, R.mipmap.icon_message_system,
            R.mipmap.icon_message_account, R.mipmap.icon_message_action};

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_message;
    }

    @Override
    public String getTitleContent() {
        return "我的消息";
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(new MessageModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        MRecyclerView<NoticeListDto> mRecyclerView = mViewManager.findView(R.id.contentRecycler);
        mRecyclerView
                .setDividerWidth(1)
                .refreshRecycler()
                .addContentLayout(R.layout.item_message_type, new ItemViewConvert<NoticeListDto>() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, NoticeListDto mData, int position, @NonNull List<Object> payloads) {
                        holder.setText(R.id.message_type_name, mData.getHeader());

                        int i = 0;
                        switch (mData.getContentType()) {
                            case "1"://账户
                                i=2;
                                break;
                            case "2"://推送
                                i= 0;
                                break;
                            case "3"://系统
                                i=1;
                                break;
                            case "4"://活动
                                i=3;
                                break;

                        }
                        GlideImageView imageView = holder.findView(R.id.giv_message_type);
                        GlideUtils.loadCircleImageByRes(imageView, messageTypeImages[i]);


                        if (TextUtils.isEmpty(mData.getNoticeDto().getContent())){
                            holder.setText(R.id.message_time,"");
                            holder.setText(R.id.message_content,"");
                        } else {
                            holder.setText(R.id.message_time, DateUtils.getInstance().dateToString(new Date(mData.getNoticeDto().getCreateDate()), DateUtils.YMD_SLASH));
                            holder.setText(R.id.message_content, mData.getNoticeDto().getContent());
                        }


                        if (mData.getCount() > 0) {
                            holder.findView(R.id.isNew).setVisibility(View.VISIBLE);
                        } else {
                            holder.findView(R.id.isNew).setVisibility(View.GONE);
                        }
                    }
                }).create();

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                NoticeListDto noticeListDto = (NoticeListDto) mData;
                Bundle bundle = new Bundle();
                bundle.putString("contentType",noticeListDto.getContentType());
                startActivity(MessageActivity.class,bundle);
            }
        });

        getPresenter().getAllMessage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMessage(RefreshMessageEvent event){
        getPresenter().getAllMessage();
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {

    }

    @Override
    public void showMessageAl(MessageAllDto messageAllDto) {
        MRecyclerView<NoticeListDto> mRecyclerView = mViewManager.findView(R.id.contentRecycler);
        mRecyclerView.clear();
        mRecyclerView.loadDataOfNextPage(messageAllDto.getNoticeList());
    }

    @Override
    public void updateMessageResult() {

    }


    @Override
    public void deleteSuccess(MessageDto messageDto) {

    }
}
