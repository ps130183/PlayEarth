package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IUserAccountView extends MvpView {
    void showAccountDetail(List<UserAccountDetailDto> userAccountDetailDtos, int curPage);
    void showBalance(UserBalanceDto userBalanceDto);
}
