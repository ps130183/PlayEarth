package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IMessageView extends MvpView {
    void showMessage(List<MessageDto> messageDtos, int pageNo);
    void showMessageAl(MessageAllDto messageAllDto);

    void updateMessageResult();
    void deleteSuccess(MessageDto messageDto);
}
