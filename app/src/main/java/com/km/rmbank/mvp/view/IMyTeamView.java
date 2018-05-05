package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/24.
 */

public interface IMyTeamView extends MvpView {
    void showMyTeam(List<MyTeamDto> teamEntities);
    void showUserCard(UserInfoDto cardDto);

    void showContracts(List<ContractDto> contractDtos, List<ContractDto> linkManDtos);
}
