package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/24.
 */

public interface INewGoodsView extends MvpView {
    void showImageUploadResult(int photoType,String photoUrl);
    void createNewGoodsSuccess();
    void showImageUploadingProgress(int photoType,int progress,int position);
    void showGoodsInfo(GoodsDetailsDto goodsDetailsDto);
}
