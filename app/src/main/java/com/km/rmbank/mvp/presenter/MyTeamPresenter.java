package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.mvp.view.IMyTeamView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/1/24.
 */

public class MyTeamPresenter extends BasePresenter<IMyTeamView,MyTeamModel> {

    public MyTeamPresenter(MyTeamModel mModel) {
        super(mModel);
    }

    public void getMyTeamData() {
        getMvpView().showLoading();
        getMvpModel().getMyTeam()
                .subscribe(newSubscriber(new Consumer<List<MyTeamDto>>() {
                    @Override
                    public void accept(List<MyTeamDto> teamDtoList) throws Exception {
                        getMvpView().showMyTeam(teamDtoList);
                    }
                }));

    }

    public void getUserCardById(String userId){
        getMvpView().showLoading();
        getMvpModel().getUserCardById(userId)
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userCardDto) throws Exception {
                        getMvpView().showUserCard(userCardDto);
                    }
                }));
    }

    public void getContracts(List<ContractDto> contractDtoList){
        getMvpView().showLoading();
        getMvpModel().getAllContracts(contractDtoList)
                .observeOn(Schedulers.io())
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
}
