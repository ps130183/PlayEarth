package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IWithDrawView extends MvpView {
    void showBalance(UserBalanceDto userBalanceDto);
    void withdrawSuccess(String money);

    void creatOrUpdateSuccess();
    void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos);
    void deleteSuccess(WithDrawAccountDto withDrawAccountDto);
}
