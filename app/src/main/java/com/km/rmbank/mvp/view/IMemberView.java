package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/27.
 */

public interface IMemberView extends MvpView {
    void showMemberList(List<MemberDto> memberDtos);
    void showPayOrderResult(PayOrderDto payOrderDto);
}
