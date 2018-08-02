package com.km.rmbank.mvp.presenter;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.dto.ContactDto;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.greendao.ContactManager;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.base.MvpModel;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.view.ContractsView;
import com.km.rmbank.utils.ContractUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/4/2.
 */

public class ContractsPresenter extends BasePresenter<ContractsView,ContactsModel> {

    public ContractsPresenter(ContactsModel mModel) {
        super(mModel);
    }

    public void getContracts(final LoadMoreWrapper wrapper, final int pageNo){
        ContactManager.getInstance().getAllContact()
                .subscribe(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contacts) throws Exception {
                        getMvpView().showContracts(wrapper,contacts);
                    }
                });
    }

    public void getContactsPayOrder(final List<ContactDto> contactDtos){
        getMvpView().showLoading();
        getMvpModel().getContactsPayOrder(contactDtos)
                .subscribe(new Consumer<PayOrderContactDto>() {
                    @Override
                    public void accept(PayOrderContactDto s) throws Exception {
                        getMvpView().hideLoading();
                        getMvpView().showPayContactOrder(s);
                    }
                });
    }

}
