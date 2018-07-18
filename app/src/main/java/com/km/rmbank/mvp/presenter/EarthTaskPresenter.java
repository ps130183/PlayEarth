package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.EarthTaskDetailsDto;
import com.km.rmbank.dto.EarthTaskDto;
import com.km.rmbank.dto.TaskSignInDto;
import com.km.rmbank.entity.EarthTaskEntity;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.EarthTaskModel;
import com.km.rmbank.mvp.view.EarthTaskView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/7/12.
 */

public class EarthTaskPresenter extends BasePresenter<EarthTaskView,EarthTaskModel> {

    public EarthTaskPresenter(EarthTaskModel mModel) {
        super(mModel);
    }

    /**
     * 获取球票任务列表
     */
    public void getEarthTaskList(){
        getMvpModel().getEarthTaskList()
                .subscribe(new Consumer<List<EarthTaskDto>>() {
                    @Override
                    public void accept(List<EarthTaskDto> earthTaskEntities) throws Exception {
                        getMvpView().showEarthTaskList(earthTaskEntities);
                    }
                });
    }

    public void signIn(){
        getMvpModel().tastSignIn()
                .subscribe(new Consumer<TaskSignInDto>() {
                    @Override
                    public void accept(TaskSignInDto taskSignInDto) throws Exception {
                        getMvpView().signInResult(taskSignInDto);
                    }
                });
    }

    public void getEarthTaskDetailList(){
        getMvpModel().getTaskDetailList()
                .subscribe(new Consumer<List<EarthTaskDetailsDto>>() {
                    @Override
                    public void accept(List<EarthTaskDetailsDto> taskDetailsDtos) throws Exception {
                        getMvpView().showEarthTaskDetailList(taskDetailsDtos);
                    }
                });
    }
}
