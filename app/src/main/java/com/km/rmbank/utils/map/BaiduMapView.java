package com.km.rmbank.utils.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.km.rmbank.R;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.event.MapLocationEvent;
import com.km.rmbank.event.RoutePlanDrivingResultEvent;
import com.km.rmbank.utils.EventBusUtils;

import java.util.List;

/**
 * Created by PengSong on 18/2/6.
 */

public class BaiduMapView implements IMapView,SensorEventListener {

    private Context mContext;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    //系统传感器
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;

    //路线规划
    private LatLng stLatlng;
    private LatLng enLatlng;
    private MapMarkerDto endMarker;

    private SparseArray<MapMarkerDto> markerArray;
    private BitmapDescriptor iconJidiMarker = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_map_jidi);
    private BitmapDescriptor iconYizhanMarker = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_map_yizhan);

    public BaiduMapView(MapView mMapView) {
        this.mMapView = mMapView;
        mContext = mMapView.getContext();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        mMapView.onCreate(mContext,savedInstanceState);
        //获取传感器管理服务
        mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        mBaiduMap = mMapView.getMap();
        markerArray = new SparseArray<>();
    }

    /**
     * 开启地图定位
     */
    public void startLocation(){
        //定位模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置定位图标 地图默认
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        // 定位初始化
        mLocClient = new LocationClient(mContext);


        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000 * 10);
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(myListener);
        mLocClient.start();
    }

    /**
     * 添加覆盖物
     * @param mapMarkerDtos
     */
    public void addMarkerForMap(List<MapMarkerDto> mapMarkerDtos, final OnMarkerInfoClickListener markerInfoClickListener){
        for (MapMarkerDto markerDto : mapMarkerDtos){
            LatLng jidiLatLng = new LatLng(markerDto.getLatitude(), markerDto.getLongitude());
            BitmapDescriptor descriptor = iconJidiMarker;
            if (markerDto.getClubType().equals("3")){
                descriptor = iconYizhanMarker;
            }
            MarkerOptions jidiOptions = new MarkerOptions().position(jidiLatLng).icon(descriptor)
                    .zIndex(9).draggable(true);
            jidiOptions.animateType(MarkerOptions.MarkerAnimateType.grow);
            Marker mMarker = (Marker) (mBaiduMap.addOverlay(jidiOptions));

            markerArray.append(mMarker.hashCode(),markerDto);
        }

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final MapMarkerDto markerDto = markerArray.get(marker.hashCode());
                if (markerDto != null){
                    View view = LayoutInflater.from(mContext).inflate(R.layout.baidumap_marker_infowindow,null,false);
                    TextView markerView = view.findViewById(R.id.mapMarker);
                    markerView.setText(markerDto.getClubName());

                    LatLng ll = marker.getPosition();
                    InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -120, new InfoWindow.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick() {
                            if (markerInfoClickListener != null){
                                markerInfoClickListener.clickMarkerInfo(markerDto);
                            }
                        }
                    });
                    mBaiduMap.showInfoWindow(mInfoWindow);
                    MapLocationEvent event = new MapLocationEvent(false);
                    event.setTargetMarker(markerDto);
                    EventBusUtils.post(event);
                    enLatlng = new LatLng(markerDto.getLatitude(),markerDto.getLongitude());
                    stLatlng = new LatLng(mCurrentLat,mCurrentLon);
                    EventBusUtils.post(new RoutePlanDrivingResultEvent(0,stLatlng,enLatlng));
                }
                return true;
            }
        });

    }


    /**
     * 驾车路线规划
     */
    public void routePlanByDrive(){
        stLatlng = new LatLng(mCurrentLat,mCurrentLon);
        PlanNode stNode = PlanNode.withLocation(stLatlng);

        enLatlng = new LatLng(endMarker.getLatitude(),endMarker.getLongitude());
        PlanNode enNode = PlanNode.withLocation(enLatlng);

        RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
        planSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            //驾车路线规划结果
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    EventBusUtils.post(new RoutePlanDrivingResultEvent(0,stLatlng,enLatlng));
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    // result.getSuggestAddrInfo()
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                        List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
                        int distance = routeLines.get(0).getDistance();
//                        for (DrivingRouteLine routeLine : routeLines){
//                            distance += routeLine.getDistance();
//                        }
                        EventBusUtils.post(new RoutePlanDrivingResultEvent(distance,null,null));
                        DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
//                        routeOverlay = overlay;
                    mBaiduMap.setOnMarkerClickListener(overlay);
                        overlay.setData(drivingRouteResult.getRouteLines().get(0));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    } else {
                        Log.d("route result", "结果数<0");
                        return;
                    }

                }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });

        planSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));

        // 移动节点至中心
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(enLatlng));
    }


    /**
     * 开启百度地图导航
     * @param context
     */
    public void openNavigation(Context context) {
        // 构建 route搜索参数以及策略，起终点也可以用name构造
        RouteParaOption para = new RouteParaOption()
                .startPoint(stLatlng)
                .endPoint(enLatlng);
        try {
//            BaiduMapRoutePlan.openBaiduMapTransitRoute(para, this);
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
//            LogUtils.d("方向为 ： " + mCurrentDirection);
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction((float) mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            if (mBaiduMap != null){
                mBaiduMap.setMyLocationData(locData);
            }
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

//            LogUtils.d("当前位置描述：" + location.getCoorType() + "  " + location.getLocType());
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(14.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (endMarker != null){
                    EventBusUtils.post(new MapLocationEvent(true));
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public interface OnMarkerInfoClickListener{
        void clickMarkerInfo(MapMarkerDto markerDto);
    }


    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
    //        if (useDefaultIcon) {
    //            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
    //        }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
    //        if (useDefaultIcon) {
    //            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
    //        }
            return null;
        }
    }

    public void setEndMarker(MapMarkerDto endMarker) {
        this.endMarker = endMarker;
    }
}
