package com.km.rmbank.mvp.presenter;

import android.view.View;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ContactsModel;
import com.km.rmbank.mvp.view.ContractsView;
import com.km.rmbank.utils.ContractUtils;

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

    public void getContracts(List<ContractDto> contractDtoList){
//        getMvpModel().getAllContracts(contractDtoList)
//                .observeOn(Schedulers.io())
//                .map(new Function<List<ContractDto>, List<ContractDto>>() {
//                    @Override
//                    public List<ContractDto> apply(List<ContractDto> contractDtoList) throws Exception {
//                        return orderByLetter(contractDtoList);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(newSubscriber(new Consumer<List<ContractDto>>() {
//                    @Override
//                    public void accept(List<ContractDto> contractDtoList) throws Exception {
//                        List<ContractDto> contractDtos = new ArrayList<>();
//                        List<ContractDto> linkManDtos = new ArrayList<>();
//
//                        for (ContractDto contractDto : contractDtoList){
//                            if ("0".equals(contractDto.getStatus())){//没绑定
//                                contractDto.setChecked(true);
//                                linkManDtos.add(contractDto);
//                            } else {
//                                contractDtos.add(contractDto);
//                            }
//                        }
//                        getMvpView().showContracts(contractDtos,linkManDtos);
//                    }
//                }));
    }

    public void getContactsPayOrder(final List<String> phones){
        getMvpView().showLoading();
        getMvpModel().getContactsPayOrder(phones)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(PayOrderDto payOrderDto) throws Exception {
                        getMvpView().showPayOrder(payOrderDto,phones.size());
                    }
                }));
    }

}
