package com.km.rmbank.utils.fileupload;

/**
 * Created by kamangkeji on 17/4/7.
 */

public abstract class FileUploadingListener {
    //监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten*100 / contentLength));
    }

    //上传进度回调
    public abstract void onProgress(int progress);

    //上传进度回调
//    public abstract void onProgress(int progress);

}
