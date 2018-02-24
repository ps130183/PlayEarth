package com.km.rmbank.event;

import com.km.rmbank.dto.MapMarkerDto;

/**
 * Created by PengSong on 18/2/8.
 */

public class MapLocationEvent {
    private boolean showRouteLine;

    private MapMarkerDto targetMarker;
    public MapLocationEvent() {
    }

    public MapLocationEvent(boolean showRouteLine) {
        this.showRouteLine = showRouteLine;
    }

    public boolean isShowRouteLine() {
        return showRouteLine;
    }

    public MapMarkerDto getTargetMarker() {
        return targetMarker;
    }

    public void setTargetMarker(MapMarkerDto targetMarker) {
        this.targetMarker = targetMarker;
    }
}
