package com.km.rmbank.mvp.presenter;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.utils.Constant;
import com.tencent.connect.UserInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/1/25.
 */

public class UserInfoPresenter extends BasePresenter<IUserInfoView, UserInfoModel> {

    public UserInfoPresenter(UserInfoModel mModel) {
        super(mModel);
    }

    public void saveUserInfo(final UserInfoDto userInfoDto) {
        getMvpView().showLoading();
        Observable.just(userInfoDto.getPortraitUrl().indexOf("http"))
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer) throws Exception {
                        return integer < 0;
                    }
                })
                //上传头像
                .flatMap(new Function<Boolean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Boolean aBoolean) throws Exception {
                        LogUtils.d(!aBoolean ? "不上传图片" : "上传图片");
                        return aBoolean ? getMvpModel().uploadUserPortrait("3", userInfoDto.getPortraitUrl()) : Observable.just("");
                    }
                })
                //保存用户信息
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        LogUtils.d("图片的内容---->" + s);
                        if (!TextUtils.isEmpty(s)){
                            userInfoDto.setPortraitUrl(s);
                        }
                        return getMvpModel().saveUserInfo(userInfoDto);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().saveUserInfoSuccess(userInfoDto);
                    }
                }));
    }
}
