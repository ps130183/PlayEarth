package com.km.rmbank.event;

import com.km.rmbank.dto.IndustryDto;

/**
 * Created by PengSong on 18/7/11.
 */

public class SelectIndustryEvent {
    private IndustryDto industryDto;
    private int industryType;
    private int position;

    public SelectIndustryEvent(IndustryDto industryDto, int industryType, int position) {
        this.industryDto = industryDto;
        this.industryType = industryType;
        this.position = position;
    }

    public IndustryDto getIndustryDto() {
        return industryDto;
    }

    public int getIndustryType() {
        return industryType;
    }

    public int getPosition() {
        return position;
    }
}
