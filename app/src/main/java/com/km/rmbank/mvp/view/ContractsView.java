package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/4/2.
 */

public interface ContractsView extends MvpView {
    void showContracts(LoadMoreWrapper wrapper,List<Contact> contacts);
    void showPayContactOrder(PayOrderContactDto payOrderContactDto);
}
