package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.EarthTaskDetailsDto;
import com.km.rmbank.dto.EarthTaskDto;
import com.km.rmbank.dto.TaskSignInDto;
import com.km.rmbank.entity.EarthTaskEntity;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/7/12.
 */

public interface EarthTaskView extends MvpView {
    void showEarthTaskList(List<EarthTaskDto> earthTaskEntities);
    void signInResult(TaskSignInDto signInDto);
    void showEarthTaskDetailList(List<EarthTaskDetailsDto> taskDetailsDtos);
}
