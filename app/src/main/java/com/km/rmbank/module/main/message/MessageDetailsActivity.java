package com.km.rmbank.module.main.message;

import android.support.annotation.Nullable;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.event.RefreshMessageEvent;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.presenter.MessagePresenter;
import com.km.rmbank.mvp.view.IMessageView;
import com.km.rmbank.utils.EventBusUtils;

import java.util.List;

public class MessageDetailsActivity extends BaseActivity<IMessageView,MessagePresenter> implements IMessageView {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_message_details;
    }

    @Override
    public String getTitleContent() {
        return "消息详情";
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(new MessageModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        MessageDto messageDto = getIntent().getParcelableExtra("messageDto");
        mViewManager.setText(R.id.message_title,messageDto.getTitle());
        mViewManager.setText(R.id.message_time,messageDto.getFormatCreateDate());
        mViewManager.setText(R.id.message_content,messageDto.getContent());

        getPresenter().updateMessageStatus(messageDto.getId());
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {

    }

    @Override
    public void showMessageAl(MessageAllDto messageAllDto) {

    }

    @Override
    public void updateMessageResult() {
        EventBusUtils.post(new RefreshMessageEvent());
    }


    @Override
    public void deleteSuccess(MessageDto messageDto) {

    }

}
