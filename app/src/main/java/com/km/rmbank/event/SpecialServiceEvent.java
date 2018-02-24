package com.km.rmbank.event;

import com.km.rmbank.dto.ScenicServiceDto;

/**
 * Created by PengSong on 18/2/7.
 */

public class SpecialServiceEvent {
    private ScenicServiceDto scenicServiceDto;

    public SpecialServiceEvent(ScenicServiceDto scenicServiceDto) {
        this.scenicServiceDto = scenicServiceDto;
    }

    public ScenicServiceDto getScenicServiceDto() {
        return scenicServiceDto;
    }
}
