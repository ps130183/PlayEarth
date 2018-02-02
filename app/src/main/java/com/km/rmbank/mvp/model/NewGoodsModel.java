package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.retrofit.RetrofitManager;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.fileupload.UploadFileRequestBody;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by PengSong on 18/1/24.
 */

public class NewGoodsModel extends BaseModel {
    /**
     * 上传图片
     * @param imageType
     * @param imagePath
     * @param listener
     * @return
     */
    public Observable<String> imageUpload(String imageType, String imagePath, FileUploadingListener listener){
        RetrofitManager util = new RetrofitManager();
        RequestBody requestOptionType = util.createRequestBody(imageType);
        File image = new File(imagePath);

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(image, listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pictureFile", image.getName(), uploadFileRequestBody);

        return getService().imageUpload(requestOptionType,part).compose(this.<String>applySchedulers());
    }

    /**
     * 发布商品
     * @param goodsDetailsDto
     * @return
     */
    public Observable<String> createNewGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().createNewGoods(Constant.userLoginInfo.getToken(),goodsDetailsDto.getName(),goodsDetailsDto.getSubtitle(),
                goodsDetailsDto.getPrice(),goodsDetailsDto.getProductBannerUrl(),
                goodsDetailsDto.getFreightInMaxCount(),goodsDetailsDto.getFreightInEveryAdd(),
                goodsDetailsDto.getProductDetail(),goodsDetailsDto.getBannerUrl(),goodsDetailsDto.getIsInIndexActivity())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 修改商品
     * @param goodsDetailsDto
     * @return
     */
    public Observable<String> updateGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().updateGoods(Constant.userLoginInfo.getToken(),goodsDetailsDto.getProductNo(),goodsDetailsDto.getName(),goodsDetailsDto.getSubtitle(),
                goodsDetailsDto.getPrice(),goodsDetailsDto.getProductBannerUrl(),
                goodsDetailsDto.getFreightInMaxCount(),goodsDetailsDto.getFreightInEveryAdd(),
                goodsDetailsDto.getProductDetail(),goodsDetailsDto.getBannerUrl(),goodsDetailsDto.getIsInIndexActivity())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 商品管理 修改商品前 获取商品信息
     * @param productNo
     * @return
     */
    public Observable<GoodsDetailsDto> getGoodsInfo(String productNo){
        return getService().getGoodsInfo(Constant.userLoginInfo.getToken(),productNo)
                .compose(this.<GoodsDetailsDto>applySchedulers());
    }
}
