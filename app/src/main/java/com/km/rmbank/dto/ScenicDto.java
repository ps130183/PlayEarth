package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicDto implements Parcelable {
    private ClubDto clubDto;
    private List<ScenicServiceDto> scenicServiceDtos;

    public ScenicDto(ClubDto clubDto, List<ScenicServiceDto> scenicServiceDtos) {
        this.clubDto = clubDto;
        this.scenicServiceDtos = scenicServiceDtos;
    }

    public ClubDto getClubDto() {
        return clubDto;
    }

    public void setClubDto(ClubDto clubDto) {
        this.clubDto = clubDto;
    }

    public List<ScenicServiceDto> getScenicServiceDtos() {
        return scenicServiceDtos;
    }

    public void setScenicServiceDtos(List<ScenicServiceDto> scenicServiceDtos) {
        this.scenicServiceDtos = scenicServiceDtos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.clubDto, flags);
        dest.writeList(this.scenicServiceDtos);
    }

    protected ScenicDto(Parcel in) {
        this.clubDto = in.readParcelable(ClubDto.class.getClassLoader());
        this.scenicServiceDtos = new ArrayList<ScenicServiceDto>();
        in.readList(this.scenicServiceDtos, ScenicServiceDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<ScenicDto> CREATOR = new Parcelable.Creator<ScenicDto>() {
        @Override
        public ScenicDto createFromParcel(Parcel source) {
            return new ScenicDto(source);
        }

        @Override
        public ScenicDto[] newArray(int size) {
            return new ScenicDto[size];
        }
    };
}
