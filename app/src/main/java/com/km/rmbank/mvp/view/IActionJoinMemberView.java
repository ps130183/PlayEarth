package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/27.
 */

public interface IActionJoinMemberView extends MvpView {
    void showActionMemberList(List<ActionMemberDto> actionMemberDtos, int pageNo);
    void showActionMemberNum(String number);
}
