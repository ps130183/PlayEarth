package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.BankDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/7/3.
 */

public interface AddBankCardView extends MvpView {
    void showBankList(List<BankDto> bankDtos);
    void saveBankCardSuccess();
}
