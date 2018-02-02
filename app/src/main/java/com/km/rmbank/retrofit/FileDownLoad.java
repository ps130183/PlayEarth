package com.km.rmbank.retrofit;

import com.km.rmbank.dto.AppVersionDto;
import com.km.rmbank.mvp.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/28.
 */

public class FileDownLoad extends BaseModel {

    private static FileDownLoad instance = new FileDownLoad();
    private FileDownLoad(){

    }

    public static FileDownLoad getInstance(){
        return instance;
    }

    /**
     * 检查版本号
     * @param curVersionCode
     * @return
     */
    public Observable<AppVersionDto> checkAppVersion(int curVersionCode){
        return getService().checkAppVersion(curVersionCode)
                .compose(this.<AppVersionDto>applySchedulers());
    }
}
