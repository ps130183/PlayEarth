package com.km.rmbank.event;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;

/**
 * Created by PengSong on 18/2/8.
 */

public class RoutePlanDrivingResultEvent {
    private int distance;
    private LatLng stLatlng;
    private LatLng enLatlng;

    public RoutePlanDrivingResultEvent(int distance, LatLng stLatlng, LatLng enLatlng) {
        this.distance = distance;
        this.stLatlng = stLatlng;
        this.enLatlng = enLatlng;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LatLng getStLatlng() {
        return stLatlng;
    }

    public void setStLatlng(LatLng stLatlng) {
        this.stLatlng = stLatlng;
    }

    public LatLng getEnLatlng() {
        return enLatlng;
    }

    public void setEnLatlng(LatLng enLatlng) {
        this.enLatlng = enLatlng;
    }
}
