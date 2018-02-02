package com.km.rmbank.event;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/4.
 */

public class ClubIntroduceEntity extends BaseEntity implements Parcelable {

    private List<String> introduceImgPaths;
    private String introduceContent;
    private int progress;
    private boolean isCanDelete;

    public List<String> getIntroduceImgPaths() {
        return introduceImgPaths;
    }

    public void setIntroduceImgPaths(List<String> introduceImgPaths) {
        this.introduceImgPaths = introduceImgPaths;
    }

    public String getIntroduceContent() {
        return introduceContent;
    }

    public void setIntroduceContent(String introduceContent) {
        this.introduceContent = introduceContent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(introduceContent) || introduceImgPaths == null || introduceImgPaths.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ClubIntroduceEntity{" +
                "introduceImgPath='" + introduceImgPaths.toString() + '\'' +
                ", introduceContent='" + introduceContent + '\'' +
                ", progress=" + progress +
                ", isCanDelete=" + isCanDelete +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.introduceImgPaths);
        dest.writeString(this.introduceContent);
        dest.writeInt(this.progress);
        dest.writeByte(this.isCanDelete ? (byte) 1 : (byte) 0);
    }

    public ClubIntroduceEntity() {
    }

    protected ClubIntroduceEntity(Parcel in) {
        this.introduceImgPaths = in.createStringArrayList();
        this.introduceContent = in.readString();
        this.progress = in.readInt();
        this.isCanDelete = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ClubIntroduceEntity> CREATOR = new Parcelable.Creator<ClubIntroduceEntity>() {
        @Override
        public ClubIntroduceEntity createFromParcel(Parcel source) {
            return new ClubIntroduceEntity(source);
        }

        @Override
        public ClubIntroduceEntity[] newArray(int size) {
            return new ClubIntroduceEntity[size];
        }
    };
}
