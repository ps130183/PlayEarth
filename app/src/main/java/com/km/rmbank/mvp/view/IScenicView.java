package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ScenicDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/2/11.
 */

public interface IScenicView extends MvpView {
    void showScenicInfo(ScenicDto scenicDto);

}
