package com.km.rmbank.mvp.model;

import com.km.rmbank.entity.IDCardEntity;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.retrofit.RetrofitManager;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.fileupload.UploadFileRequestBody;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by PengSong on 18/4/27.
 */

public class CertifyIDCardModel extends BaseModel {

    /**
     * 上传图片
     * @param imageType
     * @param imagePath
     * @param listener
     * @return
     */
    public Observable<String> uploadImage(String imageType, String imagePath, FileUploadingListener listener){
        RetrofitManager util = new RetrofitManager();
        RequestBody requestOptionType = util.createRequestBody(imageType);
        File image = new File(imagePath);

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(image, listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pictureFile", image.getName(), uploadFileRequestBody);

        return getService().imageUpload(requestOptionType,part).compose(this.<String>applySchedulers());
    }

    /**
     * 实名认证
     * @param entity
     * @param imageUrls
     * @return
     */
    public Observable<String> certifyIDCard(IDCardEntity entity,String imageUrls){
        return getService().certifyIDCard(Constant.userLoginInfo.getToken(),
                entity.getNum(),entity.getName(),imageUrls,entity.getAddress(),
                entity.getSex(),entity.getNation(),entity.getUnit(),entity.getStartDate(),entity.getEndDate())
                .compose(this.<String>applySchedulers());
    }

}
