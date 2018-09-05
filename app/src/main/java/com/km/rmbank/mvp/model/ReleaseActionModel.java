package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ReleaseActionDetailsDto;
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
 * Created by PengSong on 18/8/21.
 */

public class ReleaseActionModel extends BaseModel {

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
     *  发布活动
     * @param title
     * @param startTime
     * @param endTime
     * @param imageContent
     * @param textContent
     * @return
     */
    public Observable<String> releaseAction(String title,String startTime,String endTime,String placeReservationId,String imageContent,String textContent){
        return getService().releaseAction(Constant.userLoginInfo.getToken(),title,startTime,endTime,placeReservationId,imageContent,textContent)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取发布的活动详细信息
     * @param id
     * @return
     */
    public Observable<ReleaseActionDetailsDto> getReleaseActionDetails(String id){
        return getService().getReleaseActionDetails(Constant.userLoginInfo.getToken(),id)
                .compose(this.<ReleaseActionDetailsDto>applySchedulers());
    }
}
