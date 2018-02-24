package com.km.rmbank.utils.map;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.baidu.mapapi.map.MapView;

/**
 * Created by PengSong on 18/2/6.
 */

public class MapFactory {

    public static BaiduMapView createBaiduMap(MapView mapView){
        return new BaiduMapView(mapView);
    }

}
