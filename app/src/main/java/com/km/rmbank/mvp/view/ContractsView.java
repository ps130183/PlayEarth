package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/4/2.
 */

public interface ContractsView extends MvpView {
    void showContracts(List<ContractDto> contractDtos,List<ContractDto> linkManDtos);
}
