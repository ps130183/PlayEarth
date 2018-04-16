package com.km.rmbank.mvp.presenter;

import android.view.View;

import com.km.rmbank.dto.ContractDto;
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
        getMvpModel().getAllContracts(contractDtoList)
                .observeOn(Schedulers.io())
                .map(new Function<List<ContractDto>, List<ContractDto>>() {
                    @Override
                    public List<ContractDto> apply(List<ContractDto> contractDtoList) throws Exception {
                        return orderByLetter(contractDtoList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<List<ContractDto>>() {
                    @Override
                    public void accept(List<ContractDto> contractDtoList) throws Exception {
                        List<ContractDto> contractDtos = new ArrayList<>();
                        List<ContractDto> linkManDtos = new ArrayList<>();

                        for (ContractDto contractDto : contractDtoList){
                            if ("0".equals(contractDto.getStatus())){//没绑定
                                contractDto.setChecked(true);
                                linkManDtos.add(contractDto);
                            } else {
                                contractDtos.add(contractDto);
                            }
                        }
                        getMvpView().showContracts(contractDtos,linkManDtos);
                    }
                }));
    }

    private static Comparator<ContractDto> comparator = new Comparator<ContractDto>() {
        @Override
        public int compare(ContractDto o1, ContractDto o2) {
            CharSequence p1 = o1.getPersonNamePinyin().subSequence(0, 1);
            CharSequence p2 = o2.getPersonNamePinyin().subSequence(0, 1);

            if (p1.charAt(0) > p2.charAt(0)) {
                return 1;
            } else if (p1.charAt(0) == p2.charAt(0)) {
                return 0;
            }
            return -1;
        }
    };

    /**
     * 根据汉字首字母排序
     *
     * @param contractDtoList
     * @return
     */
    private List<ContractDto> orderByLetter(List<ContractDto> contractDtoList) {
        //将人名转换为拼音
        for (ContractDto contractDto : contractDtoList) {
            String pinyin = ContractUtils.HanziToPinyin(contractDto.getNickName());
            contractDto.setPersonNamePinyin(pinyin);
        }
        Collections.sort(contractDtoList, comparator);

        return contractDtoList;
    }
}
