package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class ActionDto extends BaseEntity implements Parcelable {

    /**
     * activityPictureUrl : http://192.168.31.216:8088/img2542342
     * activityType : 1
     * content : 不错哦
     * createDate : 1490779228000
     * durationDate : 1490779224000
     * id : 14
     * holdDate : 1489483221000
     * title : 活动
     * updateDate : 1490779230000
     */

    private String activityPictureUrl;
    private String activityType;//1 审核通过，0待审核，2拒绝
    private String content;
    private long createDate;
    private long durationDate;
    private String id;
    private String holdDate;
    private long startDate;
    private long endDate;
    private String title;
    private String address;
    private String flow;
    private String webActivityUrl;
    private long updateDate;
    private List<ActionGuestBean> guestList;
    private String clubName;
    private String clubLogo;
    private String clubId;


    private String backgroundImg;
    private String clubContent;
    private int keepStatus;
    private String mobilePhone;

    private String code;

    private int applyCount;

    private String status;

    private List<ActionMemberDto> actionMemberDtos;

    private int isDynamic;
    /**
     * endDate : 1535112300000
     * guestList : []
     * isDynamic : 0
     * isRecommend : 1
     * keepStatus : 0
     * music :
     * newType : 1
     * placeReservationId : 15
     * reason : <div style="width: 100%;font-size: 14px;line-height: 20px;">			<p style="text-align: center;margin-bottom: 28px;">拒绝原因</p>			<span style="display: block;">尊敬的用户你好：</span>			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你申请的2018年08月24日 04:05-2018年08月24日 20:05唐人汇天天英雄会场地没有审核通过。</span>			<span style="display: block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未通过理由：审核就是不通过。</span>			<br />			<span style="text-align:right;width: 100%;display: block;">感谢你的理解</span>			<span style="text-align:right;width: 100%;display: block;">2018年08月21日 14:47</span>		</div>
     * saleMemberType :
     * seatNum : 22
     * teachingUserId :
     * type : 1
     */

    private int seatNum;
    private String placeName;


    public String getActivityPictureUrl() {
        return activityPictureUrl;
    }

    public void setActivityPictureUrl(String activityPictureUrl) {
        this.activityPictureUrl = activityPictureUrl;
    }

    @Override
    public String toString() {
        return "ActionDto{" +
                "activityPictureUrl='" + activityPictureUrl + '\'' +
                ", activityType='" + activityType + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", durationDate=" + durationDate +
                ", id='" + id + '\'' +
                ", holdDate='" + holdDate + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", flow='" + flow + '\'' +
                ", webActivityUrl='" + webActivityUrl + '\'' +
                ", updateDate=" + updateDate +
                ", guestList=" + guestList +
                ", clubName='" + clubName + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubId='" + clubId + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", clubContent='" + clubContent + '\'' +
                ", keepStatus=" + keepStatus +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", code='" + code + '\'' +
                ", applyCount=" + applyCount +
                ", status='" + status + '\'' +
                ", actionMemberDtos=" + actionMemberDtos +
                ", isDynamic=" + isDynamic +
                ", seatNum=" + seatNum +
                ", placeName='" + placeName + '\'' +
                '}';
    }


    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<ActionMemberDto> getActionMemberDtos() {
        return actionMemberDtos;
    }

    public void setActionMemberDtos(List<ActionMemberDto> actionMemberDtos) {
        this.actionMemberDtos = actionMemberDtos;
    }

    public int getIsDynamic() {
        return isDynamic;
    }

    public void setIsDynamic(int isDynamic) {
        this.isDynamic = isDynamic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getWebActivityUrl() {
        return webActivityUrl;
    }

    public void setWebActivityUrl(String webActivityUrl) {
        this.webActivityUrl = webActivityUrl;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(long durationDate) {
        this.durationDate = durationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public List<ActionGuestBean> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<ActionGuestBean> guestList) {
        this.guestList = guestList;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getClubContent() {
        return clubContent;
    }

    public void setClubContent(String clubContent) {
        this.clubContent = clubContent;
    }

    public int getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(int keepStatus) {
        this.keepStatus = keepStatus;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public ActionDto() {
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(activityPictureUrl) || TextUtils.isEmpty(title) || TextUtils.isEmpty(holdDate)
                || TextUtils.isEmpty(address) || TextUtils.isEmpty(flow)){
            return true;
        }
        if (guestList != null){
            boolean isEmpty = false;
            for (ActionGuestBean bean : guestList){
                isEmpty = bean.isEmpty();
                if (isEmpty){
                    break;
                }
            }
            return isEmpty;
        }
        return false;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public static class ActionGuestBean extends BaseEntity implements Parcelable {
        private String title;
        private String avatarUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "ActionGuestBean{" +
                    "title='" + title + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    '}';
        }


        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public ActionGuestBean() {
        }

        @Override
        public boolean isEmpty() {
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(avatarUrl)){
                return true;
            }
            return false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.avatarUrl);
        }

        protected ActionGuestBean(Parcel in) {
            this.title = in.readString();
            this.avatarUrl = in.readString();
        }

        public static final Creator<ActionGuestBean> CREATOR = new Creator<ActionGuestBean>() {
            @Override
            public ActionGuestBean createFromParcel(Parcel source) {
                return new ActionGuestBean(source);
            }

            @Override
            public ActionGuestBean[] newArray(int size) {
                return new ActionGuestBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityPictureUrl);
        dest.writeString(this.activityType);
        dest.writeString(this.content);
        dest.writeLong(this.createDate);
        dest.writeLong(this.durationDate);
        dest.writeString(this.id);
        dest.writeString(this.holdDate);
        dest.writeLong(this.startDate);
        dest.writeLong(this.endDate);
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeString(this.flow);
        dest.writeString(this.webActivityUrl);
        dest.writeLong(this.updateDate);
        dest.writeTypedList(this.guestList);
        dest.writeString(this.clubName);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubId);
        dest.writeString(this.backgroundImg);
        dest.writeString(this.clubContent);
        dest.writeInt(this.keepStatus);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.code);
        dest.writeInt(this.applyCount);
        dest.writeString(this.status);
        dest.writeTypedList(this.actionMemberDtos);
        dest.writeInt(this.isDynamic);
        dest.writeInt(this.seatNum);
        dest.writeString(this.placeName);
    }

    protected ActionDto(Parcel in) {
        this.activityPictureUrl = in.readString();
        this.activityType = in.readString();
        this.content = in.readString();
        this.createDate = in.readLong();
        this.durationDate = in.readLong();
        this.id = in.readString();
        this.holdDate = in.readString();
        this.startDate = in.readLong();
        this.endDate = in.readLong();
        this.title = in.readString();
        this.address = in.readString();
        this.flow = in.readString();
        this.webActivityUrl = in.readString();
        this.updateDate = in.readLong();
        this.guestList = in.createTypedArrayList(ActionGuestBean.CREATOR);
        this.clubName = in.readString();
        this.clubLogo = in.readString();
        this.clubId = in.readString();
        this.backgroundImg = in.readString();
        this.clubContent = in.readString();
        this.keepStatus = in.readInt();
        this.mobilePhone = in.readString();
        this.code = in.readString();
        this.applyCount = in.readInt();
        this.status = in.readString();
        this.actionMemberDtos = in.createTypedArrayList(ActionMemberDto.CREATOR);
        this.isDynamic = in.readInt();
        this.seatNum = in.readInt();
        this.placeName = in.readString();
    }

    public static final Creator<ActionDto> CREATOR = new Creator<ActionDto>() {
        @Override
        public ActionDto createFromParcel(Parcel source) {
            return new ActionDto(source);
        }

        @Override
        public ActionDto[] newArray(int size) {
            return new ActionDto[size];
        }
    };
}
